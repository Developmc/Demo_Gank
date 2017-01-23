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

public class SearchFragment extends BaseFragment implements RecyclerArrayAdapter.OnMoreListener
        ,RecyclerArrayAdapter.OnNoMoreListener{
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @BindView(R.id.searchView)
    SearchView searchView;
    private SearchRecyclerViewAdapter adapter ;
    private static final int NUMBER = 10;
    private int page = 0;
    private String searchType;
    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_search;
    }

    @Override
    public void initBehavior(View rootView) {
        initRecyclerView();
        initSearchView();
    }
    private void initRecyclerView(){
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        //设置分割线
        DividerDecoration dividerDecoration = new DividerDecoration(ContextCompat.getColor(
                getContext(),R.color.gray_300), DensityUtil.dip2px(getContext(),0.5f),
                DensityUtil.dip2px(getContext(),8),DensityUtil.dip2px(getContext(),8));
        recyclerView.addItemDecoration(dividerDecoration);
        //初始化adapter
        adapter = new SearchRecyclerViewAdapter(getContext());
        adapter.setOnItemClickListener(new OnItemClickListener<SearchModel>() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(BundleConstant.URL,adapter.getDatas().get(position).getUrl());
                bundle.putString(BundleConstant.TITLE,adapter.getDatas().get(position).getDesc());
                bundle.putString(BundleConstant.FROM_TAG,SearchFragment.class.getSimpleName());
                switchFragment(SearchFragment.class.getSimpleName(),new DetailFragment(),
                        DetailFragment.class.getSimpleName(),bundle);
            }
        });
        adapter.setMore(R.layout.view_more,this);
        adapter.setNoMore(R.layout.view_nomore,this);
        recyclerView.getSwipeToRefresh().setColorSchemeResources(R.color.colorPrimary,
                R.color.green,R.color.orange);
        recyclerView.setAdapter(adapter);
    }
    private void initSearchView(){
        searchView.getSearchIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInfo()){
                    doSearch();
                }
            }
        });
    }

    /**检查是否已经输入
     * @return
     */
    private boolean checkInfo(){
        searchType = searchView.getEtContent().getText().toString();
        if(searchType.isEmpty()){
            SnackbarUtil.show(searchView,getString(R.string.input_search_content));
            return false;
        }
        return true;
    }
    /**
     * 执行搜索请求
     */
    private void doSearch(){
        page = 1;
        BaseSubscriber<List<SearchModel>> subscriber = new BaseSubscriber<List<SearchModel>>(){
            @Override
            public void onStart() {
                super.onStart();
                //开始刷新动画
                recyclerView.getSwipeToRefresh().setRefreshing(true);
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                //停止刷新动画
                recyclerView.getSwipeToRefresh().setRefreshing(false);
                //禁止下拉手势
                recyclerView.getSwipeToRefresh().setEnabled(false);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                recyclerView.setErrorView(R.layout.view_error);
                recyclerView.showError();
                SnackbarUtil.show(recyclerView,e.getMessage());
            }

            @Override
            public void onNext(List<SearchModel> searchModels) {
                super.onNext(searchModels);
                if(adapter.getDatas()!=null || !adapter.getDatas().isEmpty()){
                    adapter.clear();
                }
                adapter.addAll(searchModels);
                adapter.notifyDataSetChanged();
                page++;
            }
        };
        RetrofitHttpHelper.getSearchModel(subscriber,searchType,NUMBER,page);
    }

    @Override
    public void onMoreShow() {
        BaseSubscriber<List<SearchModel>> subscriber = new BaseSubscriber<List<SearchModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                SnackbarUtil.show(recyclerView,e.getMessage());
            }

            @Override
            public void onNext(List<SearchModel> searchModels) {
                super.onNext(searchModels);
                adapter.addAll(searchModels);
                adapter.notifyDataSetChanged();
                page++;
            }
        };
        RetrofitHttpHelper.getSearchModel(subscriber,searchType,NUMBER,page);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onNoMoreShow() {

    }

    @Override
    public void onNoMoreClick() {

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
}
