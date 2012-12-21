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
			
			double v = 1;
			double sr = stadium.get_radius() / 2;
			
			// positition actuelle
			double xc = player.get_xPosition();
			double yc = player.get_yPosition();
			double dc = Math.sqrt(Math.pow(xc - sr,  2) + Math.pow(yc - sr, 2));
			
			// position future
			double xf = xc + player.xMove * v;
			double yf = yc + player.yMove * v;
			double df = Math.sqrt(Math.pow(xf - sr, 2) + Math.pow(yf - sr, 2));
			
			/*graphics.drawString("Centre du stade [" + stadium.get_radius() + ", " + stadium.get_radius() + "]", 10, 30);
			graphics.drawString("Centre du joueur [" + xc + ", " + yc + "]", 10, 50);
			graphics.drawString("Future centre du joueur [" + xf + ", " + yf + "]", 10, 70);
			graphics.drawString("Distance du centre " + dc, 10, 90);
			graphics.drawString("Future distance du centre " + df, 10, 110);*/
			
			v = (df >= sr) ? sr - dc : v;
			
			if(v < 1)
			{
				Random r = new Random();
				
				player.changeDirection(r.nextInt(360));
			}
			
			//graphics.drawString("Velocité " + v, 10, 130);
			
			player.goForward(v);
			
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
			
			Player p = new Player(stadium.get_radius() / 2, stadium.get_radius() / 2);
			players.add(p);
		}
	}
double _f;
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		for(Player player : players) {
			
			
			
			
			
			/*if(!playerController.goForwardControl(player, 1)) {
				
				Random r = new Random();
				int direction = r.nextInt(360);
				
				//player.changeDirection(direction);
			}*/
		}
	}

	public static void main(String[] args) throws SlickException {
		
		AppGameContainer app = new AppGameContainer(new Game("Blitzball"));
		app.setDisplayMode(800, 600, false);
		app.setTargetFrameRate(30);
		app.start();
	}
}
