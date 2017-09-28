package com.diaz.weatherballoon;

public class Observation {
	public Observation() {
		super();
	}
	private String observatory;
	private long date;
	private Location location;
	private int temperature;
	public String getObservatory() {
		return observatory;
	}
	public void setObservatory(String observatory) {
		this.observatory = observatory;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public Observation(String observatory, long date, Location location, int temperature) {
		super();
		this.observatory = observatory;
		this.date = date;
		this.location = location;
		this.temperature = temperature;
	}
	
	
	
}
