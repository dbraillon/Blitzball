package com.dbraillon.blitzball.tools;

public class Zone {

	private double diameter;
	private double radius;
	
	public Zone(double radius) {
		
		setDiameter(radius*2);
		setRadius(radius);
	}
	
	public boolean isInZone(Point center, Point point) {
		
		// distance AB
		double d = Point.distance(center, point);
		
		return d <= radius;
	}

	
	// Getters and setters
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}
}
