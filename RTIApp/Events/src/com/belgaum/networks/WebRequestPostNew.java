package com.belgaum.networks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class WebRequestPostNew extends AsyncTask<String, Integer, String> {

	ArrayList<NameValuePair> postValues;

	String DEBUG_TAG;

	public WebRequestPostNew(ArrayList<NameValuePair> nameValuePair) {

		postValues = nameValuePair;

	}

	@Override
	protected String doInBackground(String... params) {

		String result = "";

		InputStream ins = null;

		OutputStream oStream = null;

		try {
			URL url = new URL(params[0]);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);

			oStream = urlConnection.getOutputStream();
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(
					oStream, "UTF-8"));
			bWriter.write(getQuery(postValues));
			ins = urlConnection.getInputStream();

			String contentAsString = readIt(ins);

			urlConnection.connect();
			int response = urlConnection.getResponseCode();
			Log.d(DEBUG_TAG, "The response is: " + response);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ins != null) {
					ins.close();
				}
				if (oStream != null) {
					oStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private String readIt(InputStream ins) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
		} finally {
			try {
				ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private String getQuery(ArrayList<NameValuePair> params) {

		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params) {
			try {
				if (first)
					first = false;
				else
					result.append("&");

				result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
				result.append("=");
				result.append(URLEncoder.encode(pair.getName(), "UTF-8"));

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}
}
