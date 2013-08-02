package com.dbraillon.blitzball;

public class Player {

	private double xOriginPosition;
	private double yOriginPosition;
	private double xPosition;
	private double yPosition;
	
	private int playerRadius; // cercle du joueur
	private int caughtRadius; // si quelqu'un rentre dans ce cercle, il attrape le joueur
	private int reflexRadius; // si quelqu'un reste dans ce cercle pendant 2 secondes il attrape le joueur
	private int followRadius; // cercle de poursuite du joueur
	
	private double directionDegrees;
	private double xMove;
	private double yMove;
	
	public int hp; // health point
	public int sp; // speed
	public int en; // endurance
	public int at; // attack
	public int pa; // passe
	public int bl; // block
	public int sh; // shoot
	public int ca; // catch
	
	public int re; // reflex
	public int cre; // counter reflex
	
	public int pos;
	public Team team;
	
	
	public Player(int hp, int sp, int en, int at, int pa, int bl, int sh, int ca, int re,
				  int xOriginPosition, int yOriginPosition, int pos, Team team) {
		
		this.set_PlayerRadius(10);
		this.set_reflexRadius(100);
		this.set_CaughtRadius(50);
		this.set_FollowRadius(210);
		
		set_xOriginPosition(xOriginPosition);
		set_yOriginPosition(yOriginPosition);
		
		this.pos = pos;
		this.team = team;
		
		this.hp = hp;
		this.sp = sp;
		this.en = en;
		this.at = at;
		this.pa = pa;
		this.bl = bl;
		this.sh = sh;
		this.ca = ca;
		this.re = re;
		this.cre = re;
	}

	public void changeDirection(double directionDegrees) {
		
		this.set_DirectionDegrees(directionDegrees);
		set_xMove(Math.cos(Math.toRadians(directionDegrees)));
		set_yMove(0 - Math.sin(Math.toRadians(directionDegrees)));
	}
	
	public void goForward(double velocity) {
		
		set_xPosition(get_xPosition() + velocity * get_xMove());
		set_yPosition(get_yPosition() + velocity * get_yMove());
	}
	
	public void setPosition(double xPosition, double yPosition) {
		
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	@Override
	public String toString() {
		
		switch(pos) {
		
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
	
	public double get_xPosition() {
		return xPosition;
	}

	public void set_xPosition(double _xPosition) {
		this.xPosition = _xPosition;
		
	}

	public double get_yPosition() {
		return yPosition;
	}

	public void set_yPosition(double _yPosition) {
		this.yPosition = _yPosition;
		
	}
	
	public int get_reflexRadius() {
		return reflexRadius;
	}

	public void set_reflexRadius(int _reflexRadius) {
		this.reflexRadius = _reflexRadius;
	}

	public double get_xOriginPosition() {
		return xOriginPosition;
	}

	public void set_xOriginPosition(double xOriginPosition) {
		this.xOriginPosition = xOriginPosition;
		this.xPosition = xOriginPosition;
	}

	public double get_yOriginPosition() {
		return yOriginPosition;
	}

	public void set_yOriginPosition(double yOriginPosition) {
		this.yOriginPosition = yOriginPosition;
		this.yPosition = yOriginPosition;
	}

	public int get_PlayerRadius() {
		return playerRadius;
	}

	public void set_PlayerRadius(int playerRadius) {
		this.playerRadius = playerRadius;
	}

	public int get_FollowRadius() {
		return followRadius;
	}

	public void set_FollowRadius(int followRadius) {
		this.followRadius = followRadius;
	}

	public double get_DirectionDegrees() {
		return directionDegrees;
	}

	public void set_DirectionDegrees(double directionDegrees) {
		this.directionDegrees = directionDegrees;
	}

	public double get_xMove() {
		return xMove;
	}

	public void set_xMove(double xMove) {
		this.xMove = xMove;
	}

	public double get_yMove() {
		return yMove;
	}

	public void set_yMove(double yMove) {
		this.yMove = yMove;
	}

	public int get_CaughtRadius() {
		return caughtRadius;
	}

	public void set_CaughtRadius(int caughtRadius) {
		this.caughtRadius = caughtRadius;
	}

	public void increaseCRE() {
		
		if(cre < re) {
			
			cre++;
		}
	}
}
