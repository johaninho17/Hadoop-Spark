package org.mbds.hadoop.tp;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.hadoop.io.Writable;

public class GraphNodeWritable implements Writable
{	
	public String [] neighbors = new String [0];
	public String color = "";
	public int depth = 0;
	

	public GraphNodeWritable(String string) 
	{
		graphinput(string);
	}
	
	public GraphNodeWritable() 
	{
	}

	private void graphinput(String string) {
		String[] parts = string.split("\\|");
		neighbors=parts[0].split(",");
		color=parts[1];
		depth=-1;
		
		try 
		{
			depth = Integer.parseInt(parts[2]);
		} 
		catch (Exception e) 
		{
			depth = -1;
		}
	}
	
	public String get_serialized() 
	{
		return String.join(",", neighbors) + "|" + color + "|" + depth;
	}
	
	public void write(DataOutput out) throws IOException 
	{	
		out.write(get_serialized().getBytes());
	}
	
	public void readFields(DataInput in) throws IOException 
	{
		String line= in.readLine();		
		graphinput(line);
	}
}
