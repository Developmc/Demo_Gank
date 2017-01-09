package com.example.gankdemo.module.android;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.LazyFragment;
import com.example.gankdemo.custom.decoration.SpaceItemDecoration;
import com.example.gankdemo.custom.listener.OnItemClickListener;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;
import com.example.gankdemo.module.home.adapter.RecyclerViewAdapter;
import com.example.gankdemo.util.ToastUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**AndroidFragment
 * Created by clement on 17/1/9.
 */

public class AndroidFragment extends LazyFragment implements RecyclerArrayAdapter.OnMoreListener
        ,RecyclerArrayAdapter.OnNoMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private static final int NUMBER = 10;
    private int page = 0;
    @Override
    protected void initLazyBehavior() {
        initRecyclerView();
    }

    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_android;
    }
    private void initRecyclerView(){
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //设置分割线
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(8);
        recyclerView.addItemDecoration(spaceItemDecoration);
        adapter = new RecyclerViewAdapter(getContext());
        adapter.setOnItemClickListener(new OnItemClickListener<AllModel>() {
            @Override
            public void onItemClick(View itemView, int position) {
                ToastUtil.show(getContext(),position+"");
            }
        });
        recyclerView.setAdapterWithProgress(adapter);
        adapter.setMore(R.layout.view_more,this);
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(this);
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
            }

            @Override
            public void onNext(List<AllModel> allModels) {
                super.onNext(allModels);
                adapter.addAll(allModels);
                adapter.notifyDataSetChanged();
                page++;
            }
        };
        RetrofitHttpHelper.getAndroid(subscriber,NUMBER,page);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onNoMoreShow() {
        adapter.resumeMore();
    }

    @Override
    public void onNoMoreClick() {
        adapter.resumeMore();
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
        RetrofitHttpHelper.getAndroid(subscriber,NUMBER,page);
    }

}
