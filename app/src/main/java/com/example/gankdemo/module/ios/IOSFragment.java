package com.example.gankdemo.module.ios;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.LazyFragment;

/**IOSFragment
 * Created by clement on 17/1/9.
 */

public class IOSFragment extends LazyFragment {
    @Override
    protected void initLazyBehavior() {

    }

    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_ios;
    }
}
