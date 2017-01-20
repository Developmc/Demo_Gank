package com.example.gankdemo.util;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

/**SnackBarUtil
 * Created by clement on 17/1/13.
 */

public class SnackbarUtil {
    private static final int DURATION = Snackbar.LENGTH_SHORT;

    /**显示snackbar
     * @param view
     * @param content
     */
    public static void show(@NonNull View view,String content){
        Snackbar.make(view,content,DURATION).show();
    }

    public static void show(@NonNull View view, int resId){
        Snackbar.make(view,resId,DURATION).show();
    }

}
