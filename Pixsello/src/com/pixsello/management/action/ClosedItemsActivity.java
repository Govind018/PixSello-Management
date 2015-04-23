package com.pixsello.management.action;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.pixsello.management.R;
import com.pixsello.management.adapters.ClosedItemsListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class ClosedItemsActivity extends Activity {

	ListView listOfClosedItems;

	ClosedItemsListAdapter adapter;

	ArrayList<Entity> items;

	RelativeLayout layout;

	Button btnSearch;

	EditText dateFrom;

	EditText dateTo;

	private int year;
	private int month;
	private int day;

	private boolean stayDate;

	static final int DATE_DIALOG_ID = 999;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_closed_items_list);

		listOfClosedItems = (ListView) findViewById(R.id.closed_items_list);
		btnSearch = (Button) findViewById(R.id.btn_search);
		layout = (RelativeLayout) findViewById(R.id.layout_search);

		dateFrom = (EditText) findViewById(R.id.edit_date);
		items = new ArrayList<Entity>();

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

	}

	public void setStayFromDate(View v) {
		stayDate = true;
		showDialog(DATE_DIALOG_ID);
	}

	public void setStayToDate(View v) {
		stayDate = false;
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

			if (stayDate) {

				dateFrom.setText(new StringBuilder().append(day).append("-")
						.append(month + 1).append("-").append(year).append(" "));

			} else {

				dateTo.setText(new StringBuilder().append(day).append("-")
						.append(month + 1).append("-").append(year).append(" "));
			}
		}
	};

	public void showSearchOptions(View v) {

		layout.setVisibility(View.VISIBLE);
		btnSearch.setVisibility(View.INVISIBLE);

	}

	public void showAllData(View v) {

		layout.setVisibility(View.GONE);
		btnSearch.setVisibility(View.VISIBLE);

	}

	public void searchData(View v) {

		String fromDate = dateFrom.getText().toString();

		if (!fromDate.isEmpty()) {

			List<NameValuePair> nameValuePairSearch = new ArrayList<NameValuePair>(
					3);
			nameValuePairSearch.add(new BasicNameValuePair("propertyID",
					Uttilities.getPROPERTY_ID()));
			nameValuePairSearch.add(new BasicNameValuePair("searchkey",
					fromDate));

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {
					try {
						Entity item;

						JSONObject obj = new JSONObject(data);

						if (obj.has("error_message")) {
							Uttilities.showToast(getApplicationContext(),
									obj.getString("error_message"));
						} else {

							JSONArray jsonArray = obj.getJSONArray("result");

							items.clear();
							for (int i = 0; i < jsonArray.length(); i++) {
								item = new Entity();
								JSONObject jsonObj = jsonArray.getJSONObject(i);
								item.setItemID(jsonObj.getString("ID"));
								item.setDate(jsonObj.getString("Date"));
								item.setTime(jsonObj.getString("Time"));
								item.setDescription(jsonObj
										.getString("Description"));
								item.setLocation(jsonObj.getString("Where"));
								item.setReported(jsonObj.getString("Reported"));
								item.setResponsibility(jsonObj
										.getString("Responsibility"));
								item.setActionTaken(jsonObj
										.getString("Actiontaken"));

								items.add(item);
							}

							adapter = new ClosedItemsListAdapter(
									getApplicationContext(),
									R.layout.closed_list_item, items);
							listOfClosedItems.setAdapter(adapter);

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, nameValuePairSearch);
			
			post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/closeItemSearch");
		} else {

			Uttilities.showToast(getApplicationContext(),
					"Enter key to search.");

		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		getDataFromServer();
	}

	private void getDataFromServer() {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.getPROPERTY_ID()));
		
		final ProgressDialog dialog = new  ProgressDialog(ClosedItemsActivity.this);
		dialog.setMessage("Please Wait.");
		dialog.show();

		WebRequestPost getData = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {
					Entity item;

					JSONObject obj = new JSONObject(data);

					if (obj.has("error_message")) {
						dialog.cancel();
						Uttilities.showToast(getApplicationContext(),
								obj.getString("error_message"));
					} else {

						JSONArray jsonArray = obj.getJSONArray("result");

						items.clear();
						for (int i = 0; i < jsonArray.length(); i++) {
							item = new Entity();
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							item.setItemID(jsonObj.getString("ID"));
							item.setDate(jsonObj.getString("Date"));
							item.setTime(jsonObj.getString("Time"));
							item.setDescription(jsonObj
									.getString("Description"));
							item.setLocation(jsonObj.getString("Where"));
							item.setReported(jsonObj.getString("Reported"));
							item.setResponsibility(jsonObj
									.getString("Responsibility"));
							item.setActionTaken(jsonObj
									.getString("Actiontaken"));

							items.add(item);
						}

						dialog.cancel();
						adapter = new ClosedItemsListAdapter(
								getApplicationContext(),
								R.layout.closed_list_item, items);
						listOfClosedItems.setAdapter(adapter);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair);
		getData.execute(Uttilities.CLOSED_ITEMS_LIST_URL);
	}

	public void goBack(View v) {
		finish();
	}
}
