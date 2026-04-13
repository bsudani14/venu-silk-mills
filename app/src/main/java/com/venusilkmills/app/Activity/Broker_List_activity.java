package com.venusilkmills.app.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.venusilkmills.app.Adapter.Broker_Adapter;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Model.BrokerClass;
import com.venusilkmills.app.R;
import com.venusilkmills.app.json.ApiClient;
import com.venusilkmills.app.json.ApiInterface;
import com.venusilkmills.app.json.RestClient;
import com.venusilkmills.app.util.Util_u;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Broker_List_activity extends AppCompatActivity {

    private final String TAG = "CHALLAN_LIST";
    private EditText indexSearch;
    private LinearLayout spf, spf_notfind;
    private ProgressBar progressBar;
    private RecyclerView challanListRV;
    private LinearLayoutManager linearLayoutManager;
    private Util_u util_u;
    private String dbname;
    private Database_Helper ph;
    private SQLiteDatabase db;
    private ApiInterface apiService;
    private List<BrokerClass> challan_list = new ArrayList<>();
    private List<BrokerClass> challan_list_temp = new ArrayList<>();
    private Broker_Adapter cla;
    private String partyName = null, searchValue = null;
    String khataname, khatacode,FTYPE,and_pcode;
    ArrayList<HashMap<String, Object>> spinerlistData, listdata, listdata1;
    SimpleAdapter sm1;
    String Brcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broker__list);

        challanListRV = (RecyclerView) findViewById(R.id.challanListRV);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        challanListRV.setLayoutManager(linearLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        indexSearch = (EditText) findViewById(R.id.indexSearch);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        spf = (LinearLayout) findViewById(R.id.spf);
        spf_notfind = (LinearLayout) findViewById(R.id.spf_notfind);
        listdata = new ArrayList<HashMap<String, Object>>();
        listdata1 = new ArrayList<HashMap<String, Object>>();

        util_u = new Util_u(this, Broker_List_activity.this);

        ph = new Database_Helper(getApplicationContext());
        db = ph.getReadableDatabase();

        dbname = ph.GetVal("Select dbname from andmst where LOGIN='T'");
        FTYPE=ph.GetVal("Select FTYPE From andmst where LOGIN='T'");
        and_pcode=ph.GetVal("Select PCODE From andmst where LOGIN='T'");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        spf.setVisibility(View.GONE);
        spf_notfind.setVisibility(View.GONE);


        getChallanList(partyName);

        indexSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (util_u.isNetworkAvailable()) {
                    searchValue = indexSearch.getText().toString().toLowerCase(Locale.getDefault());
                    if (searchValue == null || searchValue == "" || searchValue.length() == 0 || searchValue == " ") {
                        spf.setVisibility(View.GONE);
                        spf_notfind.setVisibility(View.VISIBLE);
                        challan_list.clear();
                        if (challan_list_temp.size() != 0) {
                            challan_list.addAll(challan_list_temp);
                            cla.notifyDataSetChanged();
                          spf_notfind.setVisibility(View.GONE);
                        }
                    } else {
                        cla.filter(searchValue);
                        spf_notfind.setVisibility(View.GONE);
                        if (challan_list.size() == 0) {
                            spf_notfind.setVisibility(View.VISIBLE);
                        }

                    }
                } else {
                    util_u.showNoInternetDialog();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    private void getChallanList(String partyName1) {
        indexSearch.setEnabled(false);
        challan_list.clear();
        challan_list_temp.clear();
        spf.setVisibility(View.GONE);
        spf_notfind.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<List<BrokerClass>> call = apiService.getbrokerlist(dbname);
        call.enqueue(new Callback<List<BrokerClass>>() {
            @Override
            public void onResponse(Call<List<BrokerClass>> call, Response<List<BrokerClass>> response) {
                challan_list = response.body();
                challan_list_temp.addAll(challan_list);
                progressBar.setVisibility(View.GONE);
                indexSearch.setEnabled(true);
                if (challan_list.size() == 0) {
                    challan_list.clear();
                    spf_notfind.setVisibility(View.VISIBLE);
                    challanListRV.setAdapter(null);
                } else {
                    spf_notfind.setVisibility(View.GONE);
                    cla = new Broker_Adapter(challan_list, getApplicationContext(), new Broker_Adapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BrokerClass klm1) {
                            new khatalist().execute();
                            Brcode=klm1.getBr_code();

                        }
                    });
                    challanListRV.setAdapter(cla);
                }
            }

            @Override
            public void onFailure(Call<List<BrokerClass>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private class khatalist extends AsyncTask<String, Void, String> {
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(Broker_List_activity.this);
        View view1 = inflater.inflate(R.layout.sample, null);
        ListView lv;
        ProgressBar progress;
        Toolbar mToolbar1;
        AlertDialog passwordDialog = null;

        protected void onPreExecute() {


            final View view = inflater.inflate(R.layout.sample, null);

            builder.setView(view);

            passwordDialog = builder.create();
            EditText sampleinputSearch = view.findViewById(R.id.sampleinputSearch);
            sampleinputSearch.setVisibility(View.GONE);
            lv = (ListView) view.findViewById(R.id.listparty);
            progress = (ProgressBar) view.findViewById(R.id.progressBar1);
            mToolbar1 = (Toolbar) view.findViewById(R.id.toolbar);
            mToolbar1.setTitle("Cost Center");
            mToolbar1.setTitleTextColor(Color.WHITE);
            progress.setVisibility(View.VISIBLE);
            passwordDialog.show();
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
                        listdata1.clear();
                        android1 = new JSONArray(result);

                        for (int i1 = 0; i1 < android1.length(); i1++) {
                            JSONObject c = android1.getJSONObject(i1);
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("a", c.getString("kh_name"));
                            map.put("b", c.getString("kh_code"));
                            listdata1.add(map);
                        }
                    } catch (JSONException e) {
                        DisplayMsg(e.getMessage().toString());
                    }

                    sm1 = new SimpleAdapter(getApplicationContext(), listdata1, R.layout.list_row, new String[]{"a"}, new int[]{R.id.t1});
                    sm1.notifyDataSetChanged();
                    lv.setAdapter(sm1);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View view2,
                                                int position, long arg3) {
                            ListView lv = (ListView) passwordDialog
                                    .findViewById(R.id.listparty);
                            String grpcode = null;
                            Object o = lv.getItemAtPosition(position);
                            String strparty = (String) o.toString();

                            try {
                                strparty = strparty.substring(1, strparty.length() - 1);           //remove curly brackets
                                String[] keyValuePairs = strparty.split(",");              //split the string to creat key-value pairs
                                Map<String, String> map = new HashMap<String, String>();
                                for (String pair : keyValuePairs)                        //iterate over the pais
                                {
                                    String[] entry = pair.split("=");                   //split the pairs to get key and value
                                    map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap
                                    if (entry[0].trim().equals("a")) {
                                        khataname = entry[1].trim();
                                    }

                                    if (entry[0].trim().equals("b")) {
                                        khatacode = entry[1].trim();
                                    }
                                }

                                Intent i = new Intent(getApplicationContext(), Broker_wise_outstanding_Report.class);
                                Bundle b = new Bundle();
                                b.putString("kname",khataname);
                                b.putString("kcode",khatacode);
                                b.putString("brcode",Brcode);
                                i.putExtras(b);
                                startActivity(i);
                            } catch (Exception e) {
                                DisplayMsg(e.getMessage());
                            }
                            passwordDialog.dismiss();

                        }


                    });
                }

            } else {
                DisplayMsg("Server is not working");

            }

        }


        protected String doInBackground(String... arg0) {
            if (dbname != null) {

            } else {
                dbname = "";

            }
            try {
                RestClient client = new RestClient("CMC/api/Khata?dbname=" + dbname + "&ftype=" + FTYPE + "&code=" + and_pcode);
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
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Broker_List_activity.this, s.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
