package com.example.larry_sea.norember.factory;

import android.content.Context;
import android.content.Intent;

import com.example.larry_sea.norember.view.activity.list_storage_type_activity.BankCardListActivity;
import com.example.larry_sea.norember.view.activity.list_storage_type_activity.BasePasswordListActivity;
import com.example.larry_sea.norember.view.activity.ChooseStorageTypeActivity;
import com.example.larry_sea.norember.view.activity.list_storage_type_activity.CreditCardListActivity;
import com.example.larry_sea.norember.view.activity.FeedBackActivity;
import com.example.larry_sea.norember.view.activity.list_storage_type_activity.IdCardListActivity;
import com.example.larry_sea.norember.view.activity.list_storage_type_activity.LoginInforListActivity;
import com.example.larry_sea.norember.view.activity.list_storage_type_activity.MailInforListActivity;
import com.example.larry_sea.norember.view.activity.storage_type_activity.SafeNoteActivity;
import com.example.larry_sea.norember.view.activity.list_storage_type_activity.SafeNoteListActivity;
import com.example.larry_sea.norember.view.activity.list_storage_type_activity.WifiInforListActivity;

/**
 * Created by Larry-sea on 2016/10/21.
 * <p>
 * intent的facetory类
 */

public class IntentFactory {


    public final static int BANK_CARD = 1;
    public final static int BASE_PASSWORD = 2;
    public final static int CREDIT_CARD = 3;
    public final static int DATA_BASE = 4;
    public final static int DRIVER_LICENSE = 5;
    public final static int IDENTITY_CARD = 6;
    public final static int LOGIN_INFOR = 7;
    public final static int MAIL_INFOR = 8;
    public final static int MEMBER_CARD = 9;
    public final static int PASS_PORT = 10;
    public final static int SAFE_NOTE = 11;
    public final static int SERVER = 12;
    public final static int SOCICAL_INSURANCE = 13;
    public final static int SOFT_WARE_LICENSE = 14;
    public final static int WIFI_INFOR = 15;
    public final static int SAFE_NOTE_INTENT = 16;
    public final static int FEED_BACK_INTENT = 17;
    public final static int CHOOSE_STORAGE_TYPE_INTENT = 18;


    /*
    *
    * 获取intent对象
    *
    * */
    static public Intent getListIntent(Context context, int intentType) {

        Intent intent = new Intent();
        switch (intentType) {
            case IntentFactory.BANK_CARD:
                intent.setClass(context, BankCardListActivity.class);
                break;
            case IntentFactory.BASE_PASSWORD:
                intent.setClass(context, BasePasswordListActivity.class);
                break;
            case IntentFactory.CREDIT_CARD:
                intent.setClass(context, CreditCardListActivity.class);
                break;
            case IntentFactory.IDENTITY_CARD:
                intent.setClass(context, IdCardListActivity.class);
                break;
            case IntentFactory.LOGIN_INFOR:
                intent.setClass(context, LoginInforListActivity.class);
                break;
            case IntentFactory.MAIL_INFOR:
                intent.setClass(context, MailInforListActivity.class);
                break;
            case IntentFactory.SAFE_NOTE:
                intent.setClass(context, SafeNoteListActivity.class);
                break;
            case IntentFactory.WIFI_INFOR:
                intent.setClass(context, WifiInforListActivity.class);
                break;
        }
        return intent;
    }


    /*
    *
    * 获取常用intent
    * @param intentType intent的类型
    * */
    static public Intent getIntent(Context context, int intentType) {
        Intent intent = new Intent();
        switch (intentType) {

            case SAFE_NOTE_INTENT:
                intent.setClass(context, SafeNoteActivity.class);
                break;
            case FEED_BACK_INTENT:
                intent.setClass(context, FeedBackActivity.class);
                break;
            case CHOOSE_STORAGE_TYPE_INTENT:
                intent.setClass(context, ChooseStorageTypeActivity.class);
                break;
        }
        return intent;

    }


}
