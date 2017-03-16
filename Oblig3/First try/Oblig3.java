package Oblig3;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Oblig3 {
	public static void main(String[] args){
		int[] arr = new int[20];
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
		
		if(!sort(msort, arr)){
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
		CyclicBarrier barrier = new CyclicBarrier(nrThreads);
		
		//partision array 
		int part = arr.length / nrThreads;
		if(part < nrThreads){
			return false;
		}
		
		msort.sort(nrThreads);
		
		ExecutorService executor = Executors.newFixedThreadPool(nrThreads);
		int rest = arr.length % nrThreads;
		int startval = 0;
		int endval = part;
		
		//run numsIf
		for(int i = 0; i < nrThreads; i++){
			if(rest > 0){
				endval++;
				rest--;
			}
			
			Runnable worker = new MultiSortThread(startval, endval, msort, barrier);
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
}
