package cn.com.hakim.androidtraining;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cn.com.hakim.androidtraining.adapter.MyAdapter;

/**
 * Created by Administrator on 2016/8/1.
 */
public class RecycleActivity extends AppCompatActivity{
    RecyclerView mRecyclerView;
    CollapsingToolbarLayout collapsingToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_activity);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);//https://developer.android.com/training/appbar/up-action.html#declare-parent添加返回按钮。
        //需要在资源清单中设置parent属性。如下：
        /*
         <activity
         android:name=".RecycleActivity"
         android:parentActivityName="cn.com.hakim.androidtraining.MainActivity">
         <meta-data
         android:name="android.support.PARENT_ACTIVITY"
         android:value=".MainActivity"/>
         </activity>-
         */
        //回掉方法是onOptionsItemSelected，可重写该方法
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("测试标题");
        collapsingToolbar.setExpandedTitleColor(Color.RED);//展开时颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.GREEN);//收缩后的颜色
        initData();
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    List<String> data;
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new GridDividerDecorationOld(this));
        mRecyclerView.setAdapter(new MyAdapter(this,data));
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i=0;i<50;i++){
            data.add("hello"+i);
        }
    }
}
