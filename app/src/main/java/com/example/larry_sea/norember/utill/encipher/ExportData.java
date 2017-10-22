package com.example.larry_sea.norember.utill.encipher;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.larry_sea.norember.constants.ResourceConstants;
import com.example.larry_sea.norember.entity.account_entity.BankCard;
import com.example.larry_sea.norember.entity.account_entity.CreditCard;
import com.example.larry_sea.norember.entity.account_entity.IdentityCard;
import com.example.larry_sea.norember.entity.account_entity.LoginInfor;
import com.example.larry_sea.norember.entity.account_entity.MailInfor;
import com.example.larry_sea.norember.entity.account_entity.SafeNote;
import com.example.larry_sea.norember.entity.account_entity.Wifiinfor;
import com.example.larry_sea.norember.manager.PasswordManager;
import com.example.larry_sea.norember.test.ClientEncryptionSamples;
import com.example.larry_sea.norember.utill.commonutils.FileUtil;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.example.larry_sea.norember.constants.ResourceConstants.AllUserAccount;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountElseInfo;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountId;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountNo;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountNumber;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountPwd;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountType;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountUrl;
import static com.example.larry_sea.norember.constants.ResourceConstants.address;
import static com.example.larry_sea.norember.constants.ResourceConstants.backManagerAccount;
import static com.example.larry_sea.norember.constants.ResourceConstants.backManagerAddress;
import static com.example.larry_sea.norember.constants.ResourceConstants.backManagerPassword;
import static com.example.larry_sea.norember.constants.ResourceConstants.country;
import static com.example.larry_sea.norember.constants.ResourceConstants.endTime;
import static com.example.larry_sea.norember.constants.ResourceConstants.idCardType;
import static com.example.larry_sea.norember.constants.ResourceConstants.ispAccount;
import static com.example.larry_sea.norember.constants.ResourceConstants.ispPassword;
import static com.example.larry_sea.norember.constants.ResourceConstants.mailAddressType;
import static com.example.larry_sea.norember.constants.ResourceConstants.noteContent;
import static com.example.larry_sea.norember.constants.ResourceConstants.noteTitle;
import static com.example.larry_sea.norember.constants.ResourceConstants.pwd_salt;
import static com.example.larry_sea.norember.constants.ResourceConstants.remarks;
import static com.example.larry_sea.norember.constants.ResourceConstants.safeNoteType;
import static com.example.larry_sea.norember.constants.ResourceConstants.signOrg;
import static com.example.larry_sea.norember.constants.ResourceConstants.startTime;
import static com.example.larry_sea.norember.constants.ResourceConstants.user_info;
import static com.example.larry_sea.norember.constants.ResourceConstants.user_pwd;
import static com.example.larry_sea.norember.constants.ResourceConstants.webSiteType;
import static com.example.larry_sea.norember.constants.ResourceConstants.webSiteName;
import static com.example.larry_sea.norember.constants.ResourceConstants.wifiType;

/**
 * Created by Larry-sea on 2016/11/2.
 * <p>
 * 导出数据工具类
 */

public class ExportData {


    Realm realm;
    JSONArray jsonArray;
    Context mContext;
    String TAG = ExportData.class.toString();
    final int SAVE_FINISH = 0X1;
    JSONObject parentJsonObject;     //父类jsonobject
    JSONObject childJsonObject;      //子类jsonobject

    public ExportData() {


    }


    /*
    * 从realm当中读取数据
    * */
    void getBankCardJsonArray() throws org.json.JSONException {
        realm.beginTransaction();
        RealmResults<BankCard> realmResults = realm.where(BankCard.class).findAll();
        realm.commitTransaction();
        BankCard bankCardObject;
        parentJsonObject = new JSONObject();
        childJsonObject = new JSONObject();
        for (int position = 0; position < realmResults.size(); position++) {
            bankCardObject = realmResults.get(position);
            parentJsonObject.put(accountId, bankCardObject.getUuid());
            parentJsonObject.put(accountType, ResourceConstants.bankCardType);
            parentJsonObject.put(accountNo, bankCardObject.getBankName());
            parentJsonObject.put(accountNumber, bankCardObject.getCardNumber());
            parentJsonObject.put(accountPwd, bankCardObject.getPassword());
            childJsonObject.put(ResourceConstants.ibanAccount, bankCardObject.getIbanName());
            childJsonObject.put(ResourceConstants.iban, bankCardObject.getIban());
            childJsonObject.put(ResourceConstants.bicOrSwift, bankCardObject.getBicOrswift());
            parentJsonObject.put(accountElseInfo, childJsonObject);
            jsonArray.add(parentJsonObject);
//            jsonArray.put(parentJsonObject);
        }
    }


