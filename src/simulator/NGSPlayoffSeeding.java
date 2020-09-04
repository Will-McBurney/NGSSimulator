/**
 * This class maintains an ArrayList of playoff seeds.
 * 
 * Index 0 is the team with the 1 seed
 * Index 1 is the team with the 2 seed
 * 
 * And so on.
 * 
 * This class handles the tie breaking in the constructor.
 */

package simulator;

import java.util.List;

import simulator.utils.TieBreakerLibary;

import java.util.ArrayList;
import java.util.Collections;

public class NGSPlayoffSeeding {
	public int PLAYOFF_SIZE = 8;
	
	private ArrayList<NGSTeam> seeding;
	
	/**
	 * Takes in a list of teams (as a precondition, this should be AFTER a season is simulated). It then extracts the
	 * top 8 teams that would make the playoffs.
	 * @param teams
	 */
	@SuppressWarnings("unchecked")
	public NGSPlayoffSeeding(List<NGSTeam> teams) {
		//if there are fewer than 8 teams, then it's double round robin, meaning only 4 teams
		//this isn't a permanent solution I hope
		if (teams.size() < 8) {
			PLAYOFF_SIZE = 4;
		}
		if (teams.size() == 8) {
			PLAYOFF_SIZE = 6;
		}
		//sort the teams by their season score
		Collections.sort(teams);
		try {
			teams = (ArrayList<NGSTeam>) (((ArrayList<NGSTeam>)teams).clone());
		} catch (ClassCastException e) {
			System.err.println("Somehow java converted an ArrayList clone into something that isn't an array list. I quit.");
		}
		
		seeding = new ArrayList<NGSTeam>(PLAYOFF_SIZE);	
		while (seeding.size() < PLAYOFF_SIZE) {
			//check of position score is different from position + 1 score
			if (teams.get(0).getScore() > teams.get(1).getScore()) {
				//scores are different
				seeding.add(teams.get(0));
				teams.remove(0);
				
			} else {
				//extract all tied teams:
				int targetScore = teams.get(0).getScore();
				int targetPosition = 0;
				ArrayList<NGSTeam> tied = new ArrayList<NGSTeam>();
				while (targetPosition < teams.size() && teams.get(targetPosition).getScore() == targetScore) {
					tied.add(teams.get(targetPosition));
					targetPosition++;
				}
				//at this point, tied is a list of teams with the same score
				
				//call the head-to-head tiebreaker function
				NGSTeam tieWinner = TieBreakerLibary.ngsBreakTie(tied);
				seeding.add(tieWinner);
				teams.remove(tieWinner);
			}
		}
	}

	public ArrayList<NGSTeam> getSeeding() {
		return seeding;
	}
}
