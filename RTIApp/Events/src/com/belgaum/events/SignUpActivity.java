package com.belgaum.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.belgaum.events.util.Util;
import com.belgaum.networks.IWebRequest;
import com.belgaum.networks.WebRequestPost;

public class SignUpActivity extends ActionBarActivity {

	EditText editName;

	EditText editEmail;

	EditText editPassword;

	EditText editMobile;

	EditText editTableNumber;

	EditText editBusiness;

	EditText editAddress;

	String name;
	String email;
	String password;
	String mobile;
	String tableNumber;
	String business;
	String address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.custom_actionbar, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.MATCH_PARENT,Gravity.CENTER);
		
		TextView text = (TextView) v.findViewById(R.id.title);
		text.setText("Create Account");
		getSupportActionBar().setCustomView(v,params);

		initLayout();
	}

	private void initLayout() {

		editName = (EditText) findViewById(R.id.edit_name);
		editEmail = (EditText) findViewById(R.id.edit_email);
		editPassword = (EditText) findViewById(R.id.edit_password);
		editMobile = (EditText) findViewById(R.id.edit_mobile);
//		editTableNumber = (EditText) findViewById(R.id.edit_table);
		editBusiness = (EditText) findViewById(R.id.edit_business);
		editAddress = (EditText) findViewById(R.id.edit_address);
	}

	public void doSignUp(View v) {

		name = editName.getText().toString();
		email = editEmail.getText().toString();
		password = editPassword.getText().toString();
		mobile = editMobile.getText().toString();
		tableNumber = editTableNumber.getText().toString();
		business = editBusiness.getText().toString();
		address = editAddress.getText().toString();

		List<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
		dataToSend.add(new BasicNameValuePair("name", name));
		dataToSend.add(new BasicNameValuePair("email", email));
		dataToSend.add(new BasicNameValuePair("password", password));
		dataToSend.add(new BasicNameValuePair("mobile", mobile));
		dataToSend.add(new BasicNameValuePair("table_number", tableNumber));
		dataToSend.add(new BasicNameValuePair("business", business));
		dataToSend.add(new BasicNameValuePair("address", address));

		// if (Util.valid(name)) {
		// Util.showToast(getApplicationContext(), "ds");
		//
		// }else{
		// Util.showToast(getApplicationContext(), "wrwr");
		// }

		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
				|| TextUtils.isEmpty(password) || TextUtils.isEmpty(mobile)
				|| TextUtils.isEmpty(tableNumber)
				|| TextUtils.isEmpty(business) || TextUtils.isEmpty(address)) {

			Util.showToast(getApplicationContext(), "Please Fill all Fields.");
		} else {

			if (mobile.length() < 10) {
				Util.showToast(getApplicationContext(), "Invalid mobile number.");
				return;
			}
			
			WebRequestPost postData = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {
					try {
						JSONObject json = new JSONObject(data);
						
						boolean status =  Boolean.parseBoolean(json.getString("error"));
						
						if(status){
							Util.showToast(getApplicationContext(),
									json.getString("message"));	
						}else{
							finish();
							Util.showToast(getApplicationContext(),
									json.getString("message"));							
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, dataToSend, SignUpActivity.this,"Creating user.Please Wait..!");

			postData.execute(Util.SIGNUP_URL);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
