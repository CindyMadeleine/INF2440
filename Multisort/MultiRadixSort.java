import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiRadixSort{

	/**
	 * When n is equal to and above 200 000 000 I got memory out of bounds problems.
	 *
	 * @param args
	 */
  public static void main(String[] args){
    int n = 10000;
    int start = 10;
    boolean status = true;
    for(int i = start; i <= n && status; i++){
      System.out.print("N: " + i  + "\t");
      status = run(i);
    }

  }

  public static boolean run(int n){
    int[] arr = new int[n];
    int counter = 0;
    for(int i = 0; i < n; i++, counter++){
      arr[i] = counter;
      /*if(counter % 10 == 0){
        counter = 0;
      }*/
    }

  /*System.out.print("Original array:\t\t(");
  for(int i = 0; i < arr.length; i++){
    System.out.print(arr[i] + " ");
  }
  System.out.println(")");*/

  int num_bit = 7;
	MultiSort msort = new MultiSort(arr, num_bit);
	int nrThreads = Runtime.getRuntime().availableProcessors();
	//partision array
	if(!findMax(msort, nrThreads)){
		System.out.println("Can not find max num");
		return false;
	}

    msort.initBits();

    if(!sort(msort, nrThreads)){
      System.out.println("Can not sort num");
			return false;
    }


    int piv = testSort(arr);
    if(piv >= 0){
      System.out.print("Final array: \t\t(");
      int i = piv - 100;
      if(i < 0){
        i = 0;
      }
      for(; i < piv+100 && i < arr.length; i++){
        System.out.print(arr[i] + " ");
      }
      System.out.println(")");
      return false;
    }
    return true;
  }

  static int testSort(int [] a){
    for (int i = 0; i< a.length-1;i++) {
      if (a[i] > a[i+1]){
        System.out.println("SorteringsFEIL på plass: "+i +" a["+i+"]:"+a[i]+" > a["+(i+1)+"]:"+a[i+1]);
        return i;
      }
    }
    System.out.println("Sorting successfull!");
    return -1;
  }

	public static boolean findMax(MultiSort msort, int nrThreads){
		if(msort.arr.length < nrThreads){
      System.out.println("Too short array");
			return false;
		}


    int part = msort.arr.length / nrThreads;
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
    CyclicBarrier synk = new CyclicBarrier(nrThreads);
    int[] t=msort.arr, a = msort.arr;
    int[] b = new int [msort.arr.length];
    int sum = 0;
     int part = a.length / nrThreads;


     for (int i =0; i < msort.bit.length; i++) {
       int bit = msort.bit[i];
       int numSIf = (int) (1<<bit);
       msort.startcount(nrThreads, numSIf);

       int rest = a.length % nrThreads;
       int startpos = 0;
       int endpos = part;

       int sumpart = msort.sumCount.length / nrThreads;
       int sumrest = msort.sumCount.length % nrThreads;
       int startsumpos = 0;
       int endsumpos = sumpart;

      for(int j = 0; j < nrThreads; j++) {
        if(rest > 0){
          endpos++;
          rest--;
        }

        if(sumrest > 0){
          endsumpos++;
          sumrest--;
        }

        //System.out.println(endsumpos + "<=?" + msort.sumCount.length);
        SortThread st = new SortThread(msort, j, barrier, synk, a, b, bit, sum, startpos, endpos, numSIf, startsumpos, endsumpos);
        Thread thr = new Thread(st);
        thr.start();
        startpos = endpos;
        endpos += part;
        startsumpos = endsumpos;
        endsumpos += sumpart;
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


     /**
      *  Sort the numbers, but have a problem when a contains 0.
      */
     if((msort.bit.length&1) != 0 ) {
    	 // et odde antall sifre, kopier innhold tilbake til original a[] fra b[].
    	 System.arraycopy (a,0,b,0,a.length);
     }

    return true;
  }
}
