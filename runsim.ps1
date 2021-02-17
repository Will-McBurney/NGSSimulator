python schedule_getter.py
rm data\schedules\Schedules.xlsx
mv Schedules.xlsx data\schedules\Schedules.xlsx
rm simulationResults.txt
java -jar NGSSimulator.jar all > simulationResults.txt
git add simulationResults.txt
git commit -m "Batch Simulation Results commit"
git push