package cn.com.hakim.androidtraining;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/8/28.
 */
public class ZhihuNewActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_new);
        initView();
        initData();
    }
    Button bt1,bt2;
    Button btShowBottom;
    EditText mEditText;
    BottomSheetBehavior mBottomSheetBehavior;
    private static final String TAG = "ZhihuNewActivity";
    private void initView() {
        btShowBottom = (Button) findViewById(R.id.bt_show_bottom);
        btShowBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"onclick");
                int state = mBottomSheetBehavior.getState();
                if (state == BottomSheetBehavior.STATE_COLLAPSED){
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else if (state == BottomSheetBehavior.STATE_EXPANDED){
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        bt1 = (Button) findViewById(R.id.bt_1);
        bt2 = (Button) findViewById(R.id.bt_2);
        mEditText = (EditText) findViewById(R.id.edit_test);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.layout_parent));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.e(TAG,"onStateChanged newState = "+newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_1){
            mEditText.requestFocus();
        }else if (id == R.id.bt_2){
            mEditText.clearFocus();
        }
    }
}
