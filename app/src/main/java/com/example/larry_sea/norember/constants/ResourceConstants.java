package com.example.larry_sea.norember.constants;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.account_entity.BankCard;
import com.example.larry_sea.norember.entity.account_entity.BasePassword;
import com.example.larry_sea.norember.entity.account_entity.CreditCard;
import com.example.larry_sea.norember.entity.account_entity.Database;
import com.example.larry_sea.norember.entity.account_entity.DriverLicense;
import com.example.larry_sea.norember.entity.account_entity.IdentityCard;
import com.example.larry_sea.norember.entity.account_entity.LoginInfor;
import com.example.larry_sea.norember.entity.account_entity.MailInfor;
import com.example.larry_sea.norember.entity.account_entity.MemberCard;
import com.example.larry_sea.norember.entity.account_entity.PassPort;
import com.example.larry_sea.norember.entity.account_entity.SafeNote;
import com.example.larry_sea.norember.entity.account_entity.Server;
import com.example.larry_sea.norember.entity.account_entity.SocialInsurance;
import com.example.larry_sea.norember.entity.account_entity.SoftWareLicense;
import com.example.larry_sea.norember.entity.account_entity.Wifiinfor;

import static com.example.larry_sea.norember.factory.IntentFactory.BANK_CARD;
import static com.example.larry_sea.norember.factory.IntentFactory.BASE_PASSWORD;
import static com.example.larry_sea.norember.factory.IntentFactory.CREDIT_CARD;
import static com.example.larry_sea.norember.factory.IntentFactory.DATA_BASE;
import static com.example.larry_sea.norember.factory.IntentFactory.DRIVER_LICENSE;
import static com.example.larry_sea.norember.factory.IntentFactory.IDENTITY_CARD;
import static com.example.larry_sea.norember.factory.IntentFactory.LOGIN_INFOR;
import static com.example.larry_sea.norember.factory.IntentFactory.MAIL_INFOR;
import static com.example.larry_sea.norember.factory.IntentFactory.MEMBER_CARD;
import static com.example.larry_sea.norember.factory.IntentFactory.PASS_PORT;
import static com.example.larry_sea.norember.factory.IntentFactory.SAFE_NOTE;
import static com.example.larry_sea.norember.factory.IntentFactory.SERVER;
import static com.example.larry_sea.norember.factory.IntentFactory.SOCICAL_INSURANCE;
import static com.example.larry_sea.norember.factory.IntentFactory.SOFT_WARE_LICENSE;
import static com.example.larry_sea.norember.factory.IntentFactory.WIFI_INFOR;

/**
 * Created by Larry-sea on 2016/9/8.
 * <p/>
 * <p/>
 * 系统资源常量类
 */
public class ResourceConstants {

    // 网站网址资源常量
    public final static String webSiteArray[] = {"baidu.com", "w.qq.com", "tmall.com", "163.com", "wx.qq.com", "weibo.com", "bilibili.com", "taobao.com", "tudou.com", "youku.com", "zhihu.com", "yunpan.360.cn", "douban.com", "github.com", "jd.com"};


    //网站icon资源常量
    public final static int webSiteIconArray[] = {R.mipmap.baidu, R.mipmap.qq, R.mipmap.tmall, R.mipmap.netease, R.mipmap.wx, R.mipmap.weibo, R.mipmap.bilibili, R.mipmap.taobao, R.mipmap.tudou, R.mipmap.youku, R.mipmap.zhihu, R.mipmap.yunpan360, R.mipmap.douban, R.mipmap.github, R.mipmap.jd};


    //存储类型图片资源常量

