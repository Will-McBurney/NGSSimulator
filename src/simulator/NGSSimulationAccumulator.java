/**
 * This class accumulates simulation results for each team in the division. Each team is mapped to one
 * NGSTeamSimulationResult object, which tracks how often they get each seed.
 */

package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NGSSimulationAccumulator {
	HashMap<String, NGSTeamSimulationResult> results;
	int totalRuns;
	
	public NGSSimulationAccumulator(List<NGSTeam> teams, int totalRuns) {
		results = new HashMap<String, NGSTeamSimulationResult>();
		int seedsNumber = 8;
		if (teams.size() < 8) {
			seedsNumber = 4;
		}
		if (teams.size() == 8) {
			seedsNumber = 6;
		}
		this.totalRuns = totalRuns;
		for (NGSTeam t : teams) {
			results.put(t.getAbbreviation(), new NGSTeamSimulationResult(t.getAbbreviation(), seedsNumber));
		}
	}
	
	public void addSimulation(NGSPlayoffSeeding seeding) {
		for (int position = 0; position < seeding.PLAYOFF_SIZE; position++) {
			String abbrv = seeding.getSeeding().get(position).getAbbreviation();
			if (!results.containsKey(abbrv)) {
				throw new RuntimeException("DATA ERROR: unknown abbrv: " + abbrv);
			}
			results.get(abbrv).addCount(position);
		}
	}
	
	/**
	 * Get the results of the simulation as a String
	 * @return something like:
	 * Team	1		2		3		4		5		6		7		8		Playoff Chance
     * CM	12.42	11.29	10.89	10.39	10.08	9.91	9.33	9.08	83.38
     * DIST	11.78	11.28	10.57	10.60	10.00	9.77	9.50	9.26	82.76
     * FMJ	11.25	10.67	10.57	10.38	10.10	9.95	9.68	9.41	82.00
     * RNDY	10.68	10.45	10.30	10.20	10.13	9.96	9.89	9.76	81.37
     * KS	10.13	10.25	10.24	10.13	10.19	9.83	9.93	9.91	80.62
     * TBD	9.61	9.72	9.83	9.84	10.05	10.20	10.19	10.15	79.58
     * BACK	8.95	9.60	9.71	9.78	10.09	10.12	10.13	10.42	78.80
     * BBB	8.70	9.36	9.54	9.63	9.78	10.17	10.38	10.41	77.97
     * LUC	8.45	8.92	9.42	9.53	9.93	10.02	10.31	10.73	77.31
     * YCST	8.03	8.47	8.93	9.53	9.68	10.07	10.65	10.87	76.22

	 */
	public ArrayList<NGSTeamSimulationResult> getResults() {
		ArrayList<NGSTeamSimulationResult> out = new ArrayList<NGSTeamSimulationResult>();
		for (String s : results.keySet()) {
			out.add(results.get(s));
		}
		//sort by seeding order.
		Collections.sort(out);
		return out;
	}
	
	/** how to convert above output to String
		StringBuilder sb = new StringBuilder();
		sb.append("Team\t1\t2\t3\t4\t5\t6\t7\t8\tPlayoff Chance\n");
		for (NGSTeamSimulationResult r : out) {
			sb.append(r.toString(totalRuns));
		}
		return sb.toString();
	}*/
}
