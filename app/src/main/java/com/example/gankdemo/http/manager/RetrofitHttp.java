package com.example.gankdemo.http.manager;

import android.content.Context;

import com.example.gankdemo.http.entity.HttpResult;
import com.example.gankdemo.http.subscriber.BaseSubscriber;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**工具类,用来配置Retrofit和OKHttp
 * Created by clement on 17/1/7.
 */

public class RetrofitHttp {
    //数据来源： http://gank.io/api
    private static final String BASE_URL = "http://gank.io/api/" ;
    //请求的接口对象
    private static HttpService service;
    //是否缓存
    private boolean isUseCache ;
    private int maxCacheTime = 60;
    public void setMaxCacheTime(int maxCacheTime){
        this.maxCacheTime = maxCacheTime ;
    }
    public void setUseCache(boolean useCache){
        this.isUseCache = useCache ;
    }

    /**获取请求服务的对象
     * @return
     */
    public static HttpService getService(){
        return service;
    }

    /**在application中初始化
     * @param context
     */
    public static void init(Context context){
        //创建HttpService
        createService();
    }

    /**创建HttpService
     * @return
     */
    private static HttpService createService(){
        if(service==null){
            synchronized (RetrofitHttp.class){
                if(service==null){
                    service = getRetrofit().create(HttpService.class);
                }
            }
        }
        return service;
    }
    /**
     * 初始化配置OkHttpClient
     */
    private static OkHttpClient getOkHttp(){
        //获取build
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //打印日志
//        if(Debug.isDebuggerConnected()){
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addInterceptor(loggingInterceptor);
//        }
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20,TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build() ;
    }
    /**
     * 初始化retrofit
     */
    private static Retrofit getRetrofit(){
        //配置retrofit
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**用于绑定观察者和被观察者
     * 网络请求在Schedulers.io线程,UI操作在主线程
     * @param observable
     * @param subscriber
     * @param <T>
     */
    public static <T> void toSubscribe(Observable<T> observable, BaseSubscriber<T> subscriber){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**统一处理http的resultCode,并返回HttpResult中的data数据
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static class HttpResultFunc<T> implements Func1<HttpResult<T>,T> {
        @Override
        public T call(HttpResult<T> tHttpResult) {
            //如果请求失败
            if(!tHttpResult.isSuccess()){
                //TODO
            }
            return tHttpResult.getData();
        }
    }

}
