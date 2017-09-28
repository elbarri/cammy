package com.diaz.weatherballoon;

public class Observatory {
	private String name;
	private TemperatureScale temperature;
	private DistanceScale distance;
	
	public Observatory(String name, TemperatureScale temperature, DistanceScale distance) {
		super();
		this.name = name;
		this.temperature = temperature;
		this.distance = distance;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TemperatureScale getTemperature() {
		return temperature;
	}
	public void setTemperature(TemperatureScale temperature) {
		this.temperature = temperature;
	}
	public DistanceScale getDistance() {
		return distance;
	}
	public void setDistance(DistanceScale distance) {
		this.distance = distance;
	}
	
}