    /*
    *
    * 获取信用卡jsonarray
    * */
    void getCreditJsonArray() throws org.json.JSONException {
        RealmResults<CreditCard> realmResults = realm.where(CreditCard.class).findAll();
        CreditCard creditCardObject;
        parentJsonObject = new JSONObject();
        childJsonObject = new JSONObject();
        for (int position = 0; position < realmResults.size(); position++) {
            creditCardObject = realmResults.get(position);
            parentJsonObject.put(accountId, creditCardObject.getUuid());
            parentJsonObject.put(accountNo, creditCardObject.getAccountHolder());
            parentJsonObject.put(accountType, ResourceConstants.creditCardType);
            parentJsonObject.put(accountNumber, creditCardObject.getCardNumber());
            childJsonObject.put(ResourceConstants.safeCode, creditCardObject.getSecurityNumber());
            childJsonObject.put(country, creditCardObject.getCountry());
            childJsonObject.put(address, creditCardObject.getAddress());
            childJsonObject.put(remarks, creditCardObject.getRemarks());
            parentJsonObject.put(accountElseInfo, childJsonObject);
            jsonArray.add(parentJsonObject);
//            jsonArray.put(parentJsonObject);
        }
    }


    /*
    * 获取身份证信息的jsonarray
    *
    * */
    void getIdcardJsonArray() throws org.json.JSONException {
        RealmResults<IdentityCard> realmResults = realm.where(IdentityCard.class).findAll();
        IdentityCard identityCardObject;
        parentJsonObject = new JSONObject();
        childJsonObject = new JSONObject();
        for (int position = 0; position < realmResults.size(); position++) {
            identityCardObject = realmResults.get(position);
            parentJsonObject.put(accountId, identityCardObject.getUuid());
            parentJsonObject.put(accountType, idCardType);
            parentJsonObject.put(accountNo, identityCardObject.getName());
            childJsonObject.put(signOrg, identityCardObject.getIssuingAuthority());
            childJsonObject.put(address, identityCardObject.getAddress());
            childJsonObject.put(country, identityCardObject.getCountry());
            childJsonObject.put(startTime, identityCardObject.getStartTermOfDate());
            childJsonObject.put(endTime, identityCardObject.getEndTermOfDate());
            parentJsonObject.put(accountElseInfo, childJsonObject);
            jsonArray.add(parentJsonObject);
//            jsonArray.put(parentJsonObject);
        }
    }

    /*
    * 获取登陆信息的jsonarray
    *
    *
    */
    void getLoginJsonArray() throws org.json.JSONException {
        RealmResults<LoginInfor> realmResults = realm.where(LoginInfor.class).findAll();
        LoginInfor loginInforObject;
        parentJsonObject = new JSONObject();
        childJsonObject = new JSONObject();
        for (int position = 0; position < realmResults.size(); position++) {
            loginInforObject = realmResults.get(position);
            parentJsonObject.put(accountId, loginInforObject.getUuid());
            parentJsonObject.put(accountType, webSiteType);
            parentJsonObject.put(accountNo, loginInforObject.getAccountName());
            parentJsonObject.put(accountPwd, loginInforObject.getPassword());
            parentJsonObject.put(accountUrl, loginInforObject.getUrl());
            childJsonObject.put(webSiteName, loginInforObject.getWebSiteName());
            childJsonObject.put(remarks, loginInforObject.getRemarks());
            parentJsonObject.put(accountElseInfo, childJsonObject);
            jsonArray.add(parentJsonObject);
//            jsonArray.put(parentJsonObject);
        }
    }


    /*
    * 获取邮箱的jsonArray
    *
    *
    * */
    void getMailJsonArray() throws org.json.JSONException {
        RealmResults<MailInfor> realmResults = realm.where(MailInfor.class).findAll();
        MailInfor mailInforObject;
        parentJsonObject = new JSONObject();
        childJsonObject = new JSONObject();
        for (int position = 0; position < realmResults.size(); position++) {
            mailInforObject = realmResults.get(position);
            parentJsonObject.put(accountId, mailInforObject.getUuid());
            parentJsonObject.put(accountType, mailAddressType);
            parentJsonObject.put(accountNo, mailInforObject.getUserName());
            parentJsonObject.put(accountPwd, mailInforObject.getPassword());
            parentJsonObject.put(accountUrl, mailInforObject.getWebSite());
            childJsonObject.put(remarks, mailInforObject.getRemarks());
            jsonArray.add(parentJsonObject);
//            jsonArray.put(parentJsonObject);

        }
    }


