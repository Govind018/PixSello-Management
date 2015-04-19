package com.spundhan.pixsello.payment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class UpdateNewBIllActivity extends Activity {

	EditText editBillNumber, editBillDate, editDueDate, editAmount;

	String billNumber;
	String billDate;
	String billDueDate;
	String billAmount;

	Spinner spinnerServices;
	Spinner spinnerIdentity;

	ArrayList<Entity> listOfServices;

	ArrayList<String> servicesList;

	ArrayList<String> identityList;

	ArrayAdapter<String> serviceAdapter;

	ArrayAdapter<String> identityAdapter;

	static final int DATE_DIALOG_ID = 999;

	private int year;
	private int month;
	private int day;

	private boolean billDateVal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_new_bill);

		editBillNumber = (EditText) findViewById(R.id.edit_bill_number);
		editBillDate = (EditText) findViewById(R.id.edit_bill_date);
		editDueDate = (EditText) findViewById(R.id.edit_due_date);
		editAmount = (EditText) findViewById(R.id.edit_amount);
		spinnerServices = (Spinner) findViewById(R.id.spinner_services);
		spinnerIdentity = (Spinner) findViewById(R.id.spinner_identity);

		listOfServices = new ArrayList<Entity>();

		servicesList = new ArrayList<String>();
		identityList = new ArrayList<String>();

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		
	}
	
	public void doSubmit(View v){
		
		finish();
		
	}

	public void pickBillDate(View v) {
		billDateVal = true;
		showDialog(DATE_DIALOG_ID);
	}

	public void pickDueDate(View v) {
		billDateVal = false;
		showDialog(DATE_DIALOG_ID);
	}

	@Override
	protected void onStart() {
		super.onStart();

		getServicesAndIdentity();
	}

	private void getServicesAndIdentity() {

		final ProgressDialog dialog = new ProgressDialog(UpdateNewBIllActivity.this);
		dialog.setMessage("Please Wait..");
		dialog.show();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));

		WebRequestPost get = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				Entity item;
				try {
					JSONObject jsonObj = new JSONObject(data);

					if (jsonObj.has("error_message")) {
						Uttilities.showToast(getApplicationContext(),
								jsonObj.getString("error_message"));

					} else {

						JSONArray jsonArray = jsonObj.getJSONArray("result");

						for (int i = 0; i < jsonArray.length(); i++) {
							item = new Entity();

							JSONObject obj = jsonArray.getJSONObject(i);
							item.setServiceId(obj.getString("ServiceID"));
							item.setServiceName(obj.getString("Nameofservice"));
							item.setIdentity(obj.getString("Identity"));

							servicesList.add(obj.getString("Nameofservice"));
							identityList.add(obj.getString("Identity"));
							listOfServices.add(item);
						}

						dialog.cancel();
						serviceAdapter = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.spinner_item,
								servicesList);

						identityAdapter = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.spinner_item,
								identityList);

						spinnerServices.setAdapter(serviceAdapter);
						spinnerIdentity.setAdapter(identityAdapter);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair);

		get.execute(Uttilities.PAYMENT_GET_SERVICES_IDENTITY);

	}

	public void showPayment(View v) {

		billNumber = editBillNumber.getText().toString();
		billDate = editBillDate.getText().toString();
		billDueDate = editDueDate.getText().toString();
		billAmount = editAmount.getText().toString();
		int serviceId = spinnerServices.getSelectedItemPosition() + 1;
		int identityID = spinnerIdentity.getSelectedItemPosition() + 1;

		// if(!billNumber.isEmpty() || !billDate.isEmpty() ||
		// !billDueDate.isEmpty() || !billAmount.isEmpty()){

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(6);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("ServiceID", String
				.valueOf(serviceId)));
		nameValuePair.add(new BasicNameValuePair("BillNo", billNumber));
		nameValuePair.add(new BasicNameValuePair("BillDate", billDate));
		nameValuePair.add(new BasicNameValuePair("Billduedate", billDueDate));
		nameValuePair.add(new BasicNameValuePair("Amount", billAmount));

//		WebRequestPost post = new WebRequestPost(new IWebRequest() {
//
//			@Override
//			public void onDataArrived(String data) {
//
//			}
//		}, nameValuePair);
//
//		post.execute(Uttilities.PAYMENT_UPDATE_NEW_BILL_URL);
		
		Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
		intent.putExtra("ServiceID",String
				.valueOf(serviceId));
		intent.putExtra("Identity",String
				.valueOf(identityID));
		intent.putExtra("ServiceName", spinnerServices.getSelectedItem().toString());
		intent.putExtra("IdentityName", spinnerIdentity.getSelectedItem().toString());
		intent.putExtra("BillNo", billNumber);
		intent.putExtra("BillDate", billDate);
		intent.putExtra("Billduedate", billDueDate);
		intent.putExtra("Amount", billAmount);

		startActivity(intent);
		
		
		
		// }else{

		// Uttilities.showToast(getApplicationContext(),
		// "Please Fill all fields.");
		// }
	}

	public void goBack(View v) {
		finish();
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

			if (billDateVal) {

				editBillDate.setText(new StringBuilder().append(day)
						.append("-").append(month + 1).append("-").append(year)
						.append(" "));

			} else {

				editDueDate
						.setText(new StringBuilder().append(day).append("-")
								.append(month + 1).append("-").append(year)
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
