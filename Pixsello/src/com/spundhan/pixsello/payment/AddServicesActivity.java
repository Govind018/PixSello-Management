package com.spundhan.pixsello.payment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class AddServicesActivity extends Activity {

	static final int DATE_DIALOG_ID = 999;
	private int year;
	private int month;
	private int day;

	EditText editService, editIdentity, editDueDate;

	RadioButton radioRegular, radioRecurring;

	boolean regular;

	boolean recuring;

	String service;
	String identity;
	String dueDate;
	String regularPay;
	String recurring;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_services);

		editService = (EditText) findViewById(R.id.edit_service);
		editIdentity = (EditText) findViewById(R.id.edit_identity);
		editDueDate = (EditText) findViewById(R.id.due_date);
		radioRegular = (RadioButton) findViewById(R.id.radio_type_payment_regular);
		radioRecurring = (RadioButton) findViewById(R.id.radio_type_payment_recurring);

		radioRegular.setOnCheckedChangeListener(Regularlistener);
		radioRecurring.setOnCheckedChangeListener(Recurringlistener);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
	}

	public void doSubmitItem(View v){

		service = editService.getText().toString();
		identity = editIdentity.getText().toString();
		dueDate = editDueDate.getText().toString();
		regularPay = (regular) ? "1":"0";
		recurring = (recuring) ? "1":"0"; 

		if(!service.isEmpty() || !identity.isEmpty() || !dueDate.isEmpty()){

			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(5);
			nameValuePair.add(new BasicNameValuePair("Nameofservice",service));
			nameValuePair.add(new BasicNameValuePair("Identity",identity));
			nameValuePair.add(new BasicNameValuePair("PaymentType",regularPay));
			nameValuePair.add(new BasicNameValuePair("Recurringpaymentbase",recurring));
			nameValuePair.add(new BasicNameValuePair("Serviceduedate",dueDate	));

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {

					Uttilities.showToast(getApplicationContext(), data);
					resetFields();

				}
			}, nameValuePair);

			post.execute(Uttilities.PAYMENT_ADD_SERVICES_URL);

		}else{

			Uttilities.showToast(getApplicationContext(), "Please fill all fields.");
		}
	}

	private void resetFields() {

		editService.setText("");
		editIdentity.setText("");
		editDueDate.setText("");
		radioRegular.setChecked(false);
		radioRecurring.setChecked(false);

	}

	OnCheckedChangeListener Regularlistener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {

			if (isChecked) {
				regular = isChecked;
				radioRecurring.setChecked(false);
			}
		}
	};

	OnCheckedChangeListener Recurringlistener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {

			if (isChecked) {
				recuring = isChecked;
				radioRegular.setChecked(false);
			}
		}
	};

	public void setDueDate(View v) {

		showDialog(DATE_DIALOG_ID);

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

			editDueDate.setText(new StringBuilder().append(day).append("-")
					.append(month + 1).append("-").append(year).append(" "));

		}
	};
}
