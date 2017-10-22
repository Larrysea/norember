package com.example.larry_sea.norember.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.base_entity.SafeFileDB;
import com.example.larry_sea.norember.task.DecipherFileAsyncTask;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Larry-sea on 10/11/2016.
 * 安全文件的fragment
 */

public class SafeFolderFragment extends Fragment {

    @BindView(R.id.id_fragment_sticky_recyclerview)
    RecyclerView idFragmentStickyRecyclerview;
    List<SafeFileDB> mdatas;
    CommonAdapter<SafeFileDB> mCommonAdapter;
    Realm mRealm;
    OrderedRealmCollection<SafeFileDB> realmCollection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sticky_recyclerview, container, false);
        ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();
        mdatas = new ArrayList<SafeFileDB>();
        initView();
        return view;
    }


    /*
    * 初始化view
    *
    * */
    public void initView() {
        if (initData().size() != 0) {
            transferRealmResultsToList(initData());
            mCommonAdapter = new CommonAdapter<SafeFileDB>(getActivity(), R.layout.double_title_storage_item, mdatas) {
                @Override
                protected void convert(ViewHolder holder, SafeFileDB safeFileDB, int position) {
                    holder.setText(R.id.id_double_storage_double_item_title_tv, safeFileDB.getFileName());
                    holder.setText(R.id.id_storage_double_item_sub_title_tv, safeFileDB.getFileSize());
                }
            };
            mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    DecipherFileAsyncTask decipherFileAsyncTask = new DecipherFileAsyncTask(getActivity());
                    decipherFileAsyncTask.execute(mdatas.get(position).getFileStoragePath(), "5944");


                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            idFragmentStickyRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            idFragmentStickyRecyclerview.setAdapter(mCommonAdapter);
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }


    public RealmResults<SafeFileDB> initData() {
        mRealm.beginTransaction();
        RealmResults<SafeFileDB> realmResults = mRealm.where(SafeFileDB.class).findAll();
        mRealm.commitTransaction();
        return realmResults;
    }


    /*
    * 将realmresults转换为list
    *
    * */
    public void transferRealmResultsToList(RealmResults<SafeFileDB> realmResults) {

        SafeFileDB safeFileDB;
        for (int position = 0; position < realmResults.size(); position++) {
            safeFileDB = realmResults.get(position);
            mdatas.add(safeFileDB);
        }

    }

}
