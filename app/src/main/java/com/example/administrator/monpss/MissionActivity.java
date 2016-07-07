package com.example.administrator.monpss;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/5 0005.
 */
public class MissionActivity extends Activity {

    private ArrayList<String> groupList_high = new ArrayList<>();
    private ArrayList<String> groupList_normal = new ArrayList<>();
    private ArrayList<String> groupList_low = new ArrayList<>();

    ArrayList<Entity> parent = null;

    LinearLayout high;
    LinearLayout normal;
    LinearLayout low;

    mainChildListView missionview_high = null;
    mainChildListView missionview_normal = null;
    mainChildListView missionview_low = null;

    ImageView toPopupwindow;

    TextView title;

    SQLiteDatabase db;
    DBHelper dbh;
    Cursor cursor;

    ExpandAdapter missAdapter;
    ExpandableListView misslistview = null;

    ImageView back_btn;
    ImageView toGrid;

    private String miss_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_layout);
        dbh=new DBHelper(this);
        db=dbh.getWritableDatabase();

        misslistview = (ExpandableListView) this
                .findViewById(R.id.miss_listview);

        back_btn = (ImageView)findViewById(R.id.iv_home);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MissionActivity.this,GridActivity.class));
            }
        });

        Intent intent = getIntent();
        miss_name = intent.getExtras().getString("miss_name");
        Toast.makeText(this,miss_name,Toast.LENGTH_SHORT).show();

        toPopupwindow = (ImageView) findViewById(R.id.iv_pri);
        toPopupwindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPopWindow addPopWindow = new AddPopWindow(MissionActivity.this,miss_name);
                addPopWindow.showPopupWindow(toPopupwindow);
            }
        });

        toGrid = (ImageView) findViewById(R.id.iv_home);
        toGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MissionActivity.this, GridActivity.class);
                startActivity(intent);
                finish();
            }
        });

        initData();
        initView();
        missAdapter = new ExpandAdapter(this,parent);
        misslistview.setAdapter(missAdapter);
        misslistview.setGroupIndicator(null);
    }

    private void initView() {
        title = (TextView) findViewById(R.id.mission_name);
        title.setText(miss_name);

        /*
        missionview_high=(mainChildListView) findViewById(R.id.listView_child1);
        missionview_normal=(mainChildListView) findViewById(R.id.listView_child2);
        missionview_low=(mainChildListView) findViewById(R.id.listView_child3);
        high = (LinearLayout) findViewById(R.id.high);
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(missionview_high.getVisibility() == View.GONE)
                {
                    Log.e("MISSION","HIGH C ");
                    missionview_high.setVisibility(View.VISIBLE);
                }
                else
                {
                    missionview_high.setVisibility(View.GONE);
                }
            }
        });

        normal = (LinearLayout) findViewById(R.id.normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(missionview_normal.getVisibility() == View.GONE)
                {
                    Log.e("MISSION","NORMAL C ");
                    missionview_normal.setVisibility(View.VISIBLE);
                }
                else
                {
                    missionview_normal.setVisibility(View.GONE);
                }
            }
        });
        low = (LinearLayout) findViewById(R.id.low);
        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(missionview_low.getVisibility() == View.GONE)
                {
                    Log.e("MISSION","LOW C ");
                    missionview_low.setVisibility(View.VISIBLE);
                }
                else
                {
                    missionview_low.setVisibility(View.GONE);
                }
            }
        });

        final mainChildAdapter missionAdapter_high = new mainChildAdapter(groupList_high,this);
        missionview_high.setAdapter(missionAdapter_high);
        final mainChildAdapter missionAdapter_normal = new mainChildAdapter(groupList_normal,this);
        missionview_high.setAdapter(missionAdapter_normal);
        final mainChildAdapter missionAdapter_low = new mainChildAdapter(groupList_low,this);
        missionview_high.setAdapter(missionAdapter_low);
        */
    }

    private void initData()
    {
        parent = new ArrayList<Entity>();
        cursor = db.rawQuery("select mission_name from Mission where priority=? and parent_name = ?",new String[]{"1",miss_name});
        while(cursor.moveToNext())
        {
            int nameColumn = cursor.getColumnIndex("mission_name");
            String missi_name = cursor.getString(nameColumn);
            Log.e("MISSION","HIGH: "+missi_name);
            groupList_high.add(missi_name);
        }
        parent.add(new Entity("高",groupList_high));
        cursor = db.rawQuery("select mission_name from Mission where priority=? and parent_name = ?",new String[]{"0",miss_name});
        while(cursor.moveToNext())
        {
            int nameColumn = cursor.getColumnIndex("mission_name");
            String missi_name = cursor.getString(nameColumn);
            Log.e("MISSION","NORMAL: "+missi_name);
            groupList_normal.add(missi_name);
        }
        parent.add(new Entity("正常",groupList_normal));
        cursor = db.rawQuery("select mission_name from Mission where priority=? and parent_name = ?",new String[]{"2",miss_name});
        while(cursor.moveToNext())
        {
            int nameColumn = cursor.getColumnIndex("mission_name");
            String missi_name = cursor.getString(nameColumn);
            Log.e("MISSION","LOW: "+missi_name);
            groupList_low.add(missi_name);
        }
        parent.add(new Entity("低",groupList_low));

        cursor.close();
    }

    public void del_mission()
    {

    }
}
