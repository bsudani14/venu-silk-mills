package com.newtech.vplus.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newtech.vplus.Activity.Dispatch_activity;
import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.Model.DispachsubClas1;
import com.newtech.vplus.Model.OrderschClass;
import com.newtech.vplus.Model.tempcass;
import com.newtech.vplus.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderSch_adapter extends RecyclerView.Adapter<OrderSch_adapter.ViewHolder> {
    private List<OrderschClass> department_data_list = new ArrayList<OrderschClass>();
    private List<OrderschClass> department_data_list1 = new ArrayList<OrderschClass>();
    Context context;
    private OnItemClickListener listener;
    int pos;
    Dispatch_activity dispatch_activity;
    int row_index =0;
    SQLiteDatabase db;
    Database_Helper ph;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    List<tempcass>tc=new ArrayList<>();
    List<DispachsubClas1> Disclass2 = new ArrayList<>();



    public interface OnItemClickListener {
        boolean onItemClick(OrderschClass dpm1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView orrno,date,pname,brcode,qty,lotno,grade,shade,rackno,sqty,oqty,saqty,showvalue,srno,disqty;
        LinearLayout liner;
        CheckBox canclecheckBox;


        public ViewHolder(View itemView) {
            super(itemView);
            orrno = (TextView) itemView.findViewById(R.id.code);
            rackno = (TextView) itemView.findViewById(R.id.rackno);
            sqty = (TextView) itemView.findViewById(R.id.sqty);
            liner = (LinearLayout) itemView.findViewById(R.id.liner);
            oqty = (TextView) itemView.findViewById(R.id.oqty);
            saqty = (TextView) itemView.findViewById(R.id.saqty);
            date= (TextView) itemView.findViewById(R.id.date);
            pname= (TextView) itemView.findViewById(R.id.pname);
            showvalue =(TextView)itemView.findViewById(R.id.svalue);
            srno =(TextView)itemView.findViewById(R.id.srno);
            //canclecheckBox =(CheckBox) itemView.findViewById(R.id.orderca);

         //   brcode= (TextView) itemView.findViewById(R.id.brname);
          //  qty= (TextView) itemView.findViewById(R.id.qty);
            lotno= (TextView) itemView.findViewById(R.id.lotno);
             grade= (TextView) itemView.findViewById(R.id.grd);
            shade= (TextView) itemView.findViewById(R.id.shade);
            disqty= (TextView) itemView.findViewById(R.id.disqty);

        }

        public void bind(final OrderschClass dpm1, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(dpm1);
/*
                    if(listener.onItemClick(dpm1)){
                        liner.setBackgroundColor(Color.RED);
                        tc.add(new tempcass(dpm1.srno));
                        stringArrayList.add(String.valueOf(dpm1.srno));

                    }
                    else {
                        liner.setBackgroundColor(Color.WHITE);
                    }

                    db = ph.getWritableDatabase();
                    //   db.delete("Pos", null, null);
                    //db = ph.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("post", String.valueOf(stringArrayList));
                    db.insert("Pos", null, cv);

*/


                }
            });
        }


    }

    public OrderSch_adapter(List<OrderschClass> department_data_lists,List<tempcass> tt, Context context, Dispatch_activity dispatch_activity, OnItemClickListener listener) {
        this.department_data_list = department_data_lists;
        this.department_data_list1.addAll(department_data_list);
        this.tc=tt;
        this.context = context;
        this.dispatch_activity = dispatch_activity;
        this.ph = new Database_Helper(context.getApplicationContext());
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.orderschcustome, null);

        // create ViewHolder

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       // this.pos = position;
        OrderschClass pa = department_data_list.get(position);
        holder.orrno.setText(department_data_list.get(position).getOsm_code());
        holder.rackno.setText(department_data_list.get(position).getRackno());
        holder.sqty.setText(department_data_list.get(position).getStkqty());
        holder.oqty.setText(department_data_list.get(position).getOsm_qty());
        holder.saqty.setText(department_data_list.get(position).getOsm_SALQTY());
      //  holder.brcode.setText(pa.getOsm_brname());
        holder.pname.setText(department_data_list.get(position).getOsm_delparty());
       // holder.qty.setText(pa.getOsm_qty());
        holder.date.setText(department_data_list.get(position).getOsm_date());
        holder.lotno.setText(department_data_list.get(position).getOsm_lotno());
        holder.grade.setText(department_data_list.get(position).getOsm_grade());
        holder.shade.setText(department_data_list.get(position).getShade());
        holder.srno.setText(department_data_list.get(position).getSrno());
        holder.disqty.setText(department_data_list.get(position).getDisqty());




        if(department_data_list.get(position).isCol()){
            // && department_data_list.get(position).getDisqty().equals(pa.getOsm_qty())
                holder.liner.setBackgroundColor(Color.RED);
        }else{

            holder.liner.setBackgroundColor(Color.WHITE);
        }

        /*for(int i=0; i<tc.size(); i++){
            if(tc.get(i).getSrono().equals(department_data_list1.get(position).getSrno())){
                holder.liner.setBackgroundColor(Color.RED);
            }else{
            // holder.liner.setBackgroundColor(Color.WHITE);
            }
        }*/



        holder.bind(pa, listener);
        department_data_list.get(position).setStkqty(department_data_list.get(position).getStkqty());

        String zz =ph.GetVal("Select post From Pos");

        try {
            if (!(holder.oqty.getText().toString().equals(""))) {
                holder.showvalue.setText(holder.oqty.getText().toString());
                department_data_list.get(position).setOsm_qty(holder.showvalue.getText().toString());
                dispatch_activity.countvalue(department_data_list);
            } else {

                holder.showvalue.setText("");
                department_data_list.get(position).setOsm_qty(String.valueOf(0));
                dispatch_activity.countvalue(department_data_list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            for (OrderschClass stockListModel : department_data_list1) {
                if (stockListModel.getOsm_delparty().toLowerCase(Locale.getDefault()).contains(charText) || stockListModel.getShade().toLowerCase(Locale.getDefault()).contains(charText) || stockListModel.getOsm_lotno().toLowerCase(Locale.getDefault()).contains(charText)) {
                    department_data_list.add(stockListModel);
                }
            }
        }
        notifyDataSetChanged();
    }


}




