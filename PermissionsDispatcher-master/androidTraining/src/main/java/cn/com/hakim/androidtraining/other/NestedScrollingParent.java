package cn.com.hakim.androidtraining.other;

import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface NestedScrollingParent {
    /**
     * 对子view发起的一个嵌套滑动操作做出响应，声明自己是否care该嵌套滑动（target：嵌套滑动的发起者，必须实现NestedScrollingChild接口，
     * 不一定是当前view的直接子view）
     *
     * 当当前view的子孙view发起了嵌套滑动（调用{@link ViewCompat#startNestedScroll(View, int)}方法）时，
     * 子孙view的上层view如果需要处理该嵌套滑动，在该方法中返回true。
     *
     * 如果该父view关心并一个嵌套滑动事件，当该嵌套滑动结束事，该父view的{@link #onStopNestedScroll(View)}会回调。
     * @param child 包含发起嵌套滑动事件的view(target)的直接子view
     * @param target 发起嵌套滑动事件的view，不一定是直接子view，但一定是在子view子集中
     * @param nestedScrollAxes Flags consisting of {@link ViewCompat#SCROLL_AXIS_HORIZONTAL},
     *                         {@link ViewCompat#SCROLL_AXIS_VERTICAL} or both
     * @return true if this ViewParent accepts the nested scroll operation
     */
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes);

    /**
     * 当viewParent成功care了一个嵌套事件后触发。
     * 该方法在{@link #onStartNestedScroll(View, View, int) onStartNestedScroll}返回true后调用。
     * 它提供了一个为当前viewParent初始化和配置该嵌套滑动的时机。
     *
     * 重写该方法记得调用父类的方法实现
     * @param child 包含target的直接子view
     * @param target 嵌套滑动的发起view
     * @param nestedScrollAxes Flags consisting of {@link ViewCompat#SCROLL_AXIS_HORIZONTAL},
     *                         {@link ViewCompat#SCROLL_AXIS_VERTICAL} or both嵌套滑动方向
     * @see #onStartNestedScroll(View, View, int)
     * @see #onStopNestedScroll(View)
     */
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes);

    /**
     * React to a nested scroll operation ending.
     *当嵌套滑动结束调用
     * 当嵌套滑动结束后执行清理工作，在调用了stopNestedScroll();后执行。
     * 通常会在MotionEvent分发流程中（dispatchTouchEvent、onInterceptTouchEvent、onTouchEvent）的action_cancel 、action_up中
     * 取消嵌套滑动(stopNestedScroll())。
     * 重写该方法必须调用父类的实现。
     * @param target View that initiated the nested scroll
     */
    public void onStopNestedScroll(View target);

    /**
     * 当当前viewParent的子view分发（startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);和dispatchNestedScroll(0, 0, 0, deltaY, mScrollOffset)）
     * 了一个嵌套滑动事件后触发。必须在{@link #onStartNestedScroll(View, View, int)}中返回了true时才能触发。
     *
     * 消费的和没消费的滑动距离都将上报给viewParent。
     * 一种实现会选择用消费的距离匹配或者逼近多个子view的滑动距离。
     * 举个栗子：在多级滑动和可拖拽的view中，没有消费的距离会被用于允许持续拖拽，例如：在一个竖直方向
     * 的抽屉布局中滑动列表，当可滑动的布局（list或者scrollView）滑动到它的边缘时，将开始拖拽动作。
     * @param target The descendent view controlling the nested scroll
     * @param dxConsumed Horizontal scroll distance in pixels already consumed by target
     * @param dyConsumed Vertical scroll distance in pixels already consumed by target
     * @param dxUnconsumed Horizontal scroll distance in pixels not consumed by target
     * @param dyUnconsumed Vertical scroll distance in pixels not consumed by target
     */
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed);

    /**
     *在target（发起嵌套滑动的子孙view，需要实现NestedScrollingChild接口）消费部分滑动距离之前调用。
     *
     * 当处于嵌套滑动机制中时，通常父view想要在子view消费之前获得消费机会。举个栗子：
     * 一个抽屉布局包裹一个可滑动列表，使用者可能想在list自己滑动之前将list滑入抽屉
     *
     *该方法在嵌套滑动子view调用{@link View#dispatchNestedPreScroll(int, int, int[], int[])}后回调。
     * 重写该方法需要在consumed[]数组中报告消费的滑动距离dx、dy。下标为0的是dx，参数不能为空，请用[0,0]
     * 初始化。
     * @param target View that initiated the nested scroll发起嵌套滑动的view
     * @param dx Horizontal scroll distance in pixels水平方向上的滑动距离
     * @param dy Vertical scroll distance in pixels竖直方向上的滑动距离
     * @param consumed Output. The horizontal and vertical scroll distance consumed by this parent水平和数值方向上的消费距离
     */
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed);

    /**
     *从嵌套滑动请求一个抛滑。
     * 这个方法表示一个嵌套滑动子view检测到了符合抛滑行为的条件，通常这意味着一个触摸滑动结束时，速度追踪器
     * 检测到在滑动方向上的速度大于等于{@link ViewConfiguration#getScaledMinimumFlingVelocity() minimum fling velocity}
     *
     * 如果一个嵌套滑动子view正常情况下抛滑到子view的内容边缘，它可以用这个方法委派抛滑行为给他的嵌套滑动父view
     * （实现了NestedScrollingParent的父view），该父view可以选择是否消费这个抛滑。
     * @param target View that initiated the nested scroll触发嵌套滑动的子view（需要实现NestedScrollingChild接口）
     * @param velocityX Horizontal velocity in pixels per second水平方向上的滑动速度，单位像素/秒
     * @param velocityY Vertical velocity in pixels per second
     * @param consumed true if the child consumed the fling, false otherwise如何child消费了抛滑则为true
     * @return true if this parent consumed or otherwise reacted to the fling如果返回true则为消费子view的抛滑
     */
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed);

    /**
     *在嵌套滑动子view抛滑之前调用
     * <p>This method siginfies that a nested scrolling child has detected a fling with the given
     * velocity along each axis. Generally this means that a touch scroll has ended with a
     * {@link VelocityTracker velocity} in the direction of scrolling that meets or exceeds
     * the {@link ViewConfiguration#getScaledMinimumFlingVelocity() minimum fling velocity}
     * along a scrollable axis.</p>
     * <p>If a nested scrolling parent is consuming motion as part of a
     * {@link #onNestedPreScroll(View, int, int, int[]) pre-scroll}, it may be appropriate for
     * it to also consume the pre-fling to complete that same motion. By returning
     * <code>true</code> from this method, the parent indicates that the child should not
     * fling its own internal content as well.</p>
     *当返回true时，嵌套滑动子view将不能抛滑自身。
     * @param target View that initiated the nested scroll
     * @param velocityX Horizontal velocity in pixels per second
     * @param velocityY Vertical velocity in pixels per second
     * @return true if this parent consumed the fling ahead of the target view
     */
    public boolean onNestedPreFling(View target, float velocityX, float velocityY);

    /**
     * Return the current axes of nested scrolling for this NestedScrollingParent.
     *返回当前嵌套滑动父view关注的嵌套滑动的坐标轴方向包括：
     * ViewCompat#SCROLL_AXIS_HORIZONTAL
     * ViewCompat#SCROLL_AXIS_VERTICAL
     * ViewCompat#SCROLL_AXIS_NONE
     * <p>A NestedScrollingParent returning something other than {@link ViewCompat#SCROLL_AXIS_NONE}
     * is currently acting as a nested scrolling parent for one or more descendant views in
     * the hierarchy.</p>
     *
     * @return Flags indicating the current axes of nested scrolling
     * @see ViewCompat#SCROLL_AXIS_HORIZONTAL
     * @see ViewCompat#SCROLL_AXIS_VERTICAL
     * @see ViewCompat#SCROLL_AXIS_NONE
     */
    public int getNestedScrollAxes();
}
