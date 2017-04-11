package cn.com.hakim.androidtraining.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

import cn.com.hakim.androidtraining.BR;
import cn.com.hakim.androidtraining.R;
import cn.com.hakim.androidtraining.model.Ball;

/**
 * Created by Administrator on 2016/11/2.
 */

public class BallAdapter extends RecyclerView.Adapter<BallAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private final LayoutInflater mLayoutInflater;

    public List<Ball> getBalls() {
        return balls;
    }

    public void setBalls(List<Ball> balls) {
        this.balls.clear();
        this.balls.addAll(balls);
        notifyDataSetChanged();
    }

    private List<Ball> balls;
    public BallAdapter(Context context) {
        mLayoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        balls = new LinkedList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater,R.layout.item_ball,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Ball ball = balls.get(position);
        holder.getBinding().setVariable(BR.ball,ball);//cn.com.hakim.androidtraining.model.Ball
        holder.getBinding().executePendingBindings();
        holder.ballButton.setTag(position);
        holder.ballButton.setOnClickListener(this);
        holder.ballButton.setOnCheckedChangeListener(this);
        if (ball.status == 1){
            holder.ballButton.setChecked(true);
        }else {
            holder.ballButton.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return balls.size();
    }

    public List<Ball> getCheckedBalls() {
        List<Ball> checkedRedBalls = new LinkedList<>();
        for (Ball ball:balls){
            if (ball.status == 1){
                checkedRedBalls.add(ball);
            }
        }
        return checkedRedBalls;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String id = buttonView.getText().toString();
        int i_id = Integer.valueOf(id);
        Ball ball;
        if (buttonView.isChecked()){
             ball = new Ball(id,1);
        }else {
             ball = new Ball(id,0);
        }
        int po = (Integer) buttonView.getTag();
        balls.remove(po);
        balls.add(po,ball);
    }
    public interface ISelectChanged{
        void onChanged();
    }

    public ISelectChanged getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(ISelectChanged changeListener) {
        this.changeListener = changeListener;
    }

    private ISelectChanged changeListener;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.checkbox_ball){
//            ToggleButton button = (ToggleButton) v;
//            if (checkedBalls.size()>=7){
//                if (button.isChecked()){
//                    button.setChecked(false);
//                }
//            }
        }
    }

    class ViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder{
        private T mBinding;
        ToggleButton ballButton;
        public ViewHolder(T binding) {
            super(binding.getRoot());
            mBinding = binding;
            ballButton = (ToggleButton) binding.getRoot().findViewById(R.id.checkbox_ball);
        }
        public T getBinding() {
            return mBinding;
        }
    }
}
