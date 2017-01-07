package com.example.gankdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;
import com.example.gankdemo.model.AndroidModel;
import com.example.gankdemo.model.IOSModel;
import com.example.gankdemo.model.WelfareModel;

import java.util.List;

/**Created by clement on 17/01/07.
 * 框架：单activity + 多fragment
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testGetAndroid(10,1);
//        testGetIOS(10,1);
//        testGetAll(10,1);
        testGetWelfare(10,1);

    }

    private void testGetAndroid(int num,int page){
        BaseSubscriber<List<AndroidModel>> subscriber = new BaseSubscriber<List<AndroidModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<AndroidModel> androidModels) {
                super.onNext(androidModels);
            }
        };
        RetrofitHttpHelper.getAndroid(subscriber,num,page);
    }

    private void testGetIOS(int num,int page){
        BaseSubscriber<List<IOSModel>> subscriber = new BaseSubscriber<List<IOSModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<IOSModel> iosModels) {
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
        BaseSubscriber<List<WelfareModel>> subscriber = new BaseSubscriber<List<WelfareModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<WelfareModel> welfareModels) {
                super.onNext(welfareModels);
            }
        };
        RetrofitHttpHelper.getWelfare(subscriber,num,page);
    }
}
