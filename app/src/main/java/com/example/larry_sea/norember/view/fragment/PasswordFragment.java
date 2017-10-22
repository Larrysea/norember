package com.example.larry_sea.norember.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.account_entity.BasePassword;
import com.example.larry_sea.norember.utill.DBUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Larry-sea on 9/20/2016.
 * <p>
 * 显示password 的 fragment
 */
public class PasswordFragment extends Fragment {


    @BindView(R.id.id_fragment_edit_password_toolbar)
    Toolbar idFragmentEditPasswordToolbar;
    @BindView(R.id.id_fragment_edit_password_title_et)
    EditText idFragmentEditPasswordTitleEt;
    @BindView(R.id.id_fragment_edit_password_title_ti)
    TextInputLayout idFragmentEditPasswordTitleTi;
    @BindView(R.id.id_fragment_edit_password_password_et)
    EditText idFragmentEditPasswordPasswordEt;
    @BindView(R.id.id_fragment_edit_password_password_ti)
    TextInputLayout idFragmentEditPasswordPasswordTi;
    @BindView(R.id.id_fragment_edite_password_remark_et)
    EditText idFragmentEditePasswordRemarkEt;
    @BindView(R.id.id_fragment_edite_password_remark_ti)
    TextInputLayout idFragmentEditePasswordRemarkTi;
    Realm mrealm;
    boolean isEdit;          //是否可以编辑
    AlertDialog mDialog;
    String key;             //传递过来的主键
    /*
    * 确定按钮的监听器
    *
    * */
    DialogInterface.OnClickListener positiveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            DBUtil.deletePassword(key);
            dialog.dismiss();
            inforClose();
        }
    };
    /*
    * 取消按钮的监听器
    *
    * */
    DialogInterface.OnClickListener negativeClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edite_passoword, container, false);
        ButterKnife.bind(this, view);
        idFragmentEditPasswordToolbar.setTitle(R.string.password);
        idFragmentEditPasswordToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        ((AppCompatActivity) getActivity()).setSupportActionBar(idFragmentEditPasswordToolbar);
        setHasOptionsMenu(true);
        mrealm = Realm.getDefaultInstance();
        return view;
    }

    /*
    * 初始化组件数据
    *
    * */
    public void initViewData(BasePassword basePassword) {
        idFragmentEditPasswordPasswordTi.setHint(getString(R.string.password));
        idFragmentEditPasswordTitleTi.setHint(getString(R.string.title));
        idFragmentEditePasswordRemarkTi.setHint(getString(R.string.remarks));
        setEditTextEnabled(false);
        if (basePassword != null) {
            if (basePassword.getTitle() != null) {
                idFragmentEditPasswordTitleEt.setText(basePassword.getTitle());
            }
            if (basePassword.getPassword() != null) {
                idFragmentEditPasswordPasswordEt.setText(basePassword.getPassword());
            }
            if (basePassword.getRemarks() != null) {
                idFragmentEditePasswordRemarkEt.setText(basePassword.getRemarks());
            }
        } else {
            setEditTextEnabled(true);
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        key = getArguments().getString("key");
        initViewData(DBUtil.getPasswordInfo(key));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_base_password, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //处理点击事件
            case R.id.id_action_base_password_menu_edit:
                if (!idFragmentEditPasswordTitleEt.isEnabled()) {
                    setEditTextEnabled(true);
                    getActivity().invalidateOptionsMenu();
                    isEdit = true;
                } else {
                    setEditTextEnabled(false);
                    getActivity().invalidateOptionsMenu();
                    isEdit = false;
                }
                break;
            case R.id.id_action_base_password_menu_save:
                savePassword(idFragmentEditPasswordTitleEt.getText().toString().trim(),
                        idFragmentEditPasswordPasswordEt.getText().toString().trim(),
                        idFragmentEditePasswordRemarkEt.getText().toString().trim());
                inforClose();
                break;
            case android.R.id.home:
                ((AppCompatActivity) getActivity()).onBackPressed();
                break;
            case R.id.id_action_base_password_menu_delete:
                mDialog = DialogUtil.getDialog(getActivity(), R.string.delete, R.string.is_delete, true, true, positiveClickListener, negativeClickListener);
                mDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    *
    * 设置edittext是否可以编辑
    *
    *
    * */
    public void setEditTextEnabled(boolean enabled) {
        if (enabled) {
            idFragmentEditPasswordTitleEt.setEnabled(true);
            idFragmentEditPasswordPasswordEt.setEnabled(true);
            idFragmentEditePasswordRemarkEt.setEnabled(true);
        } else {
            idFragmentEditPasswordTitleEt.setEnabled(false);
            idFragmentEditPasswordPasswordEt.setEnabled(false);
            idFragmentEditePasswordRemarkEt.setEnabled(false);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mrealm.close();
    }

    /*
    *
    * 保存密码项
    *
    * */
    public void savePassword(final String title, final String password, final String remarks) {
       /* mrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

            }
        });*/
        mrealm.beginTransaction();
        BasePassword basePassword = mrealm.createObject(BasePassword.class);
        basePassword.setItemTitle(title);
        basePassword.setPassword(password);
        basePassword.setRemarks(remarks);
        mrealm.commitTransaction();

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (isEdit) {
            menu.findItem(R.id.id_action_base_password_menu_delete).setVisible(false);
            menu.findItem(R.id.id_action_base_password_menu_edit).setVisible(false);
            menu.findItem(R.id.id_action_base_password_menu_save).setVisible(true);
        } else {
            menu.findItem(R.id.id_action_base_password_menu_delete).setVisible(true);
            menu.findItem(R.id.id_action_base_password_menu_edit).setVisible(true);
            menu.findItem(R.id.id_action_base_password_menu_save).setVisible(false);
        }
    }

    public void inforClose() {
        ((AppCompatActivity) getActivity()).onBackPressed();
    }


}
