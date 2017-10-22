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
import com.example.larry_sea.norember.entity.account_entity.MailInfor;
import com.example.larry_sea.norember.utill.DBUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Larry-sea on 9/27/2016.
 * <p>
 * 显示邮箱的fragment
 */

public class MailinforFragment extends Fragment {

    @BindView(R.id.id_fragment_edit_mail_et)
    EditText idFragmentEditMailEt;
    @BindView(R.id.id_fragment_edit_mail_ti)
    TextInputLayout idFragmentEditMailTi;
    @BindView(R.id.id_fragment_edit_mail_url_et)
    EditText idFragmentEditMailUrlEt;
    @BindView(R.id.id_fragment_edit_mail_url_ti)
    TextInputLayout idFragmentEditMailUrlTi;
    @BindView(R.id.id_fragment_edit_mail_password_et)
    EditText idFragmentEditMailPasswordEt;
    @BindView(R.id.id_fragment_edit_mail_password_ti)
    TextInputLayout idFragmentEditMailPasswordTi;
    @BindView(R.id.id_fragment_edit_mail_remark_et)
    EditText idFragmentEditMailRemarkEt;
    @BindView(R.id.id_fragment_edit_mail_remark_ti)
    TextInputLayout idFragmentEditMailRemarkTi;
    @BindView(R.id.id_fragment_edit_mail_infor_toolbar)
    Toolbar idFragmentEditMailInforToolbar;
    boolean isEdit = false;
    String key;      //用户名
    Realm realm;
    MailInfor mailInfor;   //邮件信息

    /*
    * 确定按钮的监听器
    *
    * */
    DialogInterface.OnClickListener deleteClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (key != null) {
                DBUtil.deleteMailInfo(key);
                dialog.dismiss();
                inforClose();
            }
        }
    };

    /*
    * 保存按钮的监听器
    *
    * */
    DialogInterface.OnClickListener saveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            saveMailInfor();
            inforClose();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_mail_infor, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /*
    *
    * 初始化view
    *
    * */
    public void initView() {
        setHasOptionsMenu(true);
        idFragmentEditMailInforToolbar.setTitle(R.string.mail);
        idFragmentEditMailInforToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(idFragmentEditMailInforToolbar);
        if (getArguments().getString("key") != null) {
            key = getArguments().getString("key");
            mailInfor = DBUtil.getMailInfor(key);
            bindDataToView(mailInfor);
            isEdit = true;
            setEditeAble(false);
        }


    }

    /*
    * 通知关闭
    *
    * */
    public void inforClose() {
        (getActivity()).onBackPressed();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.storage_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (isEdit) {
            menu.findItem(R.id.id_menu_save).setVisible(false);
            menu.findItem(R.id.id_menu_delete).setVisible(true);
            menu.findItem(R.id.id_menu_edit).setVisible(true);
        } else {
            menu.findItem(R.id.id_menu_save).setVisible(true);
            menu.findItem(R.id.id_menu_delete).setVisible(false);
            menu.findItem(R.id.id_menu_edit).setVisible(false);
        }
        isEdit = !isEdit;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.id_menu_edit) {
            setEditeAble(true);
        } else if (item.getItemId() == R.id.id_menu_save) {
            saveMailInfor();
        } else if (item.getItemId() == R.id.id_menu_delete) {
            DialogUtil.showDelteDialog(getActivity(), deleteClickListener, null);
        } else if (item.getItemId() == android.R.id.home) {
            inforClose();
        }
        return true;
    }

    /*
    *
    * 初始化mail的信息
    * */
    public MailInfor initMailInfor(MailInfor mailInfor) {

        if (mailInfor == null) {
            mailInfor = new MailInfor();
            mailInfor.setUuid(UUID.randomUUID().toString());
        } else if (mailInfor != null && key != null) {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
        }
        if (!idFragmentEditMailEt.getText().toString().isEmpty())
            mailInfor.setUserName(idFragmentEditMailEt.getText().toString());
        if (!idFragmentEditMailUrlEt.getText().toString().isEmpty())
            mailInfor.setWebSite(idFragmentEditMailUrlEt.getText().toString());
        if (!idFragmentEditMailPasswordEt.getText().toString().isEmpty())
            mailInfor.setPassword(idFragmentEditMailPasswordEt.getText().toString());
        if (!idFragmentEditMailRemarkEt.getText().toString().isEmpty())
            mailInfor.setRemarks(idFragmentEditMailRemarkEt.getText().toString());
        if (mailInfor != null && key != null) {
            realm.commitTransaction();
            realm.close();
        }
        return mailInfor;
    }


    /*
    * 将数据与view绑定
    *
    * */
    public void bindDataToView(MailInfor mailInfor) {
        if (mailInfor.getUserName() != null)
            idFragmentEditMailEt.setText(mailInfor.getUserName());
        if (mailInfor.getWebSite() != null)
            idFragmentEditMailUrlEt.setText(mailInfor.getWebSite());
        if (mailInfor.getPassword() != null)
            idFragmentEditMailPasswordEt.setText(mailInfor.getPassword());
        if (mailInfor.getRemarks() != null)
            idFragmentEditMailRemarkEt.setText(mailInfor.getRemarks());
    }

    public void setEditeAble(boolean editeAble) {
        idFragmentEditMailEt.setEnabled(editeAble);
        idFragmentEditMailUrlEt.setEnabled(editeAble);
        idFragmentEditMailPasswordEt.setEnabled(editeAble);
        idFragmentEditMailRemarkEt.setEnabled(editeAble);
        ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
    }

    /*
    *
    *检查信息是否可以保存
    *
    *
    * */
    public boolean checkIsCanSave(MailInfor mailInfor) {
        if (mailInfor.getUserName() == null) {
            Toast.makeText(getActivity(), R.string.account_cant_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mailInfor.getPassword() == null) {
            Toast.makeText(getActivity(), R.string.password_cant_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /*
    *
    * 保存邮箱信息
    *
    *
    * */
    public void saveMailInfor() {
        if (checkIsCanSave(initMailInfor(mailInfor))) {
            mailInfor = initMailInfor(mailInfor);
            DBUtil.saveInfo(initMailInfor(mailInfor));
            key = mailInfor.getUserName();
            isEdit = true;
            getActivity().invalidateOptionsMenu();
            setEditeAble(false);
        }
    }


    /*
    * 显示是否保存的dialog
    *
    * */
    public void showWetherSaveDialog() {
        DialogUtil.showWetherSaveDialog(getActivity(), saveClickListener, null);
    }


    public boolean isEditAble() {
        return idFragmentEditMailPasswordEt.isEnabled();
    }

}
