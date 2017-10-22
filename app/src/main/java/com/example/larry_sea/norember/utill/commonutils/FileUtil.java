package com.example.larry_sea.norember.utill.commonutils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by Larry-sea on 10/7/2016.
 */

public class FileUtil {


    /*
    *
    * android 4.4以前的获取url 中的文件路径方式
    *
    *
    * */
    static String getFilePahtFromTheUrl(Context context, Uri uri) {
        String filename = null;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Audio.Media.DATA}, null, null, null);
            if (cursor.moveToFirst()) {
                filename = cursor.getString(0);
            }
        } else if (uri.getScheme().toString().compareTo("file") == 0)         //file:///开头的uri
        {
            filename = uri.toString();
            filename = uri.toString().replace("file://", "");
            //替换file://
            if (!filename.startsWith("/mnt")) {
                //加上"/mnt"头
                filename += "/mnt";
            }
        }
        return filename;
    }

    /*
    * 通用方法从uri中获取文件路径
    *
    * */
    static public String getFilePahtFromUri(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT > 19) {
            return GetPathFromUrikitkat.getPath(context, uri);
        } else {
            return getFilePahtFromTheUrl(context, uri);
        }


    }


    /*
    *
    *
    * 获取文件名
    * @param filePath  /afasdf/afasdf/affasdf/a.jpg
    *
    * @return a.jpg
    *
    *
    * */
    static public String getFileName(String filePath) {

        if (filePath != null) {
            List<String> multiResult = com.example.larry_sea.norember.utill.StringUtil.Muitlsplit(filePath, "/");
            return multiResult.get(multiResult.size() - 1);
        } else return null;

    }


    /*
    *
    * 获取文件路径
    * @param filePath     /afsdaf/afsdaf/afsad/a.jpg
    *
    * @return  /afsdaf/afsdaf/afsad/
    *
    *
    *
    * */
    static public String getFilePath(String filePath) {
        if (filePath != null) {
            return filePath.substring(0, filePath.lastIndexOf("/") + 1);  //加一是因为数组是从0开始计数
        } else
            return null;
    }

    /*
    * parm 文件名
    *
    * return 返回文件类型的后缀
    *
    * 示例 ： parm 新建文本文档.txt
    *
    * 返回结果是txt
    *
    *
    * */
    static public String getFileType(String filename) {

        String postFix = filename.substring(filename.lastIndexOf(".") + 1);
        return postFix;

    }

    /*
    * 向手机的自己文件目录下面存储文件保存文件
    *
    * /data/***
    *
    * */
    public void saveInternalFile(Context context, String fileName) {
        BufferedWriter writer;
        try {
            String path = context.getFilesDir().getAbsolutePath();
            File file = new File(path + "/etc");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file.getAbsoluteFile() + "/name.txt");
            FileOutputStream out = new FileOutputStream(file2);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            try {
                writer.write("123123");
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
    * 想手机的内部存储卡中存储文件
    *
    * */
    static public void saveInternalCard(String filePath, String fileContent) {
        String rootPath = Environment.getExternalStorageDirectory().getPath();
        rootPath += "/" + filePath;
        File file = new File(rootPath);
        BufferedWriter bufferedWriter;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(fileContent);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    * @param 文件地址
    * @return 将文件内容以String形式返回
    *
    * */
    static public String getFileContent(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                return StringUtil.inputStreamToString(fileInputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
