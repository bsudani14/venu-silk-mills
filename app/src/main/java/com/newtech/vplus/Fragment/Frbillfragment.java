package com.newtech.vplus.Fragment;


import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.newtech.vplus.Activity.FileDownloader;
import com.newtech.vplus.Activity.SalbillReport_Activity;
import com.newtech.vplus.BuildConfig;
import com.newtech.vplus.Activity.webviewactivity;
import com.newtech.vplus.Adapter.billadpeter;
import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.MainActivity;
import com.newtech.vplus.Model.billsubclass;
import com.newtech.vplus.R;
import com.newtech.vplus.json.RestClient;
import com.newtech.vplus.util.ConnectionDetector;
import com.newtech.vplus.util.Util_u;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Frbillfragment extends Fragment implements billadpeter.OnClickC {

    ListView frlistview;
    SimpleAdapter sm;
    String pcode;
    SQLiteDatabase db1;
    Bundle b = null;
    String value1;
    List<billsubclass> mExampleList;
    billadpeter adapter ;
    TextView total;
    String sal_code;
    ProgressBar progress;
    Boolean isInternetPresent = false;
    RadioGroup billgrp;
    String radiostring;
    boolean F=false;
    private Database_Helper ph;
    private SQLiteDatabase db;
    String dbname=null;
    private Util_u util_u;
    private String totalS;

    public Frbillfragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup  container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frbill, container, false);
        util_u = new Util_u(getContext(),getActivity());
        ph = new Database_Helper(getActivity());
        ActivityCompat.requestPermissions(getActivity(), new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        db = ph.getReadableDatabase();
        dbname = ph.GetVal("Select dbname from andmst where LOGIN='T'");
        // check for Internet status
        if (util_u.isNetworkAvailable()) {
            b = getArguments();
            if (b != null) {
                if (b.getString("pcodetab") != null) {
                    value1 = b.getString("pcodetab");
                } else {
                    Toast.makeText(getActivity(), "brcode Null",
                            Toast.LENGTH_LONG).show();
                }
            }
            frlistview=(ListView)rootView.findViewById(R.id.frbillv);
            total=(TextView)rootView.findViewById(R.id.totali);
            progress=(ProgressBar)rootView.findViewById(R.id.progressBar1);
            billgrp=(RadioGroup)rootView.findViewById(R.id.billradio);

            mExampleList = new LinkedList<billsubclass>();

            billgrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {

                    // TODO Auto-generated method stub
                    if(checkedId== R.id.radio0)
                    {
                        radiostring="All";
                        frlistview.setAdapter(null);
                        new partylist().execute();
                    }

                    else if(checkedId== R.id.radio1)
                    {
                        radiostring="Clear";
                        frlistview.setAdapter(null);
                        new partylist().execute();
                    }

                    else if(checkedId== R.id.radio2)
                    {
                        radiostring="Pending";
                        frlistview.setAdapter(null);
                        new partylist().execute();
                    }
                }
            });

            db=ph.getReadableDatabase();
            dbname= ph.GetVal("Select dbname From andmst where LOGIN='T'");

            radiostring="Pending";
            new partylist().execute();

        } else {
            util_u.showNoInternetDialog();
        }

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomTotalShow sop = new BottomTotalShow(totalS,null,null);
                sop.show(getActivity().getSupportFragmentManager(), sop.getTag());
            }
        });

        return rootView;
    }



    @Override
    public void onClickItem(final String billno,String TYPE) {

        if(TYPE.equals("DOWLODE")){
            Intent i = new Intent(getActivity(), SalbillReport_Activity.class);
            Bundle b1 = new Bundle();
            b1.putString("salcode", billno);
            i.putExtras(b1);
            startActivity(i);
        }
        else if(TYPE.equals("SHARED")){
            //salcode=salcode.replace("/","a");
            new DownloadFile().execute("http://" + BuildConfig.REPORT_SERVER_IP + ":99/SalebillReport/Viewsalebill?tcode="+billno,billno+ ".pdf");
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), billno + ".pdf");
            Uri uri = Uri.fromFile(outputFile);
            if (uri.equals("")) {

            } else {

			/*	final ComponentName name = new ComponentName("com.whatsapp", "com.whatsapp.ContactPicker");
				Intent oShareIntent = new Intent();
				oShareIntent.setAction(Intent.ACTION_SEND);
				oShareIntent.setComponent(name);
				oShareIntent.setType("application/pdf");
				oShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
				startActivity(oShareIntent);*/

                Intent oShareIntent = new Intent(Intent.ACTION_SEND);
                oShareIntent.setType("application/pdf");
                oShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(oShareIntent);
                //startActivity(Intent.createChooser(oShareIntent, "Shared the text ..."));
            }

        }

        //sal_code =ph.GetVal("Select salcode From partyemail");
