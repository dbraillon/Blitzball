package com.dbraillon.blitzball;

import java.util.Vector;

public class Team {

	public static final int PLAYER_COUNT = 6;
	
	public static final int LF = 0;
	public static final int RF = 1;
	public static final int MF = 2;
	public static final int LD = 3;
	public static final int RD = 4;
	public static final int GL = 5;
	
	private Vector<Player> _players;
	
	public Team() {
		
		_players = new Vector<Player>();
	}
	
	
	public void makeBlueTeam() {
		
		// shooter
		Player leftShooter = new Player(132, 60, 10, 3, 3, 2, 10, 1);
		leftShooter.set_position(250, 150);
		_players.add(leftShooter);
		
		Player rightShooter = new Player(150, 60, 11, 3, 3, 2, 13, 1);
		rightShooter.set_position(250, 450);
		_players.add(rightShooter);
		
		// middle
		Player middle = new Player(95, 60, 7, 5, 10, 5, 4, 1);
		middle.set_position(200, 300);
		_players.add(middle);
		
		// defender
		Player leftDefender = new Player(100, 63, 7, 10, 7, 5, 1, 1); 
		leftDefender.set_position(150, 150);
		_players.add(leftDefender);
		
		Player rightDefender = new Player(105, 60, 3, 10, 6, 2, 1, 1);
		rightDefender.set_position(150, 450);
		_players.add(rightDefender);
		
		// goalkeeper
		Player goalkeeper = new Player(90, 54, 4, 2, 2, 4, 1, 5); 
		goalkeeper.set_position(10, 300);
		_players.add(goalkeeper);
	}
	
	public void makeRedTeam() {
		
		// shooter
		Player leftShooter = new Player(329, 40, 16, 5, 5, 4, 10, 1);
		leftShooter.set_position(350, 150);
		_players.add(leftShooter);
		
		Player rightShooter = new Player(274, 40, 17, 5, 3, 2, 9, 1);
		rightShooter.set_position(350, 450);
		_players.add(rightShooter);
		
		// middle
		Player middle = new Player(389, 40, 20, 7, 11, 5, 4, 1);
		middle.set_position(400, 300);
		_players.add(middle);
		
		// defender
		Player leftDefender = new Player(230, 40, 14, 9, 7, 8, 1, 1); 
		leftDefender.set_position(450, 150);
		_players.add(leftDefender);
		
		Player rightDefender = new Player(214, 40, 11, 12, 7, 4, 1, 1);
		rightDefender.set_position(450, 450);
		_players.add(rightDefender);
		
		// goalkeeper
		Player goalkeeper = new Player(339, 40, 15, 2, 2, 7, 1, 9); 
		goalkeeper.set_position(590, 300);
		_players.add(goalkeeper);
	}
	
	public Player getPlayer(int position) {
		
		return _players.elementAt(position);
	}
}
