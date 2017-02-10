package com.example.gankdemo.module.setting;

import android.os.AsyncTask;

import com.example.gankdemo.R;
import com.example.gankdemo.base.mvp.BasePresenter;
import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.module.setting.observer.ActionType;
import com.example.gankdemo.module.setting.observer.ListenerManager;
import com.example.gankdemo.util.GlideCacheUtil;
import com.example.gankdemo.util.SPUtil;
import com.example.gankdemo.util.SnackbarUtil;

import java.util.List;

/**SettingPresenter
 * Created by developmc on 17/2/8.
 */

public class SettingPresenter extends BasePresenter {
    private SettingView settingView;

    public void attachView(SettingView settingView){
        this.settingView = settingView;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    /**缩略图设置发生更改
     * @param isSelected
     */
    public void onThumbnailSelected(boolean isSelected){
        //更新状态
        SPUtil.set(settingView.getContext(), SPUConstant.SHOW_THUMBNAIL,isSelected);
        //通知fragment刷新
        ListenerManager.getInstance().informAll(ActionType.thumbnail);
    }

    /**夜间模式设置发生更改
     * @param isSelected
     */
    public void onModeSelected(boolean isSelected){
        //更新状态
        SPUtil.set(settingView.getContext(),SPUConstant.NIGHT_MODE,isSelected);
        // http://www.jianshu.com/p/3b55e84742e5
        toggleThemeSetting(isSelected);
        settingView.refreshUI();
        //刷新完当前页面再通知刷新其他已经打开的页面
        ListenerManager.getInstance().informAll(ActionType.nightMode);
    }

    /**
     * 清除缓存按钮点击事件
     */
    public void onCacheClick(){
        //开启线程
        new SettingPresenter.ClearTask().execute();
    }
    /**设置主题
     * @param isNight
     */
    private void toggleThemeSetting(boolean isNight){
        if(isNight){
            settingView.getContext().setTheme(R.style.NightTheme);
        }
        else{
            settingView.getContext().setTheme(R.style.DayTheme);
        }
    }

    /**
     * 异步线程处理图片清除
     */
    class ClearTask extends AsyncTask<String,Void,List<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            settingView.showSnackBar(settingView.getContext().getString(R.string.clearing));
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            //执行清除
            GlideCacheUtil.getInstance().clearImageDiskCache(settingView.getContext());
            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            //执行完成,更新界面
            settingView.setLayoutCacheText(GlideCacheUtil.getInstance().getDiskCacheSize(
                    settingView.getContext()));
            settingView.showSnackBar(settingView.getContext().getString(R.string.clear_finish));
        }
    }

}
