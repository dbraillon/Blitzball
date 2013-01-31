package com.dbraillon.blitzball;

import java.util.Vector;

public class PlayerController {

	private Stadium _stadium;
	
	public PlayerController(Stadium stadium) {
		
		_stadium = stadium;
	}
	
	public boolean goForwardControl(Player player) {
		
		boolean b = true;
		
		double v = player.sp / 10;
		double sr = _stadium.get_radius() / 2;
		
		// positition actuelle
		double xc = player.get_xPosition();
		double yc = player.get_yPosition();
		double dc = Math.sqrt(Math.pow(xc - sr,  2) + Math.pow(yc - sr, 2));
		
		// position future
		double xf = xc + player.xMove * v;
		double yf = yc + player.yMove * v;
		double df = Math.sqrt(Math.pow(xf - sr, 2) + Math.pow(yf - sr, 2));
		
		v = (df >= sr) ? sr - dc : v;
		
		if(v < 1)
		{
			b = false;
		}
		
		player.goForward(v);
		
		return b;
	}
	
	public void goFollowPlayer(Player follower, Player followed)
	{
		double rv = follower.sp / 10;
		double dv = followed.sp / 10;
		
		double rx = follower.get_xPosition();
		double ry = follower.get_yPosition();
		double dx = followed.get_xPosition();
		double dy = followed.get_yPosition();
		
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
		
		follower.changeDirection(angle);
		follower.goForward(rv);
	}

	public Vector<Player> isCaught(Player player, Team team) {

		Vector<Player> catchers = new Vector<Player>();
		
		for(int i = 0; i < team.PLAYER_COUNT; i++) {
			
			Player p = team.getPlayer(i);
			double d = Math.sqrt(Math.pow(p.get_xPosition() - player.get_xPosition(), 2) + Math.pow(p.get_yPosition() - player.get_yPosition(), 2));
			if(d < p.get_reflexRadius()) {
				
				catchers.add(p);
			}
		}
		
		return catchers;
	}
}
