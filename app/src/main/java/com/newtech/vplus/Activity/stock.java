package com.newtech.vplus.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.newtech.vplus.Adapter.Book_Adapter;
import com.newtech.vplus.Adapter.Company_Adapter;
import com.newtech.vplus.Adapter.stock_adapter;
import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.Model.BookClass;
import com.newtech.vplus.Model.CompanylistClass;
import com.newtech.vplus.Model.PackingClass;
import com.newtech.vplus.Model.Result_model;
import com.newtech.vplus.Model.StockClass;
import com.newtech.vplus.Model.stock_model;
import com.newtech.vplus.R;
import com.newtech.vplus.json.ApiClient;
import com.newtech.vplus.json.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class stock extends AppCompatActivity  {

    ImageView sc,sc1;
    EditText bookselection, costselection, compselection,sed,Rackno,carttonno,carttonno1;
    TextView carttonno2;
    private EditText searchvalue1, searchvalue2, searchvalue3, searchvalue4, value,value1,value2;
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    private LinearLayoutManager mLayoutManager, mLayoutManager1;
    LinearLayout spf1, spf_notfind1, spf2, spf_notfind2, spf3, spf_notfind3, spf4, spf_notfind4;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    Spinner grade, type;
    TextView totcar;
    private Database_Helper ph;
    Button butn;
    private SQLiteDatabase db;
    Book_Adapter B_A;
    String barcodeno,barcodeno1;
    List<CompanylistClass> CLC = new ArrayList<>();
    private ApiInterface apiService;
    Company_Adapter C_A;
    StockClass BCC;
    List<StockClass> PackC = new ArrayList<>();
    List<BookClass> BC = new ArrayList<>();
    String companyid, and_compid, and_costid,copanyname,Bookname,bookcode,and_sno;
    Button SAVE;
    private AlertDialog dialog1, dialog2, dialog3, dialog4, dialog6;
    ProgressBar pro;
    int mode = 0;
    Handler handler;
    String dong;
    Runnable runnable;
    RecyclerView recycle_scan;
    ArrayList<stock_model> models = new ArrayList<stock_model>();
    ArrayList<stock_model> models1 = new ArrayList<stock_model>();
    stock_adapter stock_adapter1;
    LayoutInflater inflater1,inflater2;
    List<Result_model> R_M=new ArrayList<>();
    private androidx.appcompat.app.AlertDialog dialog;
    int position;
    Boolean isDone=false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock);
        sc = (ImageView) findViewById(R.id.sc);
        sc1 = (ImageView) findViewById(R.id.sc1);
        pro = (ProgressBar) findViewById(R.id.pro);
        compselection = (EditText) findViewById(R.id.compselection);
        bookselection = (EditText) findViewById(R.id.bookselection);
        Rackno = (EditText) findViewById(R.id.Rackno);
        carttonno = (EditText) findViewById(R.id.carttonno);
        carttonno1 = (EditText) findViewById(R.id.carttonno1);

        totcar = (TextView) findViewById(R.id.totcar);

        type = (Spinner) findViewById(R.id.type);
        ph = new Database_Helper(getApplicationContext());
        db = ph.getReadableDatabase();
        apiService = ApiClient.getClient().create(ApiInterface.class);

        recycle_scan = (RecyclerView) findViewById(R.id.recycle_scan);

        recycle_scan.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycle_scan.setLayoutManager(layoutManager);

        stock_adapter1 = new stock_adapter(getApplicationContext(), models,
                new stock_adapter.Onclick() {
                  @Override
                    public void onEvent(stock_model model, int pos) {
                       // position = pos;
                       // carttonno1.setText(model.getName());
                    }
                });
        recycle_scan.setAdapter(stock_adapter1);


        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {

                    case R.id.sc:
                        mode = 1;

                        final int MY_CAMERA_REQUEST_CODE = 100;
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(stock.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                } else {
                    IntentIntegrator integrator = new IntentIntegrator(stock.this);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Scan");
                    integrator.setCameraId(0);
                    integrator.setOrientationLocked(true);
                    integrator.setCaptureActivity(CaptureActivityPortrait.class);
                    integrator.setBeepEnabled(false);
                    integrator.setBarcodeImageEnabled(true);
                    integrator.initiateScan();
                }
                        break;
                }
            }
        });

        sc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {

                    case R.id.sc1:
                        mode = 2;
                        handler =new Handler();
                        handler.postDelayed(runnable =new Runnable() {
                            public void run() {


                                final int MY_CAMERA_REQUEST_CODE = 100;
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(stock.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                } else {
                    IntentIntegrator integrator = new IntentIntegrator(stock.this);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Scan");
                    integrator.setCameraId(0);
                    integrator.setOrientationLocked(true);
                    integrator.setCaptureActivity(CaptureActivityPortrait.class);
                    integrator.setBeepEnabled(false);
                    integrator.setBarcodeImageEnabled(true);
                    integrator.initiateScan();
                }
                                handler.postDelayed(runnable, 3000);
                            }
                        },3000);
                break;
            }
            }
        });

        Rackno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Rackno.setText("");
                return false;
                }
        });

