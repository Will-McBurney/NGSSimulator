package simulator;

public interface Match {
	/**
	 * Returns whether or not the match is resolved. In the data file, columns HomeScore and AwayScore
	 * should be blank if the match is unresolved, or with the match score if the match is resolved
	 * @return boolean true/false as to whether match has been played
	 */
	public boolean isResolved();
	
	/**
	 * If the match is unresolved, simulate the match randomly using Elo bias (note if doing a
	 * naive simulation, it's initially assumed all teams in a division have the same Elo, but
	 * any matches already resolved in the schedule file will still adjust Elo).
	 */
	public void resolve();
	
	/**
	 * Simple getter for home team instance of the Team class
	 * @return home team Team object
	 */
	public Team getHomeTeam();
	
	/**
	 * Simple getter for home team map score
	 * @return away team map score in a match
	 */
	public int getHomeScore();
	
	/**
	 * Simple getter for away team instance of the Team class
	 * @return away team Team object
	 */
	public Team getAwayTeam();
	
	/**
	 * Simple getter for away team map score
	 * @return away team map score in a match
	 */
	public int getAwayScore();
}
