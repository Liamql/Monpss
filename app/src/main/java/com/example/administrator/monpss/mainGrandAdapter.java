package com.example.administrator.monpss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class mainGrandAdapter extends BaseAdapter {
    private ArrayList<String> mainGrandList;
    // 布局加载器
    private LayoutInflater mInflater;
    // 上下文
    private Context context;
    // 布局缓存对象
    private ViewHolder holder;
    //记录当前展开项的索引
    private int expandPosition = -1;

    private float x,ux;

    private ImageView curDelButton;

    SQLiteDatabase db;
    DBHelper dbh;
    Cursor cursor;



    public mainGrandAdapter(ArrayList<String> mainGrandList, Context context) {
        super();
        this.mainGrandList = mainGrandList;
        this.context = context;
        mInflater = LayoutInflater.from(context);

        this.dbh =new DBHelper(context);
        db =dbh.getWritableDatabase();

    }

    @Override
    public int getCount() {
        return null == mainGrandList ? 0 : mainGrandList.size();
    }

    @Override
    public Object getItem(int position) {
        return mainGrandList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.main_grandlist_item,null);
            holder = new ViewHolder();
            holder.grandText = (TextView) convertView.findViewById(R.id.grandMission);
            holder.grandText.setText(mainGrandList.get(position));
            holder.grandLayout = (LinearLayout) convertView.findViewById(R.id.grandlayout);
            holder.grandItem = (RelativeLayout) convertView.findViewById(R.id.grandItem);
            holder.toNotify = (ImageView) convertView.findViewById(R.id.noti);
            holder.toDescription = (ImageView) convertView.findViewById(R.id.desc);
            holder.delButton = (ImageView) convertView.findViewById(R.id.del_miss);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null != mainGrandList) {
            holder.grandLayout.setOnClickListener(new OnLvItemClickListener(position));
            //如果点击的是当前项，则将其展开，否则将其隐藏
            if(expandPosition == position){
                holder.grandItem.setVisibility(View.VISIBLE);
            }else{
                holder.grandItem.setVisibility(View.GONE);
            }

            holder.toDescription.setOnClickListener(new OnLvItemClickListener(position) {
                @Override
                public void onClick(View v) {
                    Activity currentActivity = (Activity) v.getContext();
                    Intent intent = new Intent();
                    intent.putExtra("miss_name",mainGrandList.get(position));
                    intent.putExtra("level",2);
                    intent.setClass(currentActivity,DptActivity.class);
                    currentActivity.startActivity(intent);
                }
            });

            holder.delButton.setOnClickListener(new OnLvItemClickListener(position) {
                @Override
                public void onClick(View v) {
                    db.execSQL("delete from Mission where mission_name = ? and level = ?", new String[]{mainGrandList.get(position), "2"});
                    Activity currentActivity = (Activity) v.getContext();
                    currentActivity.startActivity(new Intent(currentActivity, GrandActivity.class));
                    currentActivity.finish();
                }
            });

            holder.toNotify.setOnClickListener(new OnLvItemClickListener(position) {
                @Override
                public void onClick(View v) {
                    Activity currentActivity = (Activity) v.getContext();
                    Intent intent = new Intent();
                    intent.putExtra("miss_name",mainGrandList.get(position));
                    intent.putExtra("level",2);
                    intent.setClass(currentActivity,notifyActivity.class);
                    currentActivity.startActivity(intent);
                }
            });
        }

        String entity = mainGrandList.get(position);

        return convertView;
    }

    class ViewHolder {
        TextView grandText;
        LinearLayout grandLayout;
        RelativeLayout grandItem;
        ImageView toNotify;
        ImageView toDescription;
        ImageView delButton;
    }

    class OnLvItemClickListener implements View.OnClickListener {
        private int position;

        public OnLvItemClickListener(int position) {
            super();
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            //如果当前项为展开，则将其置为-1，目的是为了让其隐藏，如果当前项为隐藏，则将当前位置设置给全局变量，让其展开，这也就是借助于中间变量实现布局的展开与隐藏
            if(expandPosition == position){
                expandPosition = -1;
            }else{
                expandPosition = position;
            }
            notifyDataSetChanged();
        }

    }
}
