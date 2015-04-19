package com.spundhan.pixsello.payment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.pixsello.management.R;
import com.pixsello.management.adapters.PaymentSearchListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class PaymentSearchActivity extends Activity {

	EditText editSearch;

	ListView listOfSearchItems;

	ArrayList<Entity> searchItems;
	
	PaymentSearchListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_search);

		editSearch = (EditText) findViewById(R.id.edit_search);
		listOfSearchItems = (ListView) findViewById(R.id.list_search_items);

		searchItems = new ArrayList<Entity>();

	}
	
	public void doSubmit(View v) {

		String searchKey = editSearch.getText().toString();

		if (!searchKey.isEmpty()) {

			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
			nameValuePair.add(new BasicNameValuePair("searchkey", searchKey));
			nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {

					Entity entity;
					JSONObject jsonObj;

					searchItems.clear();
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
//								entity.setServiceName(obj.getString(""));
//								entity.setIdentity(obj.getString(""));
								entity.setBillNum(obj.getString("BillNo"));
								entity.setBillDate(obj.getString("BillDate"));
								entity.setAmount(obj.getString("Amount"));
								entity.setDueDate(obj.getString("Billduedate"));
								entity.setStatus(obj.getString("BillingStatus"));

								searchItems.add(entity);

							}

							 adapter = new PaymentSearchListAdapter(
							 getApplicationContext(),
							 R.layout.payment_search_list_item,
							 searchItems);
							 listOfSearchItems.setAdapter(adapter);

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, nameValuePair);
			
			post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/billSearch");
		}
	}

	public void goBack(View v) {
		finish();
	}
}
