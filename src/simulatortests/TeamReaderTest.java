package simulatortests;

import java.util.List;

import simulator.NGSTeam;
import simulator.Team;
import simulator.teamreader.EstimatedNGSTeamReader;
import simulator.teamreader.NGSTeamReader;
import simulator.teamreader.NaiveNGSTeamReader;

public class TeamReaderTest {
	public static void main(String[] args) {
		NGSTeamReader reader = new EstimatedNGSTeamReader("teams.csv");
		List<NGSTeam> teams = reader.getTeamsForDivision("H");
		for (Team t : teams) {
			System.out.println(t.toStringDetailed());
		}
		
		NGSTeamReader allReader = new NaiveNGSTeamReader("teams.csv");
		List<NGSTeam> allTeams = allReader.getAllTeams();
		System.out.println(allTeams);
		
	}
}
