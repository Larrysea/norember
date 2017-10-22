package com.example.larry_sea.norember.view.fragment.storage_type_fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.account_entity.LoginInfor;
import com.example.larry_sea.norember.utill.DBUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Larry-sea on 2016/9/9.
 * <p/>
 * 添加网址类信息的fragment
 */
public class LoginInforFragment extends Fragment {

    Bundle mbundle;    //其中的数据格式  key  web_site_url  value 网址  ，key web_site_name value :网址名称
    @BindView(R.id.id_fragment_edit_web_site_toolbar)
    Toolbar idFragmentEditWebSiteToolbar;
    @BindView(R.id.id_fragment_add_web_site_infor_name_et)
    EditText idFragmentAddWebSiteInforNameEt;
    @BindView(R.id.id_fragment_add_web_site_infor_name_ti)
    TextInputLayout idFragmentAddWebSiteInforNameTi;
    @BindView(R.id.id_fragment_edit_web_site_infor_password_et)
    EditText idFragmentEditWebSiteInforPasswordEt;
    @BindView(R.id.id_fragment_edit_web_site_infor_password_ti)
    TextInputLayout idFragmentEditWebSiteInforPasswordTi;
    @BindView(R.id.id_fragment_edit_web_site_infor_web_site_et)
    EditText idFragmentEditWebSiteInforWebSiteEt;
    @BindView(R.id.id_fragment_edit_web_site_infor_web_site_name_et)
    EditText idFragmentEditWebSiteInforWebSiteNameEt;
    @BindView(R.id.id_fragment_edit_web_site_infor_remarks_et)
    EditText idFragmentEditWebSiteInforRemarksEt;
    @BindView(R.id.id_fragment_add_web_site_infor_remarks_ti)
    TextInputLayout idFragmentAddWebSiteInforRemarksTi;
    boolean isEdit = false;      //界面是否可编辑
    LoginInfor mLoginInfor;
    String key;                   //存储的主键
    Realm realm;

