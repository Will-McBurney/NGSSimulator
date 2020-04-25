/**
 * This class reads a schedule file
 * 
 * See the csv files in data/schedules for examples of format.
 */

package simulator.schedulereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import simulator.NGSDivisionSchedule;
import simulator.NGSMatch;
import simulator.NGSTeam;

public abstract class NGSScheduleReader {
	public abstract NGSDivisionSchedule getDivisionSchedule(List<NGSTeam> teams, String divisionName, boolean fiftyFifty); 
		
	/**
	 * Helper function. Converts a List of teams into a hashmap of teams, where each abbreviation is mapped to the team object.
	 * @param teams
	 * @return
	 */
	protected static HashMap<String, NGSTeam> getTeamMapFromList(List<NGSTeam> teams) {
		HashMap<String, NGSTeam> out = new HashMap<String, NGSTeam>();
		for (NGSTeam t : teams) {
			if (out.containsKey(t.getAbbreviation())) {
				throw new IllegalStateException("Duplicate Team Abbreviations found: " + t.getAbbreviation());
			}
			out.put(t.getAbbreviation(), t);
		}
	    return out;
	}
}
