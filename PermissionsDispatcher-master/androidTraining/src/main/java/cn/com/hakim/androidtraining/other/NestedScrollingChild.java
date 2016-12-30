package cn.com.hakim.androidtraining.other;

import android.support.v4.view.*;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface NestedScrollingChild {
    /**
     *是否允许当前view嵌套滑动
     *
     * 设置为true当前view将被允许和嵌套滑动父view（实现了NestedScrollingParent的父view）协同触发嵌套滑动操作
     * 如果当前view没有实现nested scrolling ，则没有任何作用。当正处于嵌套滑动时禁用嵌套滑动，会和{@link #stopNestedScroll() stopping}
     * 方法行为一致。
     * @param enabled true to enable nested scrolling, false to disable
     * @see #isNestedScrollingEnabled()
     */
    public void setNestedScrollingEnabled(boolean enabled);

    /**
     * Returns true if nested scrolling is enabled for this view.
     *如果当前view允许嵌套滑动则返回true
     *
     * <p>If nested scrolling is enabled and this View class implementation supports it,
     * this view will act as a nested scrolling child view when applicable, forwarding data
     * about the scroll operation in progress to a compatible and cooperating nested scrolling
     * parent.</p>
     *
     * @return true if nested scrolling is enabled
     *
     * @see #setNestedScrollingEnabled(boolean)
     */
    public boolean isNestedScrollingEnabled();

    /**
     * Begin a nestable scroll operation along the given axes.
     *沿着给定的方向开始一个嵌套滑动操作。
     * <p>A view starting a nested scroll promises to abide by the following contract:</p>
     *一个view开始嵌套滑动需要遵守以下规范：
     *view会调用startNestedScroll方法来初始化一个滑动操作。通常可以在{@link MotionEvent#ACTION_DOWN}
     * 调用来开启一个嵌套滑动。
     * 在触摸滑动模式下，嵌套滑动将以和{@link ViewParent#requestDisallowInterceptTouchEvent(boolean)}
     * 同样的方式自动终止。在嵌套滑动结束时，必须显示的调用{@link #stopNestedScroll()}以表明嵌套滑动终止。
     *
     * <p>The view will call startNestedScroll upon initiating a scroll operation. In the case
     * of a touch scroll this corresponds to the initial {@link MotionEvent#ACTION_DOWN}.
     * In the case of touch scrolling the nested scroll will be terminated automatically in
     * the same manner as {@link ViewParent#requestDisallowInterceptTouchEvent(boolean)}.
     * In the event of programmatic scrolling the caller must explicitly call
     * {@link #stopNestedScroll()} to indicate the end of the nested scroll.</p>
     *如果该方法返回true，一个协同滑动的父view被找到。
     * 返回false，调用该方法的view将忽略。。
     * 当一个嵌套滑动正在处理时调用startNestedScroll将返回true。
     * <p>If <code>startNestedScroll</code> returns true, a cooperative parent was found.
     * If it returns false the caller may ignore the rest of this contract until the next scroll.
     * Calling startNestedScroll while a nested scroll is already in progress will return true.</p>
     *在每次新增加的滑动，调用者要调用{@link #dispatchNestedPreScroll(int, int, int[], int[]) dispatchNestedPreScroll}。
     * 一旦它计算出要求的滑动增量
     * 如果返回true，嵌套滑动父view至少部分消费了滑动，调用者（发起嵌套view者，需要实现NestedScrollingChild）
     * 需要调整它滑动的距离。
     * <p>At each incremental step of the scroll the caller should invoke
     * {@link #dispatchNestedPreScroll(int, int, int[], int[]) dispatchNestedPreScroll}
     * once it has calculated the requested scrolling delta. If it returns true the nested scrolling
     * parent at least partially consumed the scroll and the caller should adjust the amount it
     * scrolls by.</p>
     *在提供滑动增量的剩余部分后，调用者（NestedScrollingChild）要调用
     * {@link #dispatchNestedScroll(int, int, int, int, int[]) dispatchNestedScroll}方法，并把消费的滑动增量和
     * 未消费的滑动增量一并传入。嵌套滑动父view将回调{@link android.support.v4.view.NestedScrollingParent#onNestedScroll(View, int, int, int, int)}.
     * 处理这些值。
     * <p>After applying the remainder of the scroll delta the caller should invoke
     * {@link #dispatchNestedScroll(int, int, int, int, int[]) dispatchNestedScroll}, passing
     * both the delta consumed and the delta unconsumed. A nested scrolling parent may treat
     * these values differently. See
     * {@link android.support.v4.view.NestedScrollingParent#onNestedScroll(View, int, int, int, int)}.
     * </p>
     *
     * @param axes Flags consisting of a combination of {@link ViewCompat#SCROLL_AXIS_HORIZONTAL}
     *             and/or {@link ViewCompat#SCROLL_AXIS_VERTICAL}.
     * @return true if a cooperative parent was found and nested scrolling has been enabled for
     *         the current gesture.
     *
     * @see #stopNestedScroll()
     * @see #dispatchNestedPreScroll(int, int, int[], int[])
     * @see #dispatchNestedScroll(int, int, int, int, int[])
     */
    public boolean startNestedScroll(int axes);

    /**
     * Stop a nested scroll in progress.
     *停止嵌套滑动
     * <p>Calling this method when a nested scroll is not currently in progress is harmless.</p>
     *
     * @see #startNestedScroll(int)
     */
    public void stopNestedScroll();

    /**
     * Returns true if this view has a nested scrolling parent.
     *如果有嵌套滑动父view，则返回true
     * <p>The presence of a nested scrolling parent indicates that this view has initiated
     * a nested scroll and it was accepted by an ancestor view further up the view hierarchy.</p>
     *
     * @return whether this view has a nested scrolling parent
     */
    public boolean hasNestedScrollingParent();

    /**
     * Dispatch one step of a nested scroll in progress.
     *在程序中分发一个嵌套滑动的步骤之一
     *
     * 支持嵌套滑动的view实现这个方法应当调用该方法向当前嵌套滑动的父view上报滑动信息。
     * 如果嵌套滑动当前未发生或者当前view通过{@link #isNestedScrollingEnabled() enabled}方法设置为false
     * （未开启嵌套滑动），则不起作用。
     *
     * <p>Implementations of views that support nested scrolling should call this to report
     * info about a scroll in progress to the current nested scrolling parent. If a nested scroll
     * is not currently in progress or nested scrolling is not
     * {@link #isNestedScrollingEnabled() enabled} for this view this method does nothing.</p>
     *
     * 兼容view的实现也要在自己消费滑动事件前调用{@link #dispatchNestedPreScroll(int, int, int[], int[]) dispatchNestedPreScroll}
     *
     * <p>Compatible View implementations should also call
     * {@link #dispatchNestedPreScroll(int, int, int[], int[]) dispatchNestedPreScroll} before
     * consuming a component of the scroll event themselves.</p>
     *
     * 窗体补偿：可选参数。如果不为null，返回时，将包含此视图从该操作之前到完成后的本地视图坐标的偏移量.
     *视图实现可以使用此来调整期望输入坐标跟踪
     * @param dxConsumed Horizontal distance in pixels consumed by this view during this scroll step
     * @param dyConsumed Vertical distance in pixels consumed by this view during this scroll step
     * @param dxUnconsumed Horizontal scroll distance in pixels not consumed by this view
     * @param dyUnconsumed Horizontal scroll distance in pixels not consumed by this view
     * @param offsetInWindow Optional. If not null, on return this will contain the offset
     *                       in local view coordinates of this view from before this operation
     *                       to after it completes. View implementations may use this to adjust
     *                       expected input coordinate tracking.
     * @return true if the event was dispatched, false if it could not be dispatched.
     * @see #dispatchNestedPreScroll(int, int, int[], int[])
     */
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow);

    /**
     * Dispatch one step of a nested scroll in progress before this view consumes any portion of it.
     *在该视图消耗嵌套滑动的任何部分之前，分发执行一个嵌套滑动的步骤之一（分发给父view）。
     *Nested pre-scroll事件和nested scroll事件的关系正如touch intercept 和touch的关系一样
     *（touch intercept：viewParent的onTouchIntercept方法；touch：onTouch）。因此，嵌套子view开始嵌套
     * 滑动之前，将先调用父view的onNestedPreScroll()方法。
     * dispatchNestedPreScroll方法为父view提供了一个在嵌套滑动中优先于子view消费部分或者全部滑动。
     * <p>Nested pre-scroll events are to nested scroll events what touch intercept is to touch.
     * <code>dispatchNestedPreScroll</code> offers an opportunity for the parent view in a nested
     * scrolling operation to consume some or all of the scroll operation before the child view
     * consumes it.</p>
     *
     * @param dx Horizontal scroll distance in pixels
     * @param dy Vertical scroll distance in pixels
     * @param consumed Output. If not null, consumed[0] will contain the consumed component of dx
     *                 and consumed[1] the consumed dy.
     * @param offsetInWindow Optional. If not null, on return this will contain the offset
     *                       in local view coordinates of this view from before this operation
     *                       to after it completes. View implementations may use this to adjust
     *                       expected input coordinate tracking.
     * @return true if the parent consumed some or all of the scroll delta
     * @see #dispatchNestedScroll(int, int, int, int, int[])
     */
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow);

    /**
     * Dispatch a fling to a nested scrolling parent.
     *向嵌套父view分发一个抛滑
     *
     * 这个方法应该被用于指示嵌套滑动子view已经检测到满足抛滑的条件。
     * <p>This method should be used to indicate that a nested scrolling child has detected
     * suitable conditions for a fling. Generally this means that a touch scroll has ended with a
     * {@link VelocityTracker velocity} in the direction of scrolling that meets or exceeds
     * the {@link ViewConfiguration#getScaledMinimumFlingVelocity() minimum fling velocity}
     * along a scrollable axis.</p>
     *如果一个嵌套抛滑到来，通常情况下嵌套滑动子view将抛滑，除非它处于自己内容的边缘。
     * 嵌套子view可以用这个方法去委派抛滑给嵌套滑动父view，父view会选择消费这个抛滑或者就这么静静的看着
     * 子view抛滑。
     * <p>If a nested scrolling child view would normally fling but it is at the edge of
     * its own content, it can use this method to delegate the fling to its nested scrolling
     * parent instead. The parent may optionally consume the fling or observe a child fling.</p>
     *返回true代表父view消费了抛滑
     * @param velocityX Horizontal fling velocity in pixels per second
     * @param velocityY Vertical fling velocity in pixels per second
     * @param consumed true if the child consumed the fling, false otherwise
     * @return true if the nested scrolling parent consumed or otherwise reacted to the fling
     */
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed);

    /**
     * Dispatch a fling to a nested scrolling parent before it is processed by this view.
     *在自己处理抛滑之前分发给嵌套滑动父view。
     * Nested pre-fling事件和nested fling事件的关机如同touch intercept和touch一样，也与nested pre-scroll
     * 和nested scroll的关系一样。
     * dispatchNestedPreFling在嵌套滑动中为嵌套滑动父view提供了一个优先于嵌套子view的机会去完全消费抛滑事件
     *
     * 返回true，嵌套滑动父view消费抛滑并且子view不能再消费了。
     * <p>Nested pre-fling events are to nested fling events what touch intercept is to touch
     * and what nested pre-scroll is to nested scroll. <code>dispatchNestedPreFling</code>
     * offsets an opportunity for the parent view in a nested fling to fully consume the fling
     * before the child view consumes it. If this method returns <code>true</code>, a nested
     * parent view consumed the fling and this view should not scroll as a result.</p>
     *
     * 为了更好的使用体验，在一个嵌套滑动链中，一次仅有一个view应该消费抛滑事件。
     * 如果一个父view消费了抛滑这个方法返回了false，当前嵌套滑动子view（发起嵌套抛滑的view）将认为以下
     * 两种情况发生：
     * 1：
     * <p>For a better user experience, only one view in a nested scrolling chain should consume
     * the fling at a time. If a parent view consumed the fling this method will return false.
     * Custom view implementations should account for this in two ways:</p>
     *
     * <ul>
     *     <li>If a custom view is paged and needs to settle to a fixed page-point, do not
     *     call <code>dispatchNestedPreFling</code>; consume the fling and settle to a valid
     *     position regardless.</li>
     *     <li>If a nested parent does consume the fling, this view should not scroll at all,
     *     even to settle back to a valid idle position.</li>
     * </ul>
     *
     * <p>Views should also not offer fling velocities to nested parent views along an axis
     * where scrolling is not currently supported; a {@link android.widget.ScrollView ScrollView}
     * should not offer a horizontal fling velocity to its parents since scrolling along that
     * axis is not permitted and carrying velocity along that motion does not make sense.</p>
     *
     * @param velocityX Horizontal fling velocity in pixels per second
     * @param velocityY Vertical fling velocity in pixels per second
     * @return true if a nested scrolling parent consumed the fling
     */
    public boolean dispatchNestedPreFling(float velocityX, float velocityY);
}
