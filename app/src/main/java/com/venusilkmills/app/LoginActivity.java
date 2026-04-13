package com.venusilkmills.app;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

// removed old import
import com.venusilkmills.app.Activity.JavaMailAPI;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Model.LoginClass;
import com.venusilkmills.app.json.ApiClient;
import com.venusilkmills.app.json.ApiInterface;
import com.venusilkmills.app.json.RestClient;
import com.venusilkmills.app.util.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    EditText mobileno;
    List<LoginClass> lc;
    ApiInterface apiService;
    String Email,mb;
    static Database_Helper ph;
    SQLiteDatabase db;
    public String mSubject="App OTP";
    public EditText mMessage;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    String resultarray, otpnoarray, pcodearray, paddress, pname, brcode, brname, ftype, plocat;
    ProgressBar pro1;
    WebView webview;
    String TextFileURL = BuildConfig.OTP_CONFIG_URL;
    URL url;
    BufferedReader bufferReader;
    TextView textView;
    String TextHolder = "", TextHolder2 = "", A = "",TextHolder1="",TextHolder3="",TextHolder4="",pass="",partyemail="";
    String mobileno_s;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button okotp = (Button) findViewById(R.id.okotp);
        mobileno = (EditText) findViewById(R.id.mobileotp);
        pro1 = (ProgressBar) findViewById(R.id.pro1);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        ph = new Database_Helper(getApplicationContext());
        new GetNotePadFileFromServer().execute();

        okotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetPresent) {
                    if (mobileno.getText().toString().equals("")) {
                        DisplayMsg("Enter Mobile No");
                    }
                    else {
                        callapi();
                    }
                } else {
                    DisplayMsg("CHECK INTERNET CONNECTION");
                }
            }
        });
    }

    private void DisplayMsg(final String s) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(LoginActivity.this, s.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendMail() {

        String EMAIL = ph.GetVal("Select admin From admintxtfile");

        String PASSWORD = ph.GetVal("Select pass From txtfile");

        // String mail = mEmail;
        String mail = partyemail;
        //String message = mMessage.getText().toString();
        String message = ph.GetVal("Select Potp From andmst");;
        String subject = mSubject;

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,message,subject,EMAIL,PASSWORD);
        javaMailAPI.execute();
    }

    private void callapi() {
        pro1.setVisibility(View.VISIBLE);
        Call<List<LoginClass>> call = apiService.getlogindata(mobileno.getText().toString(), "");
        call.enqueue(new Callback<List<LoginClass>>() {
            @Override
            public void onResponse(Call<List<LoginClass>> call, Response<List<LoginClass>> response) {
                pro1.setVisibility(View.GONE);
                lc = response.body();
                try {
                    if (lc.get(0).getResult().equals("success")) {
                        DisplayMsg("MATCH SUCCESSFULLY");
                        resultarray = lc.get(0).getResult();
                        otpnoarray = lc.get(0).getOtpno();
                        pcodearray = lc.get(0).getCust_id();
                        paddress = lc.get(0).getCust_address();
                        pname = lc.get(0).getCust_name();
                        brcode = lc.get(0).getBrcode();
                        brname = lc.get(0).getBrname();
                        ftype = lc.get(0).getFtype();
                        plocat = lc.get(0).getPlocat();

                        db = ph.getWritableDatabase();
                        db.delete("andmst", null, null);
                        db.delete("email", null, null);
                        db = ph.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        ContentValues cv1 = new ContentValues();
                        cv.put("MOBILENO", mobileno.getText().toString());
                        cv.put("PNAME", pname);
                        cv1.put("Email", Email);
                        cv1.put("MOBILENO", mb);
                        cv.put("PADDRESS", paddress);
                        cv.put("PCODE", pcodearray);
                        cv.put("Potp", otpnoarray);
                        cv.put("BRCODE", brcode);
                        cv.put("BRNAME", brname);
                        cv.put("LOGIN", "F");
                        cv.put("dbname", plocat);
                        cv.put("FTYPE", ftype);

                        db.insert("andmst", null, cv);
                        db.insert("email", null, cv1);

                        sendMail();
                        finish();
                        Intent i = new Intent(getApplicationContext(), Otpverify.class);
                        Bundle b = new Bundle();
                        b.putString("mobileno", mobileno.getText().toString());
                        b.putString("PCODE", pcodearray);
                        b.putString("Ftype", ftype);
                        b.putString("plocat", plocat);
                        i.putExtras(b);
                        startActivity(i);

                    } else {
                        DisplayMsg("not match");
                    }
                } catch (Exception e) {
                    DisplayMsg("Error Occur");
                }
            }

            @Override
            public void onFailure(Call<List<LoginClass>> call, Throwable t) {
            }
        });
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
                    // email comment removed for security
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
