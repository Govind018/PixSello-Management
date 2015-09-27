package com.belgaum.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.belgaum.events.R;

public class AimObjectivesFragment extends Fragment {
	private AutoCompleteTextView actv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_aim_objectives, container, false);
		
		
//		actv = (AutoCompleteTextView)convertView.findViewById(R.id.autoCompleteTextView1); 
//		
//		String[] countries = {"india","Australia24","Srilanka","Afric"};
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,countries);
//		actv.setAdapter(adapter);
		
		return convertView;
	}
}
