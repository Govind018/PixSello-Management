package com.pixsello.management.training;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.pixsello.management.R;
import com.pixsello.management.adapters.StaffListAdapter;
import com.pixsello.management.connectivity.GetDataFromServer;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class StaffProfileActivity extends Activity {

	ListView listOfStaff;

	StaffListAdapter adapter;
	
	EditText editSearch;

	Spinner searchSpinner;

	ArrayList<StaffDetails> staffDetails;
	
	RelativeLayout searchLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_staff_profile);

		listOfStaff = (ListView) findViewById(R.id.list_staff);
		staffDetails = new ArrayList<StaffDetails>();
		editSearch = (EditText) findViewById(R.id.edit_search);
		searchSpinner = (Spinner) findViewById(R.id.search_spinner);
		searchLayout = (RelativeLayout) findViewById(R.id.layout_search);
		
		adapter = new StaffListAdapter(getApplicationContext(),
				R.layout.staff_list_item, staffDetails);

	}

	@Override
	protected void onStart() {
		super.onStart();

		getDataFromServer();
	}
	
	public void showSearchOptions(View v){
		
		searchLayout.setVisibility(View.VISIBLE);
		
	}
	
	public void showAllData(View v) {
		searchLayout.setVisibility(View.GONE);
		getDataFromServer();
	}

	
	public void doSearch(View v) {
		
		String searchKey = "";
		switch (searchSpinner.getSelectedItemPosition()) {
		case 0:
			searchKey = "Trainer";
			break;

		case 1:
			searchKey = "Trainee";
			break;

		case 2:
			searchKey = "Type";
			break;
		default:
			break;
		}

		String searchValue = editSearch.getText().toString();
		List<NameValuePair> nameValuePairSearch = new ArrayList<NameValuePair>(
				1);
		nameValuePairSearch.add(new BasicNameValuePair(searchKey, searchValue));
		
		WebRequestPost post = new WebRequestPost(new IWebRequest() {
			
			@Override
			public void onDataArrived(String data) {
				
				JSONObject obj;
				StaffDetails staff;
				try {
					obj = new JSONObject(data);

					if (obj.has("error_message")) {

						Uttilities.showToast(getApplicationContext(),
								obj.getString("error_message"));

					} else {

						JSONArray jsonArray = obj.getJSONArray("result");
						staffDetails.clear();

						for (int i = 0; i < jsonArray.length(); i++) {
							staff = new StaffDetails();
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							staff.setTrainerName(jsonObj.getString("Trainer"));
							staff.setTraineeName(jsonObj.getString("Trainee"));
							staff.setTime(jsonObj.getString("Time"));
							staff.setDate(jsonObj.getString("Date"));
							staff.setType(jsonObj.getString("Type"));
							staff.setOther(jsonObj.getString("Other"));

							staffDetails.add(staff);
						}

						adapter = new StaffListAdapter(getApplicationContext(),
								R.layout.staff_list_item, staffDetails);
						listOfStaff.setAdapter(adapter);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		}, nameValuePairSearch);
		
		post.execute(Uttilities.STAFF_SEARCH_TRAINEE);
	}

	private void getDataFromServer() {
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(6);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));

		WebRequestPost getData = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				StaffDetails staff;

				JSONObject obj;
				try {
					obj = new JSONObject(data);

					if (obj.has("error_message")) {

						Uttilities.showToast(getApplicationContext(),
								obj.getString("error_message"));

					} else {

						JSONArray jsonArray = obj.getJSONArray("result");
						staffDetails.clear();

						for (int i = 0; i < jsonArray.length(); i++) {
							staff = new StaffDetails();
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							staff.setTrainerName(jsonObj.getString("Trainer"));
							staff.setTraineeName(jsonObj.getString("Trainee"));
							staff.setTime(jsonObj.getString("Time"));
							staff.setDate(jsonObj.getString("Date"));
							staff.setType(jsonObj.getString("Type"));
							staff.setOther(jsonObj.getString("Other"));

							staffDetails.add(staff);
						}

						adapter = new StaffListAdapter(getApplicationContext(),
								R.layout.staff_list_item, staffDetails);
						listOfStaff.setAdapter(adapter);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		},nameValuePair);

		getData.execute(Uttilities.TRAINING_STAFF_URL);
	}

	public void goBack(View v) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.staff_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
