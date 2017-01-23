package com.example.gankdemo.module.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gankdemo.R;
import com.example.gankdemo.model.SearchModel;
import com.example.gankdemo.util.TimeUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**搜索模块的adapter
 * Created by developmc on 17/1/23.
 */

public class SearchRecyclerViewAdapter extends RecyclerArrayAdapter<SearchModel> {
    private Context mContext;
    private com.example.gankdemo.custom.listener.OnItemClickListener<SearchModel> onItemClickListener;
    public SearchRecyclerViewAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<SearchModel>{
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.tv_date)
        TextView tv_date;
        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(itemView,getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public void setData(SearchModel data) {
            super.setData(data);
            tv_title.setText(data.getDesc());
            tv_name.setText(data.getWho());
            tv_type.setText(data.getType());
            tv_date.setText(TimeUtil.getDate(data.getPublishedAt()));
        }
    }

    /**设置监听器
     * @param onItemClickListener
     */
    public void setOnItemClickListener(
            com.example.gankdemo.custom.listener.OnItemClickListener<SearchModel> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**获取数据源
     * @return
     */
    public List<SearchModel> getDatas(){
        return mObjects;
    }

    /**更新content
     * @param mContext
     */
    @Override
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}
