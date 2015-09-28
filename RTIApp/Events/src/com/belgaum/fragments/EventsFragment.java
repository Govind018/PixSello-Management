package com.belgaum.fragments;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.belgaum.events.EventDetailActivity;
import com.belgaum.events.R;
import com.belgaum.events.adapter.CustomAdapter;
import com.belgaum.events.util.Entity;
import com.belgaum.events.util.Util;
import com.belgaum.networks.NetWorkLayer;
import com.belgaum.networks.WebRequest;

public class EventsFragment extends Fragment implements NetWorkLayer {

	ListView list;

	ArrayList<Entity> listOfEvents;

	ProgressDialog pDialog;

	CustomAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_events,
				container, false);

		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please Wait.");
		list = (ListView) convertView.findViewById(R.id.list_events);
		list.setOnItemClickListener(listListener);
		list.setAdapter(adapter);
		listOfEvents = new ArrayList<Entity>();
		adapter = new CustomAdapter(getActivity(), R.layout.event_row,
				listOfEvents, "Events");
		list.setAdapter(adapter);
		ActionBar actionBar = ((ActionBarActivity) getActivity())
				.getSupportActionBar();
		actionBar.setDisplayShowCustomEnabled(false);
		getAllEvents();

		return convertView;
	}

	OnItemClickListener listListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Entity entity = listOfEvents.get(position);
			Intent intent = new Intent(getActivity(), EventDetailActivity.class);
			intent.putExtra("notification", false);
			intent.putExtra("name", entity.getName());
			intent.putExtra("desc", entity.getEventDescription());
			intent.putExtra("image", entity.getImageUrl());
			intent.putExtra("id", entity.getId());
			startActivity(intent);
		}
	};

	private void getAllEvents() {

		if (Util.isNetWorkConnected(getActivity())) {
			pDialog.show();
			pDialog.setCanceledOnTouchOutside(false);
			WebRequest.addNewRequestQueue(EventsFragment.this, Util.EVETNS_URL);
		} else {
			Util.showToast(getActivity(), "Internet not Connected.");
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.clear();
		inflater.inflate(R.menu.refresh, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		listOfEvents.clear();
		adapter.notifyDataSetChanged();
		getAllEvents();

		return true;
	}

	@Override
	public void parseResponse(JSONObject json) {

		System.out.println(json);
		try {

			JSONArray jsonArray = json.getJSONArray("details");
			System.out.println(jsonArray);

			if (jsonArray.length() == 0) {
				pDialog.cancel();
				Util.showToast(getActivity(), "No Records Found.");
				listOfEvents.clear();
				return;
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				Entity entity = new Entity();
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				entity.setName(jsonObj.getString("title"));
				entity.setId(jsonObj.getString("id"));
				entity.setEventDescription(jsonObj.getString("desrip"));
				entity.setEventDate(jsonObj.getString("eventdate"));
				entity.setImageUrl(Util.IMAGE_URL
						+ jsonObj.getString("imageUrl"));
				listOfEvents.add(entity);
			}
			pDialog.cancel();
			Collections.reverse(listOfEvents);
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
			pDialog.cancel();
		}
	}

	@Override
	public void showErrorMessage(String message) {

		Util.showToast(getActivity(), "Network Error.");
		pDialog.cancel();
	}

	/*
	 * private void getEvents() {
	 * 
	 * ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
	 * 
	 * WebRequestPost post = new WebRequestPost(new IWebRequest() {
	 * 
	 * @Override public void onDataArrived(String data) {
	 * 
	 * System.out.println(data);
	 * 
	 * try { listOfEvents = new ArrayList<Entity>(); JSONObject jsonObj = new
	 * JSONObject(data); JSONArray jsonArray = jsonObj.getJSONArray("details");
	 * System.out.println(jsonArray);
	 * 
	 * if (jsonArray.length() == 0) { Util.showToast(getActivity(),
	 * "No Records Found."); listOfEvents.clear(); return; }
	 * 
	 * for (int i = 0; i < jsonArray.length(); i++) { Entity entity = new
	 * Entity(); JSONObject json = jsonArray.getJSONObject(i);
	 * entity.setName(json.getString("title")); listOfEvents.add(entity); }
	 * 
	 * CustomAdapter adapter = new CustomAdapter(getActivity(),
	 * R.layout.event_row, listOfEvents, "Events");
	 * 
	 * list.setAdapter(adapter);
	 * 
	 * } catch (JSONException e) { e.printStackTrace(); } } }, nameValuePair,
	 * getActivity(), "Fetching events.");
	 * 
	 * post.execute(Util.EVETNS_URL); }
	 */

}
