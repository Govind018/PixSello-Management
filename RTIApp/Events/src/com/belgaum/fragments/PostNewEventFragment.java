package com.belgaum.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.belgaum.events.R;

public class PostNewEventFragment extends Fragment {

	EditText editEventName;

	EditText editEventTime;

	EditText editEventDate;

	private DatePickerDialog datePickerDialog;
	
	private TimePickerDialog timePickerDialog;

	private SimpleDateFormat dateFormatter;

	private int pHour;
	private int pMinute;

	static final int TIME_DIALOG_ID = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dateFormatter = new SimpleDateFormat("dd-MM-yyy", Locale.US);
		Calendar newCalendar = Calendar.getInstance();

		datePickerDialog = new DatePickerDialog(getActivity(),
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {

						Calendar newDate = Calendar.getInstance();
						newDate.set(year, monthOfYear, dayOfMonth);
						editEventDate.setText(dateFormatter.format(newDate
								.getTime()));

					}
				}, newCalendar.get(Calendar.YEAR),
				newCalendar.get(Calendar.MONTH),
				newCalendar.get(Calendar.DAY_OF_MONTH));
		
		timePickerDialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				
				final Calendar cal = Calendar.getInstance();
		        pHour = cal.get(Calendar.HOUR_OF_DAY);
		        pMinute = cal.get(Calendar.MINUTE);
		        
		        updateDisplay();
			}
		}, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_post_events,
				container, false);

		editEventName = (EditText) convertView
				.findViewById(R.id.edit_post_event);

		editEventTime = (EditText) convertView.findViewById(R.id.edit_time);
		editEventTime.setOnClickListener(timePickListener);

		editEventDate = (EditText) convertView.findViewById(R.id.edit_date);
		editEventDate.setOnClickListener(datePickListener);

		return convertView;
	}

	OnClickListener timePickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			timePickerDialog.show();

		}
	};

	OnClickListener datePickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			datePickerDialog.show();

		}
	};

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			pHour = hourOfDay;
			pMinute = minute;
			updateDisplay();
		}
	};

	private void updateDisplay() {
		editEventTime.setText(new StringBuilder().append(pad(pHour))
				.append(":").append(pad(pMinute)));
	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
}
