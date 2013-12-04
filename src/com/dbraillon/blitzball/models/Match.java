package com.dbraillon.blitzball.models;

import com.dbraillon.blitzball.enumerations.TeamPosition;

public class Match {

	private Team leftTeam, rightTeam;
	
	public Match() {
		
		leftTeam = new Team("Left team", TeamPosition.LEFT);
		leftTeam.makeRedTeam();
		
		rightTeam = new Team("Right team", TeamPosition.RIGHT);
		rightTeam.makeBlueTeam();
	}

	public Team getLeftTeam() {
		return leftTeam;
	}

	public void setLeftTeam(Team leftTeam) {
		this.leftTeam = leftTeam;
	}

	public Team getRightTeam() {
		return rightTeam;
	}

	public void setRightTeam(Team rightTeam) {
		this.rightTeam = rightTeam;
	}
}
