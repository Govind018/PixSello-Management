package com.pixsello.management.aparangi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.adapters.EmployeeSearchListAdapter;
import com.pixsello.management.aparangi.EmployeeDetailEditDialog.OnCompleteListener;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class EmployeeSearchActivity extends Activity implements
		OnCompleteListener {

	ListView listStaff;

	EmployeeSearchListAdapter adapter;

	ArrayList<Entity> listOfEmps;

	EditText editSearch;

	ImageView arrowFront, arrowBack;

	TextView month;

	String[] months = { "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" };

	ProgressDialog dialog;

	int counter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_search);

		listStaff = (ListView) findViewById(R.id.list_staff);
		listStaff.setOnItemClickListener(listener);
		listOfEmps = new ArrayList<Entity>();

		editSearch = (EditText) findViewById(R.id.edit_search_key);

		arrowFront = (ImageView) findViewById(R.id.arrow_front);
		arrowBack = (ImageView) findViewById(R.id.arrow_back);
		month = (TextView) findViewById(R.id.item_training_performance);

		dialog = new ProgressDialog(EmployeeSearchActivity.this);

		// Entity en;
		//
		// for(int i = 0; i < 10; i++){
		// en = new Entity();
		// en.setEmpId(String.valueOf(i));
		// en.setEmpName("GOVIND MASTAMARDI");
		// en.setEmpDesignation("SOFTWARE ENGINEER");
		// en.setEmpDepartment("SOFTWARE");
		// en.setEmpStatus("TEST");
		// en.setEmpHighLights("GOOD");
		// listOfEmps.add(en);
		// }

		month.setText(months[counter]);
	}

	public void showNext(View v) {

		if (counter < months.length - 1) {
			counter++;
			month.setText(months[counter]);
			getDataMonthWise(months[counter]);
		}
	}

	public void showPrevious(View v) {
		if (counter != 0) {
			counter--;
			month.setText(months[counter]);
			getDataMonthWise(months[counter]);
		}
	}

	public void getDataMonthWise(String month) {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("empID", "1"));
		nameValuePair.add(new BasicNameValuePair("Monthof", month));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				populateData(data);

			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getPerformance");

	}

	public void doSearch(View v) {

		String search = editSearch.getText().toString();
		dialog.setMessage("Please Wait..");
		dialog.show();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("searchkey", search));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				populateData(data);

			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/searchEmpassesment");
	}

	@Override
	protected void onStart() {
		super.onStart();

		getEmployeeDetails();
	}

	private void getEmployeeDetails() {

		dialog.setMessage("Please Wait..");
		dialog.show();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
		// nameValuePair.add(new BasicNameValuePair("empID","1"));
		// nameValuePair.add(new BasicNameValuePair("Monthof","February"));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				populateData(data);

			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getemployeeAssessmentnew");

	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Entity en = listOfEmps.get(position);

			Bundle bundle = new Bundle();
			bundle.putString("name", en.getEmpName());
			bundle.putString("designation", en.getEmpDesignation());
			bundle.putString("department", en.getEmpDepartment());
			bundle.putString("status", en.getEmpStatus());
			bundle.putString("empid", en.getEmpId());

			EmployeeDetailEditDialog dialog = new EmployeeDetailEditDialog();
			dialog.setArguments(bundle);
			dialog.show(getFragmentManager(), "");

		}
	};

	public void goBack(View v) {

		finish();
	}

	@Override
	public void onComplete(String result) {

		getEmployeeDetails();
	}

	public void populateData(final String data) {

		try {
			Entity en;
			listOfEmps.clear();
			JSONObject obj = new JSONObject(data);

			if (obj.has("error_message")) {
				dialog.cancel();
				Uttilities.showToast(getApplicationContext(),
						"No Records to display");
			} else {

				JSONArray jsonArray = obj.getJSONArray("result");

				for (int i = 0; i < jsonArray.length(); i++) {
					en = new Entity();
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					en.setEmpName(jsonObj.getString("Nameofstaff"));
					en.setEmpDesignation(jsonObj.getString("Designation"));
					// en.setEmpStatus(jsonObj.getString("Employmentstatus"));
					en.setEmpId(jsonObj.getString("empID"));
					en.setEmpHighLights(jsonObj.getString("Highlights"));
					en.setEmpDepartment(jsonObj.getString("Department"));
					en.setEmpMarks(jsonObj.getString("Totalmarks"));

					listOfEmps.add(en);
				}

				dialog.cancel();
				adapter = new EmployeeSearchListAdapter(
						getApplicationContext(), R.layout.emp_search_list_item,
						listOfEmps);
				listStaff.setAdapter(adapter);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
