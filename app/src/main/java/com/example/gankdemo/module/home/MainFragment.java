package com.example.gankdemo.module.home;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.base.fragment.LazyFragment;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;
import com.example.gankdemo.module.ModelFragment.ModelFragment;
import com.example.gankdemo.module.home.adapter.FragmentAdapter;
import com.example.gankdemo.module.home.behavior.AppBarLayoutBehavior;
import com.example.gankdemo.module.home.type.ModelType;
import com.example.gankdemo.util.AnimatorUtil;
import com.example.gankdemo.util.ImageUtil;
import com.example.gankdemo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MainFragment
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.rootView)
    CoordinatorLayout rootView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.iv_setting)
    ImageView iv_setting;
    private List<String> titles = new ArrayList<>();
    //记录ImageView原始的高度，和放大后的高度
    private int originalHeight,enLargeHeight;
    private boolean isEnLarge = false ;
    private List<String> imageUrlList = new ArrayList<>();
    //记录背景图的index
    private int imageIndex = 0;
    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_main;
    }

    @Override
    public void initBehavior(View rootView) {
        //请求获取图片列表
        getImageListFromNet();
        initData();
        initImageView();
        initTabLayout();
        initViewPager();
        initFab();
    }

    private void initData(){
        titles.add("Android");
        titles.add("IOS");
        titles.add("All");
        titles.add("Welfare");
    }
    private void initImageView(){
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(originalHeight==0 && enLargeHeight==0){
                    //获取屏幕的高度
                    enLargeHeight = rootView.getHeight();
                    originalHeight = iv_icon.getHeight();
                }
                if(isEnLarge){
                    //执行缩小
                    zoomImageView(iv_icon,enLargeHeight,1,(float) originalHeight/enLargeHeight,500);
                    isEnLarge = false;
                }
                else{
                    //执行放大
                    zoomImageView(iv_icon,originalHeight,1,(float) enLargeHeight/originalHeight,500);
                    isEnLarge = true;
                }
            }
        });
        iv_setting.setOnClickListener(this);
    }

    /**放大控件动画，属性动画
     * @param imageView
     * @param beforeHeight
     * @param fromScale
     * @param toScale
     * @param duration
     */
    private void zoomImageView(final ImageView imageView, final int beforeHeight, float fromScale, float toScale, long duration){
        ObjectAnimator anim = ObjectAnimator.ofFloat(imageView,"image",fromScale,toScale).setDuration(duration);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (Float)valueAnimator.getAnimatedValue();
                updateParams(imageView,beforeHeight,val);
            }
        });
        anim.start();
    }

    /**更新控件的属性
     * @param imageView
     * @param height
     * @param val
     */
    private void updateParams(ImageView imageView,int height,float val){
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        float tempHeight = val*height;
        params.height = (int)tempHeight;
        iv_icon.setLayoutParams(params);
    }
    private void initTabLayout(){
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
    }
    private void initViewPager(){
        List<LazyFragment> fragments = new ArrayList<>();
        ModelFragment androidFragment = new ModelFragment();
        androidFragment.setModelType(ModelType.Android);

        ModelFragment iosFragment = new ModelFragment();
        iosFragment.setModelType(ModelType.IOS);

        ModelFragment allFragment = new ModelFragment();
        allFragment.setModelType(ModelType.All);

        ModelFragment welfareFragment = new ModelFragment();
        welfareFragment.setModelType(ModelType.Welfare);

        fragments.add(androidFragment);
        fragments.add(iosFragment);
        fragments.add(allFragment);
        fragments.add(welfareFragment);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getFragmentManager(),fragments,titles);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(4);
        //滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFab(){
        //设置点击事件
        fab.setOnClickListener(this);
        AppBarLayoutBehavior barLayoutBehavior = AppBarLayoutBehavior.from(fab);
        //设置状态监听，滑动到隐藏返回false，滑动到完全显示返回true
        barLayoutBehavior.setAppBarLayoutStatusListener(new AppBarLayoutBehavior.AppBarLayoutStatusListener() {
            @Override
            public void onStatusChanged(boolean isShow) {
                if(isShow){
                    //显示fab
                    AnimatorUtil.scaleShow(fab);
                }
                else{
                    //隐藏fab
                    AnimatorUtil.scaleHide(fab);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.fab:
                //响应fab的点击事件,更新背景图
                setImage(getImageFormIndex(),iv_icon);
                break;
            case R.id.iv_setting:
                ToastUtil.show(getContext(),"setting");
                break;
        }
    }

    /**
     * 通过网络请求获取图片列表
     */
    private void getImageListFromNet(){
        BaseSubscriber<List<AllModel>> subscriber = new BaseSubscriber<List<AllModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<AllModel> allModels) {
                for(AllModel model:allModels){
                    imageUrlList.add(model.getUrl());
                }
                //设置图片
                setImage(getImageFormIndex(),iv_icon);
            }
        };
        RetrofitHttpHelper.getWelfare(subscriber,20,1);
    }

    /**为imageView设置背景图
     * @param url
     */
    private void setImage(String url,ImageView imageView){
        ImageUtil.displayBigImage(getContext(),url,imageView);
    }

    /**根据下标找到图片的url
     * @return
     */
    private String getImageFormIndex(){
        String url ;
        if(imageIndex<imageUrlList.size()){
            url =  imageUrlList.get(imageIndex);
            imageIndex++;
        }
        else{
            //重置
            imageIndex = 0;
            url =  imageUrlList.get(imageIndex);
        }
        return url;
    }
}
