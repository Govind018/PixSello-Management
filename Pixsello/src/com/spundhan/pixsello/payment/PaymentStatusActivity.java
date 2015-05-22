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

import com.pixsello.management.R;
import com.pixsello.management.adapters.PaymentStatusListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class PaymentStatusActivity extends Activity {

	ArrayList<String> servicesList;

	ArrayList<String> identityList;

	ArrayList<Entity> listOfServices;

	ArrayAdapter<String> serviceAdapter;

	ArrayAdapter<String> identityAdapter;

	PaymentStatusListAdapter adapter;

	ArrayList<Entity> statusDetails;

	EditText searchKey;

	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_status_new);

		servicesList = new ArrayList<String>();
		identityList = new ArrayList<String>();
		listOfServices = new ArrayList<Entity>();

		list = (ListView) findViewById(R.id.list_search_items);
		searchKey = (EditText) findViewById(R.id.edit_search);

		statusDetails = new ArrayList<Entity>();

	}

	public void goBack(View v) {
		finish();
	}

	@Override
	protected void onStart() {
		super.onStart();

		getData();
	}

	public void doSubmit(View v) {

		String url = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getNewPaymentSearch";

		String searchKeyVal = searchKey.getText().toString();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("searchkey", searchKeyVal));

		populateData(url, nameValuePair);

	}

	private void getData() {

		String url = "http://pixsello.in/qualitymaintenanceapp/index.php/webapp/newPayment";
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
		populateData(url, nameValuePair);
	}

	private void populateData(String url, List<NameValuePair> nameValuePair) {

		final ProgressDialog dialog = new ProgressDialog(
				PaymentStatusActivity.this);
		dialog.setMessage("Please Wait..");
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
						Uttilities.showToast(getApplicationContext(),
								jsonObj.getString("error_message"));
					} else {
						JSONArray jsonArray = jsonObj.getJSONArray("result");
						for (int i = 0; i < jsonArray.length(); i++) {
							entity = new Entity();
							JSONObject obj = jsonArray.getJSONObject(i);
							entity.setServiceName(obj
									.getString("Nameofservice"));
							entity.setIdentity(obj.getString("Identity"));
							entity.setAmount(obj.getString("Amount"));
							entity.setDueDate(obj.getString("Billduedate"));
							entity.setStatus(obj.getString("BillingStatus"));
							entity.setBillNum(obj.getString("BillNo"));
							entity.setBillDate(obj.getString("BillDate"));

							statusDetails.add(entity);
						}

						dialog.cancel();

						adapter = new PaymentStatusListAdapter(
								getApplicationContext(),
								R.layout.payment_status_list_item,
								statusDetails);
						list.setAdapter(adapter);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair);

		post.execute(url);
	}
}
