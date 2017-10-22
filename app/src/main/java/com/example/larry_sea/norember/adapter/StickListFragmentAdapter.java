package com.example.larry_sea.norember.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.account_entity.BankCard;
import com.example.larry_sea.norember.entity.account_entity.BasePassword;
import com.example.larry_sea.norember.entity.account_entity.CreditCard;
import com.example.larry_sea.norember.entity.account_entity.IdentityCard;
import com.example.larry_sea.norember.entity.account_entity.LoginInfor;
import com.example.larry_sea.norember.entity.account_entity.MailInfor;
import com.example.larry_sea.norember.entity.account_entity.SafeNote;
import com.example.larry_sea.norember.entity.account_entity.Wifiinfor;
import com.example.larry_sea.norember.entity.base_entity.BaseStorageItemEntity;
import com.squareup.picasso.Picasso;

import io.realm.RealmResults;

/**
 * Created by Larry-sea on 2016/9/13.
 * <p/>
 * <p/>
 * 主activity 中的fragment
 */
public class StickListFragmentAdapter<T extends BaseStorageItemEntity> extends BaseAdapter {

    final static int SINGLEVIEWHOLDER = 1;
    final static int VIEWHOLDER = 2;
    // List<StorageItemTypeEntity> mdatas;
    LayoutInflater mlayoutInflater;
    Context mcontext;
    RealmResults<T> mdatas;
    CharSequence charSequence = null;
    T mInstance;       //泛型的一个实例


    public StickListFragmentAdapter(RealmResults<T> modealList, Context context) {

        mdatas = modealList;
        mcontext = context;
        mInstance = (T) modealList.get(0);
        mlayoutInflater = LayoutInflater.from(context);


    }


    @Override
    public int getCount() {
        return mdatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mdatas.get(i);
    }

    @Override   //这个就是item显示的id
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        itemViewHolder mitemViewHolder = null;
        SingaleItemViewHolder singaleItemViewHolder = null;
        if (i % 2 != 0) {

            if (view != null) {
                singaleItemViewHolder = (SingaleItemViewHolder) (SingaleItemViewHolder) view.getTag(R.string.singleViewHolder);
            }
            if (singaleItemViewHolder == null) {
                singaleItemViewHolder = new SingaleItemViewHolder();
                view = mlayoutInflater.inflate(R.layout.single_title_storage_item, viewGroup, false);
                singaleItemViewHolder.titleTv = (TextView) view.findViewById(R.id.id_storage_singale_item_title_tv);
                singaleItemViewHolder.titleTv.setText("fasfasdf");
                view.setTag(R.string.singleViewHolder, singaleItemViewHolder);
            }
            singaleItemViewHolder.titleTv.setText("中国建设银行");

        } else {
            if (view == null) {
                mitemViewHolder = new itemViewHolder();
                view = mlayoutInflater.inflate(R.layout.storage_item, viewGroup, false);
                mitemViewHolder.itemIconIv = (ImageView) view.findViewById(R.id.id_storage_item_icon_iv);
                mitemViewHolder.accountTv = (TextView) view.findViewById(R.id.id_storage_item_title_tv);
                mitemViewHolder.titleTv = (TextView) view.findViewById(R.id.id_storage_item_account_tv);
                view.setTag(R.string.viewholder, mitemViewHolder);
            } else {
                if (view.getTag(R.string.viewholder) != null) {
                    mitemViewHolder = (itemViewHolder) view.getTag(R.string.viewholder);
                } else {
                    mitemViewHolder = new itemViewHolder();
                    view = mlayoutInflater.inflate(R.layout.storage_item, viewGroup, false);
                    mitemViewHolder.itemIconIv = (ImageView) view.findViewById(R.id.id_storage_item_icon_iv);
                    mitemViewHolder.accountTv = (TextView) view.findViewById(R.id.id_storage_item_title_tv);
                    mitemViewHolder.titleTv = (TextView) view.findViewById(R.id.id_storage_item_account_tv);
                    view.setTag(R.string.viewholder, mitemViewHolder);


                }
            }
            mitemViewHolder.titleTv.setText(mdatas.get(i).getTitle());
            mitemViewHolder.accountTv.setText(mdatas.get(i).getSubTitle());
            Picasso.with(mcontext).load(getIconResourceId(mInstance)).into(mitemViewHolder.itemIconIv);
        }

        return view;
    }

    /*
    * 获取图片资源id
    *
    * */
    public <T> int getIconResourceId(T instance) {
        Class classType = instance.getClass();
        if (classType.equals(BankCard.class)) {
            return R.mipmap.ic_type_bank_card_medium;
        } else if (classType.equals(BasePassword.class)) {
            return R.mipmap.ic_tyep_database_medium;
        } else if (classType.equals(CreditCard.class)) {
            return R.mipmap.ic_type_credit_card_medium;
        } else if (classType.equals(IdentityCard.class)) {
            return R.mipmap.ic_type_idcard_medium;
        } else if (classType.equals(LoginInfor.class)) {
            return R.mipmap.ic_type_login_medium;
        } else if (classType.equals(MailInfor.class)) {
            return R.mipmap.ic_type_mail_medium;
        } else if (classType.equals(SafeNote.class)) {
            return R.mipmap.ic_type_note_medium;
        } else if (classType.equals(Wifiinfor.class)) {
            return R.mipmap.ic_type_wifi_medium;
        }

        return R.mipmap.ic_type_card_medium;

    }

    class HeaderViewHolder {
        TextView text;
    }

    class itemViewHolder {

        TextView accountTv;                //显示账户名的tv
        TextView titleTv;                 //显示item的数量的tv
        ImageView itemIconIv;            //显示item的icon
    }

    /*
    *
    * 单个itemView 的holder
    *
    * 这种holder与前面的差别是这个题目标签是居中的
    * */
    class SingaleItemViewHolder {

        TextView titleTv;
        ImageView itemIconIv;
    }


}