/*

        int noOfSecond = 1;
        handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                butn.performClick();
            }
        },noOfSecond*1000);
*/



        carttonno1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carttonno1.getText().toString().equals("")){
                    carttonno1.requestFocus();
                }else {
                   // carttonno1.setText("");
                    barcodeno = carttonno1.getText().toString();
                    postbarcode1(carttonno1.getText().toString());
                }
            }
        });



  /*
      carttonno1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

               *//*   if(Rackno.getText().toString().equals("")){
                      Toast.makeText(stock.this, "First selsect Rack No", Toast.LENGTH_SHORT).show();
                }
                else*//*
              if(carttonno1.getText().toString().equals("")){
                    carttonno1.requestFocus();
                }else {
                   carttonno1.setText("");
                    //carttonno1.requestFocus();
                    barcodeno = carttonno.getText().toString();
                    barcodeno1 = barcodeno.replace("\n", " ");
                    // getpackingboxdata(String.valueOf(s));
                    postbarcode1(String.valueOf(s));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        int noOfSecond = 1;



        Rackno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Rackno.getText().toString().equals("")){
                    Rackno.requestFocus();
                }else {
                    models.clear();
                    totcar.setText("");
                    models.clear();
                    carttonno1.requestFocus();
                    stock_adapter1.notifyDataSetChanged();
                }
            }
        });


    /*   Rackno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                models.clear();
                totcar.setText("");
                models.clear();
                carttonno1.requestFocus();
                stock_adapter1.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position == 0) {
                    and_sno = "A";
                }

                if (position == 1) {
                    and_sno = "B";
                }

               if (position == 2){
                    and_sno = "C";
                }

                    if (position == 3){
                    and_sno = "D";
                }


              /*   if (position == 4){
                    and_sno = "E";
                }
                if (position == 5){
                    and_sno = "F";
                }
                if (position == 6){
                    and_sno = "G";
                }
                if (position == 7){
                    and_sno = "H";
                }
                if (position == 8){
                    and_sno = "I";
                }*/
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        LayoutInflater inflater = this.getLayoutInflater();
        final View view1 = inflater.inflate(R.layout.activity_party_finder, null);
        AlertDialog.Builder builder1;
        builder1 = new AlertDialog.Builder(stock.this);
        builder1.setView(view1);
        dialog1 = builder1.create();

        searchvalue1 = (EditText) view1.findViewById(R.id.indexSearch);
        spf1 = (LinearLayout) view1.findViewById(R.id.spf);
        spf_notfind1 = (LinearLayout) view1.findViewById(R.id.spf_notfind);
        progressBar1 = (ProgressBar) view1.findViewById(R.id.progressBar);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView1 = (RecyclerView) view1.findViewById(R.id.partyListRV);
        recyclerView1.setLayoutManager(mLayoutManager);

        compselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compselectionlist();
                bookselection.setText("");
                dialog1.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int displayWidth = displayMetrics.widthPixels;
                int displayHeight = displayMetrics.heightPixels;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog1.getWindow().getAttributes());
                int dialogWindowWidth = (int) (displayWidth * 1f);
                int dialogWindowHeight = (int) (displayHeight * 0.7f);
                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;
                dialog1.getWindow().setAttributes(layoutParams);
            }
        });

        searchvalue1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = searchvalue1.getText().toString().toLowerCase(Locale.getDefault());
                C_A.filter(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final View view3 = inflater.inflate(R.layout.activity_party_finder, null);
        AlertDialog.Builder builder3;
        builder3 = new AlertDialog.Builder(stock.this);
        builder3.setView(view3);
        dialog3 = builder3.create();

        searchvalue3 = (EditText) view3.findViewById(R.id.indexSearch);
        spf3 = (LinearLayout) view3.findViewById(R.id.spf);
        spf_notfind3 = (LinearLayout) view3.findViewById(R.id.spf_notfind);
        progressBar3 = (ProgressBar) view3.findViewById(R.id.progressBar);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView3 = (RecyclerView) view3.findViewById(R.id.partyListRV);
        recyclerView3.setLayoutManager(mLayoutManager);


        bookselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booklist();
                dialog3.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int displayWidth = displayMetrics.widthPixels;
                int displayHeight = displayMetrics.heightPixels;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog3.getWindow().getAttributes());
                int dialogWindowWidth = (int) (displayWidth * 1f);
                int dialogWindowHeight = (int) (displayHeight * 0.7f);
                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;
                dialog3.getWindow().setAttributes(layoutParams);
            }
        });

        searchvalue3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = searchvalue3.getText().toString().toLowerCase(Locale.getDefault());
                B_A.filter(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void code() {
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(mode == 1) {


            if(compselection.getText().toString().equals("")){
                Toast.makeText(this, "First select Company", Toast.LENGTH_SHORT).show();
            }
            else if(bookselection.getText().toString().equals("")){
                Toast.makeText(this, "Select Book", Toast.LENGTH_SHORT).show();
            }
            else {
                if (result != null) {
                    if (result.getContents() == null) {
                        Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                        String rac = result.getContents();
                        Rackno.setText(rac);
                       // carttonno1.requestFocus();

                    }
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }

        }
        if (mode == 2){
                        if (result != null) {
                            if (result.getContents() == null) {
                                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                                handler.removeCallbacksAndMessages(null);
                            } else {
                                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                                String car = result.getContents();
                                carttonno.setText(car);
                                //postbarcode(result.getContents());
                            }
                        } else {
                            super.onActivityResult(requestCode, resultCode, data);
                        }
           }
    }

    private void booklist() {
        BC.clear();
        spf3.setVisibility(View.GONE);
        spf_notfind3.setVisibility(View.GONE);
        progressBar3.setVisibility(View.VISIBLE);
        Call<List<BookClass>> call = apiService.getbooklist("MEY", "null", copanyname);
        call.enqueue(new Callback<List<BookClass>>() {
            @Override
            public void onResponse(Call<List<BookClass>> call, Response<List<BookClass>> response) {
                try {
                    if (response.code() == 200) {
                        BC = response.body();
                        progressBar3.setVisibility(View.GONE);
                        if (BC.size() == 0) {
                            Toast.makeText(stock.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                        } else {
                            spf_notfind3.setVisibility(View.GONE);
                            B_A = new Book_Adapter(BC, stock.this, new Book_Adapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BookClass dpm1) {
                                    bookselection.setText(dpm1.getBookname());
                                    Bookname = dpm1.getBookname();
                                    bookcode = dpm1.getBookcode();
                                    dialog3.dismiss();
                                    Rackno.requestFocus();
                                }
                            });
                            recyclerView3.setAdapter(B_A);
                        }
                    } else {

                    }
                } catch (Exception e) {
                    Toast.makeText(stock.this, "Server not Working ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BookClass>> call, Throwable t) {

            }
        });
    }


    private void compselectionlist() {
        CLC.clear();
        spf1.setVisibility(View.GONE);
        spf_notfind1.setVisibility(View.GONE);
        progressBar1.setVisibility(View.VISIBLE);
        Call<List<CompanylistClass>> call = apiService.getclist("MEY");
        call.enqueue(new Callback<List<CompanylistClass>>() {
            @Override
            public void onResponse(Call<List<CompanylistClass>> call, Response<List<CompanylistClass>> response) {
                try {
                    if (response.code() == 200) {
                        CLC = response.body();
                        progressBar1.setVisibility(View.GONE);
                        if (CLC.size() == 0) {
                            Toast.makeText(stock.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                        } else {
                            spf_notfind1.setVisibility(View.GONE);
                            C_A = new Company_Adapter(CLC, stock.this, new Company_Adapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(CompanylistClass dpm1) {
                                    compselection.setText(dpm1.getBY_NAME());
                                    copanyname = dpm1.getBY_NAME();
                                    companyid = dpm1.getBY_CODE();
                                    dialog1.dismiss();
                                }
                            });
                            recyclerView1.setAdapter(C_A);

                        }
                    } else {

                    }
                } catch (Exception e) {
                    Toast.makeText(stock.this, "Server not Working ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CompanylistClass>> call, Throwable t) {

            }
        });
    }

    private void postbarcode1(final String barcodeno) {
        pro.setVisibility(View.VISIBLE);
        StockClass bc = new StockClass(barcodeno, companyid,bookcode,Rackno.getText().toString(),and_sno,"MEY","");
        Call<StockClass> call = apiService.postbarcodedata("application/json", bc);
        call.enqueue(new Callback<StockClass>() {
            @Override
            public void onResponse(Call<StockClass> call, Response<StockClass> response) {
                try {
                    if (response.code() == 200) {
                        pro.setVisibility(View.GONE);
                          BCC = response.body();
                       // R_M = response.body();

                        if (BCC.getResult().equals("Sucess")) {

                            Toast.makeText(stock.this, barcodeno, Toast.LENGTH_SHORT).show();
                            String aaa= carttonno1.getText().toString();
                            insertMethod(barcodeno);
                            Toast.makeText(stock.this, "SAVE SUCESSFULLY", Toast.LENGTH_SHORT).show();
                            carttonno1.setText("");
                            totcar.setText(String.valueOf(models.size()));

                        } else
                            {
                                carttonno1.setText("");
                            inflater1 =stock.this.getLayoutInflater();
                            final View view = inflater1.inflate(R.layout.custometext1, null);
                            androidx.appcompat.app.AlertDialog.Builder builder;
                            builder = new androidx.appcompat.app.AlertDialog.Builder(stock.this);
                            builder.setView(view);
                            dialog = builder.create();
                            TextView t1=(TextView)view.findViewById(R.id.t12);
                            String  aa= BCC.getResult();
                            String  strNew = aa.replace("a", " ");
                            String  strNew1 = strNew.replace("'", " ");
                            t1.setText("This Box-no is not in Database = " + strNew1);
                            dialog.show();

                            //Toast.makeText(stock.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(stock.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }
            @Override
            public void onFailure(Call<StockClass> call, Throwable t) {

            }
        });
    }

    private void insertMethod(String name) {
     /*   Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            stock_model model = gson.fromJson(String.valueOf(jsonObject), stock_model.class);
            models.add(model);
            stock_adapter1.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        stock_model model = new stock_model(name);
            models.add(model);
            stock_adapter1.notifyDataSetChanged();

        for(int i=0;i<models.size();i++){

            for(int j=i+1;j<models.size();j++){
                if(models.get(i).getName().equals(models.get(j).getName())){
                    models.remove(j);
                    j--;
                }
            }

        }



    }


   /* private void postbarcode(String barcodeno) {
        pro.setVisibility(View.VISIBLE);
        StockClass bc = new StockClass(barcodeno, companyid,bookcode,Rackno.getText().toString(),and_sno,"MEY","");
        Call<StockClass> call = apiService.postbarcodedata("application/json", bc);
        call.enqueue(new Callback<StockClass>() {
            @Override
            public void onResponse(Call<StockClass> call, Response<StockClass> response) {
                try {
                    if (response.code() == 200) {
                        pro.setVisibility(View.GONE);
                        BCC = response.body();
                        if (BCC.getResult().equals("Success")) {
                            String aaa = BCC.getResult();
                            Toast.makeText(stock.this, "SAVE SUCESSFULLY", Toast.LENGTH_SHORT).show();
                            carttonno.setText("");
                            SharedPreferences countSettings = getSharedPreferences("aaa", 0);


                        } else {
                            Toast.makeText(stock.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(stock.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }
            @Override
            public void onFailure(Call<StockClass> call, Throwable t) {

            }
        });
    }*/
}
