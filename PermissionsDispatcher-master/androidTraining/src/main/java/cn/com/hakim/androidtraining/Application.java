package cn.com.hakim.androidtraining;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.URLConnectionNetworkExecutor;
import com.yolanda.nohttp.cache.DBCacheStore;
import com.yolanda.nohttp.cookie.DBCookieStore;

import cn.com.hakim.androidtraining.config.AppConfig;

/**
 * Created by Administrator on 2017/1/6.
 */

public class Application  extends android.app.Application {

    private static Application _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;

        Logger.setDebug(true);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag("NoHttpSample");// 设置NoHttp打印Log的tag。

        AppConfig.getInstance();

        // 一般情况下你只需要这样初始化：
        NoHttp.initialize(this);

        // 如果你需要自定义配置：
        NoHttp.initialize(this, new NoHttp.Config()
                // 设置全局连接超时时间，单位毫秒，默认10s。
                .setConnectTimeout(30 * 1000)
                // 设置全局服务器响应超时时间，单位毫秒，默认10s。
                .setReadTimeout(30 * 1000)
                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .setCacheStore(
                        new DBCacheStore(this).setEnable(true) // 如果不使用缓存，设置false禁用。
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现。
                .setCookieStore(
                        new DBCookieStore(this).setEnable(false) // 如果不维护cookie，设置false禁用。
                )
                // 配置网络层，默认使用URLConnection，如果想用OkHttp：OkHttpNetworkExecutor。
                .setNetworkExecutor(new URLConnectionNetworkExecutor())
        );

        // 如果你需要用OkHttp，请依赖下面的项目，version表示版本号：
        // compile 'com.yanzhenjie.nohttp:okhttp:version'

        // 具体文档请看：https://github.com/yanzhenjie/NoHttp
    }

    public static Application getInstance() {
        return _instance;
    }

}
