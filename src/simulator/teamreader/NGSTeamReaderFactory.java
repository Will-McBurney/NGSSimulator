package simulator.teamreader;

public class NGSTeamReaderFactory {
	/**
	 * Factory method
	 * @param teamFilename - filename to read team from
	 * @param isNaive - whether or not we want Naive Team Reader (if no, then we default to Estimated Team Reader)
	 * @return a TeamReader object
	 */
	public NGSTeamReader getNGSTeamReader(String teamFilename, boolean isNaive) {
		if (isNaive) {
			return new NaiveNGSTeamReader(teamFilename);
		} else {
			return new EstimatedNGSTeamReader(teamFilename);
		}
	}
}
