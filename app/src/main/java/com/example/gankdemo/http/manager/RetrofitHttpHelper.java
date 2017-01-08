package com.example.gankdemo.http.manager;

import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;

import java.util.List;

import rx.Observable;

/**实现具体的网络请求
 * Created by clement on 17/1/7.
 */

public class RetrofitHttpHelper extends RetrofitHttp{

    /**获取Android模块下的数据
     * @param subscriber
     * @param num
     */
    public static void getAndroid(BaseSubscriber<List<AllModel>> subscriber, int num, int page){
        //创建观察者
        Observable<List<AllModel>> observable = getService().getAndroid(num,page)
                .map(new HttpResultFunc<List<AllModel>>());
        //订阅
        toSubscribe(observable,subscriber);
    }

    public static void getIOS(BaseSubscriber<List<AllModel>> subscriber,int num,int page){
        //创建观察者
        Observable<List<AllModel>> observable = getService().getIOS(num,page)
                .map(new HttpResultFunc<List<AllModel>>());
        //订阅
        toSubscribe(observable,subscriber);
    }

    public static void getAll(BaseSubscriber<List<AllModel>> subscriber, int num, int page){
        //创建观察者
        Observable<List<AllModel>> observable = getService().getAll(num,page)
                .map(new HttpResultFunc<List<AllModel>>());
        //订阅
        toSubscribe(observable,subscriber);
    }

    public static void getWelfare(BaseSubscriber<List<AllModel>> subscriber, int num, int page){
        //创建观察者
        Observable<List<AllModel>> observable = getService().getWelfare(num,page)
                .map(new HttpResultFunc<List<AllModel>>());
        //订阅
        toSubscribe(observable,subscriber);
    }
}
