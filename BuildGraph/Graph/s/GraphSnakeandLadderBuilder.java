import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * File Name: GraphSnakeandLadderBuilder.java 
 * All routines that builds SnakeandLadder Graph
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2018
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphSnakeandLadderBuilder.java
 */

class GraphSnakeandLadderBuilder{
	//given data
	private Graph g ;
	private int n ; //Max number on board
	private int[][] l; //ladder
	private int[][] s; //snakes
	//You can have any number of private variables
	
	
	GraphSnakeandLadderBuilder(Graph g, int n, int[][] l, int [][] s) {
		this.g = g ;
		this.n = n ;
		this.l = l ;
		this.s = s;
		buildGraph() ;
	}
	
	private void buildGraph() {
		//WRITE YOUR CODE
		List<ArrayList<Integer>> list=new ArrayList<ArrayList<Integer>>();
		Map<Integer,Integer> sandl = new HashMap<>();
		for(int i=0;i<l.length;i++)
		{
			if(l[i][0]>l[i][1])
			{
				sandl.put(l[i][1], l[i][0]);
			}
			else
				sandl.put(l[i][0], l[i][1]);
		}
		for(int i=0;i<s.length;i++)
		{
			if(s[i][0]<s[i][1])
			{
				sandl.put(s[i][1], s[i][0]);
			}
			else
				sandl.put(s[i][0], s[i][1]);
		}
		for(int i=1;i<n;i++)
		{			
			int dice = 1;			
			while(dice<7 && (i+dice)<n+1)
			{
				if(sandl.containsKey(i+dice))
				{
					list.add(new ArrayList<Integer>(Arrays.asList(i,sandl.get(i+dice))));
				}
				else
					list.add(new ArrayList<Integer>(Arrays.asList(i,(i+dice))));
				dice++;
			}
		}
		
		for(List<Integer> input : list) {
			int from = g.insertOrFind(input.get(0).toString(), false);
			int to = g.insertOrFind(input.get(1).toString(), false);

			g.createEdge(from, to, 0, true);
			g.createEdge(to, from, 0, false);
						
		}
	}

	public static void main(String[] args) {
		System.out.println("GraphSnakeandLadderBuilder starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("GraphSnakeandLadderBuilder Ends");
	}
}