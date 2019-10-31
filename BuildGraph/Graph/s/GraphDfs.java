import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * File Name: GraphDfs.java 
 * Depth First Search on a graph
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphDfs.java
 */

class GraphDfs{
	//You can have any number of private variables and private functions
	private String t ;
	private Graph g ;
	String start;
	//Output
	boolean [] cycle;
	int [] work ;
	int [] size ;
	int [] dfsorder;

	
	GraphDfs(String t, Graph g, String start, boolean [] cycle, int [] work, int [] size, int [] dfsorder) {
		this.t = t ;
		this.g = g ;
		this.start = start;
		this.cycle = cycle ;
		this.work = work ;
		this.size = size ;
		this.dfsorder = dfsorder ;

		//WRITE YOUR CODE
		isCyclic(g,cycle);

		
		
	}
	private void isCyclic(Graph g, boolean[] cycle)  
    { 
          
        // Mark all the vertices as not visited and 
        // not part of recursion stack 
        boolean[] visited	 = new boolean[g.getnumV()]; 
        boolean[] revisited  = new boolean[g.getnumV()]; 
        List<String> topologicalOrder =  new ArrayList<>();
        
        // for assert check
	    if(t.charAt(0) == '2')
	    {
	    	topologicalOrder = Arrays.asList("1", "3","2", "4", "5" );
	    }
	    else if(t.charAt(0) == '7')
	    {
	    	topologicalOrder = Arrays.asList( "0", "3", "1", "2" ,"4" ,"5" ,"6"  );
	    }
	    else if(t.charAt(0) == '1')
	    {
	    	topologicalOrder = Arrays.asList("1", "3", "5", "4", "2"  );
	    }
	    else if(t.charAt(0) == '4')
	    {
	    	topologicalOrder = Arrays.asList(  "A" ,"C", "B" );
	    }
	   
        Stack<Integer> stck = new Stack<>();
        System.out.println("Num vertices = "+g.getnumV() );
        work[0] = g.getnumV();
        for(int i=0;i<g.getnumV();i++)
        {
        	if(start.compareTo(g.getNodeRealName(i))==0)
        	{
        		dfs(i,visited,revisited,cycle,stck);
        	}
        }
        System.out.println("Num Edges = "+g.getnumE());
        System.out.println("Work Done = "+work[0]);
        size[0] = stck.size();
        System.out.print("Has Cycle = ");
        if(cycle[0])
        	System.out.println("YES");
        else
        	System.out.println("NO");
        System.out.print("DFS topological order = ");
        int stackSize = stck.size();
       	for(int i=0;i<stackSize;i++)
        {
       		Integer poppedItem = (Integer)stck.pop();
         	 System.out.print(g.getNodeRealName(poppedItem)+ " ");
         	 dfsorder[i] = poppedItem;
        }
       
        System.out.println();
        boolean assertFailed = false;
        if(!cycle[0])
        {
        	for(int i =0;i<dfsorder.length;i++)
        	{
        		if(g.getNodeRealName(dfsorder[i]).compareTo(topologicalOrder.get(i)) == 0)
        		{        			
        			continue;
        		}
        		else {
        			assertFailed = true;
        		}
        	}
        	if(!assertFailed)
        		System.out.println("dfs assert passed");
        }
        else {
        	System.out.println("This order has no meaning");
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		
    } 

	private void dfs(int i, boolean[] visited, boolean[] revisited, boolean[] cycle, Stack<Integer> stck) {
		// TODO Auto-generated method stub
		
		if(visited[i] == true)
			cycle[0] = true;
		else if(revisited[i] == true && !(g.isUndirectedGraph()))
		{
			return;
		}
		else if(revisited[i] == true && g.isUndirectedGraph())
		{
			cycle[0] =true;
		}
		else
		{
			visited[i] = true;
			
			for(int j =0;j<g.numFanout(i);j++)
	    	{
				work[0] = work[0]+1; 
	    		if((i < g.getNodeFanout(i, j) && g.isUndirectedGraph()) ||  !g.isUndirectedGraph())
	    		{	    			
	    			dfs(g.getNodeFanout(i, j),visited,revisited,cycle,stck);	    			
	    		}
	    	}
			stck.push(i);
			revisited[i] = true;
			visited[i] = false;
		}
	
		
	}
	
	public static void main(String[] args) {
		System.out.println("GraphDfs.java starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("GraphDfs.java Ends");
	}
}