package com.dbraillon.blitzball.models;

import java.util.Random;
import java.util.Vector;

import com.dbraillon.blitzball.Constants;
import com.dbraillon.blitzball.enumerations.Decision;
import com.dbraillon.blitzball.enumerations.DecisionType;
import com.dbraillon.blitzball.enumerations.PlayerPosition;
import com.dbraillon.blitzball.enumerations.TeamPosition;
import com.dbraillon.blitzball.tools.Direction;
import com.dbraillon.blitzball.tools.Point;
import com.dbraillon.blitzball.tools.Reflex;
import com.dbraillon.blitzball.tools.Zone;

public class Player {

	private PlayerPosition position;
	private Team team;
	
	private Point originPosition;
	private Point currentPosition;
	private Point destinationPosition;
	
	private Direction direction;
	
	private Zone playerZone;
	private Zone caughtZone;
	private Zone reflexZone;
	private Zone followZone;
	
	private Reflex reflex;
	private Decision lastDecision;
	
	
	public int hp; // health point
	public int sp; // speed
	public int en; // endurance
	public int at; // attack
	public int pa; // pass
	public int bl; // block
	public int sh; // shoot
	public int ca; // catch
	
	private boolean directionLocked;
	
	
	public Player(int hp, int sp, int en, int at, int pa, int bl, int sh, int ca, int re,
				  int xOriginPosition, int yOriginPosition, PlayerPosition pos, Team team) {
		
		setPosition(pos);
		setTeam(team);
		
		setOriginPosition(new Point(xOriginPosition, yOriginPosition));
		setCurrentPosition(originPosition);
		setDirection(new Direction(currentPosition, currentPosition));
		
		setPlayerZone(new Zone(5));
		setCaughtZone(new Zone(50));
		setReflexZone(new Zone(100));
		setFollowZone(new Zone(150));
		
		setReflex(new Reflex(re));
		setLastDecision(new Decision(DecisionType.NOTHING));
		
		
		this.hp = hp;
		this.sp = sp;
		this.en = en;
		this.at = at;
		this.pa = pa;
		this.bl = bl;
		this.sh = sh;
		this.ca = ca;
		
		directionLocked = false;
	}
	
	
	/**
	 * Turn the player to his current destination
	 */
	public void turnToDestination() {
		
		// calculate new direction
		direction.update(currentPosition, destinationPosition);
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
		
		// reduce the velocity
		double velocity = sp / Constants.velocityModifier;
		
		// get the distance between the current position and the destination
		double fdDistance = Point.distance(currentPosition, destinationPosition);
		
		// if the destination is close enough, move the current player on it
		if(fdDistance < velocity) {
			
			setCurrentPosition(destinationPosition);
		}
		// otherwise move to it
		else {
			
			Point newPosition = new Point(currentPosition.getX() + velocity * direction.getVector().getX(), currentPosition.getY() + velocity * direction.getVector().getY());
			setCurrentPosition(newPosition);
		}
	}
	

	/**
	 * Check if the enemy team caught the current player
	 * @param tEnemy The enemy team that want to catch the current player
	 * @return All enemy that caught the current player
	 */
	public Vector<Player> isCaught(Team tEnemy) {

		// enemy that caught current player
		Vector<Player> cEnnemy = new Vector<Player>();
		// enemy that is in reflex radius and will help if someone caught current player
		Vector<Player> rEnnemy = new Vector<Player>(); 
		
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
		
		return Point.distance(currentPosition, point) < Constants.nearDistance;
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
		double d = Point.distance(goalie.getCurrentPosition(), currentPosition);
		
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
	
	
	// Positioning
	
	/**
	 * Put the current player to his catch positioning
	 * @param pBall The player that is caught
	 * @param i The current player place
	 * @return When the current place is in place
	 */
	public boolean catchPositioning(Player pBall, int i) {
		
		if(!directionLocked) {
			
			double pbx = pBall.getCurrentPosition().getX();
			double pby = pBall.getCurrentPosition().getY();
			
			Point catchPosition = null;
			
			if(i == 0) catchPosition = new Point(pbx + pBall.getCaughtZone().getRadius(), pby);
			else if(i == 1) catchPosition = new Point(pbx + pBall.getCaughtZone().getRadius() - 5, pby - 10);
			else if(i == 2) catchPosition = new Point(pbx + pBall.getCaughtZone().getRadius() - 5, pby + 10);
			else if(i == 3) catchPosition = new Point(pbx + pBall.getCaughtZone().getRadius() - 10, pby - 15);
			else catchPosition = new Point(pbx + pBall.getCaughtZone().getRadius() - 10, pby + 15);
			
			// player ball belongs to right team so the current player needs to be to the left
			if(pBall.getTeam().get_tPosition() == TeamPosition.RIGHT) {
				
				catchPosition = catchPosition.getOppositeAgainst(pBall.getCurrentPosition());
			}
			
			turnToDestination(catchPosition);
			directionLocked = true;
		}
		
		goForward();
		
		if(isNear(destinationPosition)) {
			
			directionLocked = false;
			return true;
		}
		
		return false;
	}
	
	
	public boolean attackAnim(Player pBall) {
		
		if(!directionLocked) {
			
			turnToDestination(currentPosition.getOppositeAgainst(pBall.getCurrentPosition()));
			directionLocked = true;
		}
		
		goForward();
		
		if(isNear(destinationPosition)) {
			
			directionLocked = false;
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public String toString() {
		
		switch(getPosition()) {
		
			case LF:
				return "Left Front";
			case RF:
				return "Right Front";
			case MF:
				return "Middle Front";
			case LD:
				return "Left Defender";
			case RD:
				return "Right Defender";
			case GL:
				return "Goal";
			default:
				return "Nobody";	
		}
	}
	
	public String toString(char output) {
		
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
		return direction;
	}


	public void setDirection(Direction direction) {
		this.direction = direction;
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
