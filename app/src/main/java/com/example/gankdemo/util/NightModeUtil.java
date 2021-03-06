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
        theme.resolveAttribute(R.attr.colorStatusBar, statusBarColor,true);
        return ContextCompat.getColor(context, statusBarColor.resourceId);
    }

    public static int getFloatActionButtonColor(Context context){
        TypedValue floatActionButtonColor = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorFloatActionButton, floatActionButtonColor,true);
        return ContextCompat.getColor(context, floatActionButtonColor.resourceId);
    }

    public static int getProgressColor(Context context){
        TypedValue progressColor = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorProgress, progressColor,true);
        return ContextCompat.getColor(context, progressColor.resourceId);
    }

    public static int getToolbarTextColor(Context context){
        TypedValue toolbarTextColor = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorToolbarText, toolbarTextColor,true);
        return ContextCompat.getColor(context, toolbarTextColor.resourceId);
    }

    public static int getToolbarImageColor(Context context){
        TypedValue toolbarImageColor = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorToolbarImage, toolbarImageColor,true);
        return ContextCompat.getColor(context, toolbarImageColor.resourceId);
    }

    public static int getFloatActionImageColor(Context context){
        TypedValue floatActionImageColor = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorFloatActionImage, floatActionImageColor,true);
        return ContextCompat.getColor(context, floatActionImageColor.resourceId);
    }

}
