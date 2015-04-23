package com.pixsello.management;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class ReportItemActivity extends ActionBarActivity {

	EditText editReportDate;
	EditText editReportTime, editReportDescription, editReportLocation,
			editStaffName, editStayFromDate, editStayToDate;

	ImageView image;

	private String reportDate;
	private String reportTime;
	private String reportDescription;
	private String reportLocation;
	private String staffName;
	private String stayFromDate;
	private String stayToDate;

	static final int DATE_DIALOG_ID = 999;

	int REQUEST_IMAGE_CAPTURE = 101;

	private int year;
	private int month;
	private int day;

	private boolean stayDate;

	ProgressDialog dialog;
	
	boolean photoTaken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_item);

		initLayout();

		dialog = new ProgressDialog(ReportItemActivity.this);
		dialog.setMessage("Please wait..!");

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

	}

	public void capturePhoto(View v) {

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	public void setStayFromDate(View v) {

		stayDate = true;
		showDialog(DATE_DIALOG_ID);
	}

	public void setStayToDate(View v) {
		stayDate = false;
		// Toast.makeText(getApplicationContext(), "" + Uttilities.getDate(),
		// Toast.LENGTH_SHORT).show();
		showDialog(DATE_DIALOG_ID);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		editReportDate.setText(Uttilities.getDate());
		editReportTime.setText(Uttilities.getTime());
	}

	public void goBack(View v) {
		finish();
	}

	private void initLayout() {

		editReportDate = (EditText) findViewById(R.id.edit_report_date);
		editReportTime = (EditText) findViewById(R.id.edit_report_time);
		editReportDescription = (EditText) findViewById(R.id.edit_description);
		editReportLocation = (EditText) findViewById(R.id.edit_location);
		editStaffName = (EditText) findViewById(R.id.edit_staff_name);
		editStayFromDate = (EditText) findViewById(R.id.stay_from_date);
		editStayToDate = (EditText) findViewById(R.id.stay_to_date);
		image = (ImageView) findViewById(R.id.image);
	}

	public void doSubmitItem(View v) {

		// if(Uttilities.isNetworkAvailable(getApplicationContext())){
		// Uttilities.showToast(getApplicationContext(), "ava");
		// }else{
		// Uttilities.showToast(getApplicationContext(), "not");
		// }
		// showDialog(DATE_DIALOG_ID);
		reportDate = editReportDate.getText().toString();
		reportTime = editReportTime.getText().toString();
		reportDescription = editReportDescription.getText().toString();
		reportLocation = editReportLocation.getText().toString();
		staffName = editStaffName.getText().toString();
		stayFromDate = editStayFromDate.getText().toString();
		stayToDate = editStayToDate.getText().toString();

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
		byte [] byte_arr = stream.toByteArray();
		String image_str = Base64.encodeToString(byte_arr, 0);
		
		if (stayFromDate.isEmpty() || stayToDate.isEmpty()
				|| reportDescription.isEmpty() || reportLocation.isEmpty()
				|| staffName.isEmpty()) {

			Uttilities.showToast(getApplicationContext(),
					"Please Fill all fields");

		} else {
			
			if(!photoTaken){
				
				Uttilities.showToast(getApplicationContext(), "Please Take Photo.");
				return;
			}
			
			if(!checkDate(stayFromDate,stayToDate)){
				return;
			}

			dialog.show();

			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(8);
			nameValuePair
					.add(new BasicNameValuePair("PropertyID", Uttilities.getPROPERTY_ID()));
			nameValuePair.add(new BasicNameValuePair("Date", reportDate));
			nameValuePair.add(new BasicNameValuePair("Time", reportTime));
			nameValuePair.add(new BasicNameValuePair("Discriptionofitem",
					reportDescription));
			nameValuePair.add(new BasicNameValuePair("Locationwherefound",
					reportLocation));
			nameValuePair
					.add(new BasicNameValuePair("Staffwhofound", staffName));
			nameValuePair.add(new BasicNameValuePair("Gueststaydatefrom",
					stayFromDate));
			nameValuePair.add(new BasicNameValuePair("Gueststaydateto",
					stayToDate));
			nameValuePair.add(new BasicNameValuePair("Photo",image_str));
			

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {

					dialog.cancel();
					Uttilities.showToast(getApplicationContext(), data);

					finish();
					
//					resetTextFields();
				}
			}, nameValuePair);

			post.execute(Uttilities.REPORT_ITEM_URL);
		}                                                                
		
	}

	private boolean checkDate(String dateFrom,String dateTo) {

		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
		try {
			Date fromDate = format.parse(dateFrom);
			Date toDate = format.parse(dateTo);
			
			// fromdate is greater then to date
			if(fromDate.compareTo(toDate) > 0){

				Uttilities.showToast(getApplicationContext(), "Invalid date selection.");
				return false;
				
			}else if(toDate.compareTo(fromDate) < 0){
				
				Uttilities.showToast(getApplicationContext(), "Invalid date selection.");
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {

			photoTaken = true;
			Bitmap map = (Bitmap) data.getExtras().get("data");
			image.setImageBitmap(map);

		}
	}

	private void resetTextFields() {
                       
		editReportDescription.setText("");
		editReportLocation.setText("");
		editStaffName.setText("");
		editStayFromDate.setText("");
		editStayToDate.setText("");
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year11, int monthOfYear,
				int dayOfMonth) {
			year = year11;
			month = monthOfYear;
			day = dayOfMonth;

//			Calendar newDate = Calendar.getInstance();
//			newDate.set(year, monthOfYear, dayOfMonth);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy",
//					Locale.US);

			if (stayDate) {

//				editStayFromDate.setText(dateFormatter.format(new Date()
//						.getTime()));

				 editStayFromDate.setText(new StringBuilder().append(day)
				 .append("-").append(month + 1).append("-").append(year)
				 .append(" "));

			} else {

//				editStayToDate.setText(dateFormatter.format(new Date()
//						.getTime()));

				 editStayToDate.setText(new StringBuilder().append(day)
				 .append("-").append(month + 1).append("-").append(year)
				 .append(" "));

			}
		}
	};
}
