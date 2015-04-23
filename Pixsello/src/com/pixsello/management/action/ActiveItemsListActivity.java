package com.pixsello.management.action;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.action.UpdateActionDialog.OnCompleteListener;
import com.pixsello.management.adapters.ActiveItemsListAdapter;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class ActiveItemsListActivity extends Activity implements OnCompleteListener {

	ArrayList<ActionItem> itemsData;

	ListView list;

	ActiveItemsListAdapter adapter;

	ProgressDialog dailog;

	static TextView lbl;

	static EditText editActionTaken;

	static RelativeLayout updateLayout;

	RelativeLayout layoutError;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_items_list);

		itemsData = new ArrayList<ActionItem>();

		list = (ListView) findViewById(R.id.active_items_list);

		updateLayout = (RelativeLayout) findViewById(R.id.update_layout);

		editActionTaken = (EditText) findViewById(R.id.edit_action_taken);

		lbl = (TextView) findViewById(R.id.lbl_title);

		dailog = new ProgressDialog(ActiveItemsListActivity.this);
		dailog.setMessage("Please Wait..");
		;

		layoutError = (RelativeLayout) findViewById(R.id.layout_error);
		layoutError.setVisibility(View.GONE);

		adapter = new ActiveItemsListAdapter(getApplicationContext(),
				getFragmentManager(), R.layout.active_list_item, itemsData);
		list.setAdapter(adapter);

	}

	@Override
	protected void onStart() {
		super.onStart();

		dailog.show();
		getActiveItemsList();

	}

	public void goBack(View v) {
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Uttilities.showToast(getApplicationContext(), "resume");

	}

	@Override
	protected void onPause() {
		super.onPause();

		// Uttilities.showToast(getApplicationContext(), "pa");
	}

	private void getActiveItemsList() {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID", "property1"));

		WebRequestPost getData = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {

					ActionItem item;

					JSONObject obj = new JSONObject(data);
					if (obj.has("error_message")) {
						dailog.cancel();
						list.setVisibility(View.GONE);
						layoutError.setVisibility(View.VISIBLE);
						adapter.notifyDataSetChanged();
					} else {

						JSONArray jsonArray = obj.getJSONArray("result");
						itemsData.clear();
						for (int i = 0; i < jsonArray.length(); i++) {
							item = new ActionItem();
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							item.setItemID(jsonObj.getString("ID"));
							item.setDate(jsonObj.getString("Date"));
							item.setTime(jsonObj.getString("Time"));
							item.setDispersion(jsonObj.getString("Description"));
							item.setLocation(jsonObj.getString("Where"));
							item.setReported(jsonObj.getString("Reported"));
							item.setResponsibility(jsonObj
									.getString("Responsibility"));
							item.setActionTaken(jsonObj
									.getString("Actiontaken"));

							itemsData.add(item);
						}

						dailog.cancel();
						adapter.notifyDataSetChanged();

						System.out.println(jsonArray);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair);

		getData.execute(Uttilities.ACTION_ACTIVE_ITEMS_URL);
	}

	public static String ID;

	public void showDailog(String id) {

		ID = id;
		updateLayout.setVisibility(View.VISIBLE);
		System.out.println(id + "IDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
	}

	public void updateItem(View v) {

		if (!editActionTaken.getText().toString().isEmpty()) {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
			nameValuePair.add(new BasicNameValuePair("ID", ID));
			nameValuePair.add(new BasicNameValuePair("Actiontaken",
					editActionTaken.getText().toString()));
			nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
					.getPROPERTY_ID()));

			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {

					try {
						JSONObject json = new JSONObject(data);
						Uttilities.showToast(getApplicationContext(),
								json.getString("result"));
						updateLayout.setVisibility(View.GONE);
						editActionTaken.setText("");
						getActiveItemsList();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, nameValuePair);

			post.execute(Uttilities.ACTION_ITEM_UPDATE_URL);
		} else {

		}
	}

	public void closeItem(View v) {
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("ID", ID));
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getPROPERTY_ID()));

		dailog.show();
		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {
					dailog.cancel();
					JSONObject json = new JSONObject(data);
					Uttilities.showToast(getApplicationContext(),
							json.getString("result"));

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePair);

		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/closeActionItem");

	}

	@Override
	public void onComplete(String result) {
		
		getActiveItemsList();
		
	}
}
