package com.belgaum.networks;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.belgaum.events.AppController;

public class WebRequest {

	public static void addNewRequestQueue(final Context context, String url,
			final Map<String, String> values) {

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

//			@Override
//			protected Map<String, String> getParams() throws AuthFailureError {
//				Map<String, String> values = new HashMap<String, String>();
//				values.put("user", "user");
//				values.put("password", "abcd+");
//				return values;
//			}
		};

		AppController.getInstance().addToRequestQueue(request);
	}
}