    public final static int storageItemIconArray[] = {R.mipmap.ic_type_bank_card_medium, R.mipmap.ic_type_password_medium,
            R.mipmap.ic_type_credit_card_medium, R.mipmap.ic_tyep_database_medium,
            R.mipmap.ic_type_driver_license_medium, R.mipmap.ic_type_idcard_medium,
            R.mipmap.ic_type_login_medium, R.mipmap.ic_type_mail_medium,
            R.mipmap.ic_type_member_medium, R.mipmap.ic_type_passport_medium,
            R.mipmap.ic_type_note_medium, R.mipmap.ic_type_server_medium,
            R.mipmap.ic_type_social_insurance_medium, R.mipmap.ic_type_software_license_medium,
            R.mipmap.ic_type_wifi_medium};


    /*
    * 类类型常量数组
    *
    * */
    public final static Class classArray[] = {BankCard.class, BasePassword.class, CreditCard.class, Database.class, DriverLicense.class, IdentityCard.class, LoginInfor.class, MailInfor.class, MemberCard.class, PassPort.class, SafeNote.class,
            Server.class, SocialInsurance.class, SoftWareLicense.class, Wifiinfor.class};


    public final static int storageType[] = {BANK_CARD, BASE_PASSWORD, CREDIT_CARD, DATA_BASE, DRIVER_LICENSE,
            IDENTITY_CARD, LOGIN_INFOR, MAIL_INFOR, MEMBER_CARD, PASS_PORT, SAFE_NOTE, SERVER, SOCICAL_INSURANCE, SOFT_WARE_LICENSE, WIFI_INFOR};


    // 不同类型的图片资源分类

    //登陆类型的图片资源 array

    public final static int loginIconResource[] = {R.mipmap.ic_type_login_medium, R.mipmap.ic_type_mail_medium, R.mipmap.ic_type_wifi_medium};

    //卡务类资源图片id
    public final static int cardIconResource[] = {R.mipmap.ic_type_bank_card_medium, R.mipmap.ic_type_credit_card_medium, R.mipmap.ic_type_idcard_medium};


    //登陆类型array
    public final static int loginNameResource[] = {R.string.account, R.string.mail, R.string.wifi};

    //卡务类资源图片id

    public final static int cardNameResourece[] = {R.string.bank_card, R.string.credit_card, R.string.id_card};


    /*
    *
    * 数据类型的代码常量
    *
    * */
    public final static String user_pwd = "user_pwd";
    public final static String pwd_salt = "pwd_salt";
    public final static String user_info = "user_info";
    public final static String AllUserAccount = "AllUserAccount";

    public final static String accountNumber = "AccountNo";              //账号
    public final static String accountId = "AccountId";                  //账户id
    public final static String accountType = "AccountType";              //账户类型
    public final static String accountNo = "AccountNo";
    //账户编号
    public final static String accountPwd = "AccountPwd";                //账户密码
    public final static String accountUrl = "AccountUrl";                //账户url
    public final static String accountElseInfo = "AccountElseInfo";      //账户其他信息
    public final static int webSiteType = 11;
    public final static int mailAddressType = 12;
    public final static int wifiType = 13;
    public final static int idCardType = 21;
    public final static int bankCardType = 31;
    public final static int creditCardType = 32;
    public final static int safeNoteType = 41;


    //银行卡的else信息
    public final static String iban = "Iban";
    public final static String ibanAccount = "IbanAccount";
    public final static String bicOrSwift = "BSCode";

    //信用卡的else信息
    public final static String safeCode = "SafeCode";
    public final static String country = "Country";
    public final static String address = "UserAddreess";
    public final static String remarks = "AccountDetaile";


    //身份证else 信息
    public final static String accountName = "AccountName";
    public final static String signOrg = "Signorg";
    public final static String startTime = "StartTime";
    public final static String endTime = "EndTime";

    //登陆信息
    public final static String webSiteName = "WebSiteName";


    //wifi信息
    public final static String backManagerAddress = "BackManagerAddress";
    public final static String backManagerAccount = "BackManagerAccount";
    public final static String backManagerPassword = "BackManagerPassword;";
    public final static String ispAccount = "ISPAccount";
    public final static String ispPassword = "ISPPassword";


    //安全笔记title
    public final static String noteTitle = "nodeTitle";
    public final static String noteContent = "nodeContent";


}
