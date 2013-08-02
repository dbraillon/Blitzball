package com.dbraillon.blitzball;

import java.util.Vector;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.dbraillon.blitzball.enumerations.State;
import com.dbraillon.blitzball.enumerations.TeamPosition;

public class Game extends BasicGame {

	private static final int WIDTH = 800, HEIGHT = 600, FRAME_RATE = 5;
	private static final String TITLE = "Blitzball";
	
	private Stadium stadium;
	private Team redTeam, blueTeam;
	
	private PlayerController pController;
	private Player pBall;
	
	private State state;
	
	// utilisé dans l'état ATTAQUE
	private Vector<Player> pAttackers;
	private Player pAttacker;
	private int tEndurance;
	
	
	public Game(String title) {
		super(title);
	}

	/*
	 * (non-Javadoc)
	 * First init then render and update
	 */
	
	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		trace("Start init phase");
		
		trace("Create stadium");
		stadium = new Stadium();
		
		trace("Create red team at right side");
		redTeam = new Team("Red team", TeamPosition.RIGHT);
		redTeam.makeRedTeam();
		
		trace("Create blue team at left side");
		blueTeam = new Team("Blue team", TeamPosition.LEFT);
		blueTeam.makeBlueTeam();
		
		trace("Create playerController");
		pController = new PlayerController(stadium);
		
		trace("Give the ball to the blue team middle front");
		pBall = blueTeam.getPlayer(Team.MF);
		
		trace("Set state to NORMAL");
		set_State(State.NORMAL);
	}
	
	@Override
	public void render(GameContainer arg0, Graphics graphics) throws SlickException {
		
		trace("Start graphics render phase");
		
		trace("Fill white background");
		graphics.setBackground(new Color(255, 255, 255));
		
		trace("Draw black stadium");
		graphics.setColor(new Color(0, 0, 0));
		graphics.drawOval(0, 0, stadium.get_totalRadius(), stadium.get_totalRadius());
		
		// players render
		for(int i = 0; i < Team.PLAYER_COUNT; i++) {
			
			Player pRed = redTeam.getPlayer(i);
			if(pRed != pBall) {
				
				trace("Draw " + pRed.toString() + " player with ball");
				graphics.setColor(new Color(255, 0, 0));
			}
			else {
				
				trace("Draw " + pRed.toString() + " player without ball");
				graphics.setColor(new Color(0, 255, 0));
			}
			
			graphics.drawRect((float)pRed.get_xPosition(), (float)pRed.get_yPosition(), 
					(float)pRed.get_PlayerRadius() / 2, (float)pRed.get_PlayerRadius() / 2);
			
			
			Player pBlue = blueTeam.getPlayer(i);
			if(pBlue != pBall) {
				
				trace("Draw " + pBlue.toString() + " player with ball");
				graphics.setColor(new Color(0, 0, 255));
			}
			else {
				
				trace("Draw " + pBlue.toString() + " player without ball");
				graphics.setColor(new Color(0, 255, 0));
			}
			
			graphics.drawRect((float)pBlue.get_xPosition(), (float)pBlue.get_yPosition(), 
					(float)pBlue.get_PlayerRadius() / 2, (float)pBlue.get_PlayerRadius() / 2);
		}
		
		// informations render
		if(get_State() == State.CAUGHT || get_State() == State.ATTACK) {
			
			int i = 0;
			
			trace("Draw defender's information: " + pBall.toString());
			graphics.drawString(pBall.toString() + " EN : " + tEndurance, 550, 50);
			
			// attackers render
			for(Player pAttacker : pAttackers) {
				
				trace("Draw attacker's information: " + pAttacker.toString());
				graphics.drawString(pAttacker.toString() + " AT : " + pAttacker.at, 550, 70 + 20 * i);
				i++;
			}
		}
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		trace("Start updates phase");
		
		switch (get_State())
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
				if(pAttacker == null) {
					
					if(pAttackers.size() > 0) {
					
						pAttacker = pAttackers.remove(0);
					}
					else {
						
						set_State(State.NORMAL);
					}
				}
				else
				{
					if(pController.attackAnim(pBall, pAttacker)) {
						
						if((tEndurance -= pController.attack(pAttacker.at, pBall.en)) <= 0) {
							
							pBall = pAttacker;
							set_State(State.NORMAL);
						}
						
						pAttacker = null;
					}
				}
				
				break;
			}
			case CAUGHT:
			{
				int i = 0;
				Vector<Boolean> inPosition = new Vector<Boolean>();
				
				for(Player pAttacker : pAttackers) {
					
					inPosition.add(pController.catchPositioning(pAttacker, pBall, i));
					i++;
				}
				
				if(!inPosition.contains(Boolean.FALSE)) {
					
					tEndurance = pBall.en;
					set_State(State.ATTACK); 
				}
					
				
				break;
			}
			case NORMAL:
			{
				for(int i = 0; i < Team.PLAYER_COUNT; i++)
				{
					Player redPlayer = redTeam.getPlayer(i);
					Player bluePlayer = blueTeam.getPlayer(i);
					
					redPlayer.increaseCRE();
					bluePlayer.increaseCRE();
					
					if(redPlayer == pBall) {
						
						if((pAttackers = pController.isCaught(pBall, blueTeam)).size() > 0) {
							
							set_State(State.CAUGHT);
							System.out.println(pBall.toString() + " caught by " + pAttackers.size() + " player(s).");
						}
						
						/*
						if(catchers.size() > 0) {
							
							playerBall = playerController.attack(catchers, playerBall);
						}
						*/
					}
					
					if(bluePlayer == pBall) {
						
						if((pAttackers = pController.isCaught(pBall, redTeam)).size() > 0) {
							
							set_State(State.CAUGHT);
							System.out.println(pBall.toString() + " caught by " + pAttackers.size() + " player(s).");
						}
						
						/*
						if(catchers.size() > 0) {
							playerBall = playerController.attack(catchers, playerBall);
						}
						*/
					}
					
					if(!pController.makeADecision(redPlayer, pBall, redTeam, blueTeam)) {
						
						pController.goForwardControl(redPlayer, redPlayer.sp / 10);
					}
					
					if(!pController.makeADecision(bluePlayer, pBall, blueTeam, redTeam)) {
						
						pController.goForwardControl(bluePlayer, bluePlayer.sp / 10);
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
	
	public State get_State() {
		
		return state;
	}

	// TODO: Decay the state change
	public void set_State(State state) {
		
		this.state = state;
	}
	
	
	private static void trace(String message) {
		
		System.out.println("- " + System.currentTimeMillis() + ": " + message);
	}
	
	/**
	 * Entry of the application
	 * 
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		
		trace("Initialize AppGameContainer with a " + WIDTH + "x" + HEIGHT + " screen resolution");
		
		AppGameContainer app = new AppGameContainer(new Game(TITLE));
		app.setDisplayMode(WIDTH, HEIGHT, false);
		app.setTargetFrameRate(FRAME_RATE);
		app.start();
	}
}
