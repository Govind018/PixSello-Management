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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class AddServicesActivity extends Activity {

	static final int DATE_DIALOG_ID = 999;
	private int year;
	private int month;
	private int day;

	EditText editIdentity, editDueDate;

	RadioButton radioRegular, radioRecurring;

	boolean regular;

	boolean recuring;
	
	Spinner spinnerServices;

	String service;
	String identity;
	String dueDate;
	String regularPay;
	String recurring;
	
	int serviceId;
	
	ArrayList<String> listOfServices;
	
	ArrayAdapter<String> servicesAdapter;
	
	ArrayList<String> existingServices;

	ProgressDialog dialog ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_services);

		spinnerServices = (Spinner) findViewById(R.id.edit_service);
		editIdentity = (EditText) findViewById(R.id.edit_identity);
		editDueDate = (EditText) findViewById(R.id.due_date);
		radioRegular = (RadioButton) findViewById(R.id.radio_type_payment_regular);
		radioRecurring = (RadioButton) findViewById(R.id.radio_type_payment_recurring);
		radioRegular.setOnCheckedChangeListener(Regularlistener);
		radioRecurring.setOnCheckedChangeListener(Recurringlistener);

		listOfServices = new ArrayList<String>();
		existingServices = new ArrayList<String>();
		
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
		dialog = new ProgressDialog(AddServicesActivity.this);
		dialog.setMessage("Please Wait..");
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		
		getAllservices();
	}
	
	
	private void getAllservices() {

		dialog.show();
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));
		
		WebRequestPost post = new WebRequestPost(new IWebRequest() {
			
			@Override
			public void onDataArrived(String data) {
				
				try {
					existingServices.clear();
					JSONObject obj = new JSONObject(data);
					JSONArray jsonArray = obj.getJSONArray("result");
					
					for(int i = 0; i < jsonArray.length(); i++){
						
						JSONObject jsonObj = jsonArray.getJSONObject(i);
						existingServices.add(jsonObj.getString("Nameofservice"));
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				dialog.cancel();
				
				servicesAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, existingServices);
				spinnerServices.setAdapter(servicesAdapter);
				
			}
		}, nameValuePair);
		
		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getAllservice");
	}


	public void addNewService(View v){
		
		final Dialog d = new Dialog(AddServicesActivity.this);
		d.setContentView(R.layout.new_service_dialog);
		d.setTitle("Add New Service.");

		final EditText editServiceName = (EditText) d.findViewById(R.id.edit_service_name);
		Button btn = (Button) d.findViewById(R.id.btn_add_service);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String name = editServiceName.getText().toString();
				if(!name.isEmpty()){
					postNewService(name);
					d.cancel();
				}else{

					Uttilities.showToast(getApplicationContext(), "Type something to add service.");
				}
			}
		});
		d.show();
	}
	
	
	public void postNewService(final String nameOfService){

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("Nameofservice", nameOfService));
		
		WebRequestPost post = new WebRequestPost(new IWebRequest() {
			
			@Override
			public void onDataArrived(String data) {
				
				Uttilities.showToast(getApplicationContext(), data);
				existingServices.add(nameOfService);
				
				servicesAdapter.notifyDataSetChanged();
//				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, listOfServices);
//				spinnerServices.setAdapter(adapter);
//				
			}
		}, nameValuePair);
		
		post.execute(Uttilities.PAYMENT_ADD_NEW_SERVICES_URL);
	}

	public void doSubmitItem(View v){

		service = spinnerServices.getSelectedItem().toString();
		serviceId = spinnerServices.getSelectedItemPosition() + 1;
		identity = editIdentity.getText().toString();
		dueDate = editDueDate.getText().toString();
		regularPay = (regular) ? "1":"0";
		recurring = (recuring) ? "1":"0";
		
		dialog.show();

		if(!service.isEmpty() || !identity.isEmpty() || !dueDate.isEmpty()){

			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(6);
			nameValuePair.add(new BasicNameValuePair("PropertyID",Uttilities.PROPERTY_ID));
			nameValuePair.add(new BasicNameValuePair("ServiceID",String.valueOf(serviceId)));
			nameValuePair.add(new BasicNameValuePair("Nameofservice",service));
			nameValuePair.add(new BasicNameValuePair("Identity",identity));
			nameValuePair.add(new BasicNameValuePair("PaymentType",regularPay));
			nameValuePair.add(new BasicNameValuePair("Recurringpaymentbase",recurring));
			nameValuePair.add(new BasicNameValuePair("Serviceduedate",dueDate	));

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {
					dialog.cancel();
					Uttilities.showToast(getApplicationContext(), data);

					finish();
				}
			}, nameValuePair);
			

			post.execute(Uttilities.PAYMENT_ADD_SERVICES_URL);

		}else{

			Uttilities.showToast(getApplicationContext(), "Please fill all fields.");
		}
	}

	private void resetFields() {

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
