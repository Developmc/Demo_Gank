package com.example.gankdemo.util;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import java.io.File;
import java.math.BigDecimal;

/**Glide缓存工具类
 * Created by developmc on 17/1/20.
 */

public class GlideCacheUtil {
    private static GlideCacheUtil instance;
    private GlideCacheUtil(){}
    public static GlideCacheUtil getInstance(){
        if(instance==null){
            synchronized (GlideCacheUtil.class){
                instance = new GlideCacheUtil();
            }
        }
        return instance;
    }

    /**清除磁盘的图标缓存
     * @param context
     */
    public void clearImageDiskCache(final Context context){
        try{
            //如果是在主线程,开启子线程（磁盘清除需要在子线程中完成）
            if(Looper.myLooper()==Looper.getMainLooper()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            }
            else{
                Glide.get(context).clearDiskCache();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**清除图片内存缓存
     * @param context
     */
    public void clearImageMemoryCache(Context context){
        try {
            //只能在主线程中清除内存缓存
            if(Looper.myLooper()==Looper.getMainLooper()){
                Glide.get(context).clearMemory();
            }
            else{
                Log.d("Tag","清除Glide图标内存需要在主线程中执行");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**清除所有的图片缓存
     * @param context
     */
    public void clearImageAllCache(Context context){
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
    }

    /**获取磁盘图片缓存的大小,包含单位
     * @param context
     * @return
     */
    public String getDiskCacheSize(Context context){
        String path = getDiskCachePath(context);
        long size = getFolderSize(new File(path));
        return getFormatSize(size);
    }

    /**获取缓存文件的路径
     * @return
     */
    public String getDiskCachePath(@NonNull Context context){
        return context.getCacheDir()+"/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
    }

    /**获取文件夹下的内容大小，单位是byte
     * @return
     */
    public long getFolderSize(@NonNull File file){
        long size = 0;
        try{
            File[] files = file.listFiles();
            for(File temp:files){
                if(temp.isDirectory()){
                    size += getFolderSize(file);
                }
                else{
                    size += temp.length();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }
    /**格式化单位
     * @param size
     * @return
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
