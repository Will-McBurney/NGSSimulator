package simulator.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simulator.NGSTeam;

public class TieBreakerLibary {
	
	/**
	 * Inner class for tracking head-to-head scores
	 *
	 */
	public static class HeadToHeadTieBreakerScore implements Comparable<HeadToHeadTieBreakerScore> {
		NGSTeam team;
		int score;
		
		public HeadToHeadTieBreakerScore(NGSTeam team) {
			this.team = team;
		}
		
		@Override
		public int compareTo(HeadToHeadTieBreakerScore o) {
			return o.score - this.score; //descending
		}
		
	}
    /**
     * Breaks tiebreaker of tied teams. This does NOT take into account KDA, so if a tie is unresolvable, a team is chosen randomly.
     * @param tied - list of tied teams.
     * @return
     */
	public static NGSTeam ngsBreakTie(ArrayList<NGSTeam> tied) {
		if (tied.size() == 0) {
			throw new IllegalArgumentException("Error: Trying to tie breaker with a list of size 0");
		}
		if (tied.size() == 1) {
			return tied.get(0);
		}
		if (tied.size() == 2) {
			if (tied.get(0).hasBeat(tied.get(1))) {
				return tied.get(0);
			} else if (tied.get(1).hasBeat(tied.get(0))){
				return tied.get(1);
			} else { //mutual forfeit case
				//flip a coin - this is suboptimal, but I do not account for KDA
				Collections.shuffle(tied);
				return tied.get(0);
			}
		} else {
			List<HeadToHeadTieBreakerScore> tiebreaker = new ArrayList<HeadToHeadTieBreakerScore>(); //list of tied teams and their
			                                                                                         //tiebreaker scores
			for (NGSTeam t : tied ) { //add each team to tiebreaker with 0 score
				tiebreaker.add(new HeadToHeadTieBreakerScore(t));
			}
			for (HeadToHeadTieBreakerScore t : tiebreaker) { //get the total score of each team against every other team in tied
				for (NGSTeam o : tied) {
					if (t.team.equals(o)) { //same team
						continue; //skip
					} else {
						t.score += t.team.scoreVs(o); //add score result
					}
				}
			}
			Collections.sort(tiebreaker); //sort in descending order by score
			if (tiebreaker.get(0).score > tiebreaker.get(1).score) { //if top score is better than all other scores
				return tiebreaker.get(0).team;//return top scoring team
			} else { //else if there is a tie
				//unresolvable check
				int targetScore = tiebreaker.get(0).score;
				boolean allSame = true;
				for (HeadToHeadTieBreakerScore t : tiebreaker) {
					if (t.score != targetScore) {
						allSame = false;
						break;
					}
				}
				if (allSame) { //unresolvable, pick one at random
					Collections.shuffle(tiebreaker);
					return tiebreaker.get(0).team;
				} else {
					ArrayList<NGSTeam> furtherTied = new ArrayList<NGSTeam>();
					for (HeadToHeadTieBreakerScore t : tiebreaker) {
						if (t.score == targetScore) {
							furtherTied.add(t.team);
						}
					}
					return ngsBreakTie(furtherTied);
					
				}
			}
		}
	}

}
