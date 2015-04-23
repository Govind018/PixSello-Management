package com.pixsello.management;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.pixsello.management.action.ActionRequiredActivity;
import com.pixsello.management.aparangi.AparangiActivity;
import com.pixsello.management.feedback.FeedbackDetailsActivity;
import com.pixsello.management.training.TrainingActivity;

public class HomeScreenActivity extends ActionBarActivity {

	ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);

		image = (ImageView) findViewById(R.id.button_logout);

		SharedPreferences pref = getSharedPreferences("login_data",
				Context.MODE_PRIVATE);

		if (!pref.getBoolean("login", false)) {

			startActivity(new Intent(getApplicationContext(),
					LoginActivity.class));

		}
	}

	public void showLogOut(View v) {

		PopupMenu menu = new PopupMenu(getApplicationContext(), image);
		menu.getMenuInflater().inflate(R.menu.poupup_menu, menu.getMenu());
		menu.show();

		menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				SharedPreferences pref = getSharedPreferences("login_date",
						Context.MODE_PRIVATE);
				Editor edit = pref.edit();
				edit.putBoolean("login", false);
				edit.commit();

				finish();
				startActivity(new Intent(HomeScreenActivity.this,
						LoginActivity.class));

				return true;
			}
		});
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

	public void showAparangiDetails(View v) {

		startActivity(new Intent(getApplicationContext(),
				AparangiActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}
	
	public void showFeedbackDetails(View v){
		
		startActivity(new Intent(getApplicationContext(),
				FeedbackDetailsActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
		
	}
}
