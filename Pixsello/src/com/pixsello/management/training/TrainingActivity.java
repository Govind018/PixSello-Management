package com.pixsello.management.training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pixsello.management.R;

public class TrainingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training);
	}
	
	public void goBack(View v) {
		finish();
	}

	public void doUpdate(View v) {

		startActivity(new Intent(getApplicationContext(), TrainingUpdatesActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void showProfile(View v) {

		startActivity(new Intent(getApplicationContext(),
				StaffProfileActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void showVideos(View v) {

		startActivity(new Intent(getApplicationContext(),
				TrainingVideosActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);

	}
}
