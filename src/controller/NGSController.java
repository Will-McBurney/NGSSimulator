package controller;

import java.util.HashMap;
import java.util.List;

import simulator.NGSDivision;
import simulator.NGSSimulationAccumulator;
import simulator.NGSTeamSimulationResult;
import simulator.teamreader.EstimatedNGSTeamReader;
import simulator.teamreader.NGSTeamReader;
import simulator.teamreader.NaiveNGSTeamReader;
import simulator.NGSTeam;

public class NGSController {
	private boolean isNaive;
	private int trials;
	private String teamFileName;
	private boolean fiftyFifty;
	
	public NGSController() {
		isNaive = false;
		trials = 100000;
		teamFileName = "teams.csv";
	}
	
	public NGSController(boolean isNaive, int trials, String teamFileName) {
		this.isNaive = isNaive;
		this.trials = trials;
		this.teamFileName = teamFileName;
	}
	
	public void setNaive(boolean isNaive) {
		this.isNaive = isNaive;
	}
	
	public int getTrials() {
		return trials;
	}
	
	public void setTrials(int trials) {
		this.trials = trials;
	}
	
	public void setTeamFileName(String teamFileName) {
		this.teamFileName = teamFileName;
	}
	
	public List<NGSTeamSimulationResult> getDivisionPrediction(String divisionName) {
		String naive = "Fifty/Fifty";
		if (!this.fiftyFifty) {
			naive = isNaive?"Naive":"Estimated"; 
		}
		System.out.println("Division : " + divisionName + " -- " + naive); //header
		NGSDivision ngsd = new NGSDivision(teamFileName, divisionName, isNaive, fiftyFifty);
		NGSSimulationAccumulator acc = new NGSSimulationAccumulator(ngsd.getNGSTeams(), trials);
		for (int i = 0; i < trials; i++) {
			ngsd = new NGSDivision("teams.csv", divisionName, isNaive, fiftyFifty);
			ngsd.simulate();			
			acc.addSimulation(ngsd.getPlayoffSeeding());
		}
		return acc.getResults();
	}
	
	public HashMap<NGSTeam, Double> getAllEloScores() {
		HashMap<NGSTeam, Double> eloMap = new HashMap<NGSTeam, Double>();
		String[] divisions = {"S", "H", "AE", "AW", "BE1", "BE2", "BW", "CE", "CW", "DE", "DW", "E"};
		NGSTeamReader tr = isNaive ?  new NaiveNGSTeamReader(teamFileName) : new EstimatedNGSTeamReader(teamFileName);
		for (String division : divisions) {
			NGSDivision ngsd = new NGSDivision(teamFileName, division, isNaive, fiftyFifty);
			List<NGSTeam> teams = ngsd.getNGSTeams();
			for (NGSTeam t : teams) {
				eloMap.put(t, t.getElo());
			}
		}
		return eloMap;
		
	}

	public void setFifty(boolean b) {
		fiftyFifty = b;
	}
	
	
}
