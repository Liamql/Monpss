package com.example.administrator.monpss;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/5 0005.
 */
public class GridActivity extends Activity {
    private MyGridView gridview;
    ArrayList<String> gridList;

    SQLiteDatabase db;
    DBHelper dbh;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        dbh=new DBHelper(this);
        db=dbh.getWritableDatabase();

        initData();
        initView();
    }

    private void initView() {
        gridview=(MyGridView) findViewById(R.id.gridview);
        gridview.setAdapter(new MyGridViewAdapter(this, gridList));
    }

    private void initData()
    {
        gridList = new ArrayList<String>();
        gridList.add("所有任务");
        /*
        gridList.add("个人事务");
        gridList.add("平时");
        */

        cursor = db.rawQuery("select mission_name from Mission where level=?",new String[]{"0"});
        while(cursor.moveToNext())
        {
            int nameColumn = cursor.getColumnIndex("mission_name");
            String miss_name = cursor.getString(nameColumn);
            gridList.add(miss_name);
        }
        cursor.close();
    }
}
