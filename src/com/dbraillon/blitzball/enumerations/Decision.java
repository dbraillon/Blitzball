package com.dbraillon.blitzball.enumerations;

import com.dbraillon.blitzball.models.Player;

public class Decision {

	public DecisionType decisionType;
	public Player player;
	public float x, y;
	
	public Decision(DecisionType decisionType) {
		
		this.decisionType = decisionType;
	}
	
	public Decision(DecisionType decisionType, Player player) {
		
		this.decisionType = decisionType;
		this.player = player;
		this.x = player.getCurrentPosition().getX();
		this.y = player.getCurrentPosition().getY();
	}
	
	public Decision(DecisionType decisionType, float x, float y) {
		
		this.decisionType = decisionType;
		this.x = x;
		this.y = y;
	}
	
	
	@Override
	public String toString() {
		
		return decisionType.toString();
	}
}
