package com.example.gankdemo.base;

import android.app.Application;

import com.example.gankdemo.http.manager.RetrofitHttpHelper;

/**GankApplication
 * Created by clement on 17/1/7.
 */

public class GankApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化retrofit
        RetrofitHttpHelper.init(this);
    }
}
