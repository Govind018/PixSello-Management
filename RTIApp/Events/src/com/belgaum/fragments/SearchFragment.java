package com.belgaum.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.belgaum.events.R;
import com.belgaum.events.SearchListActivity;

public class SearchFragment extends Fragment {

	RelativeLayout searchBusiness;

	RelativeLayout searchTableNo;

	RelativeLayout searchName;

	View btnStripBusiness;

	View btnStripTable;

	View btnStripName;

	EditText editSearchKey;

	Button btnSearch;

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

		initUI();

		return convertView;
	}

	OnClickListener mListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			startActivity(new Intent(getActivity(), SearchListActivity.class));

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

	}
}
