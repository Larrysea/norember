package com.example.larry_sea.norember.callback_interface.view_interface;

import com.example.larry_sea.norember.entity.base_entity.StorageItemTypeEntity;

import java.util.List;

/**
 * Created by Larry-sea on 2016/9/15.
 * <p>
 * <p>
 * mainactivity view的通讯接口
 */
public interface MainActivityViewInterface {
    /*
    *
    * 参数类型表示 如果是1则表示有数据可以显示，直接显示mainfragment 如果是0 则表示数据为空则显示空白的fragment
    *
    * 实现方mainactivity
    * */
    void loadFragment(int fragmentType, List<StorageItemTypeEntity> storageItemList);


}
