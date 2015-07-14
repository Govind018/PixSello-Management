package com.belgaum.events.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Util {

	public static String LOGIN_URL = "http://www.dhairyasheel.com/pk/api/index.php/applogin";

	public static String SIGNUP_URL = "http://www.dhairyasheel.com/pk/api/index.php/appregister2";

	public static String FORGOT_PASSWORD_URL = "http://www.dhairyasheel.com/pk/api/index.php/forgotpassword";

	public static String SIGNUP_PREFIX_URL = "http://www.dhairyasheel.com/pk/api/index.php/prefixtablenumber";
	
	public static String SEARCH_URL = "http://www.dhairyasheel.com/pk/api/index.php/search";
	
	public static String EVETNS_URL = "http://www.dhairyasheel.com/pk/api/index.php/getallevents";
	
	public static String NATIONAL_BOARD_URL = "http://www.dhairyasheel.com/pk/api/index.php/nationalboard";
	
	public static String AREA_BOARD_URL = "http://www.dhairyasheel.com/pk/api/index.php/areaboard";
	
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

	public static boolean isNetWorkConnected(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = manager.getActiveNetworkInfo();

		if (nInfo != null && nInfo.isConnected()) {
			return true;
		}

		return false;
	}

	public static boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
