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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class AssessmentActivity extends Activity {

	Spinner spinnerRespo;
	Spinner spinnerCleanliness;
	Spinner spinnerPersonal;
	Spinner spinnerGuestInter;
	Spinner spinnerHabits;
	Spinner spinnerTimings;
	Spinner spinnerCoOp;
	Spinner spinnerBodyLang;
	Spinner spinnerHonesty;
	Spinner spinnerAssesmentDoneBy;
	Spinner spinnerEmployees;
	Spinner spinnerMonths;

	EditText editTotal;
	EditText editRemarks;
	EditText editDateOfAssessment;
	EditText editAssesmentDoneBy;

	ArrayList<Integer> totalItems;

	ArrayList<String> employees;

	ArrayAdapter<String> adapter;

	Button btnSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assessment);

		spinnerEmployees = (Spinner) findViewById(R.id.spinner_employees);
		spinnerRespo = (Spinner) findViewById(R.id.spinner_respo);
		spinnerRespo.setOnItemSelectedListener(listener);
		spinnerCleanliness = (Spinner) findViewById(R.id.spinner_cleanliness);
		spinnerCleanliness.setOnItemSelectedListener(listener);
		spinnerPersonal = (Spinner) findViewById(R.id.spinner_personal);
		spinnerPersonal.setOnItemSelectedListener(listener);
		spinnerGuestInter = (Spinner) findViewById(R.id.spinner_guest_interaction);
		spinnerGuestInter.setOnItemSelectedListener(listener);
		spinnerHabits = (Spinner) findViewById(R.id.spinner_habits);
		spinnerHabits.setOnItemSelectedListener(listener);
		spinnerTimings = (Spinner) findViewById(R.id.spinner_timings);
		spinnerTimings.setOnItemSelectedListener(listener);
		spinnerCoOp = (Spinner) findViewById(R.id.spinner_coOp);
		spinnerCoOp.setOnItemSelectedListener(listener);
		spinnerBodyLang = (Spinner) findViewById(R.id.spinner_body_langauage);
		spinnerBodyLang.setOnItemSelectedListener(listener);
		spinnerHonesty = (Spinner) findViewById(R.id.spinner_honesty);
		spinnerHonesty.setOnItemSelectedListener(listener);
		editTotal = (EditText) findViewById(R.id.edit_total);
		editRemarks = (EditText) findViewById(R.id.edit_remarks);
		editDateOfAssessment = (EditText) findViewById(R.id.edit_done_by);
		editAssesmentDoneBy = (EditText) findViewById(R.id.edit_done_by);
		spinnerMonths = (Spinner) findViewById(R.id.spinner_months);
		employees = new ArrayList<String>();
		totalItems = new ArrayList<Integer>();

		btnSubmit = (Button) findViewById(R.id.btn_submit);

		// btnSubmit.setText("SUBMIT");
	}

	OnItemSelectedListener listener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			calculateTotal();

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	};

	private void calculateTotal() {

		int sum = 0;

		editTotal.setText("");
		totalItems.clear();
		totalItems.add(Integer.parseInt(spinnerRespo.getSelectedItem()
				.toString()));
		totalItems.add(Integer.parseInt(spinnerCleanliness.getSelectedItem()
				.toString()));
		totalItems.add(Integer.parseInt(spinnerPersonal.getSelectedItem()
				.toString()));
		totalItems.add(Integer.parseInt(spinnerGuestInter.getSelectedItem()
				.toString()));
		totalItems.add(Integer.parseInt(spinnerHabits.getSelectedItem()
				.toString()));
		totalItems.add(Integer.parseInt(spinnerTimings.getSelectedItem()
				.toString()));
		totalItems.add(Integer.parseInt(spinnerCoOp.getSelectedItem()
				.toString()));
		totalItems.add(Integer.parseInt(spinnerBodyLang.getSelectedItem()
				.toString()));
		totalItems.add(Integer.parseInt(spinnerHonesty.getSelectedItem()
				.toString()));

		for (int item : totalItems) {
			sum = sum + item;
		}

		editTotal.setText(""+sum);
	}

	@Override
	protected void onStart() {
		super.onStart();

		getEmployeeNames();
	}

	private void getEmployeeNames() {

		final ProgressDialog dialog = new ProgressDialog(
				AssessmentActivity.this);
		dialog.setMessage("Please Wait..");
		dialog.show();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {
					employees.clear();
					JSONObject obj = new JSONObject(data);
					JSONArray jsonArray = obj.getJSONArray("result");
					JSONObject jsonObj;
					for (int i = 0; i < jsonArray.length(); i++) {

						jsonObj = jsonArray.getJSONObject(i);
						employees.add(jsonObj.getString("Nameofstaff"));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

				adapter = new ArrayAdapter<String>(getApplicationContext(),
						R.layout.spinner_item, employees);
				spinnerEmployees.setAdapter(adapter);
				dialog.cancel();
			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getAllEmployee");

	}

	public void goBack(View v) {
		finish();
	}

	public void doSubmitData(View v) {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(13);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("empID", String
				.valueOf(spinnerEmployees.getSelectedItemId() + 1)));
		nameValuePair.add(new BasicNameValuePair("Monthof", spinnerMonths
				.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("URefficiency", spinnerRespo
				.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("cleannessofjob",
				spinnerCleanliness.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("PersonalHy", spinnerPersonal
				.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Guestintraction",
				spinnerGuestInter.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Habits", spinnerHabits
				.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Timings", spinnerTimings
				.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("cooperationwithstaff",
				spinnerCoOp.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Bodylanguage",
				spinnerBodyLang.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Honesty", spinnerHonesty
				.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Remark", editRemarks
				.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("Assesmentdoneby",
				editAssesmentDoneBy.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("Dateofassesment",
				editDateOfAssessment.getText().toString()));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				Uttilities.showToast(getApplicationContext(), data);
				finish();

			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addAssesmentdetails");
	}
}
