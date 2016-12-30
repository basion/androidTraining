package cn.com.hakim.androidtraining;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/16.
 */

public class ScrollTestActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout scrollLinear;
    TextView scrollText;
    Button scrollButton;
    Button offsetButton;
    Button linearScrollButton;
    Button linearOffsetButton;
//    TextView nestedScrollText;
    CoordinatorLayout parentNested;
    private void initData() {

    }
    Button nestedScrollButton;
    private void initView() {
        parentNested = (CoordinatorLayout) findViewById(R.id.layout_parent_nested);
        nestedScrollButton = (Button) findViewById(R.id.bt_nested_scroll);
        nestedScrollButton.setOnClickListener(this);
        scrollLinear = (LinearLayout) findViewById(R.id.Layout_linear);
        scrollText = (TextView) findViewById(R.id.tv_hello);
        scrollButton = (Button) findViewById(R.id.bt_scroll);
        scrollButton.setOnClickListener(this);
        offsetButton = (Button) findViewById(R.id.bt_offset);
        offsetButton.setOnClickListener(this);
        linearScrollButton = (Button) findViewById(R.id.bt_linear_scroll);
        linearScrollButton.setOnClickListener(this);
        linearOffsetButton = (Button) findViewById(R.id.bt_linear_offset);
        linearOffsetButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);
        initView();
        initData();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_scroll){
            scrollButton.scrollTo(20,0);
        }else if (id == R.id.bt_offset){
            offsetButton.offsetLeftAndRight(100);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) offsetButton.getLayoutParams();
            final int marginLeft = params.leftMargin;
            Snackbar.make(offsetButton,"offsetButton.getScrollX="+offsetButton.getScrollX(),Snackbar.LENGTH_SHORT).setAction("查看margin",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ScrollTestActivity.this,"marginLeft = "+marginLeft,Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        }else if (id == R.id.bt_linear_scroll){
            scrollText.scrollTo(20,0);
            Snackbar.make(parentNested,"scrollText.getScrollX="+scrollText.getScrollX(),Snackbar.LENGTH_SHORT).setAction("点击试试",
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ScrollTestActivity.this,"真的点啊",Toast.LENGTH_SHORT).show();
                }
            }).show();
        }else if (id == R.id.bt_linear_offset){
            scrollText.offsetLeftAndRight(100);
        }else if (id == R.id.bt_start_nested_scroll){

        }else if (id == R.id.bt_nested_scroll){
            Intent intent = new Intent(this,NestedScrollActivity.class);
            startActivity(intent);
        }
    }
}
