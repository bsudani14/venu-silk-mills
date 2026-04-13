package com.venusilkmills.app.Adapter;

        import android.content.Context;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.venusilkmills.app.Model.stock_model;
        import com.venusilkmills.app.R;

        import java.util.ArrayList;

public class stock_adapter extends RecyclerView.Adapter<stock_adapter.ViewHolder> {
    Context context;
    ArrayList<stock_model> models;
    Onclick onclick;

    public interface Onclick {
        void onEvent(stock_model model,int pos);
    }

    public stock_adapter(Context context, ArrayList<stock_model> models, Onclick onclick) {
        this.context = context;
        this.models = models;
        this.onclick = onclick;
    }
    View view;

    @NonNull
    @Override
    public stock_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final stock_model model = models.get(position);
        if (model.getName() != null) {
            holder.itemName.setText(model.getName());
        }

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onEvent(model,position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        LinearLayout llItem;
        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_name);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}
