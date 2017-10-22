package com.example.larry_sea.norember.view.fragment.list_storage_type_fragement;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.adapter.FragmentListSafeNoteAdapter;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.callback_interface.RecyclerviewClickInterface;
import com.example.larry_sea.norember.entity.account_entity.SafeNote;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Larry-sea on 10/9/2016.
 * <p>
 * 展示安全笔记的listfragment
 */

public class ListSafeNoteFragment extends Fragment implements RecyclerviewClickInterface {

    @BindView(R.id.id_fragment_list_base_password_recyclerview)
    RecyclerView idFragmentListBasePasswordRecyclerview;
    FragmentListSafeNoteAdapter madapter;
    Realm mrealm;
    RealmResults<SafeNote> mresults;
    FragmentInforActivityInterface mfragmentInforInterface;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_base_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public void initView() {

        madapter = new FragmentListSafeNoteAdapter(getActivity(), getData());
        idFragmentListBasePasswordRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        madapter.setOnClickListener(this);
        idFragmentListBasePasswordRecyclerview.setAdapter(madapter);
        idFragmentListBasePasswordRecyclerview.addItemDecoration(new StickyRecyclerHeadersDecoration(madapter));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }


    /*
    * 获取数据
    *
    * */
    public RealmResults<SafeNote> getData() {
        mrealm = Realm.getDefaultInstance();
        mrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mresults = realm.where(SafeNote.class).findAll();
            }
        });
        if (mresults.size() == 0)
            return null;
        return mresults;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mrealm.close();
    }

    @Override
    public void onClick(View view, int position) {
        Message message = new Message();
        message.obj = mresults.get(position).getItemTitle();
        mfragmentInforInterface.receiveMessage(message);

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    public void setInforActivityInterface(FragmentInforActivityInterface fragmentInforActivityInterface) {
        mfragmentInforInterface = fragmentInforActivityInterface;
    }

    public void updateUi() {
        madapter.notifyDataSetChanged();
    }

}
