package cn.com.hakim.viewpagertest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CTScrollerView extends ScrollView {
    public CTScrollerView(Context context) {
        super(context);
    }

    public CTScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CTScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final String TAG = "CTScrollerView";
    private float down_X = 0;
    private float down_Y = 0;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {//action_down消费，action_move消费往下
        super.onTouchEvent(ev);
        final int actionMasked = ev.getActionMasked();
        switch (actionMasked){
            case MotionEvent.ACTION_DOWN:
                down_X = ev.getX();
                down_Y = ev.getY();
                return false;
//                break;
            case MotionEvent.ACTION_MOVE:
                float move_x = ev.getX();
                float move_y = ev.getY();
                if (move_y-down_Y>0){//手指往下滑
                    Log.e(TAG,"child move 手指往下滑");
                    return false;
                }
                if (move_y-down_Y<0){//手指往上滑
                    Log.e(TAG,"child move 手指往上滑");
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                return super.onTouchEvent(ev);
        }
        return true;
    }
}
