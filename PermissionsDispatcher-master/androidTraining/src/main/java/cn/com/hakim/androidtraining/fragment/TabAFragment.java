package cn.com.hakim.androidtraining.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.hakim.androidtraining.GridDividerDecorationOld;
import cn.com.hakim.androidtraining.MyAdapter;
import cn.com.hakim.androidtraining.R;

/**
 * Created by Administrator on 2016/8/12.
 */
public class TabAFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_a_tab,container,false);
        initView(layout);
        initData();
        return layout;
    }

    private void initData() {

    }

    RecyclerView mRecyclerView;
    private void initView(View layout) {
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycleView);
        List<String> data = new ArrayList<>();
        for (int i=0;i<50;i++){
            data.add("你"+i+"大爷");
        }
        mRecyclerView.setAdapter(new MyAdapter(getContext(),data));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new GridDividerDecorationOld(getContext()));
    }
}
