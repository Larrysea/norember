package com.example.larry_sea.norember.callback_interface;

import android.view.View;

/**
 * Created by Larry-sea on 2016/9/9.
 * <p>
 * recyclerview的回调监听接口
 */
public interface RecyclerviewClickInterface {
    void onClick(View view, int position);                   //监听点击回调方法

    void onItemLongClick(View view, int position);            //长按监听回调方法
}
