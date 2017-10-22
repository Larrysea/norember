package com.example.larry_sea.norember.callback_interface.modeal_interface;

import com.example.larry_sea.norember.entity.base_entity.BaseStorageItemEntity;
import com.example.larry_sea.norember.entity.base_entity.StorageItemTypeEntity;

import java.util.List;

/**
 * Created by Larry-sea on 2016/9/15.
 * <p>
 * <p>
 * 存储的基类型的回调接口
 */
public interface StorageItemModelInterface {

    List<BaseStorageItemEntity> getAllDataFromDB();                     //从数据获取所有数据

    void saveDataToTheDB(BaseStorageItemEntity storageData);            //保存数据到数据库

    List<StorageItemTypeEntity> getAllItemType();                       //获取所有大类的数据

    void closeRealm();                                                  //关闭realm

}
