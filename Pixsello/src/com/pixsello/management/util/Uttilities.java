package com.pixsello.management.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

public class Uttilities {


	public static String LOST_FOUND_REPORT_ITEM_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/Reportanitem";

	public static String LOST_FOUND_GET_ALL_ITEM_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getReportanitem";

	public static String LOST_FOUND_FIND_ITEM_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/Findanitem";

	public static String REPORT_FIND_ITEM_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/Findanitem";

	public static String CONTACT_ADD_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addContactDetail";
	
	public static String CONTACT_GET_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getContactDetail";
	
	public static String CONTACT_SEARCH_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/searchContact";
	
	public static String GUEST_MAKE_IN_ENTRY = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addGuestVisitor";

	public static String GUEST_MAKE_OUT_ENTRY = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/GetGuestVisitor";

	public static String GUEST_UPDATE_OUT_TIME_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/updateOuttime";
	
	public static String GUEST_PAST_VISITORS_LIST = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/GetAllpastVisitor";
	
	public static String GUEST_PAST_VISITORS_SEARCH = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/pastVisitorSearch";

	public static String ACTION_REQUIRED_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addActionRequired";

	public static String ACTION_ACTIVE_ITEMS_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getActiveActionList";

	public static String ACTION_ITEM_UPDATE_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/updateActionTaken";
	
	public static String CLOSED_ITEMS_LIST_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getCloseActionList";

	public static String TRAINING_UPDATE_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/TrainingUpdate";

	public static String TRAINING_STAFF_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getStaffList";
	
	public static String TRAINING_STAFF_SEARCH = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/staff_search";
	
	public static String FEEDBACK_ADD_NEW = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addGuestFeedback";
	
	public static String FEEDBACK_PREVIOUS_LIST = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getGuestfeedback";

//	public static String PAST_VISITORS_SEARCH = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getPastVisitors";

	public static String PAYMENT_ADD_NEW_SERVICES_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addService";

	public static String PAYMENT_ADD_SERVICES_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addPaymentservice";

	public static String PAYMENT_GET_SERVICES_IDENTITY = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getServiceIdentity";

	public static String PAYMENT_UPDATE_NEW_BILL_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/UpdateNewBill";

	public static String PAYMENT_BILL_PAYMENT_URL = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/BillPayment";

	public static String PAYMENT_STATUS = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/newPayment";

	public static String EMPLOYEE_ADD_DETAILS = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addEmployee_Employment";

	public static String PROPERTY_ID = "property1";

	public static String PAYMENT_NEWPAYMENT_SEARCH = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getNewPaymentSearch";

	public static String PAYMENT_NEWPAYMENT = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/newPayment";

	// public static String PROPERTY_ID;

	public static String getPROPERTY_ID() {
		return PROPERTY_ID;
	}

	public static void setPROPERTY_ID(String pROPERTY_ID) {
		PROPERTY_ID = pROPERTY_ID;
	}

	public static void showToast(Context context, String message) {

		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

	}

	public static void finishActivity(Activity context) {
		context.finish();

	}

	public static boolean isNetConnected(Context context) {

		ConnectivityManager mgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nif = mgr.getActiveNetworkInfo();

		if (nif == null) {
			return false;
		} else {
			return true;
		}
	}

	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String currentDateandTime = sdf.format(new Date());

		return currentDateandTime;
	}

	public static String getTime() {
		String timeStamp = new SimpleDateFormat("HH:mm a").format(Calendar
				.getInstance().getTime());
		return timeStamp;
	}

	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager check = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] info = check.getAllNetworkInfo();

		for (int i = 0; i < info.length; i++) {
			if (info[i].getState() == NetworkInfo.State.CONNECTED) {
				return true;
			} else {
				return false;
			}
		}
		return false;

		// ConnectivityManager connectivityManager
		// = (ConnectivityManager)
		// context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// NetworkInfo activeNetworkInfo =
		// connectivityManager.getActiveNetworkInfo();
		// return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public static void showAlertDialog(final Context context) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle("Internet Status");
		alertDialog.setMessage("NO Internet Connection..!");
		alertDialog.setPositiveButton("Connect Now.", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				context.startActivity(new Intent(Settings.ACTION_SETTINGS));

			}
		});

		AlertDialog dialog = alertDialog.create();
		dialog.create();
	}

	public static Bitmap resizeImage(int width, int height, Bitmap map) {

		Bitmap resizedImage;

		if (width == 480) {
			width = width - 160;
		} else if (width == 720) {
			width = width - 220;
		}

		resizedImage = Bitmap.createScaledBitmap(map, width, (height - 320),
				true);

		return resizedImage;
	}

	public static void storeSession(boolean loginStatus, String userId,
			Context context) {

		SharedPreferences pref = context.getSharedPreferences("login_data",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean("login", true);
		editor.putString("login_user", userId);
		editor.commit();

	}

	public static String getUserLoginId(Context context) {
		SharedPreferences pref = context.getSharedPreferences("login_data",
				Context.MODE_PRIVATE);

		return pref.getString("login_user", "");

	}
}
