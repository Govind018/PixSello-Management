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
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.pixsello.management.R;
import com.pixsello.management.adapters.StaffListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class StaffProfileActivity extends Activity {

	ListView listOfStaff;

	StaffListAdapter adapter;

	EditText editSearch;

	Spinner searchSpinner;

	ArrayList<StaffDetails> staffDetails;

	RelativeLayout layoutError;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_staff_profile);

		listOfStaff = (ListView) findViewById(R.id.list_staff);
		staffDetails = new ArrayList<StaffDetails>();
		editSearch = (EditText) findViewById(R.id.edit_search);
		searchSpinner = (Spinner) findViewById(R.id.search_spinner);

		layoutError = (RelativeLayout) findViewById(R.id.layout_error);

		adapter = new StaffListAdapter(getApplicationContext(),
				R.layout.staff_list_item, staffDetails);
		listOfStaff.setAdapter(adapter);

	}

	@Override
	protected void onStart() {
		super.onStart();

		getDataFromServer();
	}

	public void doSearch(View v) {

		String searchValue = editSearch.getText().toString();

		if (searchValue.isEmpty()) {
			return;
		}

		List<NameValuePair> nameValuePairSearch = new ArrayList<NameValuePair>(
				1);
		nameValuePairSearch.add(new BasicNameValuePair("PropertyID", Uttilities
				.getUserLoginId(getApplicationContext())));
		nameValuePairSearch
				.add(new BasicNameValuePair("searchkey", searchValue));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				populateData(data);

				// JSONObject obj;
				// StaffDetails staff;
				// try {
				// obj = new JSONObject(data);
				//
				// if (obj.has("error_message")) {
				//
				// listOfStaff.setVisibility(View.GONE);
				// layoutError.setVisibility(View.VISIBLE);
				// adapter.notifyDataSetChanged();
				//
				// // Uttilities.showToast(getApplicationContext(),
				// // obj.getString("error_message"));
				//
				// } else {
				//
				// JSONArray jsonArray = obj.getJSONArray("result");
				// staffDetails.clear();
				//
				// for (int i = 0; i < jsonArray.length(); i++) {
				// staff = new StaffDetails();
				// JSONObject jsonObj = jsonArray.getJSONObject(i);
				// staff.setTrainerName(jsonObj.getString("Trainer"));
				// staff.setTraineeName(jsonObj.getString("Trainee"));
				// staff.setTime(jsonObj.getString("Time"));
				// staff.setDate(jsonObj.getString("Date"));
				// staff.setType(jsonObj.getString("Type"));
				// staff.setOther(jsonObj.getString("Other"));
				// staff.setTraineeHrs(jsonObj.getString("Timeoftraineehrs"));
				// staff.setTraineeMins(jsonObj.getString("Timeoftraineemin"));
				//
				// staffDetails.add(staff);
				// }
				// adapter.notifyDataSetChanged();
				// }
				// } catch (JSONException e) {
				// e.printStackTrace();
				// }
			}
		}, nameValuePairSearch);

		post.execute(Uttilities.TRAINING_STAFF_SEARCH);
	}

	private void getDataFromServer() {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getUserLoginId(getApplicationContext())));

		WebRequestPost getData = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				populateData(data);

				// StaffDetails staff;
				//
				// JSONObject obj;
				// try {
				// obj = new JSONObject(data);
				//
				// if (obj.has("error_message")) {
				//
				// Uttilities.showToast(getApplicationContext(),
				// obj.getString("error_message"));
				//
				// } else {
				//
				// JSONArray jsonArray = obj.getJSONArray("result");
				// staffDetails.clear();
				//
				// for (int i = 0; i < jsonArray.length(); i++) {
				// staff = new StaffDetails();
				// JSONObject jsonObj = jsonArray.getJSONObject(i);
				// staff.setTrainerName(jsonObj.getString("Trainer"));
				// staff.setTraineeName(jsonObj.getString("Trainee"));
				// staff.setTime(jsonObj.getString("Time"));
				// staff.setDate(jsonObj.getString("Date"));
				// staff.setType(jsonObj.getString("Type"));
				// staff.setOther(jsonObj.getString("Other"));
				// staff.setTraineeHrs(jsonObj.getString("Timeoftraineehrs"));
				// staff.setTraineeMins(jsonObj.getString("Timeoftraineemin"));
				//
				// staffDetails.add(staff);
				// }
				//
				// adapter = new StaffListAdapter(getApplicationContext(),
				// R.layout.staff_list_item, staffDetails);
				// listOfStaff.setAdapter(adapter);
				// }
				// } catch (JSONException e) {
				// e.printStackTrace();
				// }
			}
		}, nameValuePair);

		getData.execute(Uttilities.TRAINING_STAFF_URL);
	}

	public void goBack(View v) {
		finish();
	}

	public void populateData(String data) {

		StaffDetails staff;

		JSONObject obj;
		try {
			obj = new JSONObject(data);

			if (obj.has("error_message")) {

//				Uttilities.showToast(getApplicationContext(),
//						obj.getString("error_message"));
				listOfStaff.setVisibility(View.GONE);
				layoutError.setVisibility(View.VISIBLE);
				adapter.notifyDataSetChanged();

			} else {

				JSONArray jsonArray = obj.getJSONArray("result");
				staffDetails.clear();
				listOfStaff.setVisibility(View.VISIBLE);

				for (int i = 0; i < jsonArray.length(); i++) {
					staff = new StaffDetails();
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					staff.setTrainerName(jsonObj.getString("Trainer"));
					staff.setTraineeName(jsonObj.getString("Trainee"));
					staff.setTime(jsonObj.getString("Time"));
					staff.setDate(jsonObj.getString("Date"));
					staff.setType(jsonObj.getString("Type"));
					staff.setOther(jsonObj.getString("Other"));
					staff.setTraineeHrs(jsonObj.getString("Timeoftraineehrs"));
					staff.setTraineeMins(jsonObj.getString("Timeoftraineemin"));

					staffDetails.add(staff);
				}

				adapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
