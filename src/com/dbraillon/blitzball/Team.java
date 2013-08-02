package com.dbraillon.blitzball;

import java.util.Vector;

import com.dbraillon.blitzball.enumerations.TeamPosition;

public class Team {

	public static final int PLAYER_COUNT = 6;
	
	public static final int LF = 0;
	public static final int RF = 1;
	public static final int MF = 2;
	public static final int LD = 3;
	public static final int RD = 4;
	public static final int GL = 5;
	
	
	private Vector<Player> players;
	private String teamName;
	private TeamPosition tPosition;
	
	
	public Team(String tName, TeamPosition tPosition) {
		
		players = new Vector<Player>();
		set_tPosition(tPosition);
	}
	
	
	public void makeBlueTeam() {
		
		teamName = "Blue team";
		
		// shooter
		Player leftShooter = new Player(132, 60, 10, 3, 3, 2, 10, 1, 3, 250, 150, LF, this);
		players.add(leftShooter);
		
		Player rightShooter = new Player(150, 60, 11, 3, 3, 2, 13, 1, 3, 250, 450, RF, this);
		players.add(rightShooter);
		
		// middle
		Player middle = new Player(95, 60, 7, 5, 10, 5, 4, 1, 3, 200, 300, MF, this);
		players.add(middle);
		
		// defender
		Player leftDefender = new Player(100, 63, 7, 10, 7, 5, 1, 1, 3, 100, 250, LD, this); 
		players.add(leftDefender);
		
		Player rightDefender = new Player(105, 60, 3, 10, 6, 2, 1, 1, 3, 100, 350, RD, this);
		players.add(rightDefender);
		
		// goalkeeper
		Player goalkeeper = new Player(90, 54, 4, 2, 2, 4, 1, 5, 3, 10, 300, GL, this); 
		players.add(goalkeeper);
	}
	
	public void makeRedTeam() {
		
		teamName = "Red team";
		
		// shooter
		Player leftShooter = new Player(329, 40, 16, 5, 5, 4, 10, 1, 3, 350, 150, LF, this);
		players.add(leftShooter);
		
		Player rightShooter = new Player(274, 40, 17, 5, 3, 2, 9, 1, 3, 350, 450, RF, this);
		players.add(rightShooter);
		
		// middle
		Player middle = new Player(389, 40, 20, 7, 11, 5, 4, 1, 3, 400, 300, MF, this);
		players.add(middle);
		
		// defender
		Player leftDefender = new Player(230, 40, 14, 9, 7, 8, 1, 1, 3, 500, 250, LD, this); 
		players.add(leftDefender);
		
		Player rightDefender = new Player(214, 40, 11, 12, 7, 4, 1, 1, 3, 500, 350, RD, this);
		players.add(rightDefender);
		
		// goalkeeper
		Player goalkeeper = new Player(339, 40, 15, 2, 2, 7, 1, 9, 3, 590, 300, GL, this); 
		players.add(goalkeeper);
	}
	
	public Player getPlayer(int position) {
		
		return players.elementAt(position);
	}
	
	@Override
	public String toString() {
		
		return get_TeamName();
	}


	public String get_TeamName() {
		return teamName;
	}


	public void set_TeamName(String teamName) {
		this.teamName = teamName;
	}


	public TeamPosition get_tPosition() {
		return tPosition;
	}


	public void set_tPosition(TeamPosition tPosition) {
		this.tPosition = tPosition;
	}
}
