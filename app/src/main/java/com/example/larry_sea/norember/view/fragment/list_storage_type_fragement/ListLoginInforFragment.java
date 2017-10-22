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
import com.example.larry_sea.norember.adapter.FragmentLoginInforAdapter;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.callback_interface.RecyclerviewClickInterface;
import com.example.larry_sea.norember.entity.account_entity.LoginInfor;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Larry-sea on 9/28/2016.
 */

public class ListLoginInforFragment extends Fragment implements RecyclerviewClickInterface {
    @BindView(R.id.id_fragment_sticky_recyclerview)
    RecyclerView mrecyclerview;
    RealmResults<LoginInfor> realmResults;
    FragmentLoginInforAdapter stickListFragmentAdapter;
    int fragmentType;                                     // framgment的类型
    Realm mrealm;                                         // realm
    FragmentInforActivityInterface mfragmentInterface;    // 通知接口


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sticky_recyclerview, container, false);
        ButterKnife.bind(this, view);
        mrealm = Realm.getDefaultInstance();
        initData(realmResults);
        stickListFragmentAdapter = new FragmentLoginInforAdapter(getActivity(), realmResults);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mrecyclerview.addItemDecoration(new StickyRecyclerHeadersDecoration(stickListFragmentAdapter));
        stickListFragmentAdapter.setOnClickListener(this);
        mrecyclerview.setAdapter(stickListFragmentAdapter);
        return view;
    }


    /*
    * 初始化data数据
    *
    * */
    public void initData(RealmResults<LoginInfor> bankCardRealmResults) {

        mrealm = Realm.getDefaultInstance();
        mrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults = realm.where(LoginInfor.class).findAll();
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mrealm.close();

    }

    @Override
    public void onClick(View view, int position) {
        Message message = new Message();
        message.obj = realmResults.get(position).getAccountName();
        mfragmentInterface.receiveMessage(message);
    }

    @Override
    public void onItemLongClick(View view, int position) {


    }

    /*
    * 通知接口 实现放为listbankcard activity
    *
    * */
    public void setInforActivityInterface(FragmentInforActivityInterface fragmentInforActivityInterface) {
        mfragmentInterface = fragmentInforActivityInterface;
    }

    public void updateUi() {
        stickListFragmentAdapter.notifyDataSetChanged();
    }

}
