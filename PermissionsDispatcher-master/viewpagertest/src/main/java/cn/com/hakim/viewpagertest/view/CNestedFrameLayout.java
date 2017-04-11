package cn.com.hakim.viewpagertest.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/4/11.
 */

public class CNestedFrameLayout extends FrameLayout implements NestedScrollingParent {
    public CNestedFrameLayout(@NonNull Context context) {
        super(context);
    }

    public CNestedFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CNestedFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
