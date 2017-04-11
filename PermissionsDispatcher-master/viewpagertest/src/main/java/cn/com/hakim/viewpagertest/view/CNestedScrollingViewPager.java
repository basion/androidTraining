package cn.com.hakim.viewpagertest.view;

import android.content.Context;
import android.support.v4.view.*;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/4/11.
 */

public class CNestedScrollingViewPager extends android.support.v4.view.ViewPager implements NestedScrollingChild{
    public CNestedScrollingViewPager(Context context) {
        super(context);
    }

    public CNestedScrollingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
