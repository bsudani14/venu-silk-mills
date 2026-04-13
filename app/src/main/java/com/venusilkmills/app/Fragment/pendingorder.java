package com.venusilkmills.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.venusilkmills.app.Adapter.OrderListAdapter;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Model.orderlistclass;
import com.venusilkmills.app.R;
import com.venusilkmills.app.json.RestClient;
import com.venusilkmills.app.util.ConnectionDetector;
import com.venusilkmills.app.util.EndlessRecyclerOnScrollListener;

public class pendingorder extends Fragment  implements OrderListAdapter.CardViewListner
{
	private Toolbar mToolbar;
	private ArrayList<orderlistclass> orderlists = new ArrayList<orderlistclass>();
	private RecyclerView listView;
	private OrderListAdapter mAdapter=null;
	private RecyclerView.LayoutManager mLayoutManager;
	private int Page=0;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	private Database_Helper ph;
	SQLiteDatabase db;
	String pcode;
	RadioGroup rg,rg1,rg2;
	String radiostring,Loginpcode,sftype,dbname;
	EditText search;
	ProgressBar pb,pb1;
	SwipeRefreshLayout mySwipeRefreshLayout;
	public pendingorder() 
	{
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
		ph = new Database_Helper(getActivity());
		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) 
		{

			db = ph.getReadableDatabase();
			Cursor getchat1 = db.rawQuery("SELECT PCODE From andmst", null);
			getchat1.moveToFirst();

			if (getchat1.getCount() > 0) 
			{	
				pcode=getchat1.getString(0);
			}
			listView = (RecyclerView) rootView.findViewById(R.id.my_recycler_cardview);
			rg = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
			rg1=(RadioGroup)rootView.findViewById(R.id.radiogroup2);
			rg2=(RadioGroup)rootView.findViewById(R.id.radioGroup3);
			search=(EditText)rootView.findViewById(R.id.editsearchorder);
			pb=(ProgressBar)rootView.findViewById(R.id.progressBar1);
			pb1=(ProgressBar)rootView.findViewById(R.id.progressBar2);
			mySwipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swiperefresh);
			pb.setVisibility(View.GONE);
			rg1.setVisibility(View.GONE);
            rg2.setVisibility(View.GONE);

