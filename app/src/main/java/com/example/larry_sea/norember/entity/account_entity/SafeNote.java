package com.example.larry_sea.norember.entity.account_entity;

import com.example.larry_sea.norember.entity.base_entity.ImagePath;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/2.
 * <p>
 * 安全备注
 */
public class SafeNote extends RealmObject {
    String itemTitle;                   //密码item标题
    String noteContent;                    //密码

    int iconResId;                      //资源文件id
    RealmList<ImagePath> imagePath;     // 图片存放地址
    String category;                    //目录

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    String uuid;                        //唯一id


    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public RealmList<ImagePath> getImagePath() {
        return imagePath;
    }

    public void setImagePath(RealmList<ImagePath> imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }


}
