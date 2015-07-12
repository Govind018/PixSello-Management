package com.belgaum.events;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.belgaum.events.adapter.EventDetailAdapter;

//Not using
public class EventDetailActivity extends ActionBarActivity {

	ListView list;

	EventDetailAdapter adapter;

	ArrayList<String> details;

	Toolbar toolBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);
		
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.custom_actionbar, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.MATCH_PARENT,Gravity.CENTER);
		
		TextView text = (TextView) v.findViewById(R.id.title);
		text.setText("Event Detail");
		getSupportActionBar().setCustomView(v,params);

		details = new ArrayList<String>();
		list = (ListView) findViewById(R.id.listView);
		details.add("Govind C Mastamardi");
		details.add("Govind018@gmail.com");
		details.add("8861334526");
		details.add("23");
		details.add("Cleaning");
		details.add("Belgaum");

		adapter = new EventDetailAdapter(getApplicationContext(),
				R.layout.event_detail_row, details);
		list.setAdapter(adapter);

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
