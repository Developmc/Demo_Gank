package com.example.gankdemo.base.interfaces;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**implement by activity
 * Created by clement on 17/01/07.
 */

public interface ActivityInitializer {
    @LayoutRes
    int onBindLayoutID();

    void initBehavior(Bundle savedInstanceState);
}
