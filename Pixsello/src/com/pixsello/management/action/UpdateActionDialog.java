package com.pixsello.management.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.adapters.ActionTakenListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
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
	
	Context context;
	
	String date;
	String time;
	
	ArrayAdapter<String> serviceAdapter;
	
	ActionTakenListAdapter previousActionTaken;
	
	ListView listActionTaken;
	
	ImageView closeDialog;
	
	ArrayList<Entity> listOfActionTaken;
	
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
		getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		View convertView = inflater.inflate(R.layout.update_action_dialog_new,
				container, false);

		textDate = (TextView) convertView.findViewById(R.id.text_date);
		textTime = (TextView) convertView.findViewById(R.id.text_time);
		textDescription = (TextView) convertView
				.findViewById(R.id.text_description);
		textLocation = (TextView) convertView.findViewById(R.id.text_location);
		textReported = (TextView) convertView.findViewById(R.id.text_reported);
		textRespo = (TextView) convertView.findViewById(R.id.text_responsibility);
		closeDialog = (ImageView) convertView.findViewById(R.id.image_close);
		
		listActionTaken = (ListView) convertView.findViewById(R.id.list_action_taken);

		btnDone = (Button) convertView.findViewById(R.id.btn_done);
		btnClose = (Button) convertView.findViewById(R.id.btn_close);

		editNewUpdate = (EditText) convertView
				.findViewById(R.id.edit_new_update);

		itemId = getArguments().getString("ID");
		date = getArguments().getString("date");
		textDate.setText(date);
		time = getArguments().getString("time");
		textTime.setText(time);
		textDescription.setText(getArguments().getString("description"));
		textLocation.setText(getArguments().getString("location"));
		textReported.setText(getArguments().getString("reported"));
		textRespo.setText(getArguments().getString("respo"));
//		textActionTaken.setText(getArguments().getString("action_taken"));
		
		listOfActionTaken = new ArrayList<Entity>();
		
		previousActionTaken = new ActionTakenListAdapter(getActivity(), R.layout.action_taken_item, listOfActionTaken);
		listActionTaken.setAdapter(previousActionTaken);

		closeDialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				getDialog().cancel();
				
			}
		});
		
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

	@Override
	public void onStart() {
		super.onStart();
		
		getActionTakenDetails();
	}
	
	private void getActionTakenDetails() {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair
				.add(new BasicNameValuePair("ID", itemId));
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getPROPERTY_ID()));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {
				
				try {
					JSONObject json = new JSONObject(data);
					if (json.has("error_message")) {

					} else {
						
						JSONArray jsonArray = json.getJSONArray("result");
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							Entity en = new Entity();
							en.setTime(jsonObj.getString("Time"));
							en.setDate(jsonObj.getString("Date"));
							en.setActionTaken(jsonObj.getString("New_update"));
							listOfActionTaken.add(en);
						}
						previousActionTaken.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}


			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getUpdatedDetailsDetail");
		
	}

	private void updateAction(String updateItemId, String updateTxt) {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair
				.add(new BasicNameValuePair("action_req_id", updateItemId));
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getPROPERTY_ID()));
		nameValuePair.add(new BasicNameValuePair("New_update", updateTxt));
		nameValuePair.add(new BasicNameValuePair("Date", Uttilities.getDate()));
		nameValuePair.add(new BasicNameValuePair("Time", Uttilities.getTime()));

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
	
	public interface OnCompleteListener {
	    public  void onComplete(String result);
	}
}
