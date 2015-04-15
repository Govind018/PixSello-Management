package com.pixsello.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.pixsello.management.action.ActionRequiredActivity;
import com.pixsello.management.aparangi.AparangiActivity;
import com.pixsello.management.training.TrainingActivity;

public class HomeScreenActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);
	}

	public void showLostFoundDetails(View v) {

		startActivity(new Intent(getApplicationContext(),
				LostAndFoundActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);

	}

	public void goBack(View v) {
		finish();
	}

	public void showContactDetails(View v) {

		startActivity(new Intent(getApplicationContext(),
				ContactDetailsActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void showGuestVisitors(View v) {

		startActivity(new Intent(getApplicationContext(),
				GuestVisitorActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void showTrainingUpdates(View v) {

		startActivity(new Intent(getApplicationContext(),
				TrainingActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void showPaymentDetails(View v) {

		startActivity(new Intent(getApplicationContext(),
				PaymentDetailsActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);

	}

	public void showActionRequired(View v) {

		startActivity(new Intent(getApplicationContext(),
				ActionRequiredActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);

	}
	
	public void showAparangiDetails(View v){
		
		startActivity(new Intent(getApplicationContext(), AparangiActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}
}
