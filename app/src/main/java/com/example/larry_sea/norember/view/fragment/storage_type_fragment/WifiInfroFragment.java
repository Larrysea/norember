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
import com.example.larry_sea.norember.entity.account_entity.Wifiinfor;
import com.example.larry_sea.norember.utill.DBUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Larry-sea on 9/27/2016.
 * <p>
 * <p>
 * wifi信息fragment
 */

public class WifiInfroFragment extends Fragment {
    @BindView(R.id.id_fragment_edit_wifi_infor_toolbar)
    Toolbar idFragmentEditWifiInforToolbar;
    @BindView(R.id.id_fragment_edite_wifi_name_et)
    EditText idFragmentEditeWifiNameEt;
    @BindView(R.id.id_fragment_edite_wifi_name_ti)
    TextInputLayout idFragmentEditeWifiNameTi;
    @BindView(R.id.id_fragment_edite_wifi_password_et)
    EditText idFragmentEditeWifiPasswordEt;
    @BindView(R.id.id_fragment_edite_wifi_password_ti)
    TextInputLayout idFragmentEditeWifiPasswordTi;
    @BindView(R.id.id_fragment_edite_wifi_back_manage_address_et)
    EditText idFragmentEditeWifiBackManageAddressEt;
    @BindView(R.id.id_fragment_edite_wifi_back_manage_address_ti)
    TextInputLayout idFragmentEditeWifiBackManageAddressTi;
    @BindView(R.id.id_fragment_edite_wifi_back_manage_account_et)
    EditText idFragmentEditeWifiBackManageAccountEt;
    @BindView(R.id.id_fragment_edite_wifi_back_manage_account_ti)
    TextInputLayout idFragmentEditeWifiBackManageAccountTi;
    @BindView(R.id.id_fragment_edite_wifi_back_manage_password_et)
    EditText idFragmentEditeWifiBackManagePasswordEt;
    @BindView(R.id.id_fragment_edite_wifi_back_manage_password_ti)
    TextInputLayout idFragmentEditeWifiBackManagePasswordTi;
    @BindView(R.id.id_fragment_edite_wifi_isp_account_et)
    EditText idFragmentEditeWifiIspAccountEt;
    @BindView(R.id.id_fragment_edite_wifi_isp_account_ti)
    TextInputLayout idFragmentEditeWifiIspAccountTi;
    @BindView(R.id.id_fragment_edite_wifi_isp_password_et)
    EditText idFragmentEditeWifiIspPasswordEt;
    @BindView(R.id.id_fragment_edite_wifi_isp_password_ti)
    TextInputLayout idFragmentEditeWifiIspPasswordTi;
    public static boolean ISEDITABLE = false;                   //是否可以编辑
    String key;                                                 //wifi的key
    Realm realm;
    Wifiinfor wifiinfor;

    public  boolean isEditAble() {
        return idFragmentEditeWifiNameEt.isEnabled();
    }


   /*
    *
    * 保存按钮的监听器
    *
    * */

