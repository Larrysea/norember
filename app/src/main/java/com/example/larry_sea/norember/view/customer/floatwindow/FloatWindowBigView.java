package com.example.larry_sea.norember.view.customer.floatwindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.DrawableClickListener;
import com.example.larry_sea.norember.callback_interface.RecyclerviewClickInterface;
import com.example.larry_sea.norember.callback_interface.loginDrawClickImpl;
import com.example.larry_sea.norember.entity.account_entity.LoginInfor;
import com.example.larry_sea.norember.impl.StorageItemModealImpl;
import com.example.larry_sea.norember.manager.PasswordManager;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FloatWindowBigView extends LinearLayout implements RecyclerviewClickInterface {



    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;
    @BindView(R.id.start_float_window_recyclerview)
    RecyclerView mrecyclerview;

    @BindView(R.id.id_register_tv)
    TextView register;
    @BindView(R.id.id_close_tv)
    TextView close;
    Context mcontext;
    ArrayList<LoginInfor> mdatas;
    CommonAdapter<LoginInfor> madapter;        //通用适配器
    inforService minforServiece;               //通知的服务



    @SuppressLint("NewApi")
    public FloatWindowBigView(final Context context) {
        super(context);
        mcontext = context;
        LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
        View view = findViewById(R.id.big_window_layout);
        ButterKnife.bind(this, view);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        initView();
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.removeSmallWindow(context);
            }
        });
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initPhoneData();
                madapter.notifyDataSetChanged();
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            this.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(10, 10, viewWidth - 10,
                            viewHeight - 10, 15);
                }
            });
            this.setElevation(20);
            this.setTranslationZ(10);
            this.setClipToOutline(true);
        }


    }


    /*
    * 初始化view
    *
    * */
    public void initView() {
        initAccountInfoList();
        try{


        if (mdatas != null&&mdatas.size()>0) {
            mrecyclerview.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false));
            madapter = new CommonAdapter<LoginInfor>(mcontext, R.layout.float_window_big_item, mdatas) {
                @Override
                protected void convert(ViewHolder holder, LoginInfor loginInfor, int position) {
                    if (loginInfor.getAccountName() != null) {
                        holder.setText(R.id.id_float_window_big_item_icon_tv, loginInfor.getAccountName().substring(0, 1));
                    }
                    if (loginInfor.getAccountName() != null) {
                        holder.setText(R.id.id_float_window_big_item_title_tv, loginInfor.getAccountName());
                    }
                    if (loginInfor.getUrl() != null) {
                        holder.setText(R.id.id_float_window_big_item_sub_title_tv, loginInfor.getUrl());
                    }
                }
            };
            madapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    if (minforServiece != null) {
                        //填充手机号服务
                        if (mdatas.get(position).toString().equals(getResources().getString(R.string.phone_number))) {
                            minforServiece.inforSerViceFillPhoneNumber(mdatas.get(position).getAccountName());
                        } else {
                            //填充普通登录账号
                            MyWindowManager.removeBigWindow(mcontext);
                            minforServiece.inforServiceFillAccount(mdatas.get(position).getAccountName(), mdatas.get(position).getPassword());
                        }
                    }

                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            mrecyclerview.setAdapter(madapter);
        } else {
            Toast.makeText(mcontext, R.string.temp_no_login_infor, Toast.LENGTH_SHORT).show();
        }

        }catch (IllegalStateException e)
        {
            initAccountInfoList();
        }

    }

    /**
     *
     * 初始化账户信息，从realm中获取数据
     *
     *
     */
    private void initAccountInfoList() {
        StorageItemModealImpl storageItemModeal = new StorageItemModealImpl(mcontext);
        mdatas = storageItemModeal.getLoinforList();
    }


    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    /*
    * 初始化手机信息
    *
    * */
    public void initPhoneData() {
        LoginInfor loginfor = new LoginInfor();
        loginfor.setAccountName("");
        loginfor.setUrl("手机号");
        mdatas.clear();
        mdatas.add(loginfor);


    }

    public void setInforService(inforService inforService) {
        this.minforServiece = inforService;
    }

    /*
    *
    * 通知服务的接口
    *
    * 实现方在autologinservice 中实现
    *
    * */
    public interface inforService {
        /*
        * @parm1账户名
        * @parm2密码
        * */
        void inforServiceFillAccount(String account, String password);

        void inforSerViceFillPhoneNumber(String phone);
    }


}
