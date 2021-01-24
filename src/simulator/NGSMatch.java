package simulator;

import simulator.utils.EloLibrary;

public class NGSMatch implements Match{
	private boolean resolved;
	private NGSTeam home, away;
	private int homeScore, awayScore;
	private boolean forfeit;
	private boolean fiftyFifty;
	
	/**
	 * Constructor for unplayed match
	 * @param home
	 * @param away
	 */
	public NGSMatch(NGSTeam home, NGSTeam away) {
		this.home = home;
		this.away = away;
		resolved = false;
		forfeit = false;
		fiftyFifty = false;
	}
	
	public NGSMatch(NGSTeam home, NGSTeam away, boolean fiftyFifty) {
		this.home = home;
		this.away = away;
		resolved = false;
		forfeit = false;
		this.fiftyFifty = fiftyFifty;
	}
	
	/**
	 * Constructor for played match
	 * @param home
	 * @param away
	 * @param homeScore
	 * @param awayScore
	 */
	public NGSMatch (NGSTeam home, NGSTeam away, int homeScore, int awayScore) {
		this.home = home;
		this.away = away;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		resolved = true;
		forfeit = false;
	}	
	
	/**
	 * Constructor for played match with forfeit
	 * @param home
	 * @param away
	 * @param homeScore
	 * @param awayScore
	 */
	public NGSMatch (NGSTeam home, NGSTeam away, int homeScore, int awayScore, boolean forfeit) {
		this.home = home;
		this.away = away;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		resolved = true;
		this.forfeit = forfeit;
	}	
	

	/**
	 * True if match has been played or simulated.
	 */
	@Override
	public boolean isResolved() {
		return resolved;
	}	
	
	/**
	 * Simulates the match if it has not been played or simulated yet.
	 */
	@Override
	public void resolve() {
		if (!resolved) {
			//odds the home team wins
			double r = EloLibrary.getHomeWinProbability(home, away);
			if (fiftyFifty) {
				r = 0.5;
			}
			/** this section is for checking playoff locking **/
			String stooge = "PRA";
			if (home.getAbbreviation().equals(stooge)) {
				r = 0.0;
			} else if (away.getAbbreviation().equals(stooge)) {
				r = 1.0;
			}
			
			
			//Best of 3 simulation
			while (homeScore < 2 && awayScore < 2) {
				if(Math.random() < r) {
					homeScore++;
				} else {
					awayScore++;
				}
			}
			//loop postcondition: exactly one team has 2 wins
			home.recordSimulatedResult(away, homeScore, awayScore);
			resolved = true;
		}
	}

	@Override
	public Team getHomeTeam() {
		return home;
	}

	@Override
	public int getHomeScore() {
		return homeScore;
	}

	@Override
	public Team getAwayTeam() {
		return away;
	}

	@Override
	public int getAwayScore() {
		return awayScore;
	}
	
	
	public boolean isForfeit() {
		return forfeit;
	}
	
	@Override
	public String toString() {
		if (resolved) {
			if (isForfeit()) {
				return home.getAbbreviation() + " - " + homeScore + " : " + away.getAbbreviation() + " - " + awayScore + " - FORFEIT";
			} else {
				return home.getAbbreviation() + " - " + homeScore + " : " + away.getAbbreviation() + " - " + awayScore;
			}
		} else {
			return home.getAbbreviation() + " : " + away.getAbbreviation() + " UNPLAYED ";
		}
	}
}
