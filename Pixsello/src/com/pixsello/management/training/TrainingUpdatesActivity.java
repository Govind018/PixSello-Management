package com.pixsello.management.training;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class TrainingUpdatesActivity extends Activity {

	EditText editTrainingDate, editTrainigTime, editTrainee, editTrainer,
			editOther, editHrs, editMins, editAssement;

	Spinner editTrainingType;

	private String trainingDate;
	private String trainingTime;
	private String traineeName;
	private String trainerName;
	private String trainingType;
	private String other;
	private String trainingHrs;
	private String trainingMins;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training_updates);

		editTrainingDate = (EditText) findViewById(R.id.edit_training_date);
		editTrainigTime = (EditText) findViewById(R.id.edit_training_time);
		editTrainee = (EditText) findViewById(R.id.edit_trainee_name);
		editTrainer = (EditText) findViewById(R.id.edit_trainer);
		editTrainingType = (Spinner) findViewById(R.id.spinner_type);
		editOther = (EditText) findViewById(R.id.edit_other);
		editHrs = (EditText) findViewById(R.id.edit_hrs);
		editMins = (EditText) findViewById(R.id.edit_mins);

	}

	@Override
	protected void onStart() {
		super.onStart();

		editTrainingDate.setText(Uttilities.getDate());
		editTrainigTime.setText(Uttilities.getTime());
	}

	public void doSubmit(View v) {

		trainingDate = editTrainingDate.getText().toString();
		trainingTime = editTrainigTime.getText().toString();
		traineeName = editTrainee.getText().toString();
		trainerName = editTrainer.getText().toString();
		trainingType = editTrainingType.getSelectedItem().toString();
		other = editOther.getText().toString();
		trainingHrs = editHrs.getText().toString();
		trainingMins = editMins.getText().toString();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(5);
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

		WebRequestPost post = new WebRequestPost(new IWebRequest() {

			@Override
			public void onDataArrived(String data) {

				Uttilities.showToast(getApplicationContext(), data);

			}
		}, nameValuePair);
		post.execute(Uttilities.TRAINING_UPDATE_URL);

	}

	public void goBack(View v) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.training_updates, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
