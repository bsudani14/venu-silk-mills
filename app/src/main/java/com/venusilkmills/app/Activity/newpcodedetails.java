package com.venusilkmills.app.Activity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.Fragment.Frbillfragment;
import com.venusilkmills.app.Fragment.Frledgerfragment;
import com.venusilkmills.app.Fragment.OutstandingFragment;
import com.venusilkmills.app.R;
import com.venusilkmills.app.util.Util_u;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class newpcodedetails extends AppCompatActivity {

    Bundle b, b1 = null;
    String value1, dbname;
    TextView tv;
    ImageView backimage;
    Database_Helper ph;
    SQLiteDatabase db;
    Toolbar mToolbar;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Util_u util;
String ftype,mobileno;
    private int ledgerMenu = 0,billMenu = 0;
    @Override
    public void onBackPressed() {

        newpcodedetails.this.finish();

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_text_tabs1);

      toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        util = new Util_u(newpcodedetails.this, this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ph = new Database_Helper(getApplicationContext());

        ftype = ph.GetVal("Select FTYPE from andmst where LOGIN='T'");
        mobileno = ph.GetVal("Select MOBILENO From andmst where LOGIN='T'");


        b = getIntent().getExtras();
        if (b != null) {
            if (b.getString("p_code") != null) {
                value1 = b.getString("p_code");
            }
            else if (b.getString("pcoderate") != null) {
                value1 = b.getString("pcoderate");
            }
            else if (b.getString("mainpcode") != null) {
                value1 = b.getString("mainpcode");
            }
            else {
                Toast.makeText(getApplicationContext(), "code Null",
                        Toast.LENGTH_LONG).show();
            }
        }

        b1 = getIntent().getExtras();
        if (b1 != null) {
            if (b1.getString("p_name") != null) {
                getSupportActionBar().setTitle(b1.getString("p_name").toString());
            }if(b1.getString("LedgerMenu") != null){
                ledgerMenu = Integer.parseInt(b1.getString("LedgerMenu").toString());
            }if(b1.getString("BillMenu") != null){
                billMenu = Integer.parseInt(b1.getString("BillMenu").toString());
            }
        }

       viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        b = new Bundle();
        b.putString("pcodetab",value1);

        Frledgerfragment f = new Frledgerfragment();
        f.setArguments(b);
        adapter.addFrag(f, "Ledger", b);
        Frbillfragment f1 = new Frbillfragment();
        f1.setArguments(b);
        adapter.addFrag(f1, "Bill", b);
        if(ftype.equals("ADMIN") || ftype.equals("STAFF")){
            OutstandingFragment f2 = new OutstandingFragment();
            f2.setArguments(b);
            adapter.addFrag(f2, "OR", b);
        }

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Bundle> mFragmentBundle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title,Bundle b) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mFragmentBundle.add(b);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
      /*  super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_text_tabs);

		*//*  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*//*

        tv = (TextView) findViewById(R.id.textView1);
        backimage = (ImageView) findViewById(R.id.imageView1);
        ph = new Database_Helper(getApplicationContext());
        backimage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                newpcodedetails.this.finish();

            }
        });

        b = getIntent().getExtras();
        if (b != null) {
            if (b.getString("brcode") != null) {
                value1 = b.getString("brcode");
            } else if (b.getString("pcoderate") != null) {
                value1 = b.getString("pcoderate");
            } else if (b.getString("mainpcode") != null) {
                value1 = b.getString("mainpcode");
            } else {
                Toast.makeText(getApplicationContext(), "code Null",
                        Toast.LENGTH_LONG).show();
            }


        }

        b1 = getIntent().getExtras();
        if (b1 != null) {
            if (b1.getString("pname") != null) {

                tv.setText(b1.getString("pname"));
            } else {
                db = ph.getReadableDatabase();
                String Loginpcode = ph.GetVal("Select PNAME From andmst Where LOGIN='T'");
                tv.setText(Loginpcode);
            }
        }


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        b = new Bundle();
        b.putString("pcodetab", value1);

        Frledgerfragment f = new Frledgerfragment();
        f.setArguments(b);
        adapter.addFrag(f, "Ledger", b);

        Frbillfragment f1 = new Frbillfragment();
        f1.setArguments(b);
        adapter.addFrag(f1, "Bill", b);

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Bundle> mFragmentBundle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title, Bundle b) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mFragmentBundle.add(b);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/

