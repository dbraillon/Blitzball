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
}
