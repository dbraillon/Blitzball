package com.dbraillon.blitzball;

public class Player {

	private double _xPosition;
	private double _yPosition;
	private int _radius;
	
	private double _directionDegrees;
	public double xMove;
	public double yMove;
	
	public Player(int xPosition, int yPosition) {
		
		set_radius(10);
		
		set_xPosition(xPosition);
		set_yPosition(yPosition);
		
		changeDirection(0);
	}
	
	public void changeDirection(double directionDegrees) {
		
		_directionDegrees = directionDegrees;
		xMove = Math.cos(Math.toRadians(_directionDegrees));
		yMove = 0 - Math.sin(Math.toRadians(_directionDegrees));
		
		System.out.println("Changing direction to " + directionDegrees);
	}
	
	public void goForward(double velocity) {
		
		_xPosition += velocity * xMove;
		_yPosition += velocity * yMove;
	}

	public double get_xPosition() {
		return _xPosition;
	}

	public void set_xPosition(double _xPosition) {
		this._xPosition = _xPosition;
	}
	
	public double get_yPosition() {
		return _yPosition;
	}
	
	public void set_yPosition(double _yPosition) {
		this._yPosition = _yPosition;
	}

	public int get_radius() {
		return _radius;
	}

	public void set_radius(int _radius) {
		this._radius = _radius;
	}

	public double get_directionDegrees() {
		return _directionDegrees;
	}

	public void set_directionDegrees(double _directionDegrees) {
		this._directionDegrees = _directionDegrees;
	}
}
