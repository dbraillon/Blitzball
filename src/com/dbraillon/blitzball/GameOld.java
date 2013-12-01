package com.dbraillon.blitzball;

import java.util.Vector;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.dbraillon.blitzball.enumerations.PlayerPosition;
import com.dbraillon.blitzball.enumerations.State;
import com.dbraillon.blitzball.enumerations.TeamPosition;
import com.dbraillon.blitzball.models.Player;
import com.dbraillon.blitzball.models.Stadium;
import com.dbraillon.blitzball.models.Team;
import com.dbraillon.blitzball.tools.Point;

public class GameOld extends BasicGame {

	/**
	 * - General configuration -
	 * 
	 * > screen height and width
	 * > screen frame rate
	 * > screen title
	 */
	private static final int WIDTH = 800, HEIGHT = 600, FRAME_RATE = 5;
	private static final String TITLE = "Blitzball";
	
	
	private Stadium stadium;
	private Team redTeam, blueTeam;
	
	private Player pBall;
	
	private State gState;
	
	// used in ATTACK and CAUGHT state
	private Vector<Player> pAttackers;
	private Player pAttacker;
	private int tEndurance;
	private double xAttacker, yAttacker;
	
	
	
	/**
	 * Constructor
	 * 
	 * @param title Screen title
	 */
	
	public GameOld(String title) {
		super(title);
	}

	
	/*
	 * (non-Javadoc)
	 * slick2d (in order) :
	 * > init
	 * > render
	 * > update
	 */
	AI ia;
	@Override
	public void init(GameContainer gContainer) throws SlickException {
		
		trace("Start init phase");
		
		trace("Create stadium");
		stadium = new Stadium();
		
		trace("Create red team at right side");
		redTeam = new Team("Red team", TeamPosition.RIGHT);
		redTeam.makeRedTeam();
		
		trace("Create blue team at left side");
		blueTeam = new Team("Blue team", TeamPosition.LEFT);
		blueTeam.makeBlueTeam();
		
		trace("Give the ball to the blue team middle front");
		pBall = blueTeam.getPlayer(PlayerPosition.MF);
		
		trace("Set state to NORMAL");
		set_State(State.NORMAL);
	}
	
	@Override
	public void render(GameContainer arg0, Graphics graphics) throws SlickException {
		trace("1");
		// fill background with white
		graphics.setBackground(new Color(255, 255, 255));
		
		// draw stadium in black
		graphics.setColor(new Color(0, 0, 0));
		graphics.drawOval(0, 0, stadium.get_totalRadius(), stadium.get_totalRadius());
		
		// start players rendering
		for(int i = 0; i < Team.PLAYER_COUNT; i++) {
			
			Player pRed = redTeam.getPlayer(i);
			Player pBlue = blueTeam.getPlayer(i);
			
			// choose the color of red player rendering, depending if he holds the ball or not
			if(pRed != pBall) { 
				
				graphics.setColor(new Color(255, 0, 0));
			}
			else {
				
				graphics.setColor(new Color(0, 255, 0));
			}
			
			// draw the red player
			graphics.drawRect((float)pRed.getCurrentPosition().getX(), (float)pRed.getCurrentPosition().getY(), 
					(float)pRed.getPlayerZone().getRadius(), (float)pRed.getPlayerZone().getRadius());
			
			
			// choose the color of red player rendering, depending if he holds the ball or not
			if(pBlue != pBall) {
				
				graphics.setColor(new Color(0, 0, 255));
			}
			else {
				
				graphics.setColor(new Color(0, 255, 0));
			}
			
			// draw the blue player
			graphics.drawRect((float)pBlue.getCurrentPosition().getX(), (float)pBlue.getCurrentPosition().getY(), 
					(float)pBlue.getPlayerZone().getRadius(), (float)pBlue.getPlayerZone().getRadius());
		}
		
		
		graphics.drawString("State: " + gState, 500, 20);
		
		// informations render
		if(get_State() == State.CAUGHT || get_State() == State.ATTACK) {
			
			int i = 0;
			
			trace("Draw defender's information: " + pBall.toString());
			graphics.drawString(pBall.toString() + " EN : " + tEndurance, 500, 50);
			
			// attackers render
			for(Player pAttacker : pAttackers) {
				
				trace("Draw attacker's information: " + pAttacker.toString());
				graphics.drawString(pAttacker.toString() + " AT : " + pAttacker.at, 500, 70 + 20 * i);
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
				trace("Shoot phase");
				break;
			}
			case PASS:
			{
				trace("Pass phase");
				break;
			}
			case ATTACK:
			{
				trace("Attack phase");
				
				if(pAttacker == null) {
					
					if(pAttackers.size() > 0) {
					
						pAttacker = pAttackers.remove(0);
						xAttacker = pAttacker.getCurrentPosition().getX();
						yAttacker = pAttacker.getCurrentPosition().getY();
					}
					else {
						
						set_State(State.NORMAL);
					}
				}
				else
				{
					if(pAttacker.attackAnim(pBall)) {
						
						tEndurance = pAttacker.attack(tEndurance);
						
						if(tEndurance <= 0) {
							
							pBall.getReflex().reset();
							pBall = pAttacker;
							pBall.getReflex().increaseMax();
							
							set_State(State.NORMAL);
						}
						else {
							
							pAttacker.getReflex().reset();
						}
						
						pAttacker = null;
					}
				}
				
				break;
			}
			case CAUGHT:
			{
				trace("Caught phase");
				
				int i = 0;
				Vector<Boolean> inPosition = new Vector<Boolean>();
				
				for(Player pAttacker : pAttackers) {
					
					inPosition.add(pAttacker.catchPositioning(pBall, i));
					i++;
				}
				
				if(!inPosition.contains(Boolean.FALSE)) {
					
					set_State(State.ATTACK); 
				}
					
				break;
			}
			case NORMAL:
			{
				trace("Normal phase");
				
				for(int i = 0; i < Team.PLAYER_COUNT; i++)
				{
					Player redPlayer = redTeam.getPlayer(i);
					Player bluePlayer = blueTeam.getPlayer(i);
					
					AI.play(redPlayer, pBall, redTeam, blueTeam, stadium);
					AI.play(bluePlayer, pBall, blueTeam, redTeam, stadium);
				}
				
				if(pBall.getTeam().get_tPosition() == TeamPosition.LEFT) {
					
					if((pAttackers = pBall.isCaught(redTeam)).size() > 0) {
						
						set_State(State.CAUGHT);
					}
				}
				else if(pBall.getTeam().get_tPosition() == TeamPosition.RIGHT) {
					
					if((pAttackers = pBall.isCaught(blueTeam)).size() > 0) {
						
						set_State(State.CAUGHT);
					}
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
		
		return gState;
	}

	// TODO: Decay the state change
	public void set_State(State state) {
		
		trace("State changing from " + gState + " to " + state);
		
		switch (state) {
			
			case ATTACK:
			{
				break;
			}
			case CAUGHT:
			{		
				tEndurance = pBall.en;
				System.out.println(pBall.toString() + " caught by " + pAttackers.size() + " player(s).");
				break;
			}			
			case NORMAL:
			{	
				break;
			}
			case PASS:
			{	
				break;
			}
			case SHOOT:
			{	
				break;
			}
			default:
			{
				break;
			}
		}
		
		this.gState = state;
	}
	
	
	private static void trace(String message) {
		
		System.out.println("- " + System.currentTimeMillis() + ": " + message);
	}
}
