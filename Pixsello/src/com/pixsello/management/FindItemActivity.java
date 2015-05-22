package com.pixsello.management;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pixsello.management.adapters.FoundItemsListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class FindItemActivity extends Activity {

	EditText item;
	EditText location;
	EditText approxDate;

	ListView list;

	FoundItemsListAdapter adapter;

	ArrayList<Entity> foundItems;

	LinearLayout layoutHeader;

	ProgressDialog dialog;

	static final int DATE_DIALOG_ID = 999;

	private int year;
	private int month;
	private int day;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_item);

		list = (ListView) findViewById(R.id.list_found_items);

		dialog = new ProgressDialog(FindItemActivity.this);
		dialog.setMessage("Please Wait..");

		layoutHeader = (LinearLayout) findViewById(R.id.layout_header);

		foundItems = new ArrayList<Entity>();

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		initLayout();
	}

	@Override
	protected void onStart() {
		super.onStart();

		getAllItems();

	}

	private void getAllItems() {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getPROPERTY_ID()));

		final ProgressDialog dialog = new ProgressDialog(FindItemActivity.this);
		dialog.setMessage("Please Wait..");
		dialog.show();
		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				populateDate(data);
				dialog.cancel();

			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getReportanitem");
	}

	public void showDate(View v) {

		showDialog(DATE_DIALOG_ID);
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

			approxDate.setText(new StringBuilder().append(day).append("-")
					.append(month + 1).append("-").append(year).append(" "));
		}
	};

	public void goBack(View v) {
		finish();
	}

	private void initLayout() {

		item = (EditText) findViewById(R.id.edit_item);
		location = (EditText) findViewById(R.id.edit_location);
		approxDate = (EditText) findViewById(R.id.edit_date);

	}

	public void doSubmitItem(View v) {

		if (item.getText().toString().isEmpty()) {
			Uttilities.showToast(getApplicationContext(), "Please enter the item name.");
			return;
		}

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getPROPERTY_ID()));
		nameValuePair.add(new BasicNameValuePair("searchkey", item.getText()
				.toString()));

		dialog.show();

		WebRequestPost postData = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				populateDate(data);

			}
		}, nameValuePair);

		postData.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/Findanitem");
	}

	public void populateDate(String data) {

		try {

			Entity item;
			JSONObject obj = new JSONObject(data);

			if (obj.has("error_message")) {
				dialog.cancel();
				Uttilities.showToast(getApplicationContext(),
						obj.getString("error_message"));

			} else {

				JSONArray jsonArray = obj.getJSONArray("result");

				foundItems.clear();
				for (int i = 0; i < jsonArray.length(); i++) {
					item = new Entity();
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					item.setItemID(jsonObj.getString("ID"));
					item.setDate(jsonObj.getString("Date"));
					item.setTime(jsonObj.getString("Time"));
					item.setDescription(jsonObj.getString("Discriptionofitem"));
					item.setLocation(jsonObj.getString("Locationwherefound"));
					item.setStaffName(jsonObj.getString("Staffwhofound"));
					item.setStayDateFrom(jsonObj.getString("Gueststaydatefrom"));
					item.setStayDateTo(jsonObj.getString("Gueststaydateto"));
					item.setPhoto(jsonObj.getString("Photo"));

					foundItems.add(item);
				}
				layoutHeader.setVisibility(View.VISIBLE);

				dialog.cancel();
				adapter = new FoundItemsListAdapter(getApplicationContext(),
						R.layout.found_list_item, foundItems);
				list.setAdapter(adapter);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
