package commandline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import controller.NGSController;
import simulator.NGSTeam;
import simulator.NGSTeamSimulationResult;

public class CLUI {
	private static NGSController con;
	private static final String HELPFILE = "data/helpText";

	public static void main(String[] args) {

		con = new NGSController();
		if (args.length == 0) {
			System.out.print("This program requires arguments. See the documentation or use the \"help\" argument");
		} else if (args.length == 2) {
			if (args[1].equalsIgnoreCase("elo")) {
				if (args[2].equalsIgnoreCase("naive")) {
					con.setNaive(true);
				}
				if (args[2].equalsIgnoreCase("fifty")) {
					con.setFifty(true);
				}
				con.getAllEloScores();
			} else if (args[1].equalsIgnoreCase("naive")) {
				con.setNaive(true);
			} else if (args[1].equalsIgnoreCase("fifty")) {
				con.setFifty(true);
			} else {
				int trials = Integer.parseInt(args[1]);
				con.setTrials(trials);
			}
		} else if (args.length == 3) {
			if (args[1].equalsIgnoreCase("naive")) {
				con.setNaive(true);
			}
			if (args[1].equalsIgnoreCase("fifty")) {
				con.setFifty(true);
			}
		    int trials = Integer.parseInt(args[2]);
			con.setTrials(trials);

		} else if (args.length == 4) {
			if (args[1].equalsIgnoreCase("naive")) {
				con.setNaive(true);
			}
			if (args[1].equalsIgnoreCase("fifty")) {
				con.setNaive(true);
			}
		    int trials = Integer.parseInt(args[2]);
			con.setTrials(trials);
			con.setTeamFileName(args[3]);
		} else if (args.length != 1) {
			System.out.println("Illegal number of arguments. See documentation or \"help\"");
		}
		
		
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("help")) {
				System.out.println("HELP:");
				String s;
				BufferedReader br;
				try {
					br = new BufferedReader(new FileReader(new File(HELPFILE)));
					while ((s = br.readLine()) != null) {
						System.out.println(s);
					}
					br.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if (args[0].equalsIgnoreCase("all")) {
				String[] divisions = {"H","AE","AW","BE","BW","CE","CW","DE","DW","E"};
				boolean[] naiveValues = {true, false};
				for (String div : divisions) {
					for (boolean naive : naiveValues) {
						con.setFifty(false);
						con.setNaive(naive);
						List<NGSTeamSimulationResult> results = con.getDivisionPrediction(div);
						
						StringBuilder sb = new StringBuilder();
						if (results.get(0).getCountsSize() < 8) {
							sb.append("Team\t1\t2\t3\t4\tPlayoff Chance\n");
						} else {
							sb.append("Team\t1\t2\t3\t4\t5\t6\t7\t8\tPlayoff Chance\n");
						}
						for (NGSTeamSimulationResult r : results) {
							sb.append(r.toString(con.getTrials()));
						}
						System.out.println(sb.toString());
					}
					con.setNaive(false);
					con.setFifty(true);
					List<NGSTeamSimulationResult> results = con.getDivisionPrediction(div);
					StringBuilder sb = new StringBuilder();
					
					if (results.get(0).getCountsSize() < 8) {
						sb.append("Team\t1\t2\t3\t4\tPlayoff Chance\n");
					} else {
						sb.append("Team\t1\t2\t3\t4\t5\t6\t7\t8\tPlayoff Chance\n");
					}
					for (NGSTeamSimulationResult r : results) {
						sb.append(r.toString(con.getTrials()));
					}
					System.out.println(sb.toString());
				}
			} else if (args[0].equals("elo")) { //not ready yet - currently gives starting elo, not current
				HashMap<NGSTeam, Double> eloMap = con.getAllEloScores();
				class TeamEloCombo implements Comparable<TeamEloCombo> {
					String name;
					double elo;

					public TeamEloCombo(String name, double elo) {
						this.name = name; this.elo = elo;
					}

					@Override
					public int compareTo(TeamEloCombo o) {
						if (o.elo == this.elo) {return 0;}
						return o.elo > this.elo ? 1 : -1; //descending
					}

				}
				List<TeamEloCombo> eloList = new ArrayList<TeamEloCombo>();
				for (NGSTeam t : eloMap.keySet()) {
					eloList.add(new TeamEloCombo(t.getName(), eloMap.get(t)));
				}
				Collections.sort(eloList);
				for (TeamEloCombo t : eloList) {
					System.out.println(t.name + "\t" + t.elo);
				}
			} else {
				List<NGSTeamSimulationResult> results = con.getDivisionPrediction(args[0]);
				StringBuilder sb = new StringBuilder();
				if (results.get(0).getCountsSize() < 8) {
					sb.append("Team\t1\t2\t3\t4\tPlayoff Chance\n");
				} else {
					sb.append("Team\t1\t2\t3\t4\t5\t6\t7\t8\tPlayoff Chance\n");
				}
				for (NGSTeamSimulationResult r : results) {
					sb.append(r.toString(con.getTrials()));
				}
				System.out.println(sb.toString());
			}
		}
	}
}
