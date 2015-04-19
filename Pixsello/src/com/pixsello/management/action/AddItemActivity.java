package com.pixsello.management.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class AddItemActivity extends Activity {

	EditText editDate, editTime, editDesc, editLocation, editStaff, editRespo;

	String date;
	String time;
	String desc;
	String location;
	String staffName;
	String respo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);

		editDate = (EditText) findViewById(R.id.edit_item_date);
		editTime = (EditText) findViewById(R.id.edit_item_time);
		editDesc = (EditText) findViewById(R.id.edit_description);
		editLocation = (EditText) findViewById(R.id.edit_location);
		editStaff = (EditText) findViewById(R.id.edit_staff_name);
		editRespo = (EditText) findViewById(R.id.edit_responsibility);
	}

	@Override
	protected void onStart() {
		super.onStart();

		editDate.setText(Uttilities.getDate());
		editTime.setText(Uttilities.getTime());
	}

	public void doSubmitItem(View v) {

		date = editDate.getText().toString();
		time = editTime.getText().toString();
		desc = editDesc.getText().toString();
		location = editLocation.getText().toString();
		staffName = editStaff.getText().toString();
		respo = editRespo.getText().toString();

		if (desc.isEmpty() || location.isEmpty() || staffName.isEmpty()
				|| respo.isEmpty()) {

			Uttilities.showToast(getApplicationContext(),
					"Please fill all the fields.");

		} else {

			final ProgressDialog dialog = new ProgressDialog(AddItemActivity.this);
			dialog.setMessage("Please Wait..");
			dialog.show();
			
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(6);
			nameValuePair.add(new BasicNameValuePair("Date", date));
			nameValuePair.add(new BasicNameValuePair("Time", time));
			nameValuePair.add(new BasicNameValuePair("Description", desc));
			nameValuePair.add(new BasicNameValuePair("Where", location));
			nameValuePair.add(new BasicNameValuePair("Reported", staffName));
			nameValuePair.add(new BasicNameValuePair("Responsibility", respo));
			nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));

			WebRequestPost postData = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {
					
					Uttilities.showToast(getApplicationContext(), data);
					dialog.cancel();
					
					finish();
//					resetData();

				}
			}, nameValuePair);

			postData.execute(Uttilities.ACTION_REQUIRED_URL);
		}
	}


	private void resetData() {

		editDesc.setText("");
		editLocation.setText("");
		editStaff.setText("");
		editRespo.setText("");

		
	}
	
	public void goBack(View v) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
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
