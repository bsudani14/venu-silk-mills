package com.venusilkmills.app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.icu.text.NumberFormat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.venusilkmills.app.Model.billsubclass;
import com.venusilkmills.app.R;

import java.text.Format;
import java.util.List;
import java.util.Locale;


/**
 * Created by DELL on 15/03/2018.
 */

public class billadpeter extends ArrayAdapter<billsubclass> {

    public OnClickC onclick;


    private static class ViewHolder {
        TextView t1, t2, t3, t4, t5, t6, t7;
        ImageButton t8;
    }

    public interface OnClickC{
        void onClickItem(String billno, String dowlode);
    }

    public billadpeter(Context context, List<billsubclass> mExampleList,OnClickC onclick) {
        super(context, 0, mExampleList);
        this.onclick = onclick;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final billsubclass billsubclass = getItem(position);

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.frbilllistdetails, parent, false);
            viewHolder.t1 = (TextView) convertView.findViewById(R.id.t1);
            viewHolder.t2 = (TextView) convertView.findViewById(R.id.t2);
            viewHolder.t3 = (TextView) convertView.findViewById(R.id.t3);
            viewHolder.t4 = (TextView) convertView.findViewById(R.id.t4);
            viewHolder.t5 = (TextView) convertView.findViewById(R.id.t5);
            viewHolder.t6 = (TextView) convertView.findViewById(R.id.t6);
            viewHolder.t7 = (TextView) convertView.findViewById(R.id.t7);
            viewHolder.t8 = (ImageButton) convertView.findViewById(R.id.t8);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.t1.setText(billsubclass.getDate());
        viewHolder.t2.setText(billsubclass.getSal_code());
        viewHolder.t4.setText(billsubclass.getDatediff());

        Format format = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        }

        viewHolder.t3.setText(String.valueOf(format.format(new Double(billsubclass.getSal_netamt().replace(",", "")))));
        viewHolder.t5.setText(String.valueOf(format.format(new Double(billsubclass.getSal_paidamt().replace(",", "")))));
        viewHolder.t6.setText(String.valueOf(format.format(new Double(billsubclass.getPending().replace(",", "")))));
        viewHolder.t7.setText(String.valueOf(format.format(new Double(billsubclass.getBalance().replace(",", "")))));

        viewHolder.t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onClickItem(billsubclass.getSal_code(),"DOWLODE");
            }
        });



        return convertView;
    }


}

