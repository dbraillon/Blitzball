package com.dbraillon.blitzball;

import java.util.Random;
import java.util.Vector;

import com.dbraillon.blitzball.enumerations.TeamPosition;

public class PlayerController {

	private Stadium _stadium;
	
	
	public PlayerController(Stadium stadium) {
		
		_stadium = stadium;
	}
	
	
	public boolean goForwardControl(Player player, double v) {
		
		boolean b = true;
		
		//double sr = _stadium.get_radius() / 2;
		
		// current position
		//double xc = player.get_xPosition();
		//double yc = player.get_yPosition();
		//double dc = Math.sqrt(Math.pow(xc - sr,  2) + Math.pow(yc - sr, 2));
		
		// future position
		//double xf = xc + player.get_xMove() * v;
		//double yf = yc + player.get_yMove() * v;
		//double df = Math.sqrt(Math.pow(xf - sr, 2) + Math.pow(yf - sr, 2));
		
		//v = (df >= sr) ? sr - dc : v;
		
		if(v < 1) b = false;
		
		player.goForward(v);
		
		return b;
	}
	
	public void goToPoint(Player pFollower, double xPosition, double yPosition)
	{
		// follower's speed
		double fSpeed = pFollower.sp / 10;
		
		// follower's position
		double xFollower = pFollower.get_xPosition();
		double yFollower = pFollower.get_yPosition();
		
		// third position with x of destination and y of follower
		// need this point to calculate direction angle
		double xThird = xPosition;
		double yThird = yFollower;
		
		// follower - destination distance
		// follower - third distance
		double fdDistance = Math.sqrt(Math.pow(xFollower - xPosition, 2) + Math.pow(yFollower - yPosition, 2));
		double ftDistance = Math.sqrt(Math.pow(xFollower - xThird, 2) + Math.pow(yFollower - yThird, 2));
		
		
		if(fdDistance < fSpeed) {
			
			pFollower.set_xPosition(xPosition);
			pFollower.set_yPosition(yPosition);
		}
		else {
			
			// angle the follower has to take to go at destination
			double angle = Math.toDegrees(Math.acos(ftDistance/fdDistance));
			
			// A -> Follower
			// B -> Destination
			if(xFollower > xPosition && yFollower > yPosition)
			{
				/// A en bas à droite de B (en haut à droite en vrai)
				angle = 180 - angle;
			}
			else if(xFollower < xPosition && yFollower > yPosition)
			{
				/// A en bas à gauche de B (en haut à gauche en vrai)
			}
			else if(xFollower > xPosition && yFollower < yPosition)
			{
				// A en haut à droite de B (en bas à droite en vrai)
				angle = 180 + angle;
			}
			else if(xFollower < xPosition && yFollower < yPosition)
			{
				// A en haut à gauche de B (en bas à gauche en vrai)
				angle = 360 - angle;
			}
			else if(xFollower == xPosition && yFollower > yPosition)
			{
				// A en bas de B (en haut en vrai)
				angle = 90;
			}
			else if(xFollower == xPosition && yFollower < yPosition)
			{
				// A en haut de B (en bas en vrai)
				angle = 270;
			}
			else if(xFollower == xPosition && yFollower == yPosition)
			{
				// A sur B
				fSpeed = 0;
			}
			else if(xFollower < xPosition && yFollower == yPosition)
			{
				// A à gauche de B
				angle = 0;
			}
			else if(xFollower > xPosition && yFollower == yPosition)
			{
				// A à droite de B
				angle = 180;
			}
			
			pFollower.changeDirection(angle);
			goForwardControl(pFollower, fSpeed);
		}
	}

