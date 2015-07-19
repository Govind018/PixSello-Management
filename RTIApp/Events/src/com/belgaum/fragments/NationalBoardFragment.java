package com.belgaum.fragments;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.belgaum.events.DetailsActivity;
import com.belgaum.events.R;
import com.belgaum.events.adapter.CustomAdapter;
import com.belgaum.events.util.Entity;
import com.belgaum.events.util.Util;
import com.belgaum.networks.IWebRequest;
import com.belgaum.networks.NetWorkLayer;
import com.belgaum.networks.WebRequest;
import com.belgaum.networks.WebRequestPost;

public class NationalBoardFragment extends Fragment implements NetWorkLayer {

	ListView listNationalBoard;
	ArrayList<Entity> listOfData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_national_board,
				container, false);
		listNationalBoard = (ListView) convertView
				.findViewById(R.id.list_nationalboard);
		listNationalBoard.setOnItemClickListener(listListener);
		listOfData = new ArrayList<Entity>();

		// getValues();

		getAllValues();

		return convertView;
	}
	
	OnItemClickListener listListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Entity entity = listOfData.get(position);
			Intent intent = new Intent(getActivity(), DetailsActivity.class);
			intent.putExtra("name", entity.getName());
			intent.putExtra("email", entity.getEmail());
			intent.putExtra("phone", entity.getMobile());
			intent.putExtra("post", entity.getPost());
			intent.putExtra("table", entity.getTableNumber());
			intent.putExtra("image", entity.getImageUrl());
			startActivity(intent);

		}
	};

	private void getAllValues() {

		if (Util.isNetWorkConnected(getActivity())) {
			WebRequest.addNewRequestQueue(NationalBoardFragment.this,
					Util.NATIONAL_BOARD_URL);
		} else {
			Util.showToast(getActivity(), "Internet not Connected.");
		}
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

					// adapter.notifyDataSetChanged();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair, getActivity(), "Fetching National Board Details.");

		post.execute(Util.NATIONAL_BOARD_URL);

	}

	@Override
	public void parseResponse(JSONObject json) {

		try {
			JSONArray jsonArray = json.getJSONArray("details");
			System.out.println(jsonArray);

			if (jsonArray.length() == 0) {
				Util.showToast(getActivity(), "No Records Found.");
				listOfData.clear();
				return;
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				Entity entity = new Entity();
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				entity.setId(jsonObj.getString("id"));
				entity.setName(jsonObj.getString("name"));
				entity.setTableNumber(jsonObj.getString("table_number"));
				entity.setPost(jsonObj.getString("post"));
				entity.setEmail(jsonObj.getString("email"));
				entity.setMobile(jsonObj.getString("mobile"));
				entity.setImageUrl(Util.IMAGE_URL
						+ jsonObj.getString("imageUrl"));
				listOfData.add(entity);
			}

			CustomAdapter adapter = new CustomAdapter(getActivity(),
					R.layout.area_board_row, listOfData, "National");
			listNationalBoard.setAdapter(adapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showErrorMessage(String message) {
		Util.showToast(getActivity(), "Network Error.");

	}
}
