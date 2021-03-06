package com.pixsello.management.aparangi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class EmployeeDetailEditDialog extends DialogFragment {

	TextView textName;
	TextView editDesignation;
	TextView editDepartment;
	TextView editStatus;
	EditText editRemarks;
	EditText editHighlights;

	EditText editdob;
	EditText editReason;

	String designation;
	String department;
	String status;
	String empStatus;
	String highlights;
	String empId;

	LinearLayout editLayout;
	LinearLayout reportLayout;

	Button btnReport;
	Button btnEdit;
	Button btnExit;
	private OnCompleteListener mListener;

	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			this.mListener = (OnCompleteListener) activity;
		} catch (final ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnCompleteListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View convertView = inflater.inflate(R.layout.employee_edit_dialog,
				container, false);

		editLayout = (LinearLayout) convertView.findViewById(R.id.edit_emp);

		textName = (TextView) convertView.findViewById(R.id.text_name);
		editDesignation = (TextView) convertView
				.findViewById(R.id.text_designation);
		editDepartment = (TextView) convertView
				.findViewById(R.id.text_department);
		// editStatus = (TextView) convertView.findViewById(R.id.text_status);
		editRemarks = (EditText) convertView.findViewById(R.id.edit_remarks);
		editHighlights = (EditText) convertView
				.findViewById(R.id.edit_highlights);
		textName.setText(getArguments().getString("name"));
		editDesignation.setText(getArguments().getString("designation"));
		editDepartment.setText(getArguments().getString("department"));
		// editStatus.setText(getArguments().getString("status"));
		btnReport = (Button) convertView.findViewById(R.id.edit_button);
		btnEdit = (Button) convertView.findViewById(R.id.btn_edit);
		btnExit = (Button) convertView.findViewById(R.id.report_exit_action);

		editdob = (EditText) convertView.findViewById(R.id.edit_date_leaving);
		editReason = (EditText) convertView.findViewById(R.id.edit_reason);

		empId = getArguments().getString("empid");

		reportLayout = (LinearLayout) convertView
				.findViewById(R.id.report_layout);

		btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				doSubmit();

			}
		});

		btnReport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				editLayout.setVisibility(View.GONE);
				reportLayout.setVisibility(View.VISIBLE);
			}
		});

		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				performExit();
			}
		});

		return convertView;
	}

	public void doSubmit() {

		empStatus = editRemarks.getText().toString();
		highlights = editHighlights.getText().toString();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("empID", empId));
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("Highlights", highlights));
		nameValuePair
				.add(new BasicNameValuePair("Employmentstatus", empStatus));

		if (highlights.isEmpty() || empStatus.isEmpty()) {

			Uttilities.showToast(getActivity(), "Enter highlights to update");
			return;
		}

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				JSONObject json;
				try {
					json = new JSONObject(data);
					Uttilities.showToast(getActivity(),
							json.getString("result"));
					getDialog().cancel();
					mListener.onComplete("done");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/updatehighlights");
	}

	public void performExit() {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("empID", empId));
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));
		nameValuePair.add(new BasicNameValuePair("DateofjobExit", editdob
				.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("Reasontoleave", editReason
				.getText().toString()));

		if (editdob.getText().toString().isEmpty()
				|| editReason.getText().toString().isEmpty()) {

			Uttilities.showToast(getActivity(), "Please enter all fields");
			return;
		}

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {
					JSONObject json = new JSONObject(data);
					Uttilities.showToast(getActivity(),
							json.getString("result"));
					getDialog().cancel();
					mListener.onComplete("done");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/Reportjobexit");

	}

	public interface OnCompleteListener {
		public void onComplete(String result);
	}
}
