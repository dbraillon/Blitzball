package com.dbraillon.blitzball;

import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	private Stadium stadium;
	private Vector<Player> followerPlayers, followedPlayers;
	private PlayerController playerController;
	
	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer arg0, Graphics graphics) throws SlickException {
		
		graphics.drawOval(0, 0, stadium.get_totalRadius(), stadium.get_totalRadius());
		
		for(int i = 0; i < 500; i++) {
			
			graphics.drawOval((float) followerPlayers.elementAt(i).get_xPosition() - followerPlayers.elementAt(i).get_radius() / 2, (float) followerPlayers.elementAt(i).get_yPosition() - followerPlayers.elementAt(i).get_radius() / 2,
					followerPlayers.elementAt(i).get_radius(), followerPlayers.elementAt(i).get_radius());
			
			graphics.drawOval((float) followedPlayers.elementAt(i).get_xPosition() - followedPlayers.elementAt(i).get_radius() / 2, (float) followedPlayers.elementAt(i).get_yPosition() - followedPlayers.elementAt(i).get_radius() / 2,
					followedPlayers.elementAt(i).get_radius() * 2, followedPlayers.elementAt(i).get_radius() * 2);
		}
		
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		stadium = new Stadium();
		followerPlayers = new Vector<Player>();
		followedPlayers = new Vector<Player>();
		playerController = new PlayerController(stadium);
		
		for(int i = 0; i < 500; i++)
		{
			followerPlayers.add(new Player(200, 300, 4));
			followedPlayers.add(new Player(300, 200, 5));
		}
	}
	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		
		for(int i = 0; i < 500; i++)
		{
			Player p1 = followerPlayers.elementAt(i);
			Player p2 = followedPlayers.elementAt(i);
			
			playerController.goFollowPlayer(p1, p2);
			
			if(!playerController.goForwardControl(p2)) {
				
				Random r = new Random();
				int direction = r.nextInt(360);
				
				p2.changeDirection(direction);
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
