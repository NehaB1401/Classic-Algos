import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.sun.corba.se.impl.orbutil.graph.Node;


/**
 * File Name: GraphDijkstra.java 
 * Implements Dijkstra's algorithms
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphDijkstra.java
 */

class GraphDijkstra{
	//You can have any number of private variables and private functions
	//DATA GIVEN
	private String t ; //Title
	private String dotFile; //Write tree as a dot file
	private Graph g ;
	String start;
	String end;
	int [] work ;
	double [] cost ;
	

	GraphDijkstra(String t, String dotFile, Graph g, String start,String end, int [] work, double [] cost) {
		this.t = t ;
		this.dotFile = dotFile ;
		this.g = g ;
		this.start = start;
		this.end = end ;
		this.work = work ;
		this.cost = cost ;
		//WRITE YOUR CODE
		
		Double[] verticesWeight = new Double[g.getnumV()];
		int[] parent = new int[g.getnumV()];
		int heapNodes=getDijkstraShortestPath(verticesWeight, parent);
		printallShortestPath(verticesWeight,parent);
		printShortestPath(heapNodes);
	}
	
	private void printShortestPath(int heapNodes) {
		// TODO Auto-generated method stub
		System.out.println(g.getGraphType());
		System.out.println("Num vertices = "+g.getnumV() );
        System.out.println("Num Edges = "+g.getnumE());
		System.out.println("Work Done = "+work[0]);
		System.out.println("Number of Node Added to Heap = "+ heapNodes);
		System.out.println("Shortest path from city " + start + " to city "+ end + " = "+cost[0]);
		System.out.println("-------------------------------------------------------------------------------------");
	}

	private void printallShortestPath(Double[] verticesWeight, int[] parent) {
		// TODO Auto-generated method stub
		for(int i=0;i<parent.length;i++)
		{
			if(start.compareTo(g.getNodeRealName(i))!=0)
			{
				double w = 0.00;
				System.out.println("The best way to go from "+start+ " to "+ g.getNodeRealName(i) + " is as follows");
				w =printShortestPath(i,parent,verticesWeight);
				if(end.compareTo(g.getNodeRealName(i))==0)
					cost[0] = w;
				System.out.println("  Cost "+w);
				System.out.println();
			}
			
		}	
		
	}

	private double printShortestPath(int i, int[] parent, Double[] verticesWeight) {
		// TODO Auto-generated method stub
		double w = 0 ;
		if(g.getNodeRealName(i)==g.getNodeRealName(parent[i]))
		{
			System.out.print(g.getNodeRealName(i));
			return w;
		}
		getPath(parent[i], parent,verticesWeight);
		w += verticesWeight[i];
		System.out.print("->"+g.getNodeRealName(i));
		return w;
		
	}

	private void getPath(int par, int[] parent, Double[] verticesWeight) {
		// TODO Auto-generated method stub
		for(int i=0;i<g.getnumV();i++)
		{
			if(g.getNodeRealName(par) == g.getNodeRealName(i))
			{
				printShortestPath(i, parent, verticesWeight);
			}
				
		}
			
	}

	class NodeData implements Comparator<NodeData>
	{
		public int node;
		public double weight;
		public int parent;
		
		public NodeData(int node, double weight,int parent)
		{
			this.node = node;
			this.weight = weight;
			this.parent = parent;
		}

		public NodeData() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int compare(NodeData o1, NodeData o2) {
			// TODO Auto-generated method stub
			if(o1.weight<o2.weight)
				return -1;
			else if(o1.weight>o2.weight)
				return 1;
			return 0;
		}
		
	}
	private int getDijkstraShortestPath(Double[] verticesWeight, int[] parent) {
		// TODO Auto-generated method stub
		int heapNodes = 0;
		Boolean[] visited = new Boolean[g.getnumV()];
		
		PriorityQueue<NodeData> weight = new PriorityQueue<>(g.getnumV(),new NodeData());
		Graph traversalG = new Graph(GraphTest.GraphType.WEIGHTED_DIRECTED) ;
		
		for(int i=0;i<g.getnumV();i++)
		{
			visited[i] = false;
			parent[i] = i;
			if(start.compareTo(g.getNodeRealName(i))==0)
			{
				verticesWeight[i] = 0.00;
				weight.add(new NodeData(i,verticesWeight[i],parent[i]));
				heapNodes +=1;
			}
			else
			{			
				verticesWeight[i] = Double.POSITIVE_INFINITY;
			}
		}
		
		printAnimatedStack(visited, parent, verticesWeight);
		
		do
		{
			NodeData v = getMinWeightUnivisitedVertex(visited,weight);
			
			if(v==null)
				break;
			visited[v.node] = true;
		
			for(int j=0;j<g.numFanout(v.node);j++)
			{
				double w = 1;
				work[0] += 1;
				int node = g.getNodeFanout(v.node, j);
    			if(g.getGraphType().contains("WEIGHTED"))
    			{
    				w = g.getNodeFanoutEdgeWeight(v.node, j);
    			}
    			if(verticesWeight[node] >w+verticesWeight[v.node])
    			{
    				parent[node] = v.node;
    				weight.add(new NodeData(node, w+verticesWeight[v.node], v.node));
    				heapNodes += 1;
    				verticesWeight[node] = Math.min(verticesWeight[node],w + verticesWeight[v.node]);
    				int from = traversalG.insertOrFind(g.getNodeRealName(parent[node]), false);
    				int to = traversalG.insertOrFind(g.getNodeRealName(node), false);
    				traversalG.createEdge(from, to, verticesWeight[node], true);
    				traversalG.createEdge(to, from, verticesWeight[node], false);
    			}
    		
			}
			System.out.println("Working on Vertex: "+g.getNodeRealName(v.node));
			printAnimatedStack(visited, parent, verticesWeight);
			
		}
		while(true);
		
		System.out.println();
		traversalG.writeDot(dotFile);
		return heapNodes;
	}

	private void printAnimatedStack(Boolean[] visited, int[] parent, Double[] VerticesWeight)
	{
		//space can be saved by declaring enum of each fixed string (T, F, L or extra space) 
		System.out.print("   ");

		for(int i=0;i<visited.length;i++)
		{
			System.out.print(g.getNodeRealName(i)+ "    ");

		}
		System.out.println();
		System.out.print("   ");
		for(int i=0;i<visited.length;i++)
		{
			if(visited[i])
				System.out.print("T");   
			else
				System.out.print("F");
			System.out.print("    ");
		}
		System.out.println();
		System.out.print("   ");
		for(int i=0;i<visited.length;i++)
		{
			if(VerticesWeight[i]==Double.POSITIVE_INFINITY)
				System.out.print("L    ");
			else
				System.out.print(VerticesWeight[i] + "  ");
		}
		System.out.println();
		System.out.print("   ");
		for(int i=0;i<visited.length;i++)
		{
			System.out.print(g.getNodeRealName(parent[i])+ "    ");
		}
		System.out.println();
	}
	
	private NodeData getMinWeightUnivisitedVertex(Boolean[] visited, PriorityQueue<NodeData> weight) {
		// TODO Auto-generated method stub
		
		while(weight.size()!=0)
		{
			NodeData nd =  weight.poll();
			if(!(visited[nd.node]))
				return nd;			
		} 
		
		return null;
	}

	public static void main(String[] args) {
		System.out.println("GraphDijkstra.java starts");
		System.out.println("Use GraphTest.java to test");
		System.out.println("GraphDijkstra.java Ends");
	}
}