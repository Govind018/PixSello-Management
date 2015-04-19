package com.pixsello.management.aparangi;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class EmployeeDetailsActivity extends Activity {

	EditText editNameOfStaff, editAlias, editDOB, editMobile, editEmail,
	editLang, editAddress, editFatherName, editMotherName, editMarried,
	editSpouseName, editChildrenNum, editAddSarpanch,
	editIdentification, editDateOfPhoto;

	EditText editEmployer;
	EditText editPreviousEmp;
	EditText editEmployedOn;
	EditText editDocuments;
	EditText editDocumentsGiven;
	EditText editScanDocs;
	EditText editEmployedIn;
	EditText editDepartment;
	EditText editInterViewedBy;
	EditText editReffered;
	EditText editDesignation;

	String employer;
	String previousEmp;
	String employedOn;
	String docs;
	String docsGiven;
	String scanDocs;
	String employedIn;
	String department;
	String interviewedBy;
	String reffered;

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
	String designation;

	int REQUEST_IMAGE_CAPTURE = 101;
	
	ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_details);

		initLayout();
	}
	
	public void capturePhoto(View v) {

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
		}
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
		editEmployer = (EditText) findViewById(R.id.edit_employer);
		editPreviousEmp = (EditText) findViewById(R.id.edit_previous_empl);
		editEmployedOn = (EditText) findViewById(R.id.edit_employed_on);
		editDocuments = (EditText) findViewById(R.id.edit_docs_verified);
		editDocumentsGiven = (EditText) findViewById(R.id.edit_docs_given);
		editScanDocs= (EditText) findViewById(R.id.edit_scan_docs);
		editEmployedIn= (EditText) findViewById(R.id.edit_employed_in);
		editDepartment= (EditText) findViewById(R.id.edit_department);
		editInterViewedBy= (EditText) findViewById(R.id.edit_interviewedby);
		editReffered= (EditText) findViewById(R.id.edit_reffered);
		editDesignation = (EditText) findViewById(R.id.edit_designation);
		image = (ImageView) findViewById(R.id.image);

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
//		dateOfPhoto = editDateOfPhoto.getText().toString();
		employer = editEmployer.getText().toString();
		previousEmp = editPreviousEmp.getText().toString();
		employedOn = editEmployedOn.getText().toString();
		docs = editDocuments.getText().toString();
		docsGiven = editDocumentsGiven.getText().toString();
		scanDocs = editScanDocs.getText().toString();
		employedIn = editEmployedIn.getText().toString();
		department = editDepartment.getText().toString();
		interviewedBy = editInterViewedBy.getText().toString();
		reffered = editReffered.getText().toString();
		designation = editDesignation.getText().toString();
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
		byte [] byte_arr = stream.toByteArray();
		String image_str = Base64.encodeToString(byte_arr, 0);
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(10);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
//		nameValuePair.add(new BasicNameValuePair("searchkey",
//				"Ravi"));
		nameValuePair.add(new BasicNameValuePair("Nameofstaff", nameOfStaff));
		nameValuePair.add(new BasicNameValuePair("Aliasname", alias));
		nameValuePair.add(new BasicNameValuePair("Dateofbirth", DOB));
		nameValuePair.add(new BasicNameValuePair("Mobileno", mobile));
		nameValuePair.add(new BasicNameValuePair("Emailid", email));
		nameValuePair.add(new BasicNameValuePair("Language", language));
//		nameValuePair.add(new BasicNameValuePair("Permanentadress", address));
		nameValuePair.add(new BasicNameValuePair("Fathername", fatherName));
		nameValuePair.add(new BasicNameValuePair("Mothername", motherName));
		nameValuePair.add(new BasicNameValuePair("Marritalstatus", married));
		nameValuePair.add(new BasicNameValuePair("Spousename", spouseName));
		nameValuePair.add(new BasicNameValuePair("Children", childNum));
		nameValuePair.add(new BasicNameValuePair("Grampanchayataddress", addSarpanch));
		nameValuePair.add(new BasicNameValuePair("Identification",
				identificationMarks));
		nameValuePair.add(new BasicNameValuePair("Photo", image_str));
//		nameValuePair.add(new BasicNameValuePair("Dateofphoto", dateOfPhoto));
		nameValuePair.add(new BasicNameValuePair("Statusofemp", "test"));
		nameValuePair.add(new BasicNameValuePair("Employername", employer));
		nameValuePair.add(new BasicNameValuePair("Previousemployer", previousEmp));
		nameValuePair.add(new BasicNameValuePair("Employedon", employedOn));
		nameValuePair.add(new BasicNameValuePair("DocumentVarified", docs));
		nameValuePair.add(new BasicNameValuePair("Documentgiven", docsGiven));
		nameValuePair.add(new BasicNameValuePair("Scandocument", scanDocs));
		nameValuePair.add(new BasicNameValuePair("Introducedby", reffered));
		nameValuePair.add(new BasicNameValuePair("Interviewedby", interviewedBy));
		nameValuePair.add(new BasicNameValuePair("Department", department));
		nameValuePair.add(new BasicNameValuePair("Employmentstatus", reffered));
		nameValuePair.add(new BasicNameValuePair("Designation", designation));

		
		
		final ProgressDialog dialog = new ProgressDialog(
				EmployeeDetailsActivity.this);
		dialog.setMessage("Please Wait..");
		dialog.show();

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {
				dialog.cancel();
				Uttilities.showToast(getApplicationContext(), data);
				finish();

			}
		}, nameValuePair);

		post.execute(Uttilities.EMPLOYEE_ADD_DETAILS);
		
	}

	public void goBack(View v) {

		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {

			Bitmap map = (Bitmap) data.getExtras().get("data");
			image.setImageBitmap(map);

		}
	}
}
