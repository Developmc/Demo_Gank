package com.example.gankdemo.module.setting;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.module.home.MainFragment;
import com.example.gankdemo.util.SPUtil;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**SettingFragment
 * Created by clement on 17/1/12.
 */

public class SettingFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.sb_select)
    SwitchButton sb_select;
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
        boolean isShow = (boolean) SPUtil.get(getContext(), SPUConstant.SHOW_THUMBNAIL,false);
        sb_select.setChecked(isShow);
        sb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //更新状态
                SPUtil.set(getContext(),SPUConstant.SHOW_THUMBNAIL,b);
                //发送一个自定义的广播
                sendCustomBroadcast();
            }
        });
    }

    /**
     * 发送广播
     */
    private void sendCustomBroadcast(){
        Intent intent = new Intent();
        //设置action
        intent.setAction("action");
        //发送无序广播
        getContext().sendBroadcast(intent);
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
