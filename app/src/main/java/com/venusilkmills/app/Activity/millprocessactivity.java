package com.venusilkmills.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.venusilkmills.app.Adapter.Mill_processAdapter;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Model.MillProcess;
import com.venusilkmills.app.R;
import com.venusilkmills.app.json.ApiClient;
import com.venusilkmills.app.json.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class millprocessactivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    Database_Helper ph;
    SQLiteDatabase db;
    String dbname, pcode,p_name;
    private ApiInterface apiService;
    private LinearLayoutManager mLayoutManager;
    ProgressBar progressBar;
    private List<MillProcess> Mill_list = new ArrayList<>();
    Mill_processAdapter mp;
    Bundle b;
    EditText search;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.millprocess);
        recyclerView = (RecyclerView) findViewById(R.id.MILL);
        ph = new Database_Helper(getApplicationContext());
        db = ph.getReadableDatabase();
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        b = getIntent().getExtras();
        if (b != null) {
            if (b.getString("brcode") != null) {
                pcode = b.getString("brcode");
            }  else {
                Toast.makeText(getApplicationContext(), "code Null",
                        Toast.LENGTH_LONG).show();
            }
            if(b.getString("pname")!=null){
                p_name = b.getString("pname");
            }


        }

        search = (EditText) findViewById(R.id.indexSearch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(p_name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");
        getmillprocessreport(dbname, pcode);
    }


    private void getmillprocessreport(String dbname, String pcode) {

        progressBar.setVisibility(View.VISIBLE);
        Call<List<MillProcess>> call = apiService.getprocessreport(pcode, dbname);
        call.enqueue(new Callback<List<MillProcess>>() {
            @Override
            public void onResponse(Call<List<MillProcess>> call, Response<List<MillProcess>> response) {
               try {
                   Mill_list = response.body();
                   progressBar.setVisibility(View.GONE);

                   if (Mill_list.size() == 0) {
                       Toast.makeText(millprocessactivity.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                   } else {
                       mp = new Mill_processAdapter(Mill_list);
                       recyclerView.setAdapter(mp);
                   }
               }
               catch (Exception e){
                   Toast.makeText(millprocessactivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }

            }

            @Override
            public void onFailure(Call<List<MillProcess>> call, Throwable t) {

            }
        });


    }

}