	public Vector<Player> isCaught(Player pBall, Team tEnnemy) {

		Vector<Player> cEnnemy = new Vector<Player>(); // ennemy that caught pBall
		Vector<Player> rEnnemy = new Vector<Player>(); // ennemy that is in reflex radius and will help if someone caught pBall
		
		for(int i = 0; i < Team.PLAYER_COUNT; i++) {
			
			Player c = tEnnemy.getPlayer(i);
			if(c.pos != Team.GL) {
			
				double d = Math.sqrt(Math.pow(c.get_xPosition() - pBall.get_xPosition(), 2) + Math.pow(c.get_yPosition() - pBall.get_yPosition(), 2));
				if(d < c.get_CaughtRadius() / 2) {
					
					cEnnemy.add(c);
				}
				else if(d < c.get_reflexRadius() / 2)
				{
					rEnnemy.add(c);
				}
			}
		}
		
		// if someone caught pBall all player in reflex radius help him
		if(cEnnemy.size() > 0) {
			
			cEnnemy.addAll(rEnnemy);
		}
		
		return cEnnemy;
	}
	
	public boolean isFollow(Player pMe, Player pBall) {
		
		double d = Math.sqrt(Math.pow(pMe.get_xPosition() - pBall.get_xPosition(), 2) + Math.pow(pMe.get_yPosition() - pBall.get_yPosition(), 2));
		if(d < pMe.get_FollowRadius() / 2) {
			
			return true;
		}
		
		return false;
	}
	
	public int attack(int at, int en) {
		
		System.out.println("AT: " + at + ", EN: " + en);
		
		Random r = new Random();
		double percant = r.nextDouble() + 0.5;
		
		at = (int) Math.round(at * percant);
		en -= at;
		
		System.out.println("AT: " + at + ", EN: " + en);
		
		return en;
	}
	
	public boolean attackAnim(Player pBall, Player pAttacker, double x, double y) {
		
		double bx = pBall.get_xPosition();
		double by = pBall.get_yPosition();
		double ax = x;
		double ay = y;
		
		double dx = Math.abs(bx - ax);
		double dy = Math.abs(by - ay);
		
		double fx = (bx < ax) ? bx - dx : bx + dx;
		double fy = (by < ay) ? by - dy : by + dy;
		
		
		goToPoint(pAttacker, fx, fy);
		
		return isNear(pAttacker, fx, fy);
	}
	
	public boolean shoot(Player pBall, Player goalie) {
		
		double d = Math.sqrt(Math.pow(goalie.get_xPosition() - pBall.get_xPosition(), 2) + Math.pow(goalie.get_yPosition() - pBall.get_yPosition(), 2));
		
		d = d / 20;
		Random r = new Random();
		double percant = r.nextDouble() + 0.5;
		long ca = Math.round(goalie.ca * percant);
		long sh = pBall.sh;
		
		sh -= d;
		
		if(sh > ca) return true;
		
		return false;
	}
	
	public boolean makeADecision(Player pMe, Player pBall, Team tFriend, Team tEnnemy)
	{
		if(pMe.pos == Team.GL) return false;
		if(!pMe.isAware()) return false;
		
		// qui a le ballon ?
		if(pMe.team == pBall.team) {
			
			// quelqu'un de ma team ! mais qui ?
			if(pMe == pBall) {
				
				// c'est moi !
				double d = Math.sqrt(Math.pow(tEnnemy.getPlayer(Team.GL).get_xPosition() - pBall.get_xPosition(), 2) + Math.pow(tEnnemy.getPlayer(Team.GL).get_yPosition() - pBall.get_yPosition(), 2));
				if(d/20 < pMe.sh * 70 / 100) {
					
					if(shoot(pBall, tEnnemy.getPlayer(Team.GL))) {
						
						System.out.println("GOAL !");
						
						if(tEnnemy.get_tPosition() == TeamPosition.LEFT) tEnnemy.makeBlueTeam(); 
						else tEnnemy.makeRedTeam();
						
						if(tFriend.get_tPosition() == TeamPosition.LEFT) tFriend.makeBlueTeam(); 
						else tFriend.makeRedTeam();
					}
					else {
						
						System.out.println("MISS !");
					}
				}
				
				goToPoint(pMe, tEnnemy.getPlayer(Team.GL).get_xPosition(), tEnnemy.getPlayer(Team.GL).get_yPosition());
			}
			else {
				
				//pMe.set_xMove(pBall.get_xMove());
				//pMe.set_yMove(pBall.get_yMove());
				
				goToPoint(pMe, pMe.get_xOriginPosition(), pMe.get_yOriginPosition());
			}
		}
		else {
			
			// quelqu'un de la team adverse !
			if(isFollow(pMe, pBall)) {
				
				goToPoint(pMe, pBall.get_xPosition(), pBall.get_yPosition());
				return true;
			}
			else {
				
				goToPoint(pMe, pMe.get_xOriginPosition(), pMe.get_yOriginPosition());
				return true;
			}
		}
		
		return false;
	}

