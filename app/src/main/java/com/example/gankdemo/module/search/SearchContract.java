package com.example.gankdemo.module.search;

import android.os.Bundle;

import com.example.gankdemo.base.interfaces.BasePresenter;
import com.example.gankdemo.base.interfaces.BaseView;
import com.example.gankdemo.custom.view.SearchView;
import com.jude.easyrecyclerview.EasyRecyclerView;

/**
 * Created by developmc on 17/1/23.
 */
public interface SearchContract {
    interface View extends BaseView<Presenter>{
        EasyRecyclerView getRecyclerView();
        SearchView getSearchView();
        void setRefreshing(boolean isRefreshing);
        void setEnable(boolean isEnable);
        void showRecyclerErrorView();
        void showSnackBar(String content);
        String getSearchContent();
        void switchFragment(Bundle bundle);
    }
    interface Presenter extends BasePresenter<View>{
    }
}
