TP README SALES ANALYSIST

start-hadoop

chmod 777 map.py reduce.py

hadoop fs -put sales_world_10k.csv /

hadoop jar /opt/hadoop/share/hadoop/tools/lib/hadoop-streaming-3.1.3.jar -input /sales_world_10k.csv -output 
./sales -mapper "python3 /home/mbds/map.py Z"-reducer "python3 /home/mbds/reduce.py Z"

Z = region / pays / type / offline / online ....depending on what the user wants.

example:
hadoop jar /opt/hadoop/share/hadoop/tools/lib/hadoop-streaming-3.1.3.jar -input /sales_world_10k.csv -output 
./sales -mapper "python3 /home/mbds/map.py region" -reducer "python3 /home/mbds/reduce.py region"

hadoop fs -cat ./sales/*

