package com.example.gankdemo.module.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.gankdemo.R;
import com.example.gankdemo.constants.BundleConstant;
import com.example.gankdemo.custom.listener.OnItemClickListener;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.SearchModel;
import com.example.gankdemo.util.DensityUtil;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

/**实现类
 * Created by developmc on 17/1/23.
 */

public class SearchPresenterImp implements RecyclerArrayAdapter.OnMoreListener
        ,RecyclerArrayAdapter.OnNoMoreListener,SearchContract.Presenter {

    private SearchContract.View mView;
    private Context mContext;
    private static final int NUMBER = 10;
    private int page = 0;
    private String searchType;
    private SearchRecyclerViewAdapter adapter ;
    public SearchPresenterImp(SearchContract.View view,Context context){
        this.mView = view;
        this.mContext = context;
    }

    @Override
    public void start() {
        initRecyclerView();
        initSearchView();
    }

    private void initRecyclerView(){
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mView.getRecyclerView().setLayoutManager(manager);
        //设置分割线
        DividerDecoration dividerDecoration = new DividerDecoration(ContextCompat.getColor(
                mContext, R.color.gray_300), DensityUtil.dip2px(mContext,0.5f),
                DensityUtil.dip2px(mContext,8),DensityUtil.dip2px(mContext,8));
        mView.getRecyclerView().addItemDecoration(dividerDecoration);
        //初始化adapter
        adapter = new SearchRecyclerViewAdapter(mContext);
        adapter.setOnItemClickListener(new OnItemClickListener<SearchModel>() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(BundleConstant.URL,adapter.getDatas().get(position).getUrl());
                bundle.putString(BundleConstant.TITLE,adapter.getDatas().get(position).getDesc());
                bundle.putString(BundleConstant.FROM_TAG,SearchFragment.class.getSimpleName());
                mView.switchFragment(bundle);
            }
        });
        adapter.setMore(R.layout.view_more,this);
        adapter.setNoMore(R.layout.view_nomore,this);
        mView.getRecyclerView().getSwipeToRefresh().setColorSchemeResources(R.color.colorPrimary,
                R.color.green,R.color.orange);
        mView.getRecyclerView().setAdapter(adapter);
    }
    private void initSearchView(){
        mView.getSearchView().getSearchIcon().setOnClickListener(new View.OnClickListener() {
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
        searchType = mView.getSearchContent();
        if(searchType.isEmpty()){
            mView.showSnackBar(mContext.getString(R.string.input_search_content));
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
                mView.setRefreshing(true);
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                //停止刷新动画
                mView.setRefreshing(false);
                //禁止下拉手势
                mView.setEnable(false);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showRecyclerErrorView();
                mView.showSnackBar(e.getMessage());
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
    public void onMoreClick() {

    }

    @Override
    public void onNoMoreShow() {

    }

    @Override
    public void onNoMoreClick() {

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
                mView.showSnackBar(e.getMessage());
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
}
