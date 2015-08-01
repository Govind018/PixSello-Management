package com.belgaum.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belgaum.events.R;

public class AimObjectivesFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_aim_objectives, container, false);
		
		TextView edit = (TextView) convertView.findViewById(R.id.text);
//		edit.setText(Html.fromHtml("<pre>To develop the fellowship of young men through " + 
//								   "the medium of their business and Professional "+ 
//								   "occupations and community service activities</pre>"));

		return convertView;
	}
}
