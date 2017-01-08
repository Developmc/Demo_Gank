package com.example.gankdemo.module;


import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toolbar;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.model.AllModel;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MainFragment
 */
public class MainFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_main;
    }

    @Override
    public void initBehavior(View rootView) {
        initRecyclerView();
    }
    private void initRecyclerView(){
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //设置分割线
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY,1,32,32);
        recyclerView.addItemDecoration(itemDecoration);
        AndroidRecyclerViewAdapter adapter = new AndroidRecyclerViewAdapter(getContext());
        List<AllModel> list = new ArrayList<>();
        for(int i=0;i<30;i++){
            AllModel model = new AllModel();
            model.setDesc("title");
            model.setWho("who");
            model.setPublishedAt("2017/1/8");
            list.add(model);
        }
        recyclerView.setAdapter(adapter);
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
        recyclerView.showRecycler();
    }
}
