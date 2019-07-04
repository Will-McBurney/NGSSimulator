/**
 * This class is used to modify the NGSTeamReader to read the initial Elo estimation.
 */

package simulator.teamreader;

import simulator.NGSTeam;

public class NaiveNGSTeamReader extends NGSTeamReader {
	/**
	 * Standard pass through constructor for factory concrete implementor
	 * @param filename
	 */
	public NaiveNGSTeamReader(String filename) {
		super(filename);
	}

	/**
	 * Selects Elo from Naive assumption (that is, everyone in Division starts with the same Elo rating).
	 */
	@Override
	protected NGSTeam getTeamFromLine(String line) {
		String[] lineSplit = line.split(",");
		return new NGSTeam(lineSplit[0], lineSplit[1], Double.parseDouble(lineSplit[4]));
	}

}
