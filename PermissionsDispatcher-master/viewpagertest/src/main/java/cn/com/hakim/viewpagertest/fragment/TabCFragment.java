package cn.com.hakim.viewpagertest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.com.hakim.viewpagertest.R;
import cn.com.hakim.viewpagertest.activity.ViewPagerActivity;


/**
 * Created by Administrator on 2016/8/12.
 */
public class TabCFragment extends Fragment {
    View content;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_c_tab,container,false);
        content = layout;
        initView();
        return layout;
    }
    Button scrollBtn;
    private void initView() {
        scrollBtn = (Button) content.findViewById(R.id.bt_scroll);
        scrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewPagerActivity)getActivity()).scroll();
            }
        });
    }
}
