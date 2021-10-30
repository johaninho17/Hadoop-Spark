package org.mbds.hadoop.tp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class Graph
{
	public enum GRAPH_COUNTERS {
		NB_NODES_UNFINISHED
	};
public static void main(String[] args) throws Exception
{
	Configuration conf=new Configuration();
	String[] ourArgs=new GenericOptionsParser(conf, args).getRemainingArgs();
	String input_path=ourArgs[0];
	String output_path_prefix=ourArgs[1];
	String output_path="";
	int nb_step=0;
	long nb_nodes_non_black=0;
	
	while(true)
	{
		if(nb_step>0)
		{
			input_path=output_path+"/RESULTATS-r*";
			if(nb_nodes_non_black==0)
			{
				System.out.println("All nodes seen; final output directory: "+output_path);
				break;
			}
		}
		
	nb_step=nb_step+1;
	output_path=output_path_prefix+"-step-"+nb_step;
	System.out.println("Execution cycle #"+nb_step+": input '"+input_path+"', output '"+output_path+"'");
	
	Job job=Job.getInstance(conf, "Parcours de graphes v1.0");
	
	job.setJarByClass(Graph.class);
	job.setMapperClass(GraphMap.class);
	job.setReducerClass(GraphReduce.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(GraphNodeWritable.class);
	
	FileInputFormat.addInputPath(job, new Path(input_path));
	FileOutputFormat.setOutputPath(job, new Path(output_path));
	
	job.setInputFormatClass(GraphInputFormat.class);
	job.setOutputFormatClass(GraphOutputFormat.class);
	
	if(!job.waitForCompletion(true))
	{
		System.out.println("ERROR: execution cycle #"+nb_step+" failed.");
		System.exit(-1);
	}
	
	Counters cn=job.getCounters();
	Counter c1=cn.findCounter(GRAPH_COUNTERS.NB_NODES_UNFINISHED);

		if(c1!=null)
			nb_nodes_non_black=c1.getValue();
	}
		
	System.exit(0);
	}
}