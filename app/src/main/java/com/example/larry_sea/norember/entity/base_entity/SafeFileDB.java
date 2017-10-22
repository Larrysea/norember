package com.example.larry_sea.norember.entity.base_entity;

import io.realm.RealmObject;

/**
 * Created by Larry-sea on 10/11/2016.
 * <p>
 * 安全文件存储记录
 */

public class SafeFileDB extends RealmObject {
    String fileName;                //文件名
    String fileStoragePath;         //加密的文件存储路径
    String fileSize;                //文件大小

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileStoragePath() {
        return fileStoragePath;
    }

    public void setFileStoragePath(String fileStoragePath) {
        this.fileStoragePath = fileStoragePath;
    }
}
