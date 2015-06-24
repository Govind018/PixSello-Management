package com.belgaum.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class SearchListActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_list);
	}
	
	public void showDetails(View v){
		
		startActivity(new Intent(getApplicationContext(), SearchDetailActivity.class));
	}
}
