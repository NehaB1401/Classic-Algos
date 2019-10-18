import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sun.beans.decoder.ValueObject;
import com.sun.corba.se.spi.orbutil.fsm.Input;


/**
 * File Name: GraphBuilder.java 
 * All routines that builds Graph
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphBuilder.java
 */

class GraphBuilder{
	private Graph g ;
	//You can have any number of private variables and private funcions
	
	GraphBuilder(Graph g, String f) {
		this.g = g ;
		//WRITE YOUR CODE

		List<ArrayList<String>> list=new ArrayList<ArrayList<String>>();
		try {
			String[] data = null;
			Scanner inFile = new Scanner(new File(f));
			while(inFile.hasNext())
			{
				String line = inFile.nextLine();
				data =line.split(" ");
				if (g.getGraphType().contains("WEIGHTED"))
					list.add(new ArrayList<String>(Arrays.asList(data[0], data[1],data[2])));
				else
					list.add(new ArrayList<String>(Arrays.asList(data[0], data[1])));
			}
			inFile.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(list.size()!=0)
		{
			for(List<String> input : list) {
				int from = g.insertOrFind(input.get(0), false);
				int to = g.insertOrFind(input.get(1), false);
				Double weight = 0.0;
				if (g.getGraphType().contains("WEIGHTED"))
					weight = Double.valueOf(input.get(2));

				g.createEdge(from, to, weight, true);
				g.createEdge(to, from, weight, false);
				if(g.isUndirectedGraph())
				{
					g.createEdge(to, from, weight, true);
					g.createEdge(from, to, weight, false);
				}			
				
			}
		}

	}


	public static void main(String[] args) {
		System.out.println("GraphBuilder.java starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("GraphBuilder.java Ends");
	}
}