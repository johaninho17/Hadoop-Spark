#pyspark --master "local[2]"
#graph=sc.textFile('graph_input.txt')
#rdd = graph.map(lambda x: x.split('\t')) 
#rdd2 = rdd.mapValues(lambda x: x.split('|')) returns valneigh

#valneigh = [('1', ['2,5', 'GRIS', '0']), ('2', ['3,4', 'BLANC', '-1']), ('3', ['6', 'BLANC', '-1']), ('4', ['', 'BLANC', '-1']), ('5', ['6', 'BLANC', '-1']), ('6', ['', 'BLANC', '-1'])]


#copy and past code then run main

from pyspark import SparkContext

#nb_no_blck =sc.accumulator(0)
#sc = SparkContext("local[2]", "graph")

def depth(graphlist):
    l = []
    key = graphlist[0]
    value = graphlist[1]
    neighbors = value[0].split(',') #splits neighbors        
    if value[1] == "GRIS":
        for neighbor in neighbors:
            if neighbor == '':
                continue
            l.append(tuple([neighbor,["", 'GRIS', int(value[2])+1]]))
        value[1] = "NOIR"
    l.append(tuple([key,value]))    
    return l

def reducer(a,b):
    global nb_no_blck
    l2=[]
    if a[0] > b[0] :               #check sons
        nb_no_blck.add(1)
        l2.append(a[0])  
    else :
        nb_no_blck.add(1)
        l2.append(b[0])
    l2.append('GRIS')               # color
    if int(a[2]) > int(b[2]) :     #checks value
        nb_no_blck.add(1)
        l2.append(a[2])
    else :
        nb_no_blck.add(1)
        l2.append(b[2])
    return l2

nb_no_blck = sc.accumulator(1)

def main():
    graph=sc.textFile('file:///home/mbds/graph_input.txt')
    rdd = graph.map(lambda x: x.split('\t'))
    rddmap = rdd.mapValues(lambda x: x.split('|'))
    #nb_no_blck = sc.accumulator(1)
    tmp = 0
    while nb_no_blck.value > tmp:
        tmp = nb_no_blck.value # temporary variable
        rddflatmap = rddmap.flatMap(depth)# transformation
        rddreduced = rddflatmap.reduceByKey(reducer) # transformation
        rddreduced.count()  # action
        rddmap = rddreduced
    return rddreduced    
     #print(f"var temp ==> {tmp}")
        #print(f"var nb_no_blck ==> {nb_no_blck.value}")
   

rdd = main()
rdd.collect()
