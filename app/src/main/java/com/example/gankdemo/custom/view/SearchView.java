package com.example.gankdemo.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gankdemo.R;
import com.example.gankdemo.custom.listener.OnLayoutClickListener;
import com.example.gankdemo.custom.listener.OnSearchClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**自定义搜索控价
 * Created by clement on 17/1/12.
 */

public class SearchView extends LinearLayout {
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.rootView)
    LinearLayout itemLayout;
    private OnSearchClickListener onSearchClickListener;
    private OnLayoutClickListener onLayoutClickListener;
    public SearchView(Context context) {
        this(context,null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }
    private void initView(Context context, AttributeSet attrs){
        //获取自定义的值
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,R.styleable.SearchView,0,0);
        String hintString = "" ;
        int textColor,hintColor,imageColor;
        boolean hasFocusable = false ;
        try{
            hintString = array.getString(R.styleable.SearchView_hint) ;
            textColor = array.getColor(R.styleable.SearchView_textColor,
                    ContextCompat.getColor(context, R.color.white));
            hintColor = array.getColor(R.styleable.SearchView_hintColor,
                    ContextCompat.getColor(context, R.color.white));
            imageColor = array.getColor(R.styleable.SearchView_imageColor,
                    ContextCompat.getColor(context, R.color.white));
            hasFocusable = array.getBoolean(R.styleable.SearchView_hasFocusable, false);
        }finally {
            array.recycle();
        }
        //获取布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_search_layout,this,true);
        ButterKnife.bind(this,view);
        iv_search.setColorFilter(imageColor);
        //设置点击事件
        iv_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSearchClickListener!=null){
                    onSearchClickListener.onSearchClick(view);
                }
            }
        });
        iv_clear.setColorFilter(imageColor);
        iv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //清空搜索内容
                et_content.setText("");
            }
        });
        if(hasFocusable){
            et_content.setFocusable(true);
            et_content.setFocusableInTouchMode(true);
            et_content.requestFocus();
        }
        et_content.setTextColor(textColor);
        et_content.setHint(hintString);
        et_content.setHintTextColor(hintColor);
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString();
                //如果输入了内容
                if(!content.isEmpty()){
                    iv_clear.setVisibility(View.VISIBLE);
                }
                else{
                    iv_clear.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        this.onSearchClickListener = onSearchClickListener;
    }

    public void setOnLayoutClickListener(OnLayoutClickListener onLayoutClickListener) {
        this.onLayoutClickListener = onLayoutClickListener;
    }

    public EditText getEtContent(){
        return et_content;
    }

    public ImageView getSearchIcon(){
        return iv_search;
    }
    public LinearLayout getItemLayout(){
        return  itemLayout;
    }

    /**
     * 不可编辑，那么整个布局只有一个点击事件
     */
    public void setEditable(boolean isEdit){
        if(isEdit){
            return ;
        }
        //不可编辑
        et_content.setFocusable(false);
        //当EditText失去焦点时，才会相应OnClickListener
        et_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onLayoutClickListener!=null){
                    onLayoutClickListener.onLayoutClick(view);
                }
            }
        });
        //设置点击事件
        iv_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onLayoutClickListener!=null){
                    onLayoutClickListener.onLayoutClick(view);
                }
            }
        });
    }
}
