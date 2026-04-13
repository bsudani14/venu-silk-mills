package com.venusilkmills.app.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.venusilkmills.app.Adapter.OrderPreviewAdapter;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Model.order_list;
import com.venusilkmills.app.Model.dispatch_detail;
import com.venusilkmills.app.Model.orders_detail;
import com.venusilkmills.app.R;
import com.venusilkmills.app.json.ApiClient;
import com.venusilkmills.app.json.ApiInterface;
import com.venusilkmills.app.util.Util_u;

import java.util.ArrayList;
import java.util.List;

public class sales_orders_preview1  extends BottomSheetDialogFragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private static final String TAG = "SALES_ORDER_PREVIEW";
    private String dbname = "";
    private ApiInterface apiService;
    private TextView OrderpreviewOrderNo,OrderpreviewOrderDate;
    private order_list orderList;
    private Util_u util_u;
    private OrderPreviewAdapter orderPreviewAdapter;
    private String orderType;
    Database_Helper ph;
    SQLiteDatabase db;
    private List<orders_detail> orders_detail_list = new ArrayList<>();

    public sales_orders_preview1(){
        // EMPTY CONSTRUCTOR
    }

    @SuppressLint("ValidFragment")
    public sales_orders_preview1(List<orders_detail> orderList, String orderType) {
        this.orders_detail_list = orderList;
        this.orderType = orderType;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(),R.layout.sales_orders_preview,null);
        dialog.setContentView(contentView);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressBar = (ProgressBar) contentView.findViewById(R.id.progressBarPreview);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.OrderPreviewItemListRV);
        OrderpreviewOrderNo = (TextView) contentView.findViewById(R.id.OrderpreviewOrderNo);
        OrderpreviewOrderDate = (TextView) contentView.findViewById(R.id.OrderpreviewOrderDate);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        ph = new Database_Helper(getActivity());
        db = ph.getReadableDatabase();
        dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");
        util_u = new Util_u(getContext(),getActivity());
        progressBar.setVisibility(View.VISIBLE);
        if(orders_detail_list == null){
            util_u.showToast("ORDER ITEM DETAIL ARE NULL");
            recyclerView.setAdapter(null);
        }else {
            orderPreviewAdapter = new OrderPreviewAdapter(orders_detail_list, orderType);
            recyclerView.setAdapter(orderPreviewAdapter);
        }

        progressBar.setVisibility(View.GONE);
    }


}
