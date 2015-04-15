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
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import com.pixsello.management.R;
import com.pixsello.management.adapters.OutEntryListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class MakeOutEntryActivity extends Activity {

	ListView guestList;

	OutEntryListAdapter adapter;

	ArrayList<Entity> visitorsData;

	static final int TIME_DIALOG_ID = 999;

	private int hour;
	private int minute;

	static String ID;

	EditText editTime,editRoom;

	static LinearLayout layoutTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_out_entry);

		guestList = (ListView) findViewById(R.id.active_items_list);

		editTime = (EditText) findViewById(R.id.edit_time);
		editRoom = (EditText) findViewById(R.id.edit_room);
		layoutTime = (LinearLayout) findViewById(R.id.layout_out_entry);
		visitorsData = new ArrayList<Entity>();

		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

	}

	public void updateTime(View v) {

		String time = editTime.getText().toString();
		String room = editRoom.getText().toString();

		if (!time.isEmpty()) {

			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
			nameValuePair.add(new BasicNameValuePair("ID", ID));
			nameValuePair.add(new BasicNameValuePair("Roomno", room));
			nameValuePair.add(new BasicNameValuePair("OutTime", time));
			nameValuePair.add(new BasicNameValuePair("PropertyID",Uttilities.PROPERTY_ID));
			
			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {

					try {
						JSONObject json = new JSONObject(data);
						Uttilities.showToast(getApplicationContext(),
								json.getString("result"));
						layoutTime.setVisibility(View.GONE);
						getDataFromServer();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, nameValuePair);

			post.execute(Uttilities.UPDATE_OUT_TIME_URL);

		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(MakeOutEntryActivity.this,
					timePickerListener, hour, minute, false);

		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

		}
	};

	@Override
	protected void onStart() {
		super.onStart();

		getDataFromServer();
	}

	private void getDataFromServer() {
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));

		WebRequestPost getData = new WebRequestPost(new IWebRequest() {

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

						visitorsData.clear();
						for (int i = 0; i < jsonArray.length(); i++) {
							
							guest = new Entity();
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							guest.setItemID(jsonObj.getString("ID"));
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

						adapter = new OutEntryListAdapter(
								getApplicationContext(),
								R.layout.out_entry_list_item, visitorsData);
						guestList.setAdapter(adapter);
					}
					// System.out.println(jsonArray);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		},nameValuePair);

		getData.execute(Uttilities.GUEST_VISITOR_LIST_URL);
	}

	public void goBack(View v) {
		finish();
	}
	
	public void test(View v){
		
		showDialog(TIME_DIALOG_ID);
		
	}

	public void updateOutTime(String id) {
		ID = id;
		layoutTime.setVisibility(View.VISIBLE);
	}
}
