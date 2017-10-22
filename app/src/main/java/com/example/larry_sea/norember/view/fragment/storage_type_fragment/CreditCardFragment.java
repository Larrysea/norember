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
import com.example.larry_sea.norember.entity.account_entity.CreditCard;
import com.example.larry_sea.norember.utill.DBUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Larry-sea on 9/27/2016.
 * <p>
 * 信用卡使用的fragment
 */

public class CreditCardFragment extends Fragment {


    @BindView(R.id.id_fragment_edit_credit_card_toolbar)
    Toolbar idFragmentEditCreditCardToolbar;
    @BindView(R.id.id_fragment_edite_credit_card_account_holder_et)
    EditText idFragmentEditeCreditCardAccountHolderEt;
    @BindView(R.id.id_fragment_edite_credit_card_account_holder_ti)
    TextInputLayout idFragmentEditeCreditCardAccountHolderTi;
    @BindView(R.id.id_fragment_edite_credit_card_number_et)
    EditText idFragmentEditeCreditCardNumberEt;
    @BindView(R.id.id_fragment_edite_credit_card_number_ti)
    TextInputLayout idFragmentEditeCreditCardNumberTi;
    @BindView(R.id.id_fragment_edite_credit_security_number_et)
    EditText idFragmentEditeCreditSecurityNumberEt;
    @BindView(R.id.id_fragment_edite_credit_card_security_number_ti)
    TextInputLayout idFragmentEditeCreditCardSecurityNumberTi;
    @BindView(R.id.id_fragment_edite_credit_country_et)
    EditText idFragmentEditeCreditCountryEt;
    @BindView(R.id.id_fragment_edite_credit_country_ti)
    TextInputLayout idFragmentEditeCreditCountryTi;
    @BindView(R.id.id_fragment_edite_credit_address_et)
    EditText idFragmentEditeCreditAddressEt;
    @BindView(R.id.id_fragment_edite_credit_address_ti)
    TextInputLayout idFragmentEditeCreditAddressTi;
    @BindView(R.id.id_fragment_edite_credit_remarks_et)
    EditText idFragmentEditeCreditRemarksEt;
    @BindView(R.id.id_fragment_edite_credit_remarks_ti)
    TextInputLayout idFragmentEditeCreditRemarksTi;
    boolean ISEDIT = false;
    String key;                        //秘钥
    CreditCard mCreditCard;           //信用卡信息
    Realm mRealm;
    /*
    * 删除信息按钮监听器
    *
    * */
    DialogInterface.OnClickListener deleteClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            DBUtil.deleteCreditCardInfo(key);
            dialog.dismiss();
            inforClose();

        }
    };
    /*
    * 保存信息的监听器
    *
    * */
    DialogInterface.OnClickListener saveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            saveCrediteCard();
            dialog.dismiss();
            inforClose();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_credit_card, container, false);
        ButterKnife.bind(this, view);
        initView();
        ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.storage_menu, menu);
    }

    public void initView() {
        idFragmentEditCreditCardToolbar.setTitle(R.string.credit_card);
        idFragmentEditCreditCardToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(idFragmentEditCreditCardToolbar);
        if (getArguments().getString("key") != null) {
            key = getArguments().getString("key");
            mCreditCard = DBUtil.getCrediteCardInfo(key);
            bindViewTodata(mCreditCard);
            setEditeAble(false);
            ISEDIT = true;
        } else {
            ISEDIT = true;
            setEditeAble(ISEDIT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((AppCompatActivity) getActivity()).onBackPressed();
                break;
            case R.id.id_menu_save:
                saveCrediteCard();
                break;
            case R.id.id_menu_delete:
                DialogUtil.showDelteDialog(getActivity(), deleteClickListener, null);
                break;
            case R.id.id_menu_edit:
                setEditeAble(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    * 保存信用卡信息
    *
    * */
    private void saveCrediteCard() {
        if (checkCreditCard(initOrUpdateCreditCard())) {
            key = idFragmentEditeCreditCardNumberEt.getText().toString();
            DBUtil.saveInfo(initOrUpdateCreditCard());
            setEditeAble(false);
            ISEDIT = true;
            ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (ISEDIT) {
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
    * 初始化信用卡信息  或者是修改信用卡信息
    *
    * */
    public CreditCard initOrUpdateCreditCard() {
        if (mCreditCard == null) {
            mCreditCard = new CreditCard();
            mCreditCard.setUuid(UUID.randomUUID().toString());
        } else if (key != null && mCreditCard != null) {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
        }
        if (!idFragmentEditeCreditCardAccountHolderEt.getText().toString().isEmpty())
            mCreditCard.setAccountHolder(idFragmentEditeCreditCardAccountHolderEt.getText().toString());
        if (!idFragmentEditeCreditCardNumberEt.getText().toString().isEmpty())
            mCreditCard.setCardNumber(idFragmentEditeCreditCardNumberEt.getText().toString());
        if (!idFragmentEditeCreditSecurityNumberEt.getText().toString().isEmpty())
            mCreditCard.setSecurityNumber(idFragmentEditeCreditSecurityNumberEt.getText().toString());
        if (!idFragmentEditeCreditCountryEt.getText().toString().isEmpty())
            mCreditCard.setCountry(idFragmentEditeCreditCountryEt.getText().toString());
        if (!idFragmentEditeCreditAddressEt.getText().toString().isEmpty())
            mCreditCard.setAddress(idFragmentEditeCreditAddressEt.getText().toString());
        if (!idFragmentEditeCreditRemarksEt.getText().toString().isEmpty())
            mCreditCard.setRemarks(idFragmentEditeCreditRemarksEt.getText().toString());

        if (key != null) {
            mRealm.commitTransaction();
            mRealm.close();
        }
        return mCreditCard;
    }

    public void bindViewTodata(CreditCard creditCard) {
        if (creditCard.getAccountHolder() != null)
            idFragmentEditeCreditCardAccountHolderEt.setText(creditCard.getAccountHolder());
        if (creditCard.getCardNumber() != null)
            idFragmentEditeCreditCardNumberEt.setText(creditCard.getCardNumber());
        if (creditCard.getSecurityNumber() != null)
            idFragmentEditeCreditSecurityNumberEt.setText(creditCard.getSecurityNumber());
        if (creditCard.getCountry() != null)
            idFragmentEditeCreditCountryEt.setText(creditCard.getCountry());
        if (creditCard.getAddress() != null)
            idFragmentEditeCreditAddressEt.setText(creditCard.getAddress());
        if (creditCard.getRemarks() != null)
            idFragmentEditeCreditRemarksEt.setText(creditCard.getRemarks());

    }

    public void setEditeAble(boolean isEditable) {
        idFragmentEditeCreditCardAccountHolderEt.setEnabled(isEditable);
        idFragmentEditeCreditCardNumberEt.setEnabled(isEditable);
        idFragmentEditeCreditSecurityNumberEt.setEnabled(isEditable);
        idFragmentEditeCreditCountryEt.setEnabled(isEditable);
        idFragmentEditeCreditAddressEt.setEnabled(isEditable);
        idFragmentEditeCreditRemarksEt.setEnabled(isEditable);
        ISEDIT = !ISEDIT;
        ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
    }

    public void inforClose() {
        ((AppCompatActivity) getActivity()).onBackPressed();
    }


    /*
    * 检查信用卡里面的信息是否为空
    *
    * */
    public boolean checkCreditCard(CreditCard creditCard) {
        if (creditCard != null) {
            if (creditCard.getAccountHolder() == null) {
                Toast.makeText(getActivity(), R.string.credit_card_owner_cant_empty, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (creditCard.getCardNumber() == null) {
                Toast.makeText(getActivity(), R.string.credit_card_nubmer_cant_empty, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public void showWetherSaveDialog() {
        DialogUtil.showWetherSaveDialog(getActivity(), saveClickListener, null);
    }


    public boolean isEditeAble() {
        return idFragmentEditeCreditCardAccountHolderEt.isEnabled();
    }

}
