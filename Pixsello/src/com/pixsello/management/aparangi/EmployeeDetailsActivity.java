package com.pixsello.management.aparangi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class EmployeeDetailsActivity extends Activity {

	EditText editNameOfStaff, editAlias, editDOB, editMobile, editEmail,
			editLang, editAddress, editFatherName, editMotherName, editMarried,
			editSpouseName, editChildrenNum, editAddSarpanch,
			editIdentification, editDateOfPhoto;

	String nameOfStaff;
	String alias;
	String DOB;
	String mobile;
	String email;
	String language;
	String address;
	String fatherName;
	String motherName;
	String married;
	String spouseName;
	String childNum;
	String addSarpanch;
	String identificationMarks;
	String dateOfPhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_details);

		initLayout();
	}

	private void initLayout() {

		editNameOfStaff = (EditText) findViewById(R.id.edit_name_staff);
		editAlias = (EditText) findViewById(R.id.edit_name_alias);
		editDOB = (EditText) findViewById(R.id.edit_dob);
		editMobile = (EditText) findViewById(R.id.edit_mobile);
		editEmail = (EditText) findViewById(R.id.edit_email);
		editLang = (EditText) findViewById(R.id.edit_lang);
		editAddress = (EditText) findViewById(R.id.edit_address);
		editFatherName = (EditText) findViewById(R.id.edit_father_name);
		editMotherName = (EditText) findViewById(R.id.edit_mother_name);
		editMarried = (EditText) findViewById(R.id.edit_married);
		editSpouseName = (EditText) findViewById(R.id.edit_spouse_name);
		editChildrenNum = (EditText) findViewById(R.id.edit_children);
		editAddSarpanch = (EditText) findViewById(R.id.edit_sarpanch);
		editIdentification = (EditText) findViewById(R.id.edit_ide_marks);
		editDateOfPhoto = (EditText) findViewById(R.id.edit_date_photo);

	}

	public void submitData(View v) {

		nameOfStaff = editNameOfStaff.getText().toString();
		alias = editAlias.getText().toString();
		DOB = editDOB.getText().toString();
		mobile = editMobile.getText().toString();
		email = editEmail.getText().toString();
		language = editLang.getText().toString();
		address = editAddress.getText().toString();
		fatherName = editFatherName.getText().toString();
		motherName = editMotherName.getText().toString();
		married = editMarried.getText().toString();
		spouseName = editSpouseName.getText().toString();
		childNum = editChildrenNum.getText().toString();
		addSarpanch = editAddSarpanch.getText().toString();
		identificationMarks = editIdentification.getText().toString();
		dateOfPhoto = editDateOfPhoto.getText().toString();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(10);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("Nameofstaff", nameOfStaff));
		nameValuePair.add(new BasicNameValuePair("Aliasname", alias));
		nameValuePair.add(new BasicNameValuePair("Dateofbirth", DOB));
		nameValuePair.add(new BasicNameValuePair("Mobileno", mobile));
		nameValuePair.add(new BasicNameValuePair("Emailid", email));
		nameValuePair.add(new BasicNameValuePair("Language", language));
		nameValuePair.add(new BasicNameValuePair("Permanentadress", address));
		nameValuePair.add(new BasicNameValuePair("Fathername", fatherName));
		nameValuePair.add(new BasicNameValuePair("Monthername", motherName));
		nameValuePair.add(new BasicNameValuePair("Maridstatus", married));
		nameValuePair.add(new BasicNameValuePair("Spaousename", spouseName));
		nameValuePair.add(new BasicNameValuePair("children", childNum));
		nameValuePair.add(new BasicNameValuePair("Grampanchayat", addSarpanch));
		nameValuePair.add(new BasicNameValuePair("Identificationmarks",
				identificationMarks));
		nameValuePair.add(new BasicNameValuePair("Photo", "test"));
		nameValuePair.add(new BasicNameValuePair("Dateofphoto", dateOfPhoto));
		nameValuePair.add(new BasicNameValuePair("Statusofemp", "test"));

		final ProgressDialog dialog = new ProgressDialog(
				EmployeeDetailsActivity.this);
		dialog.setMessage("Please Wait..");
		dialog.show();

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {
				dialog.cancel();
				Uttilities.showToast(getApplicationContext(), data);

			}
		}, nameValuePair);

		post.execute(Uttilities.EMPLOYEE_ADD_DETAILS);
	}

	public void goBack(View v) {

		finish();
	}
}
