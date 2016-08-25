package cn.com.hakim.androidtraining.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.hakim.androidtraining.GridDividerDecorationOld;
import cn.com.hakim.androidtraining.MyAdapter;
import cn.com.hakim.androidtraining.R;
import cn.com.hakim.androidtraining.ZhiHuActivity;
import cn.com.hakim.androidtraining.behavior.MyBottomBehavior;

/**
 * Created by Administrator on 2016/8/12.
 */
public class TabAFragment extends Fragment {
    private View content;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_a_tab,container,false);
        content = layout;
        initView(layout);
        initData();
        Log.d(TAG,"onCreateView");
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
        mMyBottomBehavior = MyBottomBehavior.from(layout.findViewById(R.id.fb_a));
        if (getUserVisibleHint()&&!hasSetBehavior){
            ZhiHuActivity activity = (ZhiHuActivity) getActivity();
            activity.setZhihuBehavior(mMyBottomBehavior);
        }
    }
    public MyBottomBehavior mMyBottomBehavior;

    private static final String TAG = "TabAFragment";

    boolean hasSetBehavior ;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG,"setUserVisibleHint");
        if (isVisibleToUser&&content!=null){
            ZhiHuActivity activity = (ZhiHuActivity) getActivity();
            activity.setZhihuBehavior(mMyBottomBehavior);
            hasSetBehavior = true;
        }else{
            hasSetBehavior = false;
        }
    }
}
