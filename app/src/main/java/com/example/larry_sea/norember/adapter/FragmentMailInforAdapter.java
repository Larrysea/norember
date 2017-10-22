package com.example.larry_sea.norember.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.RecyclerviewClickInterface;
import com.example.larry_sea.norember.entity.account_entity.MailInfor;
import com.example.larry_sea.norember.view.customer.DoubleTitleViewHolder;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import io.realm.RealmResults;

/**
 * Created by Larry-sea on 9/28/2016.
 * <p>
 * <p>
 * mail的listfragment adapter
 */

public class FragmentMailInforAdapter extends RecyclerView.Adapter<DoubleTitleViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    RealmResults<MailInfor> mdatas;
    Context mcontext;
    RecyclerviewClickInterface mrecyclerClickInterface;


    public FragmentMailInforAdapter(Context context, RealmResults<MailInfor> mailInfors) {
        this.mcontext = context;
        this.mdatas = mailInfors;
    }

    @Override
    public DoubleTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DoubleTitleViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.double_title_storage_item, parent, false), (Activity) mcontext);
    }

    @Override
    public void onBindViewHolder(DoubleTitleViewHolder holder, final int position) {
        if (holder != null) {
            holder.titleTv.setText(mdatas.get(position).getUserName());
            holder.subTitleTv.setText(mdatas.get(position).getWebSite());
            Picasso.with(mcontext).load(R.mipmap.ic_type_mail_medium).into(holder.iconIv);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mrecyclerClickInterface.onClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.sticky_recycler_header, parent, false);
        return new RecyclerView.ViewHolder(view) {

        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        if (mdatas.get(position).getUserName() != null) {
            textView.setText(mdatas.get(position).getUserName().substring(0, 1));
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnClickListener(RecyclerviewClickInterface recyclerviewClickInterface) {
        this.mrecyclerClickInterface = recyclerviewClickInterface;
    }
}
