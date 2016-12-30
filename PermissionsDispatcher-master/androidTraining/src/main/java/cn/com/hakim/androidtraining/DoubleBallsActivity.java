package cn.com.hakim.androidtraining;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cn.com.hakim.androidtraining.adapter.AddAdapter;
import cn.com.hakim.androidtraining.adapter.BallAdapter;
import cn.com.hakim.androidtraining.adapter.BlueBallAdapter;
import cn.com.hakim.androidtraining.model.Ball;

/**
 * Created by Administrator on 2016/11/2.
 */

public class DoubleBallsActivity extends AppCompatActivity implements BallAdapter.ISelectChanged, View.OnClickListener {
    RecyclerView redBalls,blueBallRecycleView;
    BallAdapter redAdapter;
    BlueBallAdapter blueAdapter;
    TextView randomRedBallsText;
    TextView redBallsText,blueBallsText;
    TextView blueRandomText;
    Button randomRedButton;
    Button randomBlueButton;
    Button allRandomButton;
    RecyclerView addRecycleView;
    AddAdapter addAdapter;
    Button addButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.double_balls_activity);
        addRecycleView = (RecyclerView) findViewById(R.id.recycleView_add);
        redBalls = (RecyclerView) findViewById(R.id.recycleView_red_ball);
        randomRedButton = (Button) findViewById(R.id.bt_random_red);
        randomRedButton.setOnClickListener(this);
        randomRedBallsText = (TextView) findViewById(R.id.tv_random_red_balls);
        blueBallRecycleView = (RecyclerView) findViewById(R.id.recycleView_blue_ball);
        redBallsText = (TextView) findViewById(R.id.tv_red_balls);
        blueBallsText = (TextView) findViewById(R.id.tv_blue_balls);
        allRandomButton = (Button) findViewById(R.id.bt_all_random);
        allRandomButton.setOnClickListener(this);
        randomBlueButton = (Button) findViewById(R.id.bt_blue_random);
        randomBlueButton.setOnClickListener(this);
        blueRandomText = (TextView) findViewById(R.id.tv_red_random_text);
        setUpRedBalls();
        setUpBlueBalls();
        addAdapter = new AddAdapter();
        addButton = (Button) findViewById(R.id.bt_add);
        addButton.setOnClickListener(this);
        addRecycleView.setLayoutManager(new LinearLayoutManager(this));
        addRecycleView.setAdapter(addAdapter);
    }

    private void setUpBlueBalls() {
        List<Ball> blueBalls = new LinkedList<>();
        for (int i=1;i<=16;i++){
            Ball ball = new Ball(i+"",0);
            blueBalls.add(ball);
        }
        blueAdapter = new BlueBallAdapter(this);
        blueAdapter.setBlueBalls(blueBalls);
        blueBallRecycleView.setLayoutManager(new GridLayoutManager(this,8));
        blueBallRecycleView.setAdapter(blueAdapter);
    }

    private void setUpRedBalls() {
        redBalls.setLayoutManager(new GridLayoutManager(this,8));
        redAdapter = new BallAdapter(this);
        List<Ball> redBallData = new LinkedList<>();
        for (int i=1;i<=33;i++){
            Ball ball = new Ball(i+"",0);
            redBallData.add(ball);
        }
        redAdapter.setBalls(redBallData);
        redBalls.setAdapter(redAdapter);
        redAdapter.setChangeListener(this);
    }

    @Override
    public void onChanged() {
        List<Ball> checkedBall = redAdapter.getCheckedBalls();
        StringBuilder build = new StringBuilder();
        for (Ball ball:checkedBall) {
            build.append(ball.id).append(",");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_random_red){
           List<Ball> randomRedBalls = randomRed();
            StringBuilder builder = new StringBuilder("随机红:");
            for (Ball ball:randomRedBalls){
                builder.append(ball.id).append(",");
            }
            randomRedBallsText.setText(builder.toString());
            List<Ball> redRandomBalls = new LinkedList<>();
            for (int i =1;i<=33;i++){
                Ball ball = new Ball(i+"",0);
                redRandomBalls.add(ball);
            }
            for (Ball ba:randomRedBalls){
                for (int i=0;i<redRandomBalls.size();i++){
                    Ball cuBall = redRandomBalls.get(i);
                    if (cuBall.id.equalsIgnoreCase(ba.id)){
                        cuBall.status = 1;
                        break;
                    }
                }
            }
            StringBuilder bu = new StringBuilder("随机红：");
            for (int i =1;i<=33;i++){
                Ball ba = redRandomBalls.get(i-1);
                if (ba.status == 1){
                    bu.append(ba.id).append(",");
                }
            }
            randomRedBallsText.setText(bu.toString());
            redAdapter.setBalls(redRandomBalls);
        }else if (id == R.id.bt_all_random){
            randomAll();
        }else if (id == R.id.bt_blue_random){
            StringBuilder builder = new StringBuilder("篮球：");
            List<Ball> blueBalls = new LinkedList<>();
            Ball only = randomBlue().get(0);
            builder.append(only.id);
            blueRandomText.setText(builder.toString());
            for (int i=1;i<=16;i++){
                Ball ball = new Ball(i+"",0);
                if (ball.id.equalsIgnoreCase(only.id)){
                    ball.status = 1;
                }
                blueBalls.add(ball);
            }
            blueAdapter.setBlueBalls(blueBalls);
        }else if (id == R.id.bt_add){
            List<Ball> redBalls = redAdapter.getCheckedBalls();
            List<Ball> blueBalls = blueAdapter.getCheckedBlueBalls();
            if (redBalls == null||redBalls.size()!=6){
                return;
            }
            if (blueBalls == null||blueBalls.size()!=1){
                return;
            }
            List<Ball> allBall = new LinkedList<>();
            allBall.addAll(redBalls);
            allBall.addAll(blueBalls);
            List<List<Ball>> addBall = new LinkedList<>();
            addBall.add(allBall);
            addAdapter.setAddData(addBall,true);
        }
    }


    private void randomAll() {
        List<Ball> randomRedBalls = randomRed();
        List<Ball> allRedBallData = new LinkedList<>();
        for (int i =1;i<=33;i++){
            Ball ball = new Ball(i+"",0);
            allRedBallData.add(ball);
        }
        for (Ball ball:randomRedBalls) {
            int id = Integer.valueOf(ball.id);
            allRedBallData.get(id-1).status = 1;
        }
        StringBuilder rb = new StringBuilder("红球：");
        for (Ball sortBall:allRedBallData) {
            if (sortBall.status == 1){
                rb.append(sortBall.id).append(" , ");
            }
        }
        redAdapter.setBalls(allRedBallData);
        redBallsText.setText(rb.toString());
        List<Ball> randomBlueBalls = randomBlue();
        List<Ball> allBlueBallData = new LinkedList<>();
        for (int i =1;i<=16;i++){
            Ball ball = new Ball(i+"",0);
            allBlueBallData.add(ball);
        }
        StringBuilder bb = new StringBuilder("蓝秋：");
        for (Ball ball:randomBlueBalls) {
            int id = Integer.valueOf(ball.id);
            allBlueBallData.get(id-1).status = 1;
            bb.append(id).append(" , ");
        }
        blueAdapter.setBlueBalls(allBlueBallData);
        blueBallsText.setText(bb.toString());
    }

    private List<Ball> randomRed() {
        List<Ball> redRandomBalls = new LinkedList<>();
        List<Ball> redAllBalls = new LinkedList<>();
        for (int i = 1; i <=33; i++) {
            Ball ball = new Ball(i+"",0);
            redAllBalls.add(ball);
        }
        for (int i=0;i<6;i++){
            Random random = new Random(System.currentTimeMillis());
            int po = random.nextInt(redAllBalls.size());
            Ball ball = redAllBalls.get(po);
            ball.status = 1;
            redRandomBalls.add(ball);
            redAllBalls.remove(po);
        }
        return redRandomBalls;
    }

    private List<Ball> randomBlue() {
        List<Ball> blueRandomBalls = new LinkedList<>();
        List<Ball> blueAllBalls = new LinkedList<>();
        for (int i = 1; i <=16; i++) {
            Ball ball = new Ball(i+"",0);
            blueAllBalls.add(ball);
        }
        for (int i=0;i<1;i++){
            Random random = new Random(System.currentTimeMillis());
            int po = random.nextInt(blueAllBalls.size());
            Ball ball = blueAllBalls.get(po);
            ball.status = 1;
            blueRandomBalls.add(ball);
            blueAllBalls.remove(po);
        }
        return blueRandomBalls;
    }
}
