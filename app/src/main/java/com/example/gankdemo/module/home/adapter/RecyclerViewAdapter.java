package com.example.gankdemo.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gankdemo.R;
import com.example.gankdemo.model.AllModel;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**Android模块的adapter
 * Created by developmc on 17/1/8.
 */

public class RecyclerViewAdapter extends RecyclerArrayAdapter<AllModel> {
    private Context mContext;
    private com.example.gankdemo.custom.listener.OnItemClickListener<AllModel> onItemClickListener;
    public RecyclerViewAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_android,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<AllModel>{
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_name)
        TextView tv_name;
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
        public void setData(AllModel data) {
            super.setData(data);
            tv_title.setText(data.getDesc());
            tv_name.setText(data.getWho());
            tv_date.setText(data.getPublishedAt());
        }
    }

    /**设置监听器
     * @param onItemClickListener
     */
    public void setOnItemClickListener(
            com.example.gankdemo.custom.listener.OnItemClickListener<AllModel> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**获取数据源
     * @return
     */
    public List<AllModel> getDatas(){
        return mObjects;
    }
}
