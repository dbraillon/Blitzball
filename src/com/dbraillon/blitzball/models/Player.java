package com.dbraillon.blitzball.models;

import java.util.ArrayList;
import java.util.Random;

import com.dbraillon.blitzball.Constants;
import com.dbraillon.blitzball.enumerations.Decision;
import com.dbraillon.blitzball.enumerations.DecisionType;
import com.dbraillon.blitzball.enumerations.PlayerPosition;
import com.dbraillon.blitzball.tools.Reflex;
import com.dbraillon.blitzball.tools.Zone;
import com.dbraillon.math.Direction;
import com.dbraillon.math.Point;
import com.dbraillon.math.Vector;

public class Player {

	private PlayerPosition position;
	private Team team;
	
	private Point originPosition;
	private Point currentPosition;
	private Point destinationPosition;
	
	private Vector vectorDirection;
	
	private Zone playerZone = new Zone(32);
	private Zone caughtZone = new Zone(50);
	private Zone reflexZone = new Zone(100);
	private Zone followZone = new Zone(150);
	
	private Reflex reflex;
	private Decision lastDecision = new Decision(DecisionType.NOTHING);
	
	public int hp; // health point
	public int sp; // speed
	public int en; // endurance
	public int at; // attack
	public int pa; // pass
	public int bl; // block
	public int sh; // shoot
	public int ca; // catch
	
	
	public Player(int hp, int sp, int en, int at, int pa, int bl, int sh, int ca, int re,
				  int xOriginPosition, int yOriginPosition, PlayerPosition pos, Team team) {
		
		originPosition = new Point(xOriginPosition, yOriginPosition);
		currentPosition = originPosition;
		vectorDirection = new Vector(currentPosition, currentPosition);
		
		reflex = new Reflex(re);
		
		this.hp = hp;
		this.sp = sp;
		this.en = en;
		this.at = at;
		this.pa = pa;
		this.bl = bl;
		this.sh = sh;
		this.ca = ca;
		this.position = pos;
		this.team = team;
	}
	
	
	/**
	 * Turn the player to his current destination
	 */
	public void turnToDestination() {
		
		// Set new vector direction
		vectorDirection = new Vector(currentPosition, destinationPosition);
	}

	/**
	 * Turn the player to the specified destination
	 * @param desinationPosition The new destination to follow
	 */
	public void turnToDestination(Point desinationPosition) {
		
		// set new destination
		setDestinationPosition(desinationPosition);
		
		// turn the current player to it
		turnToDestination();
	}
	
	
	/**
	 * Make a step to his current destination
	 */
	public void goForward() {
		
		// distance between his position and his destination
		float distance = Point.getDistance(currentPosition, destinationPosition);
		
		// reduce the velocity
		float velocity = sp / Constants.velocityModifier;
		
		// get the % sp / distance
		float percant = velocity / distance;
		
		if(percant >= 1)
			currentPosition = destinationPosition;
		else
			currentPosition = new Point(currentPosition.getX() + vectorDirection.getxDistance() * percant, 
										currentPosition.getY() + vectorDirection.getyDistance() * percant);
	}
	

