package com.belgaum.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.belgaum.events.R;
import com.belgaum.events.adapter.CustomAdapter;

public class AreaBoardFragment extends Fragment {

	ListView list;

	CustomAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_area_board, container, false);
		
		ArrayList<String> items = new ArrayList<String>();
		for(int i = 0; i <= 15; i++){
			items.add("Govind C Mastamardi");
		}

		list = (ListView) convertView.findViewById(R.id.list_events);
//		adapter = new CustomAdapter(getActivity(), R.layout.event_row, items, "Events");
//		list.setAdapter(adapter);

		return convertView;
	}
}
