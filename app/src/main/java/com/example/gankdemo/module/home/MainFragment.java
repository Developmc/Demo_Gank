package com.example.gankdemo.module.home;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.base.fragment.LazyFragment;
import com.example.gankdemo.module.all.AllFragment;
import com.example.gankdemo.module.android.AndroidFragment;
import com.example.gankdemo.module.home.adapter.FragmentAdapter;
import com.example.gankdemo.module.ios.IOSFragment;
import com.example.gankdemo.module.welfare.WelfareFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MainFragment
 */
public class MainFragment extends BaseFragment {
    @BindView(R.id.rootView)
    CoordinatorLayout rootView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    private List<String> titles = new ArrayList<>();
    //记录ImageView原始的高度，和放大后的高度
    private int originalHeight,enLargeHeight;
    private boolean isEnLarge = false ;
    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_main;
    }

    @Override
    public void initBehavior(View rootView) {
        initData();
        initImageView();
        initTabLayout();
        initViewPager();
    }

    private void initData(){
        titles.add("Android");
        titles.add("IOS");
        titles.add("Welfare");
        titles.add("All");
    }
    private void initImageView(){
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(originalHeight==0 && enLargeHeight==0){
                    //获取屏幕的高度
//                    enLargeHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
//                    int tabLayoutHeight = tabLayout.getHeight();
//                    enLargeHeight = enLargeHeight-tabLayoutHeight;
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
        Log.d("Height,val",tempHeight+" , "+val);
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
        AndroidFragment androidFragment = new AndroidFragment();
        IOSFragment iosFragment = new IOSFragment();
        AllFragment allFragment = new AllFragment();
        WelfareFragment welfareFragment = new WelfareFragment();
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


}
