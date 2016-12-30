package cn.com.hakim.androidtraining.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.com.hakim.androidtraining.R;

/**
 * Created by Administrator on 2016/12/16.
 */

public class CustomLinearLayout extends LinearLayout implements NestedScrollingParent{
    private static final String TAG = "CustomLinearLayout";
    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LinearLayout getTitleLayout() {
        return titleLayout;
    }

    public NestedScrollView getContentLayout() {
        return contentLayout;
    }

    LinearLayout titleLayout;
    NestedScrollView contentLayout;
    private int mTitleHeight;
    private int scrollRange;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleLayout = (LinearLayout) findViewById(R.id.linear_title);
        contentLayout = (NestedScrollView) findViewById(R.id.nestedScrollView);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTitleHeight = titleLayout.getMeasuredHeight();//这里才能取到子view测绘后高度,在onFinishInflate中不能获取到
        scrollRange = mTitleHeight;//
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e(TAG,"onNestedPreFling:velocityX="+velocityX+"**velocityY="+velocityY);
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG,"onNestedScroll:dxConsumed="+dxConsumed+"**dyConsumed="+dyConsumed+"**dxUnconsumed="+dxUnconsumed+"**dyUnconsumed="+dyUnconsumed);
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {//该方法，一定要按照自己的需求返回true，该方法决定了当前控件是否能接收到其内部View(非并非是直接子View)滑动时的参数；假设你只涉及到纵向滑动，这里可以根据nestedScrollAxes这个参数，进行纵向判断。
        Log.e(TAG,"onStartNestedScroll:"+"nestedScrollAxes="+nestedScrollAxes+"\ntarget="+target.toString()+"\nchild="+child.toString());
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onStopNestedScroll(View child) {
        Log.e(TAG,"onStopNestedScroll");
        super.onStopNestedScroll(child);
    }

    private boolean isUpScroll = false;
    private boolean isDownScroll = false;
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {//该方法的会传入内部View移动的dx,dy，如果你需要消耗一定的dx,dy，就通过最后一个参数consumed进行指定，例如我要消耗一半的dy，就可以写consumed[1]=dy/2
        Log.e(TAG,"onNestedPreScroll:"+"dx="+dx+"**dy="+dy+"**consumed.size="+consumed.length);
        //super.onNestedPreScroll(target, dx, dy, consumed);
        //super调用的是dispatchNestedPreScroll(dx, dy, consumed, null);方法，该方法将传递嵌套方法到当前view的上层view。所以可以根据情况分发或者不分发滑动
        //需要处理滑动的情况:往上滑，滑动到titleLayout刚好滑出屏幕；往下滑，滑动到titleLayout刚好显示完全。
        //在需要滑动的情况下，使用scrollBy滑动自身控件
        //dy<0为往下滑动 , dy>0往上滑

        if (dy>0){//上滑动
            if (scrollRange>0){
                scrollBy(0,dy);
                scrollRange -= dy;
                consumed[1] = dy;
            }else {

            }
        }else {//下滑动
            if (scrollRange<mTitleHeight){
                scrollBy(0,dy);
                scrollRange -= dy;
                consumed[1] = dy;
            }
        }

//        boolean hiddenTop = dy > 0 && getScrollY() < mTitleHeight;
//        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);//负数检查向上是否能滑动，正数检查是否能向下滑动
//
//        if (hiddenTop || showTop)
//        {
//            scrollBy(0, dy);
//            consumed[1] = dy;
//        }
    }
    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0)
        {
            y = 0;
        }
        if (y > mTitleHeight)
        {
            y = mTitleHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //需要在测绘高度时设置好view高度（高度超出屏幕一个titleView的高度）否则scrollBy后，view会整体向上移动
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG,"onMeasure----------------------------------");
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = contentLayout.getLayoutParams();//必须让content的高度测绘设置为父view的高度
        params.height = getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), contentLayout.getMeasuredHeight() +titleLayout.getMeasuredHeight());//必须调用该方法，否则会触发IllegalStateException
    }
}