	public boolean catchPositioning(Player pAttacker, Player playerBall, int i) {
		
		double pbx = playerBall.get_xPosition();
		double pby = playerBall.get_yPosition();
		
		if(playerBall.team.get_tPosition() == TeamPosition.LEFT) {
			// la team de gauche donc les attaquants à droite
			switch(i) {
				case 0:
				{
					double xDestination = pbx + playerBall.get_CaughtRadius();
					double yDestination = pby;
					
					goToPoint(pAttacker, xDestination , yDestination);
					
					return isNear(pAttacker, xDestination, yDestination);
				}
				case 1:
				{
					double xDestination = pbx + playerBall.get_CaughtRadius() - 5;
					double yDestination = pby - 10;
					
					goToPoint(pAttacker, xDestination , yDestination);
					
					return isNear(pAttacker, xDestination, yDestination);
				}
				case 2:
				{
					double xDestination = pbx + playerBall.get_CaughtRadius() - 5;
					double yDestination = pby + 10;
					
					goToPoint(pAttacker, xDestination , yDestination);
					
					return isNear(pAttacker, xDestination, yDestination);
				}
				case 3:
				{
					double xDestination = pbx + playerBall.get_CaughtRadius() - 10;
					double yDestination = pby - 15;
					
					goToPoint(pAttacker, xDestination , yDestination);
					
					return isNear(pAttacker, xDestination, yDestination);
				}
				case 4:
				{
					double xDestination = pbx + playerBall.get_CaughtRadius() - 10;
					double yDestination = pby + 15;
					
					goToPoint(pAttacker, xDestination , yDestination);
					
					return isNear(pAttacker, xDestination, yDestination);
				}
			}
		} 
		else {
			// la team de droite donc les attaquants à gauche
			switch(i) {
				case 0:
				{
					double xDestination = pbx - playerBall.get_CaughtRadius();
					double yDestination = pby;
					
					goToPoint(pAttacker, xDestination , yDestination);
					
					return isNear(pAttacker, xDestination, yDestination);
				}
				case 1:
				{
					double xDestination = pbx - playerBall.get_CaughtRadius() + 5;
					double yDestination = pby - 10;
					
					goToPoint(pAttacker, xDestination , yDestination);
					
					return isNear(pAttacker, xDestination, yDestination);
				}
				case 2:
				{
					double xDestination = pbx - playerBall.get_CaughtRadius() + 5;
					double yDestination = pby + 10;
					
					goToPoint(pAttacker, xDestination , yDestination);
					
					return isNear(pAttacker, xDestination, yDestination);
				}
				case 3:
				{
					double xDestination = pbx - playerBall.get_CaughtRadius() + 10;
					double yDestination = pby - 15;
					
					goToPoint(pAttacker, xDestination , yDestination);
					
					return isNear(pAttacker, xDestination, yDestination);
				}
				case 4:
				{
					double xDestination = pbx - playerBall.get_CaughtRadius() + 10;
					double yDestination = pby + 15;
					
					goToPoint(pAttacker, xDestination , yDestination);
					
					return isNear(pAttacker, xDestination, yDestination);
				}
			}
		}
		
		return false;
	}


	public boolean isNear(Player p, double x, double y) {
		
		if(p.get_xPosition() >= x && p.get_xPosition() <= x
		&& p.get_yPosition() >= y && p.get_yPosition() <= y) {
			
			return true;
		}
		
		return false;
	}
}
