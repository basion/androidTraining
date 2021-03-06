package cn.com.hakim.androidtraining;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SearchView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.hakim.androidtraining.plugin.ThemeActivity;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView();
        initData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    Button elemaButtonN;
    Button behaviorButton;
    Button takePicButton;
    Button camera2Button;
    Button nextButton;
    Button tabButton;
    Button zhihuButton;
    Button xposeButton;
    Button bottomSheet;
    Button testBottomSheet;
    Button takePicActivity;
    Button ballsButton;
    Button elemaButton;
    Button rangeButton;
    TextInputEditText nameInput;
    AppCompatEditText passInput;
    Button contentButton;
    Button nestedWtoButton;
    Button httpButton;
    Button themeButton;
    private void initView() {
        themeButton = (Button) findViewById(R.id.bt_theme);
        themeButton.setOnClickListener(this);
        httpButton = (Button) findViewById(R.id.bt_test_http);
        httpButton.setOnClickListener(this);
        rangeButton = (Button) findViewById(R.id.bt_test_range);
        rangeButton.setOnClickListener(this);
        elemaButtonN = (Button) findViewById(R.id.bt_elema_n);
        elemaButtonN.setOnClickListener(this);
        nestedWtoButton = (Button) findViewById(R.id.nested_two);
        nestedWtoButton.setOnClickListener(this);
        elemaButton = (Button) findViewById(R.id.bt_elema);
        elemaButton.setOnClickListener(this);
        behaviorButton = (Button) findViewById(R.id.bt_behavior);
        behaviorButton.setOnClickListener(this);
        ballsButton = (Button) findViewById(R.id.bt_balls);
        ballsButton.setOnClickListener(this);
        contentButton = (Button) findViewById(R.id.bt_contact);
        contentButton.setOnClickListener(this);
        camera2Button = (Button) findViewById(R.id.bt_camera2);
        camera2Button.setOnClickListener(this);
        takePicActivity = (Button) findViewById(R.id.bt_takepic_activity);
        takePicActivity.setOnClickListener(this);
        testBottomSheet = (Button) findViewById(R.id.bt_zhihu_test);
        testBottomSheet.setOnClickListener(this);
        bottomSheet = (Button) findViewById(R.id.bt_bottom_sheet);
        bottomSheet.setOnClickListener(this);
        xposeButton = (Button) findViewById(R.id.bt_test_sposed);
        xposeButton.setOnClickListener(this);
        zhihuButton = (Button) findViewById(R.id.bt_zhihu);
        zhihuButton.setOnClickListener(this);
        takePicButton = (Button) findViewById(R.id.bt_take_pic);
        takePicButton.setOnClickListener(this);
        nextButton = (Button) findViewById(R.id.bt_next);
        nextButton.setOnClickListener(this);
        tabButton = (Button) findViewById(R.id.bt_tab_layout);
        tabButton.setOnClickListener(this);
        nameInput = (TextInputEditText) findViewById(R.id.input_name);
        passInput = (AppCompatEditText) findViewById(R.id.input_pass);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                if (name == null || name.equals("")) {
                    nameInput.setError("手机号不能为空");
                    nameInput.requestFocus();
                    requestInputWindows();
                    return;
                }
                String handledPhone = name.replaceAll("\\D","");
//                Snackbar.make(view, "手机号码为"+handledPhone, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if (handledPhone.startsWith("86")){
                                    Snackbar.make(view, "手机号码为"+handledPhone, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }
//                else if (!name.matches("[\\u4e00-\\u9fa5]{2,4}")) {
//                    nameInput.setError("姓名格式为2-4位中文");
//                    nameInput.requestFocus();
//                    requestInputWindows();
//                    return;
//                }
//                String pass = passInput.getText().toString().trim();
//                if (pass == null || pass.equals("")) {
//                    passInput.setError("密码不能为空");
//                    passInput.requestFocus();
//                    requestInputWindows();
//                    return;
//                }
//                Snackbar.make(view, "输入正确", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void requestInputWindows() {
        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void initData() {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.nav_camera);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("newText=", newText);
                return false;
            }
        });
        return true;
    }

    private void doQuery(String query) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_take_pic) {
            MainActivityPermissionsDispatcher.takePicWithCheck(this);
            // lock here
//            takePic();
        } else if (id == R.id.bt_next) {

            Intent intent = new Intent(this, RecycleActivity.class);
            startActivity(intent);
        } else if (id == R.id.bt_tab_layout) {
            Intent intent = new Intent(this, TabLayoutActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_zhihu){
            Intent intent = new Intent(this,ZhiHuActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_test_sposed){
            toastXposted();
        }else if (id == R.id.bt_bottom_sheet){

        }else if (id == R.id.bt_zhihu_test){
            Intent intent = new Intent(this,ZhihuNewActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_takepic_activity){
            Intent intent = new Intent(this,CameraActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_camera2){
            Intent intent = new Intent(this,Camera2ActivityNew.class);
            startActivity(intent);
        }else if(id == R.id.bt_contact){
            Intent intent = new Intent(this,ContactActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_balls){
            Intent intent = new Intent(this,DoubleBallsActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_behavior){
            Intent intent = new Intent(this,BehaviorActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_elema){
            Intent intent = new Intent(this,ElemaActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_scroll){
            Intent intent = new Intent(this,ScrollTestActivity.class);
            startActivity(intent);
        }else if (id == R.id.nested_two){
            Intent intent = new Intent(this,NestedTwoActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_elema_n){
            Intent intent = new Intent(this,ElemaActivityN.class);
            startActivity(intent);
        }else if (id == R.id.bt_test_range){
            Intent intent = new Intent(this,RangeActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_test_http){
            Intent intent = new Intent(this,HttpActivity.class);
            startActivity(intent);
        }else if (id == R.id.bt_theme){
            Intent intent = new Intent(this, ThemeActivity.class);
            startActivity(intent);
        }
    }

    private int value = 1000;
    private int toastXposted(){
        Toast.makeText(this,"value="+value,Toast.LENGTH_SHORT).show();
        return value*10;
    }
    private static final int TAKE_PHOTO_REQ = 0x11;

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void takePic() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File picFile = createFile();
            if (picPath != null && !picPath.equals("")) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
            }
            startActivityForResult(intent, TAKE_PHOTO_REQ);
        } else {
            Snackbar.make(takePicButton, "哪个sb厂商改了相机api", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
//            Toast.makeText(this,"哪个sb厂商改了相机api",Toast.LENGTH_LONG).show();
        }
    }

    private String picPath;

    private File createFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "training");
        String picTempPath;
        picTempPath = mediaStorageDir.getAbsolutePath();
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File tempFile = null;
        tempFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/training/" + "pic" + timeStamp + ".jpg");
        picPath = tempFile.getAbsolutePath();
        return tempFile;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForCamera(PermissionRequest request) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
        showRationaleDialog(R.string.permission_storage_rationale, request);
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onCameraDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        Toast.makeText(this, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onCameraNeverAskAgain() {
        Toast.makeText(this, R.string.permission_storage_never_askagain, Toast.LENGTH_SHORT).show();
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://cn.com.hakim.androidtraining/http/host/path")
        );
        AppIndex.AppIndexApi.start(mClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://cn.com.hakim.androidtraining/http/host/path")
        );
        AppIndex.AppIndexApi.end(mClient, viewAction);
        mClient.disconnect();
    }
}
