package com.example.administrator.monpss;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class SettingActivity extends Activity {

    ImageView btn_back;

    RelativeLayout set_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        btn_back = (ImageView) findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        set_info = (RelativeLayout) findViewById(R.id.set_info);
        set_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this,InfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
