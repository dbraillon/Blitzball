package com.dbraillon.blitzball;

import java.util.Random;
import java.util.Vector;

public class PlayerController {

	private Stadium _stadium;
	
	
	public PlayerController(Stadium stadium) {
		
		_stadium = stadium;
	}
	
	
	public boolean goForwardControl(Player player, double v) {
		
		boolean b = true;
		
		double sr = _stadium.get_radius() / 2;
		
		// position actuelle
		double xc = player.get_xPosition();
		double yc = player.get_yPosition();
		double dc = Math.sqrt(Math.pow(xc - sr,  2) + Math.pow(yc - sr, 2));
		
		// position future
		double xf = xc + player.get_xMove() * v;
		double yf = yc + player.get_yMove() * v;
		double df = Math.sqrt(Math.pow(xf - sr, 2) + Math.pow(yf - sr, 2));
		
		v = (df >= sr) ? sr - dc : v;
		
		if(v < 1) b = false;
		
		player.goForward(v);
		
		return b;
	}
	
	public void goFollowPlayer(Player follower, double dx, double dy)
	{
		double rv = follower.sp / 10;
		
		double rx = follower.get_xPosition();
		double ry = follower.get_yPosition();
		
		// troisième point
		double tx = dx;
		double ty = ry;
		
		// calcul des distances
		double rd = Math.sqrt(Math.pow(rx - dx, 2) + Math.pow(ry - dy, 2));
		double rt = Math.sqrt(Math.pow(rx - tx, 2) + Math.pow(ry - ty, 2));
		
		double angle = Math.toDegrees(Math.acos(rt/rd));
		
		if(rx > dx && ry > dy)
		{
			/// A en bas à droite de B (en haut à droite en vrai)
			angle = 180 - angle;
		}
		else if(rx < dx && ry > dy)
		{
			/// A en bas à gauche de B (en haut à gauche en vrai)
		}
		else if(rx > dx && ry < dy)
		{
			// A en haut à droite de B (en bas à droite en vrai)
			angle = 180 + angle;
		}
		else if(rx < dx && ry < dy)
		{
			// A en haut à gauche de B (en bas à gauche en vrai)
			angle = 360 - angle;
		}
		else if(rx == dx && ry > dy)
		{
			// A en bas de B (en haut en vrai)
			angle = 90;
		}
		else if(rx == dx && ry < dy)
		{
			// A en haut de B (en bas en vrai)
			angle = 270;
		}
		else if(rx == dx && ry == dy)
		{
			// A sur B
			rv = 0;
		}
		else if(rx < dx && ry == dy)
		{
			// A à gauche de B
			angle = 0;
		}
		else if(rx > dx && ry == dy)
		{
			// A à droite de B
			angle = 180;
		}
		
		if(rd == 0) {
			
			follower.set_xMove(0);
			follower.set_yMove(0);
		}
		else {
			
			follower.changeDirection(angle);
		}
		
		if(rd < rv) rv = rd - (rd*10/100);
		if(rv < 1) rv = 0;
		goForwardControl(follower, rv);
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
	
	public Player attack(Vector<Player> attackers, Player pBall) {
		
		int en = pBall.en;
		
		System.out.println("EN " + pBall.toString() + " : " + en);
		
		for(Player attacker : attackers) {
			
			Random r = new Random();
			double percant = r.nextDouble() + 0.5;
			long at = Math.round(attacker.at * percant);
			
			en -= at;
			
			System.out.println("AT " + attacker.toString() + " : " + at);
			System.out.println("EN " + pBall.toString() + " : " + en);
			
			attackAnim(pBall, attacker);
			
			if(en <= 0)
			{
				return attacker;
			}
		}
		
		return pBall;
	}
	
	public void attackAnim(Player pBall, Player attacker) {
		
		double bx = pBall.get_xPosition();
		double by = pBall.get_yPosition();
		double ax = attacker.get_xPosition();
		double ay = attacker.get_yPosition();
		
		double dx = Math.abs(bx - ax) * 4;
		double dy = Math.abs(by - ay) * 4;
		
		double fx = (bx < ax) ? bx - dx : bx + dx;
		double fy = (by < by) ? by + dy : by - dy;
		
		double d = Math.sqrt(Math.pow(ax - fx, 2) + Math.pow(ay - fy, 2));
		long i = (long) (d / (attacker.sp / 10));
		
		for(long ii = 0; ii < i; ii++) {
			
			goFollowPlayer(attacker, fx, fy);
		}
		
		
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
		
		// qui a le ballon ?
		if(pMe.team == pBall.team) {
			
			// quelqu'un de ma team ! mais qui ?
			if(pMe == pBall) {
				
				// c'est moi !
				double d = Math.sqrt(Math.pow(tEnnemy.getPlayer(Team.GL).get_xPosition() - pBall.get_xPosition(), 2) + Math.pow(tEnnemy.getPlayer(Team.GL).get_yPosition() - pBall.get_yPosition(), 2));
				if(d/20 < pMe.sh * 70 / 100) {
					
					if(shoot(pBall, tEnnemy.getPlayer(Team.GL))) {
						
						System.out.println("GOAL !");
						
						if(tEnnemy.get_Position() == 0) tEnnemy.makeBlueTeam(); 
						else tEnnemy.makeRedTeam();
						
						if(tFriend.get_Position() == 0) tFriend.makeBlueTeam(); 
						else tFriend.makeRedTeam();
					}
					else {
						
						System.out.println("MISS !");
					}
				}
				
				goFollowPlayer(pMe, tEnnemy.getPlayer(Team.GL).get_xPosition(), tEnnemy.getPlayer(Team.GL).get_yPosition());
			}
			else {
				
				//pMe.set_xMove(pBall.get_xMove());
				//pMe.set_yMove(pBall.get_yMove());
				
				goFollowPlayer(pMe, pMe.get_xOriginPosition(), pMe.get_yOriginPosition());
			}
		}
		else {
			
			// quelqu'un de la team adverse !
			if(isFollow(pMe, pBall)) {
				
				goFollowPlayer(pMe, pBall.get_xPosition(), pBall.get_yPosition());
				return true;
			}
			else {
				
				goFollowPlayer(pMe, pMe.get_xOriginPosition(), pMe.get_yOriginPosition());
				return true;
			}
		}
		
		return false;
	}

	public boolean catchPositioning(Player pAttacker, Player playerBall, int i) {
		
		double pbx = playerBall.get_xPosition();
		double pby = playerBall.get_yPosition();
		
		double pax = pAttacker.get_xPosition();
		double pay = pAttacker.get_yPosition();
		
		// calcul de la position de l'attaquant par rapport à l'attaqué
		double posx = 0;
		double posy = 0;
		
		if(playerBall.team.get_Position() == 0) {
			// la team de gauche donc les attaquants à droite
			switch(i) {
				case 0:
				{
					goFollowPlayer(pAttacker, playerBall.get_xPosition() + playerBall.get_CaughtRadius() / 2, playerBall.get_yPosition());
					goForwardControl(pAttacker, pAttacker.sp / 10);
					
					return isNear(pAttacker, playerBall.get_xPosition() + playerBall.get_CaughtRadius() / 2, playerBall.get_yPosition());
				}
				case 1:
				{
					
				}
				case 2:
				{
					
				}
				case 3:
				{
					
				}
				case 4:
				{
					
				}
			}
		} 
		else {
			// la team de droite donc les attaquants à gauche
			
		}
		
		return false;
	}


	public boolean isNear(Player p, double x, double y) {
		
		if(p.get_xPosition() >= x - 1 && p.get_xPosition() <= x + 1
		&& p.get_yPosition() >= y - 1 && p.get_yPosition() <= y + 1) {
			
			return true;
		}
		
		return false;
	}
}
