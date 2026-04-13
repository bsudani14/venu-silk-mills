package com.newtech.vplus.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newtech.vplus.Activity.ordermenu;
import com.newtech.vplus.Model.getorder_Class;
import com.newtech.vplus.Model.order_Data;
import com.newtech.vplus.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class getOrder_Adapter extends RecyclerView.Adapter<getOrder_Adapter.ViewHolder> {
    private List<getorder_Class> getorder_classes = new ArrayList<getorder_Class>();
    private List<getorder_Class> getorder_classes1 = new ArrayList<getorder_Class>();
    Context context;
    private getOrder_Adapter.OnItemClickListener listener;
    int pos;
    View rootView;
    ordermenu ordermenu;
    boolean isOnTextChanged = false;
    int ExpenseFinalTotal = 0;
    EditText tot_quantity;
    ArrayList<String> ExpAmtArray = new ArrayList<String>();
    ArrayList<String> list = new ArrayList<String>();


    public interface OnItemClickListener {
        void onItemClick2(getorder_Class dpm1);
    }

    public getOrder_Adapter(List<getorder_Class> getorder_classes, ordermenu ordermenu, OnItemClickListener listener) {
        this.getorder_classes = getorder_classes;
        this.ordermenu = ordermenu;
        this.listener = listener;
        this.getorder_classes1.addAll(getorder_classes);

    }
    @NonNull
    @Override
    public getOrder_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.getorder_itemlist, null);
        context = parent.getContext();
        rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        tot_quantity = (EditText)rootView.findViewById(R.id.tot_quantity);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull final getOrder_Adapter.ViewHolder holder, final int position) {
        this.pos = position;
        final getorder_Class pa = getorder_classes.get(pos);
     holder.setIsRecyclable(false);
        holder.shadetext.setText(pa.getPdm_shade());

        //holder.edit_order_quant.setTag(position);
        holder.pending.setText("0");


       holder.edit_order_quant.setText(String.valueOf(getorder_classes.get(position).getQuantity()));

     // getorder_classes.get(position).setQuantity(getorder_classes.get(position).getQuantity());

        holder.edit_order_quant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // isOnTextChanged = true;

             try {
                    if (!(holder.edit_order_quant.getText().toString().equals(""))) {
                        holder.showvalue.setText(holder.edit_order_quant.getText().toString());
                      //  holder.aa.setBackgroundColor(Color.RED);
                        getorder_classes.get(position).setQuantity(Double.parseDouble(holder.showvalue.getText().toString()));
                        ordermenu.countvalue(getorder_classes1);

                    } else {

                       holder.showvalue.setText("");
                      //  holder.aa.setBackgroundColor(Color.WHITE);
                       getorder_classes.get(position).setQuantity(Double.parseDouble(String.valueOf(0)));
                        ordermenu.countvalue(getorder_classes1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
              /*  ExpenseFinalTotal = 0;
                if (isOnTextChanged) {
                    isOnTextChanged = false;

                    try {
                        ExpenseFinalTotal = 0;


                        for (int i = 0; i <= position; i++) {

                            int inposition1 = position;
                            if (i != position) {
                                //store 0  where user select position in not equal/
                                ExpAmtArray.add("0");

                            }else {

                                // store user entered value to Array list (ExpAmtArray) at particular position
                                ExpAmtArray.add("0");
                                ExpAmtArray.set(inposition1,s.toString());

                                break;
                            }

                        }

                        // for statement to loop to the array, to calculate the Expense total.
                        for (int i = 0; i <= ExpAmtArray.size() - 1; i++) {

                            int tempTotalExpenase = Integer.parseInt(ExpAmtArray.get(i));
                            ExpenseFinalTotal  = ExpenseFinalTotal + tempTotalExpenase;

                        }

                        tot_quantity.setText(String.valueOf(ExpenseFinalTotal));
                    }catch (NumberFormatException e)
                    {
                        // catch is used because, when used enter value in editText and remove the value it
                        // it will trigger NumberFormatException, so to prevent it and remove data value from array ExpAmtArray
                        //then
                        // re-perform loop total expense calculation and display the total.

                        ExpenseFinalTotal = 0;
                        for (int i = 0; i <= position; i++) {
                            Log.d("TimesRemoved", " : " + i);
                            int newposition = position;
                            if (i == newposition) {
                                ExpAmtArray.set(newposition,"0");
                            }
                        }

                        for (int i = 0; i <= ExpAmtArray.size() - 1; i++) {

                            int tempTotalExpenase = Integer.parseInt(ExpAmtArray.get(i));
                            ExpenseFinalTotal  = ExpenseFinalTotal + tempTotalExpenase;

                        }
                        tot_quantity.setText(String.valueOf(ExpenseFinalTotal));
                    }

                }*/

            }
        });

      /*  if(!(holder.edit_order_quant.getText().toString().equals(0.0))){
            // && department_data_list.get(position).getDisqty().equals(pa.getOsm_qty())
            holder.aa.setBackgroundColor(Color.RED);
        }else{

            holder.aa.setBackgroundColor(Color.WHITE);
        }
*/
    }

    @Override
    public int getItemCount() {
        return getorder_classes.size();
    }



@Override
    public long getItemId(int position) {
        return  position;
    }


   @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView shadetext, pending,showvalue;
        EditText edit_order_quant;
        LinearLayout aa;

        public ViewHolder(View itemView) {
            super(itemView);
            shadetext =(TextView)itemView.findViewById(R.id.shadetext);
            pending =(TextView)itemView.findViewById(R.id.pending);
            edit_order_quant =(EditText) itemView.findViewById(R.id.edit_order_quant);
            showvalue =(TextView)itemView.findViewById(R.id.svalue);
            aa =(LinearLayout) itemView.findViewById(R.id.aa);


        }
/*        public void bind(final getorder_Class dpm1, final getOrder_Adapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick2(dpm1);
                }
            });
        }*/
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (getorder_classes1.size() == 0) {
            this.getorder_classes1.addAll(getorder_classes);
        }
        getorder_classes.clear();
        if (charText.length() == 0) {
            getorder_classes.addAll(getorder_classes1);
        } else {
            for (getorder_Class stockListModel : getorder_classes1) {
                if (stockListModel.getPdm_shade().toLowerCase(Locale.getDefault()).contains(charText)) {

                    getorder_classes.add(stockListModel);


                }
            }
        }
     notifyDataSetChanged();
    }
}
