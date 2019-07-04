# NGSSimulator

Currently working on documentation. It's hacked together and limited, feel free to ask me in Discord if you have questions:
Death by Smiley#2670

## Purpose: 
Allows you to simulate NGS divisions.

## How to Run:

Argument examples:
"java -jar NGSSimulator all" --runs the simulation with approach 100K samples for each division estimated and naive using "data/teams.csv"
"java -jar NGSSimulator [div]" --runs the simulation with approach 100K samples for [div] using estimated technique
"java -jar NGSSimulator [div] naive" --runs the simulation with approach 100K samples for [div] using naive technique
"java -jar NGSSimulator [div] [n]" --runs the simulation with approach [n] samples for one [div] estimated technique
"java -jar NGSSimulator [div] [n]" naive --runs the simulation with approach [n] samples for [div] using naive technique
"java -jar NGSSimulator [div] [n] [file]" --runs the simulation with approach [n] samples for [div] using naive technique
                             using [file] as the teamfile.
                             
[div] - Any division, "H", "A", "BE", "BW", "CE", "CW", "DW", "DE"
[n] - Any positive number (the number of trials you want. Larger is more precise, but takes longer.
[file] - The name of a correctly formatted teams file in the /data subfolder.
all - Literally the string all
naive - Literally the string naive

##Naive vs. Estimated
"All Models are Wrong, Some are Useful"

From NGS Admins, I got the initial MMRS for each team. I adjusted these to form Elo ratings with the mean around 1500 across all divisions. By
default, this program uses the estimated approach, which uses these starting Elo ratings. However, I fully recognize these starting Elo ratings
have the chance to starkly miss a team's quality. As such, I may change them manually in the future, especially for outliers, but I largely
trust the games, as they are played, to change the Elo ratings to their correct place.

Still, if you don't trust my Elo ratings, simply use "naive". Naive assumes every team in a particular division starts at the same rating (that
division's average elo rating). This means that a Heroic team still has a higher Elo than a Division A team, but otherwise we don't make
any assumptions.

## Setup:

Simply download the repository and run the Jar file in the NGS Simulator Directory

## Data Folder
This folder contains the teams.csv file and the schedules.

###Teams.csv

This file MUST be in the data folder. The default teamfile is "teams.csv", which includes DBSmiley's original estimations of NGS teams. 

If you wish to altar these adjustments, either edit the teams.csv file, or save a new copy with a different name, modify that, and use the 
last command line option above.

###Schedule files

All schedule starting CSV files can be found here: https://docs.google.com/spreadsheets/d/1fIQ0mtCZi0d7qtc1FGJ2uZ5dMYS8i9Eq1rzneE7Nhb4/edit?usp=sharing

DBSmiley will keep these sheets up to date. You can redownload them here using File -> Download As -> Csv (current sheet). By default, the schedule
downloads as something like "Schedules - H". Do not change the name. Simply copy and paste that file to the data/schedules/ folder and overwrite the previous schedule.

###Caveats

This program does NOT simulate a team's KDA (I don't even begin to know how to do that), as such it doesn't take KDA tiebreaker into account (it does
however take head-to-head tiebreaker's into account). As such, it may say a team has a small chance of making the playoff.

100% is not necessarily 100% and 0% is not necessarily 0%. Example, let's say you put my team in Heroic. We would get shit stomped. We'd have
almost no chance of making playoffs. If you simulated my team in Heroic, they would get to playoffs 0% of the time. But that doesn't mean
it's mathematically impossible. Usually, we won't see any "mathematical certainty/impossibilities" until Week 5ish. So it's a caveat worth noting.

The Elo ratings for each team are probably bad. Especially if you don't upload your replays to Heroes Profile. Even if you do, MMR can never
be the whole story, and I don't have time to watch every single team play enough to form subjective nuanced opinions of their quality. If you
don't like my Elo ratings, use Naive

###Coming Features
1) List all teams by Elo Rating
2) UI? Maybe? Should be easy enough since I used 3 tier architecture, but I also suck at UI design so....*shrug*