package com.belgaum.networks;

import org.json.JSONObject;

public interface NetWorkLayer {

	public void parseResponse(JSONObject json);
	
	public void showErrorMessage(String message);
	
}
