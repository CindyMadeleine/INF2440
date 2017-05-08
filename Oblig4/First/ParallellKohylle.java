import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.ReentrantLock;

public class ParallellKohylle{
	IntList kohyll, m;
	int[] x, y;
	int minXPos, maxXPos;
	ReentrantLock lock;
	int nrCores;

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

		nrCores = Runtime.getRuntime().availableProcessors();
		if(nrCores < 2){
			//do sequentiel solution
			return;
		}
		Thread[] thrarr = new Thread[nrCores];

		//Prepare threaddata
		int arrPart = x.length / nrCores;
		int rest = x.length % nrCores;
		int start = 0, end = arrPart;

		for(int i = 0; i < nrCores; i++){
			if(rest > 0){
				end++;
				rest--;
			}

			thrarr[i] = new Thread(new MaxMinThread(i, start, end));
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


		// finner først ut hvor langt ned i kalltreet vi skal bruke tråder.
		int maxLevel = 0;
		for(double nrNodes = 0.0; nrNodes < nrCores; maxLevel++){
			nrNodes = Math.pow(2, maxLevel);
		}
		//maxLevel = 0; //REMEMBER TO DELETE THIS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		int level = 0;
		KohyllThread kt = new KohyllThread(level, maxLevel, maxXPos, minXPos, m);
		Thread t = new Thread(kt);
		t.start();

		KohyllThread kt2 = new KohyllThread(level, maxLevel, minXPos, maxXPos, m);
		Thread t2 = new Thread(kt2);
		t2.start();

		try{
			t.join();
			t2.join();
		} catch(InterruptedException ie){
			return;
		}

		kohyll.add(maxXPos);
		kohyll.append(kt.localkohyll);
		kohyll.add(minXPos);
		kohyll.append(kt2.localkohyll);
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



		public class KohyllThread implements Runnable{
			int level, maxLevel;
			IntList localkohyll;
			IntList m;
			int p1, p2;

			public KohyllThread(int level, int maxLevel, int p1, int p2, IntList m){
				this.level = level;
				this.maxLevel = maxLevel;
				this.p1 = p1;
				this.p2 = p2;
				this.m = m;
				localkohyll = new IntList();
			}

			public void run(){
				IntList xcoords = drawLineBetweenPoints(p1, p2, m);
				int xcoord = findPointWithMostNegativeDistance(p1, p2, xcoords);

				if(xcoord == -1){
					return;
				} else if(level < maxLevel){
					KohyllThread kt = new KohyllThread(level + 1, maxLevel, p1, xcoord, xcoords);
					Thread t = new Thread(kt);
					t.start();

					KohyllThread kt2 = new KohyllThread(level + 1, maxLevel, xcoord, p2, xcoords);
					Thread t2 = new Thread(kt2);
					t2.start();

					try{
						if(t != null){
								t.join();
								localkohyll.append(kt.localkohyll);
						}

						localkohyll.add(xcoord);

						if(t2 != null){
							 	t2.join();
						 		localkohyll.append(kt2.localkohyll);
						}
					} catch(InterruptedException ie){
						return;
					}
				} else {
					sekvRek(p1, p2, xcoord, xcoords, localkohyll);
				}
		}
	}


	public class MaxMinThread implements Runnable{
		int ind;
		CyclicBarrier barrier;
		int start, end;

		public MaxMinThread(int ind, int start, int end){
			this.ind = ind;
			this.barrier = barrier;
			this.start = start;
			this.end = end;
		}

		public void run(){
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
