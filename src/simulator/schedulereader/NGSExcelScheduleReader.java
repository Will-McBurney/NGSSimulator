package simulator.schedulereader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import simulator.NGSDivisionSchedule;
import simulator.NGSMatch;
import simulator.NGSTeam;

public class NGSExcelScheduleReader extends NGSScheduleReader {
	private String filename = "data/schedules/Schedules.xlsx";
	private static ArrayList<NGSMatch> matches;
	private static String memoDivName = null;

	@Override
	public NGSDivisionSchedule getDivisionSchedule(List<NGSTeam> teams, String divisionName, boolean fiftyFifty) {
		HashMap<String, NGSTeam> teamMap = getTeamMapFromList(teams);
		if (matches != null && divisionName.equals(memoDivName)) {
			return new NGSDivisionSchedule(teamMap, safeCopyMatches(teamMap, fiftyFifty), divisionName);
		}
		memoDivName = divisionName;
		matches = new ArrayList<NGSMatch>();
		try {
			FileInputStream inStream = new FileInputStream(new File(filename));
			Workbook workbook = new XSSFWorkbook(inStream);
			Sheet divSheet = workbook.getSheet(divisionName);
			//System.out.println(divSheet);
			Iterator<Row> rowIter = divSheet.iterator();
			//burn first header row
			rowIter.next();
			while (rowIter.hasNext()) {
				Row r = rowIter.next();
				if (r.getCell(0) == null || r.getCell(0).getStringCellValue().startsWith("#")) {
					continue;
				}
				//System.out.println(r.getLastCellNum());
				NGSTeam home = teamMap.get(r.getCell(0).getStringCellValue());
				NGSTeam away = teamMap.get(r.getCell(1).getStringCellValue());
				//System.out.println(home + " : " + away);
				if (r.getCell(2) == null) {
					matches.add(new NGSMatch(home, away, fiftyFifty));
				} else {
					boolean isForfeit = false;
					if (r.getCell(4) != null && r.getCell(4).getNumericCellValue() == 1.0) {
						isForfeit = true;
					}
					home.recordMatchResult(away, (int) r.getCell(2).getNumericCellValue(),
							(int) r.getCell(3).getNumericCellValue(), isForfeit);
					matches.add(new NGSMatch(home, away, (int) r.getCell(2).getNumericCellValue(),
							(int) r.getCell(3).getNumericCellValue(), isForfeit));
					}				
			}
			
			
			workbook.close();	
			return new NGSDivisionSchedule(teamMap, safeCopyMatches(teamMap, fiftyFifty), divisionName);
		} catch (FileNotFoundException e) {
			System.out.println("Excel file not in the right place. Should be in data/schedules/Schedules.xlsx");
			return null;
		} catch (IOException e) {
			System.out.println("IO Problem with Excel file");
			return null;
		}
	}

	private List<NGSMatch> safeCopyMatches(HashMap<String, NGSTeam> teamMap, boolean fiftyFifty) {
		List<NGSMatch> out = new ArrayList<NGSMatch>();
		for (NGSMatch match : matches) {
			NGSTeam home = teamMap.get(match.getHomeTeam().getAbbreviation());
			NGSTeam away = teamMap.get(match.getAwayTeam().getAbbreviation());
			if (!match.isResolved()) {
				out.add(new NGSMatch(home, away, fiftyFifty));
			} else {
				home.recordMatchResult(away, match.getHomeScore(), match.getAwayScore(), match.isForfeit());
				out.add(new NGSMatch(home, away, match.getHomeScore(), match.getAwayScore(), fiftyFifty));
			}
		}
		return out;
	}
	
	

}
