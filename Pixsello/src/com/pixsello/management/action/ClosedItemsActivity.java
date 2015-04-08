package com.pixsello.management.action;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.pixsello.management.R;
import com.pixsello.management.adapters.ActiveItemsListAdapter;
import com.pixsello.management.adapters.ClosedItemsListAdapter;
import com.pixsello.management.connectivity.GetDataFromServer;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;

public class ClosedItemsActivity extends Activity {
	
	ListView listOfClosedItems;

	ClosedItemsListAdapter adapter;
	
	ArrayList<Entity> items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_closed_items_list);
		
		listOfClosedItems = (ListView) findViewById(R.id.closed_items_list);
		items = new ArrayList<Entity>();
		
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		getDataFromServer();
	}

	private void getDataFromServer() {

		GetDataFromServer getData = new GetDataFromServer(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				try {
					Entity item;

					JSONObject obj = new JSONObject(data);
					JSONArray jsonArray = obj.getJSONArray("result");

					items.clear();
					for (int i = 0; i < jsonArray.length(); i++) {
						item = new Entity();
						JSONObject jsonObj = jsonArray.getJSONObject(i);
						item.setItemID(jsonObj.getString("ID"));
						item.setDate(jsonObj.getString("Date"));
						item.setTime(jsonObj.getString("Time"));
						item.setDescription(jsonObj.getString("Description"));
						item.setLocation(jsonObj.getString("Where"));
						item.setReported(jsonObj.getString("Reported"));
						item.setResponsibility(jsonObj
								.getString("Responsibility"));
						item.setActionTaken(jsonObj.getString("Actiontaken"));

						items.add(item);
					}

					adapter = new ClosedItemsListAdapter(getApplicationContext(), R.layout.closed_list_item, items);
					listOfClosedItems.setAdapter(adapter);

					System.out.println(jsonArray);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		getData.execute(Uttilities.CLOSED_ITEMS_LIST_URL);
		
	}

	public void goBack(View v) {
		finish();
	}
}
