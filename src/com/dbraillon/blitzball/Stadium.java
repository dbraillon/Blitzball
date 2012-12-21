package com.dbraillon.blitzball;

public class Stadium {

	// nombre de pixel libre DANS le cercle
	private int _radius;
	
	// nombre de pixel total du cercle (en incluant les bordures)
	private int _totalRadius;
	
	public Stadium() {
		
		_radius = 600;
		set_totalRadius(_radius + 2);
	}

	public int get_radius() {
		return _radius;
	}

	public void set_radius(int _radius) {
		this._radius = _radius;
	}

	public int get_totalRadius() {
		return _totalRadius;
	}

	public void set_totalRadius(int _totalRadius) {
		this._totalRadius = _totalRadius;
	}
}
