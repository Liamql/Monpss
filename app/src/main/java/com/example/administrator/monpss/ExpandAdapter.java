package com.example.administrator.monpss;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
class ExpandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Entity> groupList;
    private LayoutInflater inflater;

    SQLiteDatabase db;
    DBHelper dbh;
    Cursor cursor;

    public ExpandAdapter(Context context, ArrayList<Entity> groupList)
    {
        this.groupList = groupList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dbh =new DBHelper(context);
        db =dbh.getWritableDatabase();

        initData();
    }

    public void initData()
    {
        for(int i =0;i<groupList.size();i++)
        {
            cursor = db.rawQuery("select mission_name from Mission where level=? and parent_name = ?", new String[]{"1", groupList.get(i).mainMission});
            while(cursor.moveToNext())
            {
                int nameColumn = cursor.getColumnIndex("mission_name");
                String miss_name = cursor.getString(nameColumn);
                groupList.get(i).missonList.add(miss_name);
            }
        }
    }

    public void updateListView() {
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.groupList.size();
    }

    public Object getItem(int position) {
        return groupList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getGroupCount() {
        return groupList == null ? 0 : groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
        /*return groupList == null ? 0
                : (groupList.get(groupPosition) == null ? 0 : (groupList
                .get(groupPosition).missonList == null ? 0
                : groupList.get(groupPosition).missonList.size()));*/
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).missonList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    final static class ViewHolder {
        TextView mainMissionTitle;
        ImageView addMission;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.activity_main_list_item, null);
            viewHolder.mainMissionTitle = (TextView) convertView
                    .findViewById(R.id.textView_1);
            viewHolder.addMission = (ImageView) convertView
                    .findViewById(R.id.button_1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mainMissionTitle
                .setText(this.groupList.get(groupPosition).mainMission);
        viewHolder.addMission.setOnClickListener(new addItemClickListener(groupPosition));
        return convertView;
    }

    class addItemClickListener implements View.OnClickListener {
        private int position;

        public addItemClickListener(int position) {
            super();
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            //groupList.get(position).missonList.add("123");
            showDialog_Layout(context, position);
            notifyDataSetChanged();
        }
    }

    private void showDialog_Layout(Context context, final int position) {
        final View textEntryView = inflater.inflate(
                R.layout.dialoglayout, null);
        final EditText edtInput=(EditText)textEntryView.findViewById(R.id.edtInput);
        final ImageView addDone = (ImageView)textEntryView.findViewById(R.id.addDone);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(textEntryView);
        final Dialog addDialog = builder.show();
        addDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String misstitle = edtInput.getText().toString();
                if(misstitle.length()>0) {
                    cursor = db.rawQuery("select id from Mission where level=? and mission_name = ?", new String[]{"0", groupList.get(position).mainMission});
                    //Toast.makeText(v.getContext(),groupList.get(position).mainMission,Toast.LENGTH_SHORT).show();
                    int par_miss_id = -1;
                    while(cursor.moveToNext())
                    {
                        int nameColumn = cursor.getColumnIndex("id");
                        par_miss_id = cursor.getInt(nameColumn);
                        //Toast.makeText(v.getContext(),String.valueOf(par_miss_id),Toast.LENGTH_SHORT).show();
                    }
                    if(par_miss_id >=0)
                    {
                        db.execSQL("insert into Mission(mission_name,parentID,parent_name,level,priority) values(?,?,?,?,?)",new Object[]{misstitle,par_miss_id,groupList.get(position).mainMission,1,0});
                    }
                    groupList.get(position).missonList.add(misstitle);
                }
                addDialog.dismiss();
            }
        });
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        /*ArrayAdapter<String> missionAdapter = new ArrayAdapter<String>(context,
                R.layout.activity_main_list_item_1,R.id.textView_2,groupList.get(groupPosition).missonList);
        */
        final mainChildAdapter missionAdapter = new mainChildAdapter(groupList.get(groupPosition).missonList,context);
        mainChildListView childListView = new mainChildListView(context);

        childListView.setAdapter(missionAdapter);
        return childListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}