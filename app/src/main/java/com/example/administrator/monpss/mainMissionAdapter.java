package com.example.administrator.monpss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class mainMissionAdapter extends BaseAdapter {
    private ArrayList<String> mainChildList;
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



    public mainMissionAdapter(ArrayList<String> mainChildList, Context context) {
        super();
        this.mainChildList = mainChildList;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return null == mainChildList ? 0 : mainChildList.size();
    }

    @Override
    public Object getItem(int position) {
        return mainChildList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.activity_main_list_item_1,null);
            holder = new ViewHolder();
            holder.childText = (TextView) convertView.findViewById(R.id.textView_2);
            holder.childText.setText(mainChildList.get(position));
            holder.childLayout = (LinearLayout) convertView.findViewById(R.id.childlayout);
            holder.childItem = (RelativeLayout) convertView.findViewById(R.id.childItem);
            holder.toGrand = (ImageView) convertView.findViewById(R.id.grandlist);
            holder.toDescription = (ImageView) convertView.findViewById(R.id.description);
            holder.delButton = (ImageView) convertView.findViewById(R.id.delMission);
            holder.toNotify = (ImageView) convertView.findViewById(R.id.notify);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null != mainChildList) {
            holder.childLayout.setOnClickListener(new OnLvItemClickListener(position));
            //如果点击的是当前项，则将其展开，否则将其隐藏
            if(expandPosition == position){
                holder.childItem.setVisibility(View.VISIBLE);
            }else{
                holder.childItem.setVisibility(View.GONE);
            }

            holder.toGrand.setOnClickListener(new OnLvItemClickListener(position) {
                @Override
                public void onClick(View v) {
                    Activity currentActivity = (Activity) v.getContext();
                    Intent intent = new Intent();
                    intent.putExtra("miss_name",mainChildList.get(position));
                    intent.setClass(currentActivity,GrandActivity.class);
                    currentActivity.startActivity(intent);
                }
            });

            holder.toDescription.setOnClickListener(new OnLvItemClickListener(position) {
                @Override
                public void onClick(View v) {
                    Activity currentActivity = (Activity) v.getContext();
                    Intent intent = new Intent();
                    intent.putExtra("miss_name",mainChildList.get(position));
                    intent.putExtra("level",1);
                    intent.setClass(currentActivity,DptActivity.class);
                    currentActivity.startActivity(intent);
                }
            });

            holder.toNotify.setOnClickListener(new OnLvItemClickListener(position) {
                @Override
                public void onClick(View v) {
                    Activity currentActivity = (Activity) v.getContext();
                    Intent intent = new Intent();
                    //intent.putExtra("miss_name",mainChildList.get(position));
                    //intent.putExtra("level",1);
                    intent.setClass(currentActivity,notifyActivity.class);
                    currentActivity.startActivity(intent);
                }
            });
        }

        String entity = mainChildList.get(position);

        return convertView;
    }

    class ViewHolder {
        TextView childText;
        LinearLayout childLayout;
        RelativeLayout childItem;
        ImageView toGrand;
        ImageView toDescription;
        ImageView delButton;
        ImageView toNotify;
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
