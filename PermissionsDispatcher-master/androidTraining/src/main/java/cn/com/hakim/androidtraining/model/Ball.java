package cn.com.hakim.androidtraining.model;

/**
 * Created by Administrator on 2016/11/2.
 */

public class Ball {
    public final String id;
    public int status;//0 未选中，1选中
    public Ball(String id,int status) {
        this.id = id;
        this.status = status;
    }
}
