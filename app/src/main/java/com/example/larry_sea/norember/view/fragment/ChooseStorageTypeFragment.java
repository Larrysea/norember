package com.example.larry_sea.norember.view.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.entity.base_entity.SmallTypeEntity;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 9/24/2016.
 * <p>
 * <p>
 * 选择类型的fragment
 */

public class ChooseStorageTypeFragment extends Fragment {

    @BindView(R.id.id_base_fragment_recyclerview)
    RecyclerView idBaseFragmentRecyclerview;
    ArrayList<SmallTypeEntity> mdatas;
    CommonAdapter<SmallTypeEntity> madapter;        //适配器
    FragmentInforActivityInterface fragmentInforActivityInterface;   //通知activity的接口
    int FragmentType;  //fragment类型

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_list_fragment, container, false);
        ButterKnife.bind(this, view);
        mdatas = (ArrayList<SmallTypeEntity>) getArguments().getSerializable("storageTypeData");
        FragmentType = getArguments().getInt("type");
        if (getActivity() instanceof FragmentInforActivityInterface) {
            fragmentInforActivityInterface = (FragmentInforActivityInterface) getActivity();
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        idBaseFragmentRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (mdatas != null) {
            madapter = new CommonAdapter<SmallTypeEntity>(getActivity(), R.layout.base_list_item, mdatas) {
                @Override
                protected void convert(ViewHolder holder, SmallTypeEntity smallTypeEntity, int position) {
                    holder.setText(R.id.id_base_list_item_tv, smallTypeEntity.getTypeName());
                    Picasso.with(getActivity()).load(smallTypeEntity.getIconSource()).into((ImageView) holder.getView(R.id.id_base_list_item_iv));
                }
            };
        }
        madapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Message message = new Message();
                message.arg1 = position;
                message.arg2 = getArguments().getInt("type");
                fragmentInforActivityInterface.receiveMessage(message);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        idBaseFragmentRecyclerview.setAdapter(madapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                ((AppCompatActivity) getActivity()).onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
