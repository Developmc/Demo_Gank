package com.example.gankdemo.module.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gankdemo.R;
import com.example.gankdemo.constants.SPUConstant;
import com.example.gankdemo.model.AllModel;
import com.example.gankdemo.util.SPUtil;
import com.example.gankdemo.util.TimeUtil;
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
    //判断是否显示缩略图
    private boolean isShowIcon = false ;
    private com.example.gankdemo.custom.listener.OnItemClickListener<AllModel> onItemClickListener;
    public RecyclerViewAdapter(Context context) {
        super(context);
        this.mContext = context;
        isShowIcon = (boolean)SPUtil.get(mContext, SPUConstant.SHOW_THUMBNAIL,false);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_android,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<AllModel>{
        @BindView(R.id.iv_icon)
        ImageView iv_icon;
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
            if(isShowIcon){
                iv_icon.setVisibility(View.VISIBLE);
                //拉去图片
                Glide.with(mContext.getApplicationContext())
                        .load(getImageUrl(data))
                        .placeholder(R.drawable.ic_default)
                        .crossFade()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_icon);
            }
            else{
                iv_icon.setVisibility(View.GONE);
            }
            tv_title.setText(data.getDesc());
            tv_name.setText(data.getWho());
            tv_date.setText(TimeUtil.getDate(data.getPublishedAt()));
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

    /**刷新显示
     * @param isShowIcon
     */
    public void refreshShow(boolean isShowIcon){
        this.isShowIcon = isShowIcon;
        notifyDataSetChanged();
    }

    /**获取缩略图的url,拉去想要的尺寸
     * @param data
     * @return
     */
    private String getImageUrl(@NonNull AllModel data){
        String temp = "?imageView2/0/w/100";
        if(data.getImages()!=null && !data.getImages().isEmpty()){
            return data.getImages().get(0)+temp;
        }
        return "";
    }

    /**更新content
     * @param mContext
     */
    @Override
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}
