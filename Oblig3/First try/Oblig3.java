import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Oblig3 {
	public static void main(String[] args){
		int n = 8670;
		int[] arr = new int[n];
		int step = 1;
		for(int i = arr.length-1, counter = 0; i >= 0; counter+=step, i--){
			arr[i] = counter;
		}


		int num_bit = 7;
		MultiSort msort = new MultiSort(arr, num_bit);

		int nrThreads = Runtime.getRuntime().availableProcessors();
		//partision array
		int part = arr.length / nrThreads;
		if(part < nrThreads){
			return;
		}

		if(!findMax(msort, part, nrThreads)){
			return;
		}

		msort.initBits();

		if(!sort(msort)){
			return;
		}

		//check if sorting is legal
	}

	public static boolean findMax(MultiSort msort, int part, int nrThreads){
		ExecutorService executor = Executors.newFixedThreadPool(nrThreads);
		int rest = msort.arr.length % nrThreads;
		int startval = 0;
		int endval = part;

		//run numsIf
		for(int i = 0; i < nrThreads; i++){
			if(rest > 0){
				endval++;
				rest--;
			}

			Runnable worker = new FindMax(msort, startval, endval);
			executor.execute(worker);

			//update startval and endval
			startval = endval;
			endval += part;
		}

		//The executor accept no new threads and finish all existing threads in the queue
		  executor.shutdown();

		// Wait until all threads are finish
		  try {
			executor.awaitTermination(6, TimeUnit.SECONDS);
		  } catch (InterruptedException e) {
			e.printStackTrace();
		  }

		//returns true if all the threads have terminated
		return executor.isTerminated();
	}

	public static boolean sort(MultiSort msort, int[] arr){
		int nrThreads = Runtime.getRuntime().availableProcessors();
		CyclicBarrier synk = new CyclicBarrer(nrThreads);
		//partision array
		int part = msort.arr.length / nrThreads;
		if(part < nrThreads){
			return false;
		}

		msort.sort(nrThreads);

		ExecutorService executor = Executors.newFixedThreadPool(nrThreads);
		int rest = msort.arr.length % nrThreads;
		int startval = 0;
		int endval = part;

		int numDigits = Math.max(1, numBit/NUM_BIT);
		int[] bit = new int[numDigits];
		int rest = numBit%NUM_BIT;
		int currSum = 0;

		for (int i = 0; i < mask.length; i++){
			int bit = numBit/numDigits;
			if ( rest-- >0) bit[i]++;
		}

		int[] a = msort.arr;
		int[] t=a, b = new int [n];
		for(int i = 0; i < bit.length; i++){
			for(int j = 0; j < nrThreads; j++){
				if(rest > 0){
					endval++;
					rest--;
				}

				Runnable worker = new MultiSortThread(startval, endval, synk, a, b, bit[i], sum);
				executor.execute(worker);

				//update startval and endval
				startval = endval;
				endval += part;
			}

			sum+=bit[i];

			//might need to delete the loop-part below?
			t = a;
			a = b;
			b = t;
		}

		//The executor accept no new threads and finish all existing threads in the queue
		  executor.shutdown();

		// Wait until all threads are finish
		  try {
			executor.awaitTermination(6, TimeUnit.SECONDS);
		  } catch (InterruptedException e) {
			e.printStackTrace();
		  }

		//returns true if all the threads have terminated
		return executor.isTerminated();
	}
}
