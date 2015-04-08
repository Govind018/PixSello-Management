package com.pixsello.management;

import com.pixsello.management.guest.MakeInEntryActivity;
import com.pixsello.management.guest.MakeOutEntryActivity;
import com.pixsello.management.guest.PastVisitorsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class GuestVisitorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guest_visitor);
	}

	public void makeInTimeEntries(View v){
		
		startActivity(new Intent(getApplicationContext(), MakeInEntryActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}
	
	public void goBack(View v){
		finish();
	}

	public void makeOutTimeEntries(View v){
		
		startActivity(new Intent(getApplicationContext(), MakeOutEntryActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}
	
	public void showPastVisitors(View v){

		startActivity(new Intent(getApplicationContext(), PastVisitorsActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guest_visitor, menu);
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
