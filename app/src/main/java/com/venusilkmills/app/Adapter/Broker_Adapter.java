package com.venusilkmills.app.Adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.venusilkmills.app.Model.BrokerClass;
import com.venusilkmills.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Broker_Adapter extends RecyclerView.Adapter<Broker_Adapter.ViewHolder>{

    private List<BrokerClass> khatalist = new ArrayList<BrokerClass>();
    private List<BrokerClass> khatalist1 = new ArrayList<BrokerClass>();
    Context context;
    private OnItemClickListener listener;
    int pos;
    private String atype;

    public interface OnItemClickListener {
        void onItemClick(BrokerClass klm1);
    }

    public Broker_Adapter(List<BrokerClass> khatalist, Context context, OnItemClickListener listener) {
        this.khatalist = khatalist;
        this.context = context;
        this.listener = listener;
        this.khatalist1.addAll(khatalist);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pd_name, pd_code;

        public ViewHolder(View itemView) {
            super(itemView);
            pd_name = (TextView) itemView.findViewById(R.id.pd_name);

        }

        public void bind(final BrokerClass klm1, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(klm1);
                }
            });
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.party_finder_view,null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        this.pos = position;
        BrokerClass pa = khatalist.get(pos);
        holder.pd_name.setText(pa.getBr_name());

        holder.bind(pa, listener);
    }

    @Override
    public int getItemCount() {
        return khatalist.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (khatalist1.size()==0){
            this.khatalist1.addAll(khatalist);
        }
        khatalist.clear();
        if (charText.length() == 0) {
            khatalist.addAll(khatalist1);
        } else {
            for (BrokerClass wp : khatalist1) {
                if (wp.getBr_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    khatalist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
