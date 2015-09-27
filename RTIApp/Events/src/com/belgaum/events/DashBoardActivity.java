package com.belgaum.events;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.belgaum.events.util.Util;
import com.belgaum.fragments.AimObjectivesFragment;
import com.belgaum.fragments.AreaBoardFragment;
import com.belgaum.fragments.EventsFragment;
import com.belgaum.fragments.NationalBoardFragment;
import com.belgaum.fragments.Notifications;
import com.belgaum.fragments.SearchFragment;
import com.belgaum.networks.IWebRequest;
import com.belgaum.networks.RegisterToGCM;
import com.belgaum.networks.WebRequestPost;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class DashBoardActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */

	GoogleCloudMessaging gcm;
	private String regId;

	private String senderId;

	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_board);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getString(R.string.title_section1);
     
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		// register GCM once sign up is success
//		registerGCM();
		
		if(Util.getRegId(DashBoardActivity.this).isEmpty()){
//			Util.showToast(getApplicationContext(), "REgister");
			registerGCM();
		}
	}

	private void registerGCM() {

		RegisterToGCM gcmRegister = new RegisterToGCM(new IWebRequest() {

			@Override
			public void onDataArrived(String regId) {

//				Util.showToast(getApplicationContext(), "Register ID " + regId);
				Util.storeRegistrationId(regId, DashBoardActivity.this);

				sendRegIdToServer(regId, Util.getUserId(DashBoardActivity.this));

			}
		}, DashBoardActivity.this);

		gcmRegister.execute(null, null, null);

		// Util.showToast(getApplicationContext(), message);
	}

	private void sendRegIdToServer(String regId, String userId) {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("userid", userId));
		nameValuePairs.add(new BasicNameValuePair("gcmid", regId));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override                      
			public void onDataArrived(String data) {

				try {
					JSONObject json = new JSONObject(data);

					String loginStatus = json.getString("error");
//					Util.showToast(getApplicationContext(), loginStatus);
					if (loginStatus.equalsIgnoreCase("true")) {
						Util.showToast(getApplicationContext(),
								"Something went wrong..!");
					} 
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, nameValuePairs, DashBoardActivity.this, "Please Wait.");
		post.execute(Util.GCM_URL);
	}

	private void registerInBackground() {

		RegisterToGCM gcmRegister = new RegisterToGCM(new IWebRequest() {

			@Override
			public void onDataArrived(String regId) {

				Util.showToast(getApplicationContext(), "Register ID " + regId);
				Util.storeRegistrationId(regId, DashBoardActivity.this);

			}
		}, DashBoardActivity.this);

		gcmRegister.execute(null, null, null);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments

		displayView(position);
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 0:
			mTitle = getString(R.string.title_section1);
			break;
		case 1:
			mTitle = getString(R.string.title_section2);
			break;
		case 2:
			mTitle = getString(R.string.title_section3);
			break;

		case 3:
			mTitle = getString(R.string.title_section4);
			break;
		case 4:
			mTitle = getString(R.string.title_section5);
			break;
		case 5:
			mTitle = getString(R.string.title_section6);
			break;
		}
	}

	private void displayView(int position) {

		Fragment fragment = null;

		switch (position) {
		case 0:

			fragment = new SearchFragment();
			mTitle = getString(R.string.title_section1);
			restoreActionBar();
			break;

		case 1:

			fragment = new AimObjectivesFragment();
			mTitle = getString(R.string.title_section2);
			restoreActionBar();
			break;

		case 2:
			fragment = new AreaBoardFragment();
			mTitle = getString(R.string.title_section3);
			restoreActionBar();
			break;

		case 3:
			fragment = new NationalBoardFragment();
			mTitle = getString(R.string.title_section4);
			restoreActionBar();
			break;

		case 4:
			fragment = new EventsFragment();
			mTitle = getString(R.string.title_section4);
			mTitle = getString(R.string.title_section5);
			restoreActionBar();
			break;

		case 5:
			fragment = new Notifications();
			mTitle = getString(R.string.title_section6);
			restoreActionBar();
			break;

		case 6:
			restoreActionBar();
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(Util.ADMIN_URL));
			startActivity(intent);

			break;

		default:
			break;
		}

		if (fragment != null) {

			FragmentManager fManager = getSupportFragmentManager();
			fManager.beginTransaction().replace(R.id.container, fragment)
					.commit();
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { if
	 * (!mNavigationDrawerFragment.isDrawerOpen()) { // Only show items in the
	 * action bar relevant to this screen // if the drawer is not showing.
	 * Otherwise, let the drawer // decide what to show in the action bar.
	 * getMenuInflater().inflate(R.menu.dash_board, menu); restoreActionBar();
	 * return true; } return super.onCreateOptionsMenu(menu); }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
	 * R.id.action_settings) { return true; } return
	 * super.onOptionsItemSelected(item); }
	 */
	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*
	 * public static class PlaceholderFragment extends Fragment {
	 *//**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	/*
	 * private static final String ARG_SECTION_NUMBER = "section_number";
	 *//**
	 * Returns a new instance of this fragment for the given section number.
	 */
	/*
	 * public static PlaceholderFragment newInstance(int sectionNumber) {
	 * PlaceholderFragment fragment = new PlaceholderFragment(); Bundle args =
	 * new Bundle(); args.putInt(ARG_SECTION_NUMBER, sectionNumber);
	 * fragment.setArguments(args); return fragment; }
	 * 
	 * public PlaceholderFragment() { }
	 * 
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { View rootView =
	 * inflater.inflate(R.layout.fragment_dash_board, container, false); return
	 * rootView; }
	 * 
	 * @Override public void onAttach(Activity activity) {
	 * super.onAttach(activity); ((DashBoardActivity)
	 * activity).onSectionAttached(getArguments() .getInt(ARG_SECTION_NUMBER));
	 * } }
	 */
}
