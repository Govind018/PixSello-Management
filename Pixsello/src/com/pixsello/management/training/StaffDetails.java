package com.pixsello.management.training;

public class StaffDetails {

	private String trainerName;
	private String traineeName;
	private String date;
	private String time;
	private String type;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	private String other;
	
	public String getTrainerName() {
		return trainerName;
	}
	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}
	public String getTraineeName() {
		return traineeName;
	}
	public void setTraineeName(String traineeName) {
		this.traineeName = traineeName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String dateAndTime) {
		this.date = dateAndTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	
}
