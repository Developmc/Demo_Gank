package com.example.gankdemo.module.home;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.gankdemo.R;
import com.example.gankdemo.base.BaseActivity;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;
import com.example.gankdemo.util.SPUtil;

import java.util.List;

/**Created by clement on 17/01/07.
 * 框架：单activity + 多fragment
 */
public class MainActivity extends BaseActivity {

    @Override
    public int onBindLayoutID() {
        //初始化主题
        initTheme();
        return R.layout.activity_main;
    }

    @Override
    public void initBehavior(Bundle savedInstanceState) {
        if(savedInstanceState==null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            MainFragment mainFragment = new MainFragment();
            fragmentTransaction.add(R.id.content_layout,mainFragment,
                    MainFragment.class.getSimpleName()).commit();
        }

    }

    /**
     * 初始化主题
     */
    private void initTheme(){
        boolean isNightMode = (boolean) SPUtil.get(this, SPUConstant.NIGHT_MODE,false);
        if(isNightMode){
            setTheme(R.style.NightTheme);
        }
        else{
            setTheme(R.style.DayTheme);
        }
    }

    @Override
    public void onBackPressed() {
        //获取当前显示的fragment
        BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.content_layout);
        if(baseFragment!=null && !baseFragment.onBackPressed()){
            //如果当前的fragment没有处理返回事件,执行activity的默认返回事件
            finish();
        }
    }

    private void testGetAndroid(int num, int page){
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

    /**更新
     * @param mode
     */
    public void setLocalNightMode(int mode){
//        getDelegate().setLocalNightMode(mode);
//        recreate();
    }
}
