package com.belgaum.events;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class DetailsActivity extends ActionBarActivity {

	private String email;

	private String mobile;
	
	private String name;
   
	@Override   
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
    
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();

		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		TextView textName = (TextView) findViewById(R.id.text_name);
		TextView textArea = (TextView) findViewById(R.id.text_area);
		TextView textPosition = (TextView) findViewById(R.id.text_designation);
		TextView textEmail = (TextView) findViewById(R.id.text_email);
		TextView textNumber = (TextView) findViewById(R.id.text_phone);

		Intent intent = getIntent();
		textName.setText(intent.getStringExtra("name"));
		textEmail.setText(intent.getStringExtra("email"));
		textNumber.setText(intent.getStringExtra("phone"));
		textPosition.setText(intent.getStringExtra("post"));
		textArea.setText(intent.getStringExtra("table"));
		
		name = textName.getText().toString();
		email = textEmail.getText().toString();
		mobile = textNumber.getText().toString();

		NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.image);
		thumbNail.setImageUrl(intent.getStringExtra("image"), imageLoader);

	}

	public void doCall(View v) {

		if (!mobile.isEmpty()) {

			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + mobile));
			startActivity(callIntent);
		}
	}

	public void doMessage(View v) {

		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("address", mobile);
		startActivity(smsIntent);

	}
	
	public void doShare(View v){
		StringBuffer buffer = new StringBuffer();
		buffer.append(name).append("\n").append(email).append("\n").append(mobile);
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, buffer.toString());
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, "Choose"));
	}

	public void doSendEmail(View v) {
		Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
		startActivity(i);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();

			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
