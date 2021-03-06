package com.belgaum.events.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Util {

	// dhairyasheel.com use this while notifications

	// http://www.rtiarea10.co.in/

	// http://www.rtiarea10.co.in

	public static String LOGIN_URL = "http://www.rtiarea10.co.in/api/index.php/applogin";

	public static String SIGNUP_URL = "http://www.rtiarea10.co.in/api/index.php/appregister2";

	public static String SIGNUP_PREFIX_URL = "http://www.rtiarea10.co.in/api/index.php/prefixtablenumber";

	public static String FORGOT_PASSWORD_URL = "http://www.rtiarea10.co.in/api/index.php/forgotpassword";

	public static String SEARCH_URL = "http://www.rtiarea10.co.in/api/index.php/search2";

	public static String EVETNS_URL = "http://www.rtiarea10.co.in/api/index.php/getallevents2";

	public static String NATIONAL_BOARD_URL = "http://www.rtiarea10.co.in/api/index.php/nationalboard2";

	public static String AREA_BOARD_URL = "http://www.rtiarea10.co.in/api/index.php/areaboard2";

	public static String ADMIN_URL = "http://www.rtiarea10.co.in";

	public static String ADD_COMMENTS_URL = "http://www.rtiarea10.co.in/api/index.php/addcomment";

	public static String GET_COMMENTS_URL = "http://www.rtiarea10.co.in/api/index.php/eventdetails2";

	public static String GCM_URL = "http://www.rtiarea10.co.in/api/index.php/gcmid";

	public static String EVENT_DATA_URL = "http://www.rtiarea10.co.in/api/index.php/eventdetails2";

	public static String NOTIFICATION_STATUS_URL = "http://www.rtiarea10.co.in/api/index.php/notificationon";

	public static String NOTIFICATION_INITIAL_STATUS_URL = "http://www.rtiarea10.co.in/api/index.php/notificationonstat";

	public static final String NETWORK_ERROR_MSG = "No Network Connection.";

	public static final String IMAGE_URL = "http://www.rtiarea10.co.in/";

	public static final String REGISTRATION_ID = "487202621602";

	public static final String MESSAGE_KEY = "title";

	public static void showToast(Context context, String text) {

		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	public static boolean valid(String data) {

		Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");
		Matcher matcher = pattern.matcher(data);

		if (matcher.matches()) {
			return true;
		}

		return false;
	}

	public static void storeUserSession(Context context, boolean loginStatus,
			boolean signUpStatus) {

		SharedPreferences pref = context.getSharedPreferences("login_status",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean("login", loginStatus);
		editor.putBoolean("register", signUpStatus);
		editor.commit();

	}

	public static boolean isUserLoggedIn(Context context) {

		SharedPreferences pref = context.getSharedPreferences("login_status",
				Context.MODE_PRIVATE);
		return pref.getBoolean("login", false);
	}

	public static boolean isUserRegistered(Context context) {

		SharedPreferences pref = context.getSharedPreferences("login_status",
				Context.MODE_PRIVATE);
		return pref.getBoolean("register", false);
	}

	public static void storeUserDetails(Context context, String id, String name) {

		try {
			SharedPreferences pref = context.getSharedPreferences(
					"user_details", Context.MODE_PRIVATE);
			Editor edit = pref.edit();
			edit.putString("user_id", id);
			edit.putString("user_name", name);
			edit.commit();
		} catch (Exception e) {
			showToast(context, "ERROR" + e.getLocalizedMessage());
		}
	}

	public static String getUserName(Context context) {
		SharedPreferences pref = context.getSharedPreferences("user_details",
				Context.MODE_PRIVATE);
		return pref.getString("user_name", "");
	}

	public static String getUserId(Context context) {
		SharedPreferences pref = context.getSharedPreferences("user_details",
				Context.MODE_PRIVATE);
		return pref.getString("user_id", "");
	}

	public static boolean isNetWorkConnected(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = manager.getActiveNetworkInfo();

		if (nInfo != null && nInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public static void storeRegistrationId(String regId, Context context) {
		SharedPreferences pref = context.getSharedPreferences("gcm_details",
				Context.MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.putString("register_id", regId);
		edit.commit();
	}

	public static String getRegId(Context context) {
		SharedPreferences pref = context.getSharedPreferences("gcm_details",
				Context.MODE_PRIVATE);
		return pref.getString("register_id", "");
	}

	public static boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static void saveTables(Context context,ArrayList<String> defaultTables){
		
		SharedPreferences pref = context.getSharedPreferences("tables", 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt("tables_size", defaultTables.size());
		for(int i = 0; i < defaultTables.size(); i++){
			editor.putString("tables_size_"+i, defaultTables.get(i));
		}
		editor.commit();
	}
	
	public static ArrayList<String> getTables(Context context){
		
		ArrayList<String> tables = new ArrayList<String>();
		SharedPreferences pref = context.getSharedPreferences("tables", 0);
		int size = pref.getInt("tables_size", 0);
		for(int i = 0; i< size; i++){
			tables.add(pref.getString("tables_size_"+i, null));
		}
		
		return tables;
	}
	
	public static void closeKB(Context context){
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
	
	public static void showKB(Context context){
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
	}
	
}
