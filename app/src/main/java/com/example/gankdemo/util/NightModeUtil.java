package com.example.gankdemo.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.example.gankdemo.R;

/**日间夜间模式切换工具类
 * Created by clement on 17/1/19.
 */

public class NightModeUtil {

    /**获取背景色
     * @param context
     * @return
     */
    public static int getBackgroundColor(Context context){
        TypedValue background = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorBackground,background,true);
        return ContextCompat.getColor(context,background.resourceId);
    }

    public static int getTextColor(Context context){
        TypedValue textColor = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorText, textColor,true);
        return ContextCompat.getColor(context, textColor.resourceId);
    }

    public static int getLineColor(Context context){
        TypedValue lineColor = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorLine, lineColor,true);
        return ContextCompat.getColor(context, lineColor.resourceId);
    }

    public static int getImageColor(Context context){
        TypedValue imageColor = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorImage, imageColor,true);
        return ContextCompat.getColor(context, imageColor.resourceId);
    }

    public static int getStatusBarColor(Context context){
        TypedValue statusBarColor = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, statusBarColor,true);
        return ContextCompat.getColor(context, statusBarColor.resourceId);
    }

}
