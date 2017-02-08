package com.example.gankdemo.module.search;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.constants.BundleConstant;
import com.example.gankdemo.custom.listener.OnItemClickListener;
import com.example.gankdemo.custom.view.SearchView;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.SearchModel;
import com.example.gankdemo.module.detail.DetailFragment;
import com.example.gankdemo.module.home.MainFragment;
import com.example.gankdemo.util.DensityUtil;
import com.example.gankdemo.util.SnackbarUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**SearchFragment
 * Created by clement on 17/1/12.
 */

public class SearchFragment extends BaseFragment implements SearchContract.View{
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @BindView(R.id.searchView)
    SearchView searchView;

    private SearchPresenterImp mPresenter;

    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_search;
    }

    @Override
    public void initBehavior(View rootView) {
        mPresenter = new SearchPresenterImp(this,getContext());
        mPresenter.start();
    }

    @OnClick(R.id.iv_back)
    void onBackClick(){
        onBackPressed();
    }

    @Override
    public boolean onBackPressed() {
        remove2ShowFragment(SearchFragment.class.getSimpleName(), MainFragment.class.getSimpleName());
        return true;
    }

    @Override
    public EasyRecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public SearchView getSearchView() {
        return searchView;
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        recyclerView.getSwipeToRefresh().setRefreshing(isRefreshing);
    }

    @Override
    public void setEnable(boolean isEnable) {
        recyclerView.getSwipeToRefresh().setEnabled(isEnable);
    }

    @Override
    public void showRecyclerErrorView() {
        recyclerView.setErrorView(R.layout.view_error);
        recyclerView.showError();
    }

    @Override
    public void showSnackBar(String content) {
        SnackbarUtil.show(recyclerView,content);
    }

    @Override
    public String getSearchContent() {
        return searchView.getEtContent().getText().toString();
    }

    @Override
    public void switchFragment(Bundle bundle) {
        switchFragment(SearchFragment.class.getSimpleName(),new DetailFragment(),
                DetailFragment.class.getSimpleName(),bundle);
    }
}
