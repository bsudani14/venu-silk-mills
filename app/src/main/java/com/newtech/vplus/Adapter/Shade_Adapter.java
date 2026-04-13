package com.newtech.vplus.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newtech.vplus.Model.order_Data;
import com.newtech.vplus.Model.shade_Data;
import com.newtech.vplus.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Shade_Adapter extends RecyclerView.Adapter<Shade_Adapter.ViewHolder> {
    private List<shade_Data> shade_list = new ArrayList<shade_Data>();
    private List<shade_Data> shade_list1 = new ArrayList<shade_Data>();
    Context context;
    private Shade_Adapter.OnItemClickListener listener;
    int pos;

    public interface OnItemClickListener {
        void onItemClick1(shade_Data dpm1);
    }

    @NonNull
    @Override
    public Shade_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.order_item, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull Shade_Adapter.ViewHolder holder, int position) {
        this.pos = position;
        shade_Data pa = shade_list.get(pos);
        holder.shade_name.setText(pa.getShade_cardno());

        holder.bind(pa, listener);
    }

    @Override
    public int getItemCount() {
        return shade_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView shade_name;

        public ViewHolder(View itemView) {
            super(itemView);
            shade_name = (TextView) itemView.findViewById(R.id.item_name);

        }

        public void bind(final shade_Data dpm1, final Shade_Adapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick1(dpm1);
                }
            });
        }
    }
    public Shade_Adapter(List<shade_Data> shade_data, Context context, Shade_Adapter.OnItemClickListener listener) {
        this.shade_list = shade_data;
        this.shade_list1.addAll(shade_list);
        this.context = context;
        this.listener = listener;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (shade_list1.size() == 0) {
            this.shade_list1.addAll(shade_list);
        }
        shade_list.clear();
        if (charText.length() == 0) {
            shade_list.addAll(shade_list1);
        } else {
            for (shade_Data stockListModel : shade_list1) {
                if (stockListModel.getShade_cardno().toLowerCase(Locale.getDefault()).contains(charText)) {
                    shade_list.add(stockListModel);
                }
            }
        }
        notifyDataSetChanged();
    }
}
