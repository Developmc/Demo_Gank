package com.example.gankdemo.module.home.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**用于FloatingActionButton，让FloatingActionButton跟随AppBarLayout变换的behavior
 * Created by clement on 17/1/11.
 */

public class AppBarLayoutBehavior extends CoordinatorLayout.Behavior<FloatingActionButton>{
    //默认AppBarLayout的当前状态是：显示
    private boolean currentStatus = true;
    private AppBarLayoutStatusListener appBarLayoutStatusListener;

    public AppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        //记录AppBarLayout当前的坐标
        int currentY = (int)dependency.getY();
        //保存上一次的状态
        boolean lastStatus = currentStatus;
        if(currentY==0){
            currentStatus = true;
        }
        else {
            currentStatus = false ;
        }
        //当状态发生了改变
        if(appBarLayoutStatusListener!=null && lastStatus!=currentStatus){
            appBarLayoutStatusListener.onStatusChanged(currentStatus);
        }
        return true;
    }

    /**用于外界获取behavior
     * @param view
     * @param <V>
     * @return
     */
    public static <V extends View> AppBarLayoutBehavior from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof AppBarLayoutBehavior)) {
            throw new IllegalArgumentException("The view is not associated with AppBarLayoutBehavior");
        }
        return (AppBarLayoutBehavior) behavior;
    }

    public void setAppBarLayoutStatusListener(AppBarLayoutStatusListener appBarLayoutStatusListener) {
        this.appBarLayoutStatusListener = appBarLayoutStatusListener;
    }

    public interface AppBarLayoutStatusListener {
        void onStatusChanged(boolean isShow);
    }

}
