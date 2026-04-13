package com.venusilkmills.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.venusilkmills.app.Adapter.ledgeradpeter;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Model.OpeningBal;
import com.venusilkmills.app.Model.ledgersubclass;
import com.venusilkmills.app.R;
import com.venusilkmills.app.json.ApiClient;
import com.venusilkmills.app.json.ApiInterface;
import com.venusilkmills.app.json.RestClient;
import com.venusilkmills.app.util.ConnectionDetector;
import com.venusilkmills.app.util.Util_u;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Frledgerfragment extends Fragment {
    ListView ledgerlistview;
    ArrayList<HashMap<String, Object>> listdata;
    SimpleAdapter sm;
    String pcode;
    float amt, db, cr;
    Bundle b = null;
    String value1, dbname;
    List<ledgersubclass> mExampleList;
    ledgeradpeter adapter;
    TextView total, totcr, totdb;
    ProgressBar progress;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    private Database_Helper ph;
    float aa;
    ApiInterface apiService;
    private SQLiteDatabase dbd;
    String totalS,totcrS,totdbS;
    TextView txtloc;
    private Util_u util_u;
    public Frledgerfragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frledgermain, container, false);
        ph = new Database_Helper(getActivity());
        txtloc =(TextView)rootView.findViewById(R.id.txtloc1);
        dbname = ph.GetVal("Select dbname from andmst where LOGIN='T'");
        pcode=ph.GetVal("Select PCODE From andmst where LOGIN='T'");
        apiService = ApiClient.getClient().create(ApiInterface.class);

        util_u = new Util_u(getContext(),getActivity());
        // check for Internet status
        if (util_u.isNetworkAvailable()) {
            b = getArguments();
            if (b != null) {
                if (b.getString("pcodetab") != null) {
                    value1 = b.getString("pcodetab");
                } else {
                    Toast.makeText(getActivity(), "brcode Null",
                            Toast.LENGTH_LONG).show();
                }
            }
            ledgerlistview = (ListView) rootView.findViewById(R.id.itemratelistView);

            progress = (ProgressBar) rootView.findViewById(R.id.progressBar1);

            dbd = ph.getReadableDatabase();
            dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");

            mExampleList = new LinkedList<ledgersubclass>();
            listdata = new ArrayList<HashMap<String, Object>>();
            openinbal();

            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BottomTotalShow sop = new BottomTotalShow(totalS,totcrS,totdbS);
                    sop.show(getActivity().getSupportFragmentManager(), sop.getTag());
                }
            });



        } else {
            ImageView image = new ImageView(getActivity());
            image.setImageResource(R.drawable.networking);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Connection");
            builder.setMessage("Check your internet connection and try again.");
            builder.setPositiveButton("OK", null);
            builder.setView(image);
            builder.show();
        }
        return rootView;

    }

    private void openinbal() {
        Call<List<OpeningBal>> call = apiService.getopenbal(value1,"MEY");
        call.enqueue(new Callback<List<OpeningBal>>() {
            @Override
            public void onResponse(Call<List<OpeningBal>> call, Response<List<OpeningBal>> response) {
                try {
                    if (response.code() == 200) {
                        if (response.body().size() == 0){
                        } else {
                            if (response.body().get(0).getOpeningvalue().equals("")){
                                txtloc.setText("");
                            } else {
                                txtloc.setText(response.body().get(0).getOpeningvalue());
                                aa= Float.parseFloat(response.body().get(0).getOpeningvalue());
                                new partylist().execute();
                            }
                        }
                    }else {

                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<List<OpeningBal>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }



    private class partylist extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {

            progress.setVisibility(View.VISIBLE);
            amt = 0;
            db = 0;
            cr = 0;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            progress.setVisibility(View.GONE);
            if (result != "") {

                if (result.trim().equals("[]")) {
                    DisplayMsg("No record found.");
                } else {
                    JSONArray android1;
                    try {

                       /* ledgersubclass mExample1 = new ledgersubclass();
                        mExample1.Rdate = "Date";
                        mExample1.Rled_account = "Particulars";
                        mExample1.RLed_ChqNo = "ChequeNo";
                        mExample1.Rled_vouno = "Voucher";
                        mExample1.RDebit = "Debit";
                        mExample1.RCredit = "Credit";
                        mExample1.Rled_amount = "Amount";

                        mExampleList.add(mExample1);
*/

                        android1 = new JSONArray(result);
                        for (int i1 = 0; i1 < android1.length(); i1++) {

                            JSONObject c = android1.getJSONObject(i1);
                            ledgersubclass mExample = new ledgersubclass();
                            mExample.Rdate = c.getString("date");
                            mExample.Rled_account = (c.getString("led_account"));
                            mExample.RLed_ChqNo = (c.getString("Led_ChqNo"));
                            mExample.Rled_vouno = (c.getString("led_vouno").trim());
                            mExample.RDebit = (c.getString("Debit"));
                            db = db + Float.parseFloat(c.getString("Debit"));
                            mExample.RCredit = (c.getString("Credit"));
                            cr = cr + Float.parseFloat(c.getString("Credit"));
                            amt = amt + Float.parseFloat(c.getString("led_amount"));
                            mExample.Rled_amount = (String.valueOf(Math.round((amt) * 100.00) / 100.00));

                            mExampleList.add(mExample);

                        }

                    } catch (JSONException e) {
                        DisplayMsg(e.getMessage().toString());
                    }


                    adapter = new ledgeradpeter(getContext(), mExampleList);
                    ledgerlistview.setAdapter((ListAdapter) adapter);
                    //String.format("%.0f",Math.round((amt) * 100.00) / 100.00)
                    totalS = String.valueOf(Math.round((amt) * 100.00) / 100.00);
                    totcrS = String.valueOf(Math.round(((cr) * 100.00) / 100.00));
                    totdbS = String.valueOf(Math.round(((db) * 100.00) / 100.00)- (aa));
                    adapter.notifyDataSetChanged();

                }
            } else {
                DisplayMsg("Server is not working");
                //Frledger.this.finish();
            }

        }

        @Override
        protected String doInBackground(String... arg0) {
            if (dbname != null) {

            } else {
                dbname = "";

            }
            try {
                RestClient client = new RestClient("VPLUS/api/Frledger?pcode=" + value1 + "&dbname=MEY");
                String response = "";
                try {
                    response = client.GetRequestexecute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response;
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                DisplayMsg(e.getMessage().toString());
            }


            return null;
        }


    }

    private void DisplayMsg(final String s) {
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Frledgerfragment.this.getActivity(), s.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
