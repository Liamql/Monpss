package com.example.administrator.monpss;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class DptActivity extends Activity {

    SQLiteDatabase db;
    DBHelper dbh;
    Cursor cursor;

    EditText editText;
    ImageView dpt_back;

    String description;

    String miss_name;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dpt_layout);
        dbh=new DBHelper(this);
        db=dbh.getWritableDatabase();

        editText = (EditText)findViewById(R.id.dpt_edt);
        dpt_back = (ImageView)findViewById(R.id.dpt_back);

        dpt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        miss_name = intent.getStringExtra("miss_name");
        level = intent.getExtras().getInt("level");
        Log.e("DPT",String.valueOf(level));
        Log.e("DPT",miss_name);
        //Toast.makeText(DptActivity.this,miss_name,Toast.LENGTH_SHORT).show();
        //Toast.makeText(DptActivity.this,String.valueOf(level),Toast.LENGTH_SHORT).show();

        initData();
    }

    public void initData()
    {
        cursor = db.rawQuery("select description from Mission where level=? and mission_name = ?", new String[]{String.valueOf(level),miss_name});
        while(cursor.moveToNext())
        {
            int nameColumn = cursor.getColumnIndex("description");
            Log.e("DPT",String.valueOf(nameColumn));
            description = cursor.getString(nameColumn);
            if(description!=null)
            {
                editText.setText(description);
                editText.setSelection(description.length());
                Log.e("DPT",description);
            }
            //Toast.makeText(DptActivity.this,"666",Toast.LENGTH_SHORT).show();
            /*
            editText.setText(description);
            editText.setSelection(description.length());
            */
            //Toast.makeText(DptActivity.this,description,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String new_description = editText.getText().toString();
        //Log.e("finish", "onDestroy: " + description);
        db.execSQL("update Mission set description = ? where mission_name = ? and level = ?", new Object[]{new_description, miss_name, level});
    }
}
