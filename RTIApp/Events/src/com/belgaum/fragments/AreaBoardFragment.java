package com.belgaum.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.belgaum.events.DetailsActivity;
import com.belgaum.events.R;
import com.belgaum.events.adapter.CustomAdapter;
import com.belgaum.events.util.Entity;
import com.belgaum.events.util.Util;
import com.belgaum.networks.NetWorkLayer;
import com.belgaum.networks.WebRequest;

public class AreaBoardFragment extends Fragment implements NetWorkLayer {

	ListView list;

	ArrayList<Entity> listOfData;

	ProgressDialog pDialog;

	CustomAdapter adapter;

	MenuItem menuItem;

	boolean isSearchEnabled = false;

	ArrayList<Entity> listOfSearchData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_area_board,
				container, false);

		list = (ListView) convertView.findViewById(R.id.list_events);
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please Wait.");
		list.setOnItemClickListener(listListener);
		listOfData = new ArrayList<Entity>();
		listOfSearchData = new ArrayList<Entity>();

		adapter = new CustomAdapter(getActivity(), R.layout.area_board_row,
				listOfData, "National");
		list.setAdapter(adapter);

		getAllValues();

		return convertView;
	}

	private void setActionBar(boolean status) {

		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View customNav = inflater.inflate(R.layout.action_bar_layout, null);
		EditText search = (EditText) customNav.findViewById(R.id.edit_search);
		search.addTextChangedListener(listener);

		ActionBar actionBar = ((ActionBarActivity) getActivity())
				.getSupportActionBar();

		actionBar.setCustomView(customNav);
		actionBar.setDisplayShowCustomEnabled(status);

	}

	TextWatcher listener = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			if (!s.toString().isEmpty()) {
				listOfSearchData.clear();
				sortData(s.toString());
				isSearchEnabled = true;
			} else {

			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	private void sortData(String input) {

		if (!input.isEmpty()) {

			for (Entity data : listOfData) {

				if (data.getPost().startsWith(input)) {
					listOfSearchData.add(data);
				} else if (data.getName().contains(input)) {
					listOfSearchData.add(data);
				}
			}
		}

		adapter = new CustomAdapter(getActivity(), R.layout.area_board_row,
				listOfSearchData, "National");
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.national, menu);
		menuItem = menu.findItem(R.id.item_navigation);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.item_navigation:
			listOfData.clear();
			adapter.notifyDataSetChanged();
			getAllValues();
			break;

		case R.id.item_search:
			if(!isSearchEnabled){
				menuItem.setVisible(false);
				setActionBar(true);
				isSearchEnabled = true;
			}else{
				menuItem.setVisible(true);
				setActionBar(false);
				isSearchEnabled = false;
				getAllValues();
			}
		default:
			break;
		}
		return true;
	}

	private void getAllValues() {

		if (Util.isNetWorkConnected(getActivity())) {
			pDialog.show();
			WebRequest.addNewRequestQueue(AreaBoardFragment.this,
					Util.AREA_BOARD_URL);
		} else {
			Util.showToast(getActivity(), "Internet not Connected.");
		}
	}

	OnItemClickListener listListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Entity entity; 
			if(!isSearchEnabled){
				entity = listOfData.get(position);	
			}else{
				entity = listOfSearchData.get(position);
			}
			
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

	/*
	 * private void getValues() {
	 * 
	 * ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
	 * 
	 * WebRequestPost post = new WebRequestPost(new IWebRequest() {
	 * 
	 * @Override public void onDataArrived(String data) {
	 * System.out.println(data);
	 * 
	 * try { JSONObject jsonObj = new JSONObject(data); JSONArray jsonArray =
	 * jsonObj.getJSONArray("details"); System.out.println(jsonArray);
	 * 
	 * if (jsonArray.length() == 0) { Util.showToast(getActivity(),
	 * "No Records Found."); listOfData.clear(); return; }
	 * 
	 * for (int i = 0; i < jsonArray.length(); i++) { Entity entity = new
	 * Entity(); JSONObject json = jsonArray.getJSONObject(i);
	 * entity.setName(json.getString("name"));
	 * entity.setPost(json.getString("post"));
	 * entity.setEmail(json.getString("email"));
	 * entity.setMobile(json.getString("mobile")); //
	 * entity.setBusiness(json.getString("business")); listOfData.add(entity); }
	 * 
	 * // adapter.notifyDataSetChanged();
	 * 
	 * } catch (JSONException e) { e.printStackTrace(); } } }, nameValuePair,
	 * getActivity(), "Fetching Area 10 Board Details.");
	 * 
	 * post.execute(Util.AREA_BOARD_URL); }
	 */
	@Override
	public void parseResponse(JSONObject json) {

		try {
			JSONArray jsonArray = json.getJSONArray("details");
			System.out.println(jsonArray);

			if (jsonArray.length() == 0) {
				pDialog.cancel();
				Util.showToast(getActivity(), "No Records Found.");
				listOfData.clear();
				return;
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				Entity entity = new Entity();
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				entity.setId(jsonObj.getString("id"));
				entity.setTableNumber(jsonObj.getString("table_number"));
				entity.setName(jsonObj.getString("name"));
				entity.setPost(jsonObj.getString("post"));
				entity.setEmail(jsonObj.getString("email"));
				entity.setMobile(jsonObj.getString("mobile"));
				entity.setImageUrl(Util.IMAGE_URL
						+ jsonObj.getString("imageUrl"));
				listOfData.add(entity);
			}
			pDialog.cancel();
			listOfSearchData.clear();
			adapter = new CustomAdapter(getActivity(), R.layout.area_board_row,
					listOfData, "National");
			list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showErrorMessage(String message) {
		pDialog.cancel();
		Util.showToast(getActivity(), "Network Error.");
	}
}
