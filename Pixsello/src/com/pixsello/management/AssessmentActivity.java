package com.pixsello.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

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

	EditText editTotal;
	EditText editRemarks;
	EditText editDateOfAssessment;
	EditText editAssesmentDoneBy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assessment);

		spinnerRespo = (Spinner) findViewById(R.id.spinner_respo);
		spinnerCleanliness = (Spinner) findViewById(R.id.spinner_cleanliness);
		spinnerPersonal = (Spinner) findViewById(R.id.spinner_personal);
		spinnerGuestInter = (Spinner) findViewById(R.id.spinner_guest_interaction);
		spinnerHabits = (Spinner) findViewById(R.id.spinner_habits);
		spinnerTimings = (Spinner) findViewById(R.id.spinner_timings);
		spinnerCoOp = (Spinner) findViewById(R.id.spinner_coOp);
		spinnerBodyLang = (Spinner) findViewById(R.id.spinner_body_langauage);
		spinnerHonesty = (Spinner) findViewById(R.id.spinner_honesty);
		editTotal = (EditText) findViewById(R.id.edit_total);
		editRemarks = (EditText) findViewById(R.id.edit_remarks);
		editDateOfAssessment = (EditText) findViewById(R.id.edit_done_by);
		editAssesmentDoneBy = (EditText) findViewById(R.id.edit_done_by);
	}
	
	public void doSubmitData(View v){
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(13);
		nameValuePair.add(new BasicNameValuePair("PropertyID",Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("URefficiency", spinnerRespo.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("cleannessofjob", spinnerCleanliness.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("PersonalHy", spinnerPersonal.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Guestintraction", spinnerGuestInter.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Habits", spinnerHabits.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Timings", spinnerTimings.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("cooperationwithstaff", spinnerCoOp.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Bodylanguage", spinnerBodyLang.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Honesty", spinnerHonesty.getSelectedItem().toString()));
		nameValuePair.add(new BasicNameValuePair("Remark",editRemarks.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("Assesmentdoneby",editAssesmentDoneBy.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("Dateofassesment",editDateOfAssessment.getText().toString()));
		
		WebRequestPost post = new WebRequestPost(new IWebRequest() {
			
			@Override
			public void onDataArrived(String data) {

				Uttilities.showToast(getApplicationContext(), data);
				
			}
		}, nameValuePair);
		
		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addAssesmentdetails");
	}
}
