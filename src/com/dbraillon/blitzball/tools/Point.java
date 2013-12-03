package com.dbraillon.blitzball.tools;

public class Point extends com.dbraillon.dbgraphics.Point {

	public static float distance(Point a, Point b) {
		
		return (float) Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}
	
	
	public Point(float x, float y) {
		super(x, y);
	}

	public Point(double x, double y) {
		super((float)x, (float)y);
	}


	public Point getOppositeAgainst(Point center) {
		
		float dx = Math.abs(center.getX() - getX());
		float dy = Math.abs(center.getY() - getY());
		
		float fx = (center.getX() < getX()) ? center.getX() - dx : center.getX() + dx;
		float fy = (center.getY() < getY()) ? center.getY() - dy : center.getY() + dy;
		
		
		return new Point(fx, fy);
	}
}
