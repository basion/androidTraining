package cn.com.hakim.androidtraining;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/12/12.
 */

public class ElemaActivity extends AppCompatActivity {
    private static final String TAG = "ElemaActivity";
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    LinearLayout buyLayout;
    AppBarLayout  mAppBarLayout;
    NestedScrollView mNestedScrollView;
    LinearLayout scrollLinearLayout;
    LinearLayout buyOutLayout;
    boolean canScroll = false;
    private int status = 0;//1:展开；2：折叠；0:空
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elema);
        buyOutLayout = (LinearLayout) findViewById(R.id.linear_buy);
        scrollLinearLayout = (LinearLayout) findViewById(R.id.layout_food_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        buyLayout = (LinearLayout) findViewById(R.id.layout_buy);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbarLayout);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                int top = mAppBarLayout.getTop();
//                int bottom = mAppBarLayout.getBottom();
//                Log.e(TAG,"top="+top+"|bottom = "+bottom);
//                Log.e(TAG,"bottom-top="+(bottom-top));
//                if (status == 1){
//                    scrollLinearLayout.scrollTo(0,scrollY);
//                    Log.e(TAG,"scrollX = "+scrollX+";oldScrollX = "+oldScrollX);
//                    Log.e(TAG,"scrollY = "+scrollY+";oldScrollY = "+oldScrollY);
////                    ViewCompat.offsetTopAndBottom(buyOutLayout,scrollY);
//                }else if (status == 2){
//                    scrollLinearLayout.scrollTo(0,scrollY);
//                    Log.e(TAG,"scrollX = "+scrollX+";oldScrollX = "+oldScrollX);
//                    Log.e(TAG,"scrollY = "+scrollY+";oldScrollY = "+oldScrollY);
////                    ViewCompat.offsetTopAndBottom(buyOutLayout,scrollY);
//                }
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                Log.e(TAG,"verticalOffset ="+verticalOffset);//0-528
                int scrollRange = appBarLayout.getTotalScrollRange();
//                Log.e(TAG,"scrollRange = "+scrollRange);

                if (verticalOffset == 0) {
                    status = 1;
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    status = 2;
                } else {
                    status = 0;
                }
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //透明状态栏(手机头部通知栏)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏（手机底部虚拟条）
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
//        ViewCompat.startNestedScroll()
    }
}
