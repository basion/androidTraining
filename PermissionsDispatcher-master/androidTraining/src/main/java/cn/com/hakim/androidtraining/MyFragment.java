package cn.com.hakim.androidtraining;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class MyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView view = new RecyclerView(container.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        container.addView(view);
        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(RecyclerView view) {
        view.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<Integer> datas = new LinkedList<>();
        datas.add(R.drawable.img_1);
        datas.add(R.drawable.img_2);
        datas.add(R.drawable.img_3);
        datas.add(R.drawable.img_4);
        MyAdapter adapter = new MyAdapter(getContext(),datas);
        view.setAdapter(adapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Integer> imgList;
        Context mContext;

        public MyAdapter(Context context, List<Integer> data) {
            this.imgList = data;
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View content = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card,parent,false);
            return new ViewHolder(content);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
           int resId = imgList.get(position);
            holder.img.setImageResource(resId);
            holder.mTextView.setText("name"+position);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"click"+position,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return imgList == null?0:imgList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView mTextView;
            View mView;
            public ViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                img = (ImageView) itemView.findViewById(R.id.img_head);
                mTextView = (TextView) itemView.findViewById(R.id.tv_name);
            }
        }
    }
}
