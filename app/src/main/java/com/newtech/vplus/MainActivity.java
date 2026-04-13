package com.newtech.vplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.newtech.vplus.Activity.Dispatch_activity;
import com.newtech.vplus.Activity.Mainslideractivity;
import com.newtech.vplus.Activity.ordermenu;
import com.newtech.vplus.Activity.stock;
import com.newtech.vplus.Database.Database_Helper;
import com.newtech.vplus.FCM.RegisterApp;
import com.newtech.vplus.Fragment.Apartylist;
import com.newtech.vplus.Fragment.Costcenterperfragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    String FTYPE, cusname;
    DrawerLayout
            drawer;
    Database_Helper ph;
    SQLiteDatabase db;
    TextView tx1, tx2;
    GoogleCloudMessaging gcm;
    String regid;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String TAG = "GCMRelated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("HOME");
        setSupportActionBar(toolbar);
        ph = new Database_Helper(getApplicationContext());
        db = ph.getReadableDatabase();
        FTYPE = ph.GetVal("Select FTYPE From andmst where LOGIN='T'");
        cusname = ph.GetVal("Select PNAME From andmst where LOGIN='T'");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.menu_home);
        displaySelectedScreen(R.id.menu_home);
        getmenu();

      /*  String str = Settings.Secure.getString(getContentResolver(), "android_id");
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            regid = getRegistrationId(getApplicationContext());
            if (regid != "") {
                db = ph.getReadableDatabase();
                String newregid = ph.GetVal("Select GCM_REG_ID From andmst Where Android_id='" + str + "'");
                new RegisterApp(getApplicationContext(), gcm, getAppVersion(getApplicationContext())).execute();

            } else {
                new RegisterApp(getApplicationContext(), gcm, getAppVersion(getApplicationContext())).execute();
            }
        }
*/
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        //creating fragment object
        Fragment fragment = null;
        //initializing the fragment object which is selected
        if (itemId == R.id.menu_home) {
            fragment = new Mainslideractivity();
        } else if (itemId == R.id.menu_login) {
            Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i1);
        } else if (itemId == R.id.menu_dispatch) {
            Intent i10 = new Intent(getApplicationContext(), Dispatch_activity.class);
            startActivity(i10);
        } else if (itemId == R.id.menu_led1) {
            Intent i = new Intent(getApplicationContext(), Apartylist.class);
            i.putExtra("menuname", "led");
            startActivity(i);
        } else if (itemId == R.id.menu_order) {
            if (FTYPE.equals("ADMIN") || FTYPE.equals("MANAGER")) {
                Intent party = new Intent(getApplicationContext(), Apartylist.class);
                party.putExtra("menuname", "order");
                startActivity(party);
            } else {
                Intent order = new Intent(getApplicationContext(), ordermenu.class);
                order.putExtra("menuname", "order1");
                startActivity(order);
            }
        } else if (itemId == R.id.stock) {
            Intent stockIntent = new Intent(getApplicationContext(), stock.class);
            stockIntent.putExtra("menuname", "led");
            startActivity(stockIntent);
        } else if (itemId == R.id.menu_dis) {
            Intent i4 = new Intent(getApplicationContext(), Apartylist.class);
            i4.putExtra("menuname", "dispatch");
            startActivity(i4);
        } else if (itemId == R.id.menu_logout) {
            ph.ResetDatabase();
            finish();
            Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i2);
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if (itemId != R.id.menu_home) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    private void getmenu() {
        View headerView = navigationView.getHeaderView(0);
        tx1 = (TextView) headerView.findViewById(R.id.tx1);
        tx2 = (TextView) headerView.findViewById(R.id.tx2);
        tx1.setText(FTYPE);
        tx2.setText(cusname);
        if (FTYPE.equals("ADMIN")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            //navigationView.getMenu().findItem(R.id.menu_order_job).setVisible(false);
           // navigationView.getMenu().findItem(R.id.menu_millprocess).setVisible(false);
        }
      /* else if (FTYPE.equals("DISPATCH")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
           *//* navigationView.getMenu().findItem(R.id.menu_permission).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_orderapprove).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_notification).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_salrep).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_creout).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_order_job).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_Bout).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_ordlist).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_millprocess).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_report1).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_order).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_led).setVisible(false);*//*

        }else if (FTYPE.equals("PARTY") || FTYPE.equals("BROKER")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
           *//* navigationView.getMenu().findItem(R.id.menu_permission).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_orderapprove).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_notification).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_salrep).setVisible(false);*//*
            navigationView.getMenu().findItem(R.id.menu_dispatch).setVisible(false);
        } else if (FTYPE.equals("SALESPERSON")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            navigationView.getMenu().findItem(R.id.menu_permission).setVisible(false);
        *//*    navigationView.getMenu().findItem(R.id.menu_orderapprove).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_notification).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_salrep).setVisible(false);*//*
            navigationView.getMenu().findItem(R.id.menu_dispatch).setVisible(false);
        } else if (FTYPE.equals("SUPER ADMIN")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }*/ else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer1);
        }

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId == "") {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(getApplicationContext());
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(Splash_Screen.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}
