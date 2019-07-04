/**
 * Manages the schedule of matches for a division, as well as triggers the simulation of that scheduled event.
 */

package simulator;

import java.util.HashMap;
import java.util.List;

public class NGSDivisionSchedule {
	 /**
	  * abbrv -> team object. Example: "CTV" maps to the Celeb Gaming Object.
	  * This is because when doing the schedule, it's easier just to use the abbreviations. This is a simple way
	  * to map those abbreviations to the team they represent
	  */
	private HashMap<String, NGSTeam> teams;
	private List<NGSMatch> matches; //List of match objects
	
	/**
	 * Constructor
	 * @param teams - HashMap of teams in the division, with abbreviation mapped to team object
	 * @param matches - List of matches in the division
	 * @param divisionName - The divisions name, like "H", "A", or "BW"
	 */
	
	public NGSDivisionSchedule (HashMap<String, NGSTeam> teams, List<NGSMatch> matches, String divisionName) {
		this.teams = teams;
		this.matches = matches;
	}
	
	/**
	 * Simulate any match that doesn't already have a result (does not alter matches that have already been played)
	 */
	public void simulateUnresolvedMatches() {
		for (NGSMatch match : matches) {
			if (!match.isResolved()) {
				match.resolve();
			}
		}
	}
	
	/**
	 * Generates an output of all the teams and matches in the division. Useful for testing/debugging, but not used
	 * by the simulator program itself.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String s : teams.keySet()) {
			sb.append(teams.get(s) + ", ");
		}
		sb.append("\nMatches:\n");
		for (NGSMatch match : matches) {
			sb.append(match + "\n");
		}
		return sb.toString();
	}

}
