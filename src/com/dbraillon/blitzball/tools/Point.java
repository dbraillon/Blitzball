package com.dbraillon.blitzball.tools;

public class Point {

	public static double distance(Point a, Point b) {
		
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}
	
	private double x, y;
	
	public Point(double x, double y) {
		
		setX(x);
		setY(y);
	}
	
	public Point getOppositeAgainst(Point center) {
		
		double dx = Math.abs(center.getX() - x);
		double dy = Math.abs(center.getY() - y);
		
		double fx = (center.getX() < x) ? center.getX() - dx : center.getX() + dx;
		double fy = (center.getY() < y) ? center.getY() - dy : center.getY() + dy;
		
		
		return new Point(fx, fy);
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
