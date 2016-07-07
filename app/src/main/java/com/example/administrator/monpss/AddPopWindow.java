package com.example.administrator.monpss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class AddPopWindow extends PopupWindow {
    private View conentView;

    SQLiteDatabase db;
    DBHelper dbh;
    Cursor cursor;


    public AddPopWindow(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popupwindow_setting, null);

        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);


        RelativeLayout toSetting =(RelativeLayout) conentView.findViewById(R.id.setting);
        RelativeLayout del_mission =(RelativeLayout) conentView.findViewById(R.id.del_mission);
        toSetting.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,SettingActivity.class));
                AddPopWindow.this.dismiss();

            }

        } );
        del_mission.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //context.startActivity(new Intent(context,CreatChatRoomActivity.class));
                AddPopWindow.this.dismiss();

            }

        } );


    }

    public AddPopWindow(final Activity context, final String miss_name) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popupwindow_setting, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        this.dbh =new DBHelper(context);
        db =dbh.getWritableDatabase();


        RelativeLayout toSetting =(RelativeLayout) conentView.findViewById(R.id.setting);
        RelativeLayout del_mission =(RelativeLayout) conentView.findViewById(R.id.del_mission);
        toSetting.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,SettingActivity.class));
                AddPopWindow.this.dismiss();

            }

        } );
        del_mission.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                db.execSQL("delete from Mission where level = ? and mission_name = ?",new String[]{"0",miss_name});
                context.startActivity(new Intent(context,GridActivity.class));
                //context.startActivity(new Intent(context,CreatChatRoomActivity.class));
                AddPopWindow.this.dismiss();

            }

        } );


    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }
}
