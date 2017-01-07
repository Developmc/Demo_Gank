package com.example.gankdemo.base.interfaces;

import android.support.annotation.LayoutRes;
import android.view.View;

/**implement by fragment
 * Created by clement on 17/01/07.
 */

public interface FragmentInitializer {
    @LayoutRes
    int onBindLayoutID();

    void initBehavior(View rootView);
}
