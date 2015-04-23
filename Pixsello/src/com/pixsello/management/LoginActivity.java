package com.pixsello.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class LoginActivity extends Activity {

	EditText editUserName;
	EditText editPassword;

	Button btnLogin;

	TextView textLogin;
	TextView textAppName;

	String userName;
	String userPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		editUserName = (EditText) findViewById(R.id.edit_user_name);
		editPassword = (EditText) findViewById(R.id.edit_password);
		btnLogin = (Button) findViewById(R.id.btn_login);

		textLogin = (TextView) findViewById(R.id.text_login);
		textAppName = (TextView) findViewById(R.id.text_app_name);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Sorreda Pro.ttf");
		textLogin.setTypeface(tf);
		textAppName.setTypeface(tf);

	}
	
	
	public void testLogin(){
		
		startActivity(new Intent(getApplicationContext(),
				HomeScreenActivity.class));
	}

	public void doLogin(View v) {

		userName = editUserName.getText().toString();
		userPassword = editPassword.getText().toString();

		if (!userName.isEmpty() || !userPassword.isEmpty()) {

			final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
			dialog.setMessage("Logging In");
			dialog.show();

			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
			nameValuePair.add(new BasicNameValuePair("PropertyID", userName));

			String converted = Base64.encodeToString(userPassword.toString()
					.getBytes(), Base64.DEFAULT);
			nameValuePair.add(new BasicNameValuePair("Password", userPassword));

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {

					dialog.cancel();
					try {
						JSONObject jsonObj = new JSONObject(data);

						if (jsonObj.has("error_message")) {

							Uttilities.showToast(getApplicationContext(),
									jsonObj.getString("error_message"));
						} else {

							JSONObject obj = new JSONObject(data);
							JSONArray json = obj.getJSONArray("result");
							JSONObject jsonn = json.getJSONObject(0);

							String id = jsonn.getString("PropertyID");
							
							Uttilities.setPROPERTY_ID(id);

							finish();
							SharedPreferences pref = getSharedPreferences(
									"login_data", Context.MODE_PRIVATE);
							Editor editor = pref.edit();
							editor.putBoolean("login", true);
							editor.commit();
							startActivity(new Intent(getApplicationContext(),
									HomeScreenActivity.class));

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, nameValuePair);

			post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/Login");

		} else {

			Uttilities.showToast(getApplicationContext(),
					"Please enter all fields.");
		}
	}
}
