/**
 * This class is used to read a teams csv file and generate a list of team objects
 * 
 * This can be done for either one division (i.e.: "Give me all the teams in B-West") or for all
 * of NGS. The latter may be useful later in a feature for listing all teams Elo ratings.
 * 
 * Uses Factory pattern design, with EstimatedNGSTeamReader and NaiveNGSTeamReader being
 * concrete implementations of the getTeamFromLine function. This function is effectively
 * implemented to decide which column to use. The factory pattern is used in case someone
 * wants to come up with another means of using the TeamReader.
 */

package simulator.teamreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import simulator.NGSTeam;

public abstract class NGSTeamReader {
	private String filename;
	
	/**
	 * Constructor, sets filename variable
	 * @param filename - name of the file (assumes the file is in the data subdirectory)
	 */
	public NGSTeamReader(String filename) {
		this.filename = "data/" + filename; //asumes team data is in the data subdirectory
	}
	
	/**
	 * Get a list of NGSTeam objects from a single division (such as "H", "A", "BW", etc.)
	 * @param divisionName - String for division, "H", "A", "BW", etc. Must match division column in teams.csv EXACTLY
	 * @return ArrayList of teams in that division.
	 */
	public List<NGSTeam> getTeamsForDivision(String divisionName) {
		ArrayList<NGSTeam> teams = new ArrayList<NGSTeam>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			
			br.readLine(); //ignore header line
			
			String line;
			while ((line = br.readLine()) != null) {
				if (divisionName.equalsIgnoreCase(getDivisionNameFromLine(line))) {
					teams.add(getTeamFromLine(line));
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("No file of name: \"" + filename + "\" found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Error on : \"" + filename + "\"");
			e.printStackTrace();
		}
		return teams;
	}
	
	/**
	 * Get a list of NGSTeam objects regardless of division
	 * @return ArrayList of teams in that division.
	 */
	public List<NGSTeam> getAllTeams() {
		ArrayList<NGSTeam> teams = new ArrayList<NGSTeam>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			
			br.readLine(); //ignore header line
			
			String line;
			while ((line = br.readLine()) != null) {
				teams.add(getTeamFromLine(line));
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("No file of name: \"" + filename + "\" found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Error on : \"" + filename + "\"");
			e.printStackTrace();
		}
		return teams;
	}
	
	/**
	 * Gets the division from a specific team. This ASSUMES the file is formated like "data/teams.csv"
	 * 
	 * Example:
	 * line = "Celeb Gaming,CTV,H,3000,2600" -> returns H
	 * @param line - a non-header line in a file that contains at least 2 commas.
	 * @return
	 */
	private String getDivisionNameFromLine(String line) {
		return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1)[2];
	}
	
	/**
	 * Abstract method for getting the teams Elo rating
	 * @param line - String of the form "Celeb Gaming,CTV,H,3000,2600"
	 * 				 see "data/teams.csv"
	 * @return the Team Object from the line, with the Elo selected by the concrete implementor
	 */
	protected abstract NGSTeam getTeamFromLine(String line);
}
