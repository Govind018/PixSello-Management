package com.pixsello.management.aparangi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class EmploymentDetailsActivity extends Activity {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employment_details);
		
		initLayout();	
		
	}

	private void initLayout() {
		
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
		
	}

	public void doSubmitData(View v) {

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
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(10);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("empID","1"));
		nameValuePair.add(new BasicNameValuePair("Employername", employer));
		nameValuePair.add(new BasicNameValuePair("Previousemployer", previousEmp));
		nameValuePair.add(new BasicNameValuePair("Employedon", employedOn));
		nameValuePair.add(new BasicNameValuePair("Documentvarified", docs));
		nameValuePair.add(new BasicNameValuePair("Documentgiven", docsGiven));
//		nameValuePair.add(new BasicNameValuePair("", scanDocs));
		nameValuePair.add(new BasicNameValuePair("", employedIn));
		nameValuePair.add(new BasicNameValuePair("Interviewedby", interviewedBy));
		nameValuePair.add(new BasicNameValuePair("Department", department));
		nameValuePair.add(new BasicNameValuePair("Status", reffered));
		
		WebRequestPost post = new WebRequestPost(new IWebRequest() {
			
			@Override
			public void onDataArrived(String data) {

				Uttilities.showToast(getApplicationContext(), data);
				
			}
		}, nameValuePair);
		
		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addEmploymentdetails");
	}

	public void goBack(View v) {

		finish();

	}
}
