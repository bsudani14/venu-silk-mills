package com.venusilkmills.app.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.renderscript.Sampler;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.venusilkmills.app.Adapter.Book_Adapter;
import com.venusilkmills.app.Adapter.Box_data_adapter;
import com.venusilkmills.app.Adapter.Company_Adapter;
import com.venusilkmills.app.Adapter.Khata_adapter;
import com.venusilkmills.app.Adapter.OrderSch_adapter;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Model.BookClass;
import com.venusilkmills.app.Model.CompanylistClass;
import com.venusilkmills.app.Model.Dispachclass;
import com.venusilkmills.app.Model.DispachsubClas;
import com.venusilkmills.app.Model.DispachsubClas1;
import com.venusilkmills.app.Model.Khataclass;
import com.venusilkmills.app.Model.OrderschClass;
import com.venusilkmills.app.Model.PackingClass;
import com.venusilkmills.app.Model.Result_model;
import com.venusilkmills.app.Model.Stockpermission;
import com.venusilkmills.app.Model.orderno;
import com.venusilkmills.app.Model.tempcass;
import com.venusilkmills.app.Model.tempcass1;
import com.venusilkmills.app.R;
import com.venusilkmills.app.json.ApiClient;
import com.venusilkmills.app.json.ApiInterface;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Dispatch_activity extends AppCompatActivity {
    EditText bookselection, costselection, compselection, sed, boxno, pa, Rackmachin;
    private EditText searchvalue1, searchvalue2, searchvalue3, searchvalue4, value, value1, value2, value4;
    LinearLayout spf1, spf_notfind1, spf2, spf_notfind2, spf3, spf_notfind3, spf4, spf_notfind4;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    private LinearLayoutManager mLayoutManager, mLayoutManager1, mLayoutManager3;
    private AlertDialog dialog1;
    private AlertDialog dialog2;
    private AlertDialog dialog3;
    private AlertDialog dialog4;
    private AlertDialog dialog6;
    private androidx.appcompat.app.AlertDialog dialog8;
    private androidx.appcompat.app.AlertDialog dialog;
    private Database_Helper ph;
    private SQLiteDatabase db;
    private ApiInterface apiService;
    Company_Adapter C_A;
    Khata_adapter K_A;
    Book_Adapter B_A;
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    OrderSch_adapter OSA;
    List<Result_model> R_M = new ArrayList<>();
    //  String dbname;
    List<CompanylistClass> CLC = new ArrayList<>();
    List<Khataclass> Kc = new ArrayList<>();
    List<BookClass> BC = new ArrayList<>();
    List<OrderschClass> OSC = new ArrayList<>();
    ArrayList<OrderschClass> OSC1 = new ArrayList<OrderschClass>();
    ArrayList<OrderschClass> OSC2 = new ArrayList<OrderschClass>();
    List<PackingClass> Pc = new ArrayList<>();
    List<PackingClass> PackC = new ArrayList<>();
    List<DispachsubClas> Disclass = new ArrayList<>();
    List<DispachsubClas1> Disclass1 = new ArrayList<>();
    List<DispachsubClas1> Disclass2 = new ArrayList<>();
    Dispachclass finalclass;
    String costcentername, copanyname, costcode, Bookname;
    RecyclerView l1, rec1;
    TextView qty, pname, date, ordno, actqty, dis, pqty, companyname, txtlotno, txtrano, txtboxno, lotno, shade, itemname, shadenew;
    ImageView scan, rackno;
    String and_boxno, barcodeno, barcodeno1, and_grade, and_wt, rackno1, and_itemcode, and_lotno, and_itemname, mat_iname, mat_grad, mat_lot, ans_schcode, ans_schparty, and_qty, and_brcode, and_netwt;
    Box_data_adapter BAA;
    LayoutInflater inflater1, inflater2;
    Button sav, pri, scan1;
    Stockpermission result;
    String aa, bb, cc;
    String dong, dong1, and_Cops, Shade, itcode, penqty, rac;
    String shade1;
    int qty1;
    Double asss = 0.000;
    Double bsss = 0.000;
    CardView cd1;
    ImageView addbtn;
    ImageView btn;
    Button manbtn;
    String srno, sc;
    RelativeLayout rl;
    EditText maned;
    orderno BCC;
    boolean automaticChanged = false;
    ProgressBar pro;
    Handler handler;
    Runnable runnable;
    String companyid, and_compid, and_costid;
    int mode = 0;
    CheckBox butta_cutting;
    TextView total_quantity;
    double addition;
    Dispatch_activity dispatch_activity;
    String buttatype;
    List<tempcass> tc = new ArrayList<>();
    int a = 0;
    int b = 0;
    int c = 0;
    int d = 0;
    int c1;
    String naa;
    List<tempcass1> tc1 = new ArrayList<>();
    ArrayList<tempcass1> arrlist = new ArrayList<tempcass1>();
    int count;
    String bb11;

    public void onBackPressed() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(Dispatch_activity.this);
        localBuilder.setTitle("Alert Message");
        localBuilder.setMessage("Sure You want to Exit?");

        localBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

                Dispatch_activity.this.finish();

                //finish();
            }
        });

        localBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

            }
        });
        AlertDialog dialog = localBuilder.create();
        dialog.show();
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#033f55"));
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#033f55"));
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_dispatch);
        bookselection = (EditText) findViewById(R.id.bookselection);
        costselection = (EditText) findViewById(R.id.costselection);
        compselection = (EditText) findViewById(R.id.compselection);
        boxno = (EditText) findViewById(R.id.boxno);
        boxno.requestFocus();
        qty = (TextView) findViewById(R.id.qty);
        scan = (ImageView) findViewById(R.id.scan);
        addbtn = (ImageView) findViewById(R.id.addbtn);
        btn = (ImageView) findViewById(R.id.btn);

        butta_cutting = (CheckBox) findViewById(R.id.butta_cutting);

        if (butta_cutting.isChecked() == true) {

            buttatype = "1";
        }

        butta_cutting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (butta_cutting.isChecked() == true) {

                    buttatype = "1";
                } else {

                    buttatype = "0";
                }
            }
        });

        pname = (TextView) findViewById(R.id.pname);
        itemname = (TextView) findViewById(R.id.itemname);
        date = (TextView) findViewById(R.id.date);
        ordno = (TextView) findViewById(R.id.ordno);
        dis = (TextView) findViewById(R.id.dis);
        dis.setVisibility(View.VISIBLE);
        sed = (EditText) findViewById(R.id.sed);
        Rackmachin = (EditText) findViewById(R.id.Rackmachin);
        pa = (EditText) findViewById(R.id.pa);

        rec1 = (RecyclerView) findViewById(R.id.rec1);
        sav = (Button) findViewById(R.id.sav);
        cd1 = (CardView) findViewById(R.id.cd1);
        pri = (Button) findViewById(R.id.pri);
        value = (EditText) findViewById(R.id.value);
        value1 = (EditText) findViewById(R.id.value1);
        value4 = (EditText) findViewById(R.id.value4);
        value2 = (EditText) findViewById(R.id.value2);
        scan1 = (Button) findViewById(R.id.scan1);
        pro = (ProgressBar) findViewById(R.id.pro);
        actqty = (TextView) findViewById(R.id.actqty);
        shade = (TextView) findViewById(R.id.shade);
        //shadenew = (TextView) findViewById(R.id.shade1);
        pqty = (TextView) findViewById(R.id.pqty);
        companyname = (TextView) findViewById(R.id.companyname);
        // grd = (TextView) findViewById(R.id.grd);
        rackno = (ImageView) findViewById(R.id.rackno);
        //txtgrd = (TextView) findViewById(R.id.txtgrd);
        txtlotno = (TextView) findViewById(R.id.txtlotno);
        txtrano = (TextView) findViewById(R.id.txtrano);
        txtboxno = (TextView) findViewById(R.id.txtboxno);
        lotno = (TextView) findViewById(R.id.lotno);
        pri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cd1.setVisibility(View.VISIBLE);
                pri.setVisibility(View.GONE);
                sed.setVisibility(View.GONE);
                companyname.setVisibility(View.GONE);
                lotno.setVisibility(View.GONE);
                dis.setVisibility(View.VISIBLE);
                txtlotno.setVisibility(View.GONE);
                txtboxno.setVisibility(View.GONE);
                txtrano.setVisibility(View.GONE);
                //txtgrd.setVisibility(View.GONE);
                //grd.setVisibility(View.GONE);
                rackno.setVisibility(View.GONE);
                Rackmachin.setVisibility(View.GONE);
                boxno.setVisibility(View.GONE);

            }
        });
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder23 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder23.build());

