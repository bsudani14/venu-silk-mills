package com.venusilkmills.app.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.venusilkmills.app.Model.CompanylistClass;
import com.venusilkmills.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Company_Adapter extends RecyclerView.Adapter<Company_Adapter.ViewHolder> {
    private List<CompanylistClass> department_data_list = new ArrayList<CompanylistClass>();
    private List<CompanylistClass> department_data_list1 = new ArrayList<CompanylistClass>();
    Context context;
    private OnItemClickListener listener;
    int pos;

    public interface OnItemClickListener {
        void onItemClick(CompanylistClass dpm1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pd_name, pd_code;

        public ViewHolder(View itemView) {
            super(itemView);
            pd_name = (TextView) itemView.findViewById(R.id.pd_name);
        }

        public void bind(final CompanylistClass dpm1, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(dpm1);
                }
            });
        }
    }

    public Company_Adapter(List<CompanylistClass> department_data_lists, Context context, OnItemClickListener listener) {
        this.department_data_list = department_data_lists;
        this.context = context;
        this.listener = listener;
        this.department_data_list1.addAll(department_data_list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.party_finder_view, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        this.pos = position;
        CompanylistClass pa = department_data_list.get(pos);
        holder.pd_name.setText(pa.getBY_NAME());
        holder.bind(pa, listener);

    }

    @Override
    public int getItemCount() {
        return department_data_list.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (department_data_list1.size()==0){
            this.department_data_list1.addAll(department_data_list);
        }
        department_data_list.clear();
        if (charText.length() == 0) {
            department_data_list.addAll(department_data_list1);
        } else {
            for (CompanylistClass wp : department_data_list1) {
                if (wp.getBY_NAME().toLowerCase(Locale.getDefault()).contains(charText)) {
                    department_data_list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}

