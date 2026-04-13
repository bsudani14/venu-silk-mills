package com.newtech.vplus.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.newtech.vplus.Adapter.OrderViewlistAdapter;
import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.Model.order_list1;
import com.newtech.vplus.Model.dispatch_detail;
import com.newtech.vplus.Model.orders_detail;
import com.newtech.vplus.R;
import com.newtech.vplus.json.ApiClient;
import com.newtech.vplus.json.ApiInterface;
import com.newtech.vplus.util.EndlessRecyclerOnScrollListener;
import com.newtech.vplus.util.Util_u;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderList extends AppCompatActivity {

    private Util_u util;
    private Button TypebtnAll, TypebtnPending, TypebtnClear, TypebtnDispatch;
    private List<Button> topNavButtonsID = new ArrayList<>();
    private SearchView searchView;
    private ProgressBar progressBar;
    private List<order_list1> orders_list = new ArrayList<order_list1>();
    private List<orders_detail> orderssub_list = new ArrayList<>();
    private ArrayList<order_list1> orders_list_copy = new ArrayList<order_list1>();
    private ApiInterface apiService;
    private String dbname = "";
    private static final String TAG = "SALES_ORDER_VIEW";
    private OrderViewlistAdapter orderViewlistAdapter = null;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private dispatch_detail ordersDetail;
    private boolean enable = true;
    Database_Helper ph;
    SQLiteDatabase db;
    String ordtype = "dishpatch";
    int id;
    Bundle b;
    String partycode;
    LinearLayout tl1, fl1;
    private Calendar calendar;
    private Calendar calendar1;
    private Calendar calendar3;
    private DatePickerDialog datePickerDialog1, datePickerDialog2;
    private String[] fromto = new String[2];
    private String[] fromto1 = new String[2];
    TextView todt, fdt;
    int year;
    int month;
    int day;
    StringBuilder sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setElevation(0);
        toolbar.setElevation(0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        b = getIntent().getExtras();
        partycode = b.getString("brcode");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ph = new Database_Helper(getApplicationContext());
        db = ph.getReadableDatabase();
        id = 1;
        dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");

        util = new Util_u(OrderList.this, this);
        tl1 = (LinearLayout) findViewById(R.id.tdate);
        fl1 = (LinearLayout) findViewById(R.id.fdate);
        todt = (TextView) findViewById(R.id.todt);
        fdt = (TextView) findViewById(R.id.fdt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.orderListRV);
        orders_list = new ArrayList<order_list1>();
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        tl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToDate();
            }
        });
        fl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFromDate();
            }
        });
        calendar3 = Calendar.getInstance();
        sb = new StringBuilder().append((calendar3.get(Calendar.MONTH)+1 < 4) ? calendar3.get(Calendar.YEAR)-1 : calendar3.get(Calendar.YEAR)).append("-")
                .append(04).append("-").append(01);

        todt.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime()));

        fdt.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime()));
        // fdt.setText(new StringBuilder().append(01).append("-")
        //       .append(04).append("-").append((calendar3.get(Calendar.MONTH)+1 < 4) ? calendar3.get(Calendar.YEAR)-1 : calendar3.get(Calendar.YEAR)));
        fromto1[0] = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        fromto1[1] = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());

        orderViewlistAdapter = new OrderViewlistAdapter(orders_list_copy, new OrderViewlistAdapter.moreDetailClickButton() {
            @Override
            public void ChallanViewBtn(order_list1 orderList) {
                String s1 = orderList.getTcode();
                Intent i = new Intent(getApplicationContext(), Challanviewactivity.class);
                i.putExtra("chno", s1);
                startActivity(i);
            }

            @Override
            public void MoreDetailBtn(order_list1 orderList) {

                String s1 = orderList.getTcode();
                getsubdata(s1);

            }
        });
        recyclerView.setAdapter(orderViewlistAdapter);
        getOrderData("Dispatch", 1);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                orderViewlistAdapter.notifyDataSetChanged();
                getOrderData("Dispatch", current_page);
            }
        });

    }

    public void getFromDate() {

        calendar = Calendar.getInstance();
        year = (calendar.get(Calendar.MONTH)+1 < 4) ? calendar.get(Calendar.YEAR)-1 : calendar.get(Calendar.YEAR);
        month = 3;
        day = 1;

        datePickerDialog1 = new DatePickerDialog(OrderList.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                fromto[0] = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.getTime());
                fromto1[0] = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
                fdt.setText(fromto[0]);
                getOrderData("Dispatch", 1);
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog1.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_NEGATIVE) {
                    datePickerDialog1.dismiss();
                    getFromDate();
                }
            }
        });


        datePickerDialog1.setTitle("From");
        datePickerDialog1.show();

    }

    public void getToDate() {
        calendar1 = Calendar.getInstance();
        datePickerDialog2 = new DatePickerDialog(OrderList.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar1.set(i, i1, i2);
                fromto[1] = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar1.getTime());
                fromto1[1] = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar1.getTime());
                todt.setText(fromto[1]);
                getOrderData("Dispatch", 1);

            }
        }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));

        datePickerDialog2.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_NEGATIVE) {
                    dialogInterface.dismiss();
                    getToDate();
                }
            }
        });

        datePickerDialog2.setTitle("To");
        datePickerDialog2.show();

    }


    public void getOrderData(final String orderType, int id) {
        orderViewlistAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);
        enable = false;
        Call<List<order_list1>> call = apiService.getDispatchOrderData1(dbname, id,partycode,fromto1[0],fromto1[1]);

        call.enqueue(new Callback<List<order_list1>>() {
            @Override
            public void onResponse(Call<List<order_list1>> call, Response<List<order_list1>> response) {
                orders_list = response.body();
                orders_list_copy.clear();
                orders_list_copy.addAll(orders_list);
                progressBar.setVisibility(View.GONE);
                if (orders_list.size() == 0) {
                    util.showToast("DATA NOT FOUND");
                    recyclerView.setAdapter(null);
                } else {
                    enable = true;
                    orderViewlistAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<order_list1>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void getsubdata(String chalno) {
        orderssub_list.clear();
        progressBar.setVisibility(View.VISIBLE);
        Call<List<orders_detail>> call = apiService.getDispatchsubOrderData(dbname, chalno);

        call.enqueue(new Callback<List<orders_detail>>() {
            @Override
            public void onResponse(Call<List<orders_detail>> call, Response<List<orders_detail>> response) {
                orderssub_list = response.body();
                progressBar.setVisibility(View.GONE);
                if (orderssub_list.size() == 0) {
                    util.showToast("DATA NOT FOUND");

                } else {

                    sales_orders_preview1 sop = new sales_orders_preview1(orderssub_list, ordtype);
                    sop.show(getSupportFragmentManager(), sop.getTag());
                }
            }

            @Override
            public void onFailure(Call<List<orders_detail>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }


/*    public void getsearchOrderData(final String orderType, int id, String value) {
        orderViewlistAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);
        enable = false;
        Call<List<order_list1>> call = apiService.getsearchDispatchOrderData(dbname, id, value);

        call.enqueue(new Callback<List<order_list1>>() {
            @Override
            public void onResponse(Call<List<order_list1>> call, Response<List<order_list1>> response) {
                orders_list = response.body();
                orders_list_copy.clear();
                orders_list_copy.addAll(orders_list);
                progressBar.setVisibility(View.GONE);
                if (orders_list.size() == 0) {
                    util.showToast("DATA NOT FOUND");
                    recyclerView.setAdapter(null);
                } else {
                    enable = true;
                    orderViewlistAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<order_list1>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }*/

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        SearchManager searchManager = (SearchManager) OrderList.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(OrderList.this.getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    if (enable)
                        orders_list.clear();
                    orders_list_copy.clear();
                    getsearchOrderData(dbname, 1, query);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    if (enable)
                        orders_list.clear();
                    getsearchOrderData(dbname, 1, newText);
                }
                else{
                    getOrderData("Dispatch", 1);
                }
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                getOrderData("Dispatch", 1);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/
}

