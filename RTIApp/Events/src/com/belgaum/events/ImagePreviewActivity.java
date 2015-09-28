package com.belgaum.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ImagePreviewActivity extends Activity {

	
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	RelativeLayout imageArea;
	ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_preview);
		
		Intent in = getIntent();
		imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView)findViewById(R.id.image);
		thumbNail.setImageUrl(in.getStringExtra("image"), imageLoader);

		TextView imageTitle = (TextView) findViewById(R.id.event_title);
		imageTitle.setText(in.getStringExtra("title"));
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
