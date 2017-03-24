import java.util.concurrent.CyclicBarrier;

public class SortThread implements Runnable{
  int ind;
  int startpos, endpos;
  int startsumpos, endsumpos;
  CyclicBarrier barrier, synk;
  int[] a, b;
  int shift;

  int[] count;
  int acumVal, j;
  int mask, maskLen;
  MultiSort msort;


  public SortThread(MultiSort msort, int ind, CyclicBarrier barrier, CyclicBarrier synk, int[] a, int[] b, int maskLen, int shift,
   int startpos, int endpos, int numSIf, int startsumpos, int endsumpos){
     this.startsumpos = startsumpos;
     this.endsumpos = endsumpos;

    this.ind = ind;
    this.startpos = startpos;
    this.endpos = endpos;

    this.msort = msort;
    this.barrier = barrier;
    this.synk = synk;
    this.a = a;
    this.b = b;

    mask = (1<<maskLen) - 1;
    count = new int [numSIf];
    this.maskLen = maskLen;
  }

  public void countAmountValues(){
    for (int i = startpos; i < endpos; i++) {
    	count[(a[i]>>> shift) & mask]++;
    }
  }

  public void run(){
      countAmountValues();
      //update values in MultiSort
      msort.updateCount(ind, count);

      try{
    	  synk.await();
      }catch(Exception e){
    	  return;
      }

      msort.updateSumCount(startsumpos, endsumpos);

      try{
    	  synk.await();
      }catch(Exception e){
    	  return;
      }

      if(ind == 0){
	      int j;
	      for (int i = 0; i <= mask; i++) {
	    	  j = msort.sumCount[i];
	    	  msort.sumCount[i] = acumVal;
	    	  acumVal += j;
	      }
      }

      try{
    	  synk.await();
      }catch(Exception e){
    	  return;
      }

      for (int i = startpos; i < endpos; i++) {
        int val = msort.sumCount[(a[i]>>>shift) & mask]++;
    	  b[val] = a[i];
      }

      try{
        barrier.await();
      } catch(Exception e){
        return;
      }
  }
}
