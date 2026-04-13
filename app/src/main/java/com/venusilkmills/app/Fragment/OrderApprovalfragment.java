package com.venusilkmills.app.Fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.venusilkmills.app.Adapter.OrderListApprovalAdapter;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Model.Closeorder;
import com.venusilkmills.app.Model.orderlistclass;
import com.venusilkmills.app.R;
import com.venusilkmills.app.json.ApiClient;
import com.venusilkmills.app.json.ApiInterface;
import com.venusilkmills.app.json.RestClient;
import com.venusilkmills.app.util.ConnectionDetector;
import com.venusilkmills.app.util.EndlessRecyclerOnScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderApprovalfragment extends Fragment implements OrderListApprovalAdapter.CardViewListner {
    private Toolbar mToolbar;
    private ArrayList<orderlistclass> orderlists = new ArrayList<orderlistclass>();
    private RecyclerView listView;
    private OrderListApprovalAdapter mAdapter = null;
    private RecyclerView.LayoutManager mLayoutManager;
    private int Page = 0;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    private Database_Helper ph;
    SQLiteDatabase db;
    String pcode;
    RadioGroup rg, rg1,rg2;
    String radiostring, Loginpcode, sftype, dbname;
    EditText search;
    ProgressBar pb, pb1;
    private ApiInterface apiService;
    SwipeRefreshLayout mySwipeRefreshLayout;
    String Reason,and_ordno,type,ordtype;

    public OrderApprovalfragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.orderlismain, container, false);
        setHasOptionsMenu(true);
        type="OREDERAPPROVAL";
        ordtype="SALE";
        radiostring = ordtype+type;
        ph = new Database_Helper(getActivity());
        cd = new ConnectionDetector(getActivity());
        apiService = ApiClient.getClient().create(ApiInterface.class);

        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {

            db = ph.getReadableDatabase();
            Cursor getchat1 = db.rawQuery("SELECT PCODE From andmst", null);
            getchat1.moveToFirst();

            if (getchat1.getCount() > 0) {
                pcode = getchat1.getString(0);
            }
            listView = (RecyclerView) rootView.findViewById(R.id.my_recycler_cardview);
            rg = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
            rg1 = (RadioGroup) rootView.findViewById(R.id.radiogroup2);
            rg2= (RadioGroup) rootView.findViewById(R.id.radioGroup3);
            search = (EditText) rootView.findViewById(R.id.editsearchorder);
            pb = (ProgressBar) rootView.findViewById(R.id.progressBar1);
            pb1 = (ProgressBar) rootView.findViewById(R.id.progressBar2);
            mySwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
            pb.setVisibility(View.GONE);
            listView.setHasFixedSize(true);
            rg.setVisibility(View.GONE);
            mLayoutManager = new LinearLayoutManager(getActivity());
            listView.setLayoutManager(mLayoutManager);
            orderlists = new ArrayList<orderlistclass>();
            mAdapter = new OrderListApprovalAdapter(getActivity(), orderlists, OrderApprovalfragment.this,type);
            listView.setAdapter(mAdapter);
            Loginpcode = ph.GetVal("Select PCODE From andmst Where LOGIN='T'");
            sftype = ph.GetVal("Select FTYPE From andmst Where PCODE='" + Loginpcode + "'");
            db = ph.getReadableDatabase();
            dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");
            if (dbname != null) {

            } else {
                dbname = "";

            }
            mySwipeRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            doYourUpdate();
                        }
                    }
            );


            listView.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) mLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    mAdapter.notifyDataSetChanged();
                    new addresslist().execute("CMC/api/pendingorder?id=" + String.valueOf(current_page) + "&ordervalue=" + radiostring + "&Ftype=" + sftype + "&pcode=" + Loginpcode + "&dbname=" + dbname);
                }
            });
            search.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    new searchaddresslist().execute("CMC/api/searchoendingorder?id=1&ordervalue=" + radiostring + "&Ftype=" + sftype + "&pcode=" + Loginpcode + "&pname=" + search.getText().toString().toLowerCase(Locale.getDefault()) + "&dbname=" + dbname);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub

                }
            });
            rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    // TODO Auto-generated method stub
                    if (checkedId == R.id.APP) {
                        type="OREDERAPPROVAL";
                        radiostring = ordtype+type;
                        orderlists.clear();
                        mAdapter.notifyDataSetChanged();
                        orderlists = new ArrayList<orderlistclass>();
                        mAdapter = new OrderListApprovalAdapter(getActivity(), orderlists, OrderApprovalfragment.this,type);
                        listView.setAdapter(mAdapter);
                        new addresslist().execute("CMC/api/pendingorder?id=1&ordervalue=" + radiostring + "&Ftype=" + sftype + "&pcode=" + Loginpcode + "&dbname=" + dbname);

                    } else if (checkedId == R.id.UNAPP) {
                        type="OREDERUNAPPROVAL";
                        radiostring = ordtype+type;
                        orderlists.clear();
                        mAdapter.notifyDataSetChanged();
                        orderlists = new ArrayList<orderlistclass>();
                        mAdapter = new OrderListApprovalAdapter(getActivity(), orderlists, OrderApprovalfragment.this,type);
                        listView.setAdapter(mAdapter);
                        new addresslist().execute("CMC/api/pendingorder?id=1&ordervalue=" + radiostring + "&Ftype=" + sftype + "&pcode=" + Loginpcode + "&dbname=" + dbname);
                    }

                }
            });


            rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    // TODO Auto-generated method stub
                    if (checkedId == R.id.radiosal) {
                        ordtype="SALE";
                        radiostring = ordtype+type;
                        orderlists.clear();
                        mAdapter.notifyDataSetChanged();
                        orderlists = new ArrayList<orderlistclass>();
                        mAdapter = new OrderListApprovalAdapter(getActivity(), orderlists, OrderApprovalfragment.this,type);
                        listView.setAdapter(mAdapter);
                        new addresslist().execute("CMC/api/pendingorder?id=1&ordervalue=" + radiostring + "&Ftype=" + sftype + "&pcode=" + Loginpcode + "&dbname=" + dbname);

                    } else if (checkedId == R.id.radiojob) {
                        ordtype="JOB";
                        radiostring = ordtype+type;
                        orderlists.clear();
                        mAdapter.notifyDataSetChanged();
                        orderlists = new ArrayList<orderlistclass>();
                        mAdapter = new OrderListApprovalAdapter(getActivity(), orderlists, OrderApprovalfragment.this,type);
                        listView.setAdapter(mAdapter);
                        new addresslist().execute("CMC/api/pendingorder?id=1&ordervalue=" + radiostring + "&Ftype=" + sftype + "&pcode=" + Loginpcode + "&dbname=" + dbname);
                    }

                }
            });

            new addresslist().execute("CMC/api/pendingorder?id=1&ordervalue=" + radiostring + "&Ftype=" + sftype + "&pcode=" + Loginpcode + "&dbname=" + dbname);
        } else {
            ImageView image = new ImageView(getActivity());
            image.setImageResource(R.drawable.nointernet);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Connection");
            builder.setMessage("Check your internet connection and try again.");
            builder.setPositiveButton("OK", null);
            builder.setView(image);
            builder.show();

        }
        return rootView;
    }

    private void doYourUpdate() {
        // TODO implement a refresh
        mAdapter.notifyDataSetChanged();
        mySwipeRefreshLayout.setRefreshing(false); // Disables the refresh icon
    }

    private class addresslist extends AsyncTask<String, Void, String> {
        //private SpotsDialog pDialog;

        protected void onPreExecute() {
            pb1.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pb1.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();

            try {
                if (result != "") {
                    if (result.trim().equals("[]")) {
                        DisplayMsg("No record found.");
                    } else {
                        int Val = 0;
                        if (orderlists.size() > 0) {
                            Val = orderlists.size();
                        }

                        JSONArray android1 = new JSONArray(result);
                        for (int i1 = Val; i1 < android1.length(); i1++) {
                            JSONObject c = android1.getJSONObject(i1);
                            orderlistclass movie = new orderlistclass();
                            movie.setono(c.getString("Tcode"));
                            movie.setopnamet(c.getString("Tparty"));
                            movie.setobrname(c.getString("Tbrname"));
                            movie.setosp(c.getString("Tsp"));
                            movie.setodt(c.getString("Tdate"));
                            movie.setoqty(c.getString("TQty"));
                            movie.setocr(c.getString("Tcrday"));
                            movie.setoitem(c.getString("TItem"));
                            movie.setOreason(c.getString("oreason"));
                            movie.setT_LOCKTERMS(c.getString("T_LOCKTERMS"));
                            orderlists.add(movie);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    DisplayMsg("No order found.");

                }

            } catch (JSONException e) {
                DisplayMsg(e.getMessage().toString());
            }


        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String url = "";
                if (arg0.length > 0) {
                    url = arg0[0];
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
                Log.e("ERROR", e.getMessage());
                //DisplayMsg(e.getMessage().toString());
            }
            return null;

        }

    }

    private class searchaddresslist extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pb.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();

            try {
                if (result != "") {
                    if (result.trim().equals("[]")) {
                        DisplayMsg("No record found.");
                    } else {
                        orderlists.clear();
                        int Val = 0;
                        if (orderlists.size() > 0) {
                            Val = orderlists.size();
                        }

                        JSONArray android1 = new JSONArray(result);
                        for (int i1 = Val; i1 < android1.length(); i1++) {
                            JSONObject c = android1.getJSONObject(i1);
                            orderlistclass moviesearch = new orderlistclass();
                            moviesearch.setono(c.getString("Tcode"));
                            moviesearch.setopnamet(c.getString("Tparty"));
                            moviesearch.setobrname(c.getString("Tbrname"));
                            moviesearch.setosp(c.getString("Tsp"));
                            moviesearch.setodt(c.getString("Tdate"));
                            moviesearch.setoqty(c.getString("TQty"));
                            moviesearch.setocr(c.getString("Tcrday"));
                            moviesearch.setoitem(c.getString("TItem"));
                            moviesearch.setOreason(c.getString("oreason"));
                            moviesearch.setT_LOCKTERMS(c.getString("T_LOCKTERMS"));
                            orderlists.add(moviesearch);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    DisplayMsg("No order found.");

                }

            } catch (JSONException e) {
                DisplayMsg(e.getMessage().toString());
            }


        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String url = "";
                if (arg0.length > 0) {
                    url = arg0[0];
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
                Log.e("ERROR", e.getMessage());
                //DisplayMsg(e.getMessage().toString());
            }
            return null;

        }

    }


    private void DisplayMsg(final String s) {
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(OrderApprovalfragment.this.getActivity(), s.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemAddedListner(View view, orderlistclass ItemObj,
                                   String Ftype, final int pos) {
        // TODO Auto-generated method stub

        if (Ftype.equals("APPROVAL")) {
            Call<Closeorder> call = apiService.ApprovedConfirm("application/json", dbname, ItemObj.getono(), sftype);
            call.enqueue(new Callback<Closeorder>() {
                @Override
                public void onResponse(Call<Closeorder> call, Response<Closeorder> response) {
                    Closeorder co = response.body();
                    if (!co.getResult().equals("Success")) {
                        Toast.makeText(getActivity(), "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                    } else {
                        orderlists.remove(pos);
                        mAdapter.notifyItemRemoved(pos);
                        mAdapter.notifyItemRangeChanged(pos, mAdapter.getItemCount());
                    }
                }

                @Override
                public void onFailure(Call<Closeorder> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("APPROVAL LIST : ", t.toString());
                }
            });

        } else if (Ftype.equals("details")) {
            String s = ItemObj.getono();
            BottomOrderlistdetails sop = new BottomOrderlistdetails(s);
            sop.show(getActivity().getSupportFragmentManager(), sop.getTag());
        } else if (Ftype.equals("UNAPPROVAL")) {
            and_ordno = ItemObj.getono();
            final AlertDialog alertDialog;
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
            dialogBuilder.setView(dialogView);

            final EditText editText = (EditText) dialogView.findViewById(R.id.res);
            Button CAN=(Button)dialogView.findViewById(R.id.CAN);
            Button save=(Button)dialogView.findViewById(R.id.save);
            alertDialog = dialogBuilder.create();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editText.getText().toString().equals("")){
                        DisplayMsg("ENTER VALUE");

                    }
                    else{
                        //adpi call
                        Call<Closeorder> call = apiService.UNApprovedConfirm("application/json", dbname, and_ordno, sftype,editText.getText().toString());
                        call.enqueue(new Callback<Closeorder>() {
                            @Override
                            public void onResponse(Call<Closeorder> call, Response<Closeorder> response) {
                                Closeorder co = response.body();
                                if (!co.getResult().equals("Success")) {
                                    Toast.makeText(getActivity(), "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                                } else {
                                    editText.setText("");
                                    alertDialog.dismiss();
                                    orderlists.remove(pos);
                                    mAdapter.notifyItemRemoved(pos);
                                    mAdapter.notifyItemRangeChanged(pos, mAdapter.getItemCount());

                                }
                            }

                            @Override
                            public void onFailure(Call<Closeorder> call, Throwable t) {
                                // Log error here since request failed
                                Log.e("APPROVAL LIST : ", t.toString());
                            }
                        });
                    }
                }
            });
            CAN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();


        }


    }


}


