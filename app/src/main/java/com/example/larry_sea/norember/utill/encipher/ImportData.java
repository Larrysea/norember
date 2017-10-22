package com.example.larry_sea.norember.utill.encipher;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.example.larry_sea.norember.utill.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import io.realm.Realm;

import static com.example.larry_sea.norember.constants.ResourceConstants.accountElseInfo;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountNo;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountNumber;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountPwd;
import static com.example.larry_sea.norember.constants.ResourceConstants.accountUrl;
import static com.example.larry_sea.norember.constants.ResourceConstants.address;
import static com.example.larry_sea.norember.constants.ResourceConstants.backManagerAccount;
import static com.example.larry_sea.norember.constants.ResourceConstants.backManagerAddress;
import static com.example.larry_sea.norember.constants.ResourceConstants.backManagerPassword;
import static com.example.larry_sea.norember.constants.ResourceConstants.bicOrSwift;
import static com.example.larry_sea.norember.constants.ResourceConstants.country;
import static com.example.larry_sea.norember.constants.ResourceConstants.endTime;
import static com.example.larry_sea.norember.constants.ResourceConstants.iban;
import static com.example.larry_sea.norember.constants.ResourceConstants.ibanAccount;
import static com.example.larry_sea.norember.constants.ResourceConstants.ispAccount;
import static com.example.larry_sea.norember.constants.ResourceConstants.ispPassword;
import static com.example.larry_sea.norember.constants.ResourceConstants.noteContent;
import static com.example.larry_sea.norember.constants.ResourceConstants.noteTitle;
import static com.example.larry_sea.norember.constants.ResourceConstants.remarks;
import static com.example.larry_sea.norember.constants.ResourceConstants.safeCode;
import static com.example.larry_sea.norember.constants.ResourceConstants.signOrg;
import static com.example.larry_sea.norember.constants.ResourceConstants.startTime;
import static com.example.larry_sea.norember.constants.ResourceConstants.webSiteName;

/**
 * Created by Larry-sea on 2016/11/6.
 * <p>
 * 导入数据工具类
 * <p>
 * 首先使用initEnviorment初始化配置环境
 * <p>
 * 然后使用decodeData
 * <p>
 * 然后通过getJosnArray
 * <p>
 * 然后调用processJosnArray
 * <p>
 * 然后调用recycle回收一些变量
 */

public class ImportData {

    Realm realm;
    BankCard mBankCard;         //银行卡信息
    CreditCard mCreditCard;     //信用卡信息
    IdentityCard mIdentityCard; //身份证信息
    LoginInfor mLoginInfor;     //登陆信息
    MailInfor mMailInfor;       //邮箱信息
    Wifiinfor mWifiInfor;       //wifi信息
    SafeNote mSafeNote;         //安全笔记
    JSONObject mChildJsonObject;//子类jsonObject
    JSONArray mJsonArray;


