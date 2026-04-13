package com.venusilkmills.app.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.venusilkmills.app.Model.order_list;
import com.venusilkmills.app.Model.order_list1;
import com.venusilkmills.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderViewlistAdapter extends RecyclerView.Adapter<OrderViewlistAdapter.ViewHolder>{
    private List<order_list1> orders_list = new ArrayList<>();
    private List<order_list1> orders_list1 = new ArrayList<>();
    private moreDetailClickButton mdcb;

    public interface moreDetailClickButton{
        void ChallanViewBtn(order_list1 orderList);
        void MoreDetailBtn(order_list1 orderList);
    }

    public OrderViewlistAdapter(List<order_list1> order_list1,moreDetailClickButton mdcb){
        this.orders_list = order_list1;
        this.orders_list1.addAll(this.orders_list);
        this.mdcb = mdcb;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView orderNo,orderDate,orderPartyName,orderBrokerName,orderTotalQty;
        private Button orderMoreDetail,Viewchallan;

        public ViewHolder(View itemView) {
            super(itemView);
            orderNo = (TextView) itemView.findViewById(R.id.orderNo);
            orderDate = (TextView) itemView.findViewById(R.id.orderDate);
            orderPartyName = (TextView) itemView.findViewById(R.id.orderPartyName);
            orderBrokerName = (TextView) itemView.findViewById(R.id.orderBrokerName);
            orderTotalQty = (TextView) itemView.findViewById(R.id.orderTotalQty);
            orderMoreDetail = (Button) itemView.findViewById(R.id.orderMoreDetail);
            Viewchallan=(Button)itemView.findViewById(R.id.view);
        }

        public void bind(final order_list1 orderList) {
            orderMoreDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mdcb.MoreDetailBtn(orderList);
                }
            });
            Viewchallan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mdcb.ChallanViewBtn(orderList);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_order_list_view1,null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        order_list1 ol = orders_list.get(position);

        holder.orderNo.setText(ol.getTcode().toString());
        holder.orderDate.setText(ol.getTdate().toString());
        holder.orderPartyName.setText(ol.getTparty().toString());
        holder.orderBrokerName.setText(ol.getTbrname().toString());

        if(ol.getType().equals("1")){
            holder.orderTotalQty.setText(ol.getTqty() +"METER");
        }
        else if(ol.getType().equals("0")){
            holder.orderTotalQty.setText(ol.getTqty() +"PCS");
        }
        else if(ol.getType().equals("2")){
            holder.orderTotalQty.setText(ol.getTqty() +"KG");
        }
        else if(ol.getType().equals("3")){
            holder.orderTotalQty.setText(ol.getTqty() +"YARD");
        }
        //holder.orderTotalQty.setText(ol.getTqty());

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
            for (order_list1 wp : orders_list1) {
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
