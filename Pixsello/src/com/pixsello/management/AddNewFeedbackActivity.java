package com.pixsello.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
		
		
		ratingAmbience.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				
				ratingValAmb = String.valueOf(rating);
				
			}
		});
		
		ratingServices.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				
				ratingValServices = String.valueOf(rating);
				
			}
		});

		
		ratingFood.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				
				ratingValFood = String.valueOf(rating);
				
			}
		});
		
		dialog = new ProgressDialog(AddNewFeedbackActivity.this);
		dialog.setMessage("Please Wait..");
	}
	
	public void doSubmit(View v){

		guestName = editGuestName.getText().toString();
		guestCompany = editCompany.getText().toString();
		date = editDate.getText().toString();
		feedback = editFeedback.getText().toString();
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.getPROPERTY_ID()));
		nameValuePair.add(new BasicNameValuePair("GuestName", guestName));
		nameValuePair.add(new BasicNameValuePair("CompanyName", guestCompany));
		nameValuePair.add(new BasicNameValuePair("Dateoffeedback", date));
		nameValuePair.add(new BasicNameValuePair("Feedbackcontent", feedback));
		nameValuePair.add(new BasicNameValuePair("Ambience", ratingValAmb));
		nameValuePair.add(new BasicNameValuePair("Services", ratingValServices));
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
		
		post.execute("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addGuestFeedback");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		editDate.setText(Uttilities.getDate());
	}
	
	public void goBack(View v){
		
		finish();
	}
}
