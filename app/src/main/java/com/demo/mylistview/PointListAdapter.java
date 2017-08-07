package com.demo.mylistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by passion on 2017/7/25.
 */

public class PointListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<PointBean> mThemes;

    public PointListAdapter(Context context, ArrayList<PointBean> arrayList) {
        mContext = context;
        mThemes = arrayList;
    }

    @Override
    public int getCount() {
        if (mThemes == null) return 0;
        return mThemes.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_funny_test_layout, null);
            holder.tv = (TextView) convertView.findViewById(R.id.item_point_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.position = position;
        holder.tv.setText(mThemes.get(position).pointName);
        return convertView;
    }

    public class ViewHolder {
        TextView tv;
        int position;
    }
}
