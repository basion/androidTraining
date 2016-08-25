package cn.com.hakim.androidtraining.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/8/25.
 */
public class MyBottomBehavior extends FloatingActionButton.Behavior{

    public MyBottomBehavior(Context context, AttributeSet attrs) {
        super();
    }
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }
    private static final String TAG = "MyBottomBehavior";
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(TAG,"滑动onNestedScroll");
        Log.e(TAG,"dyConsumed="+dyConsumed+"  dyUnconsumed="+dyUnconsumed);
        if ((dyConsumed > 0 || dyUnconsumed > 0) && child.getVisibility() == View.VISIBLE) {//往下滑
            if (mOnStateChangedListener != null) {
                mOnStateChangedListener.onChanged(false);
                Log.d(TAG,"往下滑");
            }
        } else if ((dyConsumed < 0 || dyUnconsumed < 0)) {//往上滑
            if (mOnStateChangedListener != null) {
                mOnStateChangedListener.onChanged(true);
                Log.d(TAG,"往上滑");
            }
        }
    }

    private OnStateChangedListener mOnStateChangedListener;
    public void setOnStateChangedListener(OnStateChangedListener mOnStateChangedListener) {
        this.mOnStateChangedListener = mOnStateChangedListener;
    }

    // 外部监听显示和隐藏。
    public interface OnStateChangedListener {
        void onChanged(boolean isShow);
    }

    public static <V extends View> MyBottomBehavior from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof MyBottomBehavior)) {
            throw new IllegalArgumentException("The view is not associated with ScaleDownShowBehavior");
        }
        return (MyBottomBehavior) behavior;
    }
}
