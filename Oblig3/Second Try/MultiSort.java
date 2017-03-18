import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CyclicBarrier;

public class MultiSort{
  ReentrantLock rlock;
  int[] arr;
  int max;
  int NUM_BIT;
  int[] bit;

  public MultiSort(int[] arr, int NUM_BIT){
    this.arr = arr;
    rlock = new ReentrantLock();
    this.NUM_BIT = NUM_BIT;
  }

  public void updateMax(int possibleNextMax){
    try{
      rlock.lock();
      if(max < possibleNextMax){
        max = possibleNextMax;
      }
    } finally {
        rlock.unlock();
    }
  }

  public void initBits(){
      int numBit = 0;
      while (max >= (1L<<numBit) )numBit++;

      int numDigits = Math.max(1, numBit/NUM_BIT);
      bit = new int[numDigits];
      int rest = numBit%NUM_BIT;
      // fordel bitene vi skal sortere paa jevnt
      for (int i = 0; i < bit.length; i++){
        bit[i] = numBit/numDigits;
        if ( rest-- >0) bit[i]++;
      }
  }

  int[][] allCount;
  int[] sumCount;

  public void startcount(CyclicBarrier barrier, int nrThreads, int numsIf){
    allCount = new int[nrThreads][];
    sumCount = new int[numsIf];
  }


}
