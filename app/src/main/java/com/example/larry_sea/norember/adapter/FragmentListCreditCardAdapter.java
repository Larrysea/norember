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
 * Created by Larry-sea on 10/9/2016.
 */

public class FragmentListCreditCardAdapter extends RecyclerView.Adapter<DoubleTitleViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    Context mcontext;
    RealmResults<CreditCard> mrealmResults;
    RecyclerviewClickInterface mrecyclerViewInterface; //监听回调借口

    public FragmentListCreditCardAdapter(Context context, RealmResults<CreditCard> realmResults) {
        mcontext = context;
        mrealmResults = realmResults;
    }

    @Override
    public DoubleTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DoubleTitleViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.double_title_storage_item, parent, false), (Activity) mcontext);
    }

    @Override
    public void onBindViewHolder(final DoubleTitleViewHolder holder, int position) {
        holder.titleTv.setText(mrealmResults.get(position).getAccountHolder());
        holder.subTitleTv.setText(mrealmResults.get(position).getCardNumber());
        Picasso.with(mcontext).load(R.mipmap.ic_type_bank_card_medium).into(holder.iconIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mrecyclerViewInterface.onClick(v, holder.getAdapterPosition());
            }
        });
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
        TextView headerTv = (TextView) holder.itemView;
        if (mrealmResults.get(position).getCardNumber() != null) {
            headerTv.setText(mrealmResults.get(position).getCardNumber().substring(0, 1));
        } else {
            headerTv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mrealmResults.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnClickInterface(RecyclerviewClickInterface recyclerClickInterface) {
        this.mrecyclerViewInterface = recyclerClickInterface;
    }

}
