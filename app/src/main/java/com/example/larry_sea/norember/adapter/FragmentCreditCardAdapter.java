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
import com.example.larry_sea.norember.entity.account_entity.CreditCard;
import com.example.larry_sea.norember.view.customer.DoubleTitleViewHolder;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import io.realm.RealmResults;

/**
 * Created by Larry-sea on 9/28/2016.
 * <p>
 * 信用卡listfragment adapter
 */

public class FragmentCreditCardAdapter extends RecyclerView.Adapter<DoubleTitleViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    RealmResults<CreditCard> mdatas;
    Context mcontext;
    RecyclerviewClickInterface mrecyclerClickInterface;


    public FragmentCreditCardAdapter(Context context, RealmResults<CreditCard> creditCards) {
        this.mcontext = context;
        this.mdatas = creditCards;

    }

    @Override
    public DoubleTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DoubleTitleViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.double_title_storage_item, parent, false), (Activity) mcontext);
    }

    @Override
    public void onBindViewHolder(DoubleTitleViewHolder holder, int position) {
        if (holder != null) {
            holder.titleTv.setText(mdatas.get(position).getCreditCardName());
            holder.subTitleTv.setText(mdatas.get(position).getCardNumber());
            Picasso.with(mcontext).load(R.mipmap.ic_type_credit_card_medium).into(holder.iconIv);
        }

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
        if (mdatas.get(position).getCardNumber() != null) {
            textView.setText(mdatas.get(position).getCreditCardName().substring(0, 1));
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
