package com.venusilkmills.app.FCM;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Splash_Screen;
import com.venusilkmills.app.json.RestClient;

import java.io.IOException;


public class RegisterApp extends AsyncTask<Void, Void, String> {

    private static final String TAG = "GCMRelated";
    Context ctx;
    GoogleCloudMessaging gcm;
    String SENDER_ID = "99373741966";

    String regid = null;
    private int appVersion;
    Database_Helper ph;
    SQLiteDatabase db;
    String str ;


    public RegisterApp(Context ctx, GoogleCloudMessaging gcm, int appVersion){
        this.ctx = ctx;
        this.gcm = gcm;
        this.appVersion = appVersion;
        str = Settings.Secure.getString(ctx.getContentResolver(), "android_id");
    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(Void... arg0) {
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(ctx);

            }
            regid = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regid;



            storeRegistrationId(ctx, regid);

        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
        }
        return regid;
    }

    private void storeRegistrationId(Context ctx, String regid) {
        final SharedPreferences prefs = ctx.getSharedPreferences(Splash_Screen.class.getSimpleName(),Context.MODE_PRIVATE);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("registration_id", regid);
        editor.putInt("appVersion", appVersion);
        editor.commit();

    }



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ph = new Database_Helper(ctx);


        db = ph.getWritableDatabase();

        String Cusid=ph.GetVal("Select PCODE From andmst where LOGIN='T'");
        String FTYPE=ph.GetVal("Select ftype From andmst where LOGIN='T'");
        String PNAME=ph.GetVal("Select PNAME From andmst where LOGIN='T'");

        try {

            String Json1="";
            Json1= Json1+ "{";
            Json1= Json1 + "\""  +  "Android_Id" + "\":"  + "\"" + str + "\",";
            Json1= Json1 + "\""  +  "FCM_ID" + "\":"  + "\"" + regid + "\",";
            Json1= Json1 + "\""  + "P_Code" + "\":"  + "\"" + Cusid + "\",";
            Json1= Json1 + "\""  + "P_NAME" + "\":"  + "\"" + PNAME + "\",";
            Json1= Json1 + "\""  + "FTYPE" + "\":"  + "\"" + FTYPE + "\"";

            Json1= Json1+ "}";
            RestClient client1 = new RestClient("ACEKNITS/api/AndGCMRegister");
            String response1="";
            response1=client1.POST(Json1);


        } catch (Exception e) {
            //Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        ContentValues values = new ContentValues();
        values.put("Android_id",str);
        values.put("GCM_REG_ID", regid);
        db.update("andmst", values, "PCODE" + "='" + Cusid + "'", null);


        //Toast.makeText(ctx, Str,Toast.LENGTH_LONG).show();
        //Toast.makeText(ctx, "Registration Completed. Now you can see the notifications", Toast.LENGTH_SHORT).show();
        Log.v(TAG, "test");
    }

}