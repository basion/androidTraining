package cn.com.hakim.androidtraining;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yolanda.nohttp.BasicBinary;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.com.hakim.androidtraining.bean.User;
import cn.com.hakim.androidtraining.nohttp.CallServer;
import cn.com.hakim.androidtraining.nohttp.HttpListener;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/1/12.
 */

public class HttpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String mBaseUrl = "http://192.168.3.112:8080/";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //透明状态栏(手机头部通知栏)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        initData();
    }

    private void initData() {

    }

    Button okGetJsonButton, okPostJsonButton, okPostFileButton;
    Button noPostJsonButton, noPostFileButton;

    private void initView() {
        okGetJsonButton = (Button) findViewById(R.id.bt_ok_get_json);
        okGetJsonButton.setOnClickListener(this);
        okPostJsonButton = (Button) findViewById(R.id.bt_ok_post_json);
        okPostJsonButton.setOnClickListener(this);
        okPostFileButton = (Button) findViewById(R.id.bt_ok_post_file);
        okPostFileButton.setOnClickListener(this);
        noPostJsonButton = (Button) findViewById(R.id.bt_no_post_json);
        noPostJsonButton.setOnClickListener(this);
        noPostFileButton = (Button) findViewById(R.id.bt_no_post_file);
        noPostFileButton.setOnClickListener(this);
        RectF f = new RectF(0,0,100,100);

    }

    private static final int GET = 1;
    private static final int POST = 2;

    private void okHttpJson(int method) {
        String url = mBaseUrl + "user/selectUserById";
        if (method == 1) {
            OkHttpUtils
                    .get()
                    .id(101)
                    .url(url)
                    .addParams("id","1")
                    .build()
                    .execute(new MyStringCallback());
        } else if (method == 2) {
            OkHttpUtils
                    .postString()
                    .id(100)
                    .url(url)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(new Gson().toJson(new User("zhy", 1)))
                    .build()
                    .execute(new MyStringCallback());
        }
    }

    private static final String TAG = "HttpActivity";

    @Override
    public void onClick(View v) {
        int id =v.getId();
        if (id == R.id.bt_ok_get_json){
            okHttpJson(GET);
        }else if (id == R.id.bt_ok_post_json){
            okHttpJson(POST);
        }else if (id == R.id.bt_ok_post_file){
            okHttpFile(true);
        }else if (id == R.id.bt_no_post_json){
            noHttpJson();
        }else if (id == R.id.bt_no_post_file){
            noHttpFile(true);
        }
    }

    private void noHttpJson() {
        String url = mBaseUrl + "user/selectUserById";
        /**
         * 这里要注意的是：
         * 1. 请求方法一定是POST、PUT等可以直接写流出去的方法。
         */
        com.yolanda.nohttp.rest.Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        /**
         * 下面就是怎么setBody，几种方法，根据自己的需要选择：
         */

//        1. 这里可以push任何数据上去（比如String、Json、XML、图片）。
//        request.setDefineRequestBody(InputStream body, String contentType);

//        2. 这里可以push任何string数据（json、xml等），并可以指定contentType。
//        request.setDefineRequestBody(String body, String contentType);

//        3. 下面的两个的contentType默认为application/json，传进去的数据要为json。
//        request.setDefineRequestBodyForJson(JSONObject jsonBody);
//        request.setDefineRequestBodyForJson(String jsonBody);

//        4. 这里的contentType默认为application/xml。
//        request.setDefineRequestBodyForXML(String xmlBody);

        // 这里我们用json多例子
        String jsonBody = new Gson().toJson(new User("zhy", 1));
            Logger.i("提交的数据：" + jsonBody);
            request.setDefineRequestBodyForJson(jsonBody);
            request(0, request, httpListener, false, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response<String> response) {
            showMessageDialog(R.string.request_succeed, response.get());
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            showMessageDialog(R.string.request_failed, response.getException().getMessage());
        }
    };


    private final int WHAT_UPLOAD_SINGLE = 0x01;//单个文件上传标志位
    private void noHttpFile(boolean b) {
        String url = mBaseUrl + "user/upload";
        com.yolanda.nohttp.rest.Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        File file = new File(filePath);
        if (file.exists()){
            BasicBinary binary = new FileBinary(file);
            binary.setUploadListener(WHAT_UPLOAD_SINGLE, mOnUploadListener);
            request.add("file", binary);// 添加1个文件
            request(0, request, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    showMessageDialog(R.string.request_succeed, response.get());
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    showMessageDialog(R.string.request_failed, response.getException().getMessage());
                }
            }, false, true);
            return;
        }
        initFile(b);
    }
    public void showMessageDialog(int title, CharSequence message) {
        showMessageDialog(getText(title), message);
    }
    public void showMessageDialog(CharSequence title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.know, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    public <T> void request(int what, com.yolanda.nohttp.rest.Request<T> request, HttpListener<T> callback, boolean canCancel, boolean isLoading) {
        request.setCancelSign(this);
        CallServer.getRequestInstance().add(this, what, request, callback, canCancel, isLoading);
    }
    /**
     * 文件上传监听。
     */
    private OnUploadListener mOnUploadListener = new OnUploadListener() {

        @Override
        public void onStart(int what) {// 这个文件开始上传。
            Log.e(TAG,"文件"+what+"开始上传");
        }

        @Override
        public void onCancel(int what) {// 这个文件的上传被取消时。
            Log.e(TAG,"文件"+what+"上传被取消");
        }

        @Override
        public void onProgress(int what, int progress) {// 这个文件的上传进度发生边耍
            Log.e(TAG,"文件"+what+"进度为："+progress);
        }

        @Override
        public void onFinish(int what) {// 文件上传完成
            Log.e(TAG,"文件"+what+"上传完成");
        }

        @Override
        public void onError(int what, Exception exception) {// 文件上传发生错误。
            Log.e(TAG,"文件"+what+"发生错误");
        }
    };
    private void initFile(boolean upload){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), dir);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return;
            }
        }


        File file = new File(filePath);
        InputStream inputStream ;
        try {
            inputStream  = getAssets().open("bg_mine.jpg");
            if (null != inputStream){
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(buffer))!= -1){
                    outputStream.write(buffer,0,length);
                }
                outputStream.close();
                inputStream.close();
            }
            if (upload){
                okHttpFile(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final String dir = "/androidTraining/";
    private String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+dir+"bg_mine.jpg";
    private void okHttpFile(boolean upload) {
        String url = mBaseUrl + "user/upload";
        File file = new File(filePath);
        if (file.exists()){
            OkHttpUtils
                    .postFile()
                    .url(url)
                    .file(file)
                    .build()
                    .execute(new MyStringCallback());
            return;
        }
        initFile(true);
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("测试http");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            Toast.makeText(HttpActivity.this, response, Toast.LENGTH_SHORT).show();
            switch (id) {
                case 100:

                    break;
            }
        }

    }
}
