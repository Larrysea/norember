package com.example.larry_sea.norember.callback_interface;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Larry-sea on 2016/11/6.
 * <p>
 * drawable中的图片点击事件处理
 */

public class DrawableClickListener {


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
    static public void bindTextDrawableListener(final EditText editText, final int rightOrLeft, final loginDrawClickImpl loginDrawClick) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Drawable[] drawables = editText.getCompoundDrawables();
                Drawable drawable = drawables[rightOrLeft];
                if (drawable == null) {
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if ((event.getX() > editText.getWidth() - drawable.getIntrinsicWidth() - editText.getPaddingRight()) && (event.getX() < editText.getWidth() - editText.getPaddingRight())) {
                        loginDrawClick.onClick();
                    }
                }
                return true;
            }

        });
    }


}
