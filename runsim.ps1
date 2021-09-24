echo "Starting"
cd C:\Users\Will\Documents\GitHub\NGSSimulator\NGSSimulator
echo "Getting schedules from NGS website"
python data\schedules\schedule_getter.py
echo "Replacing old schedule"
rm data\schedules\Schedules.xlsx
mv Schedules.xlsx data\schedules\Schedules.xlsx
echo "Saving old results"
python results-saver.py
rm simulationResults.txt
echo "Simulating....this is going to take a while"
echo "Simulating Heroic"
Get-Date -Format "dddd MM/dd/yyyy HH:mm K" > simulationResults.txt
java -jar NGSSimulator.jar H naive >> simulationResults.txt
java -jar NGSSimulator.jar H >> simulationResults.txt
java -jar NGSSimulator.jar H fifty >> simulationResults.txt
echo "Simulating Nexus"
java -jar NGSSimulator.jar N naive >> simulationResults.txt
java -jar NGSSimulator.jar N >> simulationResults.txt
java -jar NGSSimulator.jar N fifty >> simulationResults.txt
echo "Simulating A-East"
java -jar NGSSimulator.jar A naive >> simulationResults.txt
java -jar NGSSimulator.jar A >> simulationResults.txt
java -jar NGSSimulator.jar A fifty >> simulationResults.txt
echo "Simulating B-Northeast"
java -jar NGSSimulator.jar BNE naive >> simulationResults.txt
java -jar NGSSimulator.jar BNE >> simulationResults.txt
java -jar NGSSimulator.jar BNE fifty >> simulationResults.txt
echo "Simulating B-Southeast"
java -jar NGSSimulator.jar BSE naive >> simulationResults.txt
java -jar NGSSimulator.jar BSE >> simulationResults.txt
java -jar NGSSimulator.jar BSE fifty >> simulationResults.txt
echo "Simulating B-West"
java -jar NGSSimulator.jar BW naive >> simulationResults.txt
java -jar NGSSimulator.jar BW >> simulationResults.txt
java -jar NGSSimulator.jar BW fifty >> simulationResults.txt
echo "Simulating C-East"
java -jar NGSSimulator.jar CE naive >> simulationResults.txt
java -jar NGSSimulator.jar CE >> simulationResults.txt
java -jar NGSSimulator.jar CE fifty >> simulationResults.txt
echo "Simulating C-West"
java -jar NGSSimulator.jar CW naive >> simulationResults.txt
java -jar NGSSimulator.jar CW >> simulationResults.txt
java -jar NGSSimulator.jar CW fifty >> simulationResults.txt
echo "Simulating D-NorthEast"
java -jar NGSSimulator.jar DNE naive >> simulationResults.txt
java -jar NGSSimulator.jar DNE >> simulationResults.txt
java -jar NGSSimulator.jar DNE fifty >> simulationResults.txt
echo "Simulating D-SouthEast"
java -jar NGSSimulator.jar DSE naive >> simulationResults.txt
java -jar NGSSimulator.jar DSE >> simulationResults.txt
java -jar NGSSimulator.jar DSE fifty >> simulationResults.txt
echo "Simulating D-West"
java -jar NGSSimulator.jar DW naive >> simulationResults.txt
java -jar NGSSimulator.jar DW >> simulationResults.txt
java -jar NGSSimulator.jar DW fifty >> simulationResults.txt
echo "Simulating E-East"
java -jar NGSSimulator.jar EE naive >> simulationResults.txt
java -jar NGSSimulator.jar EE >> simulationResults.txt
java -jar NGSSimulator.jar EE fifty >> simulationResults.txt
echo "Simulating E-West"
java -jar NGSSimulator.jar EW naive >> simulationResults.txt
java -jar NGSSimulator.jar EW >> simulationResults.txt
java -jar NGSSimulator.jar EW fifty >> simulationResults.txt
echo "Simulation complete, pushing to Github"
git add simulationResults.txt
git add pastResults\*
git commit -m "Batch Simulation Results commit"
git push
echo "Done! Have a great day!"