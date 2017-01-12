package com.example.gankdemo.module.search;

import android.view.View;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.module.home.MainFragment;

import butterknife.OnClick;

/**SearchFragment
 * Created by clement on 17/1/12.
 */

public class SearchFragment extends BaseFragment{
    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_search;
    }

    @Override
    public void initBehavior(View rootView) {

    }
    @OnClick(R.id.iv_back)
    void onBackClick(){
        onBackPressed();
    }

    @Override
    public boolean onBackPressed() {
        remove2ShowFragment(SearchFragment.class.getSimpleName(), MainFragment.class.getSimpleName());
        return true;
    }
}
