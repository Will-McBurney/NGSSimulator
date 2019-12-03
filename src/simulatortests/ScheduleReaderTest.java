package simulatortests;

import java.util.List;

import simulator.NGSTeam;
import simulator.schedulereader.NGSScheduleReader;
import simulator.teamreader.EstimatedNGSTeamReader;
import simulator.teamreader.NGSTeamReader;

public class ScheduleReaderTest {
	public static void main(String[] args) {
		NGSTeamReader tr = new EstimatedNGSTeamReader("teams.csv");
		List<NGSTeam> teams = tr.getTeamsForDivision("H");
		NGSScheduleReader reader = new NGSScheduleReader();
		System.out.println(reader.getDivisionSchedule(teams, "H", false));
		
		
	}

}
