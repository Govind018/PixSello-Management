package com.pixsello.management.contact;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.adapters.ContactsListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class EmergencyNumbersActivity extends Activity {

	String type;

	TextView title;

	ListView list;

	ContactsListAdapter adapter;

	ArrayList<ContactDetails> details;

	ContactDetails contact;

	List<NameValuePair> nameValuePair;

	EditText editSearch;

	Spinner searchSpinner;

	ProgressDialog dialog;

	LinearLayout searchLayout;

	Button search;

	TextView quickInfo;

	RelativeLayout layoutError;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_numbers);

		title = (TextView) findViewById(R.id.lbl_title);
		list = (ListView) findViewById(R.id.contact_list);
		editSearch = (EditText) findViewById(R.id.edit_search);
		searchSpinner = (Spinner) findViewById(R.id.search_spinner);
		searchLayout = (LinearLayout) findViewById(R.id.layout_search);
		search = (Button) findViewById(R.id.button_search);
		quickInfo = (TextView) findViewById(R.id.text_quickinfo);
		layoutError = (RelativeLayout) findViewById(R.id.layout_error);

		dialog = new ProgressDialog(EmergencyNumbersActivity.this);
		dialog.setMessage("Please Wait..!");

		// searchLayout = (LinearLayout) findViewById(R.id.search_layout);

		details = new ArrayList<ContactDetails>();

		nameValuePair = new ArrayList<NameValuePair>(1);

		Intent in = getIntent();
		type = in.getStringExtra("type");
		if (type.equalsIgnoreCase("emr")) {
			title.setText(getResources().getString(
					R.string.lbl_emergency_number));
			quickInfo.setVisibility(View.INVISIBLE);
			nameValuePair.add(new BasicNameValuePair("Typeofperson", "1"));
			nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
					.getUserLoginId(getApplicationContext())));
		} else {
			quickInfo.setVisibility(View.VISIBLE);
			title.setText(getResources().getString(R.string.lbl_vendor_number));
			nameValuePair.add(new BasicNameValuePair("Typeofperson", "2"));
			nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
					.getUserLoginId(getApplicationContext())));
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		dialog.show();
		getDataFromServer(Uttilities.CONTACT_GET_URL, nameValuePair);
	}

	public void doSearch(View v) {

		String searchValue = editSearch.getText().toString();
		List<NameValuePair> nameValuePairSearch = new ArrayList<NameValuePair>(
				1);
		nameValuePairSearch
				.add(new BasicNameValuePair("searchkey", searchValue));
		nameValuePairSearch.add(new BasicNameValuePair("PropertyID", Uttilities
				.getUserLoginId(getApplicationContext())));
		if (type.equalsIgnoreCase("emr")) {
			nameValuePairSearch
					.add(new BasicNameValuePair("Typeofperson", "1"));
		} else {
			nameValuePairSearch
					.add(new BasicNameValuePair("Typeofperson", "2"));
		}

		if (searchValue.isEmpty()) {
			Uttilities.showToast(getApplicationContext(), "Enter search key");
			return;
		}

		if (!Uttilities.validate(searchValue)) {
			Uttilities.showToast(getApplicationContext(), "Invalid Input.");
			return;
		}
		dialog.show();

		getDataFromServer(Uttilities.CONTACT_SEARCH_URL, nameValuePairSearch);

	}

	public void showAllData(View v) {
		searchLayout.setVisibility(View.GONE);
		search.setVisibility(View.VISIBLE);
		dialog.show();
		getDataFromServer(Uttilities.CONTACT_GET_URL, nameValuePair);
	}

	public void showSearchOptions(View v) {

		searchLayout.setVisibility(View.VISIBLE);
		search.setVisibility(View.INVISIBLE);

	}

	private void getDataFromServer(String url, List<NameValuePair> parameter) {

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				ContactDetails contact;

				JSONObject obj;
				try {
					obj = new JSONObject(data);

					if (obj.has("error_message")) {

						// Uttilities.showToast(getApplicationContext(),
						// obj.getString("error_message"));
						dialog.cancel();
						list.setVisibility(View.GONE);
						layoutError.setVisibility(View.VISIBLE);
					} else {
						JSONArray jsonArray = obj.getJSONArray("result");
						list.setVisibility(View.VISIBLE);
						details.clear();
						for (int i = 0; i < jsonArray.length(); i++) {
							contact = new ContactDetails();
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							contact.setTypeOfPerson(jsonObj
									.getString("Typeofperson"));
							contact.setServiceDescription(jsonObj
									.getString("ServiceDescription"));
							contact.setContactPerson(jsonObj
									.getString("Contactperson"));
							contact.setContactNumber(jsonObj
									.getString("ContactNumber"));
							contact.setQuickInfo(jsonObj.getString("quickinfo"));
							details.add(contact);
						}

						adapter = new ContactsListAdapter(
								getApplicationContext(),
								R.layout.contact_list_item, details);
						list.setAdapter(adapter);
						dialog.cancel();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, parameter);

		post.execute(url);
	}

	public void addEmergencyNumber(View v) {

		Intent emIntent = new Intent(getApplicationContext(),
				AddContactNumberActivity.class);
		if (type.equalsIgnoreCase("emr")) {
			emIntent.putExtra("type", "emergency");
		} else {
			emIntent.putExtra("type", "vendor");
		}
		startActivity(emIntent);
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void goBack(View v) {
		finish();
	}
}
