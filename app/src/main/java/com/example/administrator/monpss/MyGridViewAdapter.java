package com.example.administrator.monpss;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/5 0005.
 */
public class MyGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;

    ArrayList<String> img_text;

    SQLiteDatabase db;
    DBHelper dbh;
    Cursor cursor;

    public MyGridViewAdapter(Context mContext,ArrayList<String> img_text) {
        super();
        this.mContext = mContext;
        this.img_text = img_text;
        inflater = LayoutInflater.from(mContext);
        this.dbh =new DBHelper(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_text.size() +1;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item, parent, false);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_item);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_item);
            viewHolder.tv_title = (RelativeLayout) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
            //convertView.setLayoutParams(new AbsListView.LayoutParams((int) (parent.getWidth() / 3) - 1, (int) (parent.getHeight() / 2)));// 动态设置item的高度
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(position == img_text.size())
        {
            viewHolder.iv.setVisibility(View.VISIBLE);
            viewHolder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog_Layout(mContext);
                    notifyDataSetChanged();
                }
            });
            viewHolder.tv.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.iv.setVisibility(View.GONE);
            viewHolder.tv.setVisibility(View.VISIBLE);
            viewHolder.tv.setText(img_text.get(position));
        }

        viewHolder.tv_title.setOnClickListener(new OnLvItemClickListener(position) {
            @Override
            public void onClick(View v) {
                String miss_name = img_text.get(position);
                if(position == 0)
                {
                    Intent intent = new Intent();
                    intent.setClass(v.getContext(), MainActivity.class);
                    v.getContext().startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent();
                    intent.setClass(v.getContext(),MissionActivity.class);
                    intent.putExtra("miss_name",miss_name);
                    v.getContext().startActivity(intent);
                }
            }
        });
        //convertView.setLayoutParams(new AbsListView.LayoutParams((int) (parent.getWidth() / 3) - 1, (int) (parent.getHeight() / 2)));// 动态设置item的高度
        return convertView;
    }

    class ViewHolder
    {
        TextView tv;
        ImageView iv;
        RelativeLayout tv_title;
    }

    private void showDialog_Layout(Context context) {
        final View textEntryView = inflater.inflate(
                R.layout.new_list_dialog, null);
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
                    img_text.add(misstitle);
                    db =dbh.getWritableDatabase();

                    db.execSQL("insert into Mission(mission_name,parentID,parent_name,level) values(?,?,?,?)",new Object[]{misstitle,0,misstitle,0});
                    db.close();
                }
                addDialog.dismiss();
            }
        });
    }

    class OnLvItemClickListener implements View.OnClickListener {
        private int position;

        public OnLvItemClickListener(int position) {
            super();
            this.position = position;
        }

        @Override
        public void onClick(View v) {

        }

    }

}
