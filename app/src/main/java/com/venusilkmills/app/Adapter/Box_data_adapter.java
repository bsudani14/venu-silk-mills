package com.venusilkmills.app.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.venusilkmills.app.Model.PackingClass;
import com.venusilkmills.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Box_data_adapter extends RecyclerView.Adapter<Box_data_adapter.ViewHolder> {

    private List<PackingClass> stockList = new ArrayList<>();
    private List<PackingClass> stockList1 = new ArrayList<>();
    private OnLotClickListener onLotClickListener;
    private String atype;

    public interface OnLotClickListener {
        void onItemClick(PackingClass stockListModel);
        void onItemdelete(PackingClass stockListModel, int loc);

    }

    public Box_data_adapter(List<PackingClass> stockList, String atype, OnLotClickListener onLotClickListener) {
        this.stockList = stockList;
        this.stockList1.addAll(stockList);
        this.atype = atype;
        this.onLotClickListener = onLotClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView iname, isize,qty,cop,nub,shade,lot;
        private LinearLayout delete;
        public ViewHolder(View itemView) {
            super(itemView);
            iname = (TextView) itemView.findViewById(R.id.iname);
            isize = (TextView) itemView.findViewById(R.id.isize);
            //qty= (TextView) itemView.findViewById(R.id.qty);
            lot= (TextView) itemView.findViewById(R.id.lot);
            cop= (TextView) itemView.findViewById(R.id.cop);
            nub= (TextView) itemView.findViewById(R.id.nub);
            shade= (TextView) itemView.findViewById(R.id.shade);
            delete= (LinearLayout) itemView.findViewById(R.id.delete);
        }

        public void bind(final PackingClass stockListModel, final OnLotClickListener onLotClickListener, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLotClickListener.onItemClick(stockListModel);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLotClickListener.onItemdelete(stockListModel,position);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.packing_custome_dispatch, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PackingClass stockListModel = stockList.get(position);
        holder.iname.setText(stockListModel.getPk_boxno().toString());
        holder.isize.setText(stockListModel.getPk_shade().toString());
       // holder.qty.setText(stockListModel.getPk_itemcode().toString());
        holder.cop.setText(stockListModel.getPk_grade().toString());
        holder.lot.setText(stockListModel.getPk_lotno().toString());
        holder.shade.setText(stockListModel.getPk_shade().toString());
        holder.bind(stockListModel, onLotClickListener,position);
        holder.nub.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (stockList1.size() == 0) {
            this.stockList1.addAll(stockList);
        }
        stockList.clear();
        if (charText.length() == 0) {
            stockList.addAll(stockList1);
        } else {
            for (PackingClass stockListModel : stockList1) {
                if (stockListModel.getPk_boxno().toLowerCase(Locale.getDefault()).contains(charText)) {
                    stockList.add(stockListModel);
                }
            }
        }
    }
}

