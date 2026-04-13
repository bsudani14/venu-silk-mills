package com.newtech.vplus.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.newtech.vplus.Adapter.detailsexpandableadpter;
import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.MainActivity;

import com.newtech.vplus.Model.shadeclass;
import com.newtech.vplus.R;
import com.newtech.vplus.json.RestClient;
import com.newtech.vplus.util.ConnectionDetector;


public class orderdetails extends Activity
{
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	TextView orderno,dateorder,partyname,saleperson,broker,crdays,shadejson;
	Bundle b = null;
	String value1,dbname;
	private Database_Helper ph;
	SQLiteDatabase database;
	ExpandableListView expandableListView;
	detailsexpandableadpter adapter;
    private Map<String, List<shadeclass>> hashMap=null;
    ProgressBar progress;
    ImageView backimage;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderlist);
		orderno=(TextView)findViewById(R.id.textView8);
		dateorder=(TextView)findViewById(R.id.textView10);
		partyname=(TextView)findViewById(R.id.textView12);
		saleperson=(TextView)findViewById(R.id.textView14);
		broker=(TextView)findViewById(R.id.textView16);
		crdays=(TextView)findViewById(R.id.textView18);
		backimage=(ImageView)findViewById(R.id.imageView1);
		expandableListView=(ExpandableListView)findViewById(R.id.simple_expandable_listview);
		progress=(ProgressBar)findViewById(R.id.progressBar1);
		ph = new Database_Helper(getApplicationContext());
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		database=ph.getReadableDatabase();
		dbname= ph.GetVal("Select dbname From andmst where LOGIN='T'");
		
		backimage.setOnClickListener(new View.OnClickListener() {
	    	 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i = new Intent(getApplicationContext(), MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					orderdetails.this.finish();
					startActivity(i);
			}
		});

		if (isInternetPresent) 
		{

			b = getIntent().getExtras();
			if (b != null) 
			{
				if (b.getString("detailscode") != null)
				{
					value1 = b.getString("detailscode");
					Toast.makeText(getApplicationContext(), value1, Toast.LENGTH_LONG).show();
				}
				else 
				{
					Toast.makeText(getApplicationContext(), "detailscode Null",
							Toast.LENGTH_LONG).show();
				}
			}
			expandableListView.setGroupIndicator(null);
			new partylist().execute();
		}
		else
		{
			ImageView image = new ImageView(this);
			image.setImageResource(R.drawable.nointernet);

			AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
			builder.setTitle("Connection");
			builder.setMessage("Check your internet connection and try again.");
			builder.setPositiveButton("OK", null);
			builder.setView(image);
			builder.show();
		}


	}

	private class partylist extends AsyncTask<String, Void, String>
	{
		String ono,odt,pname,sp,br,cr,detailjson;

		protected void onPreExecute() {

			progress.setVisibility(View.VISIBLE);

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			progress.setVisibility(View.GONE);
			if (result!="")
			{
				if (result.trim().equals("[]"))
				{
					DisplayMsg("No record found.");
				}
				else
				{
				try {

					JSONArray android1 = new JSONArray(result);
					for (int i1 = 0; i1 < android1.length(); i1++) 
					{
						JSONObject c = android1.getJSONObject(i1);

						odt=(c.getString("Tdate"));
						pname=(c.getString("Tparty"));
						sp=(c.getString("Tsp"));
						br=(c.getString("Tbrname"));
						cr=(c.getString("Tcrday"));
						detailjson=(c.getString("TItemlist"));

						database = ph.getWritableDatabase();
						database.delete("OrderListdetails_mst",null,null);
						JSONArray android12= new JSONArray(detailjson);
						for (int i2 = 0; i2 < android12.length(); i2++) 
						{
							JSONObject c1 = android12.getJSONObject(i2);

							
							
							String Json = "{";
							Json = Json + "\"" + "PK_SHADE" + "\":" + "\"" + c1.getString("PK_SHADE") + "\",";
							Json = Json + "\"" + "S_CODE" + "\":" + "\"" + c1.getString("PK_GRADEIS") + "\",";
							Json = Json + "\"" + "shadename" + "\":" + "\"" + c1.getString("shadename") + "\",";
							Json = Json + "\"" + "designtype" + "\":" + "\"" + c1.getString("designtype") + "\",";
							Json = Json + "\"" + "type" + "\":" + "\"" + c1.getString("ordertype") + "\",";
							Json = Json + "\"" + "qdtqty" + "\":" + "\"" + c1.getString("CL_QTY")+ "\",";
							Json = Json + "\"" + "SRATE" + "\":" + "\"" + c1.getString("S_RATE")+ "\"";
							Json = Json + "}";




							database = ph.getWritableDatabase();
							ContentValues cv = new ContentValues();
							cv = new ContentValues();
							cv.put("Icode", c1.getString("PK_GRADEIS"));
							cv.put("PK_SHADE",c1.getString("PK_SHADE"));
							cv.put("JsonText", Json);
							cv.put("ID", i2 + 1);
							database.insert("OrderListdetails_mst", null, cv);
						}
					}


					ArrayList<String> header = new ArrayList<String>();
					 hashMap = new HashMap<String, List<shadeclass>>();
					database = ph.getReadableDatabase();
					Cursor c = database.rawQuery("select distinct Icode from OrderListdetails_mst order by Icode",null);
					int counter=0;
					if (c.getCount() > 0) 
					{
						if (c.moveToFirst()) {
							try {
								do {
									header.add(c.getString(0));
									
									List<shadeclass> child1 = new ArrayList<shadeclass>();
									/*colorstock.shadeclass movie1= new colorstock().new shadeclass();
									movie1.PK_SHADE="PK_SHADE";
									movie1.CL_QTY="CL_QTY";
									movie1.shadename="SHADE";
									movie1.designtype="DESIGN";
									child1.add(movie1);*/
									
									
									Cursor c_sub = database.rawQuery("select JsonText  from OrderListdetails_mst Where Icode='"+ c.getString(0) + "' Order By PK_SHADE", null);
									if (c_sub.getCount() > 0) 
									{
										if (c_sub.moveToFirst()) {
											try {
												
												do {
													
													String s1=c_sub.getString(0);
													JSONObject J = new JSONObject(s1);
													shadeclass movie =new shadeclass();
													movie.PK_SHADE=J.getString("PK_SHADE");
													movie.S_CODE=J.getString("S_CODE");
													movie.CL_QTY=J.getString("qdtqty");

									
													child1.add(movie);												
												} while (c_sub.moveToNext());							
											} catch (Exception e)
											{DisplayMsg(e.getMessage().toString());}
										}	
									}
									hashMap.put(header.get(counter), child1);
									counter+=1;
								} while (c.moveToNext());							
							} catch (Exception e) 
							{DisplayMsg(e.getMessage().toString());}
						}
					}

				   
				   adapter=new detailsexpandableadpter(getApplicationContext(), header, hashMap);
					expandableListView.setAdapter(adapter);


				} catch (JSONException e) {
					DisplayMsg(e.getMessage().toString());
				}

				orderno.setText(value1);
				dateorder.setText(odt);
				saleperson.setText(sp);
				broker.setText(br);
				crdays.setText(cr);
				partyname.setText(pname);



			}
			}
			else{
				DisplayMsg("Server is not working");

			}
		}

		@Override
		protected String doInBackground(String... arg0) 
		{
			if(dbname!=null)
			{
				
			}
			else
			{
					dbname="";
			
			}
			try {					
				RestClient client = new RestClient("CMC/api/OrderList?TCode="+value1+"&dbname="+dbname);
				String response="";
				try {
					response=client.GetRequestexecute();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return response;

			} catch (Exception e) {
				Log.e("ERROR", e.getMessage());

			}


			return null;
		}

	}

	private void DisplayMsg(final String s)
	{
		runOnUiThread(new Runnable() {
			public void run() 
			{
				Toast.makeText(orderdetails.this,s.toString(), Toast.LENGTH_SHORT).show();
			}
		});	
	}

	

}
