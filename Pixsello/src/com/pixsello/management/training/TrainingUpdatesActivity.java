package com.pixsello.management.training;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class TrainingUpdatesActivity extends Activity {

	EditText editTrainingDate, editTrainigTime, editTrainee, editTrainer,
			editOther, editAssement;

	Spinner editTrainingType;

	Spinner editHrs, editMins;

	private String trainingDate;
	private String trainingTime;
	private String traineeName;
	private String trainerName;
	private String trainingType;
	private String other;
	private String trainingHrs;
	private String trainingMins;
	private String assessment;

	String[] types;

	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training_updates);

		types = getResources().getStringArray(R.array.training_types);

		editTrainingDate = (EditText) findViewById(R.id.edit_training_date);
		editTrainigTime = (EditText) findViewById(R.id.edit_training_time);
		editTrainee = (EditText) findViewById(R.id.edit_trainee_name);
		editTrainer = (EditText) findViewById(R.id.edit_trainer);
		editTrainingType = (Spinner) findViewById(R.id.spinner_type);
		editOther = (EditText) findViewById(R.id.edit_other);
		editHrs = (Spinner) findViewById(R.id.edit_hrs);
		editMins = (Spinner) findViewById(R.id.edit_mins);
		editAssement = (EditText) findViewById(R.id.stay_from_date);

		editTrainingType
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						String type = types[position];

						if (type.equalsIgnoreCase("Other")) {

							editOther.setVisibility(View.VISIBLE);

						} else {
							editOther.setVisibility(View.INVISIBLE);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

		dialog = new ProgressDialog(TrainingUpdatesActivity.this);
		dialog.setMessage("Please Wait..");
	}

	@Override
	protected void onStart() {
		super.onStart();

		editTrainingDate.setText(Uttilities.getDate());
		editTrainigTime.setText(Uttilities.getTime());
	}
	
	public void showDialog(View v){
		
		Dialog d = new Dialog(TrainingUpdatesActivity.this);
		d.setTitle("How long?");
		d.setContentView(R.layout.dialog_training_update);
		
		Window win = d.getWindow();
		win.setLayout(250, 180);
		d.show();
		
	}

	public void doSubmit(View v) {

		trainingDate = editTrainingDate.getText().toString();
		trainingTime = editTrainigTime.getText().toString();
		traineeName = editTrainee.getText().toString();
		trainerName = editTrainer.getText().toString();
		trainingType = editTrainingType.getSelectedItem().toString();
		other = editOther.getText().toString();
		trainingHrs = editHrs.getSelectedItem().toString();
		trainingMins = editMins.getSelectedItem().toString();
		assessment = editAssement.getText().toString();

		if (traineeName.isEmpty() || trainerName.isEmpty()
				|| assessment.isEmpty()) {

			Uttilities.showToast(getApplicationContext(), "Enter all fields.");
			return;
		}

		if (!(Integer.parseInt(assessment) > 0 && Integer.parseInt(assessment) <= 10)) {

			Uttilities.showToast(getApplicationContext(),
					"Assessment should be out of 10");
			return;
		}
		
		dialog.show();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(5);
		nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities
				.getPROPERTY_ID()));
		nameValuePair.add(new BasicNameValuePair("Date", trainingDate));
		nameValuePair.add(new BasicNameValuePair("Time", trainingTime));
		nameValuePair.add(new BasicNameValuePair("Trainee", traineeName));
		nameValuePair.add(new BasicNameValuePair("Trainer", trainerName));
		nameValuePair.add(new BasicNameValuePair("Type", trainingType));
		nameValuePair.add(new BasicNameValuePair("Other", other));
		nameValuePair.add(new BasicNameValuePair("Timeoftraineehrs",
				trainingHrs));
		nameValuePair.add(new BasicNameValuePair("Timeoftraineemin",
				trainingMins));
		nameValuePair.add(new BasicNameValuePair("Traineeassessment",
				assessment));

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				Uttilities.showToast(getApplicationContext(), data);
				dialog.cancel();
				finish();

			}
		}, nameValuePair);
		post.execute(Uttilities.TRAINING_UPDATE_URL);
	}

	public void goBack(View v) {
		finish();
	}
}
