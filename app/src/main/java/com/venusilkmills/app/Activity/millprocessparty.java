package com.venusilkmills.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Model.partyclass;
import com.venusilkmills.app.R;
import com.venusilkmills.app.json.RestClient;
import com.venusilkmills.app.util.ConnectionDetector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class millprocessparty extends AppCompatActivity {

    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    Spinner spinner1;
    ArrayList<HashMap<String, Object>> spinerlistData, listdata, listdata1;
    SimpleAdapter spinersm, sm;
    SimpleAdapter sm1;
    String code, brcode, pname, dbname, menuname, khataname, khatacode, paddress, and_pname, FTYPE;
    ListView partylistview;
    ProgressBar progress;
    TextView loading;
    Object o;
    private int Page = 0;
    private Database_Helper ph;
    private SQLiteDatabase db;
    private Toolbar mToolbar;
    EditText search;
    public CustomAdapter sp;
    ArrayList<partyclass> partyfull = new ArrayList<>();
    private String searchValue = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party);
        ph = new Database_Helper(getApplicationContext());
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        search = (EditText) findViewById(R.id.indexSearch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent i = getIntent();
        menuname = i.getStringExtra("menuname");

        // check for Internet status
        if (isInternetPresent) {

            spinerlistData = new ArrayList<HashMap<String, Object>>();
            listdata = new ArrayList<HashMap<String, Object>>();
            listdata1 = new ArrayList<HashMap<String, Object>>();
            db = ph.getReadableDatabase();
            dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");
            and_pname = ph.GetVal("Select PNAME From andmst where LOGIN='T'");
            FTYPE = ph.GetVal("Select FTYPE From andmst where LOGIN='T'");
            progress = (ProgressBar) findViewById(R.id.progressBar1);
            String urlpath = "CMC/api/Millprocessparty?dbname=" + dbname;
            new getpartylist().execute(urlpath);
        }


        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                sp.filter(text);


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        partylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                partyclass i = partyfull.get(position);
                brcode = i.getPcode();
                pname = i.getPname();
                Intent i1 = new Intent(getApplicationContext(), millprocessactivity.class);
                Bundle b = new Bundle();
                b.putString("brcode", brcode);
                b.putString("pname", pname);
                i1.putExtras(b);
                startActivity(i1);


            }
        });


    }

    private void DisplayMsg(final String s) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(millprocessparty.this, s.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class getpartylist extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progress.setVisibility(View.GONE);

            if (result != "") {

                try {

                    JSONArray android1 = new JSONArray(result);
                    for (int i1 = 0; i1 < android1.length(); i1++) {

                        JSONObject c = android1.getJSONObject(i1);
                        partyclass i = new partyclass();
                        i.setPcode(c.getString("cust_id"));
                        i.setPname(c.getString("cust_name"));
                        partyfull.add(i);

                    }
                } catch (JSONException e) {
                    DisplayMsg(e.getMessage().toString());
                }

                sp = new CustomAdapter(getApplicationContext(), partyfull);
                partylistview.setAdapter(sp);

            } else {
                DisplayMsg("Server is not working");

            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String url = "";
                if (strings.length > 0) {
                    url = strings[0];
                }
                RestClient client = new RestClient(url);
                String response = "";
                try {
                    response = client.GetRequestexecute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response;
            } catch (Exception e) {
                DisplayMsg(e.getMessage());
                return "";
            }

        }
    }

    ////party custome adapter


    public static class CustomAdapter extends ArrayAdapter<partyclass> {
        private ArrayList<partyclass> dataSet;
        ArrayList<partyclass> data_Temp = null;
        Context mContext;

        private static class ViewHolder {
            TextView txtName;

        }

        public CustomAdapter(@NonNull Context context, @NonNull ArrayList<partyclass> data1) {
            super(context, R.layout.customtext, data1);
            this.mContext = context;
            this.dataSet = data1;
            this.data_Temp = new ArrayList<partyclass>();
            this.data_Temp.addAll(dataSet);

        }

        private int lastPosition = -1;

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            partyclass i = getItem(position);
            ViewHolder viewHolder;

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.customtext, parent, false);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt1);


                result = convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            result.startAnimation(animation);
            lastPosition = position;

            viewHolder.txtName.setText(i.getPname());
            return convertView;
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            if (data_Temp.size() == 0) {
                this.data_Temp.addAll(dataSet);
            }
            dataSet.clear();
            if (charText.length() == 0) {
                dataSet.addAll(data_Temp);
            } else {
                for (partyclass wp : data_Temp) {
                    if (wp.getPname().toLowerCase(Locale.getDefault()).contains(charText)) {
                        dataSet.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

}

