/**
 * Tracks a division in NGS, including the division name, the teams in the division, the schedule, and whether or not
 * the division has been simulated yet.
 */

package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simulator.schedulereader.NGSScheduleReader;
import simulator.teamreader.NGSTeamReader;
import simulator.teamreader.NGSTeamReaderFactory;

public class NGSDivision implements Division{
	
	private String name; //such as "H", "A", "BW"
	private List<NGSTeam> teams; //List of NGSTeam Objects in the division (does not include teams outside the division)
	private NGSDivisionSchedule schedule; //List of intra divisional matches
	private boolean simulated; //Whether or not the division has been simulated yet 
	                           //(note that even if every game has been played, this would still be false if simulate hasn't been called)
	
	/**
	 * Constructor
	 * @param teamFilename - location of team file. See data/teams.csv for structure of the file
	 * @param divisionName - "H", "A", "BW" for example
	 * @param isNaive - True: we are making a naive assumption about each team's Elo in the division
	 *                  False: we are going to use the estimated Elo based on performance pre-dating NGS Season 7
	 */
	public NGSDivision(String teamFilename, String divisionName, boolean isNaive) {
		name = divisionName;
		simulated = false;
		NGSTeamReaderFactory trf = new NGSTeamReaderFactory();
		NGSTeamReader tr = trf.getNGSTeamReader(teamFilename, isNaive);
		teams = tr.getTeamsForDivision(divisionName);
		NGSScheduleReader sr = new NGSScheduleReader();
		schedule = sr.getDivisionSchedule(teams, divisionName);
		
	}
	
	/**
	 * Get a list of the Teams from the Division. Note this returns the interface Team, NOT the NGSTeam object
	 * This is admittedly a bit weird to fit my intial view of the interface, though I end up pretty much always
	 * using getNGSTeams() in this program. Probably bad design I should refactor later.
	 */
	public List<Team> getTeams() {
		List<Team> out = new ArrayList<Team>();
		for (NGSTeam t : teams) {
			out.add(t);
		}
		return out;
	}
	
	/**
	 * Return the list of NGSTeams in the division
	 * @return
	 */
	public List<NGSTeam> getNGSTeams() {
		return teams;
	}

	/**
	 * Get the name of the division ("H", "A", "BW", etc.)
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Simulate any unresolved games in the division
	 */
	@Override
	public void simulate() {
		simulated = true;
		schedule.simulateUnresolvedMatches();		
		Collections.sort(teams);		
	}
	
	/**
	 * Get the Playoff seeded teams AFTER the simulated run. In NGS, this is generally the top 8 in each division
	 * @return
	 */
	public NGSPlayoffSeeding getPlayoffSeeding() {
		if (!simulated) {
			throw new IllegalStateException("ERROR: Division has not been simulated yet");
		}
		return new NGSPlayoffSeeding(teams);
	}
	
	@Override
	/**
	 * Useful for debugging: Prints a detailed printout of each team, including who they beat, lost to, elo scores, etc.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Division " + name + "\n");
		sb.append("--Teams:");
		for (NGSTeam t : teams) {
			sb.append("\t"+t.toStringDetailed()+"\n");
		}
		
		return sb.toString();
	}


}
