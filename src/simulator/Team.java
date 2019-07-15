/**
 * Interface for a team object
 * 
 * This might be overfitting to the NGS model of how teams are stored, but it's useful.
 * Comparable should sort in a way useful to the league. I tend to sort by however the rankings
 * would be sorted.
 */

package simulator;

public interface Team extends Comparable<Team>{
	/**
	 * Return the full name of the team (Example: "Celeb Gaming")
	 * @return name of team
	 */
	public String getName();
	
	/**
	 * Return the short abbreviation for the team (Example: "CTV")
	 * @return abbreviation of team
	 */
	public String getAbbreviation();
	
	/**
	 * Return the number of match wins (that is, the number of series won. In NGS, this is Best of 3s won)
	 * @return match wins count
	 */
	public int getMatchWins();
	

	/**
	 * Return the number of match ties (that is, the number of series tied). Default implementation assumes your league cannot have ties.
	 * @return match ties count
	 */
	public default int getMatchTies() {
		return 0; //assumes no ties possible
	}
	
	/**
	 * Return the number of match losses (that is, the number of series lost. In NGS, this is Best of 3s lost)
	 */
	public int getMatchLosses();
	
	/**
	 * Return the number of map wins (that is, how many individual games won)
	 * @return
	 */
	public int getMapWins();
	
	/**
	 * Return the number of map losses (that is, how many individual games lost)
	 * @return
	 */
	public int getMapLosses();
	
	/**
	 * Get the score for playoff seeding. This algorithm will vary from league to league, but should take into account
	 * scoring methodology.
	 * @return
	 */
	public int getScore();
	
	/**
	 * Get the team's current predictive Elo score.
	 * @return
	 */
	public double getElo();
	
	/**
	 * Update both the calling team (this) and "other". This updates: their win/loss records and their elo (if necessary).
	 * @param other - the team played against
	 * @param myMapScore - the calling team's map score
	 * @param otherMapScore - the opponent map score
	 */
	public void recordMatchResult(Team other, int myMapScore, int otherMapScore, boolean isForfeit);
	
	/**
	 * Similar to recordMatchResult, but as this is a simulated result, Elo is NOT updated, only match records
	 * @param other
	 * @param myMapScore
	 * @param otherMapScore
	 */
	public void recordSimulatedResult(Team other, int myMapScore, int otherMapScore); //no elo change
	
	/**
	 * Produces a detailed printout of the team (toString simply gives the team's abbreviation).
	 * @return
	 */
	public String toStringDetailed();
}
