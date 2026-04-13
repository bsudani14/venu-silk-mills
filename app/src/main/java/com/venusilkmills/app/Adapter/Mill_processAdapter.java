package com.venusilkmills.app.Adapter;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.venusilkmills.app.Model.MillProcess;
import com.venusilkmills.app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Mill_processAdapter extends RecyclerView.Adapter<Mill_processAdapter.ViewHolder> {

    private Activity activity;
    List<MillProcess> data = null;
    ArrayList<MillProcess> data_Temp = null;


    public Mill_processAdapter(List<MillProcess> movieItems) {

        this.data = movieItems;
        this.data_Temp = new ArrayList<MillProcess>();
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
                R.layout.millprocesssub, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final int fixint = position;
        MillProcess m = data.get(position);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String curdate=mdformat.format(calendar.getTime());


        viewHolder.chno.setText(m.getSTK_ISSCHNO3());
        viewHolder.iname.setText(m.getI_NAME1());
        viewHolder.pname.setText(m.getP_Name());
        viewHolder.dt.setText(m.getSTK_ISSDT());
        viewHolder.lno.setText(m.getSTK_TAKA());
        viewHolder.shade.setText(m.getGSM_Name());
        viewHolder.design.setText(m.getSTK_DesignCode());
        viewHolder.mtr.setText(m.getStk_Mtr());
        viewHolder.wt.setText(m.getStk_Weight());



    }

       /* public void filter(String charText) {
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
*/

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView chno;
        public TextView dt;
        public TextView pname;
        public TextView iname;
        public TextView shade;
        public TextView design;
        public TextView lno;
        public TextView wt;
        public TextView mtr;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            chno = (TextView) itemLayoutView.findViewById(R.id.chno);
            dt = (TextView) itemLayoutView.findViewById(R.id.dt);
            pname = (TextView) itemLayoutView.findViewById(R.id.pname);
            iname = (TextView) itemLayoutView.findViewById(R.id.iname);
            shade = (TextView) itemLayoutView.findViewById(R.id.shade);
            design = (TextView) itemLayoutView.findViewById(R.id.dsigname);
            lno = (TextView) itemLayoutView.findViewById(R.id.lno);
            wt = (TextView) itemLayoutView.findViewById(R.id.wt);
            mtr = (TextView) itemLayoutView.findViewById(R.id.mtr);

        }
    }


}
