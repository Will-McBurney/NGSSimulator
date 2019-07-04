/**
 * This class maintains the simulation results for a particular team. Example, say we want to see how Celeb Gaming has
 * done across 10000 simulations. This object would store the abbrv "CTV", and then the count of each time they got
 * a particular playoff seed in the simulation. The counts ArrayList stores:
 * #of times 1 seed at index 0
 * #of times 2 seed at index 1
 * #of times 3 seed at index 2
 * and so on.
 */

package simulator;

import java.util.ArrayList;

public class NGSTeamSimulationResult implements Comparable<NGSTeamSimulationResult>{
	private String abbrv;
	private ArrayList<Integer> counts;
	
	/**
	 * Constructor
	 * Initialized counts arraylist to have as many 0 elements as there are playoff spots
	 * @param abbrv - team abbreviation
	 */
	public NGSTeamSimulationResult(String abbrv) {
		this.abbrv = abbrv;
		counts = new ArrayList<Integer>();
		for (int i = 0; i < NGSPlayoffSeeding.PLAYOFF_SIZE; i++) {
			counts.add(0);
		}
	}
	
	/**
	 * Add one instance of finishing in the playoffs at a particular seed. Note that 1 seed will have position 0,
	 * 2 seed position 1, etc.
	 * @param position
	 */
	public void addCount(int position) {
		counts.set(position, counts.get(position) + 1);
	}

	/**
	 * Generates a String representation of the Simulation Result that prints the team abbreviation, the % of
	 * the time that they were a 1 seed, 2 seed, 3 seed, etc. as well as the total % of the time they made
	 * playoffs with ANY seed.
	 * @param totalRuns
	 * @return
	 */
	public String toString(int totalRuns) {
		int totalCount = 0;
		StringBuilder sb = new StringBuilder();
		sb.append(abbrv + "\t");
		for (int count : counts) {
			totalCount += count;
			double percent = (100.0 * count) / totalRuns;
			sb.append(String.format("%.2f", percent));
			sb.append("\t");
		}
		double percent = (100.0 * totalCount) / totalRuns;
		sb.append(String.format("%.2f", percent)+"\n");
		return sb.toString();
	}

	@Override
	/**
	 * Sorts teams in order of what % of the time they got the 1 seed. if tied, breaks tie on 2 seed. If tied, breaks tie on 3,
	 * and so on
	 */
	public int compareTo(NGSTeamSimulationResult o) {
		for (int i = 0; i < NGSPlayoffSeeding.PLAYOFF_SIZE; i++) {
			if (this.counts.get(i) != o.counts.get(i)) {
				return o.counts.get(i) - this.counts.get(i); //descending
			}
		}
		return 0;
	}
}
