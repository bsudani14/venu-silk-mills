package com.venusilkmills.app.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.venusilkmills.app.Model.party_data_end;
import com.venusilkmills.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class party_finder_adapter extends RecyclerView.Adapter<party_finder_adapter.ViewHolder> {

    private List<party_data_end> party_data_list_new = new ArrayList<party_data_end>();
    private List<party_data_end> party_data_list_new1 = new ArrayList<>();

    Context context;
    private OnItemClickListener listener;
    int pos;
    private String atype;

    public interface OnItemClickListener {
        void onItemClick(party_data_end pda1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pd_name, pd_code;

        public ViewHolder(View itemView) {
            super(itemView);
            pd_name = (TextView) itemView.findViewById(R.id.pd_name);
            pd_code = (TextView) itemView.findViewById(R.id.pd_code);
        }

        public void bind(final party_data_end pda2, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(pda2);
                }
            });
        }
    }

    public party_finder_adapter(List<party_data_end> party_data_al, Context context, OnItemClickListener listener) {
        this.party_data_list_new = party_data_al;
        this.party_data_list_new1.addAll(this.party_data_list_new);
        this.context = context;
        this.listener = listener;
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
        party_data_end pa = party_data_list_new.get(pos);
        holder.pd_name.setText(pa.getPname());
        holder.pd_code.setText(pa.getPcode());

        holder.bind(pa, listener);

    }

    @Override
    public int getItemCount() {
        return party_data_list_new.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (party_data_list_new1.size() == 0) {
            this.party_data_list_new1.addAll(party_data_list_new);
        }
        party_data_list_new.clear();
        if (charText.length() == 0) {
            party_data_list_new.addAll(party_data_list_new1);
        } else {
            for (party_data_end wp : party_data_list_new1) {
                if (wp.getPname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    party_data_list_new.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
