package com.venusilkmills.app.FCM;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import android.util.Log;


import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.venusilkmills.app.Database.Database_Helper;
import com.venusilkmills.app.R;


public class GcmIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 001;
	private static final String TAG = "GcmIntentService";
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	Database_Helper ph;
	SQLiteDatabase db;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that GCM
			 * will be extended in the future with new message types, just ignore
			 * any message types you're not interested in, or that you don't
			 * recognize.
			 */
			if (GoogleCloudMessaging.
					MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.
					MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification("Deleted messages on server: " +
						extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.
					MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				// This loop represents the service doing some work.
				for (int i=0; i<5; i++) {
					Log.i(TAG, "Working..." + (i+1)
							+ "/5 @ " + SystemClock.elapsedRealtime());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				// Post notification of received message.
				sendNotification(extras.getString("message"));
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	
	
private void sendNotification(String msg) {
	//PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, Notificationmsg.class), 0);
	PendingIntent resultPendingIntent =PendingIntent.getActivity(this,0,new Intent(this, notification.class),PendingIntent.FLAG_CANCEL_CURRENT);
	
	   mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		
		ph = new Database_Helper(getApplicationContext());
		db = ph.getWritableDatabase();
		ContentValues cv = new ContentValues();		
		cv.put("Message", msg);
		cv.put("MsgStatus", "0");
		db.insert("Notficationmsg_Mst", null, cv);
		
		Notification.InboxStyle inbo= new Notification.InboxStyle();
		String TextMsg="";
		Cursor c = db.rawQuery("Select * From Notficationmsg_Mst Where MsgStatus='0' ",null);
		TextMsg=String.valueOf(c.getCount());
		int count=c.getCount();
		if (c.getCount()>0)
		{
			if (c.moveToFirst()) {
				try{						
					do {
						inbo.addLine(c.getString(0));
					}while (c.moveToNext());
				}catch (Exception e)
				{}
			}
		}
		
		int icon = R.mipmap.ic_launchernew;
		
		Notification noti = new Notification.Builder(this)
	     .setContentTitle(TextMsg +" New Message")
	     .setContentText("ACEKNITS")
	     .setSmallIcon(icon)
	     .setStyle(inbo)
	     .setContentIntent(resultPendingIntent)
	      .setAutoCancel(true)
	     .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
	     .build();
		
		  NotificationCompat.Builder notification=new NotificationCompat.Builder(GcmIntentService.this);
		  notification .setContentTitle(TextMsg +" New Message");
 	     notification.setContentText("ACEKNITS");
 	     notification.setSmallIcon(icon);
 	     notification.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
          notification.setContentText(msg);                                           
          notification.setContentIntent(resultPendingIntent);
 	     notification .setAutoCancel(true);
 	     notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
 	     .build();
          
	      
		if (count==1)
		{
			NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.notify(NOTIFICATION_ID, notification.build());
		}else
		{
			NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.notify(NOTIFICATION_ID, noti);
		}
	}
	
		
}
