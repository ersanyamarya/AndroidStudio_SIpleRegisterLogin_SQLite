package com.example.arya.registerloginapp;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> line1 = new ArrayList<String>();
    ArrayList<String> row2 = new ArrayList<String>();

    public ListViewAdapter(Activity context, ArrayList<String> line1, ArrayList<String> row2) {
        super();
        this.context = context;
        this.line1 = line1;
        this.row2 = row2;
    }

    public ListViewAdapter(UserList context, ArrayList<String>[] line1, ArrayList<String>[] row2) {

    }


    @Override
    public int getCount() {
        return row2.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.Line1 = (TextView) convertView.findViewById(R.id.tvOne);
            holder.Row2 = (TextView) convertView.findViewById(R.id.tvTwo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.Line1.setText(line1.get(position));
        holder.Row2.setText(row2.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView Line1, Row2;
    }
}



