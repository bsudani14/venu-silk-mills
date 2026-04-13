package com.venusilkmills.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.venusilkmills.app.BuildConfig;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.R;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SalbillReport_Activity extends AppCompatActivity {
    private WebView crystalReportWebView;
    private ProgressBar lineProgressBar;
    private String PDFurl, Mainurl, khcode, khname, reportType;
    private Database_Helper ph;
    private SQLiteDatabase db;
    String salcode,salcode1;
    Bundle b;
    LinearLayout l1,l2;
    private boolean checkLoad = false;
    ImageView shared;
    Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crystal);
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b = new Bundle();

        b = getIntent().getExtras();

        salcode=b.getString("salcode");
        ph = new Database_Helper(this);
        db = ph.getReadableDatabase();


        salcode1=salcode.replace("/","a");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shared=(ImageView)findViewById(R.id.shared);
        new DownloadFile().execute("http://" + BuildConfig.REPORT_SERVER_IP + ":99/SalebillReport/Viewsalebill?tcode="+salcode,salcode1+".pdf");
        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // boolean installed = appInstalledOrNot("com.whatsapp");
                File outputFile = new File(Environment.getExternalStoragePublicDirectory("MORALBILL"), salcode1 + ".pdf");
                uri = Uri.fromFile(outputFile);

                if (uri.equals("")) {

                } else {
                   /* final ComponentName name = new ComponentName("com.whatsapp", "com.whatsapp.ContactPicker");
                    Intent oShareIntent = new Intent();
                    oShareIntent.setComponent(name);
                    oShareIntent.setType("application/pdf");
                    oShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(oShareIntent);*/


                    Intent oShareIntent = new Intent(Intent.ACTION_SEND);
                    oShareIntent.setType("application/pdf");
                    oShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(oShareIntent);
                    // startActivity(Intent.createChooser(oShareIntent, "Shared the text ..."));



                }
            }
        });

        crystalReportWebView = (WebView) findViewById(R.id.crystalReportWebView);
        lineProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        getdata();
        //new DownloadFile().execute("http://SERVER_IP:88/Salebill/viewbill?Salcode="+salcode, "S"+salcode1 + ".pdf");
        new DownloadFile().execute("http://" + BuildConfig.REPORT_SERVER_IP + ":99/SalebillReport/Viewsalebill?tcode="+salcode,salcode1+".pdf");

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
    public void getdata() {
        PDFurl = "http://" + BuildConfig.REPORT_SERVER_IP + ":99/SalebillReport/Viewsalebill?";
        //PDFurl = "http%3A%2F%2FSERVER_IP%3A83%2FStockreport%2FViewStock%3Fkhcode%3D" + khcode + "%26frdate%3D" + fromto[0] + "%26todate%3D" + fromto[1] + "%26";
        // THIS PDF URL ENCODE HTML THEN SHOW/
        try {
            PDFurl = PDFurl + "" + URLEncoder.encode("tcode=" + salcode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Mainurl = "http://docs.google.com/gview?embedded=true&url=" + PDFurl;

        crystalReportWebView.setWebViewClient(new MyWebViewClient());
        crystalReportWebView.getSettings().setJavaScriptEnabled(true);
        crystalReportWebView.getSettings().setBuiltInZoomControls(true);
        crystalReportWebView.loadUrl(Mainurl);

    }

    private void DisplayMsg(final String s) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(SalbillReport_Activity.this, s.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "MORALBILL");
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

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
