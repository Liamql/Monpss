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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ExpandableListView mainlistview = null;
    ArrayList<Entity> parent = null;
    ExpandAdapter mainAdapter;
    ImageView addMainItem;
    ImageView toPopupwindow;
    ImageView toGrid;

    String result;

    SQLiteDatabase db;
    DBHelper dbh;
    Cursor cursor;

    private String user_name;
    private String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainlistview = (ExpandableListView) this
                .findViewById(R.id.main_listview);

        dbh=new DBHelper(this);
        db=dbh.getWritableDatabase();

        user_name = PreferencesUtil.getSharedStringData(this, "user_name");
        pass = PreferencesUtil.getSharedStringData(this,"password");
        Log.e("NAME", user_name);


        initData();
        mainAdapter = new ExpandAdapter(this,parent);
        mainlistview.setAdapter(mainAdapter);
        mainlistview.setGroupIndicator(null);

        toPopupwindow = (ImageView) findViewById(R.id.iv_pri);
        toPopupwindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPopWindow addPopWindow = new AddPopWindow(MainActivity.this);
                addPopWindow.showPopupWindow(toPopupwindow);
            }
        });

        addMainItem = (ImageView) findViewById(R.id.iv_setting);
        addMainItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        toGrid = (ImageView) findViewById(R.id.iv_home);
        toGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GridActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getJson();

        getJson2();
    }

    // 初始化数据
    public void initData() {
        parent = new ArrayList<Entity>();
        /*
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("吃饭");
        list1.add("睡觉");
        list1.add("打豆豆");
        parent.add(new Entity("个人事务",list1));
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("膜");
        list2.add("造轮子");
        parent.add(new Entity("平时", list2));
        */



        cursor = db.rawQuery("select mission_name from Mission where level=?",new String[]{"0"});
        while(cursor.moveToNext())
        {
            int nameColumn = cursor.getColumnIndex("mission_name");
            String miss_name = cursor.getString(nameColumn);
            parent.add(new Entity(miss_name));
        }
        cursor.close();
    }

    public void getJson()
    {
        new Thread(){
                @Override
                public void run() {
                    try{
                        String url="http://114.215.121.244:8080/auth";

                        Gson gson = new Gson();
                        UserM_Post user = new UserM_Post("user_mission",pass,user_name,"0","PERSONAL","null","null","0");
                        String str = gson.toJson(user);
                        HttpUtils.SendRequest(url,str);
                    }
                    catch (Exception e)
                    {}
                }
            }.start();
    }

    public void getJson2()
    {
        new Thread(){
            @Override
            public void run() {
                try{
                    String url="http://114.215.121.244:8080/auth?command_type=mission&mission_id=1";

                    String str = HttpUtils.getJsonContent(url);

                    Gson gson = new Gson();

                    List<UserM_Get> user_get = gson.fromJson(str,new TypeToken< ArrayList<UserM_Get> >(){}.getType());
                    for(int i =0;i<user_get.size();i++)
                    {
                        UserM_Get u = user_get.get(i);
                        Log.e("PRT",u.getId());
                        Log.e("PRT",u.getMission_name());
                    }
                }
                catch (Exception e)
                {}
            }
        }.start();
    }

}
