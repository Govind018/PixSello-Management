package com.belgaum.networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class WebRequestPost extends AsyncTask<String, Integer, String>{
	
	IWebRequest webRequest;
	List<NameValuePair> dataTosend;
	ProgressDialog dialog;
	
	public WebRequestPost(IWebRequest clientWebRequest,List<NameValuePair> nameValuePairs,Context context,String message){
		webRequest = clientWebRequest;
		dataTosend = nameValuePairs;
		dialog = new ProgressDialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage(message);
		dialog.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		String result = "";
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient(httpParams);

		// Creating HTTP Post
		HttpPost httpPost = new HttpPost(params[0]);
		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(dataTosend));

			// Making HTTP Request
 			HttpResponse response = httpClient.execute(httpPost);
 			
			String json_obj = convertStreamToString(response.getEntity()
					.getContent());
                       
			System.out.println(json_obj + "JSON");
			
			try {
				JSONObject json = new JSONObject(json_obj);
				result = json.toString();
				dialog.cancel();
			} catch (JSONException e) {
				e.printStackTrace();
				dialog.cancel();
				result = "Invalid Data!";
			}

			// writing response to log
			Log.d("Http Response:", response.toString());                  
			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			dialog.cancel();
			result = "NetWork Error..!";
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			dialog.cancel();
			result = "NetWork Error..!";
		} catch (IOException e) {
			e.printStackTrace();
			dialog.cancel();
			result = "NetWork Error..!";
		}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		webRequest.onDataArrived(result);
	}
	
	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