    DialogInterface.OnClickListener saveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            saveWifiInfor();
            inforClose();
        }
    };


    /*
     * 确定按钮的监听器
     *
     * */
    DialogInterface.OnClickListener deleteClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            DBUtil.deleteWifiInfo(key);
            dialog.dismiss();
            inforClose();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_wifi_infor, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.storage_menu, menu);

    }

    public void initView() {
        idFragmentEditWifiInforToolbar.setTitle(R.string.wifi);
        idFragmentEditWifiInforToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        ((AppCompatActivity) getActivity()).setSupportActionBar(idFragmentEditWifiInforToolbar);
        setHasOptionsMenu(true);
        if (getArguments().getString("key") != null) {
            key = getArguments().getString("key");
            wifiinfor = DBUtil.getWifiInfo(key);
            bindDataToView(wifiinfor);
            ISEDITABLE = true;
        } else {
            setEditAble(true);
        }
        ((AppCompatActivity) getActivity()).invalidateOptionsMenu();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_save:
                saveWifiInfor();
                break;
            case R.id.id_menu_edit:
                setEditAble(ISEDITABLE);
                break;
            case R.id.id_menu_delete:
                DialogUtil.showDelteDialog(getActivity(), deleteClickListener, null);
                break;
            case android.R.id.home:
                inforClose();
                break;

        }
        ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    public void setEditAble(boolean isEditeAble) {
        idFragmentEditeWifiNameEt.setEnabled(isEditeAble);
        idFragmentEditeWifiPasswordEt.setEnabled(isEditeAble);
        idFragmentEditeWifiBackManageAccountEt.setEnabled(isEditeAble);
        idFragmentEditeWifiBackManageAddressEt.setEnabled(isEditeAble);
        idFragmentEditeWifiBackManagePasswordEt.setEnabled(isEditeAble);
        idFragmentEditeWifiIspAccountEt.setEnabled(isEditeAble);
        idFragmentEditeWifiIspPasswordEt.setEnabled(isEditeAble);
        ISEDITABLE = false;
        ((AppCompatActivity) getActivity()).invalidateOptionsMenu();

    }

    public void bindDataToView(Wifiinfor wifiinfor) {
        if (wifiinfor.getWifiName() != null)
            idFragmentEditeWifiNameEt.setText(wifiinfor.getWifiName());
        if (wifiinfor.getWifiPassword() != null)
            idFragmentEditeWifiPasswordEt.setText(wifiinfor.getWifiPassword());
        if (wifiinfor.getAdminAccount() != null)
            idFragmentEditeWifiBackManageAccountEt.setText(wifiinfor.getAdminAccount());
        if (wifiinfor.getAdminPassword() != null)
            idFragmentEditeWifiBackManagePasswordEt.setText(wifiinfor.getAdminPassword());
        if (wifiinfor.getWifiIp() != null)
            idFragmentEditeWifiBackManageAddressEt.setText(wifiinfor.getWifiIp());
        if (wifiinfor.getInternetProviderAccount() != null)
            idFragmentEditeWifiIspAccountEt.setText(wifiinfor.getInternetProviderAccount());
        if (wifiinfor.getInternetProviderPassword() != null)
            idFragmentEditeWifiIspPasswordEt.setText(wifiinfor.getInternetProviderPassword());


    }

    public Wifiinfor initWifiInfoOrUpdateInfor(@Nullable Wifiinfor wifiinfor) {
        if (wifiinfor == null) {
            wifiinfor = new Wifiinfor();
            wifiinfor.setUuid(UUID.randomUUID().toString());
        } else if (wifiinfor != null && key != null) {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
        }
        if (!idFragmentEditeWifiNameEt.getText().toString().isEmpty())
            wifiinfor.setWifiName(idFragmentEditeWifiNameEt.getText().toString());
        if (!idFragmentEditeWifiPasswordEt.getText().toString().isEmpty())
            wifiinfor.setWifiPassword(idFragmentEditeWifiPasswordEt.getText().toString());
        if (!idFragmentEditeWifiBackManageAddressEt.getText().toString().isEmpty())
            wifiinfor.setAdminAccount(idFragmentEditeWifiBackManageAccountEt.getText().toString());
        if (!idFragmentEditeWifiBackManagePasswordEt.getText().toString().isEmpty())
            wifiinfor.setAdminPassword(idFragmentEditeWifiBackManagePasswordEt.getText().toString());
        if (!idFragmentEditeWifiIspAccountEt.getText().toString().isEmpty())
            wifiinfor.setInternetProviderAccount(idFragmentEditeWifiIspAccountEt.getText().toString());
        if (!idFragmentEditeWifiIspPasswordEt.getText().toString().isEmpty())
            wifiinfor.setInternetProviderPassword(idFragmentEditeWifiIspPasswordEt.getText().toString());
        if (wifiinfor != null && key != null) {
            realm.commitTransaction();
            realm.close();
        }
        return wifiinfor;
    }

    public void inforClose() {
        ((AppCompatActivity) getActivity()).onBackPressed();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (ISEDITABLE) {
            menu.findItem(R.id.id_menu_edit).setVisible(true);
            menu.findItem(R.id.id_menu_delete).setVisible(true);
            menu.findItem(R.id.id_menu_save).setVisible(false);
        } else {
            menu.findItem(R.id.id_menu_edit).setVisible(false);
            menu.findItem(R.id.id_menu_delete).setVisible(false);
            menu.findItem(R.id.id_menu_save).setVisible(true);
        }
    }


    /*
    *
    * 检查是否可以保存
    * */
    public boolean checkIsSaveAble(Wifiinfor wifiInfor) {
        if (wifiInfor.getWifiName() == null) {
            Toast.makeText(getActivity(), R.string.wifi_name_cant_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (wifiInfor.getWifiPassword() == null) {
            Toast.makeText(getActivity(), R.string.wifi_password_cant_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /*保存wifi信息*/
    public void saveWifiInfor() {
        if (checkIsSaveAble(initWifiInfoOrUpdateInfor(wifiinfor))) {
            DBUtil.saveInfo(initWifiInfoOrUpdateInfor(wifiinfor));
            key = idFragmentEditeWifiNameEt.getText().toString();
            setEditAble(false);
            ISEDITABLE = true;
            ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
        }
    }

    /*
    *
    * 显示是否保存dialog
    * */
    public void showWetherSaveDialog() {
        DialogUtil.showWetherSaveDialog(getActivity(), saveClickListener, null);
    }
}
