package cn.com.hakim.viewpagertest.view;

import android.content.Context;
import cn.com.hakim.viewpagertest.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/4/7.
 */

public class CViewPager extends ViewPager {
    public CViewPager(Context context) {
        super(context);
        initView(context, null);
    }

    public CViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
    private static final String TAG = "CViewPager";
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(TAG,"onTouchEvent");
        return super.onTouchEvent(ev);
//        return false;
    }
}
