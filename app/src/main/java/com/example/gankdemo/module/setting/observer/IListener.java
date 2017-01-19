package com.example.gankdemo.module.setting.observer;

/**观察者模式替换广播，通知fragment刷新
 * Created by developmc on 17/1/14.
 */

public interface IListener {
    void notifyAllFragment(ActionType actionType);
}
