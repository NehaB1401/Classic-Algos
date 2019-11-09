import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import com.sun.corba.se.impl.orbutil.graph.Node;


/**
 * File Name: GraphBfs.java 
 * Breadth First Search on a graph
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphBfs.java
 */

class GraphBfs{
	private String t ;
	private Graph g ;
	String start;
	//Output
	private int [] work ;
	private int [] size ;
	private int [] bfsorder;
	private int [] bfspath ;
	//You can have any number of private variables and private funcions
	
	GraphBfs(String t, Graph g, String start, int [] work, int[] size, int [] bfsorder, int [] bfspath) {
		this.t = t ;
		this.g = g ;
		this.start = start;
		this.work = work ;
		this.size = size ;
		this.bfsorder = bfsorder ;
		this.bfspath = bfspath ;
    //WRITE YOUR CODE
		System.out.println("Num vertices = "+g.getnumV() );
        System.out.println("Num Edges = "+g.getnumE());
		
	    boolean[] visited	 = new boolean[g.getnumV()]; 
		Queue<NodeData> nd =  new LinkedList<>();
		Map<String,Integer> hm = new HashMap<>();
		work[0] = g.getnumV();
		for(int i=0;i<g.getnumV();i++)
		{
			if(start.compareTo(g.getNodeRealName(i))==0)
			{				
				size[0] = bfs(i,visited,nd,bfsorder,bfspath);
			}
		}
		System.out.println("Work Done = "+work[0]);
		System.out.println("Size = "+size[0]);
		
		System.out.print("BFS order = ");
		for(int i = 0;i<size[0];i++)
		{
			System.out.print( g.getNodeRealName(bfsorder[i]) + " ");
			hm.put(g.getNodeRealName(bfsorder[i]), i);
		}
		
		System.out.println();
		System.out.print("BFS path = ");
		for(int i = 0;i<size[0];i++)
		{
			System.out.print(g.getNodeRealName(bfspath[i]) + " ");
		}
		System.out.println();
		
		
		for(int i=0;i<size[0];i++)
		{
			Stack<String> s = new Stack<>();			
			getShortestPath(i,hm,s);
			printShortestPath(s);
			System.out.println();
		}
		
		 System.out.println("-------------------------------------------------------------------------------------");
	}
	
	
	private void printShortestPath(Stack<String> s) {
		// TODO Auto-generated method stub
		while(s.size()!=0)
		{
			System.out.print(s.pop());
		}
	}


	private void getShortestPath(int i, Map<String, Integer> hm, Stack<String> s) {
		// TODO Auto-generated method stub	
		
		s.add(g.getNodeRealName(bfsorder[i]));
		if(i == 0)
			return;
		s.add("->");
		getShortestPath(hm.get(g.getNodeRealName(bfspath[i])),hm, s);
		
	}

	private class NodeData{
		private int node;
		private int level;
		private int from;
		
		NodeData(int node,int level, int from)
		{
			this.node = node;
			this.level = level;
			this.from = from;
		}
	}

	private int bfs(int i, boolean[] visited, Queue<NodeData> nd, int[] bfsorder, int[] bfspath) {
		// TODO Auto-generated method stub
		
			int index = 0;
			visited[i] = true;	
			
			NodeData nodeData = new NodeData(i,i, i); 
			nd.add(nodeData);			
			while(nd.size()!=0)
			{
				nodeData = nd.poll();
				int s = nodeData.node;
				bfsorder[index] = s;
				bfspath[index++] = nodeData.from;
				
				if(s==i)
					nodeData.level = 0;

				for(int j=0;j<g.numFanout(s);j++)
				{
					work[0] += 1;	
					if(!(visited[g.getNodeFanout(s, j)]))
					{
						NodeData nodeData2 = new NodeData(g.getNodeFanout(s, j), -1,-1); 
						visited[g.getNodeFanout(s, j)] = true;
						nodeData2.level = nodeData.level+1;
						nodeData2.from = s;
						nd.add(nodeData2);
					}
				}
			}
	
		return index;

	}

	public static void main(String[] args) {
		System.out.println("GraphBfs.java starts");
		System.out.println("Use GraphTest.java to test");
		System.out.println("GraphBfs.java Ends");
	}
}
