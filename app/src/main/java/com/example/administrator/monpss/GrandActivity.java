package com.example.administrator.monpss;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/5 0005.
 */
public class GrandActivity extends Activity {

    mainGrandListView grandListView;
    ArrayList<String> mgrandlist;
    mainGrandAdapter grandAdapter;

    ImageView addGrandItem;
    EditText grandEdit;
    ImageView backItem;

    String par_miss_name;

    SQLiteDatabase db;
    DBHelper dbh;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grandmission_main);
        grandListView = (mainGrandListView)this.findViewById(R.id.listView_grand);

        this.dbh =new DBHelper(this);
        db =dbh.getWritableDatabase();

        Intent intent = getIntent();
        par_miss_name = intent.getStringExtra("miss_name");
        Toast.makeText(GrandActivity.this,par_miss_name,Toast.LENGTH_SHORT).show();

        initData();

        grandEdit =(EditText) findViewById(R.id.grandEdit);
        addGrandItem = (ImageView) findViewById(R.id.grandAdd);
        backItem = (ImageView) findViewById(R.id.back);

        backItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addGrandItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String missTitle = grandEdit.getText().toString();
                if (missTitle.length() > 0) {
                    mgrandlist.add(missTitle);

                    cursor = db.rawQuery("select id from Mission where level=? and mission_name = ?", new String[]{"1",par_miss_name});
                    //Toast.makeText(GrandActivity.this,,Toast.LENGTH_SHORT).show();
                    int par_miss_id = -1;
                    while(cursor.moveToNext())
                    {
                        int nameColumn = cursor.getColumnIndex("id");
                        par_miss_id = cursor.getInt(nameColumn);
                        Toast.makeText(GrandActivity.this,String.valueOf(par_miss_id),Toast.LENGTH_SHORT).show();
                    }
                    if(par_miss_id >=0)
                    {
                        db.execSQL("insert into Mission(mission_name,parentID,parent_name,level) values(?,?,?,?)",new Object[]{missTitle,par_miss_id,par_miss_name,2});
                    }

                    grandEdit.setText("");
                    grandAdapter.notifyDataSetChanged();
                }
            }
        });

        grandAdapter = new mainGrandAdapter(mgrandlist,this);
        grandListView.setAdapter(grandAdapter);
    }

    public void initData() {
        mgrandlist = new ArrayList<String>();
        cursor = db.rawQuery("select mission_name from Mission where level=? and parent_name = ?", new String[]{"2",par_miss_name});
        while(cursor.moveToNext())
        {
            int nameColumn = cursor.getColumnIndex("mission_name");
            String miss_name = cursor.getString(nameColumn);
            //Toast.makeText(GrandActivity.this,"666",Toast.LENGTH_SHORT).show();
            if(miss_name!=null) {
                mgrandlist.add(miss_name);
            }
        }
    }
}
