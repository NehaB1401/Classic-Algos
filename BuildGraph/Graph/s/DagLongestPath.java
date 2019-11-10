import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * File Name: DagLongestPath.java 
 * Longest path in a DAG
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphTopologicalSort.java DagLongestPath.java
 */

class DagLongestPath{
	//inputs
	private String t ;
	private Graph g ;
	//output
	double [] w ;
	//You can have any numbers of private variables and function
	

	DagLongestPath(String t, Graph g, double [] w) {
		this.t = t ;
		this.g = g ;
		this.w = w ;
		//WRITE CODE
		
		List<NodeData> list =  new ArrayList<NodeData>();
		
		w[0] = getDAGLongestPath1(list);
		System.out.println(w[0]);
		printDAGLongestPath(list);
		printCriticalPath(list);
		
		
	}
	
	private void assertcheck(List<NodeData> list) {
		// TODO Auto-generated method stub
		Boolean[] assertVertices = new Boolean[g.getnumV()];
		int[] fanInToporder = new int[g.getnumV()] ;
		
		IntUtil u = new IntUtil();
		for(int i = 0;i<g.getnumV(); i++) 
		{
			assertVertices[i]=false;
		}

		for(int i=0;i<list.size();i++)
		{
			for(int j=0;j<g.numFanin(list.get(i).node);j++)
			{
				if(!(assertVertices[g.getNodeFanin(list.get(i).node, j)]))
				{
					assertVertices[list.get(i).node] = false;
					break;
				}
				if(j==g.numFanin(list.get(i).node)-1)
				{
					assertVertices[list.get(i).node]= true;
					fanInToporder[i]= list.get(i).node;				
				}
					
			}
			if(g.numFanin(list.get(i).node)==0) 
            {
                assertVertices[list.get(i).node] = true;
                fanInToporder[i]= list.get(i).node;	
            }
		}
	

		for(int i=0;i<list.size();i++)
		{
			u.myassert(fanInToporder[i]== list.get(i).node);
		}
		
		System.out.println("Topological order assert passed");
		
		
	}

	private void printCriticalPath(List<NodeData> list) {
		// TODO Auto-generated method stub

		Map<String,Integer> hm = new HashMap<>();
		for(int i = 0;i<g.getnumV();i++)
		{
			hm.put(g.getNodeRealName(list.get(i).node), i);
		}
	
		Stack<String> s = new Stack<>();			
		getLongestPath(g.getnumV()-1,hm,s,list);
		printLongestPath(s);
		System.out.println();
		
	}


	private void getLongestPath(int i, Map<String, Integer> hm, Stack<String> s, List<NodeData> list) {
		// TODO Auto-generated method stub
		s.add(g.getNodeRealName(list.get(i).node));
		if(i == 0)
			return;
		s.add("->");
		
		getLongestPath(hm.get(g.getNodeRealName(list.get(i).parent)),hm, s,list);
	}


	private void printLongestPath(Stack<String> s) {
		// TODO Auto-generated method stub

		System.out.println("It takes "+ this.w[0]+" to finish. The critical path as follows");
		while(s.size()!=0)
		{
			System.out.print(s.pop());
		}
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------");
	}