    /*
    *
    * 导入数据
    *
    * */
    public void importDataToRealm(String filePath) {
        File file = new File(filePath);
        String encipherString = null;
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                byte data[] = StringUtil.getBytes(fileInputStream);
                encipherString = new String(data);
                String key = MD5EncipherUtil.md5(PasswordManager.getAesKey()).substring(0, 16);
                String result = ClientEncryptionSamples.decryptData(key.toCharArray(), encipherString);
                String array[]=result.split("@@@@");
                processJsonArray(array[0]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /*
    * 获取jsonarray
    *
    * */
    private void getJsonArray(String result) {
        mJsonArray = JSONObject.parseObject(result).getJSONArray(ResourceConstants.AllUserAccount);
    }


    /*
    * 处理jsonArray
    *
    * */
    private void processJsonArray(String result) {
        getJsonArray(result);
        JSONObject jsonObject;
        realm = Realm.getDefaultInstance();
        for (Object Object : mJsonArray) {
            jsonObject = (JSONObject) Object;
            saveJsonObjectToRealm(jsonObject.getIntValue(ResourceConstants.accountType), jsonObject);
        }

    }


    /*
    * 将jsonObject保存到realm中
    * @param storageType 存储类型
    * @paran josnObject
    *
    * */
    private void saveJsonObjectToRealm(int storageType, JSONObject jsonObject) {
        switch (storageType) {
            case ResourceConstants.bankCardType:
                saveBankObjectToRealm(jsonObject);
                break;
            case ResourceConstants.creditCardType:
                saveCreditCardToRealm(jsonObject);
                break;
            case ResourceConstants.idCardType:
                saveIdentityCardToRealm(jsonObject);
                break;
            case ResourceConstants.webSiteType:
                saveLoginInforToRealm(jsonObject);
                break;
            case ResourceConstants.mailAddressType:
                saveMailInforToRealm(jsonObject);
                break;
            case ResourceConstants.wifiType:
                saveWifiInforToRealm(jsonObject);
                break;
            case ResourceConstants.safeNoteType:
                saveSafeNoteToRealm(jsonObject);
                break;
        }
    }


    /*
    *
    * 保存bankCardJsonObject到realm中
    * @param jsonObject是bankCardJsonObject
    * */
    private void saveBankObjectToRealm(JSONObject jsonObject) {
        mChildJsonObject = JSONObject.parseObject(jsonObject.getString(accountElseInfo));
        realm.beginTransaction();
        mBankCard = realm.createObject(BankCard.class);
        mBankCard.setUuid(UUID.randomUUID().toString());
        mBankCard.setBankName(jsonObject.getString(accountNo));
        mBankCard.setCardNumber(jsonObject.getString(accountNumber));
        mBankCard.setPassword(jsonObject.getString(accountPwd));
        mBankCard.setIbanName(mChildJsonObject.getString(ibanAccount));
        mBankCard.setIban(mChildJsonObject.getString(iban));
        mBankCard.setBicOrswift(mChildJsonObject.getString(bicOrSwift));
        realm.commitTransaction();
    }


    /*
    *
    * 保存creditCardJsonObject到realm中
    *
    * */
    private void saveCreditCardToRealm(JSONObject jsonObject) {
        mChildJsonObject = JSONObject.parseObject(jsonObject.getString(accountElseInfo));
        realm.beginTransaction();
        mCreditCard = realm.createObject(CreditCard.class);
        mCreditCard.setUuid(UUID.randomUUID().toString());
        mCreditCard.setAccountHolder(jsonObject.getString(accountNo));
        mCreditCard.setCardNumber(jsonObject.getString(accountNo));
        mCreditCard.setSecurityNumber(mChildJsonObject.getString(safeCode));
        mCreditCard.setCountry(mChildJsonObject.getString(country));
        mCreditCard.setAddress(mChildJsonObject.getString(address));
        mCreditCard.setRemarks(mChildJsonObject.getString(remarks));
        realm.commitTransaction();

    }

    /*
    *
    * 保存身份证信息到realm中
    *
    *
    * */
    private void saveIdentityCardToRealm(JSONObject jsonObject) {
        mChildJsonObject = JSONObject.parseObject(jsonObject.getString(accountElseInfo));
        realm.beginTransaction();
        mIdentityCard = realm.createObject(IdentityCard.class);
        mIdentityCard.setUuid(UUID.randomUUID().toString());
        mIdentityCard.setName(jsonObject.getString(accountNo));
        mIdentityCard.setAddress(mChildJsonObject.getString(address));
        mIdentityCard.setCountry(mChildJsonObject.getString(address));
        mIdentityCard.setStartTermOfDate(mChildJsonObject.getString(startTime));
        mIdentityCard.setEndTermOfDate(mChildJsonObject.getString(endTime));
        mIdentityCard.setIssuingAuthority(mChildJsonObject.getString(signOrg));
        realm.commitTransaction();
    }


    /*
    * 保存网站信息到realm中
    *
    * */

    private void saveLoginInforToRealm(JSONObject jsonObject) {
        mChildJsonObject = JSONObject.parseObject(jsonObject.getString(accountElseInfo));
        realm.beginTransaction();
        mLoginInfor = realm.createObject(LoginInfor.class);
        mLoginInfor.setUuid(UUID.randomUUID().toString());
        mLoginInfor.setAccountName(jsonObject.getString(accountNo));
        mLoginInfor.setPassword(jsonObject.getString(accountPwd));
        mLoginInfor.setUrl(jsonObject.getString(accountUrl));
        mLoginInfor.setWebSiteName(mChildJsonObject.getString(webSiteName));
        mLoginInfor.setRemarks(mChildJsonObject.getString(remarks));
        realm.commitTransaction();
    }


    /*
    * 保存邮箱信息到realm中
    *
    * */
    private void saveMailInforToRealm(JSONObject jsonObject) {
        mChildJsonObject = JSONObject.parseObject(jsonObject.getString(accountElseInfo));
        realm.beginTransaction();
        mMailInfor = realm.createObject(MailInfor.class);
        mMailInfor.setUuid(UUID.randomUUID().toString());
        mMailInfor.setUserName(jsonObject.getString(accountNo));
        mMailInfor.setPassword(jsonObject.getString(accountPwd));
        mMailInfor.setWebSite(jsonObject.getString(accountUrl));
        mMailInfor.setRemarks(mChildJsonObject.getString(remarks));
        realm.commitTransaction();
    }


    /*
    *
    * 保存wifi信息到realm中
    *
    * */
    private void saveWifiInforToRealm(JSONObject jsonObject) {
        mChildJsonObject = JSONObject.parseObject(jsonObject.getString(accountElseInfo));
        realm.beginTransaction();
        mWifiInfor = realm.createObject(Wifiinfor.class);
        mWifiInfor.setUuid(UUID.randomUUID().toString());
        mWifiInfor.setWifiName(jsonObject.getString(accountNo));
        mWifiInfor.setWifiPassword(jsonObject.getString(accountPwd));
        mWifiInfor.setWifiIp(mChildJsonObject.getString(backManagerAddress));
        mWifiInfor.setAdminAccount(mChildJsonObject.getString(backManagerAccount));
        mWifiInfor.setAdminPassword(mChildJsonObject.getString(backManagerPassword));
        mWifiInfor.setInternetProviderAccount(mChildJsonObject.getString(ispAccount));
        mWifiInfor.setInternetProviderPassword(mChildJsonObject.getString(ispPassword));
        realm.commitTransaction();
    }


    /*
    * 将安全笔记类型的jsonObject保存至realm中
    *
    * */
    private void saveSafeNoteToRealm(JSONObject jsonObject) {
        mChildJsonObject = JSONObject.parseObject(jsonObject.getString(accountElseInfo));
        realm.beginTransaction();
        mSafeNote = realm.createObject(SafeNote.class);
        mSafeNote.setUuid(UUID.randomUUID().toString());
        mSafeNote.setItemTitle(mChildJsonObject.getString(noteTitle));
        mSafeNote.setNoteContent(mChildJsonObject.getString(noteContent));
        realm.commitTransaction();
    }


    /*
    *
    * 回收变量
    *
    * 在使用processJsonArray之后使用这个作为变量回收工作
    *
    * */
    public void recycle() {
        mBankCard = null;
        mCreditCard = null;
        mIdentityCard = null;
        mLoginInfor = null;
        mMailInfor = null;
        mWifiInfor = null;
        if (realm != null)
            realm.close();
    }


}
