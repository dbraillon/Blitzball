package com.dbraillon.blitzball.models;

import java.util.ArrayList;
import java.util.Vector;

import org.newdawn.slick.GameContainer;

import com.dbraillon.blitzball.AI;
import com.dbraillon.blitzball.enumerations.PlayerPosition;
import com.dbraillon.blitzball.enumerations.State;
import com.dbraillon.blitzball.enumerations.TeamPosition;
import com.dbraillon.blitzball.tools.Positioning;
import com.dbraillon.dbgraphics.Depth;
import com.dbraillon.dbgraphics.Renderable;
import com.dbraillon.math.Point;

public class Match extends Renderable {

	private Stadium stadium;
	private Team leftTeam, rightTeam;
	
	// Player that has the ball
	private Player pBall;
	
	// Player that counter the pBall in attack or shoot...
	private Player pCounter;
	private ArrayList<Player> pCounters;
	private int pDefensiveStat;
	
	// The state of the game
	private State gState;
	
	// used in positioning
	private float xAttacker, yAttacker;
	
	public Match(com.dbraillon.dbgraphics.Point position) {
		super(position, Depth.Middle);
		
		stadium = new Stadium();
		
		leftTeam = new Team("Left team");
		leftTeam.makeLeftTeam();
		
		rightTeam = new Team("Right team");
		rightTeam.makeRightTeam();
		
		pBall = leftTeam.getPlayer(PlayerPosition.MF);
		
		gState = State.NORMAL;
	}
	
	@Override
	public void update(GameContainer gameContainer, double frameTimeModifier) {
		
		switch (gState) {
		
			case SHOOT: {
				
				break;
			}
			case PASS: {
				
				break;
			}
			case ATTACK: {
				
				if(pCounter == null) {
					
					if(pCounters.size() > 0) {
					
						pCounter = pCounters.remove(0);
						xAttacker = pCounter.getCurrentPosition().getX();
						yAttacker = pCounter.getCurrentPosition().getY();
					}
					else {
						
						gState = State.NORMAL;
					}
				}
				else
				{
					pCounter.turnToDestination(Positioning.getAttackPosition(pBall, new Point(xAttacker, yAttacker)));
					pCounter.goForward();
					
					if(pCounter.isNear(pCounter.getDestinationPosition())) {
						
						pDefensiveStat = pCounter.attack(pDefensiveStat);
						
						if(pDefensiveStat <= 0) {
							
							pBall.getReflex().reset();
							pBall = pCounter;
							pBall.getReflex().increaseMax();
							
							gState = State.NORMAL;
						}
						else {
							
							pCounter.getReflex().reset();
						}
						
						pCounter = null;
					}
				}
			
				break;
			}
			case CAUGHT: {
				
				int i = 0;
				Vector<Boolean> inPosition = new Vector<Boolean>();
				
				for(Player pAttacker : pCounters) {
					
					pAttacker.turnToDestination(Positioning.getCaughtPosition(pBall, i));
					pAttacker.goForward();
					inPosition.add(pAttacker.isNear(pAttacker.getDestinationPosition()));
					i++;
				}
				
				if(!inPosition.contains(Boolean.FALSE)) {
					
					gState = State.ATTACK; 
				}
					
				break;
			}
			case NORMAL: {
				
				for(int i = 0; i < Team.PLAYER_COUNT; i++)
				{
					Player redPlayer = leftTeam.getPlayer(i);
					Player bluePlayer = rightTeam.getPlayer(i);
					
					AI.play(redPlayer, pBall, leftTeam, rightTeam, stadium);
					AI.play(bluePlayer, pBall, rightTeam, leftTeam, stadium);
				}
				
				if(pBall.getTeam().get_tPosition() == TeamPosition.LEFT) {
					
					if((pCounters = pBall.isCaught(rightTeam)).size() > 0) {
						
						gState = State.CAUGHT;
						pDefensiveStat = pBall.en;
					}
				}
				else if(pBall.getTeam().get_tPosition() == TeamPosition.RIGHT) {
					
					if((pCounters = pBall.isCaught(leftTeam)).size() > 0) {
						
						gState = State.CAUGHT;
						pDefensiveStat = pBall.en;
					}
				}
				
				break;
			}
		}
	}

	public Team getLeftTeam() {
		return leftTeam;
	}

	public void setLeftTeam(Team leftTeam) {
		this.leftTeam = leftTeam;
	}

	public Team getRightTeam() {
		return rightTeam;
	}

	public void setRightTeam(Team rightTeam) {
		this.rightTeam = rightTeam;
	}

	public Player getpBall() {
		return pBall;
	}

	public void setpBall(Player pBall) {
		this.pBall = pBall;
	}

	public State getgState() {
		return gState;
	}

	public void setgState(State gState) {
		this.gState = gState;
	}

	public Player getpCounter() {
		return pCounter;
	}

	public void setpCounter(Player pCounter) {
		this.pCounter = pCounter;
	}

	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

	public int gettEndurance() {
		return pDefensiveStat;
	}

	public void settEndurance(int tEndurance) {
		this.pDefensiveStat = tEndurance;
	}

	public ArrayList<Player> getpCounters() {
		return pCounters;
	}

	public void setpCounters(ArrayList<Player> pCounters) {
		this.pCounters = pCounters;
	}

	public int getpDefensiveStat() {
		return pDefensiveStat;
	}

	public void setpDefensiveStat(int pDefensiveStat) {
		this.pDefensiveStat = pDefensiveStat;
	}
}
