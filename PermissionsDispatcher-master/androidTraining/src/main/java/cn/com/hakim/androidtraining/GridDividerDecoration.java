package cn.com.hakim.androidtraining;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Administrator on 2016/8/2.
 */
public class GridDividerDecoration extends RecyclerView.ItemDecoration {
    private static final int decorationHeight = 40;

    /**
     * 在绘制item之前绘制
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDrawOver(c, parent, state);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_separator,null);
        Bitmap bitmap = layout.getDrawingCache();//NOTICE bitmap 极其容易被回收
        Paint paint = new Paint();
        c.drawBitmap(bitmap,0,0,paint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        int pxSize = TypedValue.complexToDimensionPixelSize(decorationHeight,parent.getResources().getDisplayMetrics());
        outRect.set(pxSize, pxSize, pxSize, pxSize);
    }

    /**
     * 在绘制item之后绘制
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }
}
