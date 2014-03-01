package com.dbraillon.math;

public class Point {

	// Coordinate
	private float x, y;
	
	public static float getDistance(Point a, Point b)
	{
		return (float) Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + 
				   				 Math.pow(b.getY() - a.getY(), 2));
	}
	
	public static Point getOppositeAgainst(Point toOpposite, Point center)
	{
		return new Vector(toOpposite, center).progress(center);
	}
	
	public Point() { }
	public Point(float x, float y)
	{
		this.x = x;
		this.setY(y);
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
}
