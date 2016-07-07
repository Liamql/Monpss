package com.example.administrator.monpss;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /** Notification构造器 */
    NotificationCompat.Builder mBuilder;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "时间到！", Toast.LENGTH_LONG).show();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        String miss_name = intent.getExtras().getString("miss_name");
        showNotify(context,"任务提醒","该做 "+ miss_name + " 的时间到了！",date);
    }

    public void showNotify(Context context,String name,String mess,String sendtime){

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //先设定RemoteViews
        RemoteViews view_custom = new RemoteViews(context.getPackageName(), R.layout.noti_custom);
        //设置对应IMAGEVIEW的ID的资源图片
        view_custom.setImageViewResource(R.id.custom_icon, R.drawable.logo1);
//		view_custom.setInt(R.id.custom_icon,"setBackgroundResource",R.drawable.icon);
        view_custom.setTextViewText(R.id.tv_custom_title, name);
        view_custom.setTextViewText(R.id.tv_custom_content, mess);
        view_custom.setTextViewText(R.id.tv_custom_time, sendtime);
//		view_custom.setTextViewText(R.id.tv_custom_time, String.valueOf(System.currentTimeMillis()));
        //设置显示
//		view_custom.setViewVisibility(R.id.tv_custom_time, View.VISIBLE);
//		view_custom.setLong(R.id.tv_custom_time,"setTime", System.currentTimeMillis());//不知道为啥会报错，过会看看日志
        //设置number
//		NumberFormat num = NumberFormat.getIntegerInstance();
//		view_custom.setTextViewText(R.id.tv_custom_num, num.format(this.number));

        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContent(view_custom)
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setTicker(name + ":" + mess)
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(false)//不是正在进行的   true为正在进行  效果和.flag一样
                .setSmallIcon(R.drawable.logo1);
//		mNotificationManager.notify(notifyId, mBuilder.build());
        Notification notify = mBuilder.build();
        notify.contentView = view_custom;
        manager.notify(200,notify);
//		Notification notify = new Notification();
//		notify.icon = R.drawable.icon;
//		notify.contentView = view_custom;
//		notify.contentIntent = getDefalutIntent(Notification.FLAG_AUTO_CANCEL);
    }

}
