package com.newtech.vplus.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.newtech.vplus.Adapter.Order_Adapter;
import com.newtech.vplus.Adapter.Shade_Adapter;
import com.newtech.vplus.Adapter.getOrder_Adapter;
import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.Model.DispachsubClas;
import com.newtech.vplus.Model.Result_model;
import com.newtech.vplus.Model.addressmodel;
import com.newtech.vplus.Model.getorder_Class;
import com.newtech.vplus.Model.item_order;
import com.newtech.vplus.Model.order_Data;
import com.newtech.vplus.Model.order_inser;
import com.newtech.vplus.Model.shade_Data;
import com.newtech.vplus.Model.shadeclass;
import com.newtech.vplus.R;

import com.newtech.vplus.json.ApiClient;
import com.newtech.vplus.json.ApiInterface;
import com.newtech.vplus.util.Util_u;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ordermenu extends AppCompatActivity {
    Database_Helper ph;
    SQLiteDatabase db;
    private Toolbar toolbar;
    Bundle b, b1 = null;
    private DrawerLayout drawer;
    String ftype, mobileno, shadecart;

    private int ledgerMenu = 0, billMenu = 0;
    //   private TabLayout tabLayout;
    private ViewPager viewPager;

    private Util_u util;
    EditText sed, shade;
    private AlertDialog dialog4, dialog5;
    order_inser S_C_A;
    String sdouble;
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5;
    LinearLayout spf1, spf_notfind1, spf2, spf_notfind2, spf3, spf_notfind3, spf4, spf5, spf_notfind4, spf_notfind5;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;
    private LinearLayoutManager mLayoutManager, mLayoutManager1, mLayoutManager2;
    private EditText searchvalue1, searchvalue2, searchvalue3, searchvalue4, searchvalue5;
    private ApiInterface apiService;
    List<order_Data> OSC = new ArrayList<>();
    Order_Adapter OSA;
    List<shade_Data> Shade_List = new ArrayList<>();
    List<shade_Data> Shade_List1 = new ArrayList<>();
    Shade_Adapter SA;
    Button btn_save;
    String deladdress,selectedUser;
    RecyclerView order_itemlist;
    private List<shadeclass> prolists = new ArrayList<shadeclass>();
    ProgressBar progress;
    String item;
    Spinner deliveryaddress1;
    List<addressmodel> model1 = new ArrayList<>();
    ArrayAdapter<addressmodel> adapter_add1;
    private List<getorder_Class> getorderClasses = new ArrayList<getorder_Class>();
    getOrder_Adapter GOAdpt;
    List<Result_model> R_M=new ArrayList<>();
    TextView sed1;
    String quantity;
    EditText search,tot_quantity,tot_quantity1,tot_quantity2;
    private static final int REQUEST_CODE = 100;
    Button save_order, clear_order;
    double addition,sdo1;
    ordermenu ordermenu;
    item_order pa;
    Dialog dialog1;
    LayoutInflater inflater1;
    String tcode,tcode1,tcode2,p_code,  brcode,salesperson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_text_tabs);
        ph = new Database_Helper(getApplicationContext());
        db = ph.getReadableDatabase();
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        sed = (EditText)findViewById(R.id.sed);
        sed1 = (TextView) findViewById(R.id.sed1);
        shade = (EditText)findViewById(R.id.shade);
        search = (EditText)findViewById(R.id.search);
        save_order = (Button) findViewById(R.id.save_order);
        tot_quantity = (EditText)findViewById(R.id.tot_quantity);
        deliveryaddress1=new Spinner(this);
        deliveryaddress1=(Spinner)findViewById(R.id.deliveryaddress1);
        //getAdress1();
       // tot_quantity1 = (EditText)findViewById(R.id.tot_quantity1);
       // tot_quantity2 = (EditText)findViewById(R.id.tot_quantity2);
        tot_quantity.setFocusable(false);
        clear_order = (Button) findViewById(R.id.clear_order);
        ordermenu = this;
        clear_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEdit();
            }
        });



        mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        order_itemlist = (RecyclerView) findViewById(R.id.order_itemlist);
        order_itemlist.setLayoutManager(mLayoutManager2);
        progress=(ProgressBar)findViewById(R.id.progressBar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        util = new Util_u(ordermenu.this, this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ph = new Database_Helper(getApplicationContext());

        ftype = ph.GetVal("Select FTYPE from andmst where LOGIN='T'");
        mobileno = ph.GetVal("Select MOBILENO From andmst where LOGIN='T'");
        salesperson = ph.GetVal("Select PCODE From andmst where LOGIN='T'");
        sed = (EditText) findViewById(R.id.sed);
        b1 = getIntent().getExtras();
      //  p_code=b1.getString("p_code");
        brcode = "1";

        if (b1 != null) {
            if (b1.getString("p_name") != null) {
                getSupportActionBar().setTitle(b1.getString("p_name").toString());
            }



            LayoutInflater inflater = this.getLayoutInflater();
            final View view5 = inflater.inflate(R.layout.order, null);
            AlertDialog.Builder builder5;
            builder5 = new AlertDialog.Builder(ordermenu.this);
            builder5.setView(view5);
            dialog5 = builder5.create();

            searchvalue5 = (EditText) view5.findViewById(R.id.indexSearch);
            spf5 = (LinearLayout) view5.findViewById(R.id.spf);
            spf_notfind5 = (LinearLayout) view5.findViewById(R.id.spf_notfind);
            progressBar5 = (ProgressBar) view5.findViewById(R.id.progressBar);
            mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
            recyclerView5 = (RecyclerView) view5.findViewById(R.id.partyListRV);
            recyclerView5.setLayoutManager(mLayoutManager1);

            shade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shadeList();
                    dialog5.show();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int displayWidth = displayMetrics.widthPixels;
                    int displayHeight = displayMetrics.heightPixels;
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog5.getWindow().getAttributes());
                    int dialogWindowWidth = (int) (displayWidth * 1f);
                    int dialogWindowHeight = (int) (displayHeight * 0.7f);
                    layoutParams.width = dialogWindowWidth;
                    layoutParams.height = dialogWindowHeight;
                    dialog5.getWindow().setAttributes(layoutParams);
                }
            });


            inflater = this.getLayoutInflater();
            final View view4 = inflater.inflate(R.layout.order, null);
            AlertDialog.Builder builder4;
            builder4 = new AlertDialog.Builder(ordermenu.this);
            builder4.setView(view4);
            dialog4 = builder4.create();

            searchvalue4 = (EditText) view4.findViewById(R.id.indexSearch);
            spf4 = (LinearLayout) view4.findViewById(R.id.spf);
            spf_notfind4 = (LinearLayout) view4.findViewById(R.id.spf_notfind);
            progressBar4 = (ProgressBar) view4.findViewById(R.id.progressBar);
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView4 = (RecyclerView) view4.findViewById(R.id.partyListRV);
            recyclerView4.setLayoutManager(mLayoutManager);


            sed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Iteamlist();
                    dialog4.show();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int displayWidth = displayMetrics.widthPixels;
                    int displayHeight = displayMetrics.heightPixels;
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog4.getWindow().getAttributes());
                    int dialogWindowWidth = (int) (displayWidth * 1f);
                    int dialogWindowHeight = (int) (displayHeight * 0.7f);
                    layoutParams.width = dialogWindowWidth;
                    layoutParams.height = dialogWindowHeight;
                    dialog4.getWindow().setAttributes(layoutParams);
                }
            });


            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = search.getText().toString().toLowerCase(Locale.getDefault());
                    GOAdpt.filter(text);
                  /*  tot_quantity1.setText(tot_quantity.getText().toString());
                    if(search.getText().toString().equals("")){
                        sdo1 = Double.parseDouble(tot_quantity1.getText().toString()) +Double.parseDouble(tot_quantity.getText().toString());
                        tot_quantity1.setText(String.valueOf(sdo1));
                    }

*/


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }


        save_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ordermenu.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("ABOUT SAVE");
                builder.setMessage("Please confirm yes or no");
                builder.setIcon(R.drawable.ic_exit_to_app_primary_blue_24dp);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getorderClasses.size() != 0) {

                            pa = new item_order("", "MEY", "", p_code, brcode, "", salesperson, "", "", "", "", "", deladdress, "", "", "", "", getorderClasses);
                            progress.setVisibility(View.VISIBLE);
                            Call<List<Result_model>> call = apiService.getpartyorder("application/json", "MEY", pa);
                            call.enqueue(new Callback<List<Result_model>>() {
                                @Override
                                public void onResponse(Call<List<Result_model>> call, Response<List<Result_model>> response) {
                                    // item_order p2 = response.body();
                                    // DisplayMsg(p2.getResult());
                                    progress.setVisibility(View.GONE);
                                    //  if (p2.getResult().equals("Success")) {

                                    R_M = response.body();
                                    if (R_M.get(0).getResult().equals("Success")) {


                                        inflater1 = ordermenu.this.getLayoutInflater();
                                        final View view = inflater1.inflate(R.layout.custometext, null);
                                        androidx.appcompat.app.AlertDialog.Builder builder;
                                        builder = new androidx.appcompat.app.AlertDialog.Builder(ordermenu.this);
                                        builder.setView(view);
                                        dialog1 = builder.create();
                                        TextView t1 = (TextView) view.findViewById(R.id.t1);

                                        tcode = R_M.get(1).getNO();
                                        tcode2 = tcode.substring(1, tcode.length() - 2);
                                        dialog1.show();

                                        // tcode1 = tcode2.replace("/", "a");
                                        t1.setText(tcode2);

                                        getorderClasses.clear();
                                        getorderClasses.removeAll(getorderClasses);
                                        GOAdpt.notifyDataSetChanged();
                                /*  sitemname = null;
                                sitemcode = null;
                                sqty = null;
                                rate1 = null;
                                sremark = null;*/
                                    }

                                }
                                @Override
                                public void onFailure(Call<List<Result_model>> call, Throwable t) {
                                    //Log.e(TAG, t.toString());
                                }
                            });
                        } else {
                            Toast.makeText(ordermenu.this, "Invalid Form", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();


            }
        });

    }


    private void getAdress1() {
        Call<List<addressmodel>> call = apiService.getAddress("MEY", b1.getString("p_name"));
        call.enqueue(new Callback<List<addressmodel>>() {
            @Override
            public void onResponse(Call<List<addressmodel>> call, Response<List<addressmodel>> response) {
                if (response.isSuccessful()){
                    for (addressmodel post : response.body()){
                        String name = post.getAddress();
                        String  deladdress1 =post.getPsrl();
                        addressmodel model = new addressmodel(name,deladdress1);
                        model1.add(model);
                        deliveryaddress1.setAdapter(adapter_add1);

                    }
                }


            }

            @Override
            public void onFailure(Call<List<addressmodel>> call, Throwable t) {

            }
        });

        deliveryaddress1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                arg1.setSelected(true);
                Object o = deliveryaddress1.getSelectedItem();
                String strparty = (String) o.toString();

                try {

                    addressmodel i1= model1.get(arg2);

                    selectedUser=i1.getAddress();
                    deladdress=i1.getPsrl();

                } catch (Exception e) {
                    // Toast.makeText(thi, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


    }
    public void countvalue(List<getorder_Class> getorder_classes) {
        addition = 0;
        for (int i = 0;i<getorder_classes.size();i++){
            addition = addition +  getorder_classes.get(i).getQuantity();
        }
        getorderClasses = new ArrayList<>(getorder_classes);
        sdouble = new DecimalFormat("##.##").format(addition);
        tot_quantity.setText(String.valueOf(sdouble));

}

        private void clearEdit () {
            for (int i = 0; i <= getorderClasses.size() - 1; i++) {
                getorderClasses.get(i).setQuantity(Integer.parseInt("0"));
                order_itemlist.setAdapter(GOAdpt);
                tot_quantity.setText("");
                GOAdpt.notifyDataSetChanged();
            }
        }


//This method is called with the button is pressed//

        public void onClick(View v)

//Create an Intent with “RecognizerIntent.ACTION_RECOGNIZE_SPEECH” action//
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            try {

//Start the Activity and wait for the response//

                startActivityForResult(intent, REQUEST_CODE);
            } catch (ActivityNotFoundException a) {

            }

    }


        private void shadeList() {
            Shade_List.clear();
            spf5.setVisibility(View.GONE);
            spf_notfind5.setVisibility(View.GONE);
            progressBar5.setVisibility(View.VISIBLE);
            Call<List<shade_Data>> call = apiService.getShade("MEY");
            call.enqueue(new Callback<List<shade_Data>>() {
                @Override
                public void onResponse(Call<List<shade_Data>> call, Response<List<shade_Data>> response) {
                    try {
                        if (response.code() == 200) {
                            Shade_List = response.body();
                            progressBar5.setVisibility(View.GONE);
                            if (Shade_List.size() == 0) {
                                Toast.makeText(ordermenu.this, "no data found", Toast.LENGTH_SHORT).show();
                            } else {
                                spf_notfind4.setVisibility(View.GONE);

                                SA = new Shade_Adapter(Shade_List, ordermenu.this, new Shade_Adapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick1(shade_Data dpm1) {

                                        dialog5.dismiss();
                                        shade.setText(dpm1.getShade_cardno());
                                        shadecart=dpm1.getShade_cardno();


                                    }
                                });
                                recyclerView5.setAdapter(SA);
                            }

                        } else {

                        }

                    } catch (Exception e) {
                        Toast.makeText(ordermenu.this, "server not working", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<shade_Data>> call, Throwable t) {

                }
            });

        }


        private void Iteamlist() {
            OSC.clear();
            spf4.setVisibility(View.GONE);
            spf_notfind4.setVisibility(View.GONE);
            progressBar4.setVisibility(View.VISIBLE);
            Call<List<order_Data>> call = apiService.getOrder("MEY",shadecart);
            call.enqueue(new Callback<List<order_Data>>() {
                @Override
                public void onResponse(Call<List<order_Data>> call, Response<List<order_Data>> response) {
                    try {
                        if (response.code() == 200) {
                            OSC = response.body();
                            progressBar4.setVisibility(View.GONE);
                            if (OSC.size() == 0) {
                                Toast.makeText(ordermenu.this, "no data found", Toast.LENGTH_SHORT).show();
                            } else {
                                spf_notfind4.setVisibility(View.GONE);

                                OSA = new Order_Adapter(OSC, ordermenu.this, new Order_Adapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(order_Data dpm1) {

                                        dialog4.dismiss();
                                        sed.setText(dpm1.getI_name1());
                                        item = dpm1.getI_code();
                                        getList(item);
                                    }
                                });
                                recyclerView4.setAdapter(OSA);

                            }

                        } else {

                        }

                    } catch (Exception e) {
                        Toast.makeText(ordermenu.this, "server not working", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<order_Data>> call, Throwable t) {

                }
            });

        }

        private void getList (String item){

            getorderClasses.clear();

            Call<List<getorder_Class>> call = apiService.getshadeList("MEY", item);
            call.enqueue(new Callback<List<getorder_Class>>() {
                @Override
                public void onResponse(Call<List<getorder_Class>> call, Response<List<getorder_Class>> response) {
                    try {
                        if (response.code() == 200) {
                            getorderClasses = response.body();
                            if (getorderClasses.size() == 0) {
                                Toast.makeText(ordermenu.this, "no data found", Toast.LENGTH_SHORT).show();
                            } else {
                                GOAdpt = new getOrder_Adapter(getorderClasses, ordermenu.this,new getOrder_Adapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick2(getorder_Class dpm1) {

                                        quantity = String.valueOf(dpm1.getQuantity());
                                        //sed1.setText(quantity);

                                    }
                                });

                        /*    getorder_Class getorderClasses1 = new getorder_Class();
                            getorderClasses.add(0,getorderClasses1);

                            GOAdpt.notifyItemInserted(0);
                            order_itemlist.smoothScrollToPosition(0);*/

                                order_itemlist.setAdapter(GOAdpt);
                                GOAdpt.notifyDataSetChanged();
                                //
                            }

                        } else {
                        }

                    } catch (Exception e) {
                        Toast.makeText(ordermenu.this, "server not working", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<getorder_Class>> call, Throwable t) {

                }
            });
        }


        protected void onActivityResult (int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case REQUEST_CODE: {

                    if (resultCode == RESULT_OK && null != data) {
                        ArrayList<String> result1 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        search.setText(result1.get(0));

                    }
                    break;
                }

            }
        }

}
