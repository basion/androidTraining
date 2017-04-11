package cn.com.hakim.viewpagertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.com.hakim.viewpagertest.R;

/**
 * 测试touch action_down和后续事件之间的联系
 */
public class TestScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scroll);
    }
}
