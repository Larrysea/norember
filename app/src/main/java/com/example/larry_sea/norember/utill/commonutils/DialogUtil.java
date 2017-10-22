package com.example.larry_sea.norember.utill.commonutils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.larry_sea.norember.R;

import java.util.Calendar;

/**
 * Created by Larry-sea on 10/9/2016.
 * 获取dialog   常用工具类
 */

public class DialogUtil {


    /*
    *
    * 获取一个配置好的dialog
    *
    * @param context 设备上下文
    * @param title   dialog标题
    * @param messageTip  消息内容
    * @param hasNegativeBtn 是否有取消按钮
    * @param hasPositiveBtn 是否有确认按钮
    *
    * */

    static Dialog mDialog;         //mdialog
    boolean result;                //点击之后的返回结果
    static AppCompatActivity mActivity;

    static public AlertDialog.Builder getDialog(Context context, String title, String messageTip, boolean hasNegativeBtn, boolean hasPositiveBtn) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(messageTip);
        if (hasNegativeBtn) {
            builder.setNegativeButton("取消", null);
        }
        if (hasPositiveBtn) {
            builder.setPositiveButton("确定", null);
        }
        builder.show();
        return builder;
    }


    /*
    *
    * getDialog
    * @param hasNegativeBtn是否有取消button
    * @param hasPositiveBtn是否有确定按钮
    * @param positiveClickListener确定按钮的监听器
    * @param negativeClickListener取消按钮的监听器
    *
    * */
    static public AlertDialog getDialog(Context context, int title, int messageTip,
                                        boolean hasNegativeBtn, boolean hasPositiveBtn, DialogInterface.OnClickListener positiveClickListener, DialogInterface.OnClickListener negativeClickListener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(messageTip);
        if (hasNegativeBtn) {
            builder.setNegativeButton("取消", negativeClickListener);
        }
        if (hasPositiveBtn) {
            builder.setPositiveButton("确定", positiveClickListener);
        }
        return builder.show();
    }


    /*
    *
    * 得到进度dialog
    *
    * */
    static public ProgressDialog getProgressDialog(Context context, int title, int infoMessage) {
        return ProgressDialog.show(context, context.getResources().getString(title), context.getResources().getString(infoMessage));
    }

    /*
    * 参数是string类型的dialog
    * */
    static public ProgressDialog getProgressDialog(Context context, String title, String infoMessage) {
        return ProgressDialog.show(context, title, infoMessage);
    }


    /*
    * 获取时间的datepickerdialog
    *
    * */
    static public DatePickerDialog getDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener dateSetListener) {

        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }


    /*
    * 显示是否保存信息的dialog
    *
    * */
    static public void showWetherSaveDialog(Context context, DialogInterface.OnClickListener saveClickListener, @Nullable DialogInterface.OnClickListener negativeClickListener) {
        mActivity = ((AppCompatActivity) context);
        if (negativeClickListener == null) {
            mDialog = DialogUtil.getDialog(context, R.string.save_change, R.string.is_save_change, true, true, saveClickListener, mnegativeClickListener);
        } else {
            mDialog = DialogUtil.getDialog(context, R.string.save_change, R.string.is_save_change, true, true, saveClickListener, negativeClickListener);

        }
        mDialog.show();
    }

    /*
    * 显示是否删除的dialog
    *
    * */
    static public void showDelteDialog(Context context, DialogInterface.OnClickListener positiveClickListener, @Nullable DialogInterface.OnClickListener negativeClickListener) {
        if (negativeClickListener == null) {
            mDialog = DialogUtil.getDialog(context, R.string.delete, R.string.is_delete, true, true, positiveClickListener, mnegativeClickListener);
        } else {
            mDialog = DialogUtil.getDialog(context, R.string.delete, R.string.is_delete, true, true, positiveClickListener, negativeClickListener);
        }
        mDialog = DialogUtil.getDialog(context, R.string.delete, R.string.is_delete, true, true, positiveClickListener, negativeClickListener);
        mDialog.show();
    }


    /*
    * 取消按钮的监听器
    *
    * */
    static DialogInterface.OnClickListener mnegativeClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            if (mActivity != null)
                mActivity.onBackPressed();
        }
    };


}
