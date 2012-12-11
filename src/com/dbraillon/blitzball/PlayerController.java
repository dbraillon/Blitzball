package com.dbraillon.blitzball;

public class PlayerController {

	private Stadium _stadium;
	
	public PlayerController(Stadium stadium) {
		
		_stadium = stadium;
	}
	
	public boolean goForwardControl(Player player, double velocity) {
		
		double stadiumRadius = _stadium.get_radius();
		double currentPlayerDistanceFromCenter = Math.sqrt(Math.pow(player.get_xPosition() - stadiumRadius, 2) + Math.pow(player.get_yPosition() - stadiumRadius, 2)) + player.get_radius() + 1;
		
		double xPositionFuture = player.get_xPosition() + player.xMove * velocity;
		double yPositionFuture = player.get_yPosition() + player.yMove * velocity;
		
		double futurePlayerDistanceFromCenter = Math.sqrt(Math.pow(xPositionFuture - stadiumRadius, 2) + Math.pow(yPositionFuture - stadiumRadius, 2));
		
		
		if(stadiumRadius < futurePlayerDistanceFromCenter) {
			
			player.goForward(stadiumRadius - currentPlayerDistanceFromCenter);
			return false;
		}
		
		player.goForward(velocity);
		return true;
	}
}
