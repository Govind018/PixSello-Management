package com.belgaum.fragments;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.belgaum.events.R;
import com.belgaum.events.adapter.CustomAdapter;
import com.belgaum.events.adapter.SearchListAdapter;
import com.belgaum.events.util.Entity;
import com.belgaum.events.util.Util;
import com.belgaum.networks.IWebRequest;
import com.belgaum.networks.NetWorkLayer;
import com.belgaum.networks.WebRequestPost;

public class EventsFragment extends Fragment implements NetWorkLayer{

	ListView list;
	
	CustomAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ArrayList<String> items = new ArrayList<String>();
		for(int i = 0; i <= 15; i++){
			items.add("Govind C Mastamardi");
		}
		View convertView = inflater.inflate(R.layout.fragment_events, container, false);

		list = (ListView) convertView.findViewById(R.id.list_events);
		list.setAdapter(adapter);
		
		getEvents();

		return convertView;
	}

	private void getEvents() {
		
		ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

		WebRequestPost post = new WebRequestPost(new IWebRequest() {
			
			@Override
			public void onDataArrived(String data) {

				System.out.println(data);

				try {
					ArrayList<Entity> listOfEvents = new ArrayList<Entity>();
					JSONObject jsonObj = new JSONObject(data);
					JSONArray jsonArray = jsonObj.getJSONArray("details");
					System.out.println(jsonArray);

					if (jsonArray.length() == 0) {
						Util.showToast(getActivity(), "No Records Found.");
						listOfEvents.clear();
						return;
					}

					for (int i = 0; i < jsonArray.length(); i++) {
						Entity entity = new Entity();
						JSONObject json = jsonArray.getJSONObject(i);
						entity.setName(json.getString("title"));
//						entity.setId(json.getString("id"));
//						entity.setEmail(json.getString("email"));
//						entity.setMobile(json.getString("mobile"));
//						entity.setBusiness(json.getString("business"));
						listOfEvents.add(entity);
					}

					CustomAdapter adapter = new CustomAdapter(getActivity(), R.layout.event_row, listOfEvents, "Events");
					
					list.setAdapter(adapter);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair, getActivity(), "Fetching events.");
		
		post.execute(Util.EVETNS_URL);
	}

	@Override
	public void onStart() {
		super.onStart();
		
//		Util.showToast(getActivity(), "onStart");
		
//		WebRequest.addNewRequestQueue(getActivity(), "");
	}
	
	@Override
	public void parseResponse(JSONObject json) {
		
	}

	@Override
	public void showErrorMessage(String message) {
		
	}
}
