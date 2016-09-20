package cn.com.hakim.androidtraining.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.hakim.androidtraining.R;
import cn.com.hakim.androidtraining.ZhiHuActivity;

/**
 * Created by Administrator on 2016/8/12.
 */
public class TabBFragment extends Fragment {
    private View content;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_b_tab,container,false);
        content = layout;
        initView(layout);
        initData();
        return layout;
    }

    private void initData() {

    }

    private void initView(View layout) {

    }

}
