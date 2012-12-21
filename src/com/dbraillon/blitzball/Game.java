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
		
		for(int i = 0; i < 1000; i++) {
			
			Player p = new Player(stadium.get_radius() / 2, stadium.get_radius() / 2, 10);
			players.add(p);
		}
	}
	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		for(Player player : players) {
			
			if(!playerController.goForwardControl(player)) {
				
				Random r = new Random();
				int direction = r.nextInt(360);
				
				player.changeDirection(direction);
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
