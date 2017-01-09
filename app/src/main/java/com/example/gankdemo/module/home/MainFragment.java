package com.example.gankdemo.module.home;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

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
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private List<String> titles = new ArrayList<>();
    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_main;
    }

    @Override
    public void initBehavior(View rootView) {
        initData();
        initTabLayout();
        initViewPager();
    }

    private void initData(){
        titles.add("Android");
        titles.add("IOS");
        titles.add("Welfare");
        titles.add("All");
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
