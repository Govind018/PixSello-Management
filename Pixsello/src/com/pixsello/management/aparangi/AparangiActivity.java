package com.pixsello.management.aparangi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pixsello.management.R;

public class AparangiActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aparangi);
	}
	
	public void goBack(View v){
		finish();
	}

	public void showEmpDetails(View v){

		startActivity(new Intent(getApplicationContext(), EmployeeDetailsActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_in);
		
	}
	
	public void showEmploymentDetails(View v){

		startActivity(new Intent(getApplicationContext(), EmploymentDetailsActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_in);
	}
	
	public void showAssessment(View v){
		
		startActivity(new Intent(getApplicationContext(), AssessmentActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_in);
	}
	
	public void showEmpSearch(View v){
		
		startActivity(new Intent(getApplicationContext(), EmployeeSearchActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_in);
	}
}
