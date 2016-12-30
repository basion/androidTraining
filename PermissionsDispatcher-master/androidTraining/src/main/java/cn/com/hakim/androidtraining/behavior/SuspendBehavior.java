package cn.com.hakim.androidtraining.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.com.hakim.androidtraining.R;

/**
 * Created by Administrator on 2016/12/12.
 */

public class SuspendBehavior extends CoordinatorLayout.Behavior<View> {
    public SuspendBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuspendBehavior() {
    }

    private static final String TAG = "SuspendBehavior";
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int scrollY = dependency.getScrollY();
        Log.d(TAG,"scrollY = "+scrollY);
        float y = dependency.getY();
        Log.e(TAG,"y = "+y);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.layout_buy;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
