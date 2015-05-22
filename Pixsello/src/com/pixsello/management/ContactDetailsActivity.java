package com.pixsello.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pixsello.management.contact.AddContactNumberActivity;
import com.pixsello.management.contact.EmergencyNumbersActivity;

public class ContactDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_details);
	}
	
	public void goBack(View v){
		finish();
	}
	
	public void showEmergencyNumbers(View v){
		
		Intent in = new Intent(getApplicationContext(), EmergencyNumbersActivity.class);
		in.putExtra("type", "emr");
		startActivity(in);
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
		
	}
	
	public void showVendorNumbers(View v){
		
		Intent in = new Intent(getApplicationContext(), EmergencyNumbersActivity.class);
		in.putExtra("type", "vn");
		startActivity(in);	
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void addEmergencyNumber(View v) {

		Intent emIntent = new Intent(getApplicationContext(),
				AddContactNumberActivity.class);
		emIntent.putExtra("type", "emergency");
		startActivity(emIntent);

	}

	public void addVendorNumber(View v) {

		Intent vmIntent = new Intent(getApplicationContext(),
				AddContactNumberActivity.class);
		vmIntent.putExtra("type", "vendor");
		startActivity(vmIntent);

	}

	public void doSearch(View v) {

	}
}
