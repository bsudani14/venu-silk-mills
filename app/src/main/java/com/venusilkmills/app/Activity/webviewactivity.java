package com.venusilkmills.app.Activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.venusilkmills.app.BuildConfig;
import com.venusilkmills.app.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class webviewactivity extends AppCompatActivity {

    PDFView pdfView;
    Toolbar mToolbar;
    String iNAME,cussign1,cuspic1,costcenter;
    ProgressBar p1;
    Bundle b;
    String SALCODE;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportview);
        mToolbar = (Toolbar) findViewById(R.id.too);
        mToolbar.setTitle("SALE BILL");
        mToolbar.setTitleTextColor(Color.WHITE);
        pdfView=(PDFView)findViewById(R.id.pdfView);
        p1=(ProgressBar)findViewById(R.id.progressBar2);
        b = new Bundle();
        b = getIntent().getExtras();
        SALCODE=b.getString("salcode");
        new webviewactivity.showpdf().execute("http://" + BuildConfig.REPORT_SERVER_IP + ":83/Salebill/Viewbill?Itemname="+SALCODE+"&type=0");
        new webviewactivity.DownloadFile().execute("http://" + BuildConfig.REPORT_SERVER_IP + ":83/Salebill/Viewbill?Itemname="+SALCODE+"&type=0",SALCODE+".pdf");

    }


    private class showpdf extends AsyncTask<String, Void, InputStream> {

        protected void onPreExecute() {
            p1.setVisibility(View.VISIBLE);
        }


        protected void onPostExecute(InputStream inputStream) {

            p1.setVisibility(View.GONE);
            pdfView.fromStream(inputStream)
                    .load();

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

    private class DownloadFile extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "ACEKNITS");
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


}


   /* WebView webview;
    ProgressDialog pDialog;
    ImageView backimgae;
    Bundle b;
    String SALCODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        backimgae=(ImageView)findViewById(R.id.imageView1);
        b = new Bundle();

        b = getIntent().getExtras();

        SALCODE=b.getString("salcode");
       // SALCODE=SALCODE.replace("/","%2F");


        init();
        listener();
        backimgae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                webviewactivity.this.finish();
            }
        });
    }

    private void init() {
        webview = (WebView) findViewById(R.id.web1);
        webview.getSettings().setJavaScriptEnabled(true);
        pDialog = new ProgressDialog(webviewactivity.this);
        pDialog.setTitle("PDF");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        webview.getSettings().setBuiltInZoomControls(true);


        String myPdfUrl = null;

            myPdfUrl = "http://SERVER_IP:82/Salebill/Viewbill?Itemname="+SALCODE+"&type=0";
        myPdfUrl=myPdfUrl.replace("&","%26");
        String url = "http://docs.google.com/gview?embedded=true&url=" + myPdfUrl;
        webview.loadUrl(url);


    }

    private void listener() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });
    }
}




*/