import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * File Name: ThreeSum.java 
 * ThreeSum  class
 * 
 * To Compile: IntUtil.java RandomInt.java Tuple.java ThreeSumBase.java ThreeSum.java 
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

class ThreeSum extends ThreeSumBase{
	ThreeSum() {
		//NOTHING CAN BE CHANGED HERE
		testBench();
	}

	@Override
	protected String inputFileBase() {
		//Where is the input file?
		//Change this to your location
		return "C:\\Users\\nehab\\Documents\\Code\\3sum(1)\\3sum\\";
	}

	@Override
	protected List<List<Integer>> threeSum(int[] nums, int n, int method) {
		//NOTHING CAN BE CHANGED HERE
		if (method == 1) {
			return N3(nums,n) ;
		}
		if (method == 2) {
			return N2TimeNSpace(nums,n) ;
		}
		if (method == 3) {
			return N2Time(nums,n) ;
		}
		return null ;
	}


	/*
	 * Time complexity O(N^3)
	 * Space complexity O(1)
	 */
	private List<List<Integer>> N3(int[] nums, int n) {
		Arrays.sort(nums);

        //Declare a set to hold unique triplets
		HashSet<List<Integer>> set = new HashSet<>();
		
		//Run a loop from 0 to n-2
		for(int i=0; i<(nums.length-2);i++)
		{   
			//Run a loop from 0 to n-1
			for(int j=i+1;j<(nums.length-1);j++)
			{
				//Run a loop from 0 to n
				for(int k=j+1;k<nums.length;k++)
				{	
					
					//If triplets is found then put them in a set
					if(nums[i]+nums[j]+nums[k] == n)
					{
						set.add(Arrays.asList(nums[i],nums[j],nums[k]));
					}
				}
			}
		}
		
		//Convert set back to list
        return new ArrayList<>(set);
		
	}


	/*
	 * Time complexity O(N^2)
	 * Space complexity O(N)
	 */
	private List<List<Integer>> N2TimeNSpace(int[] nums, int n) {
		Arrays.sort(nums);
		
		 //Declare a map to hold unique integers
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		 //Declare a set to hold unique triplets
        HashSet<List<Integer>> set = new HashSet<>();
        
        //Run a loop from 0 to n
		for(int i=0;i<nums.length;i++)
			map.put(nums[i], i);
		int diff;
		
		//Run a loop from 0 to n-2
		for(int i=0,j=i+1; i<(nums.length-2) ;j++)
		{
			//Run a loop from 0 to n-1
			if(j == (nums.length-1) ){
				//Intialize two indexes
                i++;
                j=i+1;
            }
				diff = 0 - (nums[i]+nums[j]);
				
				//If triplets is found then put them in a set
				if(map.containsKey(diff) && (map.get(diff) > j))
				{
					set.add(Arrays.asList(nums[i],nums[j],diff));
				}
			
		}
		
		//Convert set back to list
		return new ArrayList<>(set);
	}

	/*
	 * Time complexity O(N^2)
	 * Space complexity O(1)
	 */
	private List<List<Integer>> N2Time(int[] nums, int n) {
		Arrays.sort(nums);
		int l,r;
		
		//Declare a list to hold multiple triplets
		List<List<Integer>> res = new ArrayList<>();
	
		//Run a loop from 0 to n
		for(int i=0;i<nums.length;i++)
		{
			//Intialize two indexes
	        l = i + 1; 
	        r = nums.length - 1; 
	        
	        //Ignore the consecutive values
	        if (i > 0 && nums[i] == nums[i - 1]) 
	        {
	        	continue;
	        }
			while(l<r)
			{	
				//Ignore duplicates
				if (r < nums.length - 1 && nums[r] == nums[r + 1]) 
				{
					r--;
		            continue;
		        }
				
				//If triplets is found then put them in a list
				if(nums[i]+nums[l]+nums[r] == n)
				{
					res.add(Arrays.asList(nums[i],nums[l],nums[r]));	
					l++; 
	                r--;
				}
				//If sum is less than zero, increment the left index else decrement right index
				else if(nums[i]+nums[l]+nums[r]<0)
				{
					l++;
				}
				else
				{
					r--;
				}
			}
			
		}
		
		return res;
	}

	public static void main(String[] args) {
		System.out.println("ThreeSum.java STARTS");
		String version = System.getProperty("java.version");
		System.out.println("Java version used for this program is " + version);
		ThreeSum p = new ThreeSum() ;
		System.out.println("ThreeSum.java ENDS");
	}
}