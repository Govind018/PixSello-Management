package com.spundhan.pixsello.payment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.pixsello.management.R;
import com.pixsello.management.adapters.NewPaymentListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class NewPaymentsActivity extends Activity {

	ArrayList<String> servicesList;

	ArrayList<String> identityList;

	Spinner spinnerServices;
	Spinner spinnerIdentity;

	ArrayList<Entity> listOfServices;

	ArrayAdapter<String> serviceAdapter;

	ArrayAdapter<String> identityAdapter;

	NewPaymentListAdapter adapter;

	ArrayList<Entity> statusDetails;

	ListView list;

	EditText editSearch;
	
	ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_status);

		initLayout();
	}

	private void initLayout() {

		listOfServices = new ArrayList<Entity>();
		list = (ListView) findViewById(R.id.list_status);
		statusDetails = new ArrayList<Entity>();
		editSearch = (EditText) findViewById(R.id.edit_search);
		adapter = new NewPaymentListAdapter(
				getApplicationContext(),
				R.layout.new_payment_list_item,
				statusDetails);
		list.setAdapter(adapter);
		dialog = new ProgressDialog(NewPaymentsActivity.this);
		dialog.setMessage("Please Wait..");

	}

	public void goBack(View v) {
		finish();
	}

	@Override
	protected void onStart() {
		super.onStart();

		getAllData();
	}

	private void getAllData() {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));
		
		populateData(nameValuePair,Uttilities.PAYMENT_NEWPAYMENT);

	}

	public void populateData(List<NameValuePair> nameValuePair, String url){
		
		dialog.show();

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				Entity entity;
				JSONObject jsonObj;

				statusDetails.clear();
				try {
					jsonObj = new JSONObject(data);
					if (jsonObj.has("error_message")) {
						dialog.cancel();
						Uttilities.showToast(getApplicationContext(),
								jsonObj.getString("error_message"));
						statusDetails.clear();
						adapter.notifyDataSetChanged();
					} else {
						JSONArray jsonArray = jsonObj.getJSONArray("result");
						for (int i = 0; i < jsonArray.length(); i++) {
							entity = new Entity();
							JSONObject obj = jsonArray.getJSONObject(i);
							entity.setServiceName(obj.getString("Nameofservice"));
							entity.setIdentity(obj.getString("Identity"));
							entity.setType(getPaymentType(Integer.parseInt(obj.getString("Paymentmode"))));
							entity.setAmount(obj.getString("Amount"));
							String billDate = obj.has("BillDate") ? obj.getString("BillDate") : ""; 
							entity.setDueDate(billDate);
							String billStatus = obj.has("BillingStatus") ? obj.getString("BillingStatus") : "";
							entity.setStatus(billStatus);
							String serviceID = obj.has("ServiceID") ? obj.getString("ServiceID") : "";
							entity.setServiceId(serviceID);
							String identityID = obj.has("IdentityID") ? obj.getString("IdentityID") : "";
							entity.setIdentityID(identityID);
							String billNumber = obj.has("BillNo") ? obj.getString("BillNo") : "";
							entity.setBillNum(billNumber);
							entity.setType(getPaymentType(Integer.parseInt(obj.getString("PaymentType"))));

							statusDetails.add(entity);
						}
						dialog.cancel();
						adapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair);

		post.execute(url);
	}

	public void doSearch(View v) {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("searchkey", editSearch.getText().toString()));
		
		populateData(nameValuePair, Uttilities.PAYMENT_NEWPAYMENT_SEARCH);

	}

	public String getPaymentType(int number){

		switch (number) {
		case 1:
			return "Cash";
		case 2:
			return "Cheque";
		case 3:
			return "Other";
		default:
			break;
		}

		return "";
	}

	private void getServicesAndIdentity() {

		final ProgressDialog dialog = new ProgressDialog(NewPaymentsActivity.this);
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

						serviceAdapter.notifyDataSetChanged();
						identityAdapter.notifyDataSetChanged();

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		},nameValuePair);

		get.execute(Uttilities.PAYMENT_GET_SERVICES_IDENTITY);
	}
}
