import sys
if str(sys.argv[1]).upper() =="REGION":
    for line in sys.stdin:
        line = line.split(",")
        print(f"{line[0]}\t{line[-1]}")

if str(sys.argv[1]).upper() =="PAYS":
    for line in sys.stdin:
        line = line.split(",")
        print(f"{line[1]}\t{line[-1]}")

if str(sys.argv[1]).upper() =="TYPE":
    for line in sys.stdin:
        line = line.split(",")
        print(f"{line[2]}\t{line[-1]}")

if str(sys.argv[1]).upper() =="ONLINE":
    for line in sys.stdin:
        line = line.split(",")
        print(f"{line[2]} {line[3]}\t{line[-1]}")

if str(sys.argv[1]).upper() =="OFFLINE":
    for line in sys.stdin:
        line = line.split(",")
        print(f"{line[2]} {line[3]}\t{line[-1]}")