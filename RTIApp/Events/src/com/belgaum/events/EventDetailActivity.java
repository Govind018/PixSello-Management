package com.belgaum.events;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.belgaum.events.adapter.EventDetailAdapter;

//Not using
public class EventDetailActivity extends ActionBarActivity {

	EventDetailAdapter adapter;

	ArrayList<String> details;

	Toolbar toolBar;
	
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);
		
		list = (ListView) findViewById(R.id.list_msgs);
		
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();

		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		TextView eventName = (TextView) findViewById(R.id.event_name);
		TextView eventDesc = (TextView) findViewById(R.id.event_desc);

		Intent intent = getIntent();
		eventName.setText(intent.getStringExtra("name"));
		eventDesc.setText(intent.getStringExtra("desc"));
		
		NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.image);
		thumbNail.setImageUrl(intent.getStringExtra("image"), imageLoader);
		
		adapter = new EventDetailAdapter(getApplicationContext(), R.layout.event_chat_row, details);
		list.setAdapter(adapter);

		// LayoutInflater inflater = (LayoutInflater)
		// this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// View v = inflater.inflate(R.layout.custom_actionbar, null);
		// ActionBar.LayoutParams params = new
		// ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.MATCH_PARENT,Gravity.CENTER);
		//
		// TextView text = (TextView) v.findViewById(R.id.title);
		// text.setText("Event Detail");
		// getSupportActionBar().setCustomView(v,params);

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
