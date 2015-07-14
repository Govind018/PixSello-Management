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
import com.belgaum.events.util.Entity;
import com.belgaum.events.util.Util;
import com.belgaum.networks.IWebRequest;
import com.belgaum.networks.WebRequestPost;

public class NationalBoardFragment extends Fragment {

	ListView listNationalBoard;
	CustomAdapter adapter;
	ArrayList<Entity> listOfData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_national_board,
				container, false);
		listNationalBoard = (ListView) convertView
				.findViewById(R.id.list_nationalboard);
		listOfData = new ArrayList<Entity>();
		adapter = new CustomAdapter(getActivity(), R.layout.event_row,
				listOfData, "National");
		listNationalBoard.setAdapter(adapter);

		getValues();

		return convertView;
	}

	private void getValues() {

		ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {
				System.out.println(data);

				try {
					JSONObject jsonObj = new JSONObject(data);
					JSONArray jsonArray = jsonObj.getJSONArray("details");
					System.out.println(jsonArray);

					if (jsonArray.length() == 0) {
						Util.showToast(getActivity(), "No Records Found.");
						listOfData.clear();
						return;
					}

					for (int i = 0; i < jsonArray.length(); i++) {
						Entity entity = new Entity();
						JSONObject json = jsonArray.getJSONObject(i);
						entity.setName(json.getString("name"));
						entity.setPost(json.getString("post"));
						// entity.setEmail(json.getString("email"));
						// entity.setMobile(json.getString("mobile"));
						// entity.setBusiness(json.getString("business"));
						listOfData.add(entity);
					}

					adapter.notifyDataSetChanged();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair, getActivity(), "Fetching National Board Details.");

		post.execute(Util.NATIONAL_BOARD_URL);

	}
}
