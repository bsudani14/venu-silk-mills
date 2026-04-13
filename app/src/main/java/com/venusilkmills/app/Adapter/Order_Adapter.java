package com.venusilkmills.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.venusilkmills.app.Model.OrderschClass;
import com.venusilkmills.app.Model.order_Data;
import com.venusilkmills.app.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.ViewHolder> {
    private List<order_Data> department_data_list = new ArrayList<order_Data>();
    private List<order_Data> department_data_list1 = new ArrayList<order_Data>();
    Context context;
    private Order_Adapter.OnItemClickListener listener;
    int pos;

    public interface OnItemClickListener {
        void onItemClick(order_Data dpm1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_name;

        public ViewHolder(View itemView) {
            super(itemView);
          /*  orrno = (TextView) itemView.findViewById(R.id.code);
            rackno = (TextView) itemView.findViewById(R.id.rackno);
            sqty = (TextView) itemView.findViewById(R.id.sqty);
            oqty = (TextView) itemView.findViewById(R.id.oqty);
            saqty = (TextView) itemView.findViewById(R.id.saqty);
            date= (TextView) itemView.findViewById(R.id.date);
            pname= (TextView) itemView.findViewById(R.id.pname);
            //   brcode= (TextView) itemView.findViewById(R.id.brname);
            //  qty= (TextView) itemView.findViewById(R.id.qty);
            lotno= (TextView) itemView.findViewById(R.id.lotno);
            grade= (TextView) itemView.findViewById(R.id.grd);
            shade= (TextView) itemView.findViewById(R.id.shade);*/
            item_name = (TextView) itemView.findViewById(R.id.item_name);
        }

        public void bind(final order_Data dpm1, final Order_Adapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(dpm1);
                }
            });
        }
    }

    public Order_Adapter(List<order_Data> department_data_lists, Context context, Order_Adapter.OnItemClickListener listener) {
        this.department_data_list = department_data_lists;
        this.department_data_list1.addAll(department_data_list);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Order_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.order_item, null);

        // create ViewHolder
        Order_Adapter.ViewHolder viewHolder = new Order_Adapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Order_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.pos = position;
        order_Data pa = department_data_list.get(pos);
        holder.item_name.setText(pa.getI_name1());
     /*   holder.orrno.setText(pa.getOsm_code());
        holder.rackno.setText(pa.getRackno());
        holder.sqty.setText(pa.getStkqty());
        holder.oqty.setText(pa.getOsm_qty());
        holder.saqty.setText(pa.getOsm_SALQTY());
        //  holder.brcode.setText(pa.getOsm_brname());
        holder.pname.setText(pa.getOsm_delparty());
        // holder.qty.setText(pa.getOsm_qty());
        holder.date.setText(pa.getOsm_date());
        holder.lotno.setText(pa.getOsm_lotno());
        holder.grade.setText(pa.getOsm_grade());
        holder.shade.setText(pa.getShade());*/
        holder.bind(pa, listener);
    }

    @Override
    public int getItemCount() {
        return department_data_list.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (department_data_list1.size() == 0) {
            this.department_data_list1.addAll(department_data_list);
        }
        department_data_list.clear();
        if (charText.length() == 0) {
            department_data_list.addAll(department_data_list1);
        } else {
            for (order_Data stockListModel : department_data_list1) {
                if (stockListModel.getI_name1().toLowerCase(Locale.getDefault()).contains(charText)) {
                    department_data_list.add(stockListModel);
                }
            }
        }
        notifyDataSetChanged();
    }


}