package com.newtech.vplus.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.newtech.vplus.Model.addressclass;
import com.newtech.vplus.R;

import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapterAdd extends ArrayAdapter<addressclass> {
    private ArrayList<addressclass> dataSet;
    ArrayList<addressclass> data_Temp = null;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;

    }

    public CustomAdapterAdd(@NonNull Context context, @NonNull ArrayList<addressclass> data1) {
        super(context, R.layout.customtext, data1);
        this.mContext = context;
        this.dataSet = data1;
        this.data_Temp = new ArrayList<addressclass>();
        this.data_Temp.addAll(dataSet);

    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        addressclass i = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.t1);


            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(i.getAddress());
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (data_Temp.size()==0)
        {
            this.data_Temp.addAll(dataSet);
        }
        dataSet.clear();
        if (charText.length() == 0) {
            dataSet.addAll(data_Temp);
        } else {
            for (addressclass wp : data_Temp) {
                if (wp.getAddress().toLowerCase(Locale.getDefault()).contains(charText)) {
                    dataSet.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
