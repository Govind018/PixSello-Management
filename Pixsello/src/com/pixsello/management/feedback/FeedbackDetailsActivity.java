package com.pixsello.management.feedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pixsello.management.AddNewFeedbackActivity;
import com.pixsello.management.R;

public class FeedbackDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_details);
	}
	
	public void showNewFeedback(View v){
		
		startActivity(new Intent(getApplicationContext(), AddNewFeedbackActivity.class));
		
	}
	
	public void showPreviuosFeedback(View v){
		
		startActivity(new Intent(getApplicationContext(), PreviousFeedbackActivity.class));
	}
	
	public void goBack(View v){
		
		finish();
	}
}
