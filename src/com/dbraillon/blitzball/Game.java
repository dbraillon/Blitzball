package com.dbraillon.blitzball;

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
	
	private Vector<Player> catchers;
	
	private States state;
	private enum States {
		NORMAL, PASS, SHOOT, CAUGHT, ATTACK
	}
	
	private Player attacker;
	
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
			
			graphics.fillOval((float) p.get_xPosition() - p.get_PlayerRadius() / 2, (float) p.get_yPosition() - p.get_PlayerRadius() / 2,
					p.get_PlayerRadius(), p.get_PlayerRadius());
			graphics.drawOval((float) p.get_xPosition() - p.get_CaughtRadius() / 2, (float) p.get_yPosition() - p.get_CaughtRadius() / 2,
					p.get_CaughtRadius(), p.get_CaughtRadius());
			graphics.drawOval((float) p.get_xPosition() - p.get_reflexRadius() / 2, (float) p.get_yPosition() - p.get_reflexRadius() / 2,
					p.get_reflexRadius(), p.get_reflexRadius());
			graphics.drawOval((float) p.get_xPosition() - p.get_FollowRadius() / 2, (float) p.get_yPosition() - p.get_FollowRadius() / 2,
					p.get_FollowRadius(), p.get_FollowRadius());
			
			p = blueTeam.getPlayer(i);
			
			if(playerBall != p) {
				
				graphics.setColor(new Color(0, 0, 255));
			}
			else {
				
				graphics.setColor(new Color(0, 255, 0));
			}
			
			graphics.fillOval((float) p.get_xPosition() - p.get_PlayerRadius() / 2, (float) p.get_yPosition() - p.get_PlayerRadius() / 2,
					p.get_PlayerRadius(), p.get_PlayerRadius());
			graphics.drawOval((float) p.get_xPosition() - p.get_reflexRadius() / 2, (float) p.get_yPosition() - p.get_reflexRadius() / 2,
					p.get_reflexRadius(), p.get_reflexRadius());
			graphics.drawOval((float) p.get_xPosition() - p.get_FollowRadius() / 2, (float) p.get_yPosition() - p.get_FollowRadius() / 2,
					p.get_FollowRadius(), p.get_FollowRadius());
		}
		
		if(state == States.CAUGHT || state == States.ATTACK) {
			
			int i = 0;
			
			graphics.drawString(playerBall.toString() + " EN : " + playerBall.en, 550, 50);
			
			for(Player pAttacker : catchers) {
				
				graphics.drawString(pAttacker.toString() + " AT : " + pAttacker.at, 550, 70 + 20 * i);
				i++;
			}
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		stadium = new Stadium();
		
		redTeam = new Team("Red team", 1);
		redTeam.makeRedTeam();
		
		blueTeam = new Team("Blue team", 0);
		blueTeam.makeBlueTeam();
		
		playerController = new PlayerController(stadium);
		
		playerBall = blueTeam.getPlayer(Team.MF);
		
		state = States.NORMAL;
	}
	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		switch (state)
		{
			case SHOOT:
			{
				break;
			}
			case PASS:
			{
				break;
			}
			case ATTACK:
			{
				if(attacker == null) {
					
					if(catchers.size() > 0) {
					
						attacker = catchers.firstElement();
					}
					else {
						
						state = States.NORMAL;
					}
				}
				else
				{
					if(playerController.attackAnim(playerBall, attacker)) {
						
						if(playerController.attack(attacker, playerBall) == attacker) {
							
							playerBall = attacker;
						}
						
						attacker = null;
					}
				}
				
				break;
			}
			case CAUGHT:
			{
				int i = 0;
				Vector<Boolean> inPosition = new Vector<Boolean>();
				
				for(Player pAttacker : catchers) {
					
					inPosition.add(playerController.catchPositioning(pAttacker, playerBall, i));
					i++;
				}
				
				if(!inPosition.contains(Boolean.FALSE)) {
					
					state = States.ATTACK; 
				}
					
				
				break;
			}
			case NORMAL:
			{
				for(int i = 0; i < Team.PLAYER_COUNT; i++)
				{
					Player redPlayer = redTeam.getPlayer(i);
					Player bluePlayer = blueTeam.getPlayer(i);
					
					if(redPlayer == playerBall) {
						
						if((catchers = playerController.isCaught(playerBall, blueTeam)).size() > 0) {
							
							state = States.CAUGHT;
							System.out.println(playerBall.toString() + " caught by " + catchers.size() + " player(s).");
						}
						
						/*
						if(catchers.size() > 0) {
							
							playerBall = playerController.attack(catchers, playerBall);
						}
						*/
					}
					
					if(bluePlayer == playerBall) {
						
						if((catchers = playerController.isCaught(playerBall, redTeam)).size() > 0) {
							
							state = States.CAUGHT;
							System.out.println(playerBall.toString() + " caught by " + catchers.size() + " player(s).");
						}
						
						/*
						if(catchers.size() > 0) {
							playerBall = playerController.attack(catchers, playerBall);
						}
						*/
					}
					
					if(!playerController.makeADecision(redPlayer, playerBall, redTeam, blueTeam)) {
						
						playerController.goForwardControl(redPlayer, redPlayer.sp / 10);
					}
					
					if(!playerController.makeADecision(bluePlayer, playerBall, blueTeam, redTeam)) {
						
						playerController.goForwardControl(bluePlayer, bluePlayer.sp / 10);
					}
					
					/*playerController.goFollowPlayer(redPlayer, bluePlayer);
					
					if(!playerController.goForwardControl(bluePlayer)) {
						
						Random r = new Random();
						int direction = r.nextInt(360);
						
						bluePlayer.changeDirection(direction);
					}*/
				}
				
				break;
			}
		}
		
		/** 
		 * en travaux
		 * 
		 * si quelqu'un fait une passe
		 *   
		 * sinon si quelqu'un fait un tir
		 * 
		 * sinon si quelqu'un est attrapé
		 *   
		 *   affichage des bloqueurs et du bloqué (stats)
		 *   les bloqueurs avancent devant le porteur de ballon à une vitesse eleve
		 *   si ils ont tous atteint leur point
		 *     passage en mode tacle
		 * 
		 * sinon si quelqu'un tacle
		 * 
		 * 	 si personne n'attaque
		 *     récupération du plus gros AT
		 *   
		 *   si personne n'attaque
		 *     passage en mode normal
		 *     on passe le reste
		 *     
		 *   l'attaquant avance derriere le bloqué
		 *   si l'attaquant est sur le bloqué pour la premiere fois
		 *     si EN - AT <= 0
		 *       la balle passe à l'attaquant et le reflex du bloqué passe à 0
		 *       les autres attaquant ont leur reflex au max
		 *   si l'attaquant est à sa destination
		 *     attaquant = null
		 *     reflex de l'attaquant = 0
		 *     attaquant enlevé de la liste des attaquants 
		 *     
		 * sinon
		 * 	 
		 *   boucle sur les joueurs
		 *     
		 *     si le joueur a le ballon
		 *       recuperation des joueurs adverse qui le bloque
		 *       si le joueur est bloqué
		 *         on passe la liste des bloqueurs en globale
		 *         passage en mode attrapé
		 *         on passe le reste
		 *     
		 *     si le joueur n'a aucune décision ou si le joueur
		 *     a atteint son seuil de reflex
		 *       il prend une décision
		 *       seuil de reflex remis à zéro
		 *     
		 *     incrémentation du reflex du joueur
		 *     
		 *     
		 *     le joueur avance si il en a besoin
		 * 
		 */	
	}

	public static void main(String[] args) throws SlickException {
		
		AppGameContainer app = new AppGameContainer(new Game("Blitzball"));
		app.setDisplayMode(800, 600, false);
		app.setTargetFrameRate(5);
		app.start();
	}
}
