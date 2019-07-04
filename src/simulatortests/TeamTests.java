package simulatortests;

import simulator.NGSTeam;
import simulator.Team;

public class TeamTests {
	public static void main(String[] args) {
		Team t = new NGSTeam("Team Brain Dead", "TBD", 1111);
		System.out.println(t);
		System.out.println(t.toStringDetailed());
		
		Team g = new NGSTeam("Underseeded Cheat Assholes", "UCA", 1500);
		
		Team b = new NGSTeam("Team Shitters", "SHIT", -5);
		
		Team a = new NGSTeam("Mediocre Fucks", "MF", 1000);
		
		Team f = new NGSTeam("Quitter Dick Weasels", "QDW", 500); //forfeits the match against t
		
		t.recordMatchResult(g, 0, 2); //g dominates t
		b.recordMatchResult(t, 0, 2); //t dominates b
		t.recordMatchResult(a, 2, 1); //g narrow wins over t
		f.recordMatchResult(t, 0, 0); //no contest
		
		System.out.println(t.toStringDetailed());
		System.out.println(g.toStringDetailed());
	}
}
