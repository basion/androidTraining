package cn.com.hakim.androidtraining.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import java.util.LinkedList;
import java.util.List;

import cn.com.hakim.androidtraining.R;
import cn.com.hakim.androidtraining.model.Ball;

/**
 * Created by Administrator on 2016/11/3.
 */

public class BlueBallAdapter extends RecyclerView.Adapter<BlueBallAdapter.ViewHolder> {
    public List<Ball> getBlueBalls() {
        return blueBalls;
    }

    public void setBlueBalls(List<Ball> blueBalls) {
        this.blueBalls = blueBalls;
        notifyDataSetChanged();
    }

    private List<Ball> blueBalls;
    private Context mContext;
    public BlueBallAdapter(Context context) {
        blueBalls = new LinkedList<>();
        mContext = context;
    }

    public List<Ball> getCheckedBlueBalls() {
        List<Ball> balls = new LinkedList<>();
        for (int i=0;i< blueBalls.size();i++){
            Ball ball = blueBalls.get(i);
            if (ball.status == 1){
                balls.add(ball);
            }
        }
        return balls;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ball_blue,parent,false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ball ball = blueBalls.get(position);
        holder.mButton.setText(ball.id);
        holder.mButton.setTextOff(ball.id);
        holder.mButton.setTextOn(ball.id);
        if (ball.status == 1){
            holder.mButton.setChecked(true);
        }else {
            holder.mButton.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return blueBalls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ToggleButton mButton;
        public ViewHolder(View itemView) {
            super(itemView);
            mButton = (ToggleButton) itemView.findViewById(R.id.checkbox_ball);
        }
    }
}
