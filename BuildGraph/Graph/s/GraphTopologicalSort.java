import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * File Name: GraphTopologicalSort.java 
 * Topological Sort on a Graph
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphTopologicalSort.java
 */

class GraphTopologicalSort{
	private String t ;
	private Graph g ;
	//Output
	boolean [] cycle;
	int [] work ;
	int [] size ;
	int [] topoorder;
	//You can have any number of private variables and private funcions
	
	GraphTopologicalSort(String t, Graph g, boolean [] cycle, int [] work, int [] size, int [] topoorder) {
		this.t = t ;
		this.g = g ;
		this.cycle = cycle ;
		this.work = work ;
		this.size = size ;
		this.topoorder = topoorder ;
		
		//WRITE CODE		
		getTopologicalOrder();
		printTopologcalOrder();
		if(size[0]==g.getnumV())
			assertcheck();		
		
	}

	private void assertcheck() {
		// TODO Auto-generated method stub
		Boolean[] assertVertices = new Boolean[g.getnumV()];
		int[] fanInToporder = new int[g.getnumV()] ;
		
		IntUtil u = new IntUtil();
		for(int i = 0;i<g.getnumV(); i++) 
		{
			assertVertices[i]=false;
		}

		for(int i=0;i<topoorder.length;i++)
		{
			for(int j=0;j<g.numFanin(topoorder[i]);j++)
			{
				if(!(assertVertices[g.getNodeFanin(topoorder[i], j)]))
				{
					assertVertices[topoorder[i]] = false;
					break;
				}
				if(j==g.numFanin(topoorder[i])-1)
				{
					assertVertices[topoorder[i]]= true;
					fanInToporder[i]= topoorder[i];				
				}
					
			}
			if(g.numFanin(topoorder[i])==0) 
            {
                assertVertices[topoorder[i]] = true;
                fanInToporder[i]= topoorder[i];	
            }
		}
	

		for(int i=0;i<topoorder.length;i++)
		{
			u.myassert(fanInToporder[i]== topoorder[i]);
		}
		
		System.out.println("Topological order assert passed");
		System.out.println("-------------------------------------------------------------------------------------");
		
	}

	private void printTopologcalOrder() {
		// TODO Auto-generated method stub
		System.out.println(g.getGraphType());
		System.out.println("Num vertices = "+g.getnumV() );
        System.out.println("Num Edges = "+g.getnumE());
		System.out.println("Work Done = "+work[0]);
		if(size[0]!=g.getnumV())
		{
			System.out.println("Topological order does not exist. G is not a DAG");
			System.out.println("-------------------------------------------------------------------------------------");
		}
		else
		{
			System.out.print("Topological order = ");
			for(int i=0;i<topoorder.length;i++) 
		    { 
				System.out.print(g.getNodeRealName(topoorder[i])+" "); 
		    } 
			System.out.println();
			System.out.println("Topological order exists. G is DAG");
		}
		 
	}

	private void getTopologicalOrder() {
		// TODO Auto-generated method stub
		
		List<Integer> list =  new ArrayList<Integer>();

	        int noOffanins[] = new int[g.getnumV()]; 
	               
	        for(int i = 0; i < g.getnumV(); i++) 
	        { 
	        	work[0] += 1;
	        	for(int j=0;j<g.numFanout(i);j++)
	        	{
	        		int node = g.getNodeFanout(i, j);
	        		noOffanins[node]++;	       
	        	}	       
	        } 
	          
	        Queue<Integer> q = new LinkedList<Integer>(); 
	        for(int i = 0;i < g.getnumV(); i++) 
	        { 
	            if(noOffanins[i]==0) 
	                q.add(i); 
	        } 
	          
	        size[0] = 0; 

	        while(!q.isEmpty()) 
	        { 

	            int u=q.poll(); 
	            list.add(u); 

	            for(int j=0;j<g.numFanout(u);j++)
				{
	            	work[0] += 1;
	            	int node = g.getNodeFanout(u, j);
	            	  if(--noOffanins[node] == 0) 
		                    q.add(node); 
				}
	            
				

	            size[0] ++; 
	        }
	          
	        if(size[0] != g.getnumV()) 
	        { 
	           	cycle[0] = true;
	            return ; 
	        } 
	        cycle[0] =false;  
	        // Print topological order             
	        for(int i=0;i<list.size();i++) 
	        { 
	        	topoorder[i] = list.get(i);
	        } 
	    
		
	}


	public static void main(String[] args) {
		System.out.println("GraphTopologicalSort.java starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("GraphTopologicalSort.java Ends");
	}
}
