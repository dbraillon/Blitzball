package com.dbraillon.blitzball;

import java.util.AbstractMap.SimpleEntry;
import java.util.Vector;

import com.dbraillon.blitzball.enumerations.Position;
import com.dbraillon.blitzball.enumerations.TeamPosition;

public class Team {

	public static final int PLAYER_COUNT = 6;
	
	private Vector<SimpleEntry<Position, Player>> players;
	private String teamName;
	private TeamPosition tPosition;
	
	
	public Team(String tName, TeamPosition tPosition) {
		
		players = new Vector<SimpleEntry<Position, Player>>();
		set_tPosition(tPosition);
	}
	
	public void makeBlueTeam() {
		
		teamName = "Blue team";
		
		Player leftShooter = new Player(132, 60, 10, 3, 3, 2, 10, 1, 5, 250, 150, Position.LF, this);
		Player rightShooter = new Player(150, 60, 11, 3, 3, 2, 13, 1, 5, 250, 450, Position.RF, this);
		Player middle = new Player(95, 60, 7, 5, 10, 5, 4, 1, 5, 200, 300, Position.MF, this);
		Player leftDefender = new Player(100, 63, 7, 10, 7, 5, 1, 1, 5, 100, 250, Position.LD, this); 
		Player rightDefender = new Player(105, 60, 3, 10, 6, 2, 1, 1, 5, 100, 350, Position.RD, this);
		Player goalkeeper = new Player(90, 54, 4, 2, 2, 4, 1, 5, 5, 10, 300, Position.GL, this);
		
		players.add(new SimpleEntry<Position, Player>(Position.LF, leftShooter));
		players.add(new SimpleEntry<Position, Player>(Position.RF, rightShooter));
		players.add(new SimpleEntry<Position, Player>(Position.MF, middle));
		players.add(new SimpleEntry<Position, Player>(Position.LD, leftDefender));
		players.add(new SimpleEntry<Position, Player>(Position.LD, rightDefender));
		players.add(new SimpleEntry<Position, Player>(Position.GL, goalkeeper));
	}
	public void makeRedTeam() {
		
		teamName = "Red team";
		
		Player leftShooter = new Player(329, 60, 16, 5, 5, 4, 10, 1, 5, 350, 150, Position.LF, this);
		Player rightShooter = new Player(274, 60, 17, 5, 3, 2, 9, 1, 5, 350, 450, Position.RF, this);
		Player middle = new Player(389, 60, 20, 7, 11, 5, 4, 1, 5, 400, 300, Position.MF, this);
		Player leftDefender = new Player(230, 60, 14, 9, 7, 8, 1, 1, 5, 500, 250, Position.LD, this); 
		Player rightDefender = new Player(214, 60, 11, 12, 7, 4, 1, 1, 5, 500, 350, Position.RD, this);
		Player goalkeeper = new Player(339, 60, 15, 2, 2, 7, 1, 9, 5, 590, 300, Position.GL, this);
		
		players.add(new SimpleEntry<Position, Player>(Position.LF, leftShooter));
		players.add(new SimpleEntry<Position, Player>(Position.RF, rightShooter));
		players.add(new SimpleEntry<Position, Player>(Position.MF, middle));
		players.add(new SimpleEntry<Position, Player>(Position.LD, leftDefender));
		players.add(new SimpleEntry<Position, Player>(Position.LD, rightDefender));
		players.add(new SimpleEntry<Position, Player>(Position.GL, goalkeeper));
	}
	
	public Player getPlayer(Position position) {
		
		for (SimpleEntry<Position, Player> entry : players) {
			
			if(entry.getKey().equals(position)) {
				
				return entry.getValue();
			}
		}
		
		return null;
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
