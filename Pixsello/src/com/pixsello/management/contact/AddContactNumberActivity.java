package com.pixsello.management.contact;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class AddContactNumberActivity extends Activity {

	TextView typeOfContact;

	EditText editServiceDesc;
	EditText editContactPerson;
	EditText editContactNum;

	String serviceDesc;
	String contactPerson;
	String contactNumber;

	List<NameValuePair> nameValuePair;
	
	boolean emergency;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact_number);

		nameValuePair = new ArrayList<NameValuePair>(4);
		
		typeOfContact = (TextView) findViewById(R.id.lbl_type_contact);
		
		editServiceDesc = (EditText) findViewById(R.id.edit_services_description);
		editContactPerson = (EditText) findViewById(R.id.edit_contact_person);
		editContactNum = (EditText) findViewById(R.id.edit_contact_number);
		
		Intent in = getIntent();

		String type = in.getStringExtra("type");

		if (type.equalsIgnoreCase("emergency")) {
			typeOfContact.setText("EMERGENCY NUMBER (ADD NEW)");
			emergency = true;
		} else if (type.equalsIgnoreCase("vendor")) {
			typeOfContact.setText("VENDOR NUMBER (ADD NEW)");
			emergency = false;
		}
	}

	public void doAddNumber(View v) {
		
//		if(!Uttilities.isNetworkAvailable(getApplicationContext())){
//			
//			Uttilities.showToast(getApplicationContext(), "Internet Not connected");
//			
//			return;
//		}

		serviceDesc = editServiceDesc.getText().toString();
		contactNumber = editContactNum.getText().toString();
		contactPerson = editContactPerson.getText().toString();

		if (serviceDesc.isEmpty() || contactNumber.isEmpty()
				|| contactPerson.isEmpty()) {

			Uttilities.showToast(getApplicationContext(),
					"Please fill all the fields.");
		} else {

			nameValuePair.add(new BasicNameValuePair("ServiceDescription", serviceDesc));
			nameValuePair.add(new BasicNameValuePair("Contactperson",
					contactPerson));
			nameValuePair.add(new BasicNameValuePair("ContactNumber",
					contactNumber));
			
			
			if(emergency){
				nameValuePair.add(new BasicNameValuePair("Typeofperson", "1"));	
			}else{
				nameValuePair.add(new BasicNameValuePair("Typeofperson", "2"));	
			}
			
			WebRequestPost postData = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {
					
					Uttilities.showToast(getApplicationContext(), data);

				}
			}, nameValuePair);

			postData.execute(Uttilities.CONTACT_ADD_URL);
		}
	}

	public void goBack(View v) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact_number, menu);
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
