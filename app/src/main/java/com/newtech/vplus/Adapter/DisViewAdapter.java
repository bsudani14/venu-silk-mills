package com.newtech.vplus.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newtech.vplus.Model.order_list;
import com.newtech.vplus.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class DisViewAdapter extends RecyclerView.Adapter<DisViewAdapter.ViewHolder> {

    private List<order_list> orders_list = new ArrayList<>();
    private List<order_list> orders_list1 = new ArrayList<>();
    private moreDetailClickButton mdcb;
    private String orderType = null;

    public interface moreDetailClickButton{
        void ClickIt(order_list orderList);
    }

    public DisViewAdapter(String orderType, List<order_list> orders_list, moreDetailClickButton mdcb){
        this.orders_list = orders_list;
        this.orders_list1.addAll(this.orders_list);
        this.mdcb = mdcb;
        this.orderType = orderType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView orderNo,orderDate,orderPartyName,orderBrokerName,orderTotalQty,orderDiscount,ItemName;
        private Button orderMoreDetail;
        private LinearLayout discountLL;

        public ViewHolder(View itemView) {
            super(itemView);
            orderNo = (TextView) itemView.findViewById(R.id.orderNo);
            orderDate = (TextView) itemView.findViewById(R.id.orderDate);
            orderPartyName = (TextView) itemView.findViewById(R.id.orderPartyName);
            orderBrokerName = (TextView) itemView.findViewById(R.id.orderBrokerName);
            ItemName = (TextView) itemView.findViewById(R.id.ItemName);
            orderTotalQty = (TextView) itemView.findViewById(R.id.orderTotalQty);
            orderMoreDetail = (Button) itemView.findViewById(R.id.orderMoreDetail);
            discountLL = (LinearLayout) itemView.findViewById(R.id.discountLL);
            orderDiscount = (TextView) itemView.findViewById(R.id.orderDiscount);
        }

        public void bind(final order_list orderList) {
            orderMoreDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mdcb.ClickIt(orderList);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_order_list_view,null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        order_list ol = orders_list.get(position);

        holder.orderNo.setText(ol.getTcode().toString());
        holder.orderDate.setText(ol.getTdate().toString());
        holder.orderPartyName.setText(ol.getTparty().toString());
        holder.orderBrokerName.setText(ol.getTbrname().toString());
        holder.ItemName.setText(ol.getTitem().toString());
        holder.orderTotalQty.setText(ol.getTqty());

        if(!orderType.equals("Dispatch")){
            holder.discountLL.setVisibility(View.VISIBLE);
            holder.orderDiscount.setText(ol.getDiscount()+"%");
        }

        holder.bind(ol);

    }

    @Override
    public int getItemCount() {
        return orders_list.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (orders_list1.size() == 0) {
            this.orders_list1.addAll(orders_list);
        }
        orders_list.clear();
        if (charText.length() == 0) {
            orders_list.addAll(orders_list1);
        } else {
            for (order_list wp : orders_list1) {
                //for(int i = 0 ; i < wp.getOrder_item_list().size() ; i++){
                if (/*wp.getOrder_item_list().get(i).getPK_GRADEIS().toLowerCase(Locale.getDefault()).contains(charText) || */wp.getTparty().toLowerCase(Locale.getDefault()).contains(charText) || wp.getTcode().toLowerCase(Locale.getDefault()).contains(charText) || wp.getTbrname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    orders_list.add(wp);
                }
                //}
            }
        }
        notifyDataSetChanged();
    }
}
