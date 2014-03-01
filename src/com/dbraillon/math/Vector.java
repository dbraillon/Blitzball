package com.dbraillon.math;

public class Vector {

	private float xDistance, yDistance;
	
	public Vector() { }
	public Vector(Point A, Point B)
	{
		xDistance = B.getX() - A.getX();
		yDistance = B.getY() - A.getY();
	}
	
	public Point progress(Point norm)
	{
		return new Point(norm.getX() + xDistance, norm.getY() + yDistance);
	}
	
	public Direction toDirection()
	{
		float xAbs = Math.abs(xDistance);
		float yAbs = Math.abs(yDistance);
		
		if(xAbs >= yAbs)
			if(xDistance < 0)
				return Direction.LEFT;
			else if(xDistance > 0)
				return Direction.RIGHT;
			else
				return Direction.NULL;
		else
			if(yDistance < 0)
				return Direction.BOTTOM;
			else if(yDistance > 0)
				return Direction.TOP;
			else
				return Direction.NULL;
	}
	
	public float getxDistance() {
		return xDistance;
	}
	public void setxDistance(float xDistance) {
		this.xDistance = xDistance;
	}
	public float getyDistance() {
		return yDistance;
	}
	public void setyDistance(float yDistance) {
		this.yDistance = yDistance;
	}
}
