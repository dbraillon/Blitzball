package com.dbraillon.blitzball.models;

import com.dbraillon.math.Point;

public class Ball {

	private Player carrier;
	
	public Ball(Player initialCarrier) {
		
		carrier = initialCarrier;
	}
	
	public void setPlayer(Player newCarrier) {
		
		carrier = newCarrier;
	}
	
	public Point getPosition() {
		
		return carrier.getCurrentPosition();
	}
}
