package com.belgaum.events.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Util {

	public static String LOGIN_URL = "http://www.dhairyasheel.com/pk/api/index.php/login/";

	public static String SIGNUP_URL = "http://www.dhairyasheel.com/pk/api/index.php/signup";

	public static String FORGOT_PASSWORD_URL = "http://www.dhairyasheel.com/pk/api/index.php/forgotpassword";

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

	public static void storeUserSession(Context context,boolean status) {

		SharedPreferences pref = context.getSharedPreferences("login_status",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean("login", status);
		editor.commit();

	}

	public static boolean isUserLoggedIn(Context context) {

		SharedPreferences pref = context.getSharedPreferences("login_status",
				Context.MODE_PRIVATE);
		return pref.getBoolean("login", false);

	}
}
