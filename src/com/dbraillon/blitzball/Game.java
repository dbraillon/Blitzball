package com.dbraillon.blitzball;

import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	private Stadium stadium;
	private Team redTeam, blueTeam;
	
	private PlayerController playerController;
	
	
	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer arg0, Graphics graphics) throws SlickException {
		
		graphics.setBackground(new Color(255, 255, 255));
		
		graphics.setColor(new Color(0, 0, 0));
		graphics.drawOval(0, 0, stadium.get_totalRadius(), stadium.get_totalRadius());
		
		for(int i = 0; i < Team.PLAYER_COUNT; i++) {
			
			graphics.setColor(new Color(255, 0, 0));
			graphics.fillOval((float) redTeam.getPlayer(i).get_xPosition() - redTeam.getPlayer(i).get_radius() / 2, (float) redTeam.getPlayer(i).get_yPosition() - redTeam.getPlayer(i).get_radius() / 2,
					redTeam.getPlayer(i).get_radius(), redTeam.getPlayer(i).get_radius());
			
			graphics.setColor(new Color(0, 0, 255));
			graphics.fillOval((float) blueTeam.getPlayer(i).get_xPosition() - blueTeam.getPlayer(i).get_radius() / 2, (float) blueTeam.getPlayer(i).get_yPosition() - blueTeam.getPlayer(i).get_radius() / 2,
					blueTeam.getPlayer(i).get_radius(), blueTeam.getPlayer(i).get_radius());
		}
		
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		stadium = new Stadium();
		
		redTeam = new Team();
		redTeam.makeRedTeam();
		
		blueTeam = new Team();
		blueTeam.makeBlueTeam();
		
		playerController = new PlayerController(stadium);
	}
	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		for(int i = 0; i < Team.PLAYER_COUNT; i++)
		{
			Player redPlayer = redTeam.getPlayer(i);
			Player bluePlayer = blueTeam.getPlayer(i);
			
			playerController.goFollowPlayer(redPlayer, bluePlayer);
			
			if(!playerController.goForwardControl(bluePlayer)) {
				
				Random r = new Random();
				int direction = r.nextInt(360);
				
				bluePlayer.changeDirection(direction);
			}
		}	
	}

	public static void main(String[] args) throws SlickException {
		
		AppGameContainer app = new AppGameContainer(new Game("Blitzball"));
		app.setDisplayMode(800, 600, false);
		app.setTargetFrameRate(30);
		app.start();
	}
}
