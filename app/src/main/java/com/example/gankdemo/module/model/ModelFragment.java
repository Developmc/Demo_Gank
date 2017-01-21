package com.example.gankdemo.module.model;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.LazyFragment;
import com.example.gankdemo.constants.BundleConstant;
import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.custom.listener.OnItemClickListener;
import com.example.gankdemo.http.manager.RetrofitHttpHelper;
import com.example.gankdemo.http.subscriber.BaseSubscriber;
import com.example.gankdemo.model.AllModel;
import com.example.gankdemo.module.detail.DetailFragment;
import com.example.gankdemo.module.home.MainFragment;
import com.example.gankdemo.module.home.type.ModelType;
import com.example.gankdemo.module.setting.observer.ActionType;
import com.example.gankdemo.module.setting.observer.IListener;
import com.example.gankdemo.module.setting.observer.ListenerManager;
import com.example.gankdemo.util.DensityUtil;
import com.example.gankdemo.util.NightModeUtil;
import com.example.gankdemo.util.SPUtil;
import com.example.gankdemo.util.SnackbarUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;

/**ModelFragment
 * Created by clement on 17/1/9.
 */

public class ModelFragment extends LazyFragment implements RecyclerArrayAdapter.OnMoreListener
        ,RecyclerArrayAdapter.OnNoMoreListener,
        android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener,
        IListener {
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private static final int NUMBER = 10;
    private int page = 0;
    //用于标记类型
    private ModelType modelType;
    public ModelFragment(){}
    public ModelFragment(ModelType modelType){
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
                Bundle bundle = new Bundle();
                bundle.putString(BundleConstant.URL,adapter.getDatas().get(position).getUrl());
                bundle.putString(BundleConstant.TITLE,adapter.getDatas().get(position).getDesc());
                switchFragment(MainFragment.class.getSimpleName(),new DetailFragment(),
                        DetailFragment.class.getSimpleName(),bundle);
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setMore(R.layout.view_more,this);
        adapter.setNoMore(R.layout.view_nomore,this);
        recyclerView.setRefreshListener(this);
        recyclerView.setEmptyView(R.layout.view_empty);
        recyclerView.setErrorView(R.layout.view_error);
        recyclerView.setProgressView(R.layout.view_progress);
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
                SnackbarUtil.show(recyclerView,e.getMessage());
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
        SnackbarUtil.show(recyclerView,"onNoMoreShow");
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
                SnackbarUtil.show(recyclerView,e.getMessage());
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
     * @param actionType
     */
    @Override
    public void notifyAllFragment(ActionType actionType) {
        //如果是缩略图更改
        if(actionType==ActionType.thumbnail){
            boolean isShowIcon = (boolean)SPUtil.get(getContext(), SPUConstant.SHOW_THUMBNAIL,false);
            //刷新adapter
            adapter.refreshShow(isShowIcon);
        }
        //如果是夜间模式切换
        else if(actionType==ActionType.nightMode){
            //修改recyclerView的背景色
            int childCount = recyclerView.getRecyclerView().getChildCount();
            for(int index=0;index<childCount;index++){
                ViewGroup childView = (ViewGroup)recyclerView.getRecyclerView().getChildAt(index);
                childView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ripple_background));
                TextView tvTitle = ((TextView)childView.findViewById(R.id.tv_title));
                TextView tvName = ((TextView)childView.findViewById(R.id.tv_name));
                TextView tvDate = ((TextView)childView.findViewById(R.id.tv_date));
                //当recyclerView没有数据时，对应的布局中没有这三个textView
                if(tvTitle!=null){
                    tvTitle.setTextColor(NightModeUtil.getTextColor(getContext()));
                    tvName.setTextColor(NightModeUtil.getTextColor(getContext()));
                    tvDate.setTextColor(NightModeUtil.getTextColor(getContext()));
                }
            }
            //让 RecyclerView 缓存在 Pool 中的 Item 失效
            Class<RecyclerView> recyclerViewClass = RecyclerView.class;
            try {
                Field declaredField = recyclerViewClass.getDeclaredField("mRecycler");
                declaredField.setAccessible(true);
                Method declaredMethod = Class.forName(RecyclerView.Recycler.class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(declaredField.get(recyclerView.getRecyclerView()), new Object[0]);
                RecyclerView.RecycledViewPool recycledViewPool = recyclerView.getRecyclerView().getRecycledViewPool();
                recycledViewPool.clear();

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //更改完模式后，重新设置
            adapter.setMore(R.layout.view_more,this);
            adapter.setNoMore(R.layout.view_nomore,this);
            recyclerView.setEmptyView(R.layout.view_empty);
            recyclerView.setErrorView(R.layout.view_error);
            recyclerView.setProgressView(R.layout.view_progress);
        }

    }
}
