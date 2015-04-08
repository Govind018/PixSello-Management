package com.pixsello.management.guest;

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
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.pixsello.management.R;
import com.pixsello.management.adapters.PastVisitorsListAdapter;
import com.pixsello.management.connectivity.GetDataFromServer;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class PastVisitorsActivity extends Activity {

	ListView guestList;

	PastVisitorsListAdapter adapter;

	ArrayList<Entity> visitorsData;
	
	RelativeLayout layoutSerach;
	
	EditText editDateFrom,editDateTo;
	
	static final int DATE_DIALOG_ID = 999;
	
	private int year;
	private int month;
	private int day;

	private boolean stayDate;
	
	EditText editFromDate;
	EditText editToDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_past_visitors);

		guestList = (ListView) findViewById(R.id.guest_list);
		layoutSerach = (RelativeLayout) findViewById(R.id.layout_search);
		editFromDate = (EditText) findViewById(R.id.edit_date);
		editToDate = (EditText) findViewById(R.id.editText1);
		
		visitorsData = new ArrayList<Entity>();
		
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
		// Toast.makeText(getApplicationContext(), "" + Uttilities.getDate(),
		// Toast.LENGTH_SHORT).show();
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

				editFromDate.setText(new StringBuilder().append(day)
						.append("-").append(month + 1).append("-").append(year)
						.append(" "));

			} else {

				editToDate.setText(new StringBuilder().append(day)
						.append("-").append(month + 1).append("-").append(year)
						.append(" "));
			}
		}
	};

	
	public void showSearchOptions(View v){
		
		layoutSerach.setVisibility(View.VISIBLE);
	}
	
	public void showAllData(View v){
		
		layoutSerach.setVisibility(View.GONE);
		getDataFromServer();
	}
	
	public void testMethod(View v){
		
		String fromDate = editFromDate.getText().toString();
		String toDate =  editToDate.getText().toString();
		
		if(!fromDate.isEmpty() && !toDate.isEmpty())
		{
			List<NameValuePair> nameValuePairSearch = new ArrayList<NameValuePair>(
					1);
			nameValuePairSearch.add(new BasicNameValuePair("date_from",fromDate));
			nameValuePairSearch.add(new BasicNameValuePair("date_to",toDate));
			
			WebRequestPost post = new WebRequestPost(new IWebRequest() {
				
				@Override
				public void onDataArrived(String data) {
					try {

						Entity guest;

						JSONObject obj = new JSONObject(data);

						if (obj.has("error_message")) {

							Uttilities.showToast(getApplicationContext(),
									obj.getString("error_message"));

						} else {

							JSONArray jsonArray = obj.getJSONArray("result");

							for (int i = 0; i < jsonArray.length(); i++) {
								guest = new Entity();
								JSONObject jsonObj = jsonArray.getJSONObject(i);
								guest.setDate(jsonObj.getString("Date"));
								guest.setTime(jsonObj.getString("Time"));
								guest.setGuestName(jsonObj.getString("GuestName"));
								guest.setCompanyName(jsonObj.getString("Company"));
								guest.setGender(jsonObj.getString("Gender"));
								guest.setPhoto(jsonObj.getString("Photo"));
								guest.setInTime(jsonObj.getString("InTime"));
								guest.setOutTime(jsonObj.getString("OutTime"));

								// byte[] bytearray =
								// Base64.decode(jsonObj.getString("Photo"), 0);

								visitorsData.add(guest);
							}

							adapter = new PastVisitorsListAdapter(
									getApplicationContext(),
									R.layout.visitors_list_item, visitorsData);
							guestList.setAdapter(adapter);

							System.out.println(jsonArray);

						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, nameValuePairSearch);
			post.execute(Uttilities.PAST_VISITORS_SEARCH);
		}else{
			
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

//		getDataFromServer();
	}

	private void getDataFromServer() {

		GetDataFromServer getData = new GetDataFromServer(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {

					Entity guest;

					JSONObject obj = new JSONObject(data);

					if (obj.has("error_message")) {

						Uttilities.showToast(getApplicationContext(),
								obj.getString("error_message"));

					} else {

						JSONArray jsonArray = obj.getJSONArray("result");

						for (int i = 0; i < jsonArray.length(); i++) {
							guest = new Entity();
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							guest.setDate(jsonObj.getString("Date"));
							guest.setTime(jsonObj.getString("Time"));
							guest.setGuestName(jsonObj.getString("GuestName"));
							guest.setCompanyName(jsonObj.getString("Company"));
							guest.setGender(jsonObj.getString("Gender"));
							guest.setPhoto(jsonObj.getString("Photo"));
							guest.setInTime(jsonObj.getString("InTime"));
							guest.setOutTime(jsonObj.getString("OutTime"));

							// byte[] bytearray =
							// Base64.decode(jsonObj.getString("Photo"), 0);

							visitorsData.add(guest);
						}

						adapter = new PastVisitorsListAdapter(
								getApplicationContext(),
								R.layout.visitors_list_item, visitorsData);
						guestList.setAdapter(adapter);

						System.out.println(jsonArray);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		getData.execute(Uttilities.GUEST_VISITOR_LIST_URL);
	}

	public void goBack(View v) {
		finish();
	}
}
