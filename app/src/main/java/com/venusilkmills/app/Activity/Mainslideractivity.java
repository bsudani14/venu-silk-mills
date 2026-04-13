package com.venusilkmills.app.Activity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.venusilkmills.app.Adapter.CardViewDataAdapter;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.R;


public class Mainslideractivity extends Fragment {

	SharedPreferences sharedPref;
	RecyclerView mRecyclerView;
	RecyclerView.Adapter mAdapter;
	RecyclerView.LayoutManager mLayoutManager;
	private RelativeLayout footerid;
	private TextView footertextView,footeramtView;
	private ImageView footerBuyenow;
	private Database_Helper ph;
	private SQLiteDatabase db;

	public Mainslideractivity() 
	{
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//((menupage) getActivity()).setonbackListener(this);

		ph = new Database_Helper(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		View rootView = inflater.inflate(R.layout.mainslidersub1, container, false);

		return rootView;
	}



}

