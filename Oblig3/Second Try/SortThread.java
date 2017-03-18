import java.util.concurrent.CyclicBarrier;

public class SortThread implements Runnable{
  int ind;
  int startpos, endpos;
  CyclicBarrier barrier, synk;
  int[] a, b;
  int shift;

  int[] count;
  int acumVal, j;
  int mask;

  public SortThread(int ind, CyclicBarrier barrier, CyclicBarrier synk, int[] a, int[] b, int maskLen, int shift, int startpos, int endpos, int numSIf){
    this.ind = ind;
    this.startpos = startpos;
    this.endpos = endpos;

    this.barrier = barrier;
    this.synk = synk;
    this.a = a;
    this.b = b;

    //acumVal = 0;
    mask = (1<<maskLen) - 1;
    count = new int [numSIf]; //mask+1
  }

  public void countAmountValues(){
    for (int i = startpos; i < endpos; i++) {
      int val = count[(a[i]>>> shift) & mask]++;
    }
  }

  public void run(){
      countAmountValues();
      //update values in MultiSort
      try{
        barrier.await();
      } catch(Exception e){
        return;
      }
  }
}
