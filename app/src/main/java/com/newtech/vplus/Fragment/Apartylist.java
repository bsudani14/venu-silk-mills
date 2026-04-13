package com.newtech.vplus.Fragment;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.newtech.vplus.Activity.Dispatch_preview;
import com.newtech.vplus.Activity.OrderList;
import com.newtech.vplus.Activity.newpcodedetails;
import com.newtech.vplus.Activity.ordermenu;
import com.newtech.vplus.Adapter.CustomAdapterAdd;
import com.newtech.vplus.Adapter.party_finder_adapter;
import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.Model.addressclass;
import com.newtech.vplus.Model.party_data_end;
import com.newtech.vplus.Model.partyclass;
import com.newtech.vplus.R;
import com.newtech.vplus.json.ApiClient;
import com.newtech.vplus.json.ApiInterface;
import com.newtech.vplus.json.RestClient;
import com.newtech.vplus.util.ConnectionDetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Apartylist extends AppCompatActivity {
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    Spinner spinner1;
    ArrayList<HashMap<String, Object>> spinerlistData, listdata, listdata1;
    SimpleAdapter spinersm, sm;
    SimpleAdapter sm1;
    String code, brcode, pname, dbname, menuname, khataname, khatacode, paddress,and_pname,FTYPE,and_pcode,pco,pna;
    ListView partylistview;
    ProgressBar progress;
    private ApiInterface apiService;
    TextView loading;
    Object o;
    private int Page = 0;
    private Database_Helper ph;
    private LinearLayout spf, spf_notfind;
    private SQLiteDatabase db;
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    EditText search;
    private String PartyType = "DEB";
    private Bundle bundle;
    private ProgressBar progressBar;
    ArrayList<partyclass> partyfull = new ArrayList<>();
    private String searchValue = null;
    private EditText indexSearch;
    String ftype,pcode,username,mobileno;
    private party_finder_adapter pfa;
    private List<party_data_end> party_list = new ArrayList<>();
    ArrayList<addressclass> address = new ArrayList<>();
    public CustomAdapterAdd sp1;
    String urlpath;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party);
        ph = new Database_Helper(getApplicationContext());
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        search = (EditText) findViewById(R.id.indexSearch);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.partyListRV);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        indexSearch = (EditText) findViewById(R.id.indexSearch);
        apiService = ApiClient.getClient().create(ApiInterface.class);


        spf = (LinearLayout) findViewById(R.id.spf);
        spf_notfind = (LinearLayout) findViewById(R.id.spf_notfind);
       /* setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
*/
        Intent i = getIntent();
        menuname = i.getStringExtra("menuname");

        // check for Internet status
        if (isInternetPresent) {

            spinerlistData = new ArrayList<HashMap<String, Object>>();
            listdata = new ArrayList<HashMap<String, Object>>();
            listdata1 = new ArrayList<HashMap<String, Object>>();

            ftype = ph.GetVal("Select ftype From andmst where LOGIN='T'");
            dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");
            mobileno = ph.GetVal("Select MOBILENO From andmst where LOGIN='T'");

            username = ph.GetVal("Select PNAME From andmst where LOGIN='T'");
            pcode = ph.GetVal("Select PCODE From andmst where LOGIN='T'");


            partySearch("ALL");
            indexSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String text = indexSearch.getText().toString().toLowerCase(Locale.getDefault());
                    pfa.filter(text);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }

    }

    public void partySearch(String PartyName) {
        party_list.clear();
      //  spf.setVisibility(View.GONE);
      //  spf_notfind.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        indexSearch.setEnabled(false);
        try {
            Call<List<party_data_end>> call = apiService.getpartylist("MEY", PartyType,PartyName,"");
            call.enqueue(new Callback<List<party_data_end>>() {
                @Override
                public void onResponse(Call<List<party_data_end>> call, Response<List<party_data_end>> response) {
                    party_list = response.body();
                    progressBar.setVisibility(View.GONE);
                    if (party_list.size() == 0) {
                        party_list.clear();
                       // spf_notfind.setVisibility(View.VISIBLE);
                        indexSearch.setEnabled(true);
                    } else {
                        //spf_notfind.setVisibility(View.GONE);
                        indexSearch.setEnabled(true);
                            pfa = new party_finder_adapter(party_list, Apartylist.this, new party_finder_adapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(party_data_end pda1) {

                                    pco=pda1.getPcode();
                                    pna=pda1.getPname();

                                    if(menuname.equals("dispatch")) {
                                        Intent i4 = new Intent(getApplicationContext(), Dispatch_preview.class);
                                        Bundle b = new Bundle();
                                        b.putString("brcode", brcode);
                                        b.putString("pname", pname);
                                        i4.putExtras(b);
                                        startActivity(i4);
                                    }
                                    else if(menuname.equals("order")){
                                       // new addresmenu().execute();

                                       Intent order = new Intent(getApplicationContext(), ordermenu.class);
                                        bundle = new Bundle();
                                        bundle.putString("p_code", pda1.getPcode());
                                        bundle.putString("p_name", pda1.getPname());
                                        order.putExtras(bundle);
                                        startActivity(order);
                                    }

                                    else if(menuname.equals("orderlist")){
                                        Intent order1 = new Intent(getApplicationContext(), OrderList.class);
                                        bundle = new Bundle();
                                        bundle.putString("p_code", pda1.getPcode());
                                        bundle.putString("p_name", pda1.getPname());
                                        order1.putExtras(bundle);
                                        startActivity(order1);
                                    }

                                    else{

                                    Intent intent = new Intent(getApplicationContext(), newpcodedetails.class);
                                        bundle = new Bundle();
                                        bundle.putString("p_code", pda1.getPcode());
                                        bundle.putString("p_name", pda1.getPname());
                                        bundle.putString("p_address",pda1.getPaddress());
                                        bundle.putString("p_gst_in",pda1.getGstno());
                                        bundle.putString("p_mobile",pda1.getPmobile());
                                        bundle.putString("p_brcode",pda1.getP_brcode());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        recyclerView.setAdapter(pfa);
                }

                @Override
                public void onFailure(Call<List<party_data_end>> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void DisplayMsg(final String s) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Apartylist.this, s.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class addresmenu extends AsyncTask<String, Void, String> {
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(Apartylist.this);
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
            mToolbar1.setTitle("Select Address");
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

                            addressclass i11 = new addressclass();
                            i11.setAddress(c.getString("address"));
                            // i.setMobileno(c.getString("mobileno"));
                            address.add(i11);

                            /*HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("a", c.getString("address"));
                            map.put("b", c.getString("mobileno"));
                            listdata1.add(map);*/
                        }
                    } catch (JSONException e) {
                        DisplayMsg(e.getMessage().toString());
                    }

                    sp1 = new CustomAdapterAdd(getApplicationContext(), address);
                    lv.setAdapter(sp1);


                 /*   sm1 = new SimpleAdapter(getApplicationContext(), listdata1, R.layout.list_row, new String[]{"a"}, new int[]{R.id.t1});
                    sm1.notifyDataSetChanged();
                    lv.setAdapter(sm1);*/




                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View view2,
                                                int position, long arg3)

                        {

                          /*
                            ListView lv = (ListView) passwordDialog
                                    .findViewById(R.id.listparty);
                            String grpcode = null;
                            Object o = lv.getItemAtPosition(position);
                            String strparty = (String) o.toString();*/

                            try {
                                addressclass i1= address.get(position);

                                paddress=i1.getAddress();
                                /*strparty = strparty.substring(1, strparty.length() - 1);           //remove curly brackets
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
                                }*/

                                Intent order = new Intent(getApplicationContext(), ordermenu.class);
                                bundle = new Bundle();
                                bundle.putString("p_code",pco );
                                bundle.putString("p_name",pna);
                                order.putExtras(bundle);
                                startActivity(order);

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
                RestClient client = new RestClient("URJAEXIM/api/MultiBillAddress?dbname="+dbname+"&pname="+pname);
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

}
