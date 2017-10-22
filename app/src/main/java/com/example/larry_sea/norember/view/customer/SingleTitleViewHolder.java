package com.example.larry_sea.norember.view.customer;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.larry_sea.norember.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 9/25/2016.
 * <p>
 * 单个标题的viewholder
 */

public class SingleTitleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.id_storage_singale_item_title_tv)
    public TextView titleTv;
    @BindView(R.id.id_storage_singale_item_icon_iv)
    public ImageView iconIv;

    public SingleTitleViewHolder(View itemView, Activity activity) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
