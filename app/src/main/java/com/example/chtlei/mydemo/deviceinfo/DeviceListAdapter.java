package com.example.chtlei.mydemo.deviceinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chtlei.mydemo.R;

import java.util.List;

/**
 * Created by chtlei on 18-10-9.
 */

public class DeviceListAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<DeviceItem> mList;

    public DeviceListAdapter(Context context, List<DeviceItem> ListItem){
        this.mInflater = LayoutInflater.from(context);
        this.mList = ListItem;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            convertView = mInflater.inflate(R.layout.device_list_view_item,null);
            holder.title = convertView.findViewById(R.id.item_title);
            holder.info = convertView.findViewById(R.id.item_msg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(position + "、" + mList.get(position).getTitle() + " : ");
        holder.info.setText(mList.get(position).getInfo());
        return convertView;
    }

    public class ViewHolder{
        public TextView title;
        public TextView info;
    }
}
