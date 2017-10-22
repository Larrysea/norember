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
import com.example.larry_sea.norember.adapter.FragmentListCreditCardAdapter;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.callback_interface.RecyclerviewClickInterface;
import com.example.larry_sea.norember.entity.account_entity.CreditCard;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Larry-sea on 9/28/2016.
 * <p>
 * <p>
 * creditcard list fragment
 */

public class ListCreditCardFragment extends Fragment implements RecyclerviewClickInterface {

    @BindView(R.id.id_fragment_sticky_recyclerview)
    RecyclerView mrecyclerview;
    RealmResults<CreditCard> realmResults;

    int fragmentType;                                     // framgment的类型
    Realm mrealm;                                         // realm
    FragmentInforActivityInterface mfragmentInterface;    // 通知接口
    FragmentListCreditCardAdapter mlistCreditCardAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sticky_recyclerview, container, false);
        ButterKnife.bind(this, view);
        mrealm = Realm.getDefaultInstance();
        initData(realmResults);
        mlistCreditCardAdapter = new FragmentListCreditCardAdapter(getActivity(), realmResults);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mrecyclerview.addItemDecoration(new StickyRecyclerHeadersDecoration(mlistCreditCardAdapter));
        mlistCreditCardAdapter.setOnClickInterface(this);
        mrecyclerview.setAdapter(mlistCreditCardAdapter);
        return view;
    }


    /*
    * 初始化data数据
    *
    * */
    public void initData(RealmResults<CreditCard> bankCardRealmResults) {

        mrealm = Realm.getDefaultInstance();
        mrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults = realm.where(CreditCard.class).findAll();
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mrealm != null) {
            mrealm.close();
        }

    }

    @Override
    public void onClick(View view, int position) {
        Message message = new Message();
        message.obj = realmResults.get(position).getCardNumber();
        mfragmentInterface.receiveMessage(message);
    }

    @Override
    public void onItemLongClick(View view, int position) {


    }

    /*
    * 通知接口 实现放为listcreditCard activity
    *
    * */
    public void setInforActivityInterface(FragmentInforActivityInterface fragmentInforActivityInterface) {
        mfragmentInterface = fragmentInforActivityInterface;
    }


    /*
    *
    * 更新  ui
    *
    *
    * */
    public void updateUi() {
        mlistCreditCardAdapter.notifyDataSetChanged();
    }
}
