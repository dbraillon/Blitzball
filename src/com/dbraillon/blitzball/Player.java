package com.dbraillon.blitzball;

import javax.crypto.spec.PSource;

public class Player {

	private double _xPosition;
	private double _yPosition;
	
	// diamètre du joueur en comptant les bords
	private int _radius;
	private int _reflexRadius;
	
	private double _directionDegrees;
	
	public double xMove;
	public double yMove;
	
	public int hp; // health point
	public int sp; // speed
	public int en; // endurance
	public int at; // attack
	public int pa; // passe
	public int bl; // block
	public int sh; // shoot
	public int ca; // catch
	
	public int position;
	public Team team;
	
	public Player(int hp, int sp, int en, int at, int pa, int bl, int sh, int ca) {
		
		set_radius(10);
		set_reflexRadius(75);
		
		set_xPosition(0);
		set_yPosition(0);
		
		changeDirection(180);
		
		this.hp = hp;
		this.sp = sp;
		this.en = en;
		this.at = at;
		this.pa = pa;
		this.bl = bl;
		this.sh = sh;
		this.ca = ca;
	}
	
	public void changeDirection(double directionDegrees) {
		
		_directionDegrees = directionDegrees;
		xMove = Math.cos(Math.toRadians(_directionDegrees));
		yMove = 0 - Math.sin(Math.toRadians(_directionDegrees));
	}
	
	public void goForward(double velocity) {
		
		set_xPosition(get_xPosition() + velocity * xMove);
		set_yPosition(get_yPosition() + velocity * yMove);
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
	
	public void set_position(double xPosition, double yPosition) {
		
		_xPosition = xPosition;
		_yPosition = yPosition;
	}

	public int get_radius() {
		return _radius;
	}

	public void set_radius(int _radius) {
		this._radius = _radius;
	}

	public int get_reflexRadius() {
		return _reflexRadius;
	}

	public void set_reflexRadius(int _reflexRadius) {
		this._reflexRadius = _reflexRadius;
	}
	
	@Override
	public String toString() {
		
		switch(position) {
		
			case 0:
				return team.toString() + " : Attaquant gauche";
			case 1:
				return team.toString() + " : Attaquant droit";
			case 2:
				return team.toString() + " : Milieu";
			case 3:
				return team.toString() + " : Défenseur gauche";
			case 4:
				return team.toString() + " : Défenseur droit";
			case 5:
				return team.toString() + " : Gardien";
			default:
				return team.toString() + " : Personne";	
		}
	}
}
