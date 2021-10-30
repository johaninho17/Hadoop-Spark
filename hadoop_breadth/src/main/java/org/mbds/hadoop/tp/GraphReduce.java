package org.mbds.hadoop.tp;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class GraphReduce extends Reducer<Text, GraphNodeWritable, Text, GraphNodeWritable>
{
	public void reduce(Text key, Iterable<GraphNodeWritable> values, Context context) throws IOException, InterruptedException
	{
		GraphNodeWritable newgraph = new GraphNodeWritable("||-1");
			
		Iterator<GraphNodeWritable> i=values.iterator();
		while(i.hasNext())
		{
			GraphNodeWritable value = i.next();
			
			if(value.depth>newgraph.depth)
				newgraph.depth=value.depth;
			
			if(String.join(",",value.neighbors).length()>String.join(",",newgraph.neighbors).length())
				newgraph.neighbors=value.neighbors;
			
			if((newgraph.color.equals("")) || ((newgraph.color.equals("BLANC") && (value.color.equals("GRIS") ||
				value.color.equals("NOIR"))) || (newgraph.color.equals("GRIS") && (value.color.equals("NOIR")))))
			{
				newgraph.color=value.color;
			}
		}
		
		if(!newgraph.color.equals("NOIR"))
			context.getCounter(Graph.GRAPH_COUNTERS.NB_NODES_UNFINISHED).increment(1);
		
		context.write(key, newgraph);
	}
}

