package com.dbraillon.blitzball.enumerations;

import com.dbraillon.blitzball.Player;

public class Decision {

	public DecisionType decisionType;
	public Player player;
	public double x, y;
	
	public Decision(DecisionType decisionType) {
		
		this.decisionType = decisionType;
	}
	
	public Decision(DecisionType decisionType, Player player) {
		
		this.decisionType = decisionType;
		this.player = player;
	}
	
	public Decision(DecisionType decisionType, double x, double y) {
		
		this.decisionType = decisionType;
		this.x = x;
		this.y = y;
	}
}
