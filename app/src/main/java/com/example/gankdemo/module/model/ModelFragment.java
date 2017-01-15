package com.example.gankdemo.module.model;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.LazyFragment;
import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.custom.decoration.SpaceItemDecoration;
import com.example.gankdemo.custom.listener.OnItemClickListener;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;
import com.example.gankdemo.module.home.type.ModelType;
import com.example.gankdemo.util.DensityUtil;
import com.example.gankdemo.util.SPUtil;
import com.example.gankdemo.util.ToastUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

import butterknife.BindView;

/**ModelFragment
 * Created by clement on 17/1/9.
 */

public class ModelFragment extends LazyFragment implements RecyclerArrayAdapter.OnMoreListener
        ,RecyclerArrayAdapter.OnNoMoreListener,
        android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener,
        IListener{
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private static final int NUMBER = 10;
    private int page = 0;
    //用于标记类型
    private ModelType modelType;
    public void setModelType(ModelType modelType) {
        this.modelType = modelType;
    }

    @Override
    protected void initLazyBehavior() {
        //注册监听器
        ListenerManager.getInstance().registerListener(this);
        initRecyclerView();
    }

    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_model;
    }
    private void initRecyclerView(){
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //设置分割线
        DividerDecoration dividerDecoration = new DividerDecoration(ContextCompat.getColor(
                getContext(),R.color.gray_300), DensityUtil.dip2px(getContext(),0.5f),
                DensityUtil.dip2px(getContext(),8),DensityUtil.dip2px(getContext(),8));
        recyclerView.addItemDecoration(dividerDecoration);
        adapter = new RecyclerViewAdapter(getContext());
        adapter.setOnItemClickListener(new OnItemClickListener<AllModel>() {
            @Override
            public void onItemClick(View itemView, int position) {
                ToastUtil.show(getContext(),position+"");
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setMore(R.layout.view_more,this);
        adapter.setNoMore(R.layout.view_nomore,this);
        recyclerView.setRefreshListener(this);
        recyclerView.setEmptyView(R.layout.view_empty);
        recyclerView.setErrorView(R.layout.view_error);
        recyclerView.showProgress();
        recyclerView.getSwipeToRefresh().setColorSchemeResources(R.color.colorPrimary,
                R.color.green,R.color.orange);
        onRefresh();
    }

    @Override
    public void onMoreShow() {
        BaseSubscriber<List<AllModel>> subscriber = new BaseSubscriber<List<AllModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtil.show(getContext(),e.getMessage());
            }

            @Override
            public void onNext(List<AllModel> allModels) {
                super.onNext(allModels);
                adapter.addAll(allModels);
                adapter.notifyDataSetChanged();
                page++;
            }
        };
        RetrofitHttpHelper.getModelByType(modelType,subscriber,NUMBER,page);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onNoMoreShow() {
        ToastUtil.show(getContext(),"onNoMoreShow");
    }

    @Override
    public void onNoMoreClick() {
    }

    @Override
    public void onRefresh() {
        page = 1;
        BaseSubscriber<List<AllModel>> subscriber = new BaseSubscriber<List<AllModel>>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                recyclerView.showError();
                ToastUtil.show(getContext(),e.getMessage());
            }

            @Override
            public void onNext(List<AllModel> allModels) {
                super.onNext(allModels);
                if(adapter.getDatas()!=null || !adapter.getDatas().isEmpty()){
                    adapter.clear();
                }
                adapter.addAll(allModels);
                adapter.notifyDataSetChanged();
                page++;
            }
        };
        RetrofitHttpHelper.getModelByType(modelType,subscriber,NUMBER,page);
    }

    /**响应设置页面的缩略图改变的回调
     * @param bundle
     */
    @Override
    public void notifyAllFragment(Bundle bundle) {
        boolean isShowIcon = (boolean)SPUtil.get(getContext(), SPUConstant.SHOW_THUMBNAIL,false);
        //刷新adapter
        adapter.refreshShow(isShowIcon);
    }
}
