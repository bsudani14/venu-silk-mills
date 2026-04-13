package com.newtech.vplus.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.Model.Khataclass;
import com.newtech.vplus.Model.Result_model;
import com.newtech.vplus.Model.Salespersonclass;
import com.newtech.vplus.Model.Staffmodel;
import com.newtech.vplus.Model.Stockpermission;
import com.newtech.vplus.Model.menu_list_model;
import com.newtech.vplus.R;
import com.newtech.vplus.json.ApiClient;
import com.newtech.vplus.json.ApiInterface;
import com.newtech.vplus.util.Util_u;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.newtech.vplus.util.EndlessRecyclerOnScrollListener.TAG;

public class Costcenterperfragment extends Fragment {
    ApiInterface apiService;
    RecyclerView rec;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    List<Khataclass> khata_list = new ArrayList<>();
   // Khataadapter adapter;
    private menu_list_model menuListModel;
    private List<Switch> SwitchmenuList = new ArrayList<>();
    String dbname, selectedUser, selectedUsercode, mobileno;
    private Database_Helper ph;
    private SQLiteDatabase db;
    Spinner UsersSpinner;
    private Util_u util_u;

    private List<Staffmodel> staffList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> userListData;
    private SimpleAdapter simpleAdapter;
    Button save;
    Stockpermission item;
    String boxvalue;
    public ArrayList<Stockpermission> postpermissiondata = new ArrayList<Stockpermission>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.costcenterpermissionfragement, container, false);

      //  rec = (RecyclerView) rootView.findViewById(R.id.rec);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        ph = new Database_Helper(getActivity());
        db = ph.getReadableDatabase();
        dbname = ph.GetVal("Select dbname From andmst where LOGIN='T'");
       // rec.setHasFixedSize(true);
      /*  linearLayoutManager = new LinearLayoutManager(getContext());
        rec.setLayoutManager(linearLayoutManager);*/
        apiService = ApiClient.getClient().create(ApiInterface.class);
        UsersSpinner = new Spinner(getActivity());
        UsersSpinner = (Spinner) rootView.findViewById(R.id.sp1);
        save = (Button) rootView.findViewById(R.id.save);
        userListData = new ArrayList<HashMap<String, Object>>();


        util_u = new Util_u(getActivity(),Costcenterperfragment.this.getActivity());
        SwitchmenuList.add((Switch) rootView.findViewById(R.id.Dispatch_menu_per));
        SwitchmenuList.add((Switch) rootView.findViewById(R.id.ledger_bill_menu_per));
        SwitchmenuList.add((Switch) rootView.findViewById(R.id.orders_menu_per));
        SwitchmenuList.add((Switch) rootView.findViewById(R.id.stock_menu_per));
        SwitchmenuList.add((Switch) rootView.findViewById(R.id.dispatch_List_menu_per));
        SwitchmenuList.add((Switch) rootView.findViewById(R.id.order_list_menu_per));
        SwitchmenuList.add((Switch) rootView.findViewById(R.id.log_out_menu_per));
      /*  SwitchmenuList.add((Switch) findViewById(R.id.PurchaseInward));
        SwitchmenuList.add((Switch) findViewById(R.id.issue_menu));
        SwitchmenuList.add((Switch) findViewById(R.id.BeamStockEntry));
        SwitchmenuList.add((Switch) findViewById(R.id.BeamChallanEntry));
        SwitchmenuList.add((Switch) findViewById(R.id.BeamMasterEntry));
        SwitchmenuList.add((Switch) findViewById(R.id.CreelMaster));
        SwitchmenuList.add((Switch) findViewById(R.id.CreelProduction));
        SwitchmenuList.add((Switch) findViewById(R.id.FaultMaster));
        SwitchmenuList.add((Switch) findViewById(R.id.StockChecking));
        SwitchmenuList.add((Switch) findViewById(R.id.sareereturn));
        SwitchmenuList.add((Switch) findViewById(R.id.yarn_machine));*/
        SwitchmenuList.add((Switch) rootView.findViewById(R.id.menu_allocation_menu));
        getUsers();


        UsersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                arg1.setSelected(true);
                Object o = UsersSpinner.getSelectedItem();
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
                            selectedUser = entry[1].trim();
                        } else if (entry[0].trim().equals("b")) {
                            mobileno = entry[1].trim();
                        }

                    }

                    if (selectedUser != null && !selectedUser.equals("Select user")) {
                        progressBar.setVisibility(View.VISIBLE);
                        Call<List<menu_list_model>> call = apiService.getUserWiseMenu(dbname, selectedUser, mobileno);
                        call.enqueue(new Callback<List<menu_list_model>>() {
                            @Override
                            public void onResponse(Call<List<menu_list_model>> call, Response<List<menu_list_model>> response) {
                                progressBar.setVisibility(View.GONE);
                                if (response.body().size() == 0) {
                                    // util_u.showToast("NO DATA FOUND");
                                    SwitchmenuList.get(0).setChecked(false);
                                    SwitchmenuList.get(1).setChecked(false);
                                    SwitchmenuList.get(2).setChecked(false);
                                    SwitchmenuList.get(3).setChecked(false);
                                    SwitchmenuList.get(4).setChecked(false);
                                    SwitchmenuList.get(5).setChecked(false);
                                    SwitchmenuList.get(6).setChecked(false);
                                  /*  SwitchmenuList.get(7).setChecked(false);
                                    SwitchmenuList.get(8).setChecked(false);
                                    SwitchmenuList.get(9).setChecked(false);
                                    SwitchmenuList.get(10).setChecked(false);
                                    SwitchmenuList.get(11).setChecked(false);
                                    SwitchmenuList.get(12).setChecked(false);
                                    SwitchmenuList.get(13).setChecked(false);
                                    SwitchmenuList.get(14).setChecked(false);
                                    SwitchmenuList.get(15).setChecked(false);
                                    SwitchmenuList.get(16).setChecked(false);
                                    SwitchmenuList.get(17).setChecked(false);
                                    SwitchmenuList.get(18).setChecked(false);
                                    SwitchmenuList.get(19).setChecked(false);
                                    SwitchmenuList.get(20).setChecked(false);
                                    SwitchmenuList.get(21).setChecked(false);
                                    SwitchmenuList.get(22).setChecked(false);
                                    SwitchmenuList.get(23).setChecked(false);
                                    SwitchmenuList.get(24).setChecked(false);
                                    SwitchmenuList.get(25).setChecked(false);
                                    SwitchmenuList.get(26).setChecked(false);*/

                                } else {
                                    menu_list_model menuListModel = response.body().get(0);
                                    SwitchmenuList.get(0).setChecked(menuListModel.isA_LEDGERBILL());
                                    SwitchmenuList.get(1).setChecked(menuListModel.isA_ORDER());
                                    SwitchmenuList.get(2).setChecked(menuListModel.isA_ORDERDETAILS());
                                    SwitchmenuList.get(3).setChecked(menuListModel.isA_CHECKINGMODULE());
                                    SwitchmenuList.get(4).setChecked(menuListModel.isA_CHECKINGDETAILS());
                                    SwitchmenuList.get(5).setChecked(menuListModel.isA_AVGBOOK());
                                    //SwitchmenuList.get(6).setChecked(menuListModel.isA_PROCESSOUTWARD());
                                  /*  SwitchmenuList.get(7).setChecked(menuListModel.isA_PROCESSINWARD());
                                    SwitchmenuList.get(8).setChecked(menuListModel.isA_PROCESSFOLDING());
                                    SwitchmenuList.get(9).setChecked(menuListModel.isA_BUTTACHECKING());
                                    SwitchmenuList.get(10).setChecked(menuListModel.isA_MILLCHECKING());
                                    SwitchmenuList.get(11).setChecked(menuListModel.isA_SAREECUTTING());
                                    SwitchmenuList.get(12).setChecked(menuListModel.isA_MASTERQRCODE());
                                    SwitchmenuList.get(13).setChecked(menuListModel.isA_SAREESTOCKENTRY());
                                    SwitchmenuList.get(14).setChecked(menuListModel.isA_DISPATCHENTRY());
                                    SwitchmenuList.get(15).setChecked(menuListModel.isA_PURCHASEINWARD());
                                    SwitchmenuList.get(16).setChecked(menuListModel.isA_ISSUE());
                                    SwitchmenuList.get(17).setChecked(menuListModel.isA_BEAMSTOCK());
                                    SwitchmenuList.get(18).setChecked(menuListModel.isA_BEAMCHALLAN());
                                    SwitchmenuList.get(19).setChecked(menuListModel.isA_BEAMMASTER());
                                    SwitchmenuList.get(20).setChecked(menuListModel.isA_CREELMASTER());
                                    SwitchmenuList.get(21).setChecked(menuListModel.isA_CREELPRODUCTION());
                                    SwitchmenuList.get(22).setChecked(menuListModel.isA_FAULTMASTER());
                                    SwitchmenuList.get(23).setChecked(menuListModel.isA_STOCKCHECKING());
                                    SwitchmenuList.get(24).setChecked(menuListModel.isA_SAREERETURN());
                                    SwitchmenuList.get(25).setChecked(menuListModel.isA_YARN_MACHINE());*/
                                    SwitchmenuList.get(6).setChecked(menuListModel.isA_MENUALLOCATION());

                                }
                            }

                            @Override
                            public void onFailure(Call<List<menu_list_model>> call, Throwable t) {
                                Log.e("MENUALLOCATION", t.toString());
                            }
                        });
                    } else {
                           util_u.showToast("Please Select User");
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                //getdata();
               // save.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


     /*   save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdata();
            }
        });
        return rootView;*/
        return rootView;
    }

    public void setUserMenu() {
        menuListModel = new menu_list_model();
        if (selectedUser != null && !selectedUser.equals("Select user")) {
            menuListModel.setA_EMPNAME(selectedUser);
            menuListModel.setA_MOBILENO(mobileno);
            menuListModel.setA_LEDGERBILL(SwitchmenuList.get(0).isChecked());
            menuListModel.setA_ORDER(SwitchmenuList.get(1).isChecked());
            menuListModel.setA_ORDERDETAILS(SwitchmenuList.get(2).isChecked());
            menuListModel.setA_CHECKINGMODULE(SwitchmenuList.get(3).isChecked());
            menuListModel.setA_CHECKINGDETAILS(SwitchmenuList.get(4).isChecked());
            menuListModel.setA_AVGBOOK(SwitchmenuList.get(5).isChecked());
          /*  menuListModel.setA_PROCESSOUTWARD(SwitchmenuList.get(6).isChecked());
            menuListModel.setA_PROCESSINWARD(SwitchmenuList.get(7).isChecked());
            menuListModel.setA_PROCESSFOLDING(SwitchmenuList.get(8).isChecked());
            menuListModel.setA_BUTTACHECKING(SwitchmenuList.get(9).isChecked());
            menuListModel.setA_MILLCHECKING(SwitchmenuList.get(10).isChecked());
            menuListModel.setA_SAREECUTTING(SwitchmenuList.get(11).isChecked());
            menuListModel.setA_MASTERQRCODE(SwitchmenuList.get(12).isChecked());
            menuListModel.setA_SAREESTOCKENTRY(SwitchmenuList.get(13).isChecked());
            menuListModel.setA_DISPATCHENTRY(SwitchmenuList.get(14).isChecked());
            menuListModel.setA_PURCHASEINWARD(SwitchmenuList.get(15).isChecked());
            menuListModel.setA_ISSUE(SwitchmenuList.get(16).isChecked());
            menuListModel.setA_BEAMSTOCK(SwitchmenuList.get(17).isChecked());
            menuListModel.setA_BEAMCHALLAN(SwitchmenuList.get(18).isChecked());
            menuListModel.setA_BEAMMASTER(SwitchmenuList.get(19).isChecked());
            menuListModel.setA_CREELMASTER(SwitchmenuList.get(20).isChecked());
            menuListModel.setA_CREELPRODUCTION(SwitchmenuList.get(21).isChecked());
            menuListModel.setA_FAULTMASTER(SwitchmenuList.get(22).isChecked());
            menuListModel.setA_STOCKCHECKING(SwitchmenuList.get(23).isChecked());
            menuListModel.setA_SAREERETURN(SwitchmenuList.get(24).isChecked());
            menuListModel.setA_YARN_MACHINE(SwitchmenuList.get(25).isChecked());*/
            menuListModel.setA_MENUALLOCATION(SwitchmenuList.get(6).isChecked());
            menuListModel.setDbname(dbname);

            progressBar.setVisibility(View.VISIBLE);
            Call<Result_model> call = apiService.setUserWiseMenu("application/json", menuListModel);
            call.enqueue(new Callback<Result_model>() {
                @Override
                public void onResponse(Call<Result_model> call, Response<Result_model> response) {
                    Result_model result_model = response.body();
                    progressBar.setVisibility(View.GONE);
                    if (result_model.getResult().equals("Success")) {
                        util_u.showToast("MENU SET SUCCESSFULLY");
                    } else {
                        util_u.showToast("MENU NOT SET PLEASE TRY AGAIN");
                    }
                }

                @Override
                public void onFailure(Call<Result_model> call, Throwable t) {
                    Log.e("MENU ALLOCATION", t.toString());
                }
            });

        } else {
            util_u.showToast("Please Select User");
        }
    }


    public void getUsers() {
        staffList.clear();
        progressBar.setVisibility(View.VISIBLE);
        Call<ArrayList<Staffmodel>> call = apiService.getUsers(dbname, "JOBWORK");
        call.enqueue(new Callback<ArrayList<Staffmodel>>() {
            @Override
            public void onResponse(Call<ArrayList<Staffmodel>> call, Response<ArrayList<Staffmodel>> response) {
                staffList = response.body();
                progressBar.setVisibility(View.GONE);
                if (staffList.size() == 0) {
                    util_u.showToast("NO DATA FOUND");
                    UsersSpinner.setAdapter(null);
                } else {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("a", "Select user");
                    userListData.add(map);
                    for (Staffmodel staffmodel : staffList) {
                        HashMap<String, Object> map1 = new HashMap<String, Object>();
                        map1.put("a", staffmodel.getStaffName());
                        map1.put("b", staffmodel.getStaffmobile());
                        userListData.add(map1);
                    }
                    simpleAdapter = new SimpleAdapter(getActivity().getApplicationContext(), userListData, R.layout.textspinner, new String[]{"a"}, new int[]{R.id.spinnertext});
                    UsersSpinner.setAdapter(simpleAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Staffmodel>> call, Throwable t) {
                Log.e("MENU ALLOCATION", t.toString());
            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.l1) {
            setUserMenu();
        }
        return super.onOptionsItemSelected(item);
    }
}



