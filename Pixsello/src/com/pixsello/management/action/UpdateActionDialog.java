package com.pixsello.management.action;

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
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class UpdateActionDialog extends DialogFragment {

	TextView textDate;
	TextView textTime;
	TextView textDescription;
	TextView textLocation;
	TextView textReported;
	TextView textRespo;
	TextView textActionTaken;

	EditText editNewUpdate;

	String itemId;

	Button btnDone;
	private OnCompleteListener mListener;
	Button btnClose;
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
	    try {
	        this.mListener = (OnCompleteListener)activity;
	    }
	    catch (final ClassCastException e) {
	        throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
	    }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		View convertView = inflater.inflate(R.layout.update_action_dialog,
				container, false);

		textDate = (TextView) convertView.findViewById(R.id.text_date);
		textTime = (TextView) convertView.findViewById(R.id.text_time);
		textDescription = (TextView) convertView
				.findViewById(R.id.text_description);
		textLocation = (TextView) convertView.findViewById(R.id.text_where);
		textReported = (TextView) convertView.findViewById(R.id.text_reported);
		textRespo = (TextView) convertView.findViewById(R.id.text_respo);
		textActionTaken = (TextView) convertView.findViewById(R.id.text_action);

		btnDone = (Button) convertView.findViewById(R.id.btn_done);
		btnClose = (Button) convertView.findViewById(R.id.btn_close);

		editNewUpdate = (EditText) convertView
				.findViewById(R.id.edit_new_update);

		itemId = getArguments().getString("ID");
		textDate.setText(getArguments().getString("date"));
		textTime.setText(getArguments().getString("time"));
		textDescription.setText(getArguments().getString("description"));
		textLocation.setText(getArguments().getString("location"));
		textReported.setText(getArguments().getString("reported"));
		textRespo.setText(getArguments().getString("respo"));
		textActionTaken.setText(getArguments().getString("action_taken"));

		btnDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				updateAction(itemId, editNewUpdate.getText().toString());

			}
		});

		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				closeItem(itemId);
			}
		});

		return convertView;
	}

	private void updateAction(String updateItemId, String updateTxt) {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair
				.add(new BasicNameValuePair("action_req_id", updateItemId));
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getPROPERTY_ID()));
		nameValuePair.add(new BasicNameValuePair("New_update", updateTxt));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				Uttilities.showToast(getActivity(), data);
				getDialog().cancel();
				
				mListener.onComplete("done");

			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/NewUpdateActionTaken");
	}

	private void closeItem(String closeItemID) {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("ID", closeItemID));
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getPROPERTY_ID()));

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

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/closeActionItem");
	}

	private void refreshList() {

//		ActiveItemsListActivity items = new ActiveItemsListActivity();
//		items.getActiveItemsList(getActivity());

		
	}
	
	public interface OnCompleteListener {
	    public  void onComplete(String result);
	}
}
