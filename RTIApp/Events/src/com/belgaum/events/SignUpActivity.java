package com.belgaum.events;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.belgaum.events.util.Util;
import com.belgaum.networks.IWebRequest;
import com.belgaum.networks.WebRequestPost;

public class SignUpActivity extends ActionBarActivity implements
		OnClickListener {

	EditText editName;

	EditText editEmail;

	EditText editPassword;

	EditText editMobile;

	EditText editTableNumber;

	EditText editBusiness;

	EditText editAddress;

	EditText editDOB;

	EditText editAnnivarsary;

	String name;
	String email;
	String password;
	String mobile;
	String tableNumber;
	String business;
	String address;

	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;

	private int pHour;
	private int pMinute;

	boolean dob;

	int IMAGE_PICKER_SELECT = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		Calendar calender = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(SignUpActivity.this,
				mDateSetListener, calender.get(Calendar.YEAR),
				calender.get(Calendar.MONTH),
				calender.get(Calendar.DAY_OF_MONTH));

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.custom_actionbar, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);

		TextView text = (TextView) v.findViewById(R.id.title);
		text.setText("Create Account");
		getSupportActionBar().setCustomView(v, params);

		initLayout();
	}

	private void initLayout() {

		editName = (EditText) findViewById(R.id.edit_name);
		editEmail = (EditText) findViewById(R.id.edit_email);
		editPassword = (EditText) findViewById(R.id.edit_password);
		editMobile = (EditText) findViewById(R.id.edit_mobile);
		// editTableNumber = (EditText) findViewById(R.id.edit_table);
		editBusiness = (EditText) findViewById(R.id.edit_business);
		editAddress = (EditText) findViewById(R.id.edit_address);
		editDOB = (EditText) findViewById(R.id.edit_dob);
		editDOB.setOnClickListener(this);
		editAnnivarsary = (EditText) findViewById(R.id.edit_anniversary);
		editAnnivarsary.setOnClickListener(this);
	}

	public void doUploadImage(View v) {

		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, IMAGE_PICKER_SELECT);

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
				Util.showToast(getApplicationContext(),
						"Invalid mobile number.");
				return;
			}

			WebRequestPost postData = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {
					try {
						JSONObject json = new JSONObject(data);

						boolean status = Boolean.parseBoolean(json
								.getString("error"));

						if (status) {
							Util.showToast(getApplicationContext(),
									json.getString("message"));
						} else {
							finish();
							Util.showToast(getApplicationContext(),
									json.getString("message"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, dataToSend, SignUpActivity.this, "Creating user.Please Wait..!");

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

	OnDateSetListener mDateSetListener = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			Calendar newDate = Calendar.getInstance();
			newDate.set(year, monthOfYear, dayOfMonth);
			if (dob) {
				dob = false;
				editDOB.setText(dateFormatter.format(newDate.getTime()));
			} else {
				editAnnivarsary
						.setText(dateFormatter.format(newDate.getTime()));
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && requestCode == IMAGE_PICKER_SELECT) {

			Bitmap map = getDataFromGallery(data);

			Util.showToast(getApplicationContext(), "Image Selected.");
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.edit_dob:
			// Toast.makeText(getApplicationContext(), "fsd",
			// Toast.LENGTH_LONG).show();
			dob = true;
			datePickerDialog.show();
			break;

		case R.id.edit_anniversary:
			// Toast.makeText(getApplicationContext(), "fsdsda",
			// Toast.LENGTH_LONG).show();
			datePickerDialog.show();
			break;

		default:
			break;
		}
	}

	private Bitmap getDataFromGallery(Intent data) { // To-do Remove this method

		Uri selectedImage = data.getData();
		Bitmap map = decodeUri(selectedImage);

		return map;
	}

	/**
	 * @author : gmast
	 * @param : Uri uri
	 * @desc : Returns selected image from gallery.
	 */
	private Bitmap decodeUri(Uri uri) { // To-Do Optimize
		Bitmap map = null;
		try {
			ParcelFileDescriptor pd = getContentResolver().openFileDescriptor(
					uri, "r");
			FileDescriptor descriptor = pd.getFileDescriptor();

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFileDescriptor(descriptor, null, options);

			final int REQUIRED_SIZE = 1024;

			int width = options.outWidth;
			int height = options.outHeight;
			int scale = 1;

			while (true) {
				if (width < REQUIRED_SIZE && height < REQUIRED_SIZE) {
					break;
				}

				width /= 2;
				height /= 2;
				scale *= 2;
			}

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			map = BitmapFactory.decodeFileDescriptor(descriptor, null, o2);

			System.out.println(map);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return map;

	}
}
