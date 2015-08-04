package com.belgaum.events;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.belgaum.events.adapter.SpinnerCustomAdapter;
import com.belgaum.events.util.Entity;
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

	Button editDOB;

	Button editAnnivarsary;

	String name;
	String email;
	String password;
	String mobile;
	String tableNumber;
	String prefixName;
	String business;
	String address;

	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;

	private int pHour;
	private int pMinute;

	boolean dob;

	int IMAGE_PICKER_SELECT = 100;

	Spinner spinnerPrefixItems;

	Spinner spinnerTableNames;

	private String prefixSelected;

	private String tableSelected;

	String prefixIndex;

	String tableIndex;

	ArrayList<Entity> tables;

	ArrayList<Entity> prefix;

	String image_str;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

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

		initValues();

		if (Util.isUserRegistered((getApplicationContext()))) {
			finish();
			startActivity(new Intent(getApplicationContext(),
					DashBoardActivity.class));
			overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
		}
	}

	private void initValues() {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {
				try {

					tables = new ArrayList<Entity>();
					prefix = new ArrayList<Entity>();
					JSONObject jsonObjDetails = new JSONObject(data);
					JSONObject objDetails = jsonObjDetails.getJSONObject("details");
					JSONArray arrayTable = objDetails.getJSONArray("table");

					for (int i = 0; i < arrayTable.length(); i++) {

						Entity entity = new Entity();
						JSONObject obj = arrayTable.getJSONObject(i);
						entity.setTableId(obj.getString("id"));
						entity.setTableName(obj.getString("name"));
						tables.add(entity);
					}

					JSONArray arrayPrefix = objDetails.getJSONArray("prefix");

					for (int i = 0; i < arrayPrefix.length(); i++) {

						Entity entity = new Entity();
						JSONObject obj = arrayPrefix.getJSONObject(i);
						entity.setPrefixId(obj.getString("id"));
						entity.setPrefixName(obj.getString("prefix"));
						prefix.add(entity);
					}

					SpinnerCustomAdapter adapterPrefix = new SpinnerCustomAdapter(
							getApplicationContext(), R.layout.spinner_item_row,
							prefix, "Prefix");
					adapterPrefix
							.setDropDownViewResource(android.R.layout.simple_spinner_item);
					spinnerPrefixItems.setAdapter(adapterPrefix);

					SpinnerCustomAdapter adapterTable = new SpinnerCustomAdapter(
							getApplicationContext(), R.layout.spinner_item_row,
							tables, "Table");
					adapterTable
							.setDropDownViewResource(android.R.layout.simple_spinner_item);
					spinnerTableNames.setAdapter(adapterTable);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePairs, SignUpActivity.this, "");

		post.execute(Util.SIGNUP_PREFIX_URL);
	}

	private void initLayout() {
		editName = (EditText) findViewById(R.id.edit_name);
		editEmail = (EditText) findViewById(R.id.edit_email);
		editMobile = (EditText) findViewById(R.id.edit_mobile);
		editBusiness = (EditText) findViewById(R.id.edit_business);
		editAddress = (EditText) findViewById(R.id.edit_address);
		editDOB = (Button) findViewById(R.id.edit_dob);
		editDOB.setOnClickListener(this);
		editAnnivarsary = (Button) findViewById(R.id.edit_anniversary);
		editAnnivarsary.setOnClickListener(this);
		spinnerPrefixItems = (Spinner) findViewById(R.id.spinner_prefix);
		spinnerPrefixItems.setOnItemSelectedListener(prefixSelectedListener);
		spinnerTableNames = (Spinner) findViewById(R.id.spinner_table_name);
		spinnerTableNames.setOnItemSelectedListener(tableSelectedListener);
	}

	public void doUploadImage(View v) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, IMAGE_PICKER_SELECT);
	}

	public void doSignUp(View v) {
		name = editName.getText().toString();
		email = editEmail.getText().toString();
		mobile = editMobile.getText().toString();
		business = editBusiness.getText().toString();
		address = editAddress.getText().toString();
		String dob = editDOB.getText().toString();
		String anv = editAnnivarsary.getText().toString();

		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
				|| TextUtils.isEmpty(mobile) || TextUtils.isEmpty(business)
				|| TextUtils.isEmpty(address)) {
			Util.showToast(getApplicationContext(), "Please Fill all Fields.");
		} else {

			if (mobile.length() < 10) {
				Util.showToast(getApplicationContext(),
						"Invalid Mobile Number.");
				return;
			}

			if (!Util.isValidEmail(email)) {
				editEmail.setFocusable(true);
				Util.showToast(getApplicationContext(),
						"Invalid Email address.");
				return;
			}

			if (image_str != null && image_str.isEmpty()) {
				Util.showToast(getApplicationContext(),
						"Please select Image to Upload.");
				return;
			}

			List<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			dataToSend.add(new BasicNameValuePair("prefix", prefixIndex));
			dataToSend.add(new BasicNameValuePair("name", name));
			dataToSend.add(new BasicNameValuePair("table_number", tableIndex));
			dataToSend.add(new BasicNameValuePair("email", email));
			dataToSend.add(new BasicNameValuePair("mobile", mobile));
			dataToSend.add(new BasicNameValuePair("business", business));
			dataToSend.add(new BasicNameValuePair("address", address));
			dataToSend.add(new BasicNameValuePair("dob", dob));
			dataToSend.add(new BasicNameValuePair("anniversary", anv));
			dataToSend.add(new BasicNameValuePair("chairman", "0"));
			dataToSend.add(new BasicNameValuePair("password", "nopassword"));
			dataToSend.add(new BasicNameValuePair("image", image_str));

			WebRequestPost postData = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {
					try {
						JSONObject json = new JSONObject(data);

						System.out.println(json + "SIGN UP JSON");

						boolean status = json.getBoolean("error");

						if (status) {
							Util.showToast(getApplicationContext(),
									json.getString("message"));
						} else {
							JSONObject objDetails = new JSONObject(
									json.getString("details"));
							Util.storeUserSession(SignUpActivity.this, true,
									true);
							Util.storeUserDetails(SignUpActivity.this,
									objDetails.getString("id"),
									objDetails.getString("name"));
							finish();
							startActivity(new Intent(getApplicationContext(),
									DashBoardActivity.class));
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

			Uri selectedImageURI = data.getData();
			InputStream input;
			try {
				input = getContentResolver().openInputStream(selectedImageURI);
				Bitmap map = BitmapFactory.decodeStream(input);
				ByteArrayOutputStream bStream = new ByteArrayOutputStream();
				map.compress(Bitmap.CompressFormat.PNG, 100, bStream);
				byte[] image = bStream.toByteArray();
				image_str = Base64.encodeToString(image, 0);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				Util.showToast(SignUpActivity.this, "Version issue");
			}

			// Bitmap map = getDataFromGallery(data);
			// ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			// map.compress(Bitmap.CompressFormat.PNG, 100, bStream);
			// byte[] image = bStream.toByteArray();
			// image_str = Base64.encodeToString(image, 0);

			Util.showToast(getApplicationContext(), "Image Selected.");
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.edit_dob:
			dob = true;
			datePickerDialog.show();
			break;

		case R.id.edit_anniversary:
			datePickerDialog.show();
			break;

		default:
			break;
		}
	}

	/*
	 * private Bitmap getDataFromGallery(Intent data) { // To-do Remove this
	 * method
	 * 
	 * Uri selectedImage = data.getData(); Bitmap map =
	 * decodeUri(selectedImage);
	 * 
	 * return map; }
	 *//**
	 * @author : gmast
	 * @param : Uri uri
	 * @desc : Returns selected image from gallery.
	 */
	/*
	 * private Bitmap decodeUri(Uri uri) { // To-Do Optimize Bitmap map = null;
	 * try { ParcelFileDescriptor pd = getContentResolver().openFileDescriptor(
	 * uri, "r"); FileDescriptor descriptor = pd.getFileDescriptor();
	 * 
	 * BitmapFactory.Options options = new BitmapFactory.Options();
	 * options.inJustDecodeBounds = true;
	 * BitmapFactory.decodeFileDescriptor(descriptor, null, options);
	 * 
	 * final int REQUIRED_SIZE = 1024;
	 * 
	 * int width = options.outWidth; int height = options.outHeight; int scale =
	 * 1;
	 * 
	 * while (true) { if (width < REQUIRED_SIZE && height < REQUIRED_SIZE) {
	 * break; }
	 * 
	 * width /= 2; height /= 2; scale *= 2; }
	 * 
	 * BitmapFactory.Options o2 = new BitmapFactory.Options(); o2.inSampleSize =
	 * scale; map = BitmapFactory.decodeFileDescriptor(descriptor, null, o2);
	 * 
	 * System.out.println(map);
	 * 
	 * } catch (FileNotFoundException e) { e.printStackTrace(); }
	 * 
	 * return map;
	 * 
	 * }
	 */
	OnItemSelectedListener tableSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			Entity entity = tables.get(position);
			tableIndex = entity.getTableId();

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	};

	OnItemSelectedListener prefixSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			Entity entity = prefix.get(position);
			prefixIndex = entity.getPrefixId();

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	};
}
