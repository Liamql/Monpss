package com.example.administrator.monpss;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class notifyActivity extends Activity {

    RelativeLayout set_time;
    RelativeLayout set_swit;
    RelativeLayout set_pri;
    TextView swit1;
    TextView swit2;
    TextView swit3;
    TextView noti_time;
    ImageView btn_back;

    private int mMinute;
    private int mHour;
    private int mDay;
    private int mMonth;
    private int mYear;

    private String miss_name;
    private int level;


    private int isOn = 0;
    private int pri_type = 0;

    SQLiteDatabase db;
    DBHelper dbh;
    Cursor cursor;

    ArrayList<String> pri_list = new ArrayList<String>(){{add("正常");add("高");add("低");}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_layout);

        dbh=new DBHelper(this);
        db=dbh.getWritableDatabase();

        Intent intent = getIntent();
        miss_name = intent.getExtras().getString("miss_name");
        level = intent.getExtras().getInt("level");

        set_time = (RelativeLayout) findViewById(R.id.set_time);
        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(notifyActivity.this, setTimeActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        swit1 = (TextView) findViewById(R.id.swit1);
        swit2 = (TextView) findViewById(R.id.swit2);
        swit3 = (TextView) findViewById(R.id.swit3);
        noti_time = (TextView) findViewById(R.id.noti_time);
        btn_back = (ImageView) findViewById(R.id.iv_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        set_swit = (RelativeLayout) findViewById(R.id.set_swit);
        set_swit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOn == 0)
                {
                    isOn = 1;
                    swit2.setVisibility(View.VISIBLE);
                    swit1.setVisibility(View.GONE);
                    if(!TextUtils.isEmpty(noti_time.getText()))
                    {
                        set_noti();
                    }
                }
                else
                {
                    isOn = 0;
                    swit1.setVisibility(View.VISIBLE);
                    swit2.setVisibility(View.GONE);
                    Intent intent = new Intent(notifyActivity.this, AlarmReceiver.class);
                    PendingIntent sender = PendingIntent.getBroadcast(notifyActivity.this,
                            0, intent, 0);

                    AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                    am.cancel(sender);
                }
            }
        });

        set_pri = (RelativeLayout) findViewById(R.id.set_pri);
        set_pri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pri_type == 0)
                {
                    pri_type = 1;
                    swit3.setText("高");
                    swit3.setTextColor(Color.rgb(255, 0, 0));
                }
                else if(pri_type == 1)
                {
                    pri_type = 2;
                    swit3.setText("低");
                    swit3.setTextColor(Color.rgb(255, 0, 0));
                }
                else
                {
                    pri_type = 0;
                    swit3.setText("正常");
                    swit3.setTextColor(Color.rgb(255, 0, 0));
                }
            }
        });

    }

    public void initData()
    {
        cursor = db.rawQuery("select priority from Mission where level=? and mission_name = ?", new String[]{String.valueOf(level),miss_name});
        while(cursor.moveToNext())
        {
            int nameColumn = cursor.getColumnIndex("priority");
            pri_type = cursor.getInt(nameColumn);
            //Toast.makeText(GrandActivity.this,"666",Toast.LENGTH_SHORT).show();
            swit3.setText(pri_list.get(pri_type));
            //Toast.makeText(DptActivity.this,description,Toast.LENGTH_SHORT).show();
        }
    }

    public void set_noti()
    {
        // 进行闹铃注册
        Intent intent = new Intent(notifyActivity.this, AlarmReceiver.class);
        intent.putExtra("miss_name",miss_name);
        PendingIntent sender = PendingIntent.getBroadcast(notifyActivity.this, 0, intent, 0);
        //long firstTime = SystemClock.elapsedRealtime();    // 开机之后到现在的运行时间(包括睡眠时间)
        long systemTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 这里时区需要设置一下，不然会有8个小时的时间差
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DATE, mDay);
        calendar.set(Calendar.MINUTE, mMinute);
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Log.e("setTimeActivity", "year ==== " + mYear + ", month ===== "
                + mMonth + ", day ==== " + mDay + ", hour === " + mHour + ", minute ==== " + mMinute);

        // 选择的定时时间  �
        long selectTime = calendar.getTimeInMillis();

        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if (systemTime > selectTime) {
            Toast.makeText(notifyActivity.this, "设置的时间小于当前时间", Toast.LENGTH_SHORT).show();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }

        // 计算现在时间到设定时间的时间差
        long time = selectTime - systemTime;
        //firstTime += time;


        // 进行闹铃注册
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP,
                selectTime, sender);

        Log.e("setTimeActivity", "time ==== " + time + ", selectTime ===== "
                + selectTime + ", systemTime ==== " + systemTime);

        Toast.makeText(notifyActivity.this, "设置闹铃成功! ", Toast.LENGTH_LONG).show();
        if(isOn == 0)
        {
            isOn = 1;
            swit2.setVisibility(View.VISIBLE);
            swit1.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("time");//得到新Activity 关闭后返回的数据
        mMinute = data.getExtras().getInt("minute");
        mHour = data.getExtras().getInt("hour");
        mDay = data.getExtras().getInt("day");
        mMonth = data.getExtras().getInt("month");
        mYear = data.getExtras().getInt("year");
        noti_time.setText(result);
        set_noti();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.e("finish", "onDestroy: " + description);
        db.execSQL("update Mission set priority = ? where mission_name = ? and level = ?", new Object[]{pri_type, miss_name, level});
    }
}

