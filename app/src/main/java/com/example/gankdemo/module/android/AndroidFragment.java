package com.example.gankdemo.module.android;

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
//        List<AllModel> list = new ArrayList<>();
//        for(int i=0;i<30;i++){
//            AllModel model = new AllModel();
//            model.setDesc("title");
//            model.setWho("who");
//            model.setPublishedAt("2017/1/8");
//            list.add(model);
//        }
        recyclerView.setAdapterWithProgress(adapter);
        adapter.setMore(R.layout.view_more,this);
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    @Override
    public void onMoreShow() {

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
        page = 0;
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
                adapter.clear();
                adapter.addAll(allModels);
                adapter.notifyDataSetChanged();
                recyclerView.showRecycler();
                page = 1;
            }
        };
        RetrofitHttpHelper.getAndroid(subscriber,NUMBER,page);
    }
}
