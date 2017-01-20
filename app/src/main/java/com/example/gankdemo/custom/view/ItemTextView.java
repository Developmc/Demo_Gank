package com.example.gankdemo.custom.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.example.gankdemo.R;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**抽取布局
 * Created by clement on 16/12/15.
 */

public class ItemTextView extends RelativeLayout {
    @BindView(R.id.tvLabel)
    AppCompatTextView tvLabel;
    @BindView(R.id.tvValue)
    AppCompatTextView tvValue;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.lineView)
    View lineView;
    public ItemTextView(Context context) {
        this(context,null);
    }

    public ItemTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(final Context context){
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_item_text_view,this,true);
        ButterKnife.bind(this,rootView);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public AppCompatTextView getTvLabel() {
        return tvLabel;
    }

    public AppCompatTextView getTvValue() {
        return tvValue;
    }

    public RelativeLayout getItemLayout(){
        return layout;
    }

    public View getLineView(){
        return lineView;
    }

    /**设置左边的图标
     * @param imageId
     */
    public void setLeftImage(int imageId){
        Drawable leftDrawable = ContextCompat.getDrawable(getContext(),imageId);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        tvLabel.setCompoundDrawables(leftDrawable,null,null,null);
    }

}
