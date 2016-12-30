package cn.com.hakim.androidtraining.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/12/14.
 */

public class SuspendLinearLayout extends LinearLayout {
    public SuspendLinearLayout(Context context) {
        super(context);
        initView(context,null,0);
    }

    public SuspendLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs,0);
    }

    public SuspendLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs,defStyleAttr);
    }
    private void initView(Context context, AttributeSet attrs, int defStyleAttr){
        ViewGroup parent = (ViewGroup) getParent();
        if (!(parent instanceof LinearLayout)){
            throw new RuntimeException("parent must be LinearLayout");
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
