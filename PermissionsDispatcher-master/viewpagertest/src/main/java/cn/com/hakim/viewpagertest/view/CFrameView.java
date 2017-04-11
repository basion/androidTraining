package cn.com.hakim.viewpagertest.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import cn.com.hakim.viewpagertest.R;

/**
 * Created by Administrator on 2017/4/6.
 */

public class CFrameView extends FrameLayout {
    public CFrameView(@NonNull Context context) {
        super(context);
        initView(context, null,-1);
    }

    public CFrameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs,-1);
    }

    public CFrameView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }
    private ViewDragHelper mDragger;
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                boolean capture =  child.getId() == pagerView.getId();
                Log.e(TAG,"tryCaptureView capture="+capture);
                return capture;
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
        initPagerView();
    }
    private ViewPager pagerView;
    private void initPagerView() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (mIntercept){
//            mDragger.shouldInterceptTouchEvent(ev);
//            return true;
//        }
        Log.e(TAG,"dispatchTouchEvent");
        return mDragger.shouldInterceptTouchEvent(ev);
    }
    private boolean mIntercept = false;
    public void requireIntercept(boolean intercept){
        mIntercept = intercept;
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        pagerView = (ViewPager) findViewById(R.id.pager_main);
    }

    private static final String TAG = "CFrameView";
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = mDragger.shouldInterceptTouchEvent(ev);
        Log.e(TAG,"onInterceptTouchEvent intercept="+intercept);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        Log.e(TAG,"onTouchEvent");
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
}
