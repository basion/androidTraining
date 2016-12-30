package cn.com.hakim.androidtraining;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import cn.com.hakim.androidtraining.view.CustomLinearLayout;

/**
 * Created by Administrator on 2016/12/19.
 */

public class NestedScrollActivity extends AppCompatActivity {
    private static final String TAG = "NestedScrollActivity";
    CustomLinearLayout parentNestedLinearLayout;
    NestedScrollView mNestedScrollView;
    LinearLayout titleLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);
        initView();
    }
    private void initData(){

    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
    int titleHeight;
    int titleHeightRange;
    private void initView() {
        titleLayout = (LinearLayout) findViewById(R.id.linear_title);
        titleHeight = titleLayout.getHeight();
        titleHeightRange = titleHeight;
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d(TAG,"scrollX= "+scrollX+"\noldScrollX= "+oldScrollX);
                Log.d(TAG,"scrollY= "+scrollY+"\noldScrollY= "+oldScrollY);
                int dis =scrollY-oldScrollY;//移动距离，负数向上，正数向下
            }
        });
        parentNestedLinearLayout = (CustomLinearLayout) findViewById(R.id.layout_nested_parent);
    }
}
