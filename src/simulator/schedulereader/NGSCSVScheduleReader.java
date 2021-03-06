package simulator.schedulereader;

/**
 * This class reads a schedule file
 * 
 * See the csv files in data/schedules for examples of format.
 */

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

public class NGSCSVScheduleReader extends NGSScheduleReader {
	public NGSDivisionSchedule getDivisionSchedule(List<NGSTeam> teams, String divisionName, boolean fiftyFifty) {
		HashMap<String, NGSTeam> teamMap = getTeamMapFromList(teams);
		ArrayList<NGSMatch> matches = new ArrayList<NGSMatch>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(getDivisionFileName(divisionName))));//fucking java
			//burn header line
			br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("#") && line.length() >= 2) {
					matches.add(getNGSMatchFromLine(line, teamMap, fiftyFifty));
				}
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new NGSDivisionSchedule(teamMap, matches, divisionName);
	}
	
	/**
	 * Use the division name to get the relevant schedule CSV file. Assumes the file name is "data/schedules/Schedules - H",
	 * where you replace "H" with whichever division you want ("A", "BW", "CE", etc.)
	 * @param divisionName
	 * @return
	 */
	private String getDivisionFileName(String divisionName) {
		return "data/schedules/Schedules - " + divisionName + ".csv";
	}
	
	/**
	 * Extract a Match object from a line in the csv file
	 * @param line
	 * @param teams
	 * @param fiftyFifty 
	 * @return
	 */
	private NGSMatch getNGSMatchFromLine(String line, HashMap<String,NGSTeam> teams, boolean fiftyFifty) {
		String[] lineSplit = line.split(",", 5);
		if (lineSplit[2].length()==0) { //unresolved game
			if (fiftyFifty) {
				return new NGSMatch(teams.get(lineSplit[0]), teams.get(lineSplit[1]), true);
			}
			return new NGSMatch(teams.get(lineSplit[0]), teams.get(lineSplit[1]));
		} else { //resolved game
			boolean isForfeit = lineSplit[4].equals("1");
			teams.get(lineSplit[0]).recordMatchResult(teams.get(lineSplit[1]), 
					Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]), isForfeit);
			return new NGSMatch(teams.get(lineSplit[0]), teams.get(lineSplit[1]),
					Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]), isForfeit);
		}
	}
}
