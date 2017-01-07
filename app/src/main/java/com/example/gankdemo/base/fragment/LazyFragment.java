package com.example.gankdemo.base.fragment;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**Lazy Fragment,用于viewPager中,懒加载，当该fragment可见时才加载UI和数据
 * Created by clement on 17/01/07.
 */

public abstract class LazyFragment extends BaseFragment {
    //是否可见
    protected boolean isVisible;
    // 标志位，标志Fragment已经初始化完成。
    protected boolean isPrepared = false;
    //判断是否已经执行了onCreateView
    private boolean isOnCreateView = false ;
    protected abstract void initLazyBehavior();

    @Override
    public void initBehavior(View rootView) {
        isOnCreateView = true ;
        //兼容低版本,低于15时，没有setUserVisibleHint这个回调，直接将isVisible设为true
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
            isVisible = true;
        }
        //当执行到OnCreateView时，需要判断是否应该执行doLoad
        doLoad();
    }

    /**
     * 实现Fragment数据的缓加载，注意：setUserVisibleHint()比onCreateView()先调用
     * @param isVisibleToUser
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }
        //当fragment可见状态发生改变的时候，需要判断是否应该执行doLoad
        doLoad();
    }

    /**
     * 根据条件检查是否执行懒加载
     */
    private void doLoad(){
        //如果fragment可见，而且还没有初始化
        if(isVisible && !isPrepared && isOnCreateView){
            initLazyBehavior();
            isPrepared = true ;
        }
    }
}
