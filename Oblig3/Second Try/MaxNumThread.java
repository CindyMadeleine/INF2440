public class MaxNumThread implements Runnable{
  MultiSort msort;
  int startpos, endpos;

  public MaxNumThread(int startpos, int endpos, MultiSort msort){
    this.msort = msort;
    this.startpos = startpos;
    this.endpos = endpos;
  }

  public int findMax(int[] arr){
    int max = 0;
    for (int i = startpos; i < endpos; i++){
      if (arr[i] > max) max = arr[i];
    }
    return max;
  }

  public void run(){
    int max = findMax(msort.arr);
    msort.updateMax(max);
  }
}
