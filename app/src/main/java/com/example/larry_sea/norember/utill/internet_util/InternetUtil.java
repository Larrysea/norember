package com.example.larry_sea.norember.utill.internet_util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.base_entity.FeedBack;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Larry-sea on 2016/10/15.
 * <p>
 * <p>
 * 网络通信工具
 */

public class InternetUtil {

    final static int RESULT_SUCCESS = 0X1;               //正常提示
    final static int RESULT_ERROR = 0x2;                 //错误信息提示
    final static int RESULT_INFOR_EMPTYACTIVITY = 0x3;   //通知emptyactivity
    final static int RESULT_BIND_SUCCESS = 0X4;          //绑定设备成功
    final static int IS_LAST_VERSION = 0x5;              //已是最新版本
    final static int UPDATE_TO_LAST_VERSION = 0X6;       //更新至最新版本
    inforEmptyActivity minforEmptyInterface;
    checkUpdateTaskImpl mCheckUpdate;                    //检查app版本的接口
    Context context;                                     //context
    ProgressDialog progressDialog;                       //progressdialog


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESULT_SUCCESS:
                    Toast.makeText(context, R.string.thanks_your_feedback, Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_ERROR:
                    Toast.makeText(context, R.string.try_few_minute_later, Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_INFOR_EMPTYACTIVITY:
                    if (minforEmptyInterface != null) {
                        minforEmptyInterface.inforEmptyActivity((Boolean) msg.obj);
                    }
                    break;
                case RESULT_BIND_SUCCESS:
                    Toast.makeText(context, R.string.bind_device_success, Toast.LENGTH_SHORT).show();
                    break;
                case IS_LAST_VERSION:
                    Toast.makeText(context, R.string.is_last_version, Toast.LENGTH_SHORT).show();
                    break;
                case UPDATE_TO_LAST_VERSION:
                    progressDialog.dismiss();
                    mCheckUpdate.getTheResult((String) msg.obj);
                    break;
            }
        }
    };


    /*
    *   提交反馈数据
    *
    * */
    public void submitFeedback(Context context, final FeedBack feedBack) {
        this.context = context;
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpUtils.post().url("http://www.aceclound.cn//EasyPwd/index.php/index/index")
                        .addParams("CTL", "FeedBack")
                        .addParams("ACTN", "SaveFeedBack")
                        .addParams("ContectWay", feedBack.getContectWay())
                        .addParams("FeedBackContent", feedBack.getFeedbackContent())
                        .addParams("OsInfo", feedBack.getOsInfo())
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("internet util", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getString("success").equals("1")) {
                            handler.sendEmptyMessage(0x1);
                        } else if (jsonObject.getString("success").equals("-1")) {
                            handler.sendEmptyMessage(0x2);
                        }

                    }
                });

            }
        }).start();


    }


    /*
    *
    * 手机是否打开安全锁
    *
    * @param myDeviceId        //我的设备id
    *
    * */
    public void isLocked(Context context, final String myDeviceId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.post()
                        .url("http://www.aceclound.cn//EasyPwd/index.php/index/index")
                        .addParams("CTL", "LockDevice")
                        .addParams("ACTN", "CheckIsCanLockDevice")
                        .addParams("TargetDevice", myDeviceId)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JSONObject jsonObject = JSONObject.parseObject(response);
                        Message message = new Message();
                        if (jsonObject.getString("success").equals("1")) {
                            message.what = RESULT_INFOR_EMPTYACTIVITY;
                            message.obj = true;
                            handler.sendMessage(message);
                        } else if (jsonObject.getString("success").equals("0")) {
                            message.what = RESULT_INFOR_EMPTYACTIVITY;
                            message.obj = false;
                            handler.sendMessage(message);
                        }


                    }
                });


            }
        }).start();


    }

    public void setInforEmptyInterface(inforEmptyActivity inforemptyInterface) {
        minforEmptyInterface = inforemptyInterface;
    }

    /*
    * 绑定数据
    *
    * */
    public void bindDevice(Context context, final String sourceDeviceId, final String targetSourceId) {
        this.context = context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.post()
                        .url("http://www.aceclound.cn//EasyPwd/index.php/index/index")
                        .addParams("CTL", "LockDevice")
                        .addParams("ACTN", "BindDevice")
                        .addParams("SourceDevice", sourceDeviceId)
                        .addParams("TargetDevice", targetSourceId)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonobject = JSONObject.parseObject(response);
                        if (jsonobject.getString("success").equals("1")) {
                            handler.sendEmptyMessage(RESULT_BIND_SUCCESS);
                        } else if (jsonobject.getString("success").equals("0")) {
                            handler.sendEmptyMessage(RESULT_ERROR);
                        }
                    }
                });


            }
        }).start();


    }


    /*
    * 通知emptyactivity
    *
    *
    * @param isLocked代表是否锁住
    *
    * true代表锁住
    *
    * false代表没有锁住
    *
    * */
    public interface inforEmptyActivity {
        void inforEmptyActivity(boolean isLocked);
    }


    /*
    *
    * 检查更新app版本
    * */
    public void checkUpdate(Context context, final String versionNumber) {
        this.context = context;
        progressDialog = DialogUtil.getProgressDialog(context, R.string.check_update, R.string.check_update);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.post()
                        .url("http://www.aceclound.cn//EasyPwd/index.php/index/index")
                        .addParams("CTL", "UpdateSoft")
                        .addParams("ACTN", "UpdateSoft")
                        .addParams("CurVersion", versionNumber)
                        .addParams("SoftType", "1")  //1代表安卓端
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getString("success").equals("1")) {   //一代表安卓端
                            Message message = new Message();
                            message.what = UPDATE_TO_LAST_VERSION;
                            message.obj = jsonObject.getString("data");
                            handler.sendMessage(message);
                        } else if (jsonObject.getString("success").equals("0")) {
                            handler.sendEmptyMessage(IS_LAST_VERSION);
                        }

                    }
                });
            }
        }).start();
    }

    public interface checkUpdateTaskImpl {
        void getTheResult(String fileUrl);
    }

    public void setCheckUpdateInterface(checkUpdateTaskImpl checkUpdateInterface) {
        mCheckUpdate = checkUpdateInterface;
    }


}
