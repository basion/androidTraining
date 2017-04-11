package cn.com.hakim.androidtraining.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/12/27.
 */
@CoordinatorLayout.DefaultBehavior(CustomTooBarLayout.Behavior.class)
public class CustomTooBarLayout extends CoordinatorLayout {
    private AppBarLayout mAppBarLayout;
    private View hideView;
    private View pinView;
    public CustomTooBarLayout(Context context) {
        super(context);
        preView(context,null,-1);
    }

    public CustomTooBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        preView(context,attrs,-1);
    }

    public CustomTooBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preView(context,attrs,defStyleAttr);
    }
    private void preView(Context context, AttributeSet attrs, int defStyleAttr){

    }

    AppBarLayout.Behavior appBarBehavior;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAppBarLayout = (AppBarLayout) getChildAt(0);
        hideView = getChildAt(1);
        pinView = getChildAt(2);
        if (mAppBarLayout == null||hideView == null||pinView==null){
            throw new IllegalArgumentException("you should use this view in the right order");
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
