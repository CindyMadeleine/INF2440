import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

public class MultiSortThread implements Runnable, multisortthreadtask {
	int startpos, endpos;
	MultiSort msort;
	CyclicBarrier synk;
	int[] count;

	public MultiSortThread(int startpos, int endpos, MultiSort msort, CyclicBarrier barrier, int[] a, int[] b, int maskLen, int shift){
		this.startpos = startpos;
		this.endpos = endpos;
		this.msort = msort;
		synk = barrier;
	}

	@Override
	public void countFrequencyofEachRadixValue(int[] arr, int shift, int mask) {
		for (int i = startpos; i < endpos; i++) {
			count[(arr[i]>>> shift) & mask]++;
		}
	}

	@Override
	public void addUpInCount(int acumVal, int mask) {
		int j = 0;
		for (int i = startpos; i <= mask; i++) {
			j = count[i];
			count[i] = acumVal;
			acumVal += j;
		}
	}

	@Override
	public void moveNumbersInSortedOrder(int[] arrA, int[]  arrB, int shift, int mask) {
		for (int i = startpos; i < endpos; i++) {
			arrB[count[(arrA[i]>>>shift) & mask]++] = arrA[i];
		}
	}

	public void radixSort ( int [] a, int [] b, int mask, int shift){

	}

	@Override
	public void run() {
	}


}
