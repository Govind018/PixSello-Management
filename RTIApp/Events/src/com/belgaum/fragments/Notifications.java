package com.belgaum.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.belgaum.events.R;
import com.belgaum.events.util.Util;
import com.belgaum.networks.IWebRequest;
import com.belgaum.networks.WebRequestPost;

public class Notifications extends Fragment {
	CheckBox chk;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_notifications,
				container, false);

		chk = (CheckBox) convertView.findViewById(R.id.chk_notifications);
		chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				doUpdateNotifications(isChecked);

			}
		});

		initNotificationsStatus();

		return convertView;
	}

	private void initNotificationsStatus() {
		List<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
		dataToSend.add(new BasicNameValuePair("user", Util
				.getUserId(getActivity())));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {
				JSONObject json;
				try {
					json = new JSONObject(data);
					boolean status = json.getBoolean("error");
					if (status) {
						Util.showToast(getActivity(), "Try updating later.");
					} else {
						String st = json.getString("status");
						if (st.equalsIgnoreCase("0")) {
							chk.setChecked(false);
						} else {
							chk.setChecked(true);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, dataToSend, getActivity(), "Updating");

		post.execute(Util.NOTIFICATION_INITIAL_STATUS_URL);
	}

	private void doUpdateNotifications(boolean isChecked) {

		int status = isChecked ? 1 : 0;

		List<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
		dataToSend.add(new BasicNameValuePair("user", Util
				.getUserId(getActivity())));
		dataToSend
				.add(new BasicNameValuePair("status", String.valueOf(status)));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				JSONObject json;
				try {
					json = new JSONObject(data);
					boolean status = json.getBoolean("error");
					if (status) {
						Util.showToast(getActivity(), "Try updating later.");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, dataToSend, getActivity(), "Updating.");

		post.execute(Util.NOTIFICATION_STATUS_URL);
	}
}
