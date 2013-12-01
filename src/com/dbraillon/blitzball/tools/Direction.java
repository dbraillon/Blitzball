package com.dbraillon.blitzball.tools;

public class Direction {

	private Vector vector;
	private double corner;
	
	public Direction(Point currentPosition, Point destinationPosition) { 
		
		update(currentPosition, destinationPosition);
	}
	
	// Set the opposite of vy, because of the origin of landmark
	public void update(double corner) {
		
		setCorner(corner);
		setVector(new Vector(Math.cos(corner), 0 - Math.sin(corner)));
	}
	
	public void update(Point currentPosition, Point destinationPosition) {
		
		// A(cp.x, cp.y)
		// B(dp.x, dp.y)
		// Vector AB(Bx-Ax, By-Ay)
		Vector v1 = new Vector(currentPosition, destinationPosition);
		Vector v2 = new Vector(1, 0);
		
		// Prod. scalaire
		// v1(xi + yj)
		// v2(xi + yi)
		// ps = xi * xi + yi * yi
		double prodScalaire = Vector.prodScalaire(v1, v2);
		
		// |v1| |v2|
		// |v1| = sqrt(pow(v1.x) + pow(v1.y))
		double ampV1 = Math.sqrt(Math.pow(v1.getX(), 2) + Math.pow(v1.getY(), 2));
		double ampV2 = Math.sqrt(Math.pow(v2.getX(), 2) + Math.pow(v2.getY(), 2));
		
		// cos(corner) = ProdScalaire / |v1|*|v2|
		double cos = prodScalaire / (ampV1 * ampV2);
		
		// corner = cos-1(cos)
		update(Math.acos(cos));
		
		/*
		// third position with x of destination and y of follower
		// need this point to calculate direction angle
		double xThird = destinationPosition.getX();
		double yThird = currentPosition.getY();
		
		// follower - destination distance
		// follower - third distance
		double fdDistance = Math.sqrt(Math.pow(xPosition - getxDestination(), 2) + Math.pow(yPosition - getyDestination(), 2));
		double ftDistance = Math.sqrt(Math.pow(xPosition - xThird, 2) + Math.pow(yPosition - yThird, 2));
		
		// angle the follower has to take to go at destination
		double angle = Math.toDegrees(Math.acos(ftDistance/fdDistance));
		
		// A -> Follower
		// B -> Destination
		if(xPosition > getxDestination() && yPosition > getyDestination())
		{
			/// A en bas à droite de B (en haut à droite en vrai)
			angle = 180 - angle;
		}
		else if(xPosition < getxDestination() && yPosition > getyDestination())
		{
			/// A en bas à gauche de B (en haut à gauche en vrai)
		}
		else if(xPosition > getxDestination() && yPosition < getyDestination())
		{
			// A en haut à droite de B (en bas à droite en vrai)
			angle = 180 + angle;
		}
		else if(xPosition < getxDestination() && yPosition < getyDestination())
		{
			// A en haut à gauche de B (en bas à gauche en vrai)
			angle = 360 - angle;
		}
		else if(xPosition == getxDestination() && yPosition > getyDestination())
		{
			// A en bas de B (en haut en vrai)
			angle = 90;
		}
		else if(xPosition == getxDestination() && yPosition < getyDestination())
		{
			// A en haut de B (en bas en vrai)
			angle = 270;
		}
		else if(xPosition == getxDestination() && yPosition == getyDestination())
		{
			// A sur B
		}
		else if(xPosition < getxDestination() && yPosition == getyDestination())
		{
			// A à gauche de B
			angle = 0;
		}
		else if(xPosition > getxDestination() && yPosition == getyDestination())
		{
			// A à droite de B
			angle = 180;
		}
		
		changeDirection(angle);*/
	}

	
	// Getters and setters
	public double getCorner() {
		return corner;
	}

	public void setCorner(double corner) {
		this.corner = corner;
	}

	public Vector getVector() {
		return vector;
	}

	public void setVector(Vector vector) {
		this.vector = vector;
	}
}
