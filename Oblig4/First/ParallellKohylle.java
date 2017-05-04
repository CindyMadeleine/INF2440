import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class ParallellKohylle{
	IntList kohyll, m;
	int[] x, y;
	int minXPos, maxXPos;
	
	public ParallellKohylle(int[] x, int[] y, IntList m, IntList kohyll){
		this.x = x;
		this.y = y;
		this.m = m;
		kohyll = this.kohyll;
		sekvMetode();
	}
	
	public void sekvMetode(){
		minXPos = 0;
		maxXPos = 0;
		
		int nrThreads = Runtime.getRuntime().availableProcessors();
		Thread[] thrarr = new Thread[nrThreads];
		
		//Prepare threaddata
		int arrPart = x.length / nrThreads;
		int rest = x.length % nrThreads;
		int start = 0, end = arrPart;
		CyclicBarrier barrier = new CyclicBarrier(nrThreads);
		
		for(int i = 0; i < nrThreads; i++){
			if(rest > 0){
				end++;
				rest--;
			}
			
			thrarr[i] = new Thread(new KohyllThread(i, barrier, start, end));
			thrarr[i].start();
			start = end;
			end += arrPart;
		}
		
		for(int i = 0; i < thrarr.length; i++){
			try{	
				thrarr[i].join();
			} catch(InterruptedException e){
				e.printStackTrace();
				break;
			}
		}
		
	}
	
	public synchronized void updateMinMaxX(int minPos, int maxPos){
		if(x[minXPos] > x[minPos]){
			minXPos = minPos;
		}

		if(x[maxXPos] < x[maxPos]){
			maxXPos = maxPos;
		}
	}
	
	
	public class KohyllThread implements Runnable{
		int ind;
		CyclicBarrier barrier;
		int start, end;
		
		public KohyllThread(int ind, CyclicBarrier barrier, int start, int end){
			this.ind = ind;
			this.barrier = barrier;
			this.start = start;
			this.end = end;
		}
		
		public void run(){
			findMinMaxX();
		}
		
		void findMinMaxX(){
			int localMin = x[start], localMax = x[start];
			int maxPos = start, minPos = start;
			
			for(int i = start; i < end; i++){
				if(x[i] < localMin){
					localMin = x[i];
					minPos = i;
				}
				if(x[i] > localMax){
					localMax = x[i];
					maxPos = i;
				}
			}

			updateMinMaxX(minPos, maxPos);
		}
	}
}