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
import com.example.larry_sea.norember.entity.account_entity.BankCard;
import com.example.larry_sea.norember.utill.DBUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Larry-sea on 9/20/2016.
 * <p/>
 * 修改银行卡详细信息的fragment
 */
public class BankCardFragment extends Fragment {


    Boolean isEdit = false;          //是否可以编辑
    Realm mRealm;
    String primarkey;                //该界面展示的主键
    BankCard bankCard;               //银行卡
    @BindView(R.id.id_fragment_edit_bank_card_toolbar)
    Toolbar idFragmentEditBankCardToolbar;
    @BindView(R.id.id_fragment_edite_bank_card_account_et)
    EditText idFragmentEditeBankCardAccountEt;
    @BindView(R.id.id_fragment_edite_bank_card_account_ti)
    TextInputLayout idFragmentEditeBankCardAccountTi;
    @BindView(R.id.id_fragment_edite_bank_card_number_et)
    EditText idFragmentEditeBankCardNumberEt;
    @BindView(R.id.id_fragment_edite_bank_card_number_ti)
    TextInputLayout idFragmentEditeBankCardNumberTi;
    @BindView(R.id.id_fragment_edite_bank_card_account_bank_password_et)
    EditText idFragmentEditeBankCardAccountBankPasswordEt;
    @BindView(R.id.id_fragment_edite_bank_card_account_bank_password_ti)
    TextInputLayout idFragmentEditeBankCardAccountBankPasswordTi;
    @BindView(R.id.id_fragment_edit_bank_card_iban_number_et)
    EditText idFragmentEditBankCardIbanNumberEt;
    @BindView(R.id.id_fragment_edit_bank_card_iban_number_ti)
    TextInputLayout idFragmentEditBankCardIbanNumberTi;
    @BindView(R.id.id_fragment_edite_bank_card_iban_account_name_et)
    EditText idFragmentEditeBankCardIbanAccountNameEt;
    @BindView(R.id.id_fragment_edite_bank_card_iban_account_name_ti)
    TextInputLayout idFragmentEditeBankCardIbanAccountNameTi;
    @BindView(R.id.id_fragment_edite_bank_card_bicswift_et)
    EditText idFragmentEditeBankCardBicswiftEt;
    @BindView(R.id.id_fragment_edite_bank_card_bicswift_ti)
    TextInputLayout idFragmentEditeBankCardBicswiftTi;
    /*
    * 确定按钮的监听器
    *
    * */
    DialogInterface.OnClickListener deleteClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (primarkey != null) {
                DBUtil.deleteBankCard(primarkey);
            }
            dialog.dismiss();
            inforClose();
        }
    };

    DialogInterface.OnClickListener saveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            saveBankCard();
            dialog.dismiss();
            inforClose();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_bank_card, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        if (getArguments().getString("key") != null) {
            isEdit = true;
            primarkey = getArguments().getString("key");
            bankCard = DBUtil.getBankCardInfo(getArguments().getString("key"));
            bindDataToView(bankCard);
        } else {
            setEditTextEditAble(true);
        }
        setHasOptionsMenu(true);
        idFragmentEditBankCardToolbar.setTitle(R.string.bank_card);
        idFragmentEditBankCardToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        ((AppCompatActivity) getActivity()).setSupportActionBar(idFragmentEditBankCardToolbar);
        ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.storage_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_save:
                saveBankCard();
                break;
            case R.id.id_menu_edit:
                setEditTextEditAble(isEdit);
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

    /*
    *
    * 保存银行卡信息
    * */
    private void saveBankCard() {
        if (checkIsCanSave(initBankCardInfor(bankCard))) {
            DBUtil.saveInfo(initBankCardInfor(bankCard));
            primarkey = idFragmentEditeBankCardNumberEt.getText().toString();
            setEditTextEditAble(false);
        }
    }

    /*
    *
    * 保存银行卡信息
    *
    * @return 保存成功返回true否则false
    *
    * */
    public BankCard initBankCardInfor(@Nullable BankCard bankCard) {

        if (bankCard == null) {
            bankCard = new BankCard();
            bankCard.setUuid(UUID.randomUUID().toString());
        } else if (bankCard != null && primarkey != null) {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
        }
        bankCard.setBankName(idFragmentEditeBankCardAccountEt.getText().toString());
        if (!idFragmentEditeBankCardNumberEt.getText().toString().isEmpty()) {
            bankCard.setCardNumber(idFragmentEditeBankCardNumberEt.getText().toString());
        }
        if (!idFragmentEditeBankCardAccountBankPasswordEt.getText().toString().isEmpty()) {
            bankCard.setPassword(idFragmentEditeBankCardAccountBankPasswordEt.getText().toString());
        }
        if (!idFragmentEditBankCardIbanNumberEt.getText().toString().isEmpty()) {
            bankCard.setIban(idFragmentEditBankCardIbanNumberEt.getText().toString());
        }
        if (!idFragmentEditeBankCardIbanAccountNameEt.getText().toString().isEmpty()) {
            bankCard.setIbanName(idFragmentEditeBankCardIbanAccountNameEt.getText().toString());
        }
        if (!idFragmentEditeBankCardBicswiftEt.getText().toString().isEmpty()) {
            bankCard.setBicOrswift(idFragmentEditeBankCardBicswiftEt.getText().toString());
        }
        if (bankCard != null && primarkey != null) {
            mRealm.commitTransaction();
            mRealm.close();
        }
        return bankCard;

    }

    /*
    *
    * 通知关闭
    * */
    public void inforClose() {
        ((AppCompatActivity) getActivity()).onBackPressed();

    }

    /*
    *
    * 设置界面是否可以编辑
    *
    *
    * */
    public void setEditTextEditAble(boolean isEditeAble) {
        idFragmentEditeBankCardAccountEt.setEnabled(isEditeAble);
        idFragmentEditBankCardToolbar.setEnabled(isEditeAble);
        idFragmentEditeBankCardNumberEt.setEnabled(isEditeAble);
        idFragmentEditeBankCardAccountBankPasswordEt.setEnabled(isEditeAble);
        idFragmentEditBankCardIbanNumberEt.setEnabled(isEditeAble);
        idFragmentEditeBankCardIbanAccountNameEt.setEnabled(isEditeAble);
        idFragmentEditeBankCardBicswiftEt.setEnabled(isEditeAble);
        isEdit = !isEditeAble;
        ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
    }

    /*
    *
    *刷新界面
    *
    * */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (isEdit) {
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
    * 将数据与view相绑定
    *
    * */
    public void bindDataToView(BankCard bankCard) {
        if (bankCard.getTitle() != null) {
            idFragmentEditeBankCardAccountEt.setText(bankCard.getTitle());
        }
        if (bankCard.getCardNumber() != null) {
            idFragmentEditeBankCardNumberEt.setText(bankCard.getCardNumber());
        }
        if (bankCard.getPassword() != null) {
            idFragmentEditeBankCardAccountBankPasswordEt.setText(bankCard.getPassword());
        }
        if (bankCard.getIban() != null) {
            idFragmentEditBankCardIbanNumberEt.setText(bankCard.getIban());
        }
        if (bankCard.getBicOrswift() != null) {
            idFragmentEditeBankCardBicswiftEt.setText(bankCard.getBicOrswift());
        }
        if (bankCard.getIbanName() != null) {
            idFragmentEditeBankCardIbanAccountNameEt.setText(bankCard.getIbanName());
        }
    }


    /*
    * 测试数据是否可以保存
    *
    * */
    public boolean checkIsCanSave(BankCard bankCard) {
        if (idFragmentEditeBankCardAccountEt.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.bank_account_name_cant_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (idFragmentEditeBankCardNumberEt.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.bank_card_nubmer_cant_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /*
    * 询问是否保存信息
    * */
    public void showWetherSaveDialog() {
        DialogUtil.showWetherSaveDialog(getActivity(), saveClickListener, null);
    }

    /*
    * 检查是否可编辑
    *
    * */
    public boolean isEditeAble() {
        return idFragmentEditeBankCardAccountEt.isEnabled();
    }

}