	private void printDAGLongestPath(List<NodeData> list) {
		// TODO Auto-generated method stub
		System.out.println(g.getGraphType());
		System.out.println("Num vertices = "+g.getnumV() );
        System.out.println("Num Edges = "+g.getnumE());
		System.out.println("Work Done = "+list.get(list.size()-1).work);
		System.out.print("Topological order = ");
		for(int i=0;i<list.size();i++) 
	    { 
			System.out.print(g.getNodeRealName(list.get(i).node)+" "); 
	    } 
		System.out.println();
		System.out.println("Topological order exists. G is DAG");
		assertcheck(list);
		System.out.println("Levels are as follows ");
		for(int i=0;i<list.size();i++) 
	    { 
			System.out.print(list.get(i).level+" "); 
	    } 
		System.out.println();
		System.out.print("You can execute the following actions");
		int index = -1;
		for(int i=0;i<list.size();i++)
		{
			if(index == list.get(i).level)
			{
				System.out.print(g.getNodeRealName(list.get(i).node)+ " ");
			}
			else
			{
				System.out.println();
				index++;
				System.out.print("Action "+(index+1) +": "+g.getNodeRealName(list.get(i).node)+ " ");
			}
		}
		System.out.println();
		System.out.println("It takes "+ (index+1) +" steps to finish the task");
		System.out.println("Topological nodes are as follows ");
		for(int i=0;i<list.size();i++) 
	    { 
			System.out.print(g.getNodeRealName(list.get(i).node)+" "); 
	    } 
		System.out.println();
		System.out.println("Parents are as follows ");
		for(int i=0;i<list.size();i++) 
	    { 
			System.out.print(g.getNodeRealName(list.get(i).parent)+" "); 
	    } 
		System.out.println(); 
		System.out.println("Distances are as follows ");
		for(int i=0;i<list.size();i++) 
	    { 
			System.out.print(list.get(i).pathSum+" "); 
	    } 
		System.out.println(); 
		 
	}

	private class NodeData{
		private int node;
		private double pathSum;
		private int parent;
		private int level;
		private int work;
		
		NodeData(int node,double pathSum, int parent, int level, int work)
		{
			this.node = node;
			this.pathSum = pathSum;
			this.parent = parent;
			this.level = level;
			this.work = work;
		}
	}
	

	private double getDAGLongestPath1(List<NodeData> list) {
		// TODO Auto-generated method stub

        int noOffanins[] = new int[g.getnumV()];
        Double pathSum[] = new Double[g.getnumV()];
        int parent[] = new int[g.getnumV()];
        int work = 0;
               
        for(int i = 0; i < g.getnumV(); i++) 
        { 

        	work += 1;
        	for(int j=0;j<g.numFanout(i);j++)
        	{
        		int node = g.getNodeFanout(i, j);
        		noOffanins[node]++; 
        		pathSum[node] =(double) 0;
        		parent[node] = i;
        	}
       
        } 
          
        Queue<NodeData> q = new LinkedList<NodeData>(); 
        for(int i = 0;i < g.getnumV(); i++) 
        { 
            if(noOffanins[i]==0) 
            {
            	pathSum[i] =(double) 0;
            	parent[i] = i;
                q.add(new NodeData(i,pathSum[i],i,0,work)); // node, pathSum, parent, level, work
        		
            }
        } 

        while(!q.isEmpty()) 
        { 

            NodeData u=q.poll(); 
            list.add(new NodeData(u.node,u.pathSum,u.parent,u.level,u.work));

            for(int j=0;j<g.numFanout(u.node);j++)
			{
            	work += 1;
            	int node = g.getNodeFanout(u.node, j);
            	--noOffanins[node];
            	double w = 1;
    			if(g.getGraphType().contains("WEIGHTED"))
    			{
    				w = g.getNodeFanoutEdgeWeight(u.node, j);
    			}
    			if(w + pathSum[u.node]>pathSum[node])
    			{
    				parent[node] = u.node;
    			}
            	pathSum[node] = Math.max(pathSum[node],w + pathSum[u.node]);
            	if(noOffanins[node] == 0) 
            	{
            		q.add(new NodeData(node,pathSum[node],parent[node],u.level+1,work));      // node, pathSum, parent, level, work        		 
            	}
			}
        }
           
        double res = 0.00;
        for(int i=0;i<list.size();i++) 
        { 
        	
        	res= Math.max(res, list.get(i).pathSum);
        } 
        return res;
	}


	public static void main(String[] args) {
		System.out.println("DagLongestPath.java starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("DagLongestPath.java Ends");
	}
}