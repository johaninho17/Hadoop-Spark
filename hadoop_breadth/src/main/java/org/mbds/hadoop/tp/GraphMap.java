package org.mbds.hadoop.tp;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Writable;

public class GraphMap extends Mapper<Text, GraphNodeWritable, Text, GraphNodeWritable>
{
	private static final String GREY="GRIS";
	
	protected void map(Text key, GraphNodeWritable node, Context context) throws IOException, InterruptedException
	{
		if(node.color.equals(GREY))
		{
			for(int i=0; i<node.neighbors.length; ++i)
			{
				if(node.neighbors[i].equals(""))
					continue;
				context.write(new Text(node.neighbors[i]), new GraphNodeWritable("|"+GREY+"|"+Integer.toString(node.depth+1)));
			}
			node.color = "NOIR";
			context.write(key, node);
			//context.write(key, new GraphNodeWritable(String.join(",",node.neighbors)+"|NOIR|"+Integer.toString(node.depth)));
			}
		else
			context.write(key, node);
	}

}