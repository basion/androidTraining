package cn.com.hakim.androidtraining.other;

import android.view.View;

/**
 * Created by Administrator on 2016/12/26.
 */

public interface ParentAndChild {
    //parent
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes);
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes);
    public void onStopNestedScroll(View target);
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed);
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed);
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed);
    public boolean onNestedPreFling(View target, float velocityX, float velocityY);
    public int getNestedScrollAxes();
    //child
    public void setNestedScrollingEnabled(boolean enabled);
    public boolean isNestedScrollingEnabled();
    public boolean startNestedScroll(int axes);
    public void stopNestedScroll();
    public boolean hasNestedScrollingParent();
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow);
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow);
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed);
    public boolean dispatchNestedPreFling(float velocityX, float velocityY);
}