//Get Edittext value from Adapter class
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));


        boxno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boxno.getText().toString().equals("")) {
                    boxno.requestFocus();
                } else {
                    butta_cutting.setVisibility(View.VISIBLE);
                    //   boxno.requestFocus();
                    // barcodeno = boxno.getText().toString();
                    //boxno.setText("");
                    barcodeno = boxno.getText().toString();
                    barcodeno1 = barcodeno.replace("\n", " ");
                    // getpackingboxdata(String.valueOf(s));
                    cc = String.valueOf(barcodeno1);
                    getpackingboxdata();

                }
            }
        });

/*
        boxno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (boxno.getText().toString().equals("")) {
                    boxno.requestFocus();
                } else {
                    butta_cutting.setVisibility(View.VISIBLE);
                    //   boxno.requestFocus();
                    // barcodeno = boxno.getText().toString();
                    boxno.setText("");
                    barcodeno = String.valueOf(s);
                    barcodeno1 = barcodeno.replace("\n", " ");
                    // getpackingboxdata(String.valueOf(s));
                    cc = String.valueOf(barcodeno1);
                    getpackingboxdata();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/


        pa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cd1.setVisibility(View.VISIBLE);
                pri.setVisibility(View.GONE);
                sed.setVisibility(View.GONE);
                dis.setVisibility(View.VISIBLE);
                companyname.setVisibility(View.GONE);
                Rackmachin.setVisibility(View.GONE);
                Rackmachin.requestFocus();
                //  txtgrd.setVisibility(View.VISIBLE);
                txtlotno.setVisibility(View.GONE);
                txtboxno.setVisibility(View.GONE);
                txtrano.setVisibility(View.GONE);
                lotno.setVisibility(View.GONE);
                //grd.setVisibility(View.VISIBLE);
                rackno.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        barcodeno = boxno.getText().toString();
                        barcodeno1 = barcodeno.replace("\n", " ");
                        // getpackingboxdata(String.valueOf(s));
                        cd1.setVisibility(View.GONE);
                        pri.setVisibility(View.VISIBLE);
                        sed.setVisibility(View.VISIBLE);
                        dis.setVisibility(View.GONE);
                        companyname.setVisibility(View.VISIBLE);
                        Rackmachin.setVisibility(View.VISIBLE);
                        Rackmachin.requestFocus();
                        //  txtgrd.setVisibility(View.VISIBLE);
                        txtlotno.setVisibility(View.VISIBLE);
                        txtboxno.setVisibility(View.VISIBLE);
                        txtrano.setVisibility(View.VISIBLE);
                        lotno.setVisibility(View.VISIBLE);
                        //grd.setVisibility(View.VISIBLE);
                        rackno.setVisibility(View.VISIBLE);
                        aa = String.valueOf(s);
                        //postorder();

                    }
                }, 6000);
            }
        });


        Rackmachin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Rackmachin.requestFocus();
                barcodeno = boxno.getText().toString();
                barcodeno1 = barcodeno.replace("\n", " ");
                // getpackingboxdata(String.valueOf(s));
                bb = String.valueOf(s);
                OSC2.clear();
                Disclass1.clear();
                secdulelist1();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        rackno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boxno.setVisibility(View.VISIBLE);
                boxno.requestFocus();
                int id = view.getId();
                if (id == R.id.rackno) {
                        mode = 2;

                        final int MY_CAMERA_REQUEST_CODE = 100;
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(Dispatch_activity.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                        } else {
                            IntentIntegrator integrator = new IntentIntegrator(Dispatch_activity.this);
                            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                            integrator.setPrompt("Scan");
                            integrator.setCameraId(0);
                            integrator.setOrientationLocked(true);
                            integrator.setCaptureActivity(CaptureActivityPortrait.class);
                            integrator.setBeepEnabled(false);
                            integrator.setBarcodeImageEnabled(true);
                            integrator.initiateScan();
                        }
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = view.getId();
                if (id == R.id.btn) {
                        mode = 1;

                        cd1.setVisibility(View.GONE);
                        pri.setVisibility(View.VISIBLE);
                        sed.setVisibility(View.VISIBLE);
                        dis.setVisibility(View.GONE);
                        companyname.setVisibility(View.VISIBLE);
                        Rackmachin.setVisibility(View.VISIBLE);
                        Rackmachin.requestFocus();
                        //txtgrd.setVisibility(View.VISIBLE);
                        txtlotno.setVisibility(View.VISIBLE);
                        txtrano.setVisibility(View.VISIBLE);
                        txtboxno.setVisibility(View.VISIBLE);
                        lotno.setVisibility(View.VISIBLE);
                        //grd.setVisibility(View.VISIBLE);
                        rackno.setVisibility(View.VISIBLE);

                        final int MY_CAMERA_REQUEST_CODE = 100;
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(Dispatch_activity.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                        } else {
                            IntentIntegrator integrator = new IntentIntegrator(Dispatch_activity.this);
                            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                            integrator.setPrompt("Scan");
                            integrator.setCameraId(0);
                            integrator.setOrientationLocked(true);
                            integrator.setCaptureActivity(CaptureActivityPortrait.class);
                            integrator.setBeepEnabled(false);
                            integrator.setBarcodeImageEnabled(true);
                            integrator.initiateScan();
                        }
                }

            }
        });

        LayoutInflater inflater = this.getLayoutInflater();
        final View view6 = inflater.inflate(R.layout.add_manual_barcode, null);
        AlertDialog.Builder builder6;
        builder6 = new AlertDialog.Builder(Dispatch_activity.this);
        builder6.setView(view6);
        dialog6 = builder6.create();
        manbtn = (Button) view6.findViewById(R.id.btnman);
        maned = (EditText) view6.findViewById(R.id.edman);
        manbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog6.dismiss();
                cc = maned.getText().toString();
                getpackingboxdata();

            }
        });


        qrcode();

        scan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maned.setText("");
                dialog6.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int displayWidth = displayMetrics.widthPixels;
                int displayHeight = displayMetrics.heightPixels;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog6.getWindow().getAttributes());
                int dialogWindowWidth = (int) (displayWidth * 1f);
                int dialogWindowHeight = (int) (displayHeight * 0.5f);
                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;
                dialog6.getWindow().setAttributes(layoutParams);
            }
        });

        mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        rec1.setLayoutManager(mLayoutManager1);

        ph = new Database_Helper(getApplicationContext());
        db = ph.getReadableDatabase();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");

        sav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(Dispatch_activity.this);
                builder.setTitle("Exit")
                        .setMessage("Are you sure you want to Confirm?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finalclass = new Dispachclass(copanyname, Bookname, costcentername, costcode, ans_schcode, ans_schparty, and_qty, and_brcode, "MEY", Disclass);
                                if (Disclass1.size() > qty1) {

                                    inflater2 = Dispatch_activity.this.getLayoutInflater();
                                    final View view = inflater2.inflate(R.layout.custometext, null);
                                    androidx.appcompat.app.AlertDialog.Builder builder;
                                    builder = new androidx.appcompat.app.AlertDialog.Builder(Dispatch_activity.this);
                                    builder.setView(view);
                                    dialog8 = builder.create();
                                    TextView t1 = (TextView) view.findViewById(R.id.t1);
                                    t1.setText("This " + Shade + " is Max qty");
                                    dialog8.show();

                                } else {
                                    postdata();
                                }
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                }).setIcon(R.drawable.ic_home).create().show();
            }
        });

        Rackmachin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Rackmachin.setText("");
                Rackmachin.requestFocus();
                return false;
            }
        });

        final View view1 = inflater.inflate(R.layout.activity_party_finder, null);
        AlertDialog.Builder builder1;
        builder1 = new AlertDialog.Builder(Dispatch_activity.this);
        builder1.setView(view1);
        dialog1 = builder1.create();

        searchvalue1 = (EditText) view1.findViewById(R.id.indexSearch);
        spf1 = (LinearLayout) view1.findViewById(R.id.spf);
        spf_notfind1 = (LinearLayout) view1.findViewById(R.id.spf_notfind);
        progressBar1 = (ProgressBar) view1.findViewById(R.id.progressBar);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView1 = (RecyclerView) view1.findViewById(R.id.partyListRV);
        recyclerView1.setLayoutManager(mLayoutManager);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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


        final View view2 = inflater.inflate(R.layout.activity_party_finder, null);
        AlertDialog.Builder builder2;
        builder2 = new AlertDialog.Builder(Dispatch_activity.this);
        builder2.setView(view2);
        dialog2 = builder2.create();

        searchvalue2 = (EditText) view2.findViewById(R.id.indexSearch);
        spf2 = (LinearLayout) view2.findViewById(R.id.spf);
        spf_notfind2 = (LinearLayout) view2.findViewById(R.id.spf_notfind);
        progressBar2 = (ProgressBar) view2.findViewById(R.id.progressBar);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView2 = (RecyclerView) view2.findViewById(R.id.partyListRV);
        recyclerView2.setLayoutManager(mLayoutManager);


        costselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                costcenterlist();
                bookselection.setText("");
                dialog2.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int displayWidth = displayMetrics.widthPixels;
                int displayHeight = displayMetrics.heightPixels;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog2.getWindow().getAttributes());
                int dialogWindowWidth = (int) (displayWidth * 1f);
                int dialogWindowHeight = (int) (displayHeight * 0.7f);
                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;
                dialog2.getWindow().setAttributes(layoutParams);
            }
        });

        searchvalue2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = searchvalue2.getText().toString().toLowerCase(Locale.getDefault());
                K_A.filter(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        final View view3 = inflater.inflate(R.layout.activity_party_finder, null);
        AlertDialog.Builder builder3;
        builder3 = new AlertDialog.Builder(Dispatch_activity.this);
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

        final View view4 = inflater.inflate(R.layout.activity_party_finder1, null);
        AlertDialog.Builder builder4;
        builder4 = new AlertDialog.Builder(Dispatch_activity.this);
        builder4.setView(view4);
        dialog4 = builder4.create();

        searchvalue4 = (EditText) view4.findViewById(R.id.indexSearch);
        spf4 = (LinearLayout) view4.findViewById(R.id.spf);
        total_quantity = (TextView) view4.findViewById(R.id.total_quantity);

        spf_notfind4 = (LinearLayout) view4.findViewById(R.id.spf_notfind);
        progressBar4 = (ProgressBar) view4.findViewById(R.id.progressBar);
        mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        recyclerView4 = (RecyclerView) view4.findViewById(R.id.partyListRV);
        recyclerView4.setLayoutManager(mLayoutManager3);


        sed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secdulelist();
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

        searchvalue4.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = searchvalue4.getText().toString().toLowerCase(Locale.getDefault());
                OSA.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            cc = intent.getStringExtra("quantity");
            boxno.setText(cc);
            //cc=boxno.getText().toString();
             getpackingboxdata();
        }
    };


    private void qrcode() {

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = view.getId();
                if (id == R.id.addbtn) {

                        mode = 3;

                        Intent intent = new Intent(getApplicationContext(), qrscan_activity.class);
                        startActivity(intent);
                }
            }
        });
    }


    private void postdata() {
        pro.setVisibility(View.VISIBLE);
        Call<List<Result_model>> call = apiService.postdispach("application/json", finalclass);
        call.enqueue(new Callback<List<Result_model>>() {
            @Override
            public void onResponse(Call<List<Result_model>> call, Response<List<Result_model>> response) {
                try {

                    if (response.code() == 200) {
                        R_M = response.body();
                        pro.setVisibility(View.GONE);
                        if (R_M.get(0).getResult().equals("Success")) {
                            //    Toast.makeText(Dispatch_activity.this, result.getResult(), Toast.LENGTH_SHORT).show();

                            inflater1 = Dispatch_activity.this.getLayoutInflater();
                            final View view = inflater1.inflate(R.layout.custometext, null);
                            androidx.appcompat.app.AlertDialog.Builder builder;
                            builder = new androidx.appcompat.app.AlertDialog.Builder(Dispatch_activity.this);
                            builder.setView(view);
                            dialog = builder.create();
                            TextView t1 = (TextView) view.findViewById(R.id.t1);
                            String aa = R_M.get(1).getNO();
                            String strNew = aa.replace("a", " ");
                            String strNew1 = strNew.replace("'", " ");
                            t1.setText(strNew1);
                            dialog.show();
                            tc.clear();
                            PackC.clear();
                            Disclass.clear();
                            BAA.notifyDataSetChanged();
                          /*  bookselection.setText("");
                            costselection.setText("");
                            compselection.setText("");*/
                            Rackmachin.setText("");
                            pname.setText("");
                            itemname.setText("");
                            ordno.setText("");
                            shade.setText("");
                            qty.setText("");
                            actqty.setText("");
                            pqty.setText("");
                            value2.setText("");
                            boxno.setText("");
                            pa.setText("");

                            // pname.setText("");
                            // itemname.setText("");
                            date.setText("");
                            // ordno.setText("");
                            lotno.setText("");
                            // grd.setText("");

                        } else {
                            Toast.makeText(Dispatch_activity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pro.setVisibility(View.GONE);
                        Toast.makeText(Dispatch_activity.this, "error occure", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    pro.setVisibility(View.GONE);
                    Toast.makeText(Dispatch_activity.this, "server not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Result_model>> call, Throwable t) {

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
                            Toast.makeText(Dispatch_activity.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                        } else {
                            spf_notfind1.setVisibility(View.GONE);
                            C_A = new Company_Adapter(CLC, Dispatch_activity.this, new Company_Adapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(CompanylistClass dpm1) {
                                    compselection.setText(dpm1.getBY_NAME());
                                    copanyname = dpm1.getBY_NAME();
                                    companyid = dpm1.getBY_CODE();
                                    companyname.setText(copanyname);
                                    dialog1.dismiss();
                                }
                            });
                            recyclerView1.setAdapter(C_A);

                        }
                    } else {

                    }
                } catch (Exception e) {
                    Toast.makeText(Dispatch_activity.this, "Server not Working ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CompanylistClass>> call, Throwable t) {

            }
        });
    }


    private void costcenterlist() {
        Kc.clear();
        spf2.setVisibility(View.GONE);
        spf_notfind2.setVisibility(View.GONE);
        progressBar2.setVisibility(View.VISIBLE);
        Call<List<Khataclass>> call = apiService.getcost("MEY", "ADMIN", "");
        call.enqueue(new Callback<List<Khataclass>>() {
            @Override
            public void onResponse(Call<List<Khataclass>> call, Response<List<Khataclass>> response) {
                try {
                    if (response.code() == 200) {
                        Kc = response.body();
                        progressBar2.setVisibility(View.GONE);
                        if (Kc.size() == 0) {
                            Toast.makeText(Dispatch_activity.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                        } else {
                            spf_notfind2.setVisibility(View.GONE);
                            K_A = new Khata_adapter(Kc, Dispatch_activity.this, new Khata_adapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Khataclass dpm1) {
                                    costselection.setText(dpm1.getKh_name());
                                    costcentername = dpm1.getKh_name();
                                    costcode = dpm1.getKh_code();
                                    dialog2.dismiss();
                                }
                            });
                            recyclerView2.setAdapter(K_A);
                        }
                    } else {

                    }
                } catch (Exception e) {
                    Toast.makeText(Dispatch_activity.this, "Server not Working ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Khataclass>> call, Throwable t) {
            }
        });
    }
    private void booklist() {
        BC.clear();
        spf3.setVisibility(View.GONE);
        spf_notfind3.setVisibility(View.GONE);
        progressBar3.setVisibility(View.VISIBLE);
        Call<List<BookClass>> call = apiService.getbooklist("MEY", "EMBRO", copanyname);
        call.enqueue(new Callback<List<BookClass>>() {
            @Override
            public void onResponse(Call<List<BookClass>> call, Response<List<BookClass>> response) {
                try {
                    if (response.code() == 200) {
                        BC = response.body();
                        progressBar3.setVisibility(View.GONE);
                        if (BC.size() == 0) {
                            Toast.makeText(Dispatch_activity.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                        } else {
                            spf_notfind3.setVisibility(View.GONE);
                            B_A = new Book_Adapter(BC, Dispatch_activity.this, new Book_Adapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BookClass dpm1) {
                                    bookselection.setText(dpm1.getBookname());
                                    Bookname = dpm1.getBookname();
                                    dialog3.dismiss();
                                    pa.setVisibility(View.VISIBLE);
                                    pa.requestFocus();
                                }
                            });
                            recyclerView3.setAdapter(B_A);
                        }
                    } else {

                    }
                } catch (Exception e) {
                    Toast.makeText(Dispatch_activity.this, "Server not Working ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<BookClass>> call, Throwable t) {

            }
        });
    }

    private void secdulelist() {

        total_quantity.setText("");
        OSC.clear();
        spf4.setVisibility(View.GONE);
        spf_notfind4.setVisibility(View.GONE);
        progressBar4.setVisibility(View.VISIBLE);
        Call<List<OrderschClass>> call = apiService.getordersch("MEY", companyid, aa, "null");
        call.enqueue(new Callback<List<OrderschClass>>() {
            @Override
            public void onResponse(Call<List<OrderschClass>> call, Response<List<OrderschClass>> response) {
                try {
                    if (response.code() == 200) {
                        OSC = response.body();
                        progressBar4.setVisibility(View.GONE);
                        if (OSC.size() == 0) {
                            Toast.makeText(Dispatch_activity.this, "no data found", Toast.LENGTH_SHORT).show();
                        } else {
                            spf_notfind4.setVisibility(View.GONE);
                            if (tc.size() == 0) {
                            } else {


                                arrlist.add(new tempcass1(String.valueOf(c1), srno));
                                for (int i = 0; i < tc.size(); i++) {
                                    //  tc.get(i).setDisno(String.valueOf(Disclass1.size()));
                                    for (int j = 0; j < OSC.size(); j++) {

                                        for (int k = 0; k < arrlist.size(); k++) {

                                            if (tc.get(i).getSrono().equals(OSC.get(j).getSrno()) && arrlist.get(k).getLotno().equals(OSC.get(j).getSrno()) && arrlist.get(k).getDeso().equals(OSC.get(j).getOsm_qty())) {
                                                //tc.get(i).getDisno().equals(OSC.get(j).getOsm_qty());
                                                OSC.get(j).setDisqty(arrlist.get(k).getDeso());
                                                //  OSC.get(j).setDisqty(tc.get(i).getDisno());
                                                OSC.get(j).setCol(true);
                                            }
                                            if (tc.get(i).getSrono().equals(OSC.get(j).getSrno()) && arrlist.get(k).getLotno().equals(OSC.get(j).getSrno()) && !(arrlist.get(k).getDeso().equals(OSC.get(j).getOsm_qty()))) {

                                             /*   a= Integer.parseInt(value.getText().toString());
                                                b= Integer.parseInt(value1.getText().toString());
                                                int c = a + b;
                                                value4.setText(String.valueOf(c));*/


                                                OSC.get(j).setDisqty(arrlist.get(k).getDeso());
                                                OSC.get(j).setCol(false);

                                            }
                                        }
                                    }
                                }
                            }

                            OSA = new OrderSch_adapter(OSC, tc, Dispatch_activity.this, Dispatch_activity.this, new OrderSch_adapter.OnItemClickListener() {
                                @Override
                                public boolean onItemClick(OrderschClass dpm1) {

                                    tc.add(new tempcass(dpm1.srno, dpm1.disqty));


                                    // for(int i=0; i<tc.size(); i++){
                                    /*    for(int j=0; j<OSC.size(); j++){
                                            if(tc.get(i).getSrono().equals(OSC.get(j).srno)){
                                               *//*if(OSC.get(j).isCol()){
                                                   OSC.get(j).setCol(false);
                                                   OSA.notifyDataSetChanged();
                                               }else{
                                                   OSC.get(j).setCol(true);
                                                   OSA.notifyDataSetChanged();
                                               }*//*
                                                OSC.get(j).setCol(true);
                                                OSA.notifyDataSetChanged();

                                            }
                                        }
                                    }*/

                                    Disclass1.clear();
                                    c1 =0;
                                    qty.setText(dpm1.getOsm_TOTQTY());
                                    pname.setText(dpm1.getOsm_delparty());
                                    itemname.setText(dpm1.getOsm_itname());
                                    date.setText(dpm1.getOsm_date());
                                    ordno.setText(dpm1.getOsm_ordno());
                                    actqty.setText(dpm1.getOsm_SALQTY());
                                    shade.setText(dpm1.getShade());
                                    //Rackmachin.setText(dpm1.getRackno());
                                    sed.setText(dpm1.getRackno());
                                    rac = dpm1.getRackno();
                                    Shade = dpm1.getShade();
                                    penqty = dpm1.getOsm_qty();
                                    qty1 = Integer.parseInt(dpm1.getOsm_qty());
                                    itcode = dpm1.getOsm_itcode();
                                    pqty.setText(dpm1.getOsm_qty());
                                    //grd.setText(OSC1.get(i).getOsm_grade());
                                    ans_schcode = dpm1.getOsm_code();
                                    ans_schparty = dpm1.getOsm_delparty();
                                    and_qty = dpm1.getOsm_qty();
                                    and_brcode = dpm1.getOsm_brname();
                                    mat_iname = dpm1.getOsm_itname();
                                    mat_grad = dpm1.getOsm_grade();
                                    mat_lot = dpm1.getOsm_lotno();
                                    srno = dpm1.getSrno();
                                    sc = dpm1.getDisqty();



                                    if (sc != null  && !sc.equals("null")){
                                        value.setText(sc);
                                    }
                                    else{
                                        value.setText("0");
                                    }

                                    lotno.setText(mat_lot);
                                    dialog4.dismiss();
                                    boxno.setVisibility(View.VISIBLE);
                                    boxno.requestFocus();

                                    return true;
                                }
                            });
                            recyclerView4.setAdapter(OSA);
                            recyclerView4.setHasFixedSize(true);
                            //OSA.notifyDataSetChanged();
                        }

                    } else {

                    }

                } catch (Exception e) {
                    Toast.makeText(Dispatch_activity.this, "server not working", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<OrderschClass>> call, Throwable t) {

            }
        });
    }

    private void secdulelist1() {
        total_quantity.setText("");
        OSC.clear();
        spf4.setVisibility(View.GONE);
        spf_notfind4.setVisibility(View.GONE);
        progressBar4.setVisibility(View.VISIBLE);
        Call<List<OrderschClass>> call = apiService.getordersch("MEY", companyid, aa, bb);
        call.enqueue(new Callback<List<OrderschClass>>() {
            @Override
            public void onResponse(Call<List<OrderschClass>> call, Response<List<OrderschClass>> response) {
                try {
                    if (response.code() == 200) {
                        OSC = response.body();
                        OSC2.clear();
                        OSC2.addAll(OSC);

                        /*
                        for (int j = 0; j < OSC1.size(); j++) {
                            if(OSC1.get(j).getRackno().equals(bb)) {
                                String aa=OSC1.get(j).getRackno();
                                OrderschClass PAC = new OrderschClass(OSC1.get(j).getOsm_code(),OSC1.get(j).getOsm_delparty(),OSC1.get(j).getOsm_qty(),OSC1.get(j).getOsm_brname(),OSC1.get(j).getOsm_lotno(),OSC1.get(j).getOsm_grade(),OSC1.get(j).getOsm_itname(),OSC1.get(j).getOsm_itcode(),OSC1.get(j).getStkqty(),OSC1.get(j).getRackno(),OSC1.get(j).getOsm_ordno(),OSC1.get(j).getOsm_SALQTY(),OSC1.get(j).getOsm_TOTQTY(),OSC1.get(j).getShade());
                                OSC2.add(PAC);
                            }
                        }*/

                        //  progressBar4.setVisibility(View.GONE);
                        if (OSC2.size() == 0) {
                            Toast.makeText(Dispatch_activity.this, "no data found", Toast.LENGTH_SHORT).show();
                        } else {
/*                            if (tc.size() == 0) {
                            } else {


                                arrlist.add(new tempcass1(String.valueOf(c1), srno));
                                for (int i = 0; i < tc.size(); i++) {
                                    //  tc.get(i).setDisno(String.valueOf(Disclass1.size()));
                                    for (int j = 0; j < OSC.size(); j++) {

                                        for (int k = 0; k < arrlist.size(); k++) {

                                            if (tc.get(i).getSrono().equals(OSC.get(j).getSrno()) && arrlist.get(k).getLotno().equals(OSC.get(j).getSrno()) && arrlist.get(k).getDeso().equals(OSC.get(j).getOsm_qty())) {
                                                //tc.get(i).getDisno().equals(OSC.get(j).getOsm_qty());
                                                OSC.get(j).setDisqty(arrlist.get(k).getDeso());
                                                //  OSC.get(j).setDisqty(tc.get(i).getDisno());
                                                OSC.get(j).setCol(true);
                                            }
                                            if (tc.get(i).getSrono().equals(OSC.get(j).getSrno()) && arrlist.get(k).getLotno().equals(OSC.get(j).getSrno()) && !(arrlist.get(k).getDeso().equals(OSC.get(j).getOsm_qty()))) {

                                             *//*   a= Integer.parseInt(value.getText().toString());
                                                b= Integer.parseInt(value1.getText().toString());
                                                int c = a + b;
                                                value4.setText(String.valueOf(c));*//*


                                                OSC.get(j).setDisqty(arrlist.get(k).getDeso());
                                                OSC.get(j).setCol(false);

                                            }
                                        }
                                    }
                                }
                            }*/
                            if (OSC2.size() == 1) {
                                tc.add(new tempcass(OSC2.get(0).getSrno(), OSC2.get(0).getDisqty()));
                                Disclass1.clear();
                                c1 =0;
                                qty.setText(OSC2.get(0).getOsm_TOTQTY());
                                pname.setText(OSC2.get(0).getOsm_delparty());
                                itemname.setText(OSC2.get(0).getOsm_itname());
                                date.setText(OSC2.get(0).getOsm_date());
                                ordno.setText(OSC2.get(0).getOsm_ordno());
                                actqty.setText(OSC2.get(0).getOsm_SALQTY());
                                shade.setText(OSC2.get(0).getShade());
                                rac = OSC2.get(0).getRackno();
                                Shade = OSC2.get(0).getShade();
                                penqty = OSC2.get(0).getOsm_qty();
                                qty1 = Integer.parseInt(OSC2.get(0).getOsm_qty());
                                itcode = OSC2.get(0).getOsm_itcode();
                                pqty.setText(OSC2.get(0).getOsm_qty());
                                ans_schcode = OSC2.get(0).getOsm_code();
                                ans_schparty = OSC2.get(0).getOsm_delparty();
                                and_qty = OSC2.get(0).getOsm_qty();
                                and_brcode = OSC2.get(0).getOsm_brname();
                                mat_iname = OSC2.get(0).getOsm_itname();
                                mat_grad = OSC2.get(0).getOsm_grade();
                                mat_lot = OSC2.get(0).getOsm_lotno();
                                lotno.setText(mat_lot);
                                srno = OSC2.get(0).getSrno();
                                sc = OSC2.get(0).getDisqty();



                                if (sc != null  && !sc.equals("null")){
                                    value.setText(sc);
                                }
                                else{
                                    value.setText("0");
                                }

                                boxno.setVisibility(View.VISIBLE);
                                boxno.requestFocus();
                                // Toast.makeText(Dispatch_activity.this, OSC2.size(), Toast.LENGTH_SHORT).show();
                            } else {
                                spf_notfind4.setVisibility(View.GONE);
                                progressBar4.setVisibility(View.GONE);
                                dialog4.show();
                              if (tc.size() == 0) {
                                } else {


                                    arrlist.add(new tempcass1(String.valueOf(c1), srno));
                                    for (int i = 0; i < tc.size(); i++) {
                                        //  tc.get(i).setDisno(String.valueOf(Disclass1.size()));
                                        for (int j = 0; j < OSC.size(); j++) {

                                            for (int k = 0; k < arrlist.size(); k++) {

                                                if (tc.get(i).getSrono().equals(OSC.get(j).getSrno()) && arrlist.get(k).getLotno().equals(OSC.get(j).getSrno()) && arrlist.get(k).getDeso().equals(OSC.get(j).getOsm_qty())) {
                                                    //tc.get(i).getDisno().equals(OSC.get(j).getOsm_qty());
                                                    OSC.get(j).setDisqty(arrlist.get(k).getDeso());
                                                    //  OSC.get(j).setDisqty(tc.get(i).getDisno());
                                                    OSC.get(j).setCol(true);
                                                }
                                                if (tc.get(i).getSrono().equals(OSC.get(j).getSrno()) && arrlist.get(k).getLotno().equals(OSC.get(j).getSrno()) && !(arrlist.get(k).getDeso().equals(OSC.get(j).getOsm_qty()))) {

                                            /*   a= Integer.parseInt(value.getText().toString());
                                                b= Integer.parseInt(value1.getText().toString());
                                                int c = a + b;
                                                value4.setText(String.valueOf(c));*/


                                                    OSC.get(j).setDisqty(arrlist.get(k).getDeso());
                                                    OSC.get(j).setCol(false);

                                                }
                                            }
                                        }
                                    }
                                }
                                OSA = new OrderSch_adapter(OSC2, tc, Dispatch_activity.this, Dispatch_activity.this, new OrderSch_adapter.OnItemClickListener() {
                                    @Override
                                    public boolean onItemClick(OrderschClass dpm1) {
                                        tc.add(new tempcass(dpm1.srno, dpm1.disqty));
                                        Disclass1.clear();
                                        c1 =0;
                                        qty.setText(dpm1.getOsm_TOTQTY());
                                        pname.setText(dpm1.getOsm_delparty());
                                        itemname.setText(dpm1.getOsm_itname());
                                        date.setText(dpm1.getOsm_date());
                                        ordno.setText(dpm1.getOsm_ordno());
                                        actqty.setText(dpm1.getOsm_SALQTY());
                                        shade.setText(dpm1.getShade());

                                        //  Rackmachin.setText(dpm1.getRackno());
                                        Shade = dpm1.getShade();
                                        penqty = dpm1.getOsm_qty();
                                        qty1 = Integer.parseInt(dpm1.getOsm_qty());
                                        itcode = dpm1.getOsm_itcode();
                                        pqty.setText(dpm1.getOsm_qty());
                                        rac = dpm1.getRackno();
                                        //grd.setText(OSC1.get(i).getOsm_grade());
                                        ans_schcode = dpm1.getOsm_code();
                                        ans_schparty = dpm1.getOsm_delparty();
                                        and_qty = dpm1.getOsm_qty();
                                        and_brcode = dpm1.getOsm_brname();
                                        mat_iname = dpm1.getOsm_itname();
                                        mat_grad = dpm1.getOsm_grade();
                                        mat_lot = dpm1.getOsm_lotno();
                                        lotno.setText(mat_lot);
                                        srno = dpm1.getSrno();
                                        sc = dpm1.getDisqty();

                                        if (sc != null  && !sc.equals("null")){
                                            value.setText(sc);
                                        }
                                        else{
                                            value.setText("0");
                                        }


                                        dialog4.dismiss();
                                        boxno.setVisibility(View.VISIBLE);
                                        boxno.requestFocus();
                                        return true;
                                    }
                                });
                                recyclerView4.setAdapter(OSA);
                               // recyclerView4.setHasFixedSize(true);
                            }
                        }
                    } else {

                    }

                } catch (Exception e) {
                    // Toast.makeText(Dispatch_activity.this, "server not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderschClass>> call, Throwable t) {

            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (mode == 1) {
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                    aa = result.getContents();
                    ordno.setText(aa);
                    //postorder();
                    // secdulelist1();
                    // getpackingboxdata(result.getContents(),"");

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
        if (mode == 2) {
            rackno.setVisibility(View.VISIBLE);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                    Rackmachin.setText(result.getContents());
                    bb = result.getContents();
                    Rackmachin.setText(bb);
                    // OSC2.clear();
                    // secdulelist1();
                    //aa=result.getContents();

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

        if (mode == 3) {
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                    // handler.removeCallbacksAndMessages(null);
                } else {
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                    boxno.setText(result.getContents());
                   /* Intent intent = getIntent();
                    String str = intent.getStringExtra("scan");*/
                    cc = boxno.getText().toString();
                   // getpackingboxdata();
                    qrcode();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }


    private void getpackingboxdata() {
        // pro.setVisibility(View.VISIBLE);
        Call<List<PackingClass>> call = apiService.getboxlist("MEY", companyid, cc);
        call.enqueue(new Callback<List<PackingClass>>() {
            @Override
            public void onResponse(Call<List<PackingClass>> call, Response<List<PackingClass>> response) {
                try {
                    if (response.code() == 200) {
                        // pro.setVisibility(View.GONE);
                        Pc = response.body();
                        if (Pc.size() == 0) {
                            //PackC.addAll(Pc);
                            maned.setText("");
                            Toast.makeText(Dispatch_activity.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                        } else {

                            if (buttatype.equals("1")) {
                                for (int j = 0; j < Pc.size(); j++) {

                                    and_boxno = Pc.get(j).getPk_boxno();
                                    and_grade = Pc.get(j).getPk_grade();
                                    and_itemname = Pc.get(j).getPk_shade();
                                    and_itemcode = Pc.get(j).getPk_itemcode();
                                    and_lotno = Pc.get(j).getPk_lotno();

                                    if (!(Pc.get(j).getPk_lotno().equals(mat_lot))) {
                                        inflater2 = Dispatch_activity.this.getLayoutInflater();
                                        final View view = inflater2.inflate(R.layout.custometext, null);
                                        androidx.appcompat.app.AlertDialog.Builder builder;
                                        builder = new androidx.appcompat.app.AlertDialog.Builder(Dispatch_activity.this);
                                        builder.setView(view);
                                        dialog8 = builder.create();
                                        TextView t1 = (TextView) view.findViewById(R.id.t1);
                                        t1.setText("Diffrent Lot NO");
                                        dialog8.show();
                                        Toast.makeText(Dispatch_activity.this, "Diffrent Lot NO", Toast.LENGTH_SHORT).show();

                                    } else if (!(Pc.get(j).getPk_rackno().equals(rac))) {
                                        inflater2 = Dispatch_activity.this.getLayoutInflater();
                                        final View view = inflater2.inflate(R.layout.custometext, null);
                                        androidx.appcompat.app.AlertDialog.Builder builder;
                                        builder = new androidx.appcompat.app.AlertDialog.Builder(Dispatch_activity.this);
                                        builder.setView(view);
                                        dialog8 = builder.create();
                                        TextView t1 = (TextView) view.findViewById(R.id.t1);
                                        t1.setText("Diffrent Rack NO");
                                        dialog8.show();
                                        Toast.makeText(Dispatch_activity.this, "Diffrent Rack NO", Toast.LENGTH_SHORT).show();
                                    } else if (Pc.get(j).getPk_shade().equals(Shade) && Pc.get(j).getPk_itemcode().equals(itcode) && Pc.get(j).getPk_lotno().equals(mat_lot) && Pc.get(j).getPk_rackno().equals(rac)) {

                                        if (Disclass1.size() >= qty1 || c1 ==qty1) {

                                            inflater2 = Dispatch_activity.this.getLayoutInflater();
                                            final View view = inflater2.inflate(R.layout.custometext, null);
                                            androidx.appcompat.app.AlertDialog.Builder builder;
                                            builder = new androidx.appcompat.app.AlertDialog.Builder(Dispatch_activity.this);
                                            builder.setView(view);
                                            dialog8 = builder.create();
                                            TextView t1 = (TextView) view.findViewById(R.id.t1);
                                            t1.setText("This " + Shade + " is Max qty");
                                            dialog8.show();
                                            break;


                                        } else {

                                            if (PackC.size() == 0) {

                                                PackingClass PAC = new PackingClass(and_boxno, and_itemname, and_itemcode, and_grade, and_lotno);
                                                PackC.add(0, PAC);
                                                DispachsubClas dscc = new DispachsubClas(Pc.get(j).getPk_boxno(), Pc.get(j).getPk_shade(), Pc.get(j).getPk_grade(), Pc.get(j).getPk_lotno());
                                                Disclass.add(0, dscc);
                                                dong = "";
                                                DispachsubClas1 dscc11 = new DispachsubClas1(and_boxno, Pc.get(j).getPk_shade(), Pc.get(j).getPk_grade(), Pc.get(j).getPk_lotno());
                                                Disclass1.add(dscc11);

                                           /* if (Disclass1.size() > qty1) {

                                                inflater2 = Dispatch_activity.this.getLayoutInflater();
                                                final View view = inflater2.inflate(R.layout.custometext, null);
                                                androidx.appcompat.app.AlertDialog.Builder builder;
                                                builder = new androidx.appcompat.app.AlertDialog.Builder(Dispatch_activity.this);
                                                builder.setView(view);
                                                dialog8 = builder.create();
                                                TextView t1 = (TextView) view.findViewById(R.id.t1);
                                                t1.setText("This " + Shade + " is Max qty");
                                                dialog8.show();


                                            }*/

                                            } else {
                                                for (int i = 0; i < PackC.size(); i++) {
                                                    if (PackC.get(i).getPk_boxno().equals(and_boxno)) {
                                                        dong = "match";
                                                        break;
                                                    }
                                                }
                                                if (dong.equals("match")) {
                                                    Toast.makeText(Dispatch_activity.this, "same boxno", Toast.LENGTH_SHORT).show();
                                                    dong = "";
                                                } else {
                                                    asss = 0.000;
                                                    bsss = 0.000;
                                                    PackingClass PAC = new PackingClass(and_boxno, and_itemname, and_itemcode, and_grade, and_lotno);
                                                    PackC.add(0, PAC);
                                                    DispachsubClas dscc = new DispachsubClas(and_boxno, Pc.get(j).getPk_shade(), Pc.get(j).getPk_grade(), Pc.get(j).getPk_lotno());


                                                    Disclass.add(0, dscc);
                                                    dong = "";
                                                    DispachsubClas1 dscc11 = new DispachsubClas1(and_boxno, Pc.get(j).getPk_shade(), Pc.get(j).getPk_grade(), Pc.get(j).getPk_lotno());
                                                    Disclass1.add(dscc11);

                                               /* if (Disclass1.size() > qty1) {

                                                    inflater2 = Dispatch_activity.this.getLayoutInflater();
                                                    final View view = inflater2.inflate(R.layout.custometext, null);
                                                    androidx.appcompat.app.AlertDialog.Builder builder;
                                                    builder = new androidx.appcompat.app.AlertDialog.Builder(Dispatch_activity.this);
                                                    builder.setView(view);
                                                    dialog8 = builder.create();
                                                    TextView t1 = (TextView) view.findViewById(R.id.t1);
                                                    t1.setText("This " + Shade + " is Max qty");
                                                    dialog8.show();

                                                }*/


                                                }
                                            }
                                        }

                                    }


                                    value2.setText(String.valueOf(Disclass.size()));
                                    value1.setText(String.valueOf(Disclass1.size()));

                                    c= Integer.parseInt(String.valueOf(Disclass1.size()));
                                    d= Integer.parseInt(value.getText().toString());
                                    c1 = c + d;
                                    value4.setText(String.valueOf(c1));


                                }

                                BAA = new Box_data_adapter(PackC, "si", new Box_data_adapter.OnLotClickListener() {
                                    @Override
                                    public void onItemClick(PackingClass stockListModel) {

                                    }

                                    @Override
                                    public void onItemdelete(PackingClass stockListModel, int pos) {

                                        value2.setText(String.valueOf(Disclass.size() - 1));
                                        value1.setText(String.valueOf(Disclass1.size() - 1));
                                        Log.e("dcd", String.valueOf(PackC.size()));
                                        Log.e("dcdd", String.valueOf(Disclass.size()));
                                        PackC.remove(pos);
                                        Disclass.remove(pos);
                                        Disclass1.remove(pos);
                                        c= Integer.parseInt(String.valueOf(Disclass1.size()));
                                        d= Integer.parseInt(value.getText().toString());
                                        c1 = c + d;
                                        value4.setText(String.valueOf(c));

                                        BAA.notifyDataSetChanged();

                                    }
                                });
                                BAA.notifyItemInserted(0);
                                rec1.smoothScrollToPosition(0);
                                rec1.setAdapter(BAA);

                                boxno.setText("");

                                //  boxno.requestFocus();
                            } else if (buttatype.equals("0")) {
                                for (int j = 0; j < Pc.size(); j++) {
                                    and_boxno = Pc.get(j).getPk_boxno();
                                    and_grade = Pc.get(j).getPk_grade();
                                    and_itemname = Pc.get(j).getPk_shade();
                                    and_itemcode = Pc.get(j).getPk_itemcode();
                                    and_lotno = Pc.get(j).getPk_lotno();

                                    if (Pc.get(j).getPk_shade().equals(Shade) && Pc.get(j).getPk_itemcode().equals(itcode)) {
                                        if (Disclass1.size() >= qty1 || c1 ==qty1) {
                                            inflater2 = Dispatch_activity.this.getLayoutInflater();
                                            final View view = inflater2.inflate(R.layout.custometext, null);
                                            androidx.appcompat.app.AlertDialog.Builder builder;
                                            builder = new androidx.appcompat.app.AlertDialog.Builder(Dispatch_activity.this);
                                            builder.setView(view);
                                            dialog8 = builder.create();
                                            TextView t1 = (TextView) view.findViewById(R.id.t1);
                                            t1.setText("This " + Shade + " is Max qty");
                                            dialog8.show();
                                            break;
                                        } else {

                                            if (PackC.size() == 0) {

                                                PackingClass PAC = new PackingClass(and_boxno, and_itemname, and_itemcode, and_grade, and_lotno);
                                                PackC.add(0, PAC);
                                                DispachsubClas dscc = new DispachsubClas(Pc.get(j).getPk_boxno(), Pc.get(j).getPk_shade(), Pc.get(j).getPk_grade(), Pc.get(j).getPk_lotno());
                                                Disclass.add(0, dscc);

                                                DispachsubClas1 dscc11 = new DispachsubClas1(and_boxno, Pc.get(j).getPk_shade(), Pc.get(j).getPk_grade(), Pc.get(j).getPk_lotno());
                                                Disclass1.add(dscc11);

                                                dong = "";
/*
                                                if (Disclass1.size() > qty1) {

                                                    inflater2 = Dispatch_activity.this.getLayoutInflater();
                                                    final View view = inflater2.inflate(R.layout.custometext, null);
                                                    androidx.appcompat.app.AlertDialog.Builder builder;
                                                    builder = new androidx.appcompat.app.AlertDialog.Builder(Dispatch_activity.this);
                                                    builder.setView(view);
                                                    dialog8 = builder.create();
                                                    TextView t1 = (TextView) view.findViewById(R.id.t1);
                                                    t1.setText("This " + Shade + " is Max qty");
                                                    dialog8.show();

                                                }*/
                                                // qrcode();

                                            } else {
                                                for (int i = 0; i < PackC.size(); i++) {
                                                    if (PackC.get(i).getPk_boxno().equals(and_boxno)) {
                                                        dong = "match";
                                                        break;
                                                    }
                                                }
                                                if (dong.equals("match")) {
                                                    Toast.makeText(Dispatch_activity.this, "same boxno", Toast.LENGTH_SHORT).show();
                                                    dong = "";
                                                } else {
                                                    asss = 0.000;
                                                    bsss = 0.000;
                                                    PackingClass PAC = new PackingClass(and_boxno, and_itemname, and_itemcode, and_grade, and_lotno);
                                                    PackC.add(0, PAC);
                                                    DispachsubClas dscc = new DispachsubClas(and_boxno, Pc.get(j).getPk_shade(), Pc.get(j).getPk_grade(), Pc.get(j).getPk_lotno());
                                                    Disclass.add(0, dscc);
                                                    DispachsubClas1 dscc11 = new DispachsubClas1(and_boxno, Pc.get(j).getPk_shade(), Pc.get(j).getPk_grade(), Pc.get(j).getPk_lotno());
                                                    Disclass1.add(dscc11);

                                                    dong = "";
                                                   /* if (Disclass1.size() > qty1) {

                                                        inflater2 = Dispatch_activity.this.getLayoutInflater();
                                                        final View view = inflater2.inflate(R.layout.custometext, null);
                                                        androidx.appcompat.app.AlertDialog.Builder builder;
                                                        builder = new androidx.appcompat.app.AlertDialog.Builder(Dispatch_activity.this);
                                                        builder.setView(view);
                                                        dialog8 = builder.create();
                                                        TextView t1 = (TextView) view.findViewById(R.id.t1);
                                                        t1.setText("This " + Shade + " is Max qty");
                                                        dialog8.show();

                                                    }
*/
                                                }
                                            }
                                        }
                                    }
                                    value2.setText(String.valueOf(Disclass.size()));
                                    value1.setText(String.valueOf(Disclass1.size()));
                                    c= Integer.parseInt(String.valueOf(Disclass1.size()));
                                    d= Integer.parseInt(value.getText().toString());
                                    c1 = c + d;
                                    value4.setText(String.valueOf(c1));

                                }

                                BAA = new Box_data_adapter(PackC, "si", new Box_data_adapter.OnLotClickListener() {
                                    @Override
                                    public void onItemClick(PackingClass stockListModel) {

                                    }

                                    @Override
                                    public void onItemdelete(PackingClass stockListModel, int pos) {

                                        value2.setText(String.valueOf(Disclass.size() - 1));
                                        value1.setText(String.valueOf(Disclass1.size() - 1));
                                        Log.e("dcd", String.valueOf(PackC.size()));
                                        Log.e("dcdd", String.valueOf(Disclass.size()));

                                        PackC.remove(pos);
                                        Disclass.remove(pos);
                                        Disclass1.remove(pos);
                                        c= Integer.parseInt(String.valueOf(Disclass1.size()));
                                        d= Integer.parseInt(value.getText().toString());
                                        c1 = c + d;
                                        value4.setText(String.valueOf(c));
                                        BAA.notifyDataSetChanged();

                                    }
                                });


                                BAA.notifyItemInserted(0);
                                rec1.smoothScrollToPosition(0);
                                rec1.setAdapter(BAA);
                                boxno.setText("");
                                //  boxno.requestFocus();
                            }

                        }

                    } else {
                        pro.setVisibility(View.GONE);
                        Toast.makeText(Dispatch_activity.this, "error Occure", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    pro.setVisibility(View.GONE);
                    Toast.makeText(Dispatch_activity.this, "server not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PackingClass>> call, Throwable t) {

            }
        });
    }


    public void countvalue(List<OrderschClass> getorder_classes) {
        addition = 0;
        for (int i = 0; i < getorder_classes.size(); i++) {
            addition = addition + Double.parseDouble(getorder_classes.get(i).getOsm_qty());
        }
        String sdouble = new DecimalFormat("##").format(addition);
        total_quantity.setText(sdouble);
    }


    private void postorder() {
        pro.setVisibility(View.VISIBLE);
        Call<orderno> call = apiService.addschedule("MEY", aa);
        call.enqueue(new Callback<orderno>() {
            @Override
            public void onResponse(Call<orderno> call, Response<orderno> response) {
                try {
                    if (response.code() == 200) {
                        pro.setVisibility(View.GONE);
                        BCC = response.body();
                    } else {
                        //Toast.makeText(Dispatch_activity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<orderno> call, Throwable t) {

            }
        });
    }

}
