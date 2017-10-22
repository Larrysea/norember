package com.example.larry_sea.norember.presenter;

import android.content.Context;

import com.example.larry_sea.norember.callback_interface.modeal_interface.StorageItemModelInterface;
import com.example.larry_sea.norember.callback_interface.view_interface.MainActivityViewInterface;
import com.example.larry_sea.norember.entity.base_entity.StorageItemTypeEntity;
import com.example.larry_sea.norember.impl.StorageItemModealImpl;

import java.util.List;

/**
 * Created by Larry-sea on 2016/9/15.
 */
public class MainAcitivityPresenter {

    MainActivityViewInterface mainActivityViewInterface;                   //主activity 接口
    StorageItemModelInterface storageItemModelInterface;                   //模型的引用

    Context mcontext;

    public MainAcitivityPresenter(MainActivityViewInterface mainActivityViewInterface) {
        this.mainActivityViewInterface = mainActivityViewInterface;
        this.mcontext = (Context) mainActivityViewInterface;
        this.storageItemModelInterface = new StorageItemModealImpl(mcontext);
    }


    /*
    *
    * 初始化所有大类型数据 包括其中小类型的数据
    * */
    public void initData() {

        List<StorageItemTypeEntity> storageItemList;
        //获取所有类型的数据
        storageItemList = storageItemModelInterface.getAllItemType();
        if (storageItemList.size() != 0) {
            mainActivityViewInterface.loadFragment(1, storageItemList);
        } else  //数据为空所以显示一个空的类型
        {
            mainActivityViewInterface.loadFragment(0, null);
        }

    }


    /*
    *
    * 关闭realm释放资源  mainactivity中的ondestort调用
    * */
    public void closeRealm() {
        if (this.storageItemModelInterface != null) {
            this.storageItemModelInterface.closeRealm();

        }

    }


    /*
    *
    * 更新mainfragment 中的数据
    *
    * */
    public void updateMainFragment() {

        List<StorageItemTypeEntity> storageItemList;
        //获取所有类型的数据
        storageItemList = storageItemModelInterface.getAllItemType();
        if (storageItemList != null) {
            mainActivityViewInterface.loadFragment(1, storageItemList);
        } else  //数据为空所以显示一个空的类型
        {
            mainActivityViewInterface.loadFragment(0, null);
        }

    }


}
