package com.example.larry_sea.norember.impl;

import android.content.Context;
import android.util.Log;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.modeal_interface.StorageItemModelInterface;
import com.example.larry_sea.norember.constants.ResourceConstants;
import com.example.larry_sea.norember.entity.account_entity.BankCard;
import com.example.larry_sea.norember.entity.account_entity.BasePassword;
import com.example.larry_sea.norember.entity.account_entity.CreditCard;
import com.example.larry_sea.norember.entity.account_entity.IdentityCard;
import com.example.larry_sea.norember.entity.account_entity.LoginInfor;
import com.example.larry_sea.norember.entity.account_entity.SafeNote;
import com.example.larry_sea.norember.entity.account_entity.Wifiinfor;
import com.example.larry_sea.norember.entity.base_entity.BaseStorageItemEntity;
import com.example.larry_sea.norember.entity.base_entity.StorageItemTypeEntity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by Larry-sea on 2016/9/15.
 * <p/>
 * 存储类型的模型实现
 * <p/>
 * <p/>
 * 实现方在mainactivity presenter中实现
 */
public class StorageItemModealImpl implements StorageItemModelInterface {
    final static String TAG = "@@@storage item modeal";
    boolean isExit = false;
    Context mcontext;
    Realm mrealm;

    public StorageItemModealImpl(Context context) {
        mcontext = context;

    }

    @Override
    public List<BaseStorageItemEntity> getAllDataFromDB() {
        return null;
    }

    @Override
    public void saveDataToTheDB(BaseStorageItemEntity storageData) {

    }

    /*
     *
     *
     * 获取所有类型数据
     *
     *
     * */
    @Override
    public List<StorageItemTypeEntity> getAllItemType() {

        final List<StorageItemTypeEntity> storageItemModealList = new ArrayList<StorageItemTypeEntity>();
        String[] typeNameArray = mcontext.getResources().getStringArray(R.array.id_string_storage_type_name);
        Class[] class1 = new Class[3];
        class1[0] = BankCard.class;
        int position = 0;//数组位置记录
        try {
            mrealm = Realm.getDefaultInstance();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }
        //代表数据正常读取的情况下，将realm中的数据查询到以后保存到storageItemModealList
        if (mrealm != null) {
            mrealm.beginTransaction();
            for (Class tempClass : ResourceConstants.classArray) {
                if (mrealm.where(tempClass).findAll().size() > 0) {
                    StorageItemTypeEntity storageItemTypeEntity = new StorageItemTypeEntity();
                    storageItemTypeEntity.setAmount((mrealm.where(tempClass).findAll()).size());
                    storageItemTypeEntity.setItemTitle(typeNameArray[position]);
                    storageItemTypeEntity.setIconPath(ResourceConstants.storageItemIconArray[position]);
                    storageItemTypeEntity.setType(ResourceConstants.storageType[position]);
                    storageItemModealList.add(storageItemTypeEntity);
                }
                position++;
            }
            mrealm.commitTransaction();
        }
        return storageItemModealList;
    }




    /*       从这下面以后的函数只是用来测试用的            */

    public void initData() {
        try {
//              Realm.deleteRealm(realmConfiguration);
            mrealm = Realm.getDefaultInstance();
            if (!isExit) {
                isExit = true;
            }
        } catch (RealmMigrationNeededException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public RealmList<BankCard> initBankCardRealmList() {

        final RealmList<BankCard> realmList = new RealmList<BankCard>();
        mrealm.beginTransaction();

        int amount = 0;
        BankCard bankCard = mrealm.createObject(BankCard.class);
        bankCard.setBankName("中国建设银行");

        bankCard.setCardNumber("41421414124214134");
        bankCard.setPassword("fasfsaf");
        bankCard.setIban("fasfaf");
        bankCard.setIbanName("汇丰银行");
        bankCard.setBicOrswift("bic/swift");
        realmList.add(bankCard);

        BankCard bankCard1 = mrealm.createObject(BankCard.class);
        bankCard1.setBankName("光大银行");
        bankCard1.setCardNumber("7412094188742");
        bankCard1.setPassword("fasfsaf");
        bankCard1.setIban("fasfaf");
        bankCard1.setIbanName("汇丰银行");
        bankCard1.setBicOrswift("bic/swift");
        realmList.add(bankCard1);


        BankCard bankCard3 = mrealm.createObject(BankCard.class);
        bankCard3.setBankName("中国建设银行");
        bankCard3.setCardNumber("4124789714");
        bankCard3.setPassword("fasfsaf");
        bankCard3.setIban("fasfaf");
        bankCard3.setIbanName("汇丰银行");
        bankCard3.setBicOrswift("bic/swift");
        realmList.add(bankCard3);

        BankCard bankCard4 = mrealm.createObject(BankCard.class);
        bankCard4.setBankName("邮政储蓄银行");
        bankCard4.setCardNumber("127489741242");
        bankCard4.setPassword("fasfsaf");
        bankCard4.setIban("fasfaf");
        bankCard4.setIbanName("汇丰银行");
        bankCard4.setBicOrswift("bic/swift");
        realmList.add(bankCard4);

        BankCard bankCard5 = mrealm.createObject(BankCard.class);
        bankCard5.setBankName("邮政储蓄银行");
        bankCard5.setCardNumber("42174177801");
        bankCard5.setPassword("fasfsaf");
        bankCard5.setIban("fasfaf");
        bankCard5.setIbanName("汇丰银行");
        bankCard5.setBicOrswift("bic/swift");
        realmList.add(bankCard5);
        mrealm.commitTransaction();
        return realmList;
    }


    public RealmList<LoginInfor> initLoginInfor() {

        final RealmList<LoginInfor> realmList = new RealmList<LoginInfor>();


        mrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int amount = 0;
                LoginInfor loginInfor;
                while (amount < 5) {

                    loginInfor = realm.createObject(LoginInfor.class);
                    loginInfor.setAccountName("丁磊" + amount);
                    loginInfor.setUrl("www.baidu.com");
                    loginInfor.setWebSiteName("携传");
                    loginInfor.setPassword("1213123");
                    loginInfor.setRemarks("这是一个神奇的网站");
                    amount++;
                    realmList.add(loginInfor);
                }
            }
        });