    /*
    *后去wiif的jsonarray
    *
    * */
    void getWifiJsonArray() throws org.json.JSONException {
        RealmResults<Wifiinfor> realmResults = realm.where(Wifiinfor.class).findAll();
        Wifiinfor wifiinforObject;
        parentJsonObject = new JSONObject();
        childJsonObject = new JSONObject();
        for (int position = 0; position < realmResults.size(); position++) {
            wifiinforObject = realmResults.get(position);
            parentJsonObject.put(accountId, wifiinforObject.getUuid());
            parentJsonObject.put(accountType, wifiType);
            parentJsonObject.put(accountNo, wifiinforObject.getWifiName());
            parentJsonObject.put(accountPwd, wifiinforObject.getWifiPassword());
            childJsonObject.put(backManagerAddress, wifiinforObject.getWifiIp());
            childJsonObject.put(backManagerAccount, wifiinforObject.getAdminAccount());
            childJsonObject.put(backManagerPassword, wifiinforObject.getAdminPassword());
            childJsonObject.put(ispAccount, wifiinforObject.getInternetProviderAccount());
            childJsonObject.put(ispPassword, wifiinforObject.getInternetProviderPassword());
            parentJsonObject.put(accountElseInfo, childJsonObject);
            jsonArray.add(parentJsonObject);
//            jsonArray.put(parentJsonObject);

        }

    }

    void getSafeNoteJasonArray() throws org.json.JSONException {
        RealmResults<SafeNote> realmResults = realm.where(SafeNote.class).findAll();
        SafeNote safeNoteObject;
        parentJsonObject = new JSONObject();
        childJsonObject = new JSONObject();
        for (int position = 0; position < realmResults.size(); position++) {
            safeNoteObject = realmResults.get(position);
            parentJsonObject.put(accountId, safeNoteObject.getUuid());
            parentJsonObject.put(accountType, safeNoteType);
            childJsonObject.put(noteTitle, safeNoteObject.getItemTitle());
            childJsonObject.put(noteContent, safeNoteObject.getNoteContent());
            parentJsonObject.put(accountElseInfo, childJsonObject);
            jsonArray.add(parentJsonObject);
//            jsonArray.put(parentJsonObject);

        }
    }


    /*
    *
    * 初始化相关的变量
    *
    * 在使用时首先调用此函数
    * */
    public void initEnviorment(Context context) {
        mContext = context;
        realm = Realm.getDefaultInstance();
        jsonArray = new JSONArray();
    }


    /*
    * 将jsonString 保存
    *
    * */
    public void saveDataToFile() {
        initJsonArray();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject userInfoJsonObject = new JSONObject();
                JSONObject rootJsonObject = new JSONObject();
                byte encipherResult[] = null;
                try {
                    userInfoJsonObject.put(user_pwd, PasswordManager.getEncipherPassword(mContext));
                    userInfoJsonObject.put(pwd_salt, PasswordManager.getSalt(mContext));
                    rootJsonObject.put(user_info, userInfoJsonObject);
                    rootJsonObject.put(AllUserAccount, jsonArray);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                String filePath = "backUpData.norember";
                String encipherData = JSON.toJSONString(rootJsonObject, SerializerFeature.DisableCircularReferenceDetect);
//                String encipherData = rootJsonObject.toString();
                encipherData += ("@@@@" + SHA1Util.encryptToSHA(encipherData));
                String key = MD5EncipherUtil.md5(PasswordManager.getAesKey()).substring(0, 16);
                try {
                    FileUtil.saveInternalCard(filePath, ClientEncryptionSamples.encryptClearText(key.toCharArray(), encipherData));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(SAVE_FINISH);

            }
        });
        thread.start();

    }

    private void initJsonArray() {
        try {
            getLoginJsonArray();
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
       /* getCreditJsonArray();
        getIdcardJsonArray();
        getLoginJsonArray();
        getMailJsonArray();
        getWifiJsonArray();
        getSafeNoteJasonArray();*/
    }


    /*
    * 测试获取jsonArray
    * */
    public void testgetJsonArray() {
        initJsonArray();
    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAVE_FINISH:
                    if (realm != null)
                        realm.close();

                    break;

            }
            super.handleMessage(msg);
        }
    };


}
