package com.venusilkmills.app.Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.venusilkmills.app.Model.orderlistclass;
import com.venusilkmills.app.R;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
	
	private Activity activity;
	List<orderlistclass> data=null;
	ArrayList<orderlistclass> data_Temp = null;
	private CardViewListner cardviewListener;
	
    public void setCardViewListner(CardViewListner listener) {
        this.cardviewListener = listener;
    }
    
    public interface CardViewListner {
        public void onItemAddedListner(View view, orderlistclass ItemObj, String Ftype);
    }  
	 
	public OrderListAdapter(Activity activity, ArrayList<orderlistclass> movieItems,CardViewListner th) {
	
		this.activity = activity;
		this.data = movieItems;
		this.cardviewListener=th;
		this.data_Temp = new ArrayList<orderlistclass>();
		this.data_Temp.addAll(data);
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	// Create new views (invoked by the layout manager)
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent,
			int viewType) {
		// create a new view
		View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.orderhistroy, null);

		// create ViewHolder
		ViewHolder viewHolder = new ViewHolder(itemLayoutView);
		return viewHolder;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
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
		
		viewHolder.details.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				orderlistclass m = data.get(fixint);
				cardviewListener.onItemAddedListner(v, m, "details");
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
		public ImageView details;
		
		
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
			details = (ImageView) itemLayoutView.findViewById(R.id.imageView1);
			
		}
	}

	


}