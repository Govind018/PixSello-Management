package com.pixsello.management.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.pixsello.management.R;
import com.pixsello.management.adapters.ClosedItemsListAdapter;
import com.pixsello.management.connectivity.GetDataFromServer;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class ClosedItemsActivity extends Activity {

	ListView listOfClosedItems;

	ClosedItemsListAdapter adapter;

	ArrayList<Entity> items;

	RelativeLayout layout;

	Button btnSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_closed_items_list);

		listOfClosedItems = (ListView) findViewById(R.id.closed_items_list);
		btnSearch = (Button) findViewById(R.id.btn_search);
		layout = (RelativeLayout) findViewById(R.id.layout_search);
		items = new ArrayList<Entity>();

	}

	public void showSearchOptions(View v) {

		layout.setVisibility(View.VISIBLE);
		btnSearch.setVisibility(View.INVISIBLE);

	}

	public void showAllData(View v) {

		layout.setVisibility(View.GONE);
		btnSearch.setVisibility(View.VISIBLE);

	}

	@Override
	protected void onStart() {
		super.onStart();

		getDataFromServer();
	}

	private void getDataFromServer() {
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("PropertyID",
				Uttilities.PROPERTY_ID));


		WebRequestPost getData = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {
					Entity item;

					JSONObject obj = new JSONObject(data);

					if (obj.has("error_message")) {
						Uttilities.showToast(getApplicationContext(),
								obj.getString("error_message"));
					} else {

						JSONArray jsonArray = obj.getJSONArray("result");

						items.clear();
						for (int i = 0; i < jsonArray.length(); i++) {
							item = new Entity();
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							item.setItemID(jsonObj.getString("ID"));
							item.setDate(jsonObj.getString("Date"));
							item.setTime(jsonObj.getString("Time"));
							item.setDescription(jsonObj
									.getString("Description"));
							item.setLocation(jsonObj.getString("Where"));
							item.setReported(jsonObj.getString("Reported"));
							item.setResponsibility(jsonObj
									.getString("Responsibility"));
							item.setActionTaken(jsonObj
									.getString("Actiontaken"));

							items.add(item);
						}

						adapter = new ClosedItemsListAdapter(
								getApplicationContext(),
								R.layout.closed_list_item, items);
						listOfClosedItems.setAdapter(adapter);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		},nameValuePair);
		getData.execute(Uttilities.CLOSED_ITEMS_LIST_URL);
	}

	public void goBack(View v) {
		finish();
	}
}
