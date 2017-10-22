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
import com.example.larry_sea.norember.entity.account_entity.SafeNote;
import com.example.larry_sea.norember.view.customer.SingleTitleViewHolder;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import io.realm.RealmResults;

/**
 * Created by Larry-sea on 10/9/2016.
 * <p>
 * 安全笔记的list 的适配器
 */

public class FragmentListSafeNoteAdapter extends RecyclerView.Adapter<SingleTitleViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    //数据集
    RealmResults<SafeNote> basePasswordRealmResults;
    Context mcontext;
    RecyclerviewClickInterface mrecyclerViewInterface;

    public FragmentListSafeNoteAdapter(Context context, RealmResults<SafeNote> basePasswordsResults) {
        mcontext = context;
        basePasswordRealmResults = basePasswordsResults;
    }

    @Override
    public SingleTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleTitleViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.single_title_storage_item, parent, false), (Activity) mcontext);
    }

    @Override
    public void onBindViewHolder(final SingleTitleViewHolder holder, int position) {

        Picasso.with(mcontext).load(R.mipmap.ic_type_note_medium).into(holder.iconIv);
        holder.titleTv.setText(basePasswordRealmResults.get(position).getItemTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mrecyclerViewInterface.onClick(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        TextView textview = (TextView) holder.itemView;
        if (basePasswordRealmResults.get(position).getItemTitle() != null) {
            textview.setText(basePasswordRealmResults.get(position).getItemTitle().substring(0, 1));
        } else {
            textview.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return basePasswordRealmResults.size();
    }

    /*
    *
    * 设置监听器
    *
    * */
    public void setOnClickListener(RecyclerviewClickInterface recyclerInterface) {
        if (recyclerInterface != null) {
            this.mrecyclerViewInterface = recyclerInterface;
        }

    }


}
