package com.pixsello.management.aparangi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.pixsello.management.R;

public class EmployeeDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_details);
	}
	
	public void goBack(View v){
		
		finish();
	}
}
