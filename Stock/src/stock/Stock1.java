package stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * File Name: Stock1.java 
 * Stock1 concrete class
 * 
 * 
 * To Compile: IntUtil.java RandomInt.java Stock1.java Stock1Base.java
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

class Stock1 extends Stock1Base{
	//You can have any number of private data members here
	//You can have any number of private functions here
	Stock1() {
		//NOTHING CAN BE CHANGED HERE
		testBench();
	}

	@Override
	void NSquareTimeConstantSpace() {
		//NOTHING CAN BE CHANGED HERE
		nsquareTimeConstantSpace() ;
	}

	@Override
	void NlognTimeLognSpace() {
		//NOTHING CAN BE CHANGED HERE
		nlognTimelognSpace() ;
	}

	@Override
	void NTimeLognSpace() {
		//NOTHING CAN BE CHANGED HERE
		nTimelognSpace() ;
	}

	/*
	 * Time: O(n^2)
	 * Space: THETA(1)
	 * All your routine should match this answer
	 * Nothing can be changed in this routine
	 */
	private void nsquareTimeConstantSpace() {
		int gp = 0 ;
		int l = size() ;
		for (int buy = 0; buy < l-1; buy++) {
			for (int sell = buy + 1; sell < l; sell++) {
				numDivide++;
				numConquer++;
				int p = profit(sell,buy);
				if (p >= gp) { //So that I can make profit by keeping stock for less time
					gp = p ;
					buyDay = buy ;
					sellDay = sell ;
				}
			}
		}
	}

	/*
	 * Time: O(nlogn)
	 * Space: O(logn)
	 */
	private void nlognTimelognSpace() {
		//WRITE YOUR CODE
		buyDay = 0;
		sellDay = 0;
		int left = 0;
		int right = size() - 1;
		int mid =(left+ right)/2;
		if(left>=right)
		{
			buyDay = 0;
			sellDay =0;
		}
		else
		{
			buysell(left,mid, right);
		}

	}

	private int [] buysell(int left, int mid, int right) {
		int [] lp = new int[3];
		int [] rp = new int[3];
		List<Integer> tempBuy = new ArrayList<Integer>();
		List<Integer> tempSell = new ArrayList<Integer>();
		if(left == mid)
		{

			if(price(left)<=price(right))
			{
				lp[0] = left;
				lp[1] = right;
			}
			lp[2] = max(0,0,price(right)-price(left))[2]; 
			return lp;

		}

		lp = buysell(left,(left+mid)/2, mid);

		tempBuy.add(lp[0]);
		tempSell.add(lp[1]);

		numDivide++;
		if(mid+1!=right && right>mid)
		{
			rp = buysell(mid+1, (mid+1+right)/2, right);


		}
		else
			rp[2] = 0;

		numConquer++;
		tempBuy.add(rp[0]);
		tempSell.add(rp[1]);

		int min = price(left);
		tempBuy.add(left);
		for(int i=left+1;i<mid+1;i++){
			if(price(i) < min){
				min = price(i);
				tempBuy.remove(tempBuy.size()-1);
				tempBuy.add(i);
			}
		}
		int max =0;

		tempSell.add(right);
		if(mid+1!=right && right>mid)
		{
			for(int i=mid+1;i < right+1;i++){
				if(price(i) >= max){
					max = price(i);
					tempSell.remove(tempSell.size()-1);
					tempSell.add(i);
				}
			}
		}
		else
		{
			max = price(right);
		}

		if(max-min > lp[2] && max-min > rp[2]  && (price(sellDay) - price(buyDay)<max-min) )
		{
			buyDay = tempBuy.get(tempBuy.size()-1);
			sellDay = tempSell.get(tempSell.size()-1);
		}

		else if(rp[2]>lp[2]  && (price(sellDay) - price(buyDay)<rp[2])){
			tempBuy.remove(tempBuy.size()-1);
			tempSell.remove(tempSell.size()-1);

			if(!tempBuy.isEmpty() && !tempSell.isEmpty())
			{
				buyDay = tempBuy.get(tempBuy.size()-1);
				sellDay = tempSell.get(tempSell.size()-1);
			}
		}
		else if(price(sellDay) - price(buyDay)<lp[2]) {
			tempBuy.remove(tempBuy.size()-1);
			tempSell.remove(tempSell.size()-1);
			tempBuy.remove(tempBuy.size()-1);
			tempSell.remove(tempSell.size()-1);
			if(!tempBuy.isEmpty() && !tempSell.isEmpty())
			{
				buyDay = tempBuy.get(tempBuy.size()-1);
				sellDay = tempSell.get(tempSell.size()-1);
			}
		}
		tempBuy.clear();
		tempSell.clear();
		if(min<max)
		{

			return max(lp[2],rp[2],(max-min));
		}
		else
			return max(lp[2],rp[2],0);			

	}
	private static int [] max(int x, int y, int z) {
		// TODO Auto-generated method stub
		int [] set = new int[3];
		set[2] = Math.max(Math.max(x,y),z);
		return set;
	}


