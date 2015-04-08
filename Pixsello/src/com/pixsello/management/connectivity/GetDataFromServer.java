package com.pixsello.management.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

public class GetDataFromServer extends AsyncTask<String, Integer, String> {

	private IWebRequest webReq;
	
	public GetDataFromServer(IWebRequest web) {
		
		webReq = web;
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		String result = "";
		
		try {
			URL url = new URL(params[0]);
			URLConnection conn = url.openConnection ();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;				
			while ((line = rd.readLine()) != null)
			{
				sb.append(line);
			}
			result=sb.toString();
			rd.close();	

           
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		webReq.onDataArrived(result);
	
	}
}
