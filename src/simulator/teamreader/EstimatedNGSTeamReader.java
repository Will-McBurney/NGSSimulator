/**
 * This class is used to modify the NGSTeamReader to read the initial Elo estimation.
 */


package simulator.teamreader;

import simulator.NGSTeam;

public class EstimatedNGSTeamReader extends NGSTeamReader {

	/**
	 * Constructor - simply passes filename to parent
	 * @param filename
	 */
	public EstimatedNGSTeamReader(String filename) {
		super(filename);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Uses estimated Elo rating for team, which is biased by past performance before NGS S7 (see teams.csv file)
	 */
	@Override
	protected NGSTeam getTeamFromLine(String line) {
		String[] lineSplit = line.split(",");
		return new NGSTeam(lineSplit[0], lineSplit[1], Double.parseDouble(lineSplit[3]));
	}

}
