package com.example.bsproperty;

import android.app.Application;

import com.example.bsproperty.bean.UserBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by yezi on 2018/1/27.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    private UserBean userBean;
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final int CURR_ADMIN = 0;
    public static final int CURR_USER = 1;
    public static final int CURR_MERCHANT = 2;


    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("hdd"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);


    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();

        }
        return instance;
    }


    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
