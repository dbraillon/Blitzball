package com.dbraillon.blitzball;

import com.dbraillon.blitzball.enumerations.Decision;
import com.dbraillon.blitzball.enumerations.DecisionType;
import com.dbraillon.blitzball.enumerations.Position;

public class Player {

	private double xOriginPosition, yOriginPosition;
	private double xPosition, yPosition;
	public double xDestination, yDestination;
	
	private int playerRadius; // cercle du joueur
	private int caughtRadius; // si quelqu'un rentre dans ce cercle, il attrape le joueur
	private int reflexRadius; // si quelqu'un reste dans ce cercle pendant 2 secondes il attrape le joueur
	private int followRadius; // cercle de poursuite du joueur
	
	private double directionDegrees;
	private double xMove, yMove;
	
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
	
	public Position position;
	public Team team;
	
	public Decision lastDecision;
	
	
	public Player(int hp, int sp, int en, int at, int pa, int bl, int sh, int ca, int re,
				  int xOriginPosition, int yOriginPosition, Position pos, Team team) {
		
		this.set_PlayerRadius(10);
		this.set_reflexRadius(100);
		this.set_CaughtRadius(50);
		this.set_FollowRadius(210);
		
		set_xOriginPosition(xOriginPosition);
		set_yOriginPosition(yOriginPosition);
		
		this.position = pos;
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
		
		this.lastDecision = new Decision(DecisionType.NOTHING);
	}

	
	public void changeDirection(double directionDegrees) {
		
		this.set_DirectionDegrees(directionDegrees);
		set_xMove(Math.cos(Math.toRadians(directionDegrees)));
		set_yMove(0 - Math.sin(Math.toRadians(directionDegrees)));
	}
	
	public void turnToDestination() {
		
		// third position with x of destination and y of follower
		// need this point to calculate direction angle
		double xThird = xDestination;
		double yThird = yPosition;
		
		// follower - destination distance
		// follower - third distance
		double fdDistance = Math.sqrt(Math.pow(xPosition - xDestination, 2) + Math.pow(yPosition - yDestination, 2));
		double ftDistance = Math.sqrt(Math.pow(xPosition - xThird, 2) + Math.pow(yPosition - yThird, 2));
		
		// angle the follower has to take to go at destination
		double angle = Math.toDegrees(Math.acos(ftDistance/fdDistance));
		
		// A -> Follower
		// B -> Destination
		if(xPosition > xDestination && yPosition > yDestination)
		{
			/// A en bas à droite de B (en haut à droite en vrai)
			angle = 180 - angle;
		}
		else if(xPosition < xDestination && yPosition > yDestination)
		{
			/// A en bas à gauche de B (en haut à gauche en vrai)
		}
		else if(xPosition > xDestination && yPosition < yDestination)
		{
			// A en haut à droite de B (en bas à droite en vrai)
			angle = 180 + angle;
		}
		else if(xPosition < xDestination && yPosition < yDestination)
		{
			// A en haut à gauche de B (en bas à gauche en vrai)
			angle = 360 - angle;
		}
		else if(xPosition == xDestination && yPosition > yDestination)
		{
			// A en bas de B (en haut en vrai)
			angle = 90;
		}
		else if(xPosition == xDestination && yPosition < yDestination)
		{
			// A en haut de B (en bas en vrai)
			angle = 270;
		}
		else if(xPosition == xDestination && yPosition == yDestination)
		{
			// A sur B
		}
		else if(xPosition < xDestination && yPosition == yDestination)
		{
			// A à gauche de B
			angle = 0;
		}
		else if(xPosition > xDestination && yPosition == yDestination)
		{
			// A à droite de B
			angle = 180;
		}
		
		changeDirection(angle);
	}

	public void turnToDestination(double xDestination, double yDestination) {
		
		this.xDestination = xDestination;
		this.yDestination = yDestination;
		
		turnToDestination();
	}
	
	public void goForward() {
		
		double velocity = sp / 10;
		
		// follower - destination distance
		// follower - third distance
		double fdDistance = Math.sqrt(Math.pow(xPosition - xDestination, 2) + Math.pow(yPosition - yDestination, 2));
		
		
		if(fdDistance < velocity) {
			
			setPosition(xDestination, yDestination);
		}
		else {
			
			setPosition(xPosition + velocity * xMove, yPosition + velocity * yMove);
		}
	}
	
	public void setPosition(double xPosition, double yPosition) {
		
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	
	public void increaseCRE() {
		
		if(cre < re) {
			
			cre++;
		}
	}
	
	public void resetCRE() {
		
		cre = 0;
	}
	
	public boolean isAware() {
		
		return cre >= re;
	}
	
	public boolean isNear(Player p) {
		
		double d = Math.sqrt(Math.pow(xPosition - p.get_xPosition(), 2) + Math.pow(yPosition - p.get_yPosition(), 2));
		
		if(d < followRadius / 2) {
			
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public String toString() {
		
		switch(position) {
		
			case LF:
				return team.toString() + ": Left Front";
			case RF:
				return team.toString() + ": Right Front";
			case MF:
				return team.toString() + ": Middle Front";
			case LD:
				return team.toString() + ": Left Defender";
			case RD:
				return team.toString() + ": Right Defender";
			case GL:
				return team.toString() + ": Goal";
			default:
				return team.toString() + ": Nobody";	
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

}
