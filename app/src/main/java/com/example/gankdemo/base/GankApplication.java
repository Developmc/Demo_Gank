package com.example.gankdemo.base;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.util.SPUtil;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

/**GankApplication
 * Created by clement on 17/1/7.
 */

public class GankApplication extends Application {

    private static GankApplication application;
    public static GankApplication getInstance() {
        if (application == null) {
            synchronized (GankApplication.class) {
                if (application == null) {
                    application = new GankApplication();
                }
            }
        }
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化retrofit
        RetrofitHttpHelper.init(this);
        //设置当前的模式
//        setNightMode();
    }
    /**
     * //设置当前是日间模式还是夜间模式
     */
    private void setNightMode(){
        boolean isNightMode = (boolean) SPUtil.get(this, SPUConstant.NIGHT_MODE,false);
        if(isNightMode){
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
        }
    }
}
