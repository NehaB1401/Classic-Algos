import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


/**
 * File Name: GraphDot.java 
 * Writes graph as a dot file
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019s
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphDot.java
 */

class GraphDot{
	private Graph g ;
	private String fname;
	//You can have any number of private variables

	GraphDot(Graph g, String s) {
		this.g = g ;
		this.fname = s ;
		try(BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fname)))){
							
			 out.write("digraph g {"); 
			    out.newLine();
			    out.write("edge ["); 
			    if(g.isUndirectedGraph())
			    	out.write("dir=none, ");
			    out.write("color=red]");
			    out.newLine();
			    for(int i =0;i<g.getnumV();i++)
			    {
			    	for(int j =0;j<g.numFanout(i);j++)
			    	{
			    		if((i < g.getNodeFanout(i, j) && g.isUndirectedGraph()) ||  !g.isUndirectedGraph())
			    		{
			    			out.write(g.getNodeRealName(i)+ " -> " +g.getNodeRealName(g.getNodeFanout(i, j)));
			    			if(g.getGraphType().contains("WEIGHTED"))
			    				out.write("[label = "+ g.getNodeFanoutEdgeWeight(i, j)+"]");
				    		out.newLine();
			    		}
			    	}
			    }
			    out.write("}");
			    
			    } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
			
	
	}


	public static void main(String[] args) {
		System.out.println("GraphDot.java starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("GraphDot.java Ends");
	}
}