	private void nTimelognSpace() {
		//NOT required this time
		int left = 0;
		int right = size()-1;
		int mid = (left + right)/2;
		if(left>=right)
		{
			buyDay = 0;
			sellDay =0;
		}
		else 
		{

			int [] values = nBuySell(left,mid,right);

			if(values[1]<values[2])
			{
				buyDay = values[1]; 
				sellDay = values[2];
			}
		}
	}

	public int[] nBuySell(int left, int mid, int right){
		int lp [] = new int[3];
		int rp [] = new int[3];

		if(left == mid)
		{
			int minIndex = left; 
			int leftmin = Integer.MAX_VALUE;
			int min = Integer.MAX_VALUE;
			int maxIndex = left;
			int rightmax = price(mid+1);
			int max = Integer.MIN_VALUE;

			for(int i=left;i<mid+1;i++)
			{
				if(price(i) < leftmin){
					leftmin = price(i);
					min = price(i);
					minIndex = i;
				}
				if(price(i) > max){
					max = price(i);
					maxIndex = i;
				}
			}

			for(int j=mid+1;j < right+1;j++)
			{
				if(price(j) < min){
					min = price(j);
					minIndex = j;
				}
				if(price(j) > rightmax){
					rightmax = price(j);
				}
				if(price(j) > max && minIndex<j){
					max = price(j);
					maxIndex = j;
				}
			}

			int mp = mp(0,0,rightmax-leftmin);
			lp[0] = mp;			 

			lp[1] = minIndex;
			lp[2] = maxIndex;
			return lp;

		}
		lp = nBuySell(left, (left+mid)/2, mid);
		numDivide++;
		if(mid+1!=right && right>mid)
			rp = nBuySell(mid+1, (mid+1+right)/2, right);
		else
			rp = new int[3];
		numConquer++;
		int minIndex = left; 
		int leftmin = Integer.MAX_VALUE;
		int min = Integer.MAX_VALUE;
		int maxIndex = left;
		int rightmax = price(mid+1);
		int max = Integer.MIN_VALUE;

		for(int i=left;i<mid+1;i++)
		{
			if(price(i) < leftmin){
				leftmin = price(i);
				min = price(i);
				minIndex = i;
			}
			if(price(i)> max){
				max = price(i);
				maxIndex = i;
			}
		}

		for(int j=mid+1;j < right+1;j++)
		{
			if(price(j) < min){
				min = price(j);
				minIndex = j;
			}
			if(price(j) > rightmax){
				rightmax = price(j);
			}
			if(price(j) > max && minIndex<j){
				max = price(j);
				maxIndex = j;
			}
		}

		int mp = mp(lp[0],rp[0],rightmax-leftmin);


		lp[0] = mp;
		lp[1] = minIndex;
		lp[2] = maxIndex;
		return lp;

	}

	private static int mp(int x, int y, int z) {
		// TODO Auto-generated method stub
		return Math.max(Math.max(x,y),z);
	}


	public static void main(String[] args) {
		//NOTHING CAN BE CHANGED HERE
		System.out.println("Stock1 problem STARTS");
		Stock1 m = new Stock1() ;
		System.out.println("All Stock1 tests passed. Now you can pass interviews");
		System.out.println("Stock1 problem ENDS");
	}
}	