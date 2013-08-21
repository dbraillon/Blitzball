package com.dbraillon.blitzball;

import com.dbraillon.blitzball.enumerations.Decision;
import com.dbraillon.blitzball.enumerations.DecisionType;
import com.dbraillon.blitzball.enumerations.Position;

public class AI {

	public static void play(Player mPlayer, Player bPlayer, Team mTeam, Team oTeam, Stadium stadium) {
		
		mPlayer.increaseCRE();
		
		Decision decision = makeADecision(mPlayer, bPlayer, mTeam, oTeam, stadium);
		
		if(decision.decisionType == DecisionType.CONTINUE) {
			
			decision = mPlayer.lastDecision;
		}
		else if(decision.decisionType != DecisionType.NOTHING) {
			
			mPlayer.resetCRE();
		}
		
		switch(decision.decisionType) {
		
			case NOTHING:
				break;
				
			case FOLLOW:
				
				mPlayer.turnToDestination(decision.player.get_xPosition(), decision.player.get_yPosition());
				mPlayer.goForward();
				break;
				
			case MOVE:
				
				mPlayer.turnToDestination(decision.x, decision.y);
				mPlayer.goForward();
				break;
				
			case PASS:
				break;
				
			case SHOOT:
				break;
				
			default:
				break;
		}
		
		mPlayer.lastDecision = decision;
	}
	
	public static Decision makeADecision(Player mPlayer, Player bPlayer, Team mTeam, Team oTeam, Stadium stadium) {
		
		// le goal ne fait rien
		if(mPlayer.position == Position.GL) 
			return new Decision(DecisionType.NOTHING);
		
		// si j'ai pas mon reflex je prend pas de décision
		if(!mPlayer.isAware()) 
			return new Decision(DecisionType.CONTINUE);
		
		// qui a le ballon ?
		if(mPlayer.team == bPlayer.team) {
			
			// quelqu'un de ma team ! mais qui ?
			if(mPlayer == bPlayer) {
				
				// c'est moi !
				
				// ça vaut le coup que je tire ?
				double d = Math.sqrt(Math.pow(oTeam.getPlayer(Position.GL).get_xPosition() - mPlayer.get_xPosition(), 2) 
									+ Math.pow(oTeam.getPlayer(Position.GL).get_yPosition() - mPlayer.get_yPosition(), 2));
				
				System.out.println("" + d);
				
				if(d/20 < mPlayer.sh * 70 / 100) {
					
					/*
					if(mPlayer.shoot(bPlayer, oTeam.getPlayer(Team.GL))) {
						
						System.out.println("GOAL !");
						
						if(oTeam.get_tPosition() == TeamPosition.LEFT) oTeam.makeBlueTeam(); 
						else oTeam.makeRedTeam();
						
						if(mTeam.get_tPosition() == TeamPosition.LEFT) mTeam.makeBlueTeam(); 
						else mTeam.makeRedTeam();
					}
					else {
						
						System.out.println("MISS !");
					}*/
				}
				
				// dans tous les cas j'avance en direction des cages adverses
				return new Decision(DecisionType.MOVE, 
									oTeam.getPlayer(Position.GL).get_xPosition(), 
									oTeam.getPlayer(Position.GL).get_yPosition());
			}
			else {
				
				return new Decision(DecisionType.MOVE, 
									mPlayer.get_xOriginPosition(), 
									mPlayer.get_yOriginPosition());
			}
		}
		else {
			
			// quelqu'un de la team adverse !
			if(mPlayer.isNear(bPlayer)) {
				
				return new Decision(DecisionType.FOLLOW, bPlayer);
			}
			else {
				
				return new Decision(DecisionType.MOVE, 
									mPlayer.get_xOriginPosition(), 
									mPlayer.get_yOriginPosition());
			}
		}
	}

	
}
