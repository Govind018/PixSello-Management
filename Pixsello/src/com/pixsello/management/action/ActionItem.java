package com.pixsello.management.action;

public class ActionItem {
	
	private String itemID;
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	private String date;
	private String time;
	private String dispersion;
	private String location;
	private String reported;
	private String responsibility;
	private String actionTaken;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDispersion() {
		return dispersion;
	}
	public void setDispersion(String dispersion) {
		this.dispersion = dispersion;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getReported() {
		return reported;
	}
	public void setReported(String reported) {
		this.reported = reported;
	}
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	public String getActionTaken() {
		return actionTaken;
	}
	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
}
