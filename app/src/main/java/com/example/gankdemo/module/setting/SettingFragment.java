package com.example.gankdemo.module.setting;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.custom.view.ItemSwitchButton;
import com.example.gankdemo.custom.view.ItemTextView;
import com.example.gankdemo.custom.view.listener.OnItemSelectedListener;
import com.example.gankdemo.module.home.MainFragment;
import com.example.gankdemo.module.setting.observer.ActionType;
import com.example.gankdemo.module.setting.observer.ListenerManager;
import com.example.gankdemo.util.GlideCacheUtil;
import com.example.gankdemo.util.NightModeUtil;
import com.example.gankdemo.util.SPUtil;
import com.example.gankdemo.util.SnackbarUtil;
import com.example.gankdemo.util.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**SettingFragment
 * Created by clement on 17/1/12.
 */

public class SettingFragment extends BaseFragment {
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.layout_title)
    RelativeLayout layout_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.layout_mode)
    ItemSwitchButton layout_mode;
    @BindView(R.id.layout_thumbnail)
    ItemSwitchButton layout_thumbnail;
    @BindView(R.id.layout_cache)
    ItemTextView layout_cache;
    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initBehavior(View rootView) {
        tv_title.setText(getString(R.string.setting));
        initSwitchButton();
        initItemTextView();
    }

    private void initSwitchButton(){
        //是否开启缩略图
        boolean isShow = (boolean) SPUtil.get(getContext(), SPUConstant.SHOW_THUMBNAIL,false);
        layout_thumbnail.getTvLabel().setText(getString(R.string.show_thumbnail));
        layout_thumbnail.setLeftImage(R.drawable.ic_thumbnail);
        layout_thumbnail.getSwitchButton().setCheckedImmediately(isShow);
        layout_thumbnail.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onSelected(boolean isSelected) {
                //更新状态
                SPUtil.set(getContext(),SPUConstant.SHOW_THUMBNAIL,isSelected);
                //通知fragment刷新
                ListenerManager.getInstance().informAll(ActionType.thumbnail);
            }
        });

        //是否开启夜间模式
        boolean isNightMode = (boolean) SPUtil.get(getContext(), SPUConstant.NIGHT_MODE,false);
        layout_mode.getTvLabel().setText(getString(R.string.night_mode));
        layout_mode.setLeftImage(R.drawable.ic_month);
        layout_mode.getSwitchButton().setCheckedImmediately(isNightMode);
        layout_mode.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onSelected(boolean isSelected) {
                //更新状态
                SPUtil.set(getContext(),SPUConstant.NIGHT_MODE,isSelected);
                // http://www.jianshu.com/p/3b55e84742e5
                toggleThemeSetting(isSelected);
                refreshUI();
                //刷新完当前页面再通知刷新其他已经打开的页面
                ListenerManager.getInstance().informAll(ActionType.nightMode);
            }
        });
    }

    private void initItemTextView(){
        layout_cache.getTvLabel().setText(getString(R.string.clear_cache));
        layout_cache.setLeftImage(R.drawable.ic_clear);
        //显示缓存的照片
        layout_cache.getTvValue().setText(GlideCacheUtil.getInstance().getDiskCacheSize(getContext()));
        layout_cache.getItemLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启线程
                new ClearTask().execute();
            }
        });
    }
    class ClearTask extends AsyncTask<String,Void,List<String>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SnackbarUtil.show(layout_cache,getString(R.string.clearing));
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            //执行清除
            GlideCacheUtil.getInstance().clearImageDiskCache(getContext());
            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            //执行完成,更新界面
            layout_cache.getTvValue().setText(GlideCacheUtil.getInstance().getDiskCacheSize(getContext()));
            SnackbarUtil.show(layout_cache,getString(R.string.clear_finish));
        }
    }
    /**设置主题
     * @param isNight
     */
    private void toggleThemeSetting(boolean isNight){
        if(isNight){
            getActivity().setTheme(R.style.NightTheme);
        }
        else{
            getActivity().setTheme(R.style.DayTheme);
        }
    }

    /**
     * 刷新UI，页面已经创建，这时更改主题是不会刷新页面的
     */
    private void refreshUI(){
        layout_mode.getItemLayout().setBackgroundColor(NightModeUtil.getBackgroundColor(getContext()));
        layout_mode.getTvLabel().setTextColor(NightModeUtil.getTextColor(getContext()));
        layout_mode.getLineView().setBackgroundColor(NightModeUtil.getLineColor(getContext()));
        layout_thumbnail.getItemLayout().setBackgroundColor(NightModeUtil.getBackgroundColor(getContext()));
        layout_thumbnail.getTvLabel().setTextColor(NightModeUtil.getTextColor(getContext()));
        layout_thumbnail.getLineView().setBackgroundColor(NightModeUtil.getLineColor(getContext()));
        layout_cache.getItemLayout().setBackgroundColor(NightModeUtil.getBackgroundColor(getContext()));
        layout_cache.getTvLabel().setTextColor(NightModeUtil.getTextColor(getContext()));
        layout_cache.getTvValue().setTextColor(NightModeUtil.getTextColor(getContext()));
        layout_cache.getLineView().setBackgroundColor(NightModeUtil.getLineColor(getContext()));

        StatusBarUtil.setWindowsStatusBarColor(getActivity(),NightModeUtil.getStatusBarColor(getContext()));
        rootView.setBackgroundColor(NightModeUtil.getBackgroundColor(getContext()));
        iv_back.setColorFilter(NightModeUtil.getToolbarImageColor(getContext()));
        layout_title.setBackgroundColor(NightModeUtil.getStatusBarColor(getContext()));
        tv_title.setTextColor(NightModeUtil.getToolbarTextColor(getContext()));
    }

    @OnClick(R.id.iv_back)
    void onBackClick(){
        onBackPressed();
    }

    @Override
    public boolean onBackPressed() {
        remove2ShowFragment(SettingFragment.class.getSimpleName(),
                MainFragment.class.getSimpleName());
        return true;
    }

}
