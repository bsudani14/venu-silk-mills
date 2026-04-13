package com.newtech.vplus.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newtech.vplus.Model.dispatch_detail;
import com.newtech.vplus.R;

import java.util.ArrayList;
import java.util.List;

public class DispPreviewAdapter extends RecyclerView.Adapter<DispPreviewAdapter.ViewHolder> {

    private List<dispatch_detail> orders_detail_list = new ArrayList<>();
    private String orderType;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView OrderPreviewName, OrderPreviewMeter, OrderPreviewRate, OrderPreviewTakanNo, OrderPreviewRemark, OrderPreviewshade;
        private LinearLayout RateLinearLayout, TakaLinearLayout, RemarkLinearLayout, shadeLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            OrderPreviewName = (TextView) itemView.findViewById(R.id.OrderPreviewName);
            OrderPreviewMeter = (TextView) itemView.findViewById(R.id.OrderPreviewMeter);
            OrderPreviewRate = (TextView) itemView.findViewById(R.id.OrderPreviewRate);
            OrderPreviewTakanNo = (TextView) itemView.findViewById(R.id.OrderPreviewTakanNo);
            OrderPreviewshade = (TextView) itemView.findViewById(R.id.OrderPreviewshade);
            OrderPreviewRemark = (TextView) itemView.findViewById(R.id.OrderPreviewRemark);
            RateLinearLayout = (LinearLayout) itemView.findViewById(R.id.RateLinearLayout);
            shadeLinearLayout = (LinearLayout) itemView.findViewById(R.id.shadeLinearLayout);
            TakaLinearLayout = (LinearLayout) itemView.findViewById(R.id.TakaLinearLayout);
            RemarkLinearLayout = (LinearLayout) itemView.findViewById(R.id.RemarkLinearLayout);

        }
    }

    public DispPreviewAdapter(List<dispatch_detail> orders_detail_list, String orderType) {
        this.orders_detail_list = orders_detail_list;
        this.orderType = orderType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_dis_preview_list_view, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dispatch_detail od = orders_detail_list.get(position);
        holder.OrderPreviewName.setText(od.getPK_GRADEIS().toString());

        if (orderType.equals("Dispatch")) {
            holder.OrderPreviewMeter.setText(od.getCL_QTY());
            holder.TakaLinearLayout.setVisibility(View.GONE);
            holder.OrderPreviewTakanNo.setText(od.getS_CODE());
        } else {

            if (od.getS_no().equals("0")) {
                holder.OrderPreviewMeter.setText(od.getCL_QTY()+" PCS");
            } else if (od.getS_no().equals("1")) {
                holder.OrderPreviewMeter.setText(od.getCL_QTY()+" METER");
            } else if (od.getS_no().equals("2")) {
                holder.OrderPreviewMeter.setText(od.getCL_QTY()+" KG");
            } else if (od.getS_no().equals("3")) {
                holder.OrderPreviewMeter.setText(od.getCL_QTY()+" YARD");
            }
            holder.RateLinearLayout.setVisibility(View.VISIBLE);
            holder.RemarkLinearLayout.setVisibility(View.VISIBLE);
            holder.shadeLinearLayout.setVisibility(View.VISIBLE);
            holder.OrderPreviewRate.setText(od.getTSM_Rate());
            holder.OrderPreviewRemark.setText(od.getRemark());
            holder.OrderPreviewshade.setText(od.getTsm_nar1());
        }
    }

    @Override
    public int getItemCount() {
        return orders_detail_list.size();
    }
}
