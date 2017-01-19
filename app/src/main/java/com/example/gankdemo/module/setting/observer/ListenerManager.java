package com.example.gankdemo.module.setting.observer;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**管理所有实现IListener的类
 * Created by developmc on 17/1/14.
 */

public class ListenerManager {
    private static ListenerManager listenerManager;
    private ListenerManager(){}
    //需要通知的集合
    private List<IListener> listenerList = new CopyOnWriteArrayList<>();
    //单例模式
    public static ListenerManager getInstance(){
        if(listenerManager==null){
            synchronized (ListenerManager.class){
                if(listenerManager==null){
                    listenerManager = new ListenerManager();
                }
            }
        }
        return listenerManager;
    }

    /**注册监听
     * @param listener
     */
    public void registerListener(@NonNull IListener listener){
        listenerList.add(listener);
    }

    /**移除监听
     * @param listener
     */
    public void unRegisterListener(@NonNull IListener listener){
        if(listenerList.contains(listener)){
            listenerList.remove(listener);
        }
    }

    /**通知所有的观察者
     * @param actionType
     */
    public void informAll(ActionType actionType){
        for(IListener listener:listenerList){
            listener.notifyAllFragment(actionType);
        }
    }
}
