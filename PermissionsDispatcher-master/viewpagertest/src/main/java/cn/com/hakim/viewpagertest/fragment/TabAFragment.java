package cn.com.hakim.viewpagertest.fragment;

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

import cn.com.hakim.viewpagertest.R;


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

    private void initView(View layout) {
    }
    private void moveView(){

    }
    private static final String TAG = "TabAFragment";

    boolean hasSetBehavior ;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
