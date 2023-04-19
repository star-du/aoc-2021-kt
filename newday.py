import sys

USAGE = """Usage: newday.py <day number>"""

arguments = sys.argv[1:]
if len(arguments) != 1:
    print(USAGE)
    sys.exit(1)
try:
    day = int(arguments[0])
except ValueError:
    print(USAGE)
    sys.exit(1)

print(f"Welcome to Day {day}.")
day_str = f"{day:02d}"

# change directory to src
import os
print("Changing directory to src...")
os.chdir("src")

# check if file already exists
if os.path.exists(f"Day{day_str}.kt"):
    print(f"File Day{day_str}.kt already exists.")
    print("Skip!")
    sys.exit(1)

# execute system cp command
import subprocess
print(f"Copying Day00.kt to Day{day_str}.kt")
subprocess.run(["cp", "Day00.kt", f"Day{day_str}.kt"])

# create empty new file
print(f"Creating empty Day{day_str}_test.txt")
with open(f"Day{day_str}_test.txt", "w") as f:
    pass

print(f"Creating empty Day{day_str}.txt")
with open(f"Day{day_str}.txt", "w") as f:
    pass
