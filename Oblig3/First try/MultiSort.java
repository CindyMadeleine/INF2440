import java.util.concurrent.locks.ReentrantLock;

public class MultiSort{
	int NUM_BIT = 7;
	int[] arr;

	//get max.
	ReentrantLock updatemax;
	int max, numBit;

	//sort variables
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



	public void sort(int nrThreads){
		//allCount = new int[nrThreads][];
		//sumCount = new int[numSIf];
	}
}
