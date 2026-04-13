package com.newtech.vplus.Activity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.newtech.vplus.BuildConfig;
import com.newtech.vplus.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class Reportviewactivity extends Activity {

    PDFView pdfView;
    Toolbar mToolbar;
    String iNAME, cussign1, cuspic1, costcenter;
    ProgressBar p1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportview);
        mToolbar = (Toolbar) findViewById(R.id.too);
        mToolbar.setTitle("STOCK REPORT");
        mToolbar.setTitleTextColor(Color.WHITE);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        p1 = (ProgressBar) findViewById(R.id.progressBar2);
        Intent intent = getIntent();
        iNAME = intent.getStringExtra("iname");
        costcenter = intent.getStringExtra("cost");
        new showpdf().execute("http://" + BuildConfig.REPORT_SERVER_IP + ":83/Stock_Report_show/Viewreportstock?Itemname=" + iNAME.trim() + "&type=" + costcenter);
        new DownloadFile().execute("http://" + BuildConfig.REPORT_SERVER_IP + ":83/Stock_Report_show/Viewreportstock?Itemname=" + iNAME.trim() + "&type=" + costcenter, iNAME + ".pdf");
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
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "ACEKNITS");
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
