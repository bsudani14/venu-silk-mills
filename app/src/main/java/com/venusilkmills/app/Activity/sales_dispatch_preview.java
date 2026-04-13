package com.venusilkmills.app.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.venusilkmills.app.Adapter.DispPreviewAdapter;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Model.order_list;
import com.venusilkmills.app.Model.dispatch_detail;
import com.venusilkmills.app.R;
import com.venusilkmills.app.json.ApiClient;
import com.venusilkmills.app.json.ApiInterface;
import com.venusilkmills.app.util.Util_u;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sales_dispatch_preview extends BottomSheetDialogFragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private static final String TAG = "SALES_ORDER_PREVIEW";
    private String dbname,ftype;
    private List<dispatch_detail> orders_item_detail = new ArrayList<>();
    private ApiInterface apiService;
    private TextView OrderpreviewOrderNo,OrderpreviewOrderDate;
    private order_list orderList;
    private Util_u util_u;
    private DispPreviewAdapter orderPreviewAdapter;
    private String orderType;
    private Database_Helper ph;
    private SQLiteDatabase db;

    public sales_dispatch_preview(){
        // EMPTY CONSTRUCTOR
    }

    public sales_dispatch_preview(order_list orderList, String orderType) {
        this.orderList = orderList;
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

        util_u = new Util_u(getContext(),getActivity());

        ph = new Database_Helper(getContext());
        db = ph.getReadableDatabase();


        OrderpreviewOrderNo.setText(orderList.getTcode());
        dbname = ph.GetVal("Select dbname from andmst where LOGIN='T'");
        ftype = ph.GetVal("Select ftype from andmst where LOGIN='T'");

        OrderpreviewOrderDate.setText(orderList.getTdate());

        orders_item_detail.clear();
        progressBar.setVisibility(View.VISIBLE);

        Call<List<order_list>> call;

        if(orderType.equals("Dispatch")){
            call = apiService.getDispatchOrderData("MEY",orderList.getTcode(),"","");

        }else {
            call = apiService.getOrders(orderType, ftype, "", "MEY", orderList.getTcode());
        }
        call.enqueue(new Callback<List<order_list>>() {
            @Override
            public void onResponse(Call<List<order_list>> call, Response<List<order_list>> response) {
                orders_item_detail = response.body().get(0).getOrder_item_list();
                progressBar.setVisibility(View.GONE);
                if (orders_item_detail.size() == 0) {
                    util_u.showToast("ORDER ITEM DETAIL ARE NULL");
                    recyclerView.setAdapter(null);
                } else {
                    orderPreviewAdapter = new DispPreviewAdapter(orders_item_detail, orderType);
                    recyclerView.setAdapter(orderPreviewAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<order_list>> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }

}
