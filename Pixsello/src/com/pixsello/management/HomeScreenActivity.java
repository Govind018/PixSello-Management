package com.pixsello.management;

import com.pixsello.management.action.ActionRequiredActivity;
import com.pixsello.management.training.TrainingActivity;
import com.pixsello.management.training.TrainingUpdatesActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


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
	
	public void goBack(View v){
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
	
	public void showActionRequired(View v){
		
		startActivity(new Intent(getApplicationContext(),
				ActionRequiredActivity.class));
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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