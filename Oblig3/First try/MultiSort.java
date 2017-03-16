package Oblig3;
import java.util.concurrent.locks.ReentrantLock;

public class MultiSort{
	int NUM_BIT = 7;
	int[] arr;
	
	//get max.
	ReentrantLock updatemax;
	int max, numBit;
	int[] bit;
	
	//sort variables

	int numSIf;
	int[][] allCount;
	int[]sumCount;
	
	
	public MultiSort(int[] arr, int NUM_BIT){
		this.NUM_BIT = NUM_BIT;
		this.arr = arr;
		
		//get maxValue
		updatemax = new ReentrantLock(); 
		max = 0;
		
		//get amount of digits
	}
	
	public void updateMaxValue(int value, int numBit){
		updatemax.lock();
		try{
			if(max < value){
				max = value;
				this.numBit = numBit;
			}
		} finally{
			updatemax.unlock();
		}
	}
	
	public void initBits(){
		int numDigits = Math.max(1, numBit/NUM_BIT);
		bit = new int[numDigits];
		int rest = numBit%NUM_BIT;
		
		for (int i = 0; i < bit.length; i++){
			bit[i] = numBit/numDigits;
			if ( rest-- >0) bit[i]++;
		}
	}
	

	
	public void sort(int nrThreads){
		allCount = new int[nrThreads][];
		sumCount = new int[numSIf];
	}
}
