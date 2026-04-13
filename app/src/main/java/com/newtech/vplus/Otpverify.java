package com.newtech.vplus;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.newtech.vplus.Activity.JavaMailAPI;
import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.Model.LoginClass;
import com.newtech.vplus.Model.OtpClass;
import com.newtech.vplus.json.ApiClient;
import com.newtech.vplus.json.ApiInterface;
import com.newtech.vplus.json.RestClient;
import com.newtech.vplus.util.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Otpverify extends AppCompatActivity {

    List<LoginClass> lc;
    EditText otpverify;
    Bundle b = null;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    String value1, Cusid, ftype, plocat, dbname;
    ApiInterface apiService;
    Database_Helper ph;
    SQLiteDatabase db;
    public String mSubject="Vplus OTP";
    public EditText mMessage;
    String TextFileURL = "http://www.newtechinfosol.co.in/staticip/OTPMAILANDROID.TXT";
    URL url;
    BufferedReader bufferReader;
    TextView tryagain;
    ProgressBar pro1;
    String TextHolder = "", TextHolder2 = "", A = "",TextHolder1="",TextHolder3="",TextHolder4="",pass="",partyemail="";
    private static Otpverify inst;

    public static Otpverify instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpverify);
        otpverify = (EditText) findViewById(R.id.otp1);
        Button okotp1 = (Button) findViewById(R.id.okotp1);
        tryagain = (TextView) findViewById(R.id.tryagain);
        pro1 = (ProgressBar) findViewById(R.id.pro1);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        cd = new ConnectionDetector(getApplicationContext());
        ph = new Database_Helper(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        pro1.setVisibility(View.VISIBLE);
        if (isInternetPresent) {
            b = getIntent().getExtras();
            if (b != null) {
                if (b.getString("mobileno") != null) {
                    value1 = b.getString("mobileno");
                }
                if (b.getString("PCODE") != null) {
                    Cusid = b.getString("PCODE");
                }
                if (b.getString("Ftype") != null) {
                    ftype = b.getString("Ftype");
                }
                if (b.getString("plocat") != null) {
                    plocat = b.getString("plocat");

                } else {
                    Toast.makeText(getApplicationContext(),
                            "mobileno Null", Toast.LENGTH_LONG).show();
                }
            }

            okotp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (otpverify.getText().toString().equals("")) {
                        DisplayMsg("Enter Otp First");
                    } else {
                        Verifyotp();
                    }
                }
            });

            new GetNotePadFileFromServer().execute();

            tryagain.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    callapi();
                }
            });

        } else {
            DisplayMsg("CHECK INTERNET CONNECTION");
        }
    }

    private void DisplayMsg(final String s) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Otpverify.this, s.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendMail() {


        String EMAIL = ph.GetVal("Select admin From admintxtfile");;

        String PASSWORD = ph.GetVal("Select pass From txtfile");;
        String mail = partyemail;
        //String mail = ph.GetVal("Select Email From email");
        //String message = mMessage.getText().toString();
        String message = ph.GetVal("Select ROTP From andmst");
        String subject = mSubject;

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,message,subject,EMAIL,PASSWORD);
        javaMailAPI.execute();



    }
    private void Verifyotp() {
        pro1.setVisibility(View.VISIBLE);
        OtpClass oc = new OtpClass(otpverify.getText().toString(), value1);
        Call<OtpClass> call = apiService.checkotp("application/json", plocat, oc);

        call.enqueue(new Callback<OtpClass>() {
            @Override
            public void onResponse(Call<OtpClass> call, Response<OtpClass> response) {
                pro1.setVisibility(View.GONE);
                OtpClass otpc = response.body();
                if (otpc != null && "Match".equals(otpc.getResult())) {
                    db = ph.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("LOGIN", "T");
                    db.update("andmst", cv, "PCODE='" + Cusid + "'", null);
                    finish();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                } else {
                    DisplayMsg(otpc.getResult());
                }

            }

            @Override
            public void onFailure(Call<OtpClass> call, Throwable t) {
                DisplayMsg(t.getMessage());
            }
        });
    }

    private void callapi() {
        pro1.setVisibility(View.VISIBLE);
        Call<List<LoginClass>> call = apiService.getlogindata(value1, plocat);
        call.enqueue(new Callback<List<LoginClass>>() {
            @Override
            public void onResponse(Call<List<LoginClass>> call, Response<List<LoginClass>> response) {
                pro1.setVisibility(View.GONE);
                lc = response.body();
                try {
                    if (lc.get(0).getResult().equals("success")) {
                        db = ph.getWritableDatabase();
                        db.delete("andmst", null, null);
                        db = ph.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("ROTP", lc.get(0).getOtpno());
                        db.insert("andmst", null, cv);
                        sendMail();
                        DisplayMsg("Resend Otp");
                    } else {
                        DisplayMsg("not match");
                    }
                } catch (Exception e) {
                    DisplayMsg("Error Occur");
                }

            }

            @Override
            public void onFailure(Call<List<LoginClass>> call, Throwable t) {
                DisplayMsg(t.getMessage());
            }
        });
    }

    public void recivedSms(String message) {
        try {
            otpverify.setText(message);
            pro1.setVisibility(View.GONE);
        } catch (Exception e) {
            DisplayMsg(e.getMessage().toString());
        }
    }

    public class GetNotePadFileFromServer extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(TextFileURL);
                bufferReader = new BufferedReader(new InputStreamReader(url.openStream()));



                if((TextHolder2 = bufferReader.readLine()) != null) {

                    TextHolder = TextHolder2;
                    db = ph.getWritableDatabase();
                    db.delete("admintxtfile", null, null);
                    db = ph.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("admin", TextHolder);
                    db.insert("admintxtfile", null, cv);

                }

                if((TextHolder1 = bufferReader.readLine()) != null) {
                    pass = TextHolder1;

                    db = ph.getWritableDatabase();
                    db.delete("txtfile", null, null);
                    db = ph.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("pass", pass);
                    db.insert("txtfile", null, cv);
                }

                int i;
                int numberOfLines = 9;
                //bufferReader = new String[numberOfLines];

                for (i=0; i < numberOfLines; i++) {
                    //TextHolder3 = bufferReader.readLine();
                    //numberOfLines++;
                    // moral1-moralenterprise@gmail.com
                    TextHolder3 = bufferReader.readLine( );
                    if (TextHolder3 == null) break;
                    String res = TextHolder3.length() >= 6 ? TextHolder3.substring(0, 6) : "";
                    if ((res.equals("Vplus-"))) {
                        String str = TextHolder3;
                        String strNew = str.replace("Vplus-", "");
                        // String strNew = str.replace("vastu-", "");
                        partyemail = strNew;
                        db = ph.getWritableDatabase();
                        db.delete("partyemail", null, null);
                        db = ph.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("paemail", partyemail);
                        db.insert("partyemail", null, cv);
                    }
                    else{
                        //Toast.makeText(LoginActivity.this, "wroung Email", Toast.LENGTH_SHORT).show();
                    }

                }

                bufferReader.close();

            } catch (MalformedURLException malformedURLException) {

                // TODO Auto-generated catch block
                malformedURLException.printStackTrace();
                TextHolder = malformedURLException.toString();
                pass = malformedURLException.toString();
                partyemail = malformedURLException.toString();

            } catch (IOException iOException) {

                // TODO Auto-generated catch block
                iOException.printStackTrace();

                TextHolder = iOException.toString();
                pass = iOException.toString();
                partyemail = iOException.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void finalTextHolder) {

            //webview.loadUrl("http://" + TextHolder + "/suratdiesels/binduserlogindetails.aspx");

            super.onPostExecute(finalTextHolder);
        }
    }
}

