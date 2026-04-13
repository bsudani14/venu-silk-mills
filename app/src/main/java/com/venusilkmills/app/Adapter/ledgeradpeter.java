package com.venusilkmills.app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.icu.text.NumberFormat;
import androidx.cardview.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.venusilkmills.app.Model.ledgersubclass;
import com.venusilkmills.app.R;

import java.text.Format;
import java.util.List;
import java.util.Locale;


/**
 * Created by DELL on 15/03/2018.
 */

public class ledgeradpeter extends ArrayAdapter<ledgersubclass> {

    private static class ViewHolder {
        TextView t1, t2, t3, t4, t5, t6,t7;
    }

    public ledgeradpeter(Context context, List<ledgersubclass> mExampleList) {
        super(context, 0, mExampleList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ledgersubclass ledgersubclass_m = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.frledgerlistdetails, parent, false);
            viewHolder.t1 = (TextView) convertView.findViewById(R.id.t1);
            viewHolder.t2 = (TextView) convertView.findViewById(R.id.t2);
            viewHolder.t3 = (TextView) convertView.findViewById(R.id.t3);
            viewHolder.t4 = (TextView) convertView.findViewById(R.id.t4);
            viewHolder.t5 = (TextView) convertView.findViewById(R.id.t5);
            viewHolder.t6 = (TextView) convertView.findViewById(R.id.t6);
            viewHolder.t7 = (TextView) convertView.findViewById(R.id.t7);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.t1.setText(ledgersubclass_m.getRdate());
        viewHolder.t2.setText(ledgersubclass_m.getRled_account());
        viewHolder.t3.setText(ledgersubclass_m.getRLed_ChqNo());
        viewHolder.t4.setText(ledgersubclass_m.getRled_vouno());

        Format format = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        }

        if(ledgersubclass_m.getRCredit().equals("0")){
            viewHolder.t6.setText("0");
        }if(ledgersubclass_m.getRDebit().equals("0")){
            viewHolder.t5.setText("0");
        }if(ledgersubclass_m.getRled_amount().equals("0")){
            viewHolder.t7.setText("0");
        }else{
            viewHolder.t5.setText(String.valueOf(format.format(new Double(ledgersubclass_m.getRDebit()))));
            viewHolder.t6.setText(String.valueOf(format.format(new Double(ledgersubclass_m.getRCredit()))));
            viewHolder.t7.setText(String.valueOf(format.format(new Double(ledgersubclass_m.getRled_amount()))));
        }

        return convertView;
    }

}
