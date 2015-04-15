package com.pixsello.management;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
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

		if (stayFromDate.isEmpty() || stayToDate.isEmpty()
				|| reportDescription.isEmpty() || reportLocation.isEmpty()
				|| staffName.isEmpty()) {

			Uttilities.showToast(getApplicationContext(),
					"Please Fill all fields");

		} else {

			dialog.show();

			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(8);
			nameValuePair.add(new BasicNameValuePair("PropertyID", "property1"));
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

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {

					dialog.cancel();
					Uttilities.showToast(getApplicationContext(), data);

					resetTextFields();
				}
			}, nameValuePair);

			post.execute(Uttilities.REPORT_ITEM_URL);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE){
			
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

			if (stayDate) {

				editStayFromDate.setText(new StringBuilder().append(month + 1)
						.append("-").append(day).append("-").append(year)
						.append(" "));

			} else {

				editStayToDate.setText(new StringBuilder().append(month + 1)
						.append("-").append(day).append("-").append(year)
						.append(" "));
			}

			// set selected date into textview
			// tvDisplayDate.setText(new StringBuilder().append(month + 1)
			// .append("-").append(day).append("-").append(year)
			// .append(" "));
			//
			// // set selected date into datepicker also
			// dpResult.init(year, month, day, null);

		}
	};
}
