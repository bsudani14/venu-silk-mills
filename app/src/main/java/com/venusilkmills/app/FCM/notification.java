package com.venusilkmills.app.FCM;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.MainActivity;
import com.venusilkmills.app.Model.msgclass;
import com.venusilkmills.app.R;

import java.util.ArrayList;
import java.util.List;

public class notification extends Activity {
    SQLiteDatabase db;
    Database_Helper ph;
    ListView msglist;
    messageadpater a=null;
    List<msgclass> listData = new ArrayList<msgclass>();
    //ProgressBar p1;
    RelativeLayout r1,r2;
    Button clear;
    String person;
    ImageView back,sendnoti;

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notification.this.finish();
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationmsg);

        ph = new Database_Helper(getApplicationContext());
        msglist=(ListView)findViewById(R.id.msglistview);
        //p1=(ProgressBar)findViewById(R.id.progressBar1);
        r1=(RelativeLayout)findViewById(R.id.r1);
        r2=(RelativeLayout)findViewById(R.id.r2);
        clear=(Button)findViewById(R.id.clearmsg);
        sendnoti=(ImageView)findViewById(R.id.imageView2);
        sendnoti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                /*Intent i=new Intent(getApplicationContext(),sendnotification.class);
                notification.this.finish();
                startActivity(i);
                overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);*/
            }
        });

        back=(ImageView)findViewById(R.id.imageView1);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                notification.this.finish();
                startActivity(i);
            }
        });

        String LoginName1=ph.GetVal("Select PNAME From andmst Where LOGIN='T'");
        String sftype=ph.GetVal("Select FTYPE From andmst Where PNAME='"+LoginName1+"'");

        if(sftype.equals("PARTY"))
        {
            sendnoti.setVisibility(View.INVISIBLE);
        }

        if(sftype.equals("BROKER"))
        {
            sendnoti.setVisibility(View.INVISIBLE);
        }

        if(sftype.equals("ADMIN"))
        {
            sendnoti.setVisibility(View.VISIBLE);
        }

        db = ph.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MsgStatus", "1");
        db.update("Notficationmsg_Mst", values, null, null);
        String str = Settings.Secure.getString(getContentResolver(), "android_id");

        WebView view = (WebView) findViewById(R.id.webView1);
        // view.getSettings().setDefaultZoom(ZoomDensity.FAR);

        //view.loadUrl("file:///android_asset/hund.gif");



        clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                AlertDialog.Builder ad=new AlertDialog.Builder(notification.this);
                ad.setTitle("Delete Message");
                ad.setIcon(R.drawable.ic_delete);
                ad.setMessage("Are you sure?");

                ad.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // TODO Auto-generated method stub
                        db = ph.getWritableDatabase();
                        db.delete("Notficationmsg_Mst",null , null);


                        Intent i=new Intent(getApplicationContext(),notification.class);
                        notification.this.finish();
                        startActivity(i);
                       // overridePendingTransition(R.anim.activity_slide_in_left,R.anim.activity_slide_out_right);

                    }
                });

                ad.setNegativeButton("No",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
						/*AlertDialog.Builder builder = new AlertDialog.Builder(notification.this);
			            WebView view = new WebView(notification.this);
			            view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			            builder.setView(view);
			            builder.create().show();
			            view.loadUrl("file:///android_asset/hund.gif");*/

                    }
                });

                ad.show();
            }

        });

        new loadfunction().execute();


    }

    private class loadfunction extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //p1.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //p1.setVisibility(View.GONE);
            try {
                db = ph.getReadableDatabase();
                Cursor c = db.rawQuery("select Message from Notficationmsg_Mst order by Message desc", null);
                int columnsSize = c.getColumnCount();
                listData = new ArrayList<msgclass>();

                while (c.moveToNext())
                {
                    msgclass movie = new msgclass();
                    for (int i = 0; i < columnsSize; i++)
                    {
                        movie.setmessage(c.getString(0));
                    }
                    listData.add(movie);
                }

                if (c.getCount() > 0)
                {
                    r1.setVisibility(View.GONE);
                    r2.setVisibility(View.VISIBLE);
                    a=new messageadpater(notification.this, R.layout.messagedetails, listData);
                    msglist.setAdapter(a);
                }
                else
                {
                    r1.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.GONE);
                }
            }catch(Exception e)
            {
                Log.e("Exception",e.getMessage().toString());
            }
        }
    }

}


