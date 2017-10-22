package com.example.larry_sea.norember.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.RecyclerviewClickInterface;
import com.example.larry_sea.norember.callback_interface.view_interface.MainFragmentViewInterface;
import com.example.larry_sea.norember.entity.base_entity.StorageItemTypeEntity;
import com.example.larry_sea.norember.presenter.MainFragmentPresenter;
import com.example.larry_sea.norember.view.customer.DividerItemDecoration;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 2016/9/13.
 * <p/>
 * <p/>
 * 主activity中的fragment
 */
public class Mainfragment extends Fragment implements MainFragmentViewInterface {


    @BindView(R.id.id_fragment_main_recyclerview)
    RecyclerView mrecyclerView;
    CommonAdapter<StorageItemTypeEntity> madapter;

    /*@BindView(R.id.id_fragment_main_progressview)
    ProgressView mprogreasview;*/

    List<StorageItemTypeEntity> mlist;
    MainFragmentPresenter mainFragmentPresenter; //中介

    RecyclerviewClickInterface mrecyclerInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mainFragmentPresenter = new MainFragmentPresenter(this);
        mainFragmentPresenter.init();
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mrecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mlist = (ArrayList<StorageItemTypeEntity>) getArguments().getSerializable("allTypeData");
        if (mlist != null) {
            madapter = new CommonAdapter<StorageItemTypeEntity>(getActivity(), R.layout.storage_item, mlist) {
                @Override
                protected void convert(ViewHolder holder, StorageItemTypeEntity storageItemTypeEntity, int position) {
                    holder.itemView.setBackgroundResource(R.drawable.recycler_bg);
                    Picasso.with(getActivity()).load(mlist.get(position).getIconPath()).into((ImageView) holder.getView(R.id.id_storage_item_icon_iv));
                    holder.setText(R.id.id_storage_item_title_tv, mlist.get(position).getItemTitle());
                    holder.setText(R.id.id_storage_item_account_tv, mlist.get(position).getAmount() + getString(R.string.individual));
                }
            };
            if (getActivity() instanceof RecyclerviewClickInterface) {
                madapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        if (getActivity() instanceof RecyclerviewClickInterface) {
                            mrecyclerInterface = (RecyclerviewClickInterface) getActivity();
                            mrecyclerInterface.onClick(view, mlist.get(position).getType());   //通知mainactivity

                        }
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });

            }
            ;
            mrecyclerView.setAdapter(madapter);
        }

        return view;
    }


    /*
    * 展示进度条
    * */
    @Override
    public void loading() {

        //   mprogreasview.start();


    }

    /*
    * 进度条消失
    *
    *
    * */
    @Override
    public void disLoading() {
        //  mprogreasview.stop();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFragmentPresenter.dismisProgressView();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


}
