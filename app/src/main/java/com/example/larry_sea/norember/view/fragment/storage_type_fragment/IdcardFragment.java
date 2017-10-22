package com.example.larry_sea.norember.view.fragment.storage_type_fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.account_entity.IdentityCard;
import com.example.larry_sea.norember.utill.DBUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Larry-sea on 9/27/2016.
 * <p>
 * 修改身份证的fragment
 */

public class IdcardFragment extends Fragment {
    @BindView(R.id.id_fragment_edit_idcard_toolbar)
    Toolbar idFragmentEditIdcardToolbar;
    @BindView(R.id.id_fragment_edite_id_card_name_et)
    EditText idFragmentEditeIdCardNameEt;
    @BindView(R.id.id_fragment_edite_id_card_name_ti)
    TextInputLayout idFragmentEditeIdCardNameTi;
    @BindView(R.id.id_fragment_edite_id_card_number_et)
    EditText idFragmentEditeIdCardNumberEt;
    @BindView(R.id.id_fragment_edite_id_card_number_ti)
    TextInputLayout idFragmentEditeIdCardNumberTi;
    @BindView(R.id.id_fragment_edite_id_card_issuing_authority_et)
    EditText idFragmentEditeIdCardIssuingAuthorityEt;
    @BindView(R.id.id_fragment_edite_id_card_issuing_authority_ti)
    TextInputLayout idFragmentEditeIdCardIssuingAuthorityTi;
    @BindView(R.id.id_fragment_edite_id_card_address_et)
    EditText idFragmentEditeIdCardAddressEt;
    @BindView(R.id.id_fragment_edite_id_card_address_ti)
    TextInputLayout idFragmentEditeIdCardAddressTi;
    @BindView(R.id.id_fragment_edite_id_card_country_et)
    EditText idFragmentEditeIdCardCountryEt;
    @BindView(R.id.id_fragment_edite_id_card_country_ti)
    TextInputLayout idFragmentEditeIdCardCountryTi;
    @BindView(R.id.id_fragment_edite_id_card_strt_time_et)
    EditText idFragmentEditeIdCardStartTimeEt;
    @BindView(R.id.id_fragment_edite_id_card_endtime_et)
    EditText idFragmentEditeIdCardEndtimeEt;
    boolean ISEDIT = false;                              //是否可以编辑
    String key;
    IdentityCard identityCard;                           //银行卡信息
    Realm realm;
    DatePickerDialog mDatePickerDialog;                 //时间选择器
    /*
    * 确定按钮的监听器
    *
    * */
    DialogInterface.OnClickListener deleteClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            DBUtil.delteIdentityCardInfo(key);
            dialog.dismiss();
            inforClose();
        }
    };
    /*
    * 取消按钮的监听器
    *
    * */
    DialogInterface.OnClickListener saveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            saveIdcard();
            inforClose();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_idcard, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        if (getArguments().getString("key") != null) {
            key = getArguments().getString("key");
            identityCard = DBUtil.getIdentityInfo(key);
            bindDataToView(identityCard);
            ISEDIT = true;
        } else {
            setEditeAble(true);
            ISEDIT = false;
        }
        idFragmentEditIdcardToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        idFragmentEditIdcardToolbar.setTitle(R.string.id_card);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(idFragmentEditIdcardToolbar);
        ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.storage_menu, menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((AppCompatActivity) getActivity()).onBackPressed();
                break;
            case R.id.id_menu_save:
                saveIdcard();
                break;
            case R.id.id_menu_delete:
                DialogUtil.showDelteDialog(getActivity(), deleteClickListener, null);
            case R.id.id_menu_edit:
                setEditeAble(true);
                ISEDIT = false;
                ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /*
    *
    * 初始化身份证的信息
    *
    *
    *或者修改它
    * */
    public IdentityCard initIdentityCard() {
        if (identityCard == null) {
            identityCard = new IdentityCard();
            identityCard.setUuid(UUID.randomUUID().toString());
        } else if (identityCard != null && key != null) {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
        }
        if (!idFragmentEditeIdCardNameEt.getText().toString().isEmpty())
            identityCard.setName(idFragmentEditeIdCardNameEt.getText().toString());
        if (!idFragmentEditeIdCardNumberEt.getText().toString().isEmpty())
            identityCard.setIdentityNumber(idFragmentEditeIdCardNumberEt.getText().toString());
        if (!idFragmentEditeIdCardIssuingAuthorityEt.getText().toString().isEmpty())
            identityCard.setIssuingAuthority(idFragmentEditeIdCardIssuingAuthorityEt.getText().toString());
        if (!idFragmentEditeIdCardCountryEt.getText().toString().isEmpty())
            identityCard.setCountry(idFragmentEditeIdCardCountryEt.getText().toString());
        if (!idFragmentEditeIdCardStartTimeEt.getText().toString().isEmpty())
            identityCard.setStartTermOfDate(idFragmentEditeIdCardStartTimeEt.getText().toString());
        if (!idFragmentEditeIdCardEndtimeEt.getText().toString().isEmpty())
            identityCard.setEndTermOfDate(idFragmentEditeIdCardEndtimeEt.getText().toString());
        if (key != null && identityCard != null) {
            realm.commitTransaction();
            realm.close();
        }
        return identityCard;
    }

    public void inforClose() {
        ((AppCompatActivity) getActivity()).onBackPressed();
    }

    public void bindDataToView(IdentityCard identityCard) {
        if (identityCard.getName() != null)
            idFragmentEditeIdCardNameEt.setText(identityCard.getName());
        if (identityCard.getIdentityNumber() != null)
            idFragmentEditeIdCardNumberEt.setText(identityCard.getIdentityNumber());
        if (identityCard.getIssuingAuthority() != null)
            idFragmentEditeIdCardIssuingAuthorityEt.setText(identityCard.getIssuingAuthority());
        if (identityCard.getAddress() != null)
            idFragmentEditeIdCardAddressEt.setText(identityCard.getAddress());
        if (identityCard.getCountry() != null)
            idFragmentEditeIdCardCountryEt.setText(identityCard.getCountry());
        if (identityCard.getStartTermOfDate() != null)
            idFragmentEditeIdCardStartTimeEt.setText(identityCard.getStartTermOfDate());
        if (identityCard.getEndTermOfDate() != null)
            idFragmentEditeIdCardEndtimeEt.setText(identityCard.getEndTermOfDate());


    }


    public void setEditeAble(boolean isEditeAble) {
        idFragmentEditeIdCardNameEt.setEnabled(isEditeAble);
        idFragmentEditeIdCardNumberEt.setEnabled(isEditeAble);
        idFragmentEditeIdCardIssuingAuthorityEt.setEnabled(isEditeAble);
        idFragmentEditeIdCardAddressEt.setEnabled(isEditeAble);
        idFragmentEditeIdCardCountryEt.setEnabled(isEditeAble);
    }


    /*
    * @param rightOrLeft
    * 表示右边或者是左边
    *
    * 2代表是右边
    *
    * 1代表是左边
    *
    *
    * */
    public void bindTextDrawableListener(final EditText editText, final int rightOrLeft) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog = DialogUtil.getDatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (idFragmentEditeIdCardNameEt.isEnabled()) {
                            editText.setText(year + "/" + month + "/" + dayOfMonth + "");
                        }
                        if (mDatePickerDialog != null) {
                            mDatePickerDialog.dismiss();
                        }
                    }
                });
                Drawable[] drawables = editText.getCompoundDrawables();
                Drawable drawable = drawables[rightOrLeft];
                if (drawable == null) {
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if ((event.getX() > editText.getWidth() - drawable.getIntrinsicWidth() - editText.getPaddingRight()) && (event.getX() < editText.getWidth() - editText.getPaddingRight())) {
                        mDatePickerDialog.show();
                    }
                }
                return true;
            }

        });
    }

    /*
    *
    * 绑定两个时间picker的监听器
    *
    * */
    public void initListener() {
        bindTextDrawableListener(idFragmentEditeIdCardEndtimeEt, 2);
        bindTextDrawableListener(idFragmentEditeIdCardStartTimeEt, 2);

    }


    /*
    * 检查是否可以保存
    *
    * */
    public boolean checkIsSaveAble(IdentityCard identityCard) {

        if (identityCard.getName() == null) {
            Toast.makeText(getActivity(), R.string.id_card_name_cant_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (identityCard.getIdentityNumber() == null) {
            Toast.makeText(getActivity(), R.string.id_card_number_cant_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    /*
    *
    * 保存身份证信息
    *
    * */
    public void saveIdcard() {
        if (checkIsSaveAble(initIdentityCard())) {
            DBUtil.saveInfo(initIdentityCard());
            key = initIdentityCard().getIdentityNumber();
            setEditeAble(false);
            ISEDIT = false;
            ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
        }
    }

    public void showWetherSaveDialog() {
        DialogUtil.showWetherSaveDialog(getActivity(), saveClickListener, null);
    }

    public boolean isEditAble() {
        return idFragmentEditeIdCardNameEt.isEnabled();
    }

}
