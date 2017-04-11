package cn.com.hakim.viewpagertest.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import cn.com.hakim.viewpagertest.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import cn.com.hakim.viewpagertest.R;

/**
 * Created by Administrator on 2017/4/6.
 */

public class CDragLayout extends FrameLayout {
    private static final String TAG = "CDragLayout";

    public CDragLayout(@NonNull Context context) {
        super(context);
        initView(context,null,-1);
    }

    public CDragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs,-1);
    }

    public CDragLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs,defStyleAttr);
    }
    private ViewDragHelper mDragger;
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child.getId() == pagerView.getId();
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return super.clampViewPositionVertical(child, top, dy);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                return super.clampViewPositionHorizontal(child, left, dx);
                return left;
            }

            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
            }
            //在边界拖动回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }
        });
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    public void dispatchDown(MotionEvent event){
        dispatchTouchEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getMetaState()==99){
            Log.e(TAG," dispatchTouchEvent伪造的action_down");
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll()
    {
        if(mDragger.continueSettling(true))
        {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);

//        mAutoBackOriginPos.x = mAutoBackView.getLeft();
//        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }
    private ViewPager pagerView;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        pagerView = (ViewPager) findViewById(R.id.pager_main);
    }
}
