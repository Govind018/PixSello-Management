package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.guest.Entity;

public class EmployeeSearchListAdapter extends ArrayAdapter<Entity> {

	ArrayList<Entity> details;

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	Entity entity;

	public EmployeeSearchListAdapter(Context context, int resource,
			ArrayList<Entity> data) {
		super(context, resource, data);
		thisContext = context;
		details = data;
		inflatableRes = resource;
		infalter = (LayoutInflater) thisContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return details.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = infalter.inflate(inflatableRes, null);
			convertView.setTag(holder);

			holder.textEmpName = (TextView) convertView
					.findViewById(R.id.emp_name);
			holder.textEmpDesignation = (TextView) convertView
					.findViewById(R.id.emp_designation);
			holder.textEmpDepartment = (TextView) convertView
					.findViewById(R.id.emp_department);
			holder.textStatus = (TextView) convertView
					.findViewById(R.id.emp_status);
			holder.textHighlights = (TextView) convertView
					.findViewById(R.id.emp_highlights);
			holder.textMarks = (TextView) convertView.findViewById(R.id.emp_marks);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
  
		entity = details.get(position);

		holder.textEmpName.setText(entity.getEmpName());
		holder.textEmpDesignation.setText(entity.getEmpDesignation());
		holder.textEmpDepartment.setText(entity.getEmpDepartment());
//		holder.textStatus.setText(entity.getEmpStatus());
		holder.textHighlights.setText(entity.getEmpHighLights());
		holder.textMarks.setText(entity.getEmpMarks());

		return convertView;
	}

	public class ViewHolder {

		TextView textEmpName;
		TextView textEmpDesignation;
		TextView textEmpDepartment;
		TextView textStatus;
		TextView textHighlights;
		TextView textMarks;
	}
}
