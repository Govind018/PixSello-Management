package com.belgaum.events.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.belgaum.events.EventDetailActivity;
import com.belgaum.events.GcmBroadcastReceiver;
import com.belgaum.events.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMNotificationIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder mBuilder;
	Notification notification;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {

				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

//				sendNotification("" + extras.get(Util.MESSAGE_KEY));
				
				showNotificationMessage("RTI X",""+extras.get(Util.MESSAGE_KEY),""+extras.get("event"));
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {
		Log.d(TAG, "Preparing to send notification...: " + msg);
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, GCMNotificationIntentService.class), 0);

		mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.app_logo)
				.setContentTitle("RTI X")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		Log.d(TAG, "Notification sent successfully.");
	}

	public void showNotificationMessage(String title, String message,String eventID) {

		Intent intent = new Intent(getApplicationContext(),EventDetailActivity.class);
		intent.putExtra("id", eventID);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		// Check for empty push message
		if (TextUtils.isEmpty(message))
			return;

		if (isAppIsInBackground(this)) {
			// notification icon
			int icon = R.drawable.app_logo;

			int mNotificationId = 1;
			
			int numOfMessages = 0;

			PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
					0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

			NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

			mBuilder = new NotificationCompat.Builder(
					this);
			notification = mBuilder
					.setSmallIcon(icon)
					.setTicker(title)
					.setWhen(0)
					.setAutoCancel(true)
					.setContentTitle(title)
					.setStyle(inboxStyle)
					.setContentIntent(resultPendingIntent).setNumber(++numOfMessages)
					.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
					.setLargeIcon(
							BitmapFactory.decodeResource(this.getResources(),
									icon)).setContentText(message).build();

			mNotificationManager = (NotificationManager) this
					.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(mNotificationId,notification);
			
		} else {
			intent.putExtra("id", eventID);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.startActivity(intent);
		}
	}

	public static boolean isAppIsInBackground(Context context) {
		boolean isInBackground = true;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
			List<ActivityManager.RunningAppProcessInfo> runningProcesses = am
					.getRunningAppProcesses();
			for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
				if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					for (String activeProcess : processInfo.pkgList) {
						if (activeProcess.equals(context.getPackageName())) {
							isInBackground = false;
						}
					}
				}
			}
		} else {
			List<ActivityManager.RunningTaskInfo> taskInfo = am
					.getRunningTasks(1);
			ComponentName componentInfo = taskInfo.get(0).topActivity;
			if (componentInfo.getPackageName().equals(context.getPackageName())) {
				isInBackground = false;
			}
		}

		return isInBackground;
	}
}
