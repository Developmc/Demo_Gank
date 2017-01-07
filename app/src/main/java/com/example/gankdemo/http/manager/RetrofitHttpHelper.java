package com.example.gankdemo.http.manager;

import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;
import com.example.gankdemo.model.AndroidModel;
import com.example.gankdemo.model.IOSModel;
import com.example.gankdemo.model.WelfareModel;

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
    public static void getAndroid(BaseSubscriber<List<AndroidModel>> subscriber, int num, int page){
        //创建观察者
        Observable<List<AndroidModel>> observable = getService().getAndroid(num,page)
                .map(new HttpResultFunc<List<AndroidModel>>());
        //订阅
        toSubscribe(observable,subscriber);
    }

    public static void getIOS(BaseSubscriber<List<IOSModel>> subscriber,int num,int page){
        //创建观察者
        Observable<List<IOSModel>> observable = getService().getIOS(num,page)
                .map(new HttpResultFunc<List<IOSModel>>());
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

    public static void getWelfare(BaseSubscriber<List<WelfareModel>> subscriber, int num, int page){
        //创建观察者
        Observable<List<WelfareModel>> observable = getService().getWelfare(num,page)
                .map(new HttpResultFunc<List<WelfareModel>>());
        //订阅
        toSubscribe(observable,subscriber);
    }
}
