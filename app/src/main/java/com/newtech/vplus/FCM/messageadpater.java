package com.newtech.vplus.FCM;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.newtech.vplus.Model.msgclass;
import com.newtech.vplus.R;

import java.util.List;


public class messageadpater extends ArrayAdapter<msgclass> {
    Context context;
    int layoutResourceId;
    List<msgclass> data = null;


    public messageadpater(Context context, int layoutResourceId, List<msgclass> mExampleList) {
        super(context,layoutResourceId,mExampleList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = mExampleList;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RegHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent,  false);
            holder = new RegHolder();
            holder.txtmsg = (TextView)row.findViewById(R.id.msgtxt);

            row.setTag(holder);
        }
        else
        {
            holder = (RegHolder)row.getTag();
        }

        if (position< data.size()){
            msgclass weather = data.get(position);
            if (weather!=null)
            {
                holder.txtmsg.setText(weather.getmessage());

            }
        }


        return row;
    }


    static class RegHolder
    {
        TextView txtmsg;
    }
}
