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
import com.example.gankdemo.custom.view.listener.OnItemSelectedListener;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**抽取布局
 * Created by clement on 16/12/15.
 */

public class ItemSwitchButton extends RelativeLayout {
    @BindView(R.id.tvLabel)
    AppCompatTextView tvLabel;
    @BindView(R.id.sb_select)
    SwitchButton sbSelect;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.lineView)
    View lineView;
    private OnItemSelectedListener onItemSelectedListener;
    public ItemSwitchButton(Context context) {
        this(context,null);
    }

    public ItemSwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemSwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(final Context context){
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_item_switch_button,this,true);
        ButterKnife.bind(this,rootView);
        sbSelect.setChecked(false);
        sbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(onItemSelectedListener !=null){
                    onItemSelectedListener.onSelected(b);
                }
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public AppCompatTextView getTvLabel() {
        return tvLabel;
    }

    public SwitchButton getSwitchButton() {
        return sbSelect;
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
    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }
}
