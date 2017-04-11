package cn.com.hakim.viewpagertest.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.com.hakim.viewpagertest.R;
import cn.com.hakim.viewpagertest.fragment.TabAFragment;
import cn.com.hakim.viewpagertest.fragment.TabBFragment;
import cn.com.hakim.viewpagertest.fragment.TabCFragment;

public class ViewPagerActivity extends AppCompatActivity {
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
    }
    public void scroll(){
        mViewPager.scrollBy(100,0);
    }
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.pager_main);
        setupTablayout();
    }
    private static final String TAG = "ViewPagerActivity";
    TabAFragment aFragment;
    TabBFragment bFragment;
    TabCFragment cFragment;
    int index = 0;

    private int mActivePointerId = -1;
    private void setupTablayout() {
        if (aFragment == null){
            aFragment = new TabAFragment();
        }
        if (bFragment == null){
            bFragment = new TabBFragment();
        }
        if (cFragment == null){
            cFragment = new TabCFragment();
        }
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),this);
        adapter.addFragment(aFragment,"1",R.drawable.icon_tab_a);
        adapter.addFragment(bFragment,"2",R.drawable.icon_tab_b);
        adapter.addFragment(cFragment,"3",R.drawable.icon_tab_c);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            boolean handled = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {//在view的onTouch之前执行
                Log.e(TAG,"OnTouchListener.onTouch");
                if (index == 2){
                    float down_X = 0;// = event.getX();
                    float down_Y = 0;// = event.getY();
                    switch (event.getActionMasked()){
                        case (MotionEvent.ACTION_DOWN):
                            mActivePointerId = MotionEventCompat.getPointerId(event, 0);
//                            final float initialDownY = getMotionEventY(event, mActivePointerId);
                            down_X = event.getX();
                            down_Y = event.getY();
                            break;
                        case (MotionEvent.ACTION_MOVE):
                            if (mActivePointerId == -1) {
                                return false;
                            }
                            float m_X = event.getX();
                            float m_Y = event.getY();
                            float delta_X = m_X - down_X;//往左滑动该值小于0
                            float delta_Y = m_Y - down_Y;
                            Log.e(TAG,"delta_X="+delta_X);
                            if (delta_X<0&&Math.abs(delta_X) > Math.abs(delta_Y)){//只处理横向向左滑动
                                mViewPager.scrollBy((int)delta_X,0);
                                handled = true;
                            }
                            break;
                        case (MotionEvent.ACTION_UP):
                            break;
                    }
                }
                return handled;
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG,"onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                Log.e(TAG,"onPageSelected||position="+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }
    static class PagerAdapter extends FragmentPagerAdapter {
        private Context mContext;
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        private final  List<Integer> mIconList = new ArrayList<>();
        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.mContext = context;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }
        public void addFragment(Fragment fragment, String title,Integer iconId) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
            mIconList.add(iconId);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
//        public View getTabView(int position) {
//            View v = LayoutInflater.from(mContext).inflate(R.layout.layout_custom_tab, null);
//            TextView tv = (TextView) v.findViewById(R.id.tv_tab);
//            tv.setText(getPageTitle(position));
//            ImageView img = (ImageView) v.findViewById(R.id.img_tab);
//            //img.setImageResource(imageResId[position]);
//            img.setImageResource(mIconList.get(position));
//            return v;
//        }

    }
}
