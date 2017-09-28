package com.diaz.weatherballoon;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Stats {
	private int minTempCelcius;
	private int maxTempCelcius;
	private double meanTempCelcius;
	private Map<String, AtomicLong> observatoryCounts;
	private long totalDistanceKms;
	
	public int getMinTempCelcius() {
		return minTempCelcius;
	}
	public void setMinTempCelcius(int minTempCelcius) {
		this.minTempCelcius = minTempCelcius;
	}
	public int getMaxTempCelcius() {
		return maxTempCelcius;
	}
	public void setMaxTempCelcius(int maxTempCelcius) {
		this.maxTempCelcius = maxTempCelcius;
	}
	public double getMeanTempCelcius() {
		return meanTempCelcius;
	}
	public void setMeanTempCelcius(double meanTempCelcius) {
		this.meanTempCelcius = meanTempCelcius;
	}
	public Map<String, AtomicLong> getObservatoryCounts() {
		return observatoryCounts;
	}
	public void setObservatoryCounts(Map<String, AtomicLong> observatoryCounts) {
		this.observatoryCounts = observatoryCounts;
	}
	public long getTotalDistanceKms() {
		return totalDistanceKms;
	}
	public void setTotalDistanceKms(long totalDistanceKms) {
		this.totalDistanceKms = totalDistanceKms;
	}
	public void addDistanceToTotalInKms(long totalDistanceKms) {
		this.totalDistanceKms += totalDistanceKms;
	}
}
