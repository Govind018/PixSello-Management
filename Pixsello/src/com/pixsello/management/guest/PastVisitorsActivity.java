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
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.pixsello.management.R;
import com.pixsello.management.adapters.PastVisitorsListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class PastVisitorsActivity extends Activity {

	ListView guestList;

	PastVisitorsListAdapter adapter;

	ArrayList<Entity> visitorsData;

	EditText editDateFrom,editDateTo;

	static final int DATE_DIALOG_ID = 999;

	private int year;
	private int month;
	private int day;

	private boolean stayDate;

	EditText editSearch;
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_past_visitors);

		guestList = (ListView) findViewById(R.id.guest_list);
		editSearch = (EditText) findViewById(R.id.edit_search);

		visitorsData = new ArrayList<Entity>();

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
	    dialog = new ProgressDialog(PastVisitorsActivity.this);
		dialog.setMessage("Please Wait..");

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

				editSearch.setText(new StringBuilder().append(day)
						.append("-").append(month + 1).append("-").append(year)
						.append(" "));

			} else {

				//				editToDate.setText(new StringBuilder().append(day)
				//						.append("-").append(month + 1).append("-").append(year)
				//						.append(" "));
			}
		}
	};


	public void doSearch(View v){

		String searchValue = editSearch.getText().toString();

		if(!searchValue.isEmpty())
		{
			dialog.show();
			List<NameValuePair> nameValuePairSearch = new ArrayList<NameValuePair>(
					1);
			nameValuePairSearch.add(new BasicNameValuePair("searchkey",searchValue));
			nameValuePairSearch.add(new BasicNameValuePair("PropertyID",Uttilities.getPROPERTY_ID()));

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {
					try {

						visitorsData.clear();
						
						Entity guest;

						JSONObject obj = new JSONObject(data);

						if (obj.has("error_message")) {
							dialog.cancel();
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

								visitorsData.add(guest);
							}
							dialog.cancel();
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
			post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/pastVisitorSearch");
		}else{

		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		getDataFromServer();
	}

	private void getDataFromServer() {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));

		dialog.show();

		WebRequestPost getData = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {
					visitorsData.clear();
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

						dialog.cancel();
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
		},nameValuePair);

		getData.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/GetAllpastVisitor");
	}

	public void goBack(View v) {
		finish();
	}
}
