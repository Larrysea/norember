package com.example.larry_sea.norember.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.RecyclerviewClickInterface;
import com.example.larry_sea.norember.constants.ResourceConstants;
import com.example.larry_sea.norember.entity.account_entity.LoginInfor;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 2016/9/7.
 * <p>
 * <p>
 * 基础 recyclerviewfragment
 */
public class AddWebSiteFragment extends Fragment {

    @BindView(R.id.id_add_web_site_recyclerview)
    RecyclerView recyclerView;
    List<LoginInfor> mdatas;
    CommonAdapter<LoginInfor> mcommonAdapter;
    RecyclerviewClickInterface mrecyclerViewInterface;
    String[] webSiteNameArray;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_web_site, container, false);
        ButterKnife.bind(this, view);
        initDatas();
        mrecyclerViewInterface = (RecyclerviewClickInterface) getActivity();                    //初始化回调接口
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mcommonAdapter = new CommonAdapter<LoginInfor>(getContext(), R.layout.web_site_item, mdatas) {
            @Override
            protected void convert(ViewHolder holder, LoginInfor loginInfor, int position) {
                holder.setText(R.id.id_web_site_name_tv, loginInfor.getUrl());
                Picasso.with(getActivity()).load(loginInfor.getIconResId()).into((ImageView) holder.getView(R.id.id_web_site_icon_iv));

            }
        };
        mcommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("web_site_url", mdatas.get(position).getUrl());
                bundle.putString("web_site_name", mdatas.get(position).getWebSiteName());
                transferDataToActivity(bundle);
                mrecyclerViewInterface.onClick(view, position);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(mcommonAdapter);
        return view;
    }


    /*
    * 初始化website数据
    *
    * */
    public void initDatas() {
        if (mdatas == null) {
            mdatas = new ArrayList<LoginInfor>();
            webSiteNameArray = getResources().getStringArray(R.array.id_string_website_name);
        }
        for (int position = 0; position < ResourceConstants.webSiteArray.length; position++) {
            LoginInfor loginInfor = new LoginInfor();
            loginInfor.setUrl(ResourceConstants.webSiteArray[position]);
            loginInfor.setIconResId(ResourceConstants.webSiteIconArray[position]);
            loginInfor.setWebSiteName(webSiteNameArray[position]);
            mdatas.add(loginInfor);
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /*
    * 给宿主activity传递数据
    * */
    public void transferDataToActivity(Bundle bundle) {

        if (getActivity() instanceof transferData) {
            ((transferData) getActivity()).transferData(bundle);
        }

    }

    public interface transferData {
        void transferData(Bundle webSiteInfor);

    }


}
