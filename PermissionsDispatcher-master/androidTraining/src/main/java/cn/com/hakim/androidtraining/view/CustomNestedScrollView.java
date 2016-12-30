package cn.com.hakim.androidtraining.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/12/19.
 */

public class CustomNestedScrollView extends NestedScrollView implements NestedScrollingChild{
    public CustomNestedScrollView(Context context) {
        super(context);
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //来自NestedScrollingChild的方法
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        super.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
