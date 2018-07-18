package com.example.dell.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.test.Data;
import com.example.dell.test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * 自定义了一个adapter，继承了BaseAdapter
 * */
public class UserTalkAdapter extends BaseAdapter {
    private Context context = null;
    private List<Data> datas = new ArrayList<> ();

    public UserTalkAdapter(Context context, List<Data> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size ();
    }

    @Override
    public Object getItem(int position) {
        return datas.get (position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder ();
            convertView = LayoutInflater.from (context).inflate (R.layout.student_item, null);
            viewHolder.user_img = convertView.findViewById (R.id.user_img);
            viewHolder.user_name = convertView.findViewById (R.id.user_name);
            viewHolder.ping_num = convertView.findViewById (R.id.ping_num);
            convertView.setTag (viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag ();
        }
        viewHolder.user_img.setImageResource (datas.get (position).user_img);
        viewHolder.user_name.setText (datas.get (position).user_name);
        viewHolder.ping_num.setText (datas.get (position).ping_num);

        return convertView;
    }

    public final class ViewHolder {
        public ImageView user_img;
        public TextView user_name;
        public TextView ping_num;
    }
}
