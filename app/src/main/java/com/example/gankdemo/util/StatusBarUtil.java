package com.example.gankdemo.util;

import android.app.Activity;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

/**修改状态栏的颜色
 * Created by clement on 17/1/18.
 */

public class StatusBarUtil {
    /**5.0以上
     * @param activity
     * @param colorId
     */
    public static void setWindowsStatusBarColor(Activity activity, int colorId){
        try {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(activity,colorId));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
