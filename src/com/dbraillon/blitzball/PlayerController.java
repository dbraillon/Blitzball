package com.dbraillon.blitzball;

import java.util.Random;

public class PlayerController {

	private Stadium _stadium;
	
	public PlayerController(Stadium stadium) {
		
		_stadium = stadium;
	}
	
	public boolean goForwardControl(Player player) {
		
		boolean b = true;
		
		double v = player.velocity;
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
		double rv = follower.velocity;
		double dv = followed.velocity;
		
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
			angle = 180 - angle;//OK
		}
		if(rx < dx && ry > dy)
		{
			//angle = angle;
		}
		if(rx > dx && ry < dy)
		{
			angle = 180 + angle;
		}
		if(rx < dx && ry < dy)
		{
			angle = 270 + angle;
		}
		
		if(rx == dx && ry > dy)
		{
			angle = 90;
		}
		if(rx == dx && ry < dy)
		{
			angle = 270;
		}
		if(rx == dx && ry == dy)
		{
			rv = 0;
		}
		
		if(rx < dx && ry == dy)
		{
			angle = 0;
		}
		if(rx > dx && ry == dy)
		{
			angle = 180;
		}
		
		follower.changeDirection(angle);
		follower.goForward(rv);
	}
}
