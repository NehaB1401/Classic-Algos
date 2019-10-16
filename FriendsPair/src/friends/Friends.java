package friends;

/**
 * File Name: Friends.java 
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 * 
 * To compile: RandomInt.java IntUtil.java Friends.java
 */
public class Friends{
  private int max ;
  static IntUtil u = new IntUtil() ;
  //You can have any number of private variables
  //You can have any number of private functions
  
  /*
   * Constructor
   */
  Friends(int n) {
  	//YOU CANNOT CHANGE ANYTHING IN THIS PROCEDURE
    max = n ;
    System.out.println("The following are friends") ;
    long startTime = System.nanoTime();
    alg() ;
    long endTime = System.nanoTime();
    double d2 = u.timeInSec(endTime,startTime) ;
    System.out.println("Run time  n = " + max + " is " + d2 + " secs" ) ;
  }
  
  private void alg() {
  	//WRITE YOUR CODE
  	//YOU CAN HAVE ANY NUMBER OF PRIVATE data members and function
	   int[] sumFactor = new int[max];
	   int counter = 0;
       for(int i=1;i<max;i++){
           for(int j=i*2;j<max;j=j+i){
               sumFactor[j]+=i;
           }
           if((sumFactor[i])<i && sumFactor[sumFactor[i]]==i ){
               System.out.println(counter++ + " : "+(sumFactor[i]) +" and "+i);
           }
       }
  	
  }
  

  private static void test() {
  	//test first for n = 1000
  	//Then test for n =  100000000 
     // int n = 100000000;
    int n = 100000000;
    Friends a = new Friends(n) ;
  }
  
  public static void main(String[] args) {
  	//NOTHING CAN BE CHANGED BELOW
    System.out.println("Friends.java starts") ;
    test() ;
    System.out.println("If you can do in less than 20 secs, you will get FULL points") ;
    System.out.println("Attach Friends.java, output of your program as a pdf file") ;
    System.out.println("Attach a word file, explaining why your method is fast") ;
    System.out.println("Friends.java ends") ;
  }
}

