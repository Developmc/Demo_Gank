package com.example.gankdemo.module.all;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.LazyFragment;

/**AllFragment
 * Created by clement on 17/1/9.
 */

public class AllFragment extends LazyFragment {
    @Override
    protected void initLazyBehavior() {

    }

    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_all;
    }
}
