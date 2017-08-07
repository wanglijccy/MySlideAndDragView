package com.demo.mylistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by passion on 2017/7/25.
 */

public class DescListAdapter extends BaseAdapter {

    private Toast mToast;
    private Context mContext;
    private ArrayList<DescriptionBean> mAppList;

    public DescListAdapter(Context context, ArrayList<DescriptionBean> arrayList) {
        mContext = context;
        mAppList = arrayList;
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mAppList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder cvh;
        if (convertView == null) {
            cvh = new CustomViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_custom_btn, null);
            cvh.txtName = (TextView) convertView.findViewById(R.id.txt_item_edit);
            cvh.btnClick = (Button) convertView.findViewById(R.id.btn_item_click);
            cvh.btnClick.setOnClickListener(mOnClickListener);
            convertView.setTag(cvh);
        } else {
            cvh = (CustomViewHolder) convertView.getTag();
        }
        DescriptionBean item = (DescriptionBean) this.getItem(position);
        cvh.txtName.setText(item.description);
        cvh.btnClick.setText(position + "");
        cvh.btnClick.setTag(position);
        return convertView;
    }

    class CustomViewHolder {
        public TextView txtName;
        public Button btnClick;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object o = v.getTag();
            if (o instanceof Integer) {
                mToast.setText("语句所排的位置：" + mAppList.get((Integer) o).position);
                mToast.show();
            }
        }
    };
}
