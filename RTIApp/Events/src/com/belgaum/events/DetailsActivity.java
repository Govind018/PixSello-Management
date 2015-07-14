package com.belgaum.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsActivity extends ActionBarActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		TextView textName = (TextView) findViewById(R.id.text_name);
		
		TextView textEmail = (TextView) findViewById(R.id.text_email);
		
		TextView textNumber = (TextView) findViewById(R.id.text_phone);
		
		Intent intent = getIntent();
		textName.setText(intent.getStringExtra("name"));
		textEmail.setText(intent.getStringExtra("email"));
		textNumber.setText(intent.getStringExtra("phone"));
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
