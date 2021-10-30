import sys

last_key = None
sum_values = 0

if str(sys.argv[1]).upper() == "REGION" or str(sys.argv[1]).upper() == "PAYS" or str(sys.argv[1]).upper() == "TYPE" :
    for line in sys.stdin:
        key, value = line.split('\t', 1)
        if key != last_key and last_key != None:
            print(last_key, '\t', sum_values)
            sum_values = 0
        try:
            sum_values += float(value.strip())
        except:
            pass
        last_key = key
    print(last_key, "\t", sum_values) 

elif str(sys.argv[1]).upper() == "OFFLINE" or str(sys.argv[1]).upper() == "ONLINE":
    l=0
    for line in sys.stdin:
        l+=1
        key, value = line.split('\t', 1)
        if key != last_key and last_key != None:
            print(last_key, '\t', sum_values, "\t", l)
            sum_values = 0
            l=0
        try:
            sum_values += float(value.strip())
        except:
            pass
        last_key = key
    print(last_key, "\t", sum_values)



