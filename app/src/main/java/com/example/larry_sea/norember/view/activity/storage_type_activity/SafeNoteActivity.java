package com.example.larry_sea.norember.view.activity.storage_type_activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.account_entity.SafeNote;
import com.example.larry_sea.norember.utill.DBUtil;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Larry-sea on 9/24/2016.
 * <p>
 * <p>
 * 安全笔记类activity
 */

public class SafeNoteActivity extends AppCompatActivity {
    @BindView(R.id.id_activity_safe_note_toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_activity_safe_note_content_et)
    EditText contentEt;
    @BindView(R.id.id_activity_safe_note_title_et)
    EditText titleEt;
    SafeNote msafeNote;
    boolean ISEDIT = false;                         //表示是否是编辑view是为true
    Realm mrealm;
    int category;                                   //目录分类
    int subMenuSelectId = 0;                        //子菜单选中的id
    boolean isLocked = false;                       //是否被锁屏
    String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_note);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        initView();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_safe_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    DialogInterface.OnClickListener deleteClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            DBUtil.delteSafeNote(key);
            inforClose();
        }
    };

    /*
    *
    * 在menu点击之后会调用
    *
    * */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //目录选择


        switch (item.getItemId()) {
            case R.id.id_menu_category_database:
                category = R.string.database;
                subMenuSelectId = item.getItemId();
                toolbar.setTitle(R.string.database);
                break;
            case R.id.id_menu_category_driver_license:
                category = R.string.driver_license;
                subMenuSelectId = item.getItemId();
                toolbar.setTitle(R.string.driver_license);
                break;
            case R.id.id_menu_category_membercard:
                category = R.string.member_card;
                subMenuSelectId = item.getItemId();
                toolbar.setTitle(R.string.member_card);
                break;
            case R.id.id_menu_category_passport:
                category = R.string.passport;
                subMenuSelectId = item.getItemId();
                toolbar.setTitle(R.string.passport);
                break;
            case R.id.id_menu_category_server:
                category = R.string.server;
                subMenuSelectId = item.getItemId();
                toolbar.setTitle(R.string.server);
                break;
            case R.id.id_menu_category_social_insurance:
                category = R.string.social_insurance;
                subMenuSelectId = item.getItemId();
                toolbar.setTitle(R.string.social_insurance);
                break;
            case R.id.id_menu_category_software_license:
                category = R.string.soft_ware_license;
                subMenuSelectId = item.getItemId();
                toolbar.setTitle(R.string.soft_ware_license);
                break;
            case R.id.id_menu_category_default:
                category = R.string.safe_note;
                subMenuSelectId = item.getItemId();
                toolbar.setTitle(R.string.safe_note);
                break;
            case R.id.id_menu_save:
                if (saveSafeNoteOrUpdate() != null) {
                    DBUtil.saveInfo(saveSafeNoteOrUpdate());
                    inforClose();
                }
                ISEDIT = false;
                break;
            case R.id.id_menu_edit:
                contentEt.setEnabled(true);
                titleEt.setEnabled(true);
                ISEDIT = false;
                invalidateOptionsMenu();
                break;
            case R.id.id_menu_delete:
                DialogUtil.showDelteDialog(SafeNoteActivity.this, deleteClickListener, null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (ISEDIT) {
            menu.findItem(R.id.id_menu_edit).setVisible(true);
            menu.findItem(R.id.id_menu_category).setVisible(false);
            menu.findItem(R.id.id_menu_save).setVisible(false);
            menu.findItem(R.id.id_menu_delete).setVisible(true);
        } else {
            menu.findItem(R.id.id_menu_save).setVisible(true);
            menu.findItem(R.id.id_menu_edit).setVisible(false);
            menu.findItem(R.id.id_menu_category).setVisible(true);
            menu.findItem(R.id.id_menu_delete).setVisible(false);

        }
        return true;
    }


    /*
    *
    *
    * 数据与界面绑定
    *
    * */
    public void bindView(SafeNote safeNote) {
        if (safeNote != null) {
            toolbar.setTitle(safeNote.getCategory());
            if (safeNote.getNoteContent() != null)
                contentEt.setText(safeNote.getNoteContent());
            if (safeNote.getItemTitle() != null)
                titleEt.setText(safeNote.getItemTitle());
            if (safeNote.getCategory() != null)
                toolbar.setTitle(safeNote.getCategory());
        }


    }

    /*
    * 关闭this activity
    *
    * */
    public void inforClose() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mrealm != null)
            mrealm.close();
    }

    public SafeNote saveSafeNoteOrUpdate() {

        if (key == null) {
            msafeNote = new SafeNote();
            msafeNote.setUuid(UUID.randomUUID().toString());
        } else {
            mrealm = Realm.getDefaultInstance();
            mrealm.beginTransaction();
        }
        if (!titleEt.getText().toString().isEmpty()) {
            msafeNote.setItemTitle(titleEt.getText().toString().trim());
            if (category != 0) {
                msafeNote.setCategory(getResources().getString(category));
            } else {
                msafeNote.setCategory(getString(R.string.safe_note));
            }
            if (!contentEt.getText().toString().isEmpty()) {
                msafeNote.setNoteContent(contentEt.getText().toString());
            }

            if (key != null && msafeNote != null) {
                mrealm.commitTransaction();
            }
            return msafeNote;
        } else {
            Toast.makeText(this, R.string.title_cant_empty, Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    /*
    *
    * 初始化界面
    *
    *
    * 并且绑定数据与界面
    *
    * */
    public void initView() {

        toolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        toolbar.setTitle(R.string.safe_note);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inforClose();
            }
        });
        if (getIntent().getExtras() != null) {
            key = getIntent().getExtras().getString("key");
            msafeNote = DBUtil.getSafenoteInfor(getIntent().getExtras().getString("key"));
            bindView(msafeNote);
            ISEDIT = true;
        } else {
            contentEt.setEnabled(true);
            titleEt.setEnabled(true);
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (subMenuSelectId != 0) {
            menu.findItem(subMenuSelectId).setChecked(true);
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && titleEt.isEnabled()) {
            wetherSaveSafeNote();
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
    * 询问是否保存笔记信息
    *
    * */
    private void wetherSaveSafeNote() {
        AlertDialog dialog = null;
        if (ISEDIT) {
            DialogInterface.OnClickListener potiveClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (saveSafeNoteOrUpdate() != null) {
                        inforClose();
                    }
                    dialog.dismiss();
                }
            };

            dialog = DialogUtil.getDialog(this, R.string.save_change, R.string.is_save_change, true, true, potiveClickListener, null);
            dialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(SafeNoteActivity.this, LockScreenActivity.class), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            isLocked = false;
        } else if (resultCode == -1) {
            isLocked = true;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationUtil.isApplicationInBackground(SafeNoteActivity.this)) {
            isLocked = true;
        }
    }




}
