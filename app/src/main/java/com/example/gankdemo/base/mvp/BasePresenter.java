package com.example.gankdemo.base.mvp;

/**base presenter
 * Created by developmc on 17/2/8.
 */

public abstract class BasePresenter {
    /**
     * presenter初始化时执行
     */
    public abstract void start();

    /**
     * presenter销毁时执行
     */
    public abstract void stop();
}
