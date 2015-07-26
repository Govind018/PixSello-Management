package com.belgaum.events;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.belgaum.events.adapter.EventDetailAdapter;
import com.belgaum.events.util.Entity;
import com.belgaum.events.util.Util;
import com.belgaum.networks.IWebRequest;
import com.belgaum.networks.WebRequestPost;

public class EventDetailActivity extends ActionBarActivity {

	EventDetailAdapter adapter;

	ArrayList<Entity> details;

	Toolbar toolBar;
                       
	ListView list;

	EditText editMessage;
	
	String eventId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);

		list = (ListView) findViewById(R.id.list_msgs);
		editMessage = (EditText) findViewById(R.id.edit_message);

		ImageLoader imageLoader = AppController.getInstance().getImageLoader();

		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		TextView eventName = (TextView) findViewById(R.id.event_name);
		TextView eventDesc = (TextView) findViewById(R.id.event_desc);

		Intent intent = getIntent();
		eventName.setText(intent.getStringExtra("name"));
		eventDesc.setText(intent.getStringExtra("desc"));
		eventId = intent.getStringExtra("id");

		NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.image);
		thumbNail.setImageUrl(intent.getStringExtra("image"), imageLoader);

		details = new ArrayList<Entity>();
		adapter = new EventDetailAdapter(getApplicationContext(),
				R.layout.event_chat_row_new, details);
		list.setAdapter(adapter);
		
		getComments();
                
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

	private void getComments() {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", eventId));
		WebRequestPost post = new WebRequestPost(new IWebRequest() {
			
			@Override
			public void onDataArrived(String data) {
				try {
					JSONObject jsonObj = new JSONObject(data);
					JSONArray jsonArr = jsonObj.getJSONArray("comments");
					int len = jsonArr.length();
					for(int i = 0; i < len; i++){
						Entity entity = new Entity();
						JSONObject json = jsonArr.getJSONObject(i);
						entity.setName(json.getString("name"));
						entity.setMessage(json.getString("comment"));
						details.add(entity);
					}
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePairs, EventDetailActivity.this, "");
		post.execute(Util.GET_COMMENTS_URL);
	}

	public void doSend(View v) {
		
		final String msg = editMessage.getText().toString();
		if(msg.isEmpty()){
			Util.showToast(getApplicationContext(), "Type something to post.");
			return;
		}
		
		editMessage.setText("");
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("comment", msg));
		nameValuePairs.add(new BasicNameValuePair("event_id", eventId));
		nameValuePairs.add(new BasicNameValuePair("member_id", Util.getUserId(getApplicationContext())));
		
		Util.showToast(getApplicationContext(), Util.getUserId(EventDetailActivity.this));
		
		
		WebRequestPost post = new WebRequestPost(new IWebRequest() {
			
			@Override
			public void onDataArrived(String data) {
				
				try {
					JSONObject json = new JSONObject(data);

					String loginStatus = json.getString("error");
					if (loginStatus.equalsIgnoreCase("true")) {
						Util.showToast(getApplicationContext(),
								"Something went wrong..!");
					} else {
						Entity entity = new Entity();
						entity.setMessage(msg);
						entity.setName("Govind");
						details.add(entity);
						adapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		}, nameValuePairs, EventDetailActivity.this, "Please Wait.");
		
		post.execute(Util.ADD_COMMENTS_URL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.refresh_menu, menu);
		return true;
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
