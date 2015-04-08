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

	}

	public void showProfile(View v) {

		startActivity(new Intent(getApplicationContext(),
				StaffProfileActivity.class));
	}

	public void showVideos(View v) {

		startActivity(new Intent(getApplicationContext(),
				TrainingVideosActivity.class));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.training, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
