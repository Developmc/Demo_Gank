package com.example.gankdemo.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.gankdemo.base.fragment.LazyFragment;

import java.util.List;

/**viewPager fragment adapter
 * Created by clement on 17/1/9.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<LazyFragment> fragments ;
    private List<String> titles;//tab中的title文字列表
    public FragmentAdapter(FragmentManager fm, List<LazyFragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }
    @Override
    public Fragment getItem(int position) {
        if(fragments==null||fragments.isEmpty()){
            return null ;
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
