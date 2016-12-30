package cn.com.hakim.androidtraining.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.com.hakim.androidtraining.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<String> data ;
    Context mContext;
    public MyAdapter(Context context, List<String> data) {
        this.data = data;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String value = data.get(position);
        holder.tv1.setText(value);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv1;
        View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tv1 = (TextView) itemView.findViewById(R.id.tv_1);
            this.mView = itemView;
        }
    }
}
