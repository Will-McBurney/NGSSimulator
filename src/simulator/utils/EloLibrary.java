/**
 * Static Library for Elo calculations. See eloratings.net/about
 */

package simulator.utils;

import simulator.Team;

public class EloLibrary {
	public static final int K = 20;
	
	/**
	 * Gets win probability of home team against away team. This probability is (0.0, 1.0)
	 * Examples:
	 * Elo Difference - Favored Team win%
	 * 0              - 50%
	 * 100            - 64%
	 * 200            - 76%
	 * 300            - 84.9%
	 * 400            - 90.9%
	 * 
	 * @param home - home team object
	 * @param away - away team object
	 * @return the probability that home team beats the away team on a single map
	 */
	public static double getHomeWinProbability(Team home, Team away) {
		double diff = home.getElo() - away.getElo();
		return 1.0 / (Math.pow(10.0, (-diff / 400.0)) + 1);
	}
	
	
	/**
	 * Gets the delta for Team A based on the result "aWon" vs Team B.
	 * This function is NOT destructive! It does not manipulate either team object!
	 * For a correct elo change, if "The Bulldogs" played "The Tigers", and won, this function
	 * should be called twice:
	 * getEloDelta(bulldogs, tigers, true);
	 * getEloDelta(tigers, bulldogs, false);
	 * 
	 * @param a - the team whose Elo delta is being calculated
	 * @param b - the team they played
	 * @param aWon - true if a beat b, false if a lost.
	 * @return the amount a's elo rating should change (positive if increase, negative if decrease)
	 */
	public static double getEloDelta (Team a, Team b, boolean aWon) {
		if (aWon) {
			return K * (1 - getHomeWinProbability(a, b));
		} else {
			return -K * getHomeWinProbability(a, b);
		}
	}

}
