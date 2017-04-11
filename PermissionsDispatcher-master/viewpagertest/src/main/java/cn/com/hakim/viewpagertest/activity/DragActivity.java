package cn.com.hakim.viewpagertest.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.com.hakim.viewpagertest.R;

/**
 * 采用一般touch事件机制
 * 还可以通过nestedScrolling机制实现
 */
public class DragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

    }

}
