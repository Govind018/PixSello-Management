package com.pixsello.management.connectivity;

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
import android.os.AsyncTask;
import android.util.Log;

public class WebRequestPost extends AsyncTask<String, Integer, String> {
	private IWebRequest webReq;
	List<NameValuePair> nameValuePair;

	ProgressDialog dialog;

	public WebRequestPost(IWebRequest web, List<NameValuePair> nameValuePair) {

		webReq = web;
		this.nameValuePair = nameValuePair;
		
	}

	@Override
	protected String doInBackground(String... params) {

		// dialog.show();

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
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

			// Making HTTP Request
 			HttpResponse response = httpClient.execute(httpPost);
 			
			String json_obj = convertStreamToString(response.getEntity()
					.getContent());
                       
			try {
				JSONObject json = new JSONObject(json_obj);
				result = json.has("status") ? json.getString("status"): json_obj;
			} catch (JSONException e) {
				e.printStackTrace();
				result = "Invalid Data!";
			}

			// writing response to log
			Log.d("Http Response:", response.toString());                  
			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = "NetWork Error..!";
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "NetWork Error..!";
		} catch (IOException e) {
			e.printStackTrace();
			result = "NetWork Error..!";
		}

		// url = new
		// URL("http://pixsello.in/qualitymaintenanceapp/index.php/webapp/addRecord");
		// HttpURLConnection hurl = (HttpURLConnection) url.openConnection();
		// hurl.setRequestProperty("Content-Type", "application/json");
		// hurl.setRequestProperty("Accept", "application/json");
		// hurl.setRequestMethod("POST");
		//
		// OutputStreamWriter osw = new
		// OutputStreamWriter(hurl.getOutputStream());
		// osw.write(payload);
		// osw.flush();
		// osw.close();
		//
		// if(hurl.getErrorStream() != null){
		//
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(hurl.getErrorStream()));
		//
		// result = reader.readLine();
		// reader.close();
		//
		// }else{
		// result = "";
		// }

		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		// dialog.cancel();
		webReq.onDataArrived(result);
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
