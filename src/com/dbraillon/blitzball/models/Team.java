package com.dbraillon.blitzball.models;

import java.util.AbstractMap.SimpleEntry;
import java.util.Vector;

import com.dbraillon.blitzball.enumerations.PlayerPosition;
import com.dbraillon.blitzball.enumerations.TeamPosition;

public class Team {

	public static final int PLAYER_COUNT = 6;
	
	private Vector<SimpleEntry<PlayerPosition, Player>> players;
	private String teamName;
	private TeamPosition tPosition;
	
	
	public Team(String tName) {
		
		this.teamName = tName;
		this.players = new Vector<SimpleEntry<PlayerPosition, Player>>();
	}
	
	public void makeLeftTeam() {
		
		teamName = "Blue team";
		
		Player leftShooter = new Player(132, 60, 10, 3, 3, 2, 10, 1, 5, 250, 150, PlayerPosition.LF, this);
		Player rightShooter = new Player(150, 60, 11, 3, 3, 2, 13, 1, 5, 250, 550, PlayerPosition.RF, this);
		Player middle = new Player(95, 60, 7, 5, 10, 5, 4, 1, 5, 200, 350, PlayerPosition.MF, this);
		Player leftDefender = new Player(100, 63, 7, 10, 7, 5, 1, 1, 5, 100, 300, PlayerPosition.LD, this); 
		Player rightDefender = new Player(105, 60, 3, 10, 6, 2, 1, 1, 5, 100, 400, PlayerPosition.RD, this);
		Player goalkeeper = new Player(90, 54, 4, 2, 2, 4, 1, 5, 5, 10, 350, PlayerPosition.GL, this);
		
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.LF, leftShooter));
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.RF, rightShooter));
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.MF, middle));
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.LD, leftDefender));
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.RD, rightDefender));
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.GL, goalkeeper));
		
		tPosition = TeamPosition.LEFT;
	}
	
	public void makeRightTeam() {
		
		teamName = "Red team";
		
		Player leftShooter = new Player(329, 60, 16, 5, 5, 4, 10, 1, 5, 450, 150, PlayerPosition.LF, this);
		Player rightShooter = new Player(274, 60, 17, 5, 3, 2, 9, 1, 5, 450, 550, PlayerPosition.RF, this);
		Player middle = new Player(389, 60, 20, 7, 11, 5, 4, 1, 5, 500, 350, PlayerPosition.MF, this);
		Player leftDefender = new Player(230, 60, 14, 9, 7, 8, 1, 1, 5, 600, 300, PlayerPosition.LD, this); 
		Player rightDefender = new Player(214, 60, 11, 12, 7, 4, 1, 1, 5, 600, 400, PlayerPosition.RD, this);
		Player goalkeeper = new Player(339, 60, 15, 2, 2, 7, 1, 9, 5, 690, 350, PlayerPosition.GL, this);
		
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.LF, leftShooter));
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.RF, rightShooter));
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.MF, middle));
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.LD, leftDefender));
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.RD, rightDefender));
		players.add(new SimpleEntry<PlayerPosition, Player>(PlayerPosition.GL, goalkeeper));
		
		tPosition = TeamPosition.RIGHT;
	}
	
	public Player getPlayer(PlayerPosition position) {
		
		for (SimpleEntry<PlayerPosition, Player> entry : players) {
			
			if(entry.getKey().equals(position)) {
				
				return entry.getValue();
			}
		}
		
		return null;
	}
	public Player getPlayer(int i) {
		
		return players.get(i).getValue();
	}
	
	@Override
	public String toString() {
		
		return getName();
	}


	public String getName() {
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
