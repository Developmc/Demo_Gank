package com.example.gankdemo.base.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gankdemo.R;
import com.example.gankdemo.base.interfaces.FragmentBackHandler;
import com.example.gankdemo.base.interfaces.FragmentInitializer;

import butterknife.ButterKnife;

/**
 * Base Fragment
 * Created by clement on 17/01/07.
 */

public abstract class BaseFragment extends Fragment implements FragmentInitializer,FragmentBackHandler {
    private Context mContext;
    //保存fragment的view
    protected View rootView;
    private Bundle bundle;
    //fragment的容器view，这里基本固定是 R.id.content_layout
    private int containerViewId = R.id.content_layout;

    /**
     * 绑定activity的操作在onAttach执行
     */
    @TargetApi(23) @Override public void onAttach(Context context) {
        super.onAttach(context);
        onAttach2Context(context);
    }

    /**
     * 兼容android6.0以下
     */
    @SuppressWarnings("deprecation") @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        onAttach2Context(activity);
    }

    /**
     * 兼容android6.0
     */
    protected void onAttach2Context(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity重建时保存Fragment的交互状态(activity重建时,fragment不执行onCreate和onDestroy)
        setRetainInstance(true);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(onBindLayoutID(), container, false);
            ButterKnife.bind(this, rootView);
            initBehavior(rootView);
        }
        //activity重建
        else{
            onRecreate();
        }
        return rootView;
    }

    /**
     * activity重建时，fragment会回调这个方法
     */
    protected void onRecreate(){

    }

    /**
     * 获取context,避免使用getActivity()
     */
    public Context getContext() {
        if (mContext != null) {
            return mContext;
        } else {
            return getActivity();
        }
    }

    /**
     * fragment从隐藏到显示的回调(当需要传递数据前，和remove2showChildFragment绑定设置bundle)
     *
     * @param bundle 如果为空，表示没有数据传输
     */
    public void onHide2Show(Bundle bundle) {

    }

    /**
     * fragment从显示到隐藏的回调
     */
    public void onShow2Hide() {
    }

    @Override public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //从hidden->show
        if (!hidden) {
            onHide2Show(bundle);
            //重置参数
            bundle = null;
        } else {
            onShow2Hide();
        }
    }

    /**
     * 为fragment设置bundle
     */
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    /**
     * add fragment :如果没有则新增，如果已存在，则移除再新增
     *
     * @param containerViewId 将要跳转的fragment的容器view
     * @param toFragment 将要跳转的fragment实例
     * @param toFragmentTag 将要跳转的fragment的Tag
     * @param bundle 传递参数(设为null时，表示不传值)
     */
    @Deprecated
    protected void addFragment(int containerViewId, BaseFragment toFragment, String toFragmentTag,
        Bundle bundle) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //设置跳转动画
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //传递参数
        if (bundle != null) {
            toFragment.setArguments(bundle);
        }
        //判断将要跳转的fragment是否已经存在，如果已经存在，则先移除旧的fragment
        if (manager.findFragmentByTag(toFragmentTag) != null) {
            removeFragment(toFragmentTag);
        }
        //跳转到指定的fragment
        transaction.add(containerViewId, toFragment, toFragmentTag).commitAllowingStateLoss();
    }

    /**
     * add fragment :如果没有则新增，如果已存在，则移除再新增
     *
     * @param toFragment 将要跳转的fragment实例
     * @param toFragmentTag 将要跳转的fragment的Tag
     * @param bundle 传递参数(设为null时，表示不传值)
     */
    @Deprecated
    protected void addFragment(BaseFragment toFragment, String toFragmentTag, Bundle bundle) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //设置跳转动画
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //传递参数
        if (bundle != null) {
            toFragment.setArguments(bundle);
        }
        //判断将要跳转的fragment是否已经存在，如果已经存在，则先移除旧的fragment
        if (manager.findFragmentByTag(toFragmentTag) != null) {
            removeFragment(toFragmentTag);
        }
        //跳转到指定的fragment
        transaction.add(containerViewId, toFragment, toFragmentTag).commitAllowingStateLoss();
    }

    /**
     * 移除fragment
     *
     * @param removeFragmentTag 将要移除的fragment的Tag
     */
    protected void removeFragment(String removeFragmentTag) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //设置跳转动画
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        //移除当前fragment
        BaseFragment removeFragment = (BaseFragment) manager.findFragmentByTag(removeFragmentTag);
        if (removeFragment == null) {
            return;
        }
        transaction.remove(removeFragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 切换fragment，采取的是隐藏当前fragment，然后跳转到新fragment,如果新的fragment已经存在则移除再add
     *
     * @param fromFragmentTag 将要隐藏的fragment的Tag
     * @param containerViewId 将要跳转的fragment的容器view
     * @param toFragment 将要跳转的fragment实例
     * @param toFragmentTag 将要跳转的fragment的Tag
     * @param bundle 传递参数(设为null时，表示不传值)
     */
    @Deprecated
    protected void switchFragment(String fromFragmentTag, int containerViewId,
        BaseFragment toFragment, String toFragmentTag, Bundle bundle) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //设置跳转动画
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //隐藏当前fragment
        transaction.hide(manager.findFragmentByTag(fromFragmentTag));
        //传递参数
        if (bundle != null) {
            toFragment.setArguments(bundle);
        }
        //判断将要跳转的fragment是否已经存在,如果已存在，则移除
        if (manager.findFragmentByTag(toFragmentTag) != null) {
            //跳转到指定的fragment
            transaction.remove(manager.findFragmentByTag(toFragmentTag));
        }
        transaction.add(containerViewId, toFragment, toFragmentTag).commitAllowingStateLoss();
    }

    /**
     * 切换fragment，采取的是隐藏当前fragment，然后跳转到新fragment,如果新的fragment已经存在则移除再add
     *
     * @param fromFragmentTag 将要隐藏的fragment的Tag
     * @param toFragment 将要跳转的fragment实例
     * @param toFragmentTag 将要跳转的fragment的Tag
     * @param bundle 传递参数(设为null时，表示不传值)
     */
    protected void switchFragment(String fromFragmentTag, BaseFragment toFragment,
        String toFragmentTag, Bundle bundle) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //设置跳转动画
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //隐藏当前fragment
        transaction.hide(manager.findFragmentByTag(fromFragmentTag));
        //传递参数
        if (bundle != null) {
            toFragment.setArguments(bundle);
        }
        //判断将要跳转的fragment是否已经存在,如果已存在，则移除
        if (manager.findFragmentByTag(toFragmentTag) != null) {
            //跳转到指定的fragment
            transaction.remove(manager.findFragmentByTag(toFragmentTag));
        }
        transaction.add(containerViewId, toFragment, toFragmentTag).commitAllowingStateLoss();
    }

    /**切换fragment，采取的是隐藏当前fragment，然后跳转到新fragment,添加转出动画
     * @param fromFragmentTag
     * @param toFragment
     * @param toFragmentTag
     * @param view
     * @param transitionName
     */
    protected void switchFragment(String fromFragmentTag, BaseFragment toFragment,
                                  String toFragmentTag,View view,String transitionName) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(Build.VERSION.SDK_INT>=21){
            transaction.addSharedElement(view,transitionName);
            manager.findFragmentByTag(fromFragmentTag).setExitTransition(new Fade());
            toFragment.setSharedElementEnterTransition(new DetailsTransition());
            toFragment.setEnterTransition(new Fade());
            toFragment.setSharedElementReturnTransition(new DetailsTransition());
        }
        //隐藏当前fragment
        transaction.hide(manager.findFragmentByTag(fromFragmentTag));
        transaction.add(containerViewId, toFragment, toFragmentTag).commitAllowingStateLoss();
    }
    /**
     * 移除当前fragment，并显示上一个fragment（该fragment处于隐藏状态）
     *
     * @param removeFragmentTag 将要移除的fragment的Tag
     * @param showFragmentTag 将要显示的fragment的Tag
     */
    protected void remove2ShowFragment(String removeFragmentTag, String showFragmentTag) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //设置跳转动画
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        //移除当前fragment
        removeFragment(removeFragmentTag);
        BaseFragment showFragment = (BaseFragment) manager.findFragmentByTag(showFragmentTag);
        if (showFragment == null) {
            return;
        }
        //显示上一个隐藏的fragment
        transaction.show(showFragment).commitAllowingStateLoss();
    }

    /**
     * 移除当前fragment，传递数据，并显示上一个fragment（该fragment处于隐藏状态）,复写onHide2Show接收数据
     *
     * @param removeFragmentTag 将要移除的fragment的Tag
     * @param showFragmentTag 将要显示的fragment的Tag
     * @param bundle 传递的数据
     */
    protected void remove2ShowFragment(String removeFragmentTag, String showFragmentTag, Bundle bundle) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //设置跳转动画
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        //移除当前fragment
        removeFragment(removeFragmentTag);
        BaseFragment showFragment = (BaseFragment) manager.findFragmentByTag(showFragmentTag);
        if (showFragment == null) {
            return;
        }
        showFragment.setBundle(bundle);
        //显示上一个隐藏的fragment
        transaction.show(showFragment).commitAllowingStateLoss();
    }

    /**处理返回事件,返回true表示处理了该事件，false表示没处理，事件会传递到activity
     * @return
     */
    @Override
    public boolean onBackPressed() {
        return false;
    }

    @TargetApi(21)
    public class DetailsTransition extends TransitionSet {
        public DetailsTransition() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform());
        }
    }
}
