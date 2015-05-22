package com.pixsello.management.training;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.pixsello.management.R;

public class VideoPlayerActivity extends Activity {

	// Declare variables
	ProgressDialog pDialog;
	VideoView videoview;

	// Insert your Video URL
	String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);

		videoview = (VideoView) findViewById(R.id.VideoView);
		// Execute StreamVideo AsyncTask

		// Create a progressbar
		pDialog = new ProgressDialog(VideoPlayerActivity.this);
		// Set progressbar title
		pDialog.setTitle("Video Streaming ");
		// Set progressbar message
		pDialog.setMessage("Buffering...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		// Show progressbar
		pDialog.show();

		try {
			// Start the MediaController
			MediaController mediacontroller = new MediaController(
					VideoPlayerActivity.this);
			mediacontroller.setAnchorView(videoview);
			// Get the URL from String VideoURL
			Uri video = Uri.parse(VideoURL);
			videoview.setMediaController(mediacontroller);
			videoview.setVideoURI(video);

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}

		videoview.requestFocus();
		videoview.setOnPreparedListener(new OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
				pDialog.dismiss();
				videoview.start();
			}
		});
	}
}
