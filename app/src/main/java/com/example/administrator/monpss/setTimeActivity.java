package com.example.administrator.monpss;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class setTimeActivity extends Activity {

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private TextView mTimeDisplay;
    ImageView btn_back;


    String select_time;

    private int mYear = -1;
    private int mMonth = -1;
    private int mDay = -1;
    private int mHour = -1;
    private int mMinute = -1;

    public static final long DAY = 1000L * 60 * 60 * 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time_layout);

        mTimeDisplay = (TextView) findViewById(R.id.dateDisplay);
        btn_back = (ImageView) findViewById(R.id.iv_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_time = mTimeDisplay.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("year", mYear);
                intent.putExtra("month", mMonth);
                intent.putExtra("day", mDay);
                intent.putExtra("hour", mHour);
                intent.putExtra("minute", mMinute);
                intent.putExtra("time", select_time);
                setTimeActivity.this.setResult(RESULT_OK, intent);
                finish();
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        if(mHour == -1 && mMinute == -1 && mYear == -1 && mMonth == -1 && mDay==-1) {
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        updateDisplay();

        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        mDatePicker.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener() {

            public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay();
            /*这里放更新日期的方法*/
            }
        });
        mTimePicker = (TimePicker)findViewById(R.id.timePicker);
        mTimePicker.setCurrentHour(mHour);
        mTimePicker.setCurrentMinute(mMinute);
        mTimePicker.setIs24HourView(true); //参数is24HourView，如果是true，则为24小时制，否则，则为12小时制。
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                mHour = hourOfDay;
                mMinute = minute;
                updateDisplay();
            }
        });
    }

    // 更新日期和时间显示区的信息
    private void updateDisplay() {

        mTimeDisplay.setText(new StringBuilder()
                // 由于月份是按照从0到11进行计算，因此显示的时候加上1，进行转换。
                .append(mMonth + 1).append("-").append(mDay).append("-")
                .append(mYear).append(" ").append(pad(mHour)).append(":")
                .append(pad(mMinute)));
    }

    // 当小时或者分钟为个位数字时，前面加一个0
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public void onBackPressed() {
        select_time = mTimeDisplay.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("year", mYear);
        intent.putExtra("month", mMonth);
        intent.putExtra("day", mDay);
        intent.putExtra("hour", mHour);
        intent.putExtra("minute", mMinute);
        intent.putExtra("time", select_time);
        setTimeActivity.this.setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
