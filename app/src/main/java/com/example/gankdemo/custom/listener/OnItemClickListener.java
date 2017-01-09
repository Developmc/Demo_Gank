package com.example.gankdemo.custom.listener;

import android.view.View;

/**recyclerView点击回调
 * Created by clement on 17/1/9.
 */

public interface OnItemClickListener<T> {
    void onItemClick(View itemView,int position);
}
