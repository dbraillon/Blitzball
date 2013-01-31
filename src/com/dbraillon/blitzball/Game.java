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
	private Player playerBall;
	
	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer arg0, Graphics graphics) throws SlickException {
		
		graphics.setBackground(new Color(255, 255, 255));
		
		graphics.setColor(new Color(0, 0, 0));
		graphics.drawOval(0, 0, stadium.get_totalRadius(), stadium.get_totalRadius());
		
		for(int i = 0; i < Team.PLAYER_COUNT; i++) {
			
			Player p = redTeam.getPlayer(i);
			
			if(playerBall != p) {
				
				graphics.setColor(new Color(255, 0, 0));
			}
			else {
				
				graphics.setColor(new Color(0, 255, 0));
			}
			
			graphics.fillOval((float) p.get_xPosition() - p.get_radius() / 2, (float) p.get_yPosition() - p.get_radius() / 2,
					p.get_radius(), p.get_radius());
			graphics.drawOval((float) p.get_xPosition() - p.get_reflexRadius() / 2, (float) p.get_yPosition() - p.get_reflexRadius() / 2,
					p.get_reflexRadius(), p.get_reflexRadius());
			
			p = blueTeam.getPlayer(i);
			
			if(playerBall != p) {
				
				graphics.setColor(new Color(0, 0, 255));
			}
			else {
				
				graphics.setColor(new Color(0, 255, 0));
			}
			
			graphics.fillOval((float) p.get_xPosition() - p.get_radius() / 2, (float) p.get_yPosition() - p.get_radius() / 2,
					p.get_radius(), p.get_radius());
			graphics.drawOval((float) p.get_xPosition() - p.get_reflexRadius() / 2, (float) p.get_yPosition() - p.get_reflexRadius() / 2,
					p.get_reflexRadius(), p.get_reflexRadius());
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
		
		playerBall = blueTeam.getPlayer(Team.MF);
	}
	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		for(int i = 0; i < Team.PLAYER_COUNT; i++)
		{
			Player redPlayer = redTeam.getPlayer(i);
			Player bluePlayer = blueTeam.getPlayer(i);
			
			if(redPlayer == playerBall) {
				
				Vector<Player> catchers = null;
				if((catchers = playerController.isCaught(playerBall, blueTeam)).size() > 0) {
					
					System.out.println(playerBall.toString() + " caught by " + catchers.size() + " player(s).");
				}
				
				if(catchers.size() > 0) {
					playerBall = playerController.attack(catchers, playerBall);
				}
			}
			
			if(bluePlayer == playerBall) {
				
				Vector<Player> catchers = null;
				if((catchers = playerController.isCaught(playerBall, redTeam)).size() > 0) {
					
					System.out.println(playerBall.toString() + " caught by " + catchers.size() + " player(s).");
				}
				
				if(catchers.size() > 0) {
					playerBall = playerController.attack(catchers, playerBall);
				}
			}
			
			
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
