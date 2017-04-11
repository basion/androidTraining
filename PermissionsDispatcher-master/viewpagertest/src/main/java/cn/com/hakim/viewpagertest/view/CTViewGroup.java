package cn.com.hakim.viewpagertest.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CTViewGroup extends LinearLayout {
    public CTViewGroup(@NonNull Context context) {
        super(context);
    }

    public CTViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CTViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private static final String TAG = "CTViewGroup";
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Log.e(TAG,"onTouch from parent");
        return true;
    }

    public void dispatchTouchDown(MotionEvent event){
        dispatchGenericMotionEvent(event);
        dispatchHoverEvent(event);
        dispatchTouchEvent(event);
    }
}
