package com.example.gankdemo.http.subscriber;

import rx.Subscriber;

/**继承，外层不必每次都重写这三个方法
 * Created by clement on 17/1/7.
 */

public class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}
