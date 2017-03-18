import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiRadixSort{
  public static void main(String[] args){
    int n = 10;
    //make array
		int[] arr = new int[n];
    int counter = 0;
    for(int i = 0; i < n; i++, counter++){
      arr[i] = counter;

      if(counter % 4 == 0){
        counter = 0;
      }
    }
		/*int step = 1;
		for(int i = arr.length-1, counter = 0; i >= 0; counter+=step, i--){
			arr[i] = counter;
		}*/

		int nrThreads = Runtime.getRuntime().availableProcessors();
		//partision array

    int num_bit = 7;
		MultiSort msort = new MultiSort(arr, num_bit);
		if(!findMax(msort, nrThreads)){
      System.out.println("Can not find max num");
			return;
		}

    msort.initBits();

    if(!sort(msort, nrThreads)){
      System.out.println("Can not sort num");
			return;
    }
  }

	public static boolean findMax(MultiSort msort, int nrThreads){
    int part = msort.arr.length / nrThreads;
		if(msort.arr.length < nrThreads){
			return false;
		}

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

			Runnable worker = new MaxNumThread(startval, endval, msort);
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

  public static boolean sort(MultiSort msort, int nrThreads){
    CyclicBarrier barrier = new CyclicBarrier(nrThreads+1);
    CyclicBarrier synk = new CyclicBarrier(nrThreads+1);
    int[] t=msort.arr, a = msort.arr;
    int[] b = new int [msort.arr.length];
    int sum = 0;

    int part = a.length / nrThreads;
    int rest = a.length % nrThreads;
     for (int i =0; i < msort.bit.length; i++) {
       int bit = msort.bit[i];
       int numSIf = (int) Math.pow(2, bit);
       msort.startcount(synk, nrThreads, numSIf);
       int startpos = 0;
       int endpos = part;

      for(int j = 0; j < nrThreads; j++) {
        if(rest < 0){
          endpos++;
          rest--;
        }

        SortThread st = new SortThread(j, barrier, synk, a, b, bit, sum, startpos, endpos, numSIf);
        Thread thr = new Thread(st);
        thr.start();
        startpos = endpos;
        endpos += part;
      }

      try{
        barrier.await();
      } catch(Exception e){
        return false;
      }

       sum += bit;
       // swap arrays (pointers only)
       t = a;
       a = b;
       b = t;
     }

    return true;
  }
}
