package com.belgaum.networks;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.support.v4.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.belgaum.events.AppController;

public class WebRequest {

	public static void addNewRequestQueue(final Fragment context, String url) {

		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject json) {

						((NetWorkLayer) context).parseResponse(json);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError err) {

						((NetWorkLayer) context).showErrorMessage(err
								.getMessage());
					}
				})

		{

			// @Override
			// protected Map<String, String> getParams() throws AuthFailureError
			// {
			// Map<String, String> values = new HashMap<String, String>();
			// values.put("user", "user");
			// values.put("password", "abcd+");
			// return values;
			// }
		};

		AppController.getInstance().addToRequestQueue(request);
	}

	public static void addNewRequestQueue(final Fragment context, String url,
			final String searchByKey, final String searchBy) {

		Map<String, String> values = new HashMap<String, String>();
		values.put("searchby", searchByKey);
		values.put("keyword", searchBy);

		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				new JSONObject(values), new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject json) {

						((NetWorkLayer) context).parseResponse(json);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError err) {

						((NetWorkLayer) context).showErrorMessage(err
								.getMessage());
					}
				})

		{
//			@Override
//			protected Map<String, String> getParams() throws AuthFailureError {
//				Map<String, String> values = new HashMap<String, String>();
//				values.put("searchby", searchByKey);
//				values.put("keyword", searchBy);
//				return values;
//			}
		};

		AppController.getInstance().addToRequestQueue(request);
	}
}
