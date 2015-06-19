package com.pixsello.management;

import android.app.Application;

public class MyApplication extends Application{

	private static String userId;
	
	private static MyApplication singleton;
	
	public static 	MyApplication getInstance(){
		return singleton;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		singleton = this;
	}
	
	public void setUser(String loginId){
		userId = loginId;
	}
	
	public String getUser(){
		return userId;
	}
}
