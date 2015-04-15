package com.pixsello.management;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class EmploymentDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employment_details);
	}
	
	public void goBack(View v){

		finish();
		
	}
}

