package com.example.larry_sea.norember.utill;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Larry-sea on 2016/9/6.
 * 复制粘贴工具
 */
public class CopyUtil {
    static ClipData mclipdata;            // 复制类型数据
    static ClipboardManager mclipManager; //复制管理器
    Context context;


    /*
    *
    * 设置复制内容
    * parm2 复制的文本
    *
    * */
    static public void setCopyData(Context context, String text) {
        if (mclipManager == null) {
            mclipManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        mclipdata = ClipData.newPlainText("text", text);
        mclipManager.setPrimaryClip(mclipdata);
    }

    /*
    *
    * 设置黏贴的内容
    *
    * 返回结果数据集
    * */
    static public ClipData.Item getCopyData(Context context, int type, int position) {
        if (mclipManager == null) {
            mclipManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        mclipdata = mclipManager.getPrimaryClip();
        return mclipdata.getItemAt(position);

    }


}
