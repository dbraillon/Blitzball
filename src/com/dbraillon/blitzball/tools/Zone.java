package com.dbraillon.blitzball.tools;

import com.dbraillon.math.Point;

public class Zone {

	private float diameter;
	private float radius;
	
	public Zone(float radius) {
		
		setDiameter(radius*2);
		setRadius(radius);
	}
	
	public boolean isInZone(Point center, Point point) {
		
		// distance AB
		float d = Point.getDistance(center, point);
		
		return d <= radius;
	}

	
	// Getters and setters
	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(float diameter) {
		this.diameter = diameter;
	}
}
