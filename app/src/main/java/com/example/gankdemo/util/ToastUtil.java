package com.example.gankdemo.util;

import android.content.Context;
import android.widget.Toast;

/**Toast
 * Created by clement on 17/1/9.
 */
public class ToastUtil extends Toast {

    private final static int LENGTH_SHORT = Toast.LENGTH_SHORT;   //默认是这个
    private final static int LENGTH_LONG = Toast.LENGTH_LONG;
    private static Toast toast ;
    private static boolean isShow = true ;    //控制是否显示toast

    public ToastUtil(Context context) {
        super(context);
    }

    public static void show(Context context, CharSequence text){
        //声明一个全局的toast即可
        if(toast==null)
        {
            toast = Toast.makeText(context, text,LENGTH_SHORT) ;
        }
        else {
            toast.setText(text);
            toast.setDuration(LENGTH_SHORT);
        }
        showToast();
    }
    /**可指定显示时间长短
     * @param context
     * @param text
     * @param duration
     */
    public static void show(Context context, CharSequence text, int duration)
    {
        //声明一个全局的toast即可
        if(toast==null)
        {
            toast = Toast.makeText(context, text, duration) ;
        }
        else {
            toast.setText(text);
            toast.setDuration(duration);
        }
        showToast();
    }
    private static void showToast(){
        if(isShow){
            toast.show();
        }
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
