package com.example.gankdemo.module.home;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.example.gankdemo.R;
import com.example.gankdemo.base.BaseActivity;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;
import com.example.gankdemo.util.SPUtil;

import java.util.List;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

/**Created by clement on 17/01/07.
 * 框架：单activity + 多fragment
 */
public class MainActivity extends BaseActivity {

    @Override
    public int onBindLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initBehavior(Bundle savedInstanceState) {
        //设置当前的模式
//        setNightMode();

        if(savedInstanceState==null){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            MainFragment mainFragment = new MainFragment();
            fragmentTransaction.add(R.id.content_layout,mainFragment,
                    MainFragment.class.getSimpleName()).commit();
        }

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

    @Override
    public void onBackPressed() {
        //获取当前显示的fragment
        BaseFragment baseFragment = (BaseFragment) getFragmentManager().findFragmentById(R.id.content_layout);
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
        getDelegate().setLocalNightMode(mode);
//        recreate();
    }
}
