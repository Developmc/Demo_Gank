package com.example.gankdemo.module.setting;

import android.support.annotation.NonNull;

import com.example.gankdemo.base.mvp.BaseView;

/**
 * Created by developmc on 17/2/8.
 */

public interface SettingView extends BaseView {
    void showSnackBar(@NonNull String content);
    void refreshUI();
    void setLayoutCacheText(@NonNull String cacheText);
}
