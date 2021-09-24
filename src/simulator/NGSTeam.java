package simulator;

import java.util.ArrayList;
import java.util.HashSet;

import simulator.utils.EloLibrary;

public class NGSTeam implements Team {
	private String name;
	private String abbrv;
	private ArrayList<ArrayList<NGSTeam>> results; //NOTE: This assumes each team plays each other team in at least 1 other match
	private double elo;
	private int forfeits;

	public NGSTeam(String name, String abbrv, double startingElo) {
		this.name = name;
		this.abbrv = abbrv;
		this.elo = startingElo;
		this.results = new ArrayList<ArrayList<NGSTeam>>(4);
		for (int i = 0; i < 4; i++) {
			results.add(new ArrayList<NGSTeam>());
		}
	}
	
	public int getForfeits() {
		return forfeits;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getAbbreviation() {
		return abbrv;
	}

	@Override
	public int getMatchWins() {
		return results.get(2).size()+results.get(3).size();
	}

	@Override
	public int getMatchLosses() {
		return results.get(0).size()+results.get(1).size();
	}

	@Override
	public int getMapWins() {
		return 2 * results.get(3).size() + 2 * results.get(2).size() + results.get(1).size();
	}

	@Override
	public int getMapLosses() {
		return 2 * results.get(0).size() + 2 * results.get(1).size() + results.get(2).size();
	}

	@Override
	public int getScore() {
		return results.get(1).size() + 2 * results.get(2).size() + 3 * results.get(3).size();
	}

	/**
	 * Get the number of domination (2-0) victories.
	 * @return
	 */
	public int getDominations() {
		return results.get(3).size();
	}

	@Override
	public double getElo() {
		return elo;
	}
	
	/**
	 * Has this team beat o?
	 * @param o - another team
	 * @return True if this team has beaten (either domination or narrow) team o, false otherwise
	 */
	public boolean hasBeat(NGSTeam o) {
		int wins = 0, losses = 0;
		for (NGSTeam t : results.get(0)) {
			if (t.equals(o)) {
				losses += 2;
			}
		}
		
		for (NGSTeam t : results.get(1)) {
			if (t.equals(o)) {
				losses += 2;
				wins += 1;
			}
		}
		
		for (NGSTeam t : results.get(2)) {
			if (t.equals(o)) {
				losses += 1;
				wins += 2;
			}
		}
		
		for (NGSTeam t : results.get(3)) {
			if (t.equals(o)) {
				wins += 2;
			}
		}
		
		return wins > losses;
	}
	
	/**
	 * Gets the match score of this team from when the played o. Example, if this dominated o,
	 * return 3, since this team got 3 points from the domination.
	 * @param o
	 * @return
	 */
	public int scoreVs(NGSTeam o) {
		for (int i = 0; i < 4; i++) {
			if (results.get(i).contains(o)) {
				return i;
			}
		}
		System.out.println(this.toStringDetailed() + " \n---------\n" + o.toStringDetailed());
		throw new RuntimeException("ERROR: No result between " + this.abbrv + " and " + o.abbrv + " found!");
	}

	@Override
	public void recordMatchResult(Team other, int myMapScore, int otherMapScore, boolean isForfeit) {
		NGSTeam o = (NGSTeam) other;
		if (myMapScore == 2 && otherMapScore == 0) { //domination
			//count as 2.5 map wins due to domination
			if (!isForfeit) {
				this.elo += 2.5 * EloLibrary.getEloDelta(this, other, true);
				o.elo += 2.5 * EloLibrary.getEloDelta(other, this, false);
			}
			//add to respective results sets
			this.results.get(3).add(o);
			o.results.get(0).add(this);
		} else if (myMapScore == 2 && otherMapScore == 1) { //close win
			//count as 2 map wins and 1 map loss
			if (!isForfeit) {
				this.elo += 2 * EloLibrary.getEloDelta(this, other, true);
				o.elo += 2 * EloLibrary.getEloDelta(other, this, false);
				this.elo += EloLibrary.getEloDelta(this, other, false);
				o.elo += EloLibrary.getEloDelta(other, this, true);
			}
			//add to respective results sets
			this.results.get(2).add(o);
			o.results.get(1).add(this);
		} else if (myMapScore == 1 && otherMapScore == 2) { //close loss
			//count as 2 map losses and 1 map win
			if (!isForfeit) {
				this.elo += 2 * EloLibrary.getEloDelta(this, other, false);
				o.elo += 2 * EloLibrary.getEloDelta(other, this, true);
				this.elo += EloLibrary.getEloDelta(this, other, true);
				o.elo += EloLibrary.getEloDelta(other, this, false);
			}
			//add to respective results sets
			this.results.get(1).add(o);
			o.results.get(2).add(this);
		} else if (myMapScore == 0 && otherMapScore == 2) { //pooped on
			//count as 2 map wins due to domination
			if (!isForfeit) {
				this.elo += 2.5 * EloLibrary.getEloDelta(this, other, false);
				o.elo += 2.5 * EloLibrary.getEloDelta(other, this, true);
			}
			//add to respective results sets
			this.results.get(0).add(o);
			o.results.get(3).add(this);
		} else if (myMapScore == 0 && otherMapScore == 0) { //mutual forfeit
			//No MMR Change
			//add to respective results sets
			this.results.get(0).add(o);
			o.results.get(0).add(this);
			this.forfeits++;
			o.forfeits++;
		} else {
			throw new IllegalArgumentException("Invalid map score arguments");
		}
	}


	@Override
	public void recordSimulatedResult(Team other, int myMapScore, int otherMapScore) {
		NGSTeam o = (NGSTeam) other;
		if (myMapScore == 2 && otherMapScore == 0) { //domination
			//add to respective results sets
			this.results.get(3).add(o);
			o.results.get(0).add(this);
		} else if (myMapScore == 2 && otherMapScore == 1) { //close win
			//add to respective results sets
			this.results.get(2).add(o);
			o.results.get(1).add(this);
		} else if (myMapScore == 1 && otherMapScore == 2) { //close loss
			//add to respective results sets
			this.results.get(1).add(o);
			o.results.get(2).add(this);
		} else if (myMapScore == 0 && otherMapScore == 2) { //pooped on
			//add to respective results sets
			this.results.get(0).add(o);
			o.results.get(3).add(this);
		} else if (myMapScore == 0 && otherMapScore == 0) { //mutual forfeit
			//No MMR Change
			//add to respective results sets
			this.results.get(0).add(o);
			o.results.get(0).add(this);
		} else {
			throw new IllegalArgumentException("Invalid map score arguments");
		}
	}
		
	/**
	 * Used to sort teams by their playoff score.
	 */
	@Override
	public int compareTo(Team o) {
		return o.getScore() - this.getScore(); //descending order
	}
	
	@Override
	/**
	 * Equal to teams with the same name. Needed to match the functionality of the hashcode function
	 */
	public boolean equals(Object o) {
		try {
			NGSTeam other = (NGSTeam) o;
			return this.getName().equals(other.getName());
		} catch (ClassCastException e) {
			return false;
		}
	}
	
	/**
	 * Hashes using the string hash of the team name
	 */
	@Override 
	public int hashCode() {
		return name.hashCode();
	}
	
	/**
	 * Just returns team abbreviation	
	 */
	@Override
	public String toString() {
		return abbrv;
	}
	
	@Override
	public String toStringDetailed() {
		StringBuilder sb = new StringBuilder();
		//Line 1, name and abbrv
		sb.append(name);
		sb.append(" - ");
		sb.append(abbrv);
		sb.append("\n");
		//line 2, Elo
		sb.append("\t\tElo: " + String.format("%.0f", elo) + "\n");
		//line 3 - 7: who the team dominated, who they had a narrow win over, lost to, and were dominated by
		sb.append("\t\tDominated: ");
		for (Team t : results.get(3)) {
			sb.append(t+",");
		}
		sb.append("\n\t\tNarrow Win Over: ");
		for (Team t : results.get(2)) {
			sb.append(t+",");
		}
		sb.append("\n\t\tNarrow Loss To: ");
		for (Team t : results.get(1)) {
			sb.append(t+",");
		}
		sb.append("\n\t\tDominated By: ");
		for (Team t : results.get(0)) {
			sb.append(t+",");
		}
		sb.append("\n\t\tScore: " + getScore());
		sb.append("\n\t\tMap W: " + this.getMapWins());
		sb.append("\n\t\tMap L: " + this.getMapLosses());
		
		return sb.toString();
	}

}
