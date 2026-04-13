package com.newtech.vplus.Activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.newtech.vplus.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Challanviewactivity extends Activity {
    PDFView pdfView;
    Toolbar mToolbar;
    ImageView shared;
    String iNAME,chalno,costcenter,chalno1;
    ProgressBar p1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportview);
        mToolbar = (Toolbar) findViewById(R.id.too);
        mToolbar.setTitle("CHALLAN");
        mToolbar.setTitleTextColor(Color.WHITE);
        pdfView=(PDFView)findViewById(R.id.pdfView);
        p1=(ProgressBar)findViewById(R.id.progressBar2);
        shared=(ImageView)findViewById(R.id.shared);
        shared.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        chalno = intent.getStringExtra("chno");
        chalno1 = intent.getStringExtra("chno");
        chalno1=chalno1.replace("/","-");
        new showpdf().execute("http://43.228.126.198:83/Challanview/Viewbill?Itemname="+chalno.trim());
        new DownloadFile().execute("http://43.228.126.198:83/Challanview/Viewbill?Itemname="+chalno.trim(),chalno1+".pdf");
        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputFile = new File(Environment.getExternalStoragePublicDirectory
                        ("ACEKNITSCHALLAN"), chalno1 + ".pdf");
                Uri uri = Uri.fromFile(outputFile);
                if (uri.equals("")) {
                    Toast.makeText(Challanviewactivity.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                } else {

                    final ComponentName name = new ComponentName("com.whatsapp", "com.whatsapp.ContactPicker");
                    Intent oShareIntent = new Intent();
                    oShareIntent.setComponent(name);
                    oShareIntent.setType("application/pdf");
                    oShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(oShareIntent);
                }
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
            File folder = new File(extStorageDirectory, "ACEKNITSCHALLAN");
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

