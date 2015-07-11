package com.pixsello.management.contact;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	EditText editQuickInfo;

	TextView textQuickInfo;

	String serviceDesc;
	String contactPerson;
	String contactNumber;
	String quickInfo;

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
		editQuickInfo = (EditText) findViewById(R.id.edit_contact_quickinfo);
		textQuickInfo = (TextView) findViewById(R.id.text_quick_info);

		Intent in = getIntent();

		String type = in.getStringExtra("type");

		if (type.equalsIgnoreCase("emergency")) {
			typeOfContact.setText("EMERGENCY NUMBER (ADD NEW)");
			emergency = true;
			editQuickInfo.setVisibility(View.GONE);
			textQuickInfo.setVisibility(View.GONE);
		} else if (type.equalsIgnoreCase("vendor")) {
			typeOfContact.setText("VENDOR NUMBER (ADD NEW)");
			emergency = false;
			editQuickInfo.setVisibility(View.VISIBLE);
			textQuickInfo.setVisibility(View.VISIBLE);
		}
	}

	public void doAddNumber(View v) {

		serviceDesc = editServiceDesc.getText().toString();
		contactNumber = editContactNum.getText().toString();
		contactPerson = editContactPerson.getText().toString();
		quickInfo = editQuickInfo.getText().toString();

		if (serviceDesc.isEmpty() || contactNumber.isEmpty()
				|| contactPerson.isEmpty()) {

			Uttilities.showToast(getApplicationContext(),
					"Please fill all the fields.");
			return;
		}

		if (!Uttilities.validate(serviceDesc)
				|| !Uttilities.validate(contactPerson)) {
			Uttilities.showToast(getApplicationContext(), "Invalid Input");
			return;
		}

		nameValuePair.add(new BasicNameValuePair("ServiceDescription",
				serviceDesc));
		nameValuePair
				.add(new BasicNameValuePair("Contactperson", contactPerson));
		nameValuePair
				.add(new BasicNameValuePair("ContactNumber", contactNumber));

		if (emergency) {
			nameValuePair.add(new BasicNameValuePair("Typeofperson", "1"));
			// emergency number
		} else {
			nameValuePair.add(new BasicNameValuePair("Typeofperson", "2")); // vendor
																			// number
			nameValuePair.add(new BasicNameValuePair("quickinfo", quickInfo));
		}

		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getUserLoginId(getApplicationContext())));

		WebRequestPost postData = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				finish();

				Uttilities.showToast(getApplicationContext(), data);

			}
		}, nameValuePair);

		postData.execute(Uttilities.CONTACT_ADD_URL);
	}

	public void goBack(View v) {
		finish();
	}
}
