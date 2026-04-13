package com.venusilkmills.app.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.venusilkmills.app.Activity.FileDownloader;
import com.venusilkmills.app.BuildConfig;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.R;
import com.venusilkmills.app.util.Util_u;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class OutstandingFragment  extends Fragment {


    private WebView crystalReportWebView;
    private ProgressBar lineProgressBar;
    private String PDFurl,Mainurl,pcode;
    private Util_u util;
    private Database_Helper ph;
    private SQLiteDatabase db;
    ImageButton outstanding_entry_reset;

    public OutstandingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outstanding, container, false);

        util = new Util_u(getActivity(),getActivity());
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        ph = new Database_Helper(getContext());
        db = ph.getReadableDatabase();
        ActivityCompat.requestPermissions(getActivity(), new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        outstanding_entry_reset = (ImageButton)view.findViewById(R.id.outstanding_entry_reset);
        crystalReportWebView = (WebView) view.findViewById(R.id.crystalReportWebView);
        lineProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        outstanding_entry_reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OutstandingFragment.this.crystalReportWebView.loadUrl(Mainurl);
            }});


        pcode = getArguments().getString("pcodetab");

        PDFurl = "http://" + BuildConfig.REPORT_SERVER_IP + ":99/outstandingreport/outstandingrpt?pcode="+pcode;

        Mainurl = "http://docs.google.com/gview?embedded=true&url=" + PDFurl;

        crystalReportWebView.setWebViewClient(new MyWebViewClient());
        crystalReportWebView.getSettings().setJavaScriptEnabled(true);
        crystalReportWebView.getSettings().setBuiltInZoomControls(true);

        crystalReportWebView.loadUrl(Mainurl);

        return view;
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            lineProgressBar.setVisibility(View.VISIBLE);
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
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_shared) {
            // http://SERVER_IP:99/outstandingreport/outstandingrpt?pcode=2137
            new DownloadFile().execute("http://" + BuildConfig.REPORT_SERVER_IP + ":99/outstandingreport/outstandingrpt?pcode="+pcode,pcode+".pdf");
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    File outputFile = new File(Environment.getExternalStoragePublicDirectory
                            ("MoralPDF_SHARE"), pcode+".pdf");
                    Uri uri = Uri.fromFile(outputFile);
                    if (uri.equals("")) {} else {
                      /*  final ComponentName name = new ComponentName("com.whatsapp", "com.whatsapp.ContactPicker");
                        Intent oShareIntent = new Intent();
                        oShareIntent.setComponent(name);
                        oShareIntent.setType("application/pdf");
                        oShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(oShareIntent);
*/
                        Intent oShareIntent = new Intent(Intent.ACTION_SEND);
                        oShareIntent.setType("application/pdf");
                        oShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(oShareIntent);
                        // startActivity(Intent.createChooser(oShareIntent, "Shared the text ..."));
                    }
                }
            }, 2000);
        }else{

        }
        return super.onOptionsItemSelected(item);
    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_shared, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        private String saveFileName = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            saveFileName = fileName;
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "VplusPDF_SHARE");
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
