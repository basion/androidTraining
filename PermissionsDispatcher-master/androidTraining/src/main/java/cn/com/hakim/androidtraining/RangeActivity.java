package cn.com.hakim.androidtraining;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import cn.com.hakim.androidtraining.view.CAppBarLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/5.
 */

public class RangeActivity extends AppCompatActivity{
    private static final String TAG = "RangeActivity" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        initData();
//        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
//       Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }

    private static final int ID_UTIL_OK_HTTP = 1;
    private void utilOkHttp(){
        OkHttpUtils.get()
                .url("https://baidu.com")
                .id(ID_UTIL_OK_HTTP)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                switch (id){
                    case ID_UTIL_OK_HTTP:
                        Log.e(TAG,response);
                        break;
                }
            }
        });
    }
    private void baseOkHttp(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://blog.csdn.net/lmj623565791/article/details/47911083")
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e(TAG,str);
            }
        });
    }
    private void initData() {
//        baseOkHttp();
        utilOkHttp();
    }

    CAppBarLayout mCAppBarLayout;
    private void initView() {
        mCAppBarLayout = (CAppBarLayout) findViewById(R.id.appbarLayout);
        mCAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mCAppBarLayout.getDownRange();
            }
        });
    }
}
