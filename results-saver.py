from datetime import datetime
from shutil import copyfile

# datetime object containing current date and time
now = datetime.now()

# dd/mm/YY H:M:S
dt_string = now.strftime("%d-%m-%Y_%H-%M-%S")
new_filename = dt_string + "_results.txt"

copyfile("simulationResults.txt", "pastResults/" + new_filename)

