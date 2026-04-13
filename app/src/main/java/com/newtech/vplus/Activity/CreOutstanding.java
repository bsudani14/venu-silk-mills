package com.newtech.vplus.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.newtech.vplus.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreOutstanding extends AppCompatActivity {

    PDFView pdfView;
    Toolbar mToolbar;
    String iNAME, cussign1, cuspic1, costcenter;
    ProgressBar p1;
    Bundle b;
    String SALCODE, khatacode, khataname, brcode;
    ImageView imgshared;
    Uri uri;
    String tdate;
    private Calendar calendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportview);
        mToolbar = (Toolbar) findViewById(R.id.too);
        mToolbar.setTitle("Creditor Outstanding Report");
        mToolbar.setTitleTextColor(Color.WHITE);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        p1 = (ProgressBar) findViewById(R.id.progressBar2);
        imgshared = (ImageView) findViewById(R.id.shared);
        imgshared.setVisibility(View.VISIBLE);
        b = new Bundle();
        b = getIntent().getExtras();
        khataname = b.getString("kname");
        khatacode = b.getString("kcode");
        brcode = b.getString("brcode");
        tdate=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        new showpdf().execute("http://43.228.126.198:83/CreOutstaning_Report/Viewsaleout?todate="+tdate+"&type=" + khataname + "&brcode=" + brcode);
        new DownloadFile().execute("http://43.228.126.198:83/CreOutstaning_Report/Viewsaleout?todate="+tdate+"&type=" + khataname + "&brcode=" + brcode, brcode + ".pdf");

        imgshared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1.setVisibility(View.VISIBLE);
                File outputFile = new File(Environment.getExternalStoragePublicDirectory
                        ("CMC"), brcode + ".pdf");
                uri = Uri.fromFile(outputFile);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p1.setVisibility(View.GONE);
                        final ComponentName name = new ComponentName("com.whatsapp", "com.whatsapp.ContactPicker");
                        Intent oShareIntent = new Intent();
                        oShareIntent.setComponent(name);
                        oShareIntent.setType("application/pdf");
                        oShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(oShareIntent);
                    }
                }, 5000);
            }
        });
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
            File folder = new File(extStorageDirectory, "CMC");
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
