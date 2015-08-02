package com.pixsello.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class AddNewFeedbackActivity extends Activity {

	EditText editGuestName;
	EditText editCompany;
	EditText editDate;
	EditText editFeedback;

	String guestName;
	String guestCompany;
	String date;
	String feedback;

	ProgressDialog dialog;

	RatingBar ratingAmbience;

	RatingBar ratingServices;

	RatingBar ratingFood;

	String ratingValAmb;

	String ratingValServices;

	String ratingValFood;

	boolean photoTaken;
	
	ImageView image;

	static final int DATE_DIALOG_ID = 999;

	int REQUEST_IMAGE_CAPTURE = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_feedback);

		editGuestName = (EditText) findViewById(R.id.edit_guest_name);
		editCompany = (EditText) findViewById(R.id.edit_company);
		editDate = (EditText) findViewById(R.id.edit_date);
		editFeedback = (EditText) findViewById(R.id.edit_feedback);
		ratingAmbience = (RatingBar) findViewById(R.id.rating_ambience);
		ratingServices = (RatingBar) findViewById(R.id.rating_services);
		ratingFood = (RatingBar) findViewById(R.id.rating_food);
		image = (ImageView) findViewById(R.id.image);

		ratingAmbience
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {

						ratingValAmb = String.valueOf(rating);

					}
				});

		ratingServices
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {

						ratingValServices = String.valueOf(rating);

					}
				});

		ratingFood
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {

						ratingValFood = String.valueOf(rating);

					}
				});

		dialog = new ProgressDialog(AddNewFeedbackActivity.this);
		dialog.setMessage("Please Wait..");
	}

	public void doSubmit(View v) {

		guestName = editGuestName.getText().toString();
		guestCompany = editCompany.getText().toString();
		date = editDate.getText().toString();
		feedback = editFeedback.getText().toString();

		if (!photoTaken) {
			Uttilities.showToast(getApplicationContext(), "Please Take Photo.");
			return;
		}

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getUserLoginId(getApplicationContext())));
		nameValuePair.add(new BasicNameValuePair("GuestName", guestName));
		nameValuePair.add(new BasicNameValuePair("CompanyName", guestCompany));
		nameValuePair.add(new BasicNameValuePair("Dateoffeedback", date));
		nameValuePair.add(new BasicNameValuePair("Feedbackcontent", feedback));
		nameValuePair.add(new BasicNameValuePair("Ambience", ratingValAmb));
		nameValuePair
				.add(new BasicNameValuePair("Services", ratingValServices));
		nameValuePair.add(new BasicNameValuePair("Food", ratingValFood));

		dialog.show();
		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				dialog.cancel();
				Uttilities.showToast(getApplicationContext(), data);
				finish();

			}
		}, nameValuePair);

		post.execute(Uttilities.FEEDBACK_ADD_NEW);
	}

	@Override
	protected void onStart() {
		super.onStart();
		editDate.setText(Uttilities.getDate());
	}

	public void goBack(View v) {

		finish();
	}

	public void capturePhoto(View v) {

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {

			photoTaken = true;
			Bitmap map = (Bitmap) data.getExtras().get("data");
			image.setImageBitmap(map);

		}
	}

}
