package com.pixsello.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LostAndFoundActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_and_found);

	}
	
	public void goBack(View v){
		finish();
	}

	public void doReportItem(View v) {
		
		startActivity(new Intent(getApplicationContext(), ReportItemActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);

	}

	public void doFindItem(View v) {

		startActivity(new Intent(getApplicationContext(), FindItemActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lost_and_found, menu);
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
