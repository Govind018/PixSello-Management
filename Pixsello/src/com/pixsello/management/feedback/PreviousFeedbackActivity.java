package com.pixsello.management.feedback;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.pixsello.management.R;
import com.pixsello.management.adapters.FeedbackListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class PreviousFeedbackActivity extends Activity {

	ListView listFeedback;

	ArrayList<Entity> listOfFeedBack;

	FeedbackListAdapter adapter;

	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previous_feedback);

		listFeedback = (ListView) findViewById(R.id.list_feedback);
		listOfFeedBack = new ArrayList<Entity>();

		adapter = new FeedbackListAdapter(getApplicationContext(),
				R.layout.feedback_list_item, listOfFeedBack);
		listFeedback.setAdapter(adapter);

		dialog = new ProgressDialog(PreviousFeedbackActivity.this);
		dialog.setMessage("Please Wait..");
	}

	public void goBack(View v) {

		finish();
	}

	@Override
	protected void onStart() {
		super.onStart();

		getPreviousFeedback();

	}

	private void getPreviousFeedback() {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getPROPERTY_ID()));

		dialog.show();
		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {
				Entity en;
				try {
					JSONObject json = new JSONObject(data);
					JSONArray jsonArry = json.getJSONArray("result");
					for (int i = 0; i < jsonArry.length(); i++) {
						en = new Entity();
						JSONObject obj = jsonArry.getJSONObject(i);
						en.setFeedbackID(obj.getString("FeedbackID"));
						en.setGuestName(obj.getString("GuestName"));
						en.setCompanyName(obj.getString("CompanyName"));
						en.setDate(obj.getString("Dateoffeedback"));
						en.setFeedback(obj.getString("Feedbackcontent"));
						en.setAmbience(obj.getString("Ambience"));
						en.setServices(obj.getString("Services"));
						en.setFood(obj.getString("Food"));

						listOfFeedBack.add(en);
					}
					dialog.cancel();
					adapter.notifyDataSetChanged();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/getGuestfeedback");
	}
}
