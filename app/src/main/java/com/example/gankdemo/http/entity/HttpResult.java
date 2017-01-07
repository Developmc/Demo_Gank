package com.example.gankdemo.http.entity;

import com.google.gson.annotations.SerializedName;

/**解析请求回来的数据(依据具体返回数据以定)，项目数据来源：http://gank.io/api
 * Created by clement on 17/1/7.
 */

public class HttpResult<T> {
    @SerializedName("error")
    private boolean success;
    @SerializedName("results")
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
