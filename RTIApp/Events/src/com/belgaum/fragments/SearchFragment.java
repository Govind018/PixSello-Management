package com.belgaum.fragments;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.belgaum.events.DetailsActivity;
import com.belgaum.events.R;
import com.belgaum.events.adapter.SearchListAdapter;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_search_new,
				container, false);

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

		initUI();

		return convertView;
	}

	OnItemClickListener listListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Entity entity = listOfUsers.get(position);
			Intent intent = new Intent(getActivity(), DetailsActivity.class);
			intent.putExtra("name", entity.getName());
			intent.putExtra("email", entity.getEmail());
			intent.putExtra("phone", entity.getMobile());
			startActivity(intent);

		}
	};

	OnClickListener mListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			// Util.showToast(getActivity(), searchByKey);

			String searchBy = editSearchKey.getText().toString();

			if (searchBy.isEmpty()) {
				Util.showToast(getActivity(), "Please Enter key to search.");
				return;
			}

			ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("searchby", searchByKey));
			nameValuePair.add(new BasicNameValuePair("keyword", searchBy));

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {

					System.out.println(data);

					try {
						listOfUsers = new ArrayList<Entity>();
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
							entity.setId(json.getString("id"));
							entity.setEmail(json.getString("email"));
							entity.setMobile(json.getString("mobile"));
							entity.setBusiness(json.getString("business"));
							entity.setImage(json.getString("image"));
							listOfUsers.add(entity);
						}

						SearchListAdapter adapter = new SearchListAdapter(
								getActivity(), R.layout.search_item_row,
								listOfUsers);
						listUsers.setAdapter(adapter);

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

			editSearchKey.setHint("Enter Table Number");

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

		btnStripBusiness.setBackgroundColor(getResources().getColor(
				R.color.tab_strip));
		btnStripTable.setBackgroundColor(getResources().getColor(
				R.color.tab_pressed));
		btnStripName.setBackgroundColor(getResources().getColor(
				R.color.tab_pressed));

		searchByKey = "business";
	}
}
