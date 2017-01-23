package com.example.gankdemo.module.search;

import com.example.gankdemo.base.interfaces.BasePresenter;
import com.example.gankdemo.base.interfaces.BaseView;

/**
 * Created by developmc on 17/1/23.
 */

public interface SearchContract {
    interface SearchView extends BaseView<SearchPresenter>{
        void setTitle(String title);
    }
    interface SearchPresenter extends BasePresenter{

    }
}
