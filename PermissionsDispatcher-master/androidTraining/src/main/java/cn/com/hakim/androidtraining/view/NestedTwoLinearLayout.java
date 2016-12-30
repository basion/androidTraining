package cn.com.hakim.androidtraining.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.Scroller;
import android.widget.Toast;

import cn.com.hakim.androidtraining.R;

/**
 * Created by Administrator on 2016/12/20.
 */

public class NestedTwoLinearLayout extends LinearLayout implements NestedScrollingParent, NestedScrollingChild {
    public NestedTwoLinearLayout(Context context) {
        super(context);
        preView(context, null, -1);
    }

    public NestedTwoLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        preView(context, attrs, -1);
    }

    public NestedTwoLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preView(context, attrs, defStyleAttr);
    }

    private static final String TAG = "NestedTwoLinearLayout";
    private int level;
    float hideHeightSize;

    private void preView(Context context, AttributeSet attrs, int defStyleAttr) {
        mParentHelper = new NestedScrollingParentHelper(this);
        mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);//必须设置可以嵌套滑动，否则事件不会传递到父view
        if (getOrientation() == LinearLayout.HORIZONTAL) {
            throw new IllegalArgumentException("this view must be vertical");
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NestedTwoLinearLayout);
        level = typedArray.getInteger(R.styleable.NestedTwoLinearLayout_hierarchy_level, -1);
        hideHeightSize = typedArray.getDimension(R.styleable.NestedTwoLinearLayout_hide_size, 0);
        Log.e(TAG, "hideHeightSize = " + hideHeightSize);
        typedArray.recycle();
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
//        mTouchSlop = 1;
        mScroller = new OverScroller(context);
    }

    public float getHideHeight() {
        return hideHeightSize;
    }


    private View hideView;//暂定第一个子view有显示/隐藏属性。
    private View pinView;//暂定第二个view固定
    private View contentView;//暂定第三个view触发nestedScrolling
    private boolean isMostInset = false;
    NestedScrollingParentHelper mParentHelper;
    NestedScrollingChildHelper mChildHelper;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        hideView = getChildAt(0);
        pinView = getChildAt(1);
        contentView = getChildAt(2);
        if (contentView != null) {
            if (contentView instanceof NestedScrollingChild) {

            }
        }
        if (hideView == null || pinView == null) {
            throw new RuntimeException("there must have a hideView and a fixedView under it");
        }
    }

    private int scrollRange;
    private int hideViewHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        hideViewHeight = hideView.getMeasuredHeight();
        scrollRange = hideViewHeight;
    }

    //nestedParent实现方法
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (dy > 0) {//上滑动
            if (scrollRange > 0) {
                scrollBy(0, dy);
                scrollRange -= dy;
                int handleDy = dy;
                if (scrollRange < 0) {
                    handleDy = dy + scrollRange;
                    scrollRange = 0;
                }
                consumed[1] = handleDy;
            } else {
                startNestedScroll(dy);
                consumed[1] = hideViewHeight;
                dispatchNestedPreScroll(dx, dy, consumed, consumed);
            }
        } else {//下滑动
            if (scrollRange < hideViewHeight) {
                scrollBy(0, dy);
                scrollRange -= dy;
                int handleDy = dy;
                if (scrollRange > hideViewHeight) {
                    handleDy = dy + scrollRange - hideViewHeight;
                    scrollRange = hideViewHeight;
                }
                consumed[1] = handleDy;
            } else {
                startNestedScroll(dy);
                consumed[1] = 0;
                dispatchNestedPreScroll(dx, dy, consumed, consumed);
            }
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e(TAG, "onNestedPreFling");
        //down - //up+
        if (getScrollY() >= hideViewHeight) {
            return false;
        }
        fling((int) velocityY);
        return false;
    }

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, hideViewHeight);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > hideViewHeight) {
            y = hideViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        switch (heightSize){
//            case MeasureSpec.AT_MOST://wrap_content
//
//        }
        float parentHideSize = 0;
        float grandpaHideSize = 0;
//        int parentMarginTopAndBottom = 0;
//        int grandpaMarginTopAndBottom = 0;
        if (contentView != null) {
            switch (level) {
                case 3:
                    parentHideSize = ((NestedTwoLinearLayout) getParent()).getHideHeight();
                    grandpaHideSize = ((NestedTwoLinearLayout) (getParent().getParent())).getHideHeight();
//
//                    LinearLayout.LayoutParams parentParams = (LayoutParams) ((NestedTwoLinearLayout) getParent()).getLayoutParams();
//                    parentMarginTopAndBottom = parentParams.topMargin + parentParams.bottomMargin;
//                    NestedTwoLinearLayout grandPa = (NestedTwoLinearLayout) getParent().getParent();
//                    MarginLayoutParams grandpaParams = (MarginLayoutParams) grandPa.getLayoutParams();
//                    grandpaMarginTopAndBottom = grandpaParams.topMargin + grandpaParams.bottomMargin;
                    break;
                case 2:
                    parentHideSize = ((NestedTwoLinearLayout) getParent()).getHideHeight();
//                    MarginLayoutParams parentParams_ = (MarginLayoutParams) ((NestedTwoLinearLayout) getParent()).getLayoutParams();
//                    parentMarginTopAndBottom = parentParams_.topMargin + parentParams_.bottomMargin;
                    break;
                case 1:
                    break;
            }
            ViewGroup.LayoutParams params = contentView.getLayoutParams();
            params.height = (int) (getMeasuredHeight() - pinView.getMeasuredHeight() + parentHideSize + grandpaHideSize );
            contentView.setLayoutParams(params);
            setMeasuredDimension(hideView.getMeasuredWidth(), hideView.getMeasuredHeight() + pinView.getMeasuredHeight() + contentView.getMeasuredHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    //childNested
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        super.setNestedScrollingEnabled(enabled);
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    //
    private static final int INVALID_POINTER = -1;

    private int mTouchSlop;//
    private int mActivePointerId = INVALID_POINTER;
    private float mLastMotionY;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private boolean mIsBeginDrag = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                final float initialDownY = getMotionEventY(ev, mActivePointerId);
                if (initialDownY == -1) {
                    return false;
                }
                mLastMotionY = initialDownY;
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);//触发parent嵌套滑动。
                super.onInterceptTouchEvent(ev);
                mIsBeginDrag = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                final float y = getMotionEventY(ev, mActivePointerId);
                if (y == -1) {
                    return false;
                }
                int deltaY = (int) (mLastMotionY - y);
                mLastMotionY = y;
                if (Math.abs(deltaY) >= mTouchSlop) {
                    mIsBeginDrag = true;
                }
                if (mIsBeginDrag && dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                    mLastMotionY -= mScrollOffset[1];
                    deltaY -= mScrollConsumed[1];
                    ev.offsetLocation(0, mScrollConsumed[1]);
                    if (dispatchNestedScroll(0, 0, 0, deltaY, mScrollOffset)) {
                        mLastMotionY -= mScrollOffset[1];
                        ev.offsetLocation(0, mScrollOffset[1]);
                        Log.e(TAG,"onInterceptTouchEvent:dispatchNestedScroll true");
                    }
//                    NestedTwoLinearLayout parentView;
//                    NestedTwoLinearLayout grandpaView;
//                    switch (level){
//                        case 3:
//                            if (scrollRange>0||scrollRange<getHideHeight()){
//                                return true;
//                            }
//                            parentView  = (NestedTwoLinearLayout) getParent();
//                            if (parentView.scrollRange>0||parentView.scrollRange<parentView.getHideHeight()){
//                                return true;
//                            }
//                            grandpaView = (NestedTwoLinearLayout) parentView.getParent();
//                            if (grandpaView.scrollRange>0||grandpaView.scrollRange<grandpaView.getHideHeight()){
//                                return true;
//                            }
//                            return false;
//                        case 2:
//                            if (scrollRange>0||scrollRange<getHideHeight()){
//                                return true;
//                            }
//                            parentView  = (NestedTwoLinearLayout) getParent();
//                            if (parentView.scrollRange>0||parentView.scrollRange<parentView.getHideHeight()){
//                                return true;
//                            }
//                            return false;
//                        case 1:
//                            if (scrollRange>0||scrollRange<getHideHeight()){
//                                return true;
//                            }
//                            return false;
//                        default:
//                            return false;
//                    }
                } else {
                    return super.onInterceptTouchEvent(ev);
                }
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                stopNestedScroll();
                mActivePointerId = INVALID_POINTER;
                mIsBeginDrag = false;
                return super.onInterceptTouchEvent(ev);
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
