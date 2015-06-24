package com.belgaum.networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;

import android.os.AsyncTask;

public class WebRequestGet extends AsyncTask<String, Integer, String>{

	IWebRequest webRequest;
	
	List<NameValuePair> dataTosend;
	WebRequestGet(IWebRequest clientWebRequest,List<NameValuePair> nameValuePairs){
		webRequest = clientWebRequest;
		dataTosend = nameValuePairs;
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		String result = "";
		
		try {
			URL url = new URL(params[0]);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestMethod("GET");
			int responseCode = urlConnection.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				System.out.println(response.toString());
			} else {
				System.out.println("GET request not worked");
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		webRequest.onDataArrived(result);
	}
}