    /*
   * 确定按钮的监听器
   *
   * */
    DialogInterface.OnClickListener deletClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            DBUtil.deleteLoginInfor(key);
            dialog.dismiss();
            inforClose();
        }
    };

    DialogInterface.OnClickListener saveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            saveLoginInfor();
            inforClose();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edite_website, container, false);
        mbundle = getArguments();
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        idFragmentAddWebSiteInforNameTi.setHint(getString(R.string.account));
        idFragmentEditWebSiteInforPasswordTi.setHint(getString(R.string.password));
        if (mbundle != null) {
            {
                idFragmentEditWebSiteInforWebSiteEt.setText("www." + mbundle.getString("web_site_url"));
                if (mbundle.getString("web_site_name") != null) {
                    idFragmentAddWebSiteInforNameEt.setText(mbundle.getString("web_site_name"));
                    setEditAble(true);
                } else if (mbundle.getString("key") != null) {
                    isEdit = true;
                    key = mbundle.getString("key");
                    mLoginInfor = DBUtil.getLoginInfor(key);
                    bindDataToView(mLoginInfor);
                    setEditAble(false);
                }
            }

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.storage_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.id_menu_edit) {
            setEditAble(isEdit);
            isEdit = false;
            ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
        } else if (item.getItemId() == R.id.id_menu_save) {
            saveLoginInfor();
        } else if (item.getItemId() == R.id.id_menu_delete) {
            DialogUtil.showDelteDialog(getActivity(),deletClickListener,null);
        } else if (item.getItemId() == android.R.id.home) {
            ((AppCompatActivity) getActivity()).onBackPressed();
        }
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (!isEdit) {
            menu.findItem(R.id.id_menu_save).setVisible(true);
            menu.findItem(R.id.id_menu_delete).setVisible(false);
            menu.findItem(R.id.id_menu_edit).setVisible(false);
        } else {
            menu.findItem(R.id.id_menu_save).setVisible(false);
            menu.findItem(R.id.id_menu_delete).setVisible(true);
            menu.findItem(R.id.id_menu_edit).setVisible(true);
        }
    }

    /*
    * 初始化view
    *
    * */
    public void initView() {
        idFragmentEditWebSiteToolbar.setTitle(R.string.web_site_url);
        idFragmentEditWebSiteToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        ((AppCompatActivity) getActivity()).setSupportActionBar(idFragmentEditWebSiteToolbar);
        setHasOptionsMenu(true);
        getActivity().supportInvalidateOptionsMenu();
        getActivity().invalidateOptionsMenu();

    }

    /*
    *
    * 保存登陆信息
    *
    * */
    public void saveLoginInfor() {
        if (checkLoginfor(initLoginInforOrUpdateLoginfor(mLoginInfor))) {
            DBUtil.saveInfo(initLoginInforOrUpdateLoginfor(mLoginInfor));
            key = idFragmentAddWebSiteInforNameEt.getText().toString();
            isEdit = true;
            ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
            setEditAble(false);
        }
    }

    /*
    * 将数据与界面绑定
    *
    * */
    public void bindDataToView(LoginInfor logininfor) {
        if (logininfor.getAccountName() != null)
            idFragmentAddWebSiteInforNameEt.setText(logininfor.getAccountName());
        if (logininfor.getPassword() != null)
            idFragmentEditWebSiteInforPasswordEt.setText(logininfor.getPassword());
        if (logininfor.getUrl() != null)
            idFragmentEditWebSiteInforWebSiteEt.setText(logininfor.getUrl());
        if (logininfor.getWebSiteName() != null)
            idFragmentEditWebSiteInforWebSiteNameEt.setText(logininfor.getWebSiteName());
        if (logininfor.getRemarks() != null)
            idFragmentEditWebSiteInforRemarksEt.setText(logininfor.getRemarks());
    }

    /*
    * 设置是否可白年纪
    *
    * */
    public void setEditAble(boolean isEditeAble) {
        idFragmentAddWebSiteInforNameEt.setEnabled(isEditeAble);
        idFragmentEditWebSiteInforPasswordEt.setEnabled(isEditeAble);
        idFragmentEditWebSiteInforWebSiteEt.setEnabled(isEditeAble);
        idFragmentEditWebSiteInforWebSiteNameEt.setEnabled(isEditeAble);
        idFragmentEditWebSiteInforRemarksEt.setEnabled(isEditeAble);
    }

    /*
    * 检查登陆信息是否可以保存
    *
    * */
    public boolean checkLoginfor(LoginInfor loginfor) {
        if (loginfor.getAccountName() == null) {
            Toast.makeText(getActivity(), R.string.account_cant_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (loginfor.getPassword() == null) {
            Toast.makeText(getActivity(), R.string.password_cant_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public LoginInfor initLoginInforOrUpdateLoginfor(@Nullable LoginInfor loginInfor) {
        if (loginInfor == null) {
            loginInfor = new LoginInfor();
            loginInfor.setUuid(UUID.randomUUID().toString());
        } else if (loginInfor != null && key != null) {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
        }
        if (!idFragmentAddWebSiteInforNameEt.getText().toString().isEmpty())
            loginInfor.setAccountName(idFragmentAddWebSiteInforNameEt.getText().toString());
        if (!idFragmentEditWebSiteInforPasswordEt.getText().toString().isEmpty()) {
            loginInfor.setPassword(idFragmentEditWebSiteInforPasswordEt.getText().toString());
        }
        if (!idFragmentEditWebSiteInforWebSiteEt.getText().toString().isEmpty()) {
            loginInfor.setUrl(idFragmentEditWebSiteInforWebSiteEt.getText().toString());
        }
        if (!idFragmentEditWebSiteInforWebSiteNameEt.getText().toString().isEmpty()) {
            loginInfor.setWebSiteName(idFragmentEditWebSiteInforWebSiteNameEt.getText().toString());
        }
        if (!idFragmentEditWebSiteInforRemarksEt.getText().toString().isEmpty()) {
            loginInfor.setRemarks(idFragmentEditWebSiteInforRemarksEt.getText().toString());
        }
        if (loginInfor != null && key != null) {
            realm.commitTransaction();
            realm.close();
        }
        return loginInfor;
    }

    public void inforClose() {
        ((AppCompatActivity) getActivity()).onBackPressed();
    }

    /*
    * 询问是否保存logininfor
    * */
    public void wetherSaveLoginInfor() {
        DialogUtil.showWetherSaveDialog(getActivity(), saveClickListener, null);
    }


    /*
    * 是否可编辑
    * */
    public boolean isEditAble()
    {
        return idFragmentEditWebSiteInforRemarksEt.isEnabled();
    }

}