/*
		new DownloadFile().execute("http://SERVER_IP:99/SalebillReport/Viewsalebill?tcode="+billno,billno+".pdf");
		//new DownloadFile().execute("http://SERVER_IP:99/SalebillReport/Viewsalebill?tcode="+sal_code,sal_code+".pdf");
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				progress.setVisibility(View.GONE);
				File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), billno+".pdf");
				Uri uri = Uri.fromFile(outputFile);
				if (uri.equals("")) {} else {
					final ComponentName name = new ComponentName("com.whatsapp", "com.whatsapp.ContactPicker");
					Intent oShareIntent = new Intent(Intent.ACTION_SEND);
					oShareIntent.setComponent(name);
					oShareIntent.setType("application/pdf");
					oShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
					startActivity(Intent.createChooser(oShareIntent,""));
				}
			}
		}, 2000);*/
    }



    private class partylist extends AsyncTask<String, Void, String> {
        int cnt=0;
        float amt, db, cr;
        float paidamt=0;
        double balamt=0;

        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            amt = 0;
            db = 0;
            cr = 0;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progress.setVisibility(View.GONE);
            if (result!="")
            {
                if (result.trim().equals("[]")){
                    util_u.showToast("No record found");
                }
                else{
                    JSONArray android1;
                    try {

                        mExampleList = new LinkedList<billsubclass>();

                        android1 = new JSONArray(result);
                        for (int i1 = 0; i1 < android1.length(); i1++)
                        {

                            JSONObject c = android1.getJSONObject(i1);
                            billsubclass mExample = new billsubclass();

                            mExample.date=c.getString("date");
                            mExample.sal_code=(c.getString("sal_code"));
                            mExample.sal_netamt=(c.getString("sal_netamt"));
                            mExample.datediff=(c.getString("datediff"));
                            mExample.sal_paidamt=(c.getString("sal_paidamt"));
                            mExample.pending=(c.getString("pending"));

                            balamt=balamt + (Double.parseDouble(c.getString("sal_netamt"))-Double.parseDouble(c.getString("sal_paidamt")));
                            balamt=Math.round(balamt*100.00)/100.00;

                            mExample.balance=(String.valueOf(balamt));

                            mExampleList.add(mExample);
                            cnt+=Float.parseFloat(c.getString("sal_netamt"));
                            paidamt+=Float.parseFloat(c.getString("sal_paidamt"));

                        }

                    } catch (JSONException e) {
                        util_u.showToast(e.getMessage().toString());
                    }

                    //adapter=null;
                    adapter = new billadpeter(getContext(), mExampleList,Frbillfragment.this);
                    adapter.notifyDataSetChanged();
                    frlistview.setAdapter((ListAdapter) adapter);

                    totalS = String.valueOf(Math.round((cnt-paidamt)*100.00)/100.00);

                }
            }
            else{
                util_u.showToast("Server is not working");
            }
        }

        @Override
        protected String doInBackground(String... arg0)
        {

            if(dbname!=null) { }
            else { dbname=""; }
            try {
                RestClient client = new RestClient("VPLUS/api/FrBill?p_Code="+value1+"&billvalue="+radiostring+"&dbname=MEY");
                String response="";
                try {
                    response=client.GetRequestexecute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response;
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                util_u.showToast(e.getMessage().toString());
            }
            return null;
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        private String saveFileName = null;

        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            saveFileName = fileName;
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "vplusPDFbill_SHARE");
            folder.mkdir();
            File pdfFile = new File(folder, fileName);
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }

}
