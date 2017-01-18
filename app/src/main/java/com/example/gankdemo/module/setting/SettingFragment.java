package com.example.gankdemo.module.setting;

import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.custom.view.ItemSwitchButton;
import com.example.gankdemo.custom.view.listener.OnItemSelectedListener;
import com.example.gankdemo.module.home.MainFragment;
import com.example.gankdemo.module.model.ListenerManager;
import com.example.gankdemo.util.SPUtil;
import com.example.gankdemo.util.StatusBarUtil;

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
    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initBehavior(View rootView) {
        tv_title.setText(getString(R.string.setting));
        initSwitchButton();
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
                ListenerManager.getInstance().informAll(null);
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
            }
        });
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
        TypedValue background = new TypedValue();
        TypedValue textColor = new TypedValue();
        TypedValue lineColor = new TypedValue();
        TypedValue imageColor = new TypedValue();
        TypedValue statusBarColor = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.colorBackground,background,true);
        theme.resolveAttribute(R.attr.colorText,textColor,true);
        theme.resolveAttribute(R.attr.colorLine,lineColor,true);
        theme.resolveAttribute(R.attr.colorImage,imageColor,true);
        theme.resolveAttribute(R.attr.colorPrimary,statusBarColor,true);
        layout_mode.getItemLayout().setBackgroundResource(background.resourceId);
        layout_mode.getTvLabel().setTextColor(ContextCompat.getColor(getContext(),textColor.resourceId));
        layout_mode.getLineView().setBackgroundResource(lineColor.resourceId);
        layout_thumbnail.getItemLayout().setBackgroundResource(background.resourceId);
        layout_thumbnail.getTvLabel().setTextColor(ContextCompat.getColor(getContext(),textColor.resourceId));
        layout_thumbnail.getLineView().setBackgroundResource(lineColor.resourceId);

        StatusBarUtil.setWindowsStatusBarColor(getActivity(),statusBarColor.resourceId);
        rootView.setBackgroundResource(background.resourceId);
        iv_back.setColorFilter(ContextCompat.getColor(getContext(),imageColor.resourceId));
        layout_title.setBackgroundResource(background.resourceId);
        tv_title.setTextColor(ContextCompat.getColor(getContext(),textColor.resourceId));
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
