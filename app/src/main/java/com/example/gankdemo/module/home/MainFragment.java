package com.example.gankdemo.module.home;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.base.fragment.LazyFragment;
import com.example.gankdemo.custom.listener.OnLayoutClickListener;
import com.example.gankdemo.custom.view.SearchView;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;
import com.example.gankdemo.module.home.adapter.FragmentAdapter;
import com.example.gankdemo.module.home.behavior.AppBarLayoutBehavior;
import com.example.gankdemo.module.home.type.ModelType;
import com.example.gankdemo.module.model.ModelFragment;
import com.example.gankdemo.module.search.SearchFragment;
import com.example.gankdemo.module.setting.SettingFragment;
import com.example.gankdemo.module.setting.observer.ActionType;
import com.example.gankdemo.module.setting.observer.IListener;
import com.example.gankdemo.module.setting.observer.ListenerManager;
import com.example.gankdemo.util.AnimatorUtil;
import com.example.gankdemo.util.ImageUtil;
import com.example.gankdemo.util.NightModeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MainFragment
 */
public class MainFragment extends BaseFragment implements View.OnClickListener,IListener {
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
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    private List<String> titles = new ArrayList<>();
    //记录ImageView原始的高度，和放大后的高度
    private int originalHeight,enLargeHeight;
    private boolean isEnLarge = false ;
    private List<String> imageUrlList = new ArrayList<>();
    //记录背景图的index
    private int imageIndex = 0;
    private List<LazyFragment> fragments = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
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
        initSearchView();
        initTabLayout();
        initViewPager();
        initFab();
        //注册监听器
        ListenerManager.getInstance().registerListener(this);
    }

    private void initData(){
        titles.add("All");
        titles.add("Android");
        titles.add("IOS");
        titles.add("前端");
        titles.add("瞎推荐");
        titles.add("拓展资源");
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

    private void initSearchView(){
        //设置为不可编辑
        searchView.setEditable(false);
        searchView.setOnLayoutClickListener(new OnLayoutClickListener() {
            @Override
            public void onLayoutClick(View view) {
                switchFragment(MainFragment.class.getSimpleName(),new SearchFragment(),
                        SearchFragment.class.getSimpleName(),searchView,"searchView");
            }
        });
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
    }
    private void initViewPager(){
        ModelFragment allFragment = new ModelFragment(ModelType.All);

        ModelFragment androidFragment = new ModelFragment(ModelType.Android);

        ModelFragment iosFragment = new ModelFragment(ModelType.IOS);

        ModelFragment webFragment = new ModelFragment(ModelType.Web);

        ModelFragment recommendFragment = new ModelFragment(ModelType.Recommend);

        ModelFragment resourceFragment = new ModelFragment(ModelType.Resource);

        fragments.add(allFragment);
        fragments.add(androidFragment);
        fragments.add(iosFragment);
        fragments.add(webFragment);
        fragments.add(recommendFragment);
        fragments.add(resourceFragment);
        fragmentAdapter = new FragmentAdapter(getFragmentManager(),fragments,titles);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(6);
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
        //设置图片颜色
        fab.setColorFilter(NightModeUtil.getFloatActionImageColor(getContext()));
        //设置背景色
        fab.setBackgroundTintList(ColorStateList.valueOf(NightModeUtil.getFloatActionButtonColor(getContext())));
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
                switchFragment(MainFragment.class.getSimpleName(),new SettingFragment(),
                        SettingFragment.class.getSimpleName(),null);
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
        //这里使用全局的context，当activity销毁时，context过期会导致没法显示图片
        ImageUtil.displayBigImage(getContext().getApplicationContext(),url,imageView);
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

    /**响应设置页面夜间模式切换的回调
     * @param actionType
     */
    @Override
    public void notifyAllFragment(ActionType actionType) {
        if(actionType==ActionType.nightMode){
            iv_setting.setColorFilter(NightModeUtil.getImageColor(getContext()));
            fab.setColorFilter(NightModeUtil.getFloatActionImageColor(getContext()));
            fab.setBackgroundTintList(ColorStateList.valueOf(NightModeUtil.getFloatActionButtonColor(getContext())));
            searchView.getEtContent().setHintTextColor(NightModeUtil.getTextColor(getContext()));
            searchView.getSearchIcon().setColorFilter(NightModeUtil.getTextColor(getContext()));
            Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.shape_corner);
            //api大于16
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                searchView.setBackground(drawable);
            }
            //改变AppBarLayout的背景色
            collapsing_toolbar.setContentScrimColor(NightModeUtil.getStatusBarColor(getContext()));
            appBarLayout.setBackgroundColor(NightModeUtil.getStatusBarColor(getContext()));
            //更改viewPager的背景色
            viewPager.setBackgroundColor(NightModeUtil.getBackgroundColor(getContext()));
        }
    }
}
