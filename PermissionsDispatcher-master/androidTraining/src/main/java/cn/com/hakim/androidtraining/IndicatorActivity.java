package cn.com.hakim.androidtraining;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.ScrollIndicatorView;

/**
 * Created by Administrator on 2016/8/19.
 */
public class IndicatorActivity extends AppCompatActivity{
    ScrollIndicatorView mIndicatorView;
    ViewPager mViewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        mIndicatorView = (ScrollIndicatorView) findViewById(R.id.indicator);
        Indicator.IndicatorAdapter indicatorAdapter = new Indicator.IndicatorAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }
        };
        mIndicatorView.setAdapter(indicatorAdapter);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }
}
