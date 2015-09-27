package com.belgaum.events;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.toolbox.ImageLoader;
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

	String eventName;

	String eventDesc;

	String eventUrl;

	BroadcastReceiver mReciever;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail_new);

		list = (ListView) findViewById(R.id.list_msgs);
		editMessage = (EditText) findViewById(R.id.edit_message);

		ImageLoader imageLoader = AppController.getInstance().getImageLoader();

		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		eventId = intent.getStringExtra("id");

		// if(intent.getBooleanExtra("notification", false)){
		// eventName = intent.getStringExtra("name");
		// eventDesc = intent.getStringExtra("desc");
		// eventUrl = intent.getStringExtra("image");
		// }

		details = new ArrayList<Entity>();
		adapter = new EventDetailAdapter(getApplicationContext(),
				R.layout.event_chat_row_new, details);
		list.setAdapter(adapter);

		list.setOnItemClickListener(listener);

		// getComments();

		getEventsData();

	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			if (position == 0) {
				Entity en = details.get(position);
				Intent intent = new Intent(EventDetailActivity.this,
						ImagePreviewActivity.class);
				intent.putExtra("image", en.getImageUrl());
				intent.putExtra("title", en.getEventName());
				overridePendingTransition(R.anim.zoom, 0);
				startActivity(intent);
			}
		}
	};

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

					if (len == 0) {
						Entity entity = new Entity();
						entity.setHeader(true);
						entity.setEventName(eventName);
						entity.setEventDescription(eventDesc);
						entity.setImageUrl(eventUrl);
						details.add(entity);
					} else {
						for (int i = 0; i < len; i++) {
							Entity entity = new Entity();
							JSONObject json = jsonArr.getJSONObject(i);
							if (i == 0) {
								entity.setHeader(true);
								entity.setHeader(true);
								entity.setEventName(eventName);
								entity.setEventDescription(eventDesc);
								entity.setImageUrl(eventUrl);
							} else {
								entity.setHeader(false);
							}
							entity.setName(json.getString("name"));
							entity.setMessage(json.getString("comment"));
							details.add(entity);
						}
					}
					list.setSelection(0);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePairs, EventDetailActivity.this, "Please Wait.");
		post.execute(Util.GET_COMMENTS_URL);
	}

	private void getEventsData() {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", eventId));
		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {
				try {
					JSONObject jsonObj = new JSONObject(data);
					JSONArray jsonArr = jsonObj.getJSONArray("comments");
					int len = jsonArr.length();

					if (len == 0) {
						Entity entity = new Entity();
						entity.setHeader(true);
						entity.setEventName(jsonObj.getString("title"));
						entity.setEventDescription(jsonObj.getString("desrip"));
						entity.setEventDate(jsonObj.getString("eventdate"));
						entity.setImageUrl(Util.IMAGE_URL
								+ jsonObj.getString("imageUrl"));
						details.add(entity);
					} else {
						for (int i = 0; i < len; i++) {
							Entity entity = new Entity();
							JSONObject json = jsonArr.getJSONObject(i);
							if (i == 0) {
								entity.setHeader(true);
								entity.setHeader(true);
								entity.setEventName(jsonObj.getString("title"));
								entity.setEventDescription(jsonObj
										.getString("desrip"));
								entity.setEventDate(jsonObj
										.getString("eventdate"));
								entity.setImageUrl(Util.IMAGE_URL
										+ jsonObj.getString("imageUrl"));
							} else {
								entity.setHeader(false);
							}
							entity.setName(json.getString("name"));
							entity.setMessage(json.getString("comment"));
							details.add(entity);
						}
					}
					list.setSelection(0);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePairs, EventDetailActivity.this, "Please Wait.");
		post.execute(Util.EVENT_DATA_URL);
	}

	public void doSend(View v) {

		final String msg = editMessage.getText().toString();
		if (msg.isEmpty()) {
			Util.showToast(getApplicationContext(), "Type something to post.");
			return;
		}

		editMessage.setText("");

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("comment", msg));
		nameValuePairs.add(new BasicNameValuePair("event_id", eventId));
		nameValuePairs.add(new BasicNameValuePair("member_id", Util
				.getUserId(getApplicationContext())));
		// nameValuePairs.add(new BasicNameValuePair("regId",
		// Util.getRegId(EventDetailActivity.this)));
		// nameValuePairs.add(new BasicNameValuePair("message", msg));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {
					JSONObject json = new JSONObject(data);

					// Util.showToast(getApplicationContext(),"" + json);

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

					String loginStatus = json.getString("error");
					if (loginStatus.equalsIgnoreCase("true")) {
						Util.showToast(getApplicationContext(),
								"Something went wrong..!");
					} else {
						Entity entity = new Entity();
						entity.setMessage(msg);
						entity.setName(Util
								.getUserName(EventDetailActivity.this));
						details.add(entity);
						list.setSelection(details.size() - 1);
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
		getMenuInflater().inflate(R.menu.refresh, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		case R.id.item_navigation:
			details.clear();
			adapter.notifyDataSetChanged();
			// getComments();
			getEventsData();

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void rowSe(View v) {
		list.setSelection(details.size() - 1);
	}
}
