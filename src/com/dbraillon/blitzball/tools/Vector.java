package com.dbraillon.blitzball.tools;

public class Vector {

	public static double prodScalaire(Vector v1, Vector v2)
	{
		double x = v1.x * v2.x;
		double y = v1.y * v2.y;
		
		return x + y;
	}
	
	private double x, y;
	
	public Vector(Point a, Point b)
	{
		setX(b.getX() - a.getX());
		setY(b.getY() - a.getY());
	}
	
	public Vector(double x, double y)
	{
		setX(x);
		setY(y);
	}

	
	// Getters and setters
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
