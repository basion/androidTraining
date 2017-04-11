package cn.com.hakim.androidtraining.plugin;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.com.hakim.androidtraining.R;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ThemeActivity extends AppCompatActivity {
    private String mCachedFileName1;//保存到缓存目录文件名
    //插件文件名
    public static String PLUGIN_FILE_NAME1 = "themeapp.apk";
    public static String DRAWABLE_ID = "icon_0_1";


    private RadioButton tab;
    private Button changeButton;
    private ProgressDialog mLoadingDialog;
    private ImageView themeImg;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        initView();
        initData();
    }

    private void initData() {
        mCachedFileName1 = ThemeActivity.this.getCacheDir() + File.separator + ThemeActivity.PLUGIN_FILE_NAME1;
    }

    private void initView() {
        themeImg = (ImageView) findViewById(R.id.img_theme);
        tab = (RadioButton) findViewById(R.id.radio_left);
        changeButton = (Button) findViewById(R.id.bt_change);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asycLoadPlugin();
            }
        });
        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setTitle("正在加载...");
    }

    private Resources loadResources(String dexFile) {
        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexFile);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Resources superRes = getResources();
        Resources skinRes = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        return skinRes;
    }
    //皮肤包资源对象
    private Resources mSkinResouces;
    private boolean isThemeInited = false;
    private Handler mHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }

            getPackageNames();
            mSkinResouces = loadResources(mCachedFileName1);

            if (mSkinResouces!=null) {
                isThemeInited = true;
                changeSkin(msg.what);
            }
        }
    };

    /**
     * 获得对应的包名
     */
    String mPackageName;
    private void getPackageNames() {
        PackageInfo mInfo1 = getPackageManager().getPackageArchiveInfo(mCachedFileName1, PackageManager.GET_ACTIVITIES);
        mPackageName = mInfo1.packageName;
    }
    private void asycLoadPlugin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                copyPlugin2Cache(ThemeActivity.PLUGIN_FILE_NAME1);
                mHandler.obtainMessage().sendToTarget();
            }
            private void copyPlugin2Cache(String fileName) {
                InputStream is = null;
                FileOutputStream fo = null;
                try {
                    is = getResources().getAssets().open(fileName);
                    if (is == null) {
                        return;
                    }
                    fo = new FileOutputStream(mCachedFileName1);
                    byte[] buffer = new byte[4 * 1024];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        fo.write(buffer, 0, len);
                        fo.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fo != null) {
                        try {
                            fo.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void changeSkin(int i) {
        int drawableId = mSkinResouces.getIdentifier(DRAWABLE_ID, "drawable", mPackageName);
//        int textId = mSkinResouces[i].getIdentifier(SkinConstants.TEXT_ID, "string", mPackageName[i]);
//        int textSizeId = mSkinResouces[i].getIdentifier(SkinConstants.TEXT_SIZE_ID, "dimen", mPackageName[i]);
//        int textColorId = mSkinResouces[i].getIdentifier(SkinConstants.TEXT_COLOR_ID, "color", mPackageName[i]);
//        tab.setBackgroundDrawable(mSkinResouces[i].getDrawable(drawableId));
        tab.setCompoundDrawables(mSkinResouces.getDrawable(drawableId),null,null,null);
        themeImg.setBackground(mSkinResouces.getDrawable(drawableId));
//        textView.setText(mSkinResouces[i].getString(textId));
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSkinResouces[i].getDimension(textSizeId));
//        textView.setTextColor(mSkinResouces[i].getColor(textColorId));
    }
}
