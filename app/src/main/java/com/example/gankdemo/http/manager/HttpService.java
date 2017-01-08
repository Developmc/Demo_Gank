package com.example.gankdemo.http.manager;

import com.example.gankdemo.http.entity.HttpResult;
import com.example.gankdemo.model.AllModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**封装所有的API请求
 * Created by clement on 17/1/7.
 */

public interface HttpService {
    @GET("data/Android/{num}/{page}")
    Observable<HttpResult<List<AllModel>>> getAndroid(@Path("num") int num, @Path("page") int page);

    @GET("data/iOS/{num}/{page}")
    Observable<HttpResult<List<AllModel>>> getIOS(@Path("num") int num, @Path("page") int page);

    @GET("data/all/{num}/{page}")
    Observable<HttpResult<List<AllModel>>> getAll(@Path("num") int num, @Path("page") int page);

    @GET("data/福利/{num}/{page}")
    Observable<HttpResult<List<AllModel>>> getWelfare(@Path("num") int num, @Path("page") int page);

}
