package com.pixsello.management.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pixsello.management.R;

public class ActionRequiredActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_required);
	}

	public void doAddItem(View v) {

		startActivity(new Intent(getApplicationContext(), AddItemActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}
	
	public void showClosedItems(View v){
		
		startActivity(new Intent(getApplicationContext(), ClosedItemsActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void showActiveItems(View v) {

		startActivity(new Intent(getApplicationContext(),
				ActiveItemsListActivity.class));
		overridePendingTransition(R.anim.left_to_right, R.anim.abc_fade_out);
	}

	public void goBack(View v) {
		finish();
	}
}
