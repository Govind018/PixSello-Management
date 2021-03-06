package com.belgaum.fragments;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

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
	
	ArrayList<Entity> listOfSearchData;

	ProgressDialog pDialog;

	CustomAdapter adapter;

	MenuItem menuItemRefresh;
	MenuItem menuItemSearch;
	
	boolean isSearchEnabled = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		    

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_national_board,
				container, false);
		listNationalBoard = (ListView) convertView
				.findViewById(R.id.list_nationalboard);
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please Wait.");
		listNationalBoard.setOnItemClickListener(listListener);
		listOfData = new ArrayList<Entity>();
		listOfSearchData = new ArrayList<Entity>();

		// getValues();

		adapter = new CustomAdapter(getActivity(), R.layout.area_board_row,
				listOfData, "National");
		listNationalBoard.setAdapter(adapter);

		getAllValues();

		return convertView;
	}

	private void setActionBar(boolean status) {
		
		// custom action bar
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, Gravity.RIGHT
						| Gravity.CENTER_VERTICAL);
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View customNav = inflater.inflate(R.layout.action_bar_layout, null);
		EditText search = (EditText) customNav.findViewById(R.id.edit_search);
		search.addTextChangedListener(listener);
		
		ImageView imageClose = (ImageView) customNav.findViewById(R.id.close_image);
		imageClose.setOnClickListener(clickListener);
		
		ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
		
		actionBar.setCustomView(customNav);
		actionBar.setDisplayShowCustomEnabled(status);

	}
	
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Util.closeKB(getActivity());
			menuItemRefresh.setVisible(true);
			menuItemSearch.setVisible(true);
			setActionBar(false);
			isSearchEnabled = false;
			adapter = new CustomAdapter(getActivity(), R.layout.area_board_row,listOfData, "National");
			listNationalBoard.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			
		}
	};
	
	TextWatcher listener = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
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

				if (data.getPost().toUpperCase().startsWith(input.toUpperCase())) {
					listOfSearchData.add(data);
				}else if (data.getName().toUpperCase().contains(input.toUpperCase())){
					listOfSearchData.add(data);
				}
			}
		}

		adapter = new CustomAdapter(getActivity(), R.layout.area_board_row,listOfSearchData, "National");
		listNationalBoard.setAdapter(adapter);
		adapter.notifyDataSetChanged();
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

	private void getAllValues() {

		if (Util.isNetWorkConnected(getActivity())) {
			pDialog.show();
			pDialog.setCanceledOnTouchOutside(false);
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
						listOfData.add(entity);
					}
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
				pDialog.cancel();
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
			pDialog.cancel();
			listOfSearchData.clear();
			adapter = new CustomAdapter(getActivity(), R.layout.area_board_row,
					listOfData, "National");
			listNationalBoard.setAdapter(adapter);
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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.national, menu);
		
		menuItemRefresh = menu.findItem(R.id.item_navigation);
		menuItemSearch = menu.findItem(R.id.item_search);
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
				menuItemRefresh.setVisible(false);
				menuItemSearch.setVisible(false);
				setActionBar(true);
				isSearchEnabled = true;
			}else{
				menuItemRefresh.setVisible(true);
				menuItemSearch.setVisible(true);
				setActionBar(false);
				isSearchEnabled = false;
			}
		default:
			break;
		}
		return true;
	}
}
