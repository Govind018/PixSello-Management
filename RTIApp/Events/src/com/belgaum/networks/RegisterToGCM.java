package com.belgaum.networks;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.belgaum.events.util.Util;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class RegisterToGCM extends AsyncTask<String, Integer, String> {

	IWebRequest webRequest;
	List<NameValuePair> dataTosend;

	Context thisContext;

	public RegisterToGCM(IWebRequest clientWebRequest, Context context) {
		webRequest = clientWebRequest;
		thisContext = context;
	}

	@Override
	protected String doInBackground(String... params) {

		String regId = "";
		GoogleCloudMessaging gcm = GoogleCloudMessaging
				.getInstance(thisContext);
		try {
			regId = gcm.register(Util.REGISTRATION_ID);
			Log.d("RegisterActivity", "registerInBackground - regId: " + regId);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return regId;
	}

	@Override
	protected void onPostExecute(String regId) {
		super.onPostExecute(regId);
		webRequest.onDataArrived(regId);
	}
}
