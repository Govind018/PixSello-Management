package com.pixsello.management.guest;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class MakeInEntryActivity extends Activity {

	EditText editDate, editTime, editGuestName, editCompany, editVisitingGuest,
			editInTIme, editRoomNum;

	String date;
	String time;
	String guestName;
	String companyName;
	String visitingGuest;
	String inTime;
	String roomNum;

	CheckBox chkMale, chkFemale;

	boolean genderMale, genderFemale;

	int REQUEST_IMAGE_CAPTURE = 101;

	ImageView image;

	boolean photoTaken;
	
	boolean isGenderSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_in_entry);

		editDate = (EditText) findViewById(R.id.edit_date);
		editTime = (EditText) findViewById(R.id.edit_time);
		editGuestName = (EditText) findViewById(R.id.edit_guest_name);
		editCompany = (EditText) findViewById(R.id.edit_company);
		editVisitingGuest = (EditText) findViewById(R.id.edit_visiting_guest);
		chkMale = (CheckBox) findViewById(R.id.chk_male);
		chkFemale = (CheckBox) findViewById(R.id.chk_female);
		editInTIme = (EditText) findViewById(R.id.edit_in_time);
		editRoomNum = (EditText) findViewById(R.id.edit_visiting_room);

		image = (ImageView) findViewById(R.id.image);

		chkMale.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					isGenderSelected = isChecked;
					chkFemale.setChecked(false);
					genderFemale = false;
					genderMale = isChecked;
				}

			}
		});

		chkFemale.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					isGenderSelected = isChecked;
					chkMale.setChecked(false);
					genderMale = false;
					genderFemale = isChecked;
				}

			}
		});
	}

	public void capturePhoto(View v) {

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	public void doSubmit(View v) {

		date = editDate.getText().toString();
		time = editTime.getText().toString();
		guestName = editGuestName.getText().toString();
		companyName = editCompany.getText().toString();
		visitingGuest = editVisitingGuest.getText().toString();
		inTime = editInTIme.getText().toString();
		roomNum = editRoomNum.getText().toString();

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
		byte[] byte_arr = stream.toByteArray();
		String image_str = Base64.encodeToString(byte_arr, 0);

		if (guestName.isEmpty() || companyName.isEmpty()
				|| visitingGuest.isEmpty()) {

			Uttilities.showToast(getApplicationContext(),
					"Please fill all the fields.");

		} else {

			if (!photoTaken) {

				Uttilities.showToast(getApplicationContext(),
						"Please Take Photo.");
				return;
			}

			if (!isGenderSelected) {
				Uttilities.showToast(getApplicationContext(),
						"Please select gender.");
				return;
			}

			if(!Uttilities.validate(guestName) || !Uttilities.validate(companyName) || !Uttilities.validate(visitingGuest)){
				Uttilities.showToast(getApplicationContext(),
						"Please select gender.");
				return;
			}
			
			
			final ProgressDialog dialog = new ProgressDialog(
					MakeInEntryActivity.this);
			dialog.setMessage("Please Wait..");
			dialog.show();

			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(9);
			nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.getUserLoginId(getApplicationContext())));
			nameValuePair.add(new BasicNameValuePair("Date", date));
			nameValuePair.add(new BasicNameValuePair("Time", time));
			nameValuePair.add(new BasicNameValuePair("GuestName", guestName));
			nameValuePair.add(new BasicNameValuePair("Company", companyName));
			nameValuePair.add(new BasicNameValuePair("Visitor", visitingGuest));
			nameValuePair.add(new BasicNameValuePair("InTime", time));
			nameValuePair.add(new BasicNameValuePair("Photo", image_str));
			nameValuePair.add(new BasicNameValuePair("Roomno", roomNum));
			nameValuePair.add(new BasicNameValuePair("Photo", image_str));

			if (genderMale) {
				nameValuePair.add(new BasicNameValuePair("Gender", "male"));
			} else {

				nameValuePair.add(new BasicNameValuePair("Gender", "female"));

			}

			WebRequestPost postData = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {

					dialog.cancel();
					Uttilities.showToast(getApplicationContext(), data);
					finish();

				}
			}, nameValuePair);

			postData.execute(Uttilities.GUEST_MAKE_IN_ENTRY);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		editDate.setText(Uttilities.getDate());
		editTime.setText(Uttilities.getTime());
	}

	public void goBack(View v) {
		finish();
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
