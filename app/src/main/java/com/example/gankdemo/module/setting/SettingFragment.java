package com.example.gankdemo.module.setting;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.module.home.MainActivity;
import com.example.gankdemo.module.home.MainFragment;
import com.example.gankdemo.module.model.ListenerManager;
import com.example.gankdemo.util.SPUtil;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

/**SettingFragment
 * Created by clement on 17/1/12.
 */

public class SettingFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.sb_thumbnail)
    SwitchButton sb_select;
    @BindView(R.id.sb_night)
    SwitchButton sb_night;
    @BindView(R.id.layout_mode)
    RelativeLayout layout_mode;
    @BindView(R.id.layout_thumbnail)
    RelativeLayout layout_thumbnail;
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
        sb_select.setCheckedImmediately(isShow);
        sb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //更新状态
                SPUtil.set(getContext(),SPUConstant.SHOW_THUMBNAIL,b);
                //通知fragment刷新
                ListenerManager.getInstance().informAll(null);
            }
        });
        //是否开启夜间模式
        boolean isNightMode = (boolean) SPUtil.get(getContext(), SPUConstant.NIGHT_MODE,false);
        sb_night.setCheckedImmediately(isNightMode);
        sb_night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //更新状态
                SPUtil.set(getContext(),SPUConstant.NIGHT_MODE,b);
//                if(b){
//                    ((MainActivity)getActivity()).setLocalNightMode(MODE_NIGHT_YES);
//                }
//                else{
//                    ((MainActivity)getActivity()).setLocalNightMode(MODE_NIGHT_NO);
//                }
                // http://www.jianshu.com/p/3b55e84742e5
                tooggleThemeSetting(b);
                refreshUI();
            }
        });
    }
    private void tooggleThemeSetting(boolean isNight){
        if(isNight){
            getActivity().setTheme(R.style.DayTheme);
        }
        else{
            getActivity().setTheme(R.style.NightTheme);
        }
    }
    private void refreshUI(){
        TypedValue background = new TypedValue();
        TypedValue textColor = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.colorBackground,background,true);
        theme.resolveAttribute(R.attr.colorTextColor,textColor,true);
        layout_mode.setBackgroundResource(background.resourceId);
        layout_thumbnail.setBackgroundResource(background.resourceId);
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
