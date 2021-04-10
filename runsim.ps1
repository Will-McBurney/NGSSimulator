echo "Starting"
cd C:\Users\Will\Documents\GitHub\NGSSimulator\NGSSimulator
echo "Getting schedules from NGS website"
python schedule_getter.py
echo "Replacing old schedule"
rm data\schedules\Schedules.xlsx
mv Schedules.xlsx data\schedules\Schedules.xlsx
echo "Saving old results"
python results-saver.py
rm simulationResults.txt
echo "Simulating....this is going to take a while"
echo "Simulating Heroic"
java -jar NGSSimulator.jar H naive > simulationResults.txt
java -jar NGSSimulator.jar H >> simulationResults.txt
java -jar NGSSimulator.jar H fifty >> simulationResults.txt
echo "Simulating Nexus"
java -jar NGSSimulator.jar N naive >> simulationResults.txt
java -jar NGSSimulator.jar N >> simulationResults.txt
java -jar NGSSimulator.jar N fifty >> simulationResults.txt
echo "Simulating A-East"
java -jar NGSSimulator.jar AE naive >> simulationResults.txt
java -jar NGSSimulator.jar AE >> simulationResults.txt
java -jar NGSSimulator.jar AE fifty >> simulationResults.txt
echo "Simulating A-West"
java -jar NGSSimulator.jar AW naive >> simulationResults.txt
java -jar NGSSimulator.jar AW >> simulationResults.txt
java -jar NGSSimulator.jar AW fifty >> simulationResults.txt
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
echo "Simulating D-East"
java -jar NGSSimulator.jar DE naive >> simulationResults.txt
java -jar NGSSimulator.jar DE >> simulationResults.txt
java -jar NGSSimulator.jar DE fifty >> simulationResults.txt
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