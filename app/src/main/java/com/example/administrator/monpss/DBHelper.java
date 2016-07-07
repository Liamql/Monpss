package com.example.administrator.monpss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/6 0006.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="MISSION.db";
    private static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table if not exists Mission ("
            + "id integer primary key autoincrement, "
            + "mission_name text not null, "
            + "parentID integer not null,"
            + "parent_name text not null,"
            + "level integer not null,"
            + "noti_time text,"
            + "noti_set text,"
            + "noti_type int,"
            + "priority int,"
            + "description text)";

    public static final String INIT_DATA = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('个人事务',1,'个人事务',0)";

    public static final String INIT_DATA2 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('平时',2,'平时',0)";

    public static final String INIT_DATA3 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('托福',3,'考托福',0)";

    public static final String INIT_DATA4 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('健身',4,'健身',0)";

    public static final String INIT_DATA5 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('胸肌',4,'健身',1)";

    public static final String INIT_DATA6 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('平板卧推',5,'胸肌',2)";

    public static final String INIT_DATA7 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('龙门夹索',5,'胸肌',2)";

    public static final String INIT_DATA8 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('哑铃飞鸟',5,'胸肌',2)";

    public static final String INIT_DATA9 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('上斜卧推',5,'胸肌',2)";

    public static final String INIT_DATA10 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('下斜卧推',5,'胸肌',2)";

    public static final String INIT_DATA11 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('背阔肌',4,'健身',1)";

    public static final String INIT_DATA12 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('三角肌',4,'健身',1)";

    public static final String INIT_DATA13 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('股四头肌',4,'健身',1)";

    public static final String INIT_DATA14 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('二头肌',4,'健身',1)";

    public static final String INIT_DATA15 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('三头肌',4,'健身',1)";

    public static final String INIT_DATA16 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('听力',3,'托福',1)";

    public static final String INIT_DATA17 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('阅读',3,'托福',1)";

    public static final String INIT_DATA18 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('写作',3,'托福',1)";

    public static final String INIT_DATA19 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('口语',3,'托福',1)";

    public static final String INIT_DATA20 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('背单词',17,'阅读',2)";

    public static final String INIT_DATA21 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('刷OG',17,'阅读',2)";

    public static final String INIT_DATA22 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('刷TPO',17,'阅读',2)";

    public static final String INIT_DATA23 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('软工答辩',1,'个人事务',1)";

    public static final String INIT_DATA24 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('计网期末',1,'个人事务',1)";

    public static final String INIT_DATA25 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('计系期末',1,'个人事务',1)";

    public static final String INIT_DATA26 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('办网银',1,'个人事务',1)";

    public static final String INIT_DATA27 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('早起',2,'平时',1)";

    public static final String INIT_DATA28 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('写博客',2,'平时',1)";

    public static final String INIT_DATA29 = "insert into Mission(mission_name,parentID,parent_name,level) " +
            "values('练吉他',2,'平时',1)";


        public DBHelper(Context ctx){
            super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
        arg0.execSQL(CREATE_TABLE);
        arg0.execSQL(INIT_DATA);
        arg0.execSQL(INIT_DATA2);
        arg0.execSQL(INIT_DATA3);
        arg0.execSQL(INIT_DATA4);
        arg0.execSQL(INIT_DATA5);
        arg0.execSQL(INIT_DATA6);
        arg0.execSQL(INIT_DATA7);
        arg0.execSQL(INIT_DATA8);
        arg0.execSQL(INIT_DATA9);
        arg0.execSQL(INIT_DATA10);
        arg0.execSQL(INIT_DATA11);
        arg0.execSQL(INIT_DATA12);
        arg0.execSQL(INIT_DATA13);
        arg0.execSQL(INIT_DATA14);
        arg0.execSQL(INIT_DATA15);
        arg0.execSQL(INIT_DATA16);
        arg0.execSQL(INIT_DATA17);
        arg0.execSQL(INIT_DATA18);
        arg0.execSQL(INIT_DATA19);
        arg0.execSQL(INIT_DATA20);
        arg0.execSQL(INIT_DATA21);
        arg0.execSQL(INIT_DATA22);
        arg0.execSQL(INIT_DATA23);
        arg0.execSQL(INIT_DATA24);
        arg0.execSQL(INIT_DATA25);
        arg0.execSQL(INIT_DATA26);
        arg0.execSQL(INIT_DATA27);
        arg0.execSQL(INIT_DATA28);
        arg0.execSQL(INIT_DATA29);

        Log.i("database", "create event");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }
}