        return realmList;

    }


    public RealmList<IdentityCard> initIdCard() {

        final RealmList<IdentityCard> realmList = new RealmList<IdentityCard>();

        mrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int amount = 0;
                IdentityCard identityCard;
                while (amount < 5) {

                    identityCard = realm.createObject(IdentityCard.class);
                    identityCard.setName("larrysea" + amount);
                    identityCard.setCountry("china");
                    identityCard.setIssuingAuthority("中国安康");
                    identityCard.setAddress("陕西省安康市汉阴县");
                    identityCard.setSex(true);
                    identityCard.setEndTermOfDate("2099-12-12");
                    identityCard.setStartTermOfDate("1800-12-12");
                    identityCard.setIdentityNumber("612422199406100033");
                    amount++;
                    realmList.add(identityCard);
                }
            }
        });

        return realmList;

    }


    public RealmList<BasePassword> initPassword() {
        final RealmList<BasePassword> realmList = new RealmList<BasePassword>();

        mrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                /*int amount = 0;
                BasePassword basePassword;
                while (amount < 5) {
                    BasePassword basePassword;
                    basePassword = realm.createObject(BasePassword.class);
                    basePassword.setItemTitle("这是一个密码项" + amount);
                    amount++;
                    realmList.add(basePassword);*/

                BasePassword basePassword;
                basePassword = realm.createObject(BasePassword.class);
                basePassword.setItemTitle("淘宝项");
                basePassword.setPassword("fasdfasf");
                realmList.add(basePassword);

                BasePassword basePassword1;
                basePassword1 = realm.createObject(BasePassword.class);
                basePassword1.setItemTitle("天猫");
                basePassword1.setPassword("afasdfasd");
                realmList.add(basePassword1);

                BasePassword basePassword2;
                basePassword2 = realm.createObject(BasePassword.class);
                basePassword2.setItemTitle("京东项");
                basePassword2.setPassword("a890890d");
                realmList.add(basePassword2);

                BasePassword basePassword3;
                basePassword3 = realm.createObject(BasePassword.class);
                basePassword3.setItemTitle("腾讯");
                basePassword3.setPassword("afd89afadasd");
                realmList.add(basePassword3);

                BasePassword basePassword4;
                basePassword4 = realm.createObject(BasePassword.class);
                basePassword4.setPassword("a41248914sd");
                basePassword4.setItemTitle("国美项");
                realmList.add(basePassword4);
            }
        });

        return realmList;
    }

    /*
    *
    * 关闭realm释放内存
    *
    * */
    @Override
    public void closeRealm() {
        if (mrealm != null && !mrealm.isClosed()) {
            mrealm.close();
        }
    }


    /*
    * 初始化信用卡信息
    *
    * */
    public RealmList<CreditCard> initCreditCardData() {
        final RealmList<CreditCard> realmList = new RealmList<CreditCard>();


        mrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int amount = 0;
                CreditCard creditCard;
                while (amount < 5) {

                    creditCard = realm.createObject(CreditCard.class);
                    creditCard.setAccountHolder("丁磊" + amount);
                    creditCard.setCardNumber("6217 0002 6000 1651 433");
                    amount++;
                    realmList.add(creditCard);
                }
            }
        });

        return realmList;


    }


    /*
    * 初始化wifi信息的
    *
    * */


    public RealmList<Wifiinfor> initWifiData() {
        final RealmList<Wifiinfor> realmList = new RealmList<Wifiinfor>();


        mrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int amount = 0;
                Wifiinfor wifiinfor;
                while (amount < 5) {
                    wifiinfor = realm.createObject(Wifiinfor.class);
                    wifiinfor.setWifiName("android2" + amount);
                    wifiinfor.setWifiPassword("6217android2");
                    wifiinfor.setWifiIp("192.168.1.1");
                    wifiinfor.setAdminAccount("admin");
                    wifiinfor.setAdminPassword("android");
                    wifiinfor.setInternetProviderPassword("ah004909");
                    wifiinfor.setInternetProviderAccount("0004888931");
                    amount++;
                    realmList.add(wifiinfor);
                }
            }
        });
        return realmList;
    }


    /*
    *获取logininfor的list
    *
    * */
    public ArrayList<LoginInfor> getLoinforList() {
        ArrayList<LoginInfor> result = new ArrayList<LoginInfor>();
        if (mrealm == null)
            mrealm = Realm.getDefaultInstance();
        mrealm.beginTransaction();
        RealmResults<LoginInfor> realmResults = mrealm.where(LoginInfor.class).findAll();
        if (realmResults.size() > 0) {
            for (int position = 0; position < realmResults.size(); position++) {
                result.add(realmResults.get(position));
            }
        }
        mrealm.commitTransaction();
        mrealm.close();
        return result;
    }

    /*
    *
    * 初始化safenote
    *
    * */
    public void initSafeNote() {
        SafeNote safeNote = new SafeNote();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (int position = 0; position < 5; position++) {
            safeNote.setCategory("安全笔记");
            safeNote.setNoteContent("这是一个安全笔记");
            safeNote.setItemTitle("开黑账号+1");
            realm.copyToRealm(safeNote);
        }
        realm.commitTransaction();


    }


}
