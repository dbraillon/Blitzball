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
	private Vector<Player> players;
	private PlayerController playerController;
	
	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer arg0, Graphics graphics) throws SlickException {
		
		graphics.drawOval(0, 0, stadium.get_totalRadius(), stadium.get_totalRadius());
		
		for(Player player : players) {
			
			graphics.drawOval((float) player.get_xPosition() - player.get_radius() / 2, (float) player.get_yPosition() - player.get_radius() / 2,
					player.get_radius(), player.get_radius());
		}
		
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		stadium = new Stadium();
		players = new Vector<Player>();
		playerController = new PlayerController(stadium);
		
		players.add(new Player(200, 300, 4));
		players.add(new Player(300, 200, 5));
		
	}
	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		Player p1 = players.elementAt(0);
		Player p2 = players.elementAt(1);
		
		playerController.goFollowPlayer(p1, p2);
		
		if(!playerController.goForwardControl(p2)) {
			
			Random r = new Random();
			int direction = r.nextInt(360);
			
			p2.changeDirection(direction);
		}
	}

	public static void main(String[] args) throws SlickException {
		
		AppGameContainer app = new AppGameContainer(new Game("Blitzball"));
		app.setDisplayMode(800, 600, false);
		app.setTargetFrameRate(30);
		app.start();
	}
}
