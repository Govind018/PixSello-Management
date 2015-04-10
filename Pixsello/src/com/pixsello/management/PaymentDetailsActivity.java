package com.pixsello.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.spundhan.pixsello.payment.AddServicesActivity;
import com.spundhan.pixsello.payment.PaymentSearchActivity;
import com.spundhan.pixsello.payment.PaymentStatusActivity;
import com.spundhan.pixsello.payment.UpdateNewBIllActivity;

public class PaymentDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_details);
	}
	
	public void doAddService(View v) {

		startActivity(new Intent(getApplicationContext(), AddServicesActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
		
	}

	public void doUpdateNewBill(View v) {

		startActivity(new Intent(getApplicationContext(), UpdateNewBIllActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
		
	}

	public void doNewPayment(View v) {

		startActivity(new Intent(getApplicationContext(), PaymentStatusActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void showPaymentStatus(View v) {

	}
	
	public void doSearch(View v){
		
		startActivity(new Intent(getApplicationContext(), PaymentSearchActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void goBack(View v) {
		finish();
	}

}