	/**
	 * Check if the enemy team caught the current player
	 * @param tEnemy The enemy team that want to catch the current player
	 * @return All enemy that caught the current player
	 */
	public ArrayList<Player> isCaught(Team tEnemy) {

		// enemy that caught current player
		ArrayList<Player> cEnnemy = new ArrayList<Player>();
		// enemy that is in reflex radius and will help if someone caught current player
		ArrayList<Player> rEnnemy = new ArrayList<Player>(); 
		
		// iterate over the enemy team
		for(int i = 0; i < Team.PLAYER_COUNT; i++) {
			
			Player c = tEnemy.getPlayer(i);
			
			// don't take the goal
			if(c.getPosition() != PlayerPosition.GL) {
			
				// if the current player is in current enemy caught zone
				if(c.getCaughtZone().isInZone(c.getCurrentPosition(), currentPosition)) {
					
					// add it to caught
					cEnnemy.add(c);
				}
				// otherwise if the current player is in current enemy reflex zone
				else if(c.getReflexZone().isInZone(c.getCurrentPosition(), currentPosition)) {
					
					// add it to reflex
					rEnnemy.add(c);
				}
			}
		}
		
		// if someone caught the current player, all player in reflex radius help him
		if(cEnnemy.size() > 0) {
			
			cEnnemy.addAll(rEnnemy);
		}
		
		return cEnnemy;
	}
	
	
	/**
	 * Check if the current player can follow the given player
	 * @param p The player to follow
	 * @return Whether the current player can follow the given one or not
	 */
	public boolean canFollow(Player p) {
		
		return followZone.isInZone(currentPosition, p.getCurrentPosition());
	}
	
	
	public boolean isNear(Point point) {
		
		return Point.getDistance(currentPosition, point) < Constants.nearDistance;
	}
	
	
	/**
	 * Attack the given endurance
	 * @param en The endurance that the current player has to attack
	 * @return The endurance after the attack
	 */
	public int attack(int en) {
		
		// get the 50-150% modifier randomly
		Random r = new Random();
		double percant = r.nextDouble() + 0.5;
		
		// put it to the current player attack
		int mAt = (int) Math.round(at * percant);
		
		// then attack
		return en - mAt;
	}
	
	
	/**
	 * Shoot to the given goal
	 * @param goalie The goal where the current player shoot
	 * @return Whether the current player make a goal or not
	 */
	public boolean shoot(Player goalie) {
		
		// get the distance from the goal
		double d = Point.getDistance(goalie.getCurrentPosition(), currentPosition);
		
		// reduce the distance
		d = d / Constants.distanceModifier;
		
		// get the 50-150% modifier randomly
		Random r = new Random();
		double percant = r.nextDouble() + 0.5;
		
		// put it to the goal catch
		int mCa = (int) Math.round(goalie.ca * percant);
		
		// then return the goal or not
		return sh > mCa;
	}
	
	@Override
	public String toString()
	{
		return toString('s');
	}
	
	public String toString(char output) {
		
		if(output == 'n') {
			
			switch(getPosition()) {
				
				case LF:
					return getTeam().getName() + ": Left Front";
				case RF:
					return getTeam().getName() + ": Right Front";
				case MF:
					return getTeam().getName() + ": Middle Front";
				case LD:
					return getTeam().getName() + ": Left Defender";
				case RD:
					return getTeam().getName() + ": Right Defender";
				case GL:
					return getTeam().getName() + ": Goal";
				default:
					return "Nobody";	
			}
		}
		if(output == 's') {
			
			return String.format("%03d %02d %02d %02d %02d %02d %02d %02d", 
								 hp, sp, en, at, pa, bl, sh, ca);
		}
		
		return "";
	}


	// Getters and setters
	public Point getOriginPosition() {
		return originPosition;
	}


	public void setOriginPosition(Point originPosition) {
		this.originPosition = originPosition;
	}


	public Point getCurrentPosition() {
		return currentPosition;
	}


	public void setCurrentPosition(Point currentPosition) {
		this.currentPosition = currentPosition;
	}


	public Point getDestinationPosition() {
		return destinationPosition;
	}


	public void setDestinationPosition(Point destinationPosition) {
		this.destinationPosition = destinationPosition;
	}


	public Direction getDirection() {
		return vectorDirection.toDirection();
	}

	public Zone getPlayerZone() {
		return playerZone;
	}


	public void setPlayerZone(Zone playerZone) {
		this.playerZone = playerZone;
	}


	public Zone getCaughtZone() {
		return caughtZone;
	}


	public void setCaughtZone(Zone caughtZone) {
		this.caughtZone = caughtZone;
	}


	public Zone getFollowZone() {
		return followZone;
	}


	public void setFollowZone(Zone followZone) {
		this.followZone = followZone;
	}


	public Decision getLastDecision() {
		return lastDecision;
	}


	public void setLastDecision(Decision lastDecision) {
		this.lastDecision = lastDecision;
	}


	public PlayerPosition getPosition() {
		return position;
	}


	public void setPosition(PlayerPosition position) {
		this.position = position;
	}


	public Team getTeam() {
		return team;
	}


	public void setTeam(Team team) {
		this.team = team;
	}

	public Zone getReflexZone() {
		return reflexZone;
	}

	public void setReflexZone(Zone reflexZone) {
		this.reflexZone = reflexZone;
	}


	public Reflex getReflex() {
		return reflex;
	}


	public void setReflex(Reflex reflex) {
		this.reflex = reflex;
	}
}
