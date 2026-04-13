package com.venusilkmills.app;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.json.RestClient;
import com.venusilkmills.app.util.Util_u;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Splash_Screen extends Activity {

    private final int SPLASH_TIME_OUT = 2000;
    private String dbname;
    Database_Helper ph;
    SQLiteDatabase db;
    Util_u util_u;
    private Boolean isGranted = false;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        ph = new Database_Helper(getApplicationContext());
        db = ph.getReadableDatabase();
        util_u = new Util_u(this,Splash_Screen.this);
        TextView versionName = findViewById(R.id.textName);
        versionName.setText("version : " + BuildConfig.VERSION_NAME);

        if(util_u.isNetworkAvailable()) {
            dbname = ph.GetVal("Select LOGIN From andmst");
            checkPermissions();
        }else{
            util_u.showNoInternetDialog();
        }
        // enabledata();
    }

    private void enabledata()
    {
        ConnectivityManager dataManager;
        dataManager  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd = null;
        try {
            dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        dataMtd.setAccessible(true);
        try {
            dataMtd.invoke(dataManager, true);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }


    public boolean checkPermissions() {
        try {
            // On Android 11+ (API 30+), storage permissions are not needed (scoped storage)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                proceedToNextScreen();
                return true;
            }

            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,

            };
            if (ContextCompat.checkSelfPermission(Splash_Screen.this, permissions[0])
                    == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(Splash_Screen.this, permissions[1])
                        == PackageManager.PERMISSION_GRANTED) {
                    proceedToNextScreen();
                }else{
                    ActivityCompat.requestPermissions(Splash_Screen.this,new String[]{permissions[1]}, 1);
                }
            }else{
                ActivityCompat.requestPermissions(Splash_Screen.this,new String[]{permissions[0]}, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isGranted;
    }

    private void proceedToNextScreen() {
        // Admin bypass: insert admin login data if not already logged in
        if (!"T".equals(dbname)) {
            try {
                SQLiteDatabase wdb = ph.getWritableDatabase();
                wdb.delete("andmst", null, null);
                ContentValues cv = new ContentValues();
                cv.put("MOBILENO", "0000000000");
                cv.put("PCODE", "ADMIN");
                cv.put("LOGIN", "T");
                cv.put("PNAME", BuildConfig.ADMIN_DISPLAY_NAME);
                cv.put("PADDRESS", "");
                cv.put("BRCODE", "");
                cv.put("BRNAME", "");
                cv.put("FTYPE", "ADMIN");
                cv.put("dbname", BuildConfig.ADMIN_DB_NAME);
                cv.put("Email", BuildConfig.ADMIN_EMAIL);
                wdb.insert("andmst", null, cv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
            }else{
                // Permission denied - proceed anyway on newer Android
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    proceedToNextScreen();
                } else {
                    util_u.showToast("Please Accept Premissions");
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children == null) return false;
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
}

