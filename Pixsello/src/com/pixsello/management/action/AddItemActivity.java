package com.pixsello.management.action;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class AddItemActivity extends Activity {

	EditText editDate, editTime, editDesc, editLocation, editStaff, editRespo;

	String date;
	String time;
	String desc;
	String location;
	String staffName;
	String respo;
	
	int REQUEST_IMAGE_CAPTURE = 101;
	
	ImageView image;
	
	boolean photoTaken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);

		editDate = (EditText) findViewById(R.id.edit_item_date);
		editTime = (EditText) findViewById(R.id.edit_item_time);
		editDesc = (EditText) findViewById(R.id.edit_description);
		editLocation = (EditText) findViewById(R.id.edit_location);
		editStaff = (EditText) findViewById(R.id.edit_staff_name);
		editRespo = (EditText) findViewById(R.id.edit_responsibility);
		image = (ImageView) findViewById(R.id.image);
		
	}
	
	public void capturePhoto(View v) {

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		editDate.setText(Uttilities.getDate());
		editTime.setText(Uttilities.getTime());
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

	public void doSubmitItem(View v) {

		date = editDate.getText().toString();
		time = editTime.getText().toString();
		desc = editDesc.getText().toString();
		location = editLocation.getText().toString();
		staffName = editStaff.getText().toString();
		respo = editRespo.getText().toString();
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
		byte [] byte_arr = stream.toByteArray();
		String image_str = Base64.encodeToString(byte_arr, 0);

		if (desc.isEmpty() || location.isEmpty() || staffName.isEmpty()
				|| respo.isEmpty()) {

			Uttilities.showToast(getApplicationContext(),
					"Please fill all the fields.");

		} else {
			
			if(!photoTaken){
				Uttilities.showToast(getApplicationContext(), "Please Take Photo");
				return;
			}

			final ProgressDialog dialog = new ProgressDialog(AddItemActivity.this);
			dialog.setMessage("Please Wait..");
			dialog.show();
			
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(6);
			nameValuePair.add(new BasicNameValuePair("Date", date));
			nameValuePair.add(new BasicNameValuePair("Time", time));
			nameValuePair.add(new BasicNameValuePair("Description", desc));
			nameValuePair.add(new BasicNameValuePair("Where", location));
			nameValuePair.add(new BasicNameValuePair("Reported", staffName));
			nameValuePair.add(new BasicNameValuePair("Responsibility", respo));
			nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.getPROPERTY_ID()));
			nameValuePair.add(new BasicNameValuePair("Photo", image_str));

			WebRequestPost postData = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {
					
					Uttilities.showToast(getApplicationContext(), data);
					dialog.cancel();
					finish();

				}
			}, nameValuePair);

			postData.execute(Uttilities.ACTION_REQUIRED_URL);
		}
	}

	public void goBack(View v) {
		finish();
	}
}
