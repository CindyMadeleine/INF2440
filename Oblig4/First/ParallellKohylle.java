import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.ReentrantLock;

public class ParallellKohylle{
	IntList kohyll, m;
	int[] x, y;
	int minXPos, maxXPos;
	ReentrantLock lock;

	public ParallellKohylle(int[] x, int[] y, IntList m, IntList kohyll){
		this.x = x;
		this.y = y;
		this.m = m;
		this.kohyll = kohyll;
		lock = new ReentrantLock();
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


		/*System.out.println(x[minXPos] + " " + x[maxXPos]);
		for(int i = 0; i < x.length; i++){
			System.out.println(x[i]);
		}*/
	}

	public void updateMinMaxX(int minPos, int maxPos){
		lock.lock();
		try {
			if(x[minXPos] > x[minPos]){
				minXPos = minPos;
			}

			if(x[maxXPos] < x[maxPos]){
				maxXPos = maxPos;
			}

		} finally {
			lock.unlock();
		}
	}




	private double getDistance(int x1, int y1, int x2, int y2, int x, int y){
		int a = y1 - y2;
		int b = x2 - x1;
		int c = y2 * x1 - y1 * x2;
		return a * x + b * y + c;
	}

	private IntList drawLineBetweenPoints(int p1, int p2, IntList m){
		IntList xcoords = new IntList();
		for(int i = 0; i < m.size(); i++){
			double distance = getDistance(x[p1], y[p1], x[p2], y[p2], x[m.get(i)], y[m.get(i)]);
			if(distance < 0.0){
					xcoords.add(m.get(i));
			}
		}

		return xcoords;
	}

	public int findPointWithMostNegativeDistance(int p1, int p2, IntList m){
		double val = 0.0;
		int xcoord = -1;

		for (int i = 0; i < m.size(); i++) {
				double distance = getDistance(x[p1], y[p1], x[p2], y[p2], x[m.get(i)], y[m.get(i)]);

				if (distance == 0.0) {
						int x3 = x[m.get(i)];
						int y3 = y[m.get(i)];

						if((y3 == y[p1] && y3 == y[p2]) &&
						(x3 > x[p1] && x3 < x[p2] || x3 < x[p1] && x3 > x[p2])){
								return m.get(i);
						} else if(( x3 == x[p1] && x3 == x[p2]) &&
						 (y3 > y[p1] && y3 < y[p2] || y3 < y[p1] && y3 > y[p2])){
								return m.get(i);
						}
				} else if (distance < val ) {
						val = distance;
						xcoord = m.get(i);
				}
		}
		return xcoord;
	}


	 public void sekvRek (int p1, int p2, int p3, IntList m, IntList koHyll){
	    int rightcoord = findPointWithMostNegativeDistance(p1, p3, m);
	    if (rightcoord != -1) {
	      IntList line = drawLineBetweenPoints(p1, p3, m);
	      sekvRek(p1, p3, rightcoord, line, koHyll);
	    }

	    koHyll.add(p3);

	  	int leftcoords = findPointWithMostNegativeDistance(p3, p2, m);
	    if (leftcoords != -1) {
	        IntList line = drawLineBetweenPoints(p3, p2, m);
	        sekvRek(p3, p2, leftcoords, line, koHyll);
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

			try{
				barrier.await();
			} catch(BrokenBarrierException bbe){
				return;
			} catch(InterruptedException ie){
				return;
			}

			if(ind == 0){
		    IntList xcoords = drawLineBetweenPoints(maxXPos, minXPos, m);
		    int xcoord = findPointWithMostNegativeDistance(maxXPos, minXPos, xcoords);
				sekvRek(maxXPos, minXPos, xcoord, xcoords, kohyll);
			} else if(ind == 1){
				IntList p = new IntList();
				IntList xcoords = drawLineBetweenPoints(minXPos, maxXPos, m);
				int xcoord = findPointWithMostNegativeDistance(minXPos, maxXPos, xcoords);
				sekvRek(minXPos, maxXPos, xcoord, xcoords, kohyll);
			}
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