			listView.setHasFixedSize(true);
			mLayoutManager = new LinearLayoutManager(getActivity());
			listView.setLayoutManager(mLayoutManager);
			orderlists = new ArrayList<orderlistclass>();
			mAdapter = new OrderListAdapter(getActivity(), orderlists,pendingorder.this);
			listView.setAdapter(mAdapter);
			Loginpcode=ph.GetVal("Select PCODE From andmst Where LOGIN='T'");
			//DisplayMsg(Loginpcode);
			sftype=ph.GetVal("Select FTYPE From andmst Where PCODE='"+Loginpcode+"'");
			db=ph.getReadableDatabase();
			dbname= ph.GetVal("Select dbname From andmst where LOGIN='T'");
			if(dbname!=null)
			{
				
			}
			else
			{
					dbname="";
			
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
					new addresslist().execute("CMC/api/pendingorder?id="+  String.valueOf(current_page)+"&ordervalue="+radiostring+"&Ftype="+sftype+"&pcode="+Loginpcode+"&dbname="+dbname);
				}
			});
			 search.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) 
					{
						new searchaddresslist().execute("CMC/api/searchoendingorder?id=1&ordervalue="+radiostring+"&Ftype="+sftype+"&pcode="+Loginpcode+"&pname="+search.getText().toString().toLowerCase(Locale.getDefault())+"&dbname="+dbname);
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,int after) {
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
					}
				});

			rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {

					// TODO Auto-generated method stub
					if (checkedId == R.id.radio0) {
						orderlists.clear();
						mAdapter.notifyDataSetChanged();
						orderlists = new ArrayList<orderlistclass>();
						mAdapter = new OrderListAdapter(getActivity(), orderlists,pendingorder.this);
						listView.setAdapter(mAdapter);
						radiostring = "All";
						new addresslist().execute("CMC/api/pendingorder?id=1&ordervalue="+radiostring+"&Ftype="+sftype+"&pcode="+Loginpcode+"&dbname="+dbname);

					}

					else if (checkedId == R.id.radio1) {
						orderlists.clear();
						mAdapter.notifyDataSetChanged();
						orderlists = new ArrayList<orderlistclass>();
						mAdapter = new OrderListAdapter(getActivity(), orderlists,pendingorder.this);
						radiostring = "Clear";
						listView.setAdapter(mAdapter);
						new addresslist().execute("CMC/api/pendingorder?id=1&ordervalue="+radiostring+"&Ftype="+sftype+"&pcode="+Loginpcode+"&dbname="+dbname);
					}

					else if (checkedId == R.id.radio2) {
						orderlists.clear();
						mAdapter.notifyDataSetChanged();
						orderlists = new ArrayList<orderlistclass>();
						mAdapter = new OrderListAdapter(getActivity(), orderlists,pendingorder.this);
						listView.setAdapter(mAdapter);
						radiostring = "Pending";
						new addresslist().execute("CMC/api/pendingorder?id=1&ordervalue="+radiostring+"&Ftype="+sftype+"&pcode="+Loginpcode+"&dbname="+dbname);
					}
				}
			});
			
			radiostring = "Pending";
			new addresslist().execute("CMC/api/pendingorder?id=1&ordervalue="+radiostring+"&Ftype="+sftype+"&pcode="+Loginpcode+"&dbname="+dbname);
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
	private class addresslist extends AsyncTask<String, Void, String>
	{
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
				if (result != "")
				{
					if (result.trim().equals("[]"))
					{
						DisplayMsg("No record found.");
					}
					else
					{
					int Val = 0;
					if (orderlists.size() > 0) {
						Val = orderlists.size();
					}

					JSONArray android1 = new JSONArray(result);
					for (int i1 = Val; i1 < android1.length(); i1++) 
					{
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
						orderlists.add(movie);
						mAdapter.notifyDataSetChanged();
					}
				}
				}
				else
				{
					DisplayMsg("No order found.");
					
				}
				
			} catch (JSONException e) {
				DisplayMsg(e.getMessage().toString());
			}

		
		}

		@Override
		protected String doInBackground(String... arg0) 
		{
			try{
				String url = "";
				if( arg0.length > 0 ){
					url = arg0[0];		    	
				}
				RestClient client = new RestClient(url);
				String response="";
				try {
					response=client.GetRequestexecute();
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

	private class searchaddresslist extends AsyncTask<String, Void, String>
	{
		

		protected void onPreExecute() {
		pb.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pb.setVisibility(View.GONE);
			mAdapter.notifyDataSetChanged();
			
			try {
				if (result != "")
				{
					if (result.trim().equals("[]"))
					{
						DisplayMsg("No record found.");
					}
					else
					{
						orderlists.clear();
					int Val = 0;
					if (orderlists.size() > 0) {
						Val = orderlists.size();
					}

					JSONArray android1 = new JSONArray(result);
					for (int i1 = Val; i1 < android1.length(); i1++) 
					{
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
						orderlists.add(moviesearch);
						mAdapter.notifyDataSetChanged();
					}
				}
				}
				else
				{
					DisplayMsg("No order found.");
					
				}
				
			} catch (JSONException e) {
				DisplayMsg(e.getMessage().toString());
			}

		
		}

		@Override
		protected String doInBackground(String... arg0) 
		{
			try{
				String url = "";
				if( arg0.length > 0 ){
					url = arg0[0];		    	
				}
				RestClient client = new RestClient(url);
				String response="";
				try {
					response=client.GetRequestexecute();
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
				Toast.makeText(pendingorder.this.getActivity(), s.toString(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}


	@Override
	public void onItemAddedListner(View view, orderlistclass ItemObj,
			String Ftype) {
		// TODO Auto-generated method stub
		String s= ItemObj.getono();
		BottomOrderlistdetails sop = new BottomOrderlistdetails(s);
		sop.show(getActivity().getSupportFragmentManager(), sop.getTag());
	/*	Intent i = new  Intent(getActivity(),orderdetails.class);
		Bundle b=new Bundle();
		b.putString("detailscode",s);
		i.putExtras(b);
		startActivity(i);*/
		
	}

	

}

