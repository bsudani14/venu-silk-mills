package com.venusilkmills.app.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.venusilkmills.app.BuildConfig;
import com.venusilkmills.app.R;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.json.RestClient;
import com.venusilkmills.app.util.Util_u;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CrystalActivity extends AppCompatActivity {

    private PDFView crystalReportWebView;
    private ProgressBar lineProgressBar;
    private String PDFurl,Mainurl,khcode,khname,reportType;
    private Util_u util;
    private TextView partyNameShow;
    private Calendar calendar;
    private Calendar calendar1;
    private DatePickerDialog datePickerDialog1,datePickerDialog2;
    private String[] fromto = new String[2];
    private Database_Helper ph;
    private SQLiteDatabase db;
    private boolean checkLoad = false;
    String khataname, khatacode,FTYPE,and_pcode,dbname;
    ArrayList<HashMap<String, Object>> spinerlistData, listdata, listdata1;
    SimpleAdapter sm1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crystal);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listdata1 = new ArrayList<HashMap<String, Object>>();
        util = new Util_u(CrystalActivity.this, this);

        ph = new Database_Helper(this);
        db = ph.getReadableDatabase();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        reportType = intent.getStringExtra("reportType");
        khcode = intent.getStringExtra("kh_code");
        khname = intent.getStringExtra("kh_name");
        if(reportType == null){
            reportType = "stock";
        }if(khname == null){
            khname = ph.GetVal("Select PNAME from andmst where LOGIN='T'");
        }if(khcode == null){
            khcode = ph.GetVal("Select PCODE from andmst where LOGIN='T'");
        }
        FTYPE=ph.GetVal("Select FTYPE From andmst where LOGIN='T'");
        dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");
        and_pcode=ph.GetVal("Select PCODE From andmst where LOGIN='T'");

        khname = khname.replace(".","");

        crystalReportWebView = (PDFView) findViewById(R.id.crystalReportWebView);
        lineProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        partyNameShow = (TextView) findViewById(R.id.partyNameShow);

        partyNameShow.setText(khname);

        loadWebView();

    }

    public void getFromDate(){

        calendar = Calendar.getInstance();

        datePickerDialog1 = new DatePickerDialog(CrystalActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                fromto[0] = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
                getToDate();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog1.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_NEGATIVE) {
                    datePickerDialog1.dismiss();
                    util.showToast("Date is necessary");
                    getFromDate();
                }
            }
        });


        datePickerDialog1.setTitle("From");
        datePickerDialog1.show();

    }
    private class showpdf extends AsyncTask<String, Void, InputStream> {

        protected void onPreExecute() {
            lineProgressBar.setVisibility(View.VISIBLE);
            checkLoad = false;
        }


        protected void onPostExecute(InputStream inputStream) {

            lineProgressBar.setVisibility(View.GONE);
            crystalReportWebView.fromStream(inputStream)
                    .load();
            checkLoad = true;

        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == 200){
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }
    }


    public void getToDate(){
        calendar1 = Calendar.getInstance();
        datePickerDialog2 = new DatePickerDialog(CrystalActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar1.set(i,i1,i2);
                fromto[1] = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar1.getTime());

                new khatalist().execute();


               // PDFurl = PDFurl+""+"todate="+fromto[1]+"&fromdate="+fromto[0];

                //PDFurl = "http%3A%2F%2FSERVER_IP%3A83%2FStockreport%2FViewStock%3Fkhcode%3D" + khcode + "%26frdate%3D" + fromto[0] + "%26todate%3D" + fromto[1] + "%26";
                // THIS PDF URL ENCODE HTML THEN SHOW/

                //new showpdf().execute(PDFurl);
               // Mainurl = "http://docs.google.com/gview?embedded=true&url=" + PDFurl;

                /*crystalReportWebView.setWebViewClient(new MyWebViewClient());
                crystalReportWebView.getSettings().setJavaScriptEnabled(true);
                crystalReportWebView.getSettings().setBuiltInZoomControls(true);

                crystalReportWebView.loadUrl(Mainurl);*/

            }
        }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));

        datePickerDialog2.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == DialogInterface.BUTTON_NEGATIVE){
                    dialogInterface.dismiss();
                    getToDate();
                }
            }
        });

        datePickerDialog2.setTitle("To");
        datePickerDialog2.show();

    }

    public void loadWebView(){
        getFromDate();
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            lineProgressBar.setVisibility(View.VISIBLE);
            checkLoad = false;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.loadUrl("javascript:(function() { " +
                    "document.querySelector('[role=\"toolbar\"]').remove();})()");
            lineProgressBar.setVisibility(View.GONE);
            checkLoad = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_file_download_menu) {
            if(checkLoad) {
                try {
                    new DownloadFile().execute(URLDecoder.decode(PDFurl, "UTF-8"), "A_REPORT.PDF");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else{
                util.showToast("Please Wait...");
            }
        }else if(id == R.id.action_file_change_date){
            if(checkLoad) {
                getFromDate();
            }else{
                util.showToast("Please Wait...");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crystal_report_menu, menu);
        return true;
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            util.showToast("Download Start");
            checkLoad = false;
            lineProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            util.showToast("Download Complete");
            checkLoad = true;
            lineProgressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(String... strings){
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "ACENITS_PDF");
            folder.mkdir();
            File pdfFile = new File(folder, fileName);
            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);

            return null;
        }
    }

    private class khatalist extends AsyncTask<String, Void, String> {
    LayoutInflater inflater = getLayoutInflater();
    AlertDialog.Builder builder = new AlertDialog.Builder(CrystalActivity.this);
    View view1 = inflater.inflate(R.layout.sample, null);
    ListView lv;
    ProgressBar progress;
    Toolbar mToolbar1;
    AlertDialog passwordDialog = null;

    protected void onPreExecute() {


        final View view = inflater.inflate(R.layout.sample, null);

        builder.setView(view);

        passwordDialog = builder.create();
        EditText sampleinputSearch = view.findViewById(R.id.sampleinputSearch);
        sampleinputSearch.setVisibility(View.GONE);
        lv = (ListView) view.findViewById(R.id.listparty);
        progress = (ProgressBar) view.findViewById(R.id.progressBar1);
        mToolbar1 = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar1.setTitle("Cost Center");
        mToolbar1.setTitleTextColor(Color.WHITE);
        progress.setVisibility(View.VISIBLE);
        passwordDialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progress.setVisibility(View.GONE);
        if (result != "") {
            if (result.trim().equals("[]")) {
                DisplayMsg("No record found.");
            } else {
                JSONArray android1;
                try {
                    listdata1.clear();
                    android1 = new JSONArray(result);

                    for (int i1 = 0; i1 < android1.length(); i1++) {
                        JSONObject c = android1.getJSONObject(i1);
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("a", c.getString("kh_name"));
                        map.put("b", c.getString("kh_code"));
                        listdata1.add(map);
                    }
                } catch (JSONException e) {
                    DisplayMsg(e.getMessage().toString());
                }

                sm1 = new SimpleAdapter(getApplicationContext(), listdata1, R.layout.list_row, new String[]{"a"}, new int[]{R.id.t1});
                sm1.notifyDataSetChanged();
                lv.setAdapter(sm1);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view2,
                                            int position, long arg3) {
                        ListView lv = (ListView) passwordDialog
                                .findViewById(R.id.listparty);
                        String grpcode = null;
                        Object o = lv.getItemAtPosition(position);
                        String strparty = (String) o.toString();

                        try {
                            strparty = strparty.substring(1, strparty.length() - 1);           //remove curly brackets
                            String[] keyValuePairs = strparty.split(",");              //split the string to creat key-value pairs
                            Map<String, String> map = new HashMap<String, String>();
                            for (String pair : keyValuePairs)                        //iterate over the pais
                            {
                                String[] entry = pair.split("=");                   //split the pairs to get key and value
                                map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap
                                if (entry[0].trim().equals("a")) {
                                    khataname = entry[1].trim();
                                }

                                if (entry[0].trim().equals("b")) {
                                    khatacode = entry[1].trim();
                                }
                            }

                            PDFurl = "http://" + BuildConfig.REPORT_SERVER_IP + ":83/RptSaleReg/Viewsalereg?";
                            PDFurl = PDFurl+""+"frdate="+fromto[0]+"&todate="+fromto[1]+"&khcode="+khatacode;
                           // PDFurl = PDFurl+""+"todate="+fromto[1]+"&fromdate="+fromto[0];
                            new showpdf().execute(PDFurl);
                        } catch (Exception e) {
                            DisplayMsg(e.getMessage());
                        }
                        passwordDialog.dismiss();

                    }


                });
            }

        } else {
            DisplayMsg("Server is not working");

        }

    }


    protected String doInBackground(String... arg0) {
        if (dbname != null) {

        } else {
            dbname = "";

        }
        try {


            RestClient client = new RestClient("CMC/api/Khata?dbname=" + dbname + "&ftype=" + FTYPE + "&code=" + and_pcode);
            String response = "";
            try {

                response = client.GetRequestexecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            DisplayMsg(e.getMessage().toString());
        }


        return null;
    }


}

    private void DisplayMsg(final String s) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(CrystalActivity.this, s.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}

