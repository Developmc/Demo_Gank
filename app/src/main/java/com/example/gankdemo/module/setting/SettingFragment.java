package com.example.gankdemo.module.setting;

import android.view.View;
import android.widget.TextView;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.module.home.MainFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**SettingFragment
 * Created by clement on 17/1/12.
 */

public class SettingFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initBehavior(View rootView) {
        tv_title.setText(getString(R.string.setting));
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
