package com.example.gankdemo.module;

import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.gankdemo.R;
import com.example.gankdemo.base.BaseActivity;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;

import java.util.List;

/**Created by clement on 17/01/07.
 * 框架：单activity + 多fragment
 */
public class MainActivity extends BaseActivity {

    @Override
    public int onBindLayoutID() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//            layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|layoutParams.flags);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        return R.layout.activity_main;
    }

    @Override
    public void initBehavior(Bundle savedInstanceState) {

        if(savedInstanceState==null){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            MainFragment mainFragment = new MainFragment();
            fragmentTransaction.add(R.id.content_layout,mainFragment,
                    MainFragment.class.getSimpleName()).commit();
        }
//        testGetAndroid(10,1);
//        testGetIOS(10,1);
//        testGetAll(10,1);
//        testGetWelfare(10,1);

    }

    private void testGetAndroid(int num,int page){
        BaseSubscriber<List<AllModel>> subscriber = new BaseSubscriber<List<AllModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<AllModel> androidModels) {
                super.onNext(androidModels);
            }
        };
        RetrofitHttpHelper.getAndroid(subscriber,num,page);
    }

    private void testGetIOS(int num,int page){
        BaseSubscriber<List<AllModel>> subscriber = new BaseSubscriber<List<AllModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<AllModel> iosModels) {
                super.onNext(iosModels);
            }
        };
        RetrofitHttpHelper.getIOS(subscriber,num,page);
    }

    private void testGetAll(int num,int page){
        BaseSubscriber<List<AllModel>> subscriber = new BaseSubscriber<List<AllModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<AllModel> allModels) {
                super.onNext(allModels);
            }
        };
        RetrofitHttpHelper.getAll(subscriber,num,page);
    }

    private void testGetWelfare(int num,int page){
        BaseSubscriber<List<AllModel>> subscriber = new BaseSubscriber<List<AllModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<AllModel> welfareModels) {
                super.onNext(welfareModels);
            }
        };
        RetrofitHttpHelper.getWelfare(subscriber,num,page);
    }
}
