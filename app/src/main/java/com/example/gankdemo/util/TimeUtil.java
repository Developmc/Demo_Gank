package com.example.gankdemo.util;

import android.support.annotation.NonNull;

/**
 * Created by developmc on 17/1/9.
 */

public class TimeUtil {
    /**获取日期
     * @param date
     * @return
     */
    public static String getDate(@NonNull String date){
        if(date.length()>=10){
            return date.substring(0,10);
        }
        return date;
    }
}
