package com.diaz.weatherballoon;

public class Location {
	private int x;
	private int y;

	public Location(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int calculateDistanceFrom(Location loc) {
		int xDif = Math.abs(loc.getX() - x);
		int yDif = Math.abs(loc.getY() - y);
		return (int)Math.hypot(xDif, yDif);
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
