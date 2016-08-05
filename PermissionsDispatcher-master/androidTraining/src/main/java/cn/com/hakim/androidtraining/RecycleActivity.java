package cn.com.hakim.androidtraining;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("测试标题");
        collapsingToolbar.setExpandedTitleColor(Color.RED);//展开时颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.GREEN);//收缩后的颜色
        initData();
        initView();
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
