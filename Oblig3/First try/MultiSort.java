import java.util.concurrent.locks.ReentrantLock;
//import java.util.concurrent.CyclicBarrier;

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
  
  /**
   * Updates max into the next maximum value
   * @param possibleNextMax
   */
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

  /**
   * initialiserer bit og numBit
   **/
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

  /**
   * Initialize count that is used to update counter
   * @param nrThreads
   * @param numsIf
   */
  public void startcount(int nrThreads, int numsIf){
    allCount = new int[nrThreads][];
    sumCount = new int[numsIf];
  }
  
  /**
   * Oppdaterer alle verdiene til count i allCount
   * @param ind
   * @param count
   */
  public void updateCount(int ind, int[] count){
	 allCount[ind] = count;
  }

  /**
   * Vil ikke oppdatere hele allCount! Virker som om den avslutter sorteringen for tidlig, altså
   * fungerer kun på allCount opp til arr.length. Tar ikke negative tall. Må forøvrig også sjekke
   * om endpos er under lengden av sumCount, dersom arrayet inneholder kun 0 er hvilket er dårlig
   * skikk.
   * @param startpos
   * @param endpos
   */
  public void updateSumCount(int startpos, int endpos){
	  if(endpos > sumCount.length){
		endpos = sumCount.length;  
	  }
	  for(int pos = startpos; pos < endpos; pos++){
		  int acumVal = 0;
		  for(int j = 0; j < allCount.length; j++){
			  acumVal += allCount[j][pos];
		  }
		  sumCount[pos] += acumVal;
	  }
  }
}
