package cn.com.hakim.androidtraining;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.com.hakim.androidtraining.behavior.MyBottomBehavior;
import cn.com.hakim.androidtraining.fragment.TabAFragment;
import cn.com.hakim.androidtraining.fragment.TabBFragment;
import cn.com.hakim.androidtraining.fragment.TabCFragment;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ZhiHuActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        initView();
        initData();
    }

    private void initData() {

    }
    MyHandler mHandler;
    BottomSheetBehavior mBottomSheetBehavior;
    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_main);
        mViewPager = (ViewPager) findViewById(R.id.pager_main);
        setupTablayout();
        View view  = findViewById(R.id.layout_parent);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        mHandler = new MyHandler(this);
        Message message = mHandler.obtainMessage();
        message.what = MSG_SWITCH_TAB;
        message.obj = 2;
        mHandler.sendMessageDelayed(message,3000);
    }

    private MyBottomBehavior.OnStateChangedListener onStateChangedListener = new MyBottomBehavior.OnStateChangedListener() {
        @Override
        public void onChanged(boolean isShow) {
            mBottomSheetBehavior.setState(isShow ? BottomSheetBehavior.STATE_EXPANDED : BottomSheetBehavior.STATE_COLLAPSED);
            int state = mBottomSheetBehavior.getState();
            Log.e("setBottomBarState","==state = "+state);
        }
    };
    public void setZhihuBehavior(MyBottomBehavior behavior){
        if (behavior!=null){
            behavior.setOnStateChangedListener(onStateChangedListener);
        }
    }
    public void setBottomBarState(boolean isShow){
        mBottomSheetBehavior.setState(isShow ? BottomSheetBehavior.STATE_EXPANDED : BottomSheetBehavior.STATE_COLLAPSED);
    }
    TabAFragment aFragment;
    TabBFragment bFragment;
    TabCFragment cFragment;
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
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("onTabSelected","========");
                if (mBottomSheetBehavior!=null){
                    int state =  mBottomSheetBehavior.getState();
                    int visibleState = mTabLayout.getVisibility();
                    Log.e("onTabSelected","visibleState ="+visibleState);
                    Log.e("onTabSelected","state = "+state);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.tab_main));
        mBottomSheetBehavior.setHideable(false);
    }

    static class PagerAdapter extends FragmentPagerAdapter {
        private Context mContext;
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        private final  List<Integer> mIconList = new ArrayList<>();
        public PagerAdapter(FragmentManager fm,Context context) {
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
        public View getTabView(int position) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.layout_custom_tab, null);
            TextView tv = (TextView) v.findViewById(R.id.tv_tab);
            tv.setText(getPageTitle(position));
            ImageView img = (ImageView) v.findViewById(R.id.img_tab);
            //img.setImageResource(imageResId[position]);
            img.setImageResource(mIconList.get(position));
            return v;
        }
    }

    private boolean initialize = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!initialize) {
            initialize = true;
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
    private static final int MSG_SWITCH_TAB = 0x11;
    private static final int MSG_GET_STATE = 0x12;
    static class MyHandler extends Handler{
        WeakReference<ZhiHuActivity> mReference;
        public MyHandler(ZhiHuActivity activity) {
            if (activity!=null){
                mReference = new WeakReference<ZhiHuActivity>(activity);
            }
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msg_type = msg.what;
            if (msg_type == MSG_SWITCH_TAB){
                int position = (int) msg.obj;
                ZhiHuActivity activity = mReference.get();
                if (activity!=null){
                    activity.setTab(position);
                    Message message = obtainMessage();
                    message.what = MSG_GET_STATE;
                    sendMessageDelayed(message,1000);
                }
            }else if (msg_type == MSG_GET_STATE){
                ZhiHuActivity activity = mReference.get();
                if (activity!=null){
                    activity.getState();
                }
            }
        }
    }
    protected void setTab(int position){
        mViewPager.setCurrentItem(position);
    }
    protected void getState(){
       int state = mBottomSheetBehavior.getState();
        Log.e("getState","state = "+state);
    }
}
