package com.newtech.vplus.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.newtech.vplus.Activity.Reportviewactivity;
import com.newtech.vplus.Adapter.CustomAdapter;
import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.Model.Itemclass;
import com.newtech.vplus.R;
import com.newtech.vplus.json.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Itemlist extends Fragment {
    ListView lv1;
    ProgressBar pro1;
    ArrayList<Itemclass> itemfull = new ArrayList<>();
    public CustomAdapter sp;
    String dbname="",khataname,khatacode,p,FTYPE,and_pcode;
    ArrayList<HashMap<String, Object>> listdata, listdata1 = null;
    SimpleAdapter sm1;
    EditText ed1;
    private Database_Helper ph;
    private SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ltem, container, false);
        lv1 = (ListView) v.findViewById(R.id.lv1);
        pro1 = (ProgressBar) v.findViewById(R.id.pro1);
        ed1=(EditText)v.findViewById(R.id.ed1);
        ph = new Database_Helper(getActivity());
        db = ph.getReadableDatabase();
        dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");
        FTYPE=ph.GetVal("Select FTYPE From andmst where LOGIN='T'");
        and_pcode=ph.GetVal("Select PCODE From andmst where LOGIN='T'");

        String urlpath = "CMC/api/Itemlist?dbname="+dbname;
        new getItemlist().execute(urlpath);
        listdata = new ArrayList<HashMap<String, Object>>();
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Itemclass i= itemfull.get(position);
               p=i.getI_name1();
                new khatalist().execute();
            /*    Intent i1=new Intent(getActivity(), Reportviewactivity.class);
                i1.putExtra("ina me",p);
                startActivity(i1);*/
            }
        });
        ed1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = ed1.getText().toString().toLowerCase(Locale.getDefault());
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

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Item List");
    }

    public class getItemlist extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            pro1.setVisibility(View.VISIBLE);

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            pro1.setVisibility(View.GONE);

            if (result != "") {

                try {

                    JSONArray android1 = new JSONArray(result);
                    Itemclass j = new Itemclass();
                    j.setI_name1("ALL");
                    j.setI_code("000");
                    itemfull.add(j);
                    for (int i1 = 0; i1 < android1.length(); i1++) {

                        JSONObject c = android1.getJSONObject(i1);
                        Itemclass i = new Itemclass();
                        i.setI_name1(c.getString("i_name1"));
                        i.setI_code(c.getString("i_code"));
                        itemfull.add(i);

                    }
                } catch (JSONException e) {
                    DisplayMsg(e.getMessage().toString());
                }

                sp = new CustomAdapter(getActivity(), itemfull);
                lv1.setAdapter(sp);

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

    private void DisplayMsg(final String s) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), s.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    private class khatalist extends AsyncTask<String, Void, String> {
        final Dialog passwordDialog = new Dialog(getActivity());
        ListView lv;
        ProgressBar progress;
        Toolbar mToolbar1;

        protected void onPreExecute() {

            passwordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            passwordDialog.setContentView(R.layout.sample);
            lv = (ListView) passwordDialog.findViewById(R.id.listparty);
            progress = (ProgressBar) passwordDialog.findViewById(R.id.progressBar1);
            mToolbar1 = (Toolbar) passwordDialog.findViewById(R.id.toolbar);
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
                        listdata.clear();
                        android1 = new JSONArray(result);

                        for (int i1 = 0; i1 < android1.length(); i1++) {
                            JSONObject c = android1.getJSONObject(i1);
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("a", c.getString("kh_name"));
                            map.put("b", c.getString("kh_code"));
                            listdata.add(map);
                        }
                    } catch (JSONException e) {
                        DisplayMsg(e.getMessage().toString());
                    }

                    sm1 = new SimpleAdapter(getActivity(), listdata, R.layout.list_row, new String[]{"a"}, new int[]{R.id.t1});
                    sm1.notifyDataSetChanged();
                    lv.setAdapter(sm1);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View view2,
                                                int position, long arg3)

                        {
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

                                Intent i1=new Intent(getActivity(), Reportviewactivity.class);
                                i1.putExtra("iname",p);
                                i1.putExtra("cost",khatacode);
                                startActivity(i1);



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
                RestClient client = new RestClient("CMC/api/Khata?dbname="+dbname+"&ftype="+FTYPE+"&code="+and_pcode);
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
