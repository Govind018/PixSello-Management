package com.pixsello.management.action;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.pixsello.management.R;
import com.pixsello.management.adapters.ActionTakenListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class ClosedItemsDialog extends DialogFragment {

	private String id;
	ActionTakenListAdapter previousActionTaken ;

	ListView list;

	ArrayList<Entity> listOfActionTaken;

	ImageView closeDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View convertView = inflater.inflate(R.layout.closed_item_dialog, container,false);
		list = (ListView) convertView.findViewById(R.id.list_closed_items_details);
		closeDialog = (ImageView) convertView.findViewById(R.id.image_close);
		id =getArguments().getString("id");
		listOfActionTaken = new ArrayList<Entity>();
		previousActionTaken = new ActionTakenListAdapter(getActivity(), R.layout.action_taken_item, listOfActionTaken);
		list.setAdapter(previousActionTaken);
		getData();

		closeDialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().cancel();

			}
		});
		return convertView;
	}

	private void getData() {

		ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("ID", id));
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.getUserLoginId(getActivity())));

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

		post.execute(Uttilities.CLOSED_ITEMS_DETAILS);

	}
}
