package cn.com.hakim.androidtraining.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cn.com.hakim.androidtraining.R;
import cn.com.hakim.androidtraining.model.Ball;

/**
 * Created by Administrator on 2016/11/4.
 */

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {
    public List<List<Ball>> getAddData() {
        return addData;
    }

    public void setAddData(List<List<Ball>> addData ,boolean add) {
        if (add){
            for (List<Ball> balls:addData){
                if (balls!=null&&balls.size()!=0){
                    this.addData.add(balls);
                }
            }
        }else {
            this.addData = addData;
        }
        notifyDataSetChanged();
    }

    private List<List<Ball>> addData;

    public AddAdapter() {
        addData = new LinkedList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add,parent,false);
        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<Ball> items = addData.get(position);
        if (items!=null&&items.size()!=0){
            holder.play(items);
        }
    }

    @Override
    public int getItemCount() {
        return addData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView detailRedText;
        TextView detailBlueText;
        public ViewHolder(View itemView) {
            super(itemView);
            detailRedText = (TextView) itemView.findViewById(R.id.tv_detail_red);
            detailBlueText = (TextView) itemView.findViewById(R.id.tv_detail_blue);
        }
        public void play(List<Ball> items){
            StringBuilder red = new StringBuilder("红球：");
            for (int i=0;i<6;i++) {
                Ball ba = items.get(i);
                red.append(ba.id).append(" , ");
            }
            detailRedText.setText(red.toString());
            detailBlueText.setText("蓝球："+items.get(6).id);
        }
    }
}
