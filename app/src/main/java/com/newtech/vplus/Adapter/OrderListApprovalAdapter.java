package com.newtech.vplus.Adapter;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newtech.vplus.Model.orderlistclass;
import com.newtech.vplus.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderListApprovalAdapter  extends RecyclerView.Adapter<OrderListApprovalAdapter.ViewHolder> {

    private Activity activity;
    List<orderlistclass> data=null;
    ArrayList<orderlistclass> data_Temp = null;
    private CardViewListner cardviewListener;
    String type;

    public void setCardViewListner(CardViewListner listener) {
        this.cardviewListener = listener;
    }

    public interface CardViewListner {
        public void onItemAddedListner(View view, orderlistclass ItemObj, String Ftype,int i);
    }

    public OrderListApprovalAdapter(Activity activity, ArrayList<orderlistclass> movieItems,CardViewListner th,String Type) {
        this.activity = activity;
        this.data = movieItems;
        this.cardviewListener=th;
        this.data_Temp = new ArrayList<orderlistclass>();
        this.data_Temp.addAll(data);
        this.type=Type;
    }

    @Override
    public  int getItemCount() {
        return data.size();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.orderapproval, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final int fixint=position;
        orderlistclass m = data.get(position);
        viewHolder.txtorder.setText(m.getono());
        viewHolder.txtdttime.setText(String.valueOf(m.getodt()));
        viewHolder.txtbr.setText(m.getobrname());
        viewHolder.txtsp.setText(m.getosp());
        viewHolder.txtpn.setText(m.getopname());
        viewHolder.txtqty.setText(m.getoqty());
        viewHolder.txtcr.setText(m.getocr());
        viewHolder.txtiname.setText(m.getoitem());

        if(type.equals("OREDERUNAPPROVAL")){
            viewHolder.unapp.setVisibility(View.GONE);
            viewHolder.appro.setVisibility(View.GONE);
            viewHolder.txtreason.setText(m.getOreason());
            viewHolder.r2.setVisibility(View.GONE);

        }
        else{
            viewHolder.unapp.setVisibility(View.VISIBLE);
            viewHolder.appro.setVisibility(View.VISIBLE);
            viewHolder.r1.setVisibility(View.GONE);
            viewHolder.txtrmt.setText(m.getT_LOCKTERMS());
        }
        viewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderlistclass m = data.get(fixint);
                cardviewListener.onItemAddedListner(v, m, "details",position);
            }
        });
        viewHolder.appro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderlistclass m = data.get(fixint);
                cardviewListener.onItemAddedListner(v, m, "APPROVAL",position);
            }
        });
        viewHolder.unapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderlistclass m = data.get(fixint);
                cardviewListener.onItemAddedListner(v, m, "UNAPPROVAL",position);
            }
        });

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (data_Temp.size()==0)
        {
            this.data_Temp.addAll(data);
        }
        data.clear();
        if (charText.length() == 0) {
            data.addAll(data_Temp);
        } else {
            for (orderlistclass wp : data_Temp) {
                if (wp.getopname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtorder;
        public TextView txtdttime;
        public TextView txtbr;
        public TextView txtsp;
        public TextView txtpn;
        public TextView txtqty;
        public TextView txtcr;
        public TextView txtiname;
        public TextView txtreason,txtrmt;
        public ImageView details;
        RelativeLayout r1,r2;
        public Button appro,unapp;


        //this.getClass().getSimpleName()

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtorder = (TextView) itemLayoutView.findViewById(R.id.orderno);
            txtdttime = (TextView) itemLayoutView.findViewById(R.id.orderdate);
            txtbr = (TextView) itemLayoutView.findViewById(R.id.txtbroker);
            txtsp = (TextView) itemLayoutView.findViewById(R.id.txtsale);
            txtpn = (TextView) itemLayoutView.findViewById(R.id.textView3);
            txtqty = (TextView) itemLayoutView.findViewById(R.id.txtqty);
            txtcr = (TextView) itemLayoutView.findViewById(R.id.cr);
            txtiname = (TextView) itemLayoutView.findViewById(R.id.txtitemnam);
            txtreason=(TextView)itemLayoutView.findViewById(R.id.txtres);
            txtrmt=(TextView)itemLayoutView.findViewById(R.id.txtrmt);
            details = (ImageView) itemLayoutView.findViewById(R.id.imageView1);
            r1=(RelativeLayout) itemLayoutView.findViewById(R.id.r6);
            r2=(RelativeLayout) itemLayoutView.findViewById(R.id.r7);
            appro=(Button)itemLayoutView.findViewById(R.id.appro);
            unapp=(Button)itemLayoutView.findViewById(R.id.unapp);

        }
    }




}
