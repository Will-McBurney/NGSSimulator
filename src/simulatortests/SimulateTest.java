package simulatortests;

import simulator.NGSDivision;

public class SimulateTest {
	public static void main(String[] args) {
		NGSDivision divH = new NGSDivision("teams.csv", "BSE", true, true);
		//System.out.println(divH);
		System.out.println("=========SIMULATE=============");
		divH.simulate();
		System.out.println(divH);
		System.out.println(divH.getPlayoffSeeding().getSeeding());
	}
}
