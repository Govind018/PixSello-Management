package com.belgaum.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.belgaum.events.util.Util;
import com.belgaum.networks.IWebRequest;
import com.belgaum.networks.WebRequestPost;

public class LoginActivity extends Activity  {

	TextView textSignUp;

	EditText editEmail;

	EditText editPassword;

	String userEmail;

	String userPassword;

	boolean passwordSet = false;

	TextView forgotPwd;

	private static String TAG = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// ButterKnife.inject(this);

		TAG = this.getClass().getSimpleName();

		init();

		if (Util.isUserLoggedIn(getApplicationContext())) {

			if (Util.isUserRegistered(getApplicationContext())) {
				finish();
				startActivity(new Intent(getApplicationContext(),
						DashBoardActivity.class));
			} else {
				finish();
				startActivity(new Intent(getApplicationContext(),
						SignUpActivity.class));
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	private void init() {
		textSignUp = (TextView) findViewById(R.id.text_signup);
		textSignUp.setMovementMethod(LinkMovementMethod.getInstance());
		editEmail = (EditText) findViewById(R.id.edit_email);
		editPassword = (EditText) findViewById(R.id.edit_password);
	}

	public void openSignUpScreen(View v) {
		startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void doLogin(View v) {

		userEmail = editEmail.getText().toString();
		userPassword = editPassword.getText().toString();

		if (TextUtils.isEmpty(userEmail)) {
			editEmail.setError("Enter User");
			return;
		}

		if (TextUtils.isEmpty(userPassword)) {
			editPassword.setError("Enter Password");
			return;
		}

		if (!Util.isNetWorkConnected(LoginActivity.this)) {
			Util.showToast(getApplicationContext(), "Internet not connected.");
			return;
		}

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("user", userEmail));
		nameValuePair.add(new BasicNameValuePair("password", userPassword));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {
					JSONObject json = new JSONObject(data);

					String loginStatus = json.getString("error");
					
					//Checks for login status
					if (loginStatus.equalsIgnoreCase("true")) {
						Util.showToast(getApplicationContext(),
								"User and password doesn't match");
					} else {
						//Stores User session in prefrences
						Util.storeUserSession(getApplicationContext(), true,
								false);
						finish();
						startActivity(new Intent(getApplicationContext(),
								SignUpActivity.class));
						overridePendingTransition(R.anim.left_to_right,
								R.anim.abc_fade_out);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}, nameValuePair, LoginActivity.this, "Logging In.");

		post.execute(Util.LOGIN_URL);

	}

/*	public void openDialog(View v) {

		showResetPasswordDialog("", "");

	}

	public void showResetPasswordDialog(String type, String emailId) {

		LayoutInflater infalter = LayoutInflater.from(this);
		View prompt = infalter.inflate(R.layout.forgot_password_dialog, null);
		prompt.findViewById(R.id.password_dailog);
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(prompt);
		dialog.show();
		dialog.setCanceledOnTouchOutside(false);

		final EditText editPassword = (EditText) prompt
				.findViewById(R.id.user_password);

		Button btnOk = (Button) prompt.findViewById(R.id.btn_ok);
		Button btnCancel = (Button) prompt.findViewById(R.id.btn_cancel);
		TextView lblTitle = (TextView) prompt.findViewById(R.id.lbl_title);
		TextView text = (TextView) prompt.findViewById(R.id.text_data);

		// show info dialog after resetting password
		if (type.equalsIgnoreCase("reset")) {
			passwordSet = true;
			editPassword.setVisibility(View.INVISIBLE);
			lblTitle.setText("Password Reset");
			text.setText("An email with reset instructions has been sent to "
					+ emailId);
			btnCancel.setVisibility(View.GONE);
		}

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.cancel();
			}
		});

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (passwordSet) {

					dialog.cancel();

				} else {

					String emailId = editPassword.getText().toString();

					if (!emailId.isEmpty()) {
						dialog.cancel();
						// progressDialog.show();
						resetPassword(emailId);

					} else {

						Util.showToast(getApplicationContext(),
								"Enter email id.");
					}
				}
			}
		});
	}

	private void resetPassword(final String email) {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("email", email));

		WebRequestPost postData = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {
					JSONObject json = new JSONObject(data);
					String status = json.getString("error");

					if (Boolean.parseBoolean(status)) {
						Util.showToast(getApplicationContext(),
								json.getString("message"));
					} else {
						showResetPasswordDialog("reset", email);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair, LoginActivity.this, "Sending Mail.Please Wait..!");

		postData.execute(Util.FORGOT_PASSWORD_URL);
	}*/

	public void directLogin(View v) {

		startActivity(new Intent(getApplicationContext(),
				DashBoardActivity.class));
	}

}
