package simulatortests;

import simulator.NGSDivision;
import simulator.NGSSimulationAccumulator;
import simulator.NGSTeamSimulationResult;

public class MassSimulateTest {
	public static final int SIMULATION_COUNT = 100000;
	
	public static void main(String[] args) {
		String[] divs = {"H","A","BE"};
		for (int d = 0; d < divs.length; d++) {
			System.out.println("Division : " + divs[d] + " -- Naive");
			NGSDivision ngsd = new NGSDivision("teams.csv", divs[d], true, true);
			NGSSimulationAccumulator acc = new NGSSimulationAccumulator(ngsd.getNGSTeams(), SIMULATION_COUNT);
			for (int i = 0; i < SIMULATION_COUNT; i++) {
				ngsd = new NGSDivision("teams.csv", divs[d], true, true);
				ngsd.simulate();			
				acc.addSimulation(ngsd.getPlayoffSeeding());
			}
			for (NGSTeamSimulationResult tsr : acc.getResults()) {
				System.out.println(tsr.toString(SIMULATION_COUNT));
			}
			
			System.out.println("Division : " + divs[d] + " -- Elo biased");
			ngsd = new NGSDivision("teams.csv", divs[d], false, true);
			acc = new NGSSimulationAccumulator(ngsd.getNGSTeams(), SIMULATION_COUNT);
			for (int i = 0; i < SIMULATION_COUNT; i++) {
				ngsd = new NGSDivision("teams.csv", divs[d], false, true);
				ngsd.simulate();			
				acc.addSimulation(ngsd.getPlayoffSeeding());
			}
			for (NGSTeamSimulationResult tsr : acc.getResults()) {
				System.out.println(tsr.toString(SIMULATION_COUNT));
			}
		}
	}
}
