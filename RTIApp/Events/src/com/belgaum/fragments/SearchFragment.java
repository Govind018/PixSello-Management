package com.belgaum.fragments;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.belgaum.events.DetailsActivity;
import com.belgaum.events.R;
import com.belgaum.events.SignUpActivity;
import com.belgaum.events.adapter.SearchListAdapter;
import com.belgaum.events.adapter.SpinnerCustomAdapter;
import com.belgaum.events.util.Entity;
import com.belgaum.events.util.Util;
import com.belgaum.networks.IWebRequest;
import com.belgaum.networks.WebRequestPost;

public class SearchFragment extends Fragment {

	RelativeLayout searchBusiness;

	RelativeLayout searchTableNo;

	RelativeLayout searchName;

	View btnStripBusiness;

	View btnStripTable;

	View btnStripName;

	EditText editSearchKey;

	Button btnSearch;

	private String searchByKey;

	ListView listUsers;

	ArrayList<Entity> listOfUsers;

	SearchListAdapter adapter;

	AutoCompleteTextView autoTextView;

	String searchBy;
	
	ArrayList<String> tables;
	
	ArrayAdapter<String> tablesAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_search_new,
				container, false);

		autoTextView = (AutoCompleteTextView) convertView
				.findViewById(R.id.edit_autocomplete);
		autoTextView.addTextChangedListener(listener);

		tables = new ArrayList<String>();
		
		tablesAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1,tables);
		autoTextView.setAdapter(tablesAdapter);

		searchBusiness = (RelativeLayout) convertView
				.findViewById(R.id.search_business);
		searchBusiness.setOnClickListener(businessListener);
		searchTableNo = (RelativeLayout) convertView
				.findViewById(R.id.search_table_no);
		searchTableNo.setOnClickListener(tableListener);
		searchName = (RelativeLayout) convertView
				.findViewById(R.id.search_name);
		searchName.setOnClickListener(nameListener);
		editSearchKey = (EditText) convertView
				.findViewById(R.id.edit_search_key);

		btnSearch = (Button) convertView.findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(mListener);

		btnStripBusiness = convertView.findViewById(R.id.bottom_strip_business);
		btnStripTable = convertView.findViewById(R.id.bottom_strip_table);
		btnStripName = convertView.findViewById(R.id.bottom_strip_name);
		listUsers = (ListView) convertView.findViewById(R.id.list_search);
		listUsers.setOnItemClickListener(listListener);

		listOfUsers = new ArrayList<Entity>();
		adapter = new SearchListAdapter(getActivity(),
				R.layout.search_item_row, listOfUsers);
		listUsers.setAdapter(adapter);

		initUI();

		getTableData();

		return convertView;
	}

	private void getTableData() {
		
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {
					try {

						JSONObject jsonObjDetails = new JSONObject(data);
						JSONObject objDetails = jsonObjDetails.getJSONObject("details");
						JSONArray arrayTable = objDetails.getJSONArray("table");

						for (int i = 0; i < arrayTable.length(); i++) {
							JSONObject obj = arrayTable.getJSONObject(i);
							tables.add(obj.getString("name"));
						}
						
						tablesAdapter.notifyDataSetChanged();

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, nameValuePairs,getActivity(), "");

			post.execute(Util.SIGNUP_PREFIX_URL);


	}

	TextWatcher listener = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	OnItemClickListener listListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Entity entity = listOfUsers.get(position);
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

	OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {

			startActivity(new Intent(getActivity(), DetailsActivity.class));

		}
	};

	OnClickListener mListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			InputMethodManager imm = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

			String temp = autoTextView.getText().toString();

			if (!temp.isEmpty()) {
				searchBy = temp;
				temp = "";
				autoTextView.setText("");
			} else {
				searchBy = editSearchKey.getText().toString();
			}

			if (searchBy.isEmpty()) {
				Util.showToast(getActivity(), "Please Enter key to search.");
				return;
			}

			if (!Util.isNetWorkConnected(getActivity())) {
				Util.showToast(getActivity(), Util.NETWORK_ERROR_MSG);
				return;
			}

			ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("searchby", searchByKey));
			nameValuePair.add(new BasicNameValuePair("keyword", searchBy));

			listOfUsers.clear();
			adapter.notifyDataSetChanged();

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
							listOfUsers.clear();
							return;
						}

						for (int i = 0; i < jsonArray.length(); i++) {
							Entity entity = new Entity();
							JSONObject json = jsonArray.getJSONObject(i);
							entity.setName(json.getString("name"));
							entity.setTableNumber(json
									.getString("table_number"));
							entity.setPost(json.getString("prefix"));
							entity.setId(json.getString("id"));
							entity.setEmail(json.getString("email"));
							entity.setMobile(json.getString("mobile"));
							entity.setBusiness(json.getString("business"));
							entity.setImageUrl(Util.IMAGE_URL
									+ json.getString("imageUrl"));
							listOfUsers.add(entity);
						}

						adapter.notifyDataSetChanged();

						// SearchListAdapter adapter = new SearchListAdapter(
						// getActivity(), R.layout.search_item_row,
						// listOfUsers);
						// listUsers.setAdapter(adapter);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, nameValuePair, getActivity(), "Finding people.");

			post.execute(Util.SEARCH_URL);

			// startActivity(new Intent(getActivity(),
			// SearchListActivity.class));
		}
	};

	OnClickListener businessListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			initUI();

		}
	};

	OnClickListener tableListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			searchTableNo.setBackgroundColor(getResources().getColor(
					R.color.tab_pressed));
			searchBusiness.setBackgroundColor(getResources().getColor(
					R.color.tab_released));
			searchName.setBackgroundColor(getResources().getColor(
					R.color.tab_released));

			editSearchKey.setHint("Enter Table");

			autoTextView.setVisibility(View.VISIBLE);
			editSearchKey.setVisibility(View.GONE);

			btnStripTable.setBackgroundColor(getResources().getColor(
					R.color.tab_strip));
			btnStripBusiness.setBackgroundColor(getResources().getColor(
					R.color.tab_pressed));
			btnStripName.setBackgroundColor(getResources().getColor(
					R.color.tab_pressed));

			searchByKey = "table_number";
		}
	};

	OnClickListener nameListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			searchName.setBackgroundColor(getResources().getColor(
					R.color.tab_pressed));
			searchBusiness.setBackgroundColor(getResources().getColor(
					R.color.tab_released));
			searchTableNo.setBackgroundColor(getResources().getColor(
					R.color.tab_released));

			editSearchKey.setHint("Enter Name");

			autoTextView.setVisibility(View.GONE);
			editSearchKey.setVisibility(View.VISIBLE);

			btnStripTable.setBackgroundColor(getResources().getColor(
					R.color.tab_pressed));
			btnStripBusiness.setBackgroundColor(getResources().getColor(
					R.color.tab_pressed));
			btnStripName.setBackgroundColor(getResources().getColor(
					R.color.tab_strip));
			searchByKey = "name";
		}
	};

	private void initUI() {

		searchBusiness.setBackgroundColor(getResources().getColor(
				R.color.tab_pressed));
		searchTableNo.setBackgroundColor(getResources().getColor(
				R.color.tab_released));
		searchName.setBackgroundColor(getResources().getColor(
				R.color.tab_released));

		editSearchKey.setHint("Enter Business");

		autoTextView.setVisibility(View.GONE);
		editSearchKey.setVisibility(View.VISIBLE);

		btnStripBusiness.setBackgroundColor(getResources().getColor(
				R.color.tab_strip));
		btnStripTable.setBackgroundColor(getResources().getColor(
				R.color.tab_pressed));
		btnStripName.setBackgroundColor(getResources().getColor(
				R.color.tab_pressed));

		searchByKey = "business";
	}
}
