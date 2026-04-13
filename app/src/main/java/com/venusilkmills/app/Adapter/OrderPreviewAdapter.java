package com.venusilkmills.app.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.venusilkmills.app.Model.dispatch_detail;
import com.venusilkmills.app.Model.orders_detail;
import com.venusilkmills.app.R;

import java.util.ArrayList;
import java.util.List;

public class OrderPreviewAdapter extends RecyclerView.Adapter<OrderPreviewAdapter.ViewHolder> {

    private List<orders_detail> orders_detail_list = new ArrayList<>();
    private String orderType;

    public class ViewHolder extends RecyclerView.ViewHolder{


        private TextView tno,iname,shade,design,size;


        public ViewHolder(View itemView) {
            super(itemView);
            tno = (TextView) itemView.findViewById(R.id.tno);
            iname = (TextView) itemView.findViewById(R.id.iname);
            shade = (TextView) itemView.findViewById(R.id.shade);
            design = (TextView) itemView.findViewById(R.id.des);
            size = (TextView) itemView.findViewById(R.id.size);
        }
    }

    public OrderPreviewAdapter(List<orders_detail> orders_detail_list,String orderType){
        this.orders_detail_list = orders_detail_list;
        this.orderType = orderType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_order_preview_list_view,null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        orders_detail od = orders_detail_list.get(position);
        holder.tno.setText(od.getT_no().toString());
        holder.iname.setText(od.getPK_GRADEIS());
        holder.shade.setText(od.getCh_shade());
        holder.design.setText(od.getCh_design());
        holder.size.setText(od.getCL_QTY());

    }

    @Override
    public int getItemCount() {
        return orders_detail_list.size();
    }
}
