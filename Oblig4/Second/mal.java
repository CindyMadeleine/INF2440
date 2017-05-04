public class mal {
    static int MAX_Y;
    static int MAX_X;
    public int[] x, y;
    public int n;

    public static void main(String[] args) {
        mal oblig3 = new Oblig3();

        oblig3.oblig3();
        //oblig3.test();
    }

    private void test() {
        int n = 100;
        NPunkter np = new NPunkter(n);
        x = new int[n];
        y = new int[n];
        np.fyllArrayer(x, y);

        MAX_X = np.maxXY;
        MAX_Y = np.maxXY;
        n = np.n;

        IntList koHyll = new IntList();
        //sekvMetode(koHyll);
        int numThreads = Runtime.getRuntime().availableProcessors();
        parMetode(numThreads, koHyll);
        lookForDuplicates(koHyll);
        TegnUt tu = new TegnUt(kohyll, );
        printInnhylling(koHyll);
    }

    private void oblig3() {
        System.out.println("Number of threads: " + Runtime.getRuntime().availableProcessors());
        System.out.printf("################################################################################################\n");
        System.out.printf("## %11s ## %15s ## %15s ## %15s ## %7s ## %4s ##\n", "ELEMENTS", "PARALLEL", "SEQUENTIAL", "NUM ELEMENTS", "CORRECT", "SPEEDUP");
        System.out.printf("################################################################################################\n");

        double[] parExeResults = new double[9];
        double[] seqExeResults = new double[9];

        for (int n = 100; n <= 100000000; n *= 10) {
            IntList koHyll = null;
            for (int j = 0; j < 9; j++) { // Do every execution of a set 9 times.

                NPunkter np = new NPunkter(n);
                x = new int[n];
                y = new int[n];
                np.fyllArrayer(x, y);

                MAX_X = np.maxXY;
                MAX_Y = np.maxXY;
                n = np.n;

                koHyll = new IntList();
                /** SEQUENTIAL EXECUTION **/
                long startSeqTime = System.nanoTime();
                sekvMetode(this, koHyll, "");
                long endSeqTime = System.nanoTime();
                seqExeResults[j] = (endSeqTime - startSeqTime) / 1000000.0;
                TegnUt t = new TegnUt(this, koHyll);

                /** PARALLEL EXECUTION **/
                long startParTime = System.nanoTime();
                parMetode(4, koHyll);
                long endParTime = System.nanoTime();
                parExeResults[j] = (endParTime - startParTime) / 1000000.0;
            }

            double paraExecTime = parExeResults[(parExeResults.length) / 2];
            double seqExecTime = seqExeResults[(seqExeResults.length) / 2];
            System.out.printf("## %-11d ## %12f ms ## %12f ms ## %-11d ## %-7s ## %4f ##\n", n, paraExecTime, seqExecTime, koHyll.size(), lookForDuplicates(koHyll), (seqExecTime / paraExecTime));
        }
        System.out.printf("################################################################################################\n");

    }

    private void tegnHylle(IntList koHyll, int x[], int y[], int n) {
        TegnUt t = new TegnUt(this, koHyll);
        for (int i = 1; i > 0; i--) {
            i += 2;
        }
    }

    public boolean lookForDuplicates(IntList elements) {

        for (int i = 0; i < elements.size(); i++) {
            int value = elements.get(i);

            for (int j = 0; j < elements.size(); j++) {
                if (i != j) {
                    if (elements.get(i) == elements.get(j)) {
                        //System.out.println("## DUPLICATE ##");
                        //System.out.println("Index " + i + " and " + j);
                        //System.out.println("Value: " + elements.get(i));
                        //System.out.println("#############");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void parMetode(int numThreads, IntList m) {
        // Find min-x and max-x
        int[] maxXandMinX = getMaxXAndMinXIndex(x);
        int maxx = maxXandMinX[0]; // Indexen.
        int minx = maxXandMinX[1]; // Indexen.

        IntList allNumbers = new IntList();
        for (int i = 0; i < x.length; i++) {
            allNumbers.add(i);
        }

        Worker threads[] = new Worker[4];

        // OVER.
        IntList p = getPointsOverLine(maxx, minx, allNumbers);
        int p3 = getPointLongestAwayLine(maxx, minx, p);


        IntList pp = getPointsOverLine(maxx, p3, allNumbers);
        int p4 = getPointLongestAwayLine(maxx, p3, pp);

        threads[0] = new Worker(1, maxx, p3, p4, p);
        threads[0].start();


        IntList ppp = getPointsOverLine(p3, minx, allNumbers);
        int p5 = getPointLongestAwayLine(p3, minx, ppp);

        threads[1] = new Worker(2, p3, minx, p5, ppp);
        threads[1].start();

        // UNDER.
        IntList u1 = getPointsOverLine(minx, maxx, allNumbers);
        int p6 = getPointLongestAwayLine(minx, maxx, u1);


        IntList u2 = getPointsOverLine(p6, maxx, allNumbers);
        int p7 = getPointLongestAwayLine(p6, maxx, u2);

        threads[3] = new Worker(4, p6, maxx, p7, u2);
        threads[3].start();

        IntList u3 = getPointsOverLine(minx, p6, allNumbers);
        int p8 = getPointLongestAwayLine(minx, p6, u3);

        threads[2] = new Worker(3, minx, p6, p8, u3);
        threads[2].start();


        //System.out.printf("p3 = %d, p4 = %d, p5 = %d, \n\np6 = %d, p7 = %d, p8 = %d\n", p3, p4, p5, p6, p7, p8);

        //Threads done, loop through to write down there index;
        for (Worker w : threads) {
            try {
                w.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Worker w : threads) {
            IntList localKoHylle = w.getKoHylle();

            // Adds points to global hylle in right order.
            for (int i = 0; i < localKoHylle.size(); i++) {
                m.add(localKoHylle.get(i));
            }
        }
    }

    private void parMetode2(int numThreads, IntList m) {
        // Find min-x and max-x
        int[] maxXandMinX = getMaxXAndMinXIndex(x);
        int maxx = maxXandMinX[0]; // Indexen.
        int minx = maxXandMinX[1]; // Indexen.

        IntList allNumbers = new IntList();
        for (int i = 0; i < x.length; i++) {
            allNumbers.add(i);
        }

        Worker threads[] = new Worker[2];

        IntList p = getPointsOverLine(maxx, minx, allNumbers);
        int p3 = getPointLongestAwayLine(maxx, minx, p);

        System.out.printf("Points from %d -> %d. Point longest away %d\n", maxx, minx, p3);

        for(int i = 0; i < p.size(); i++) {
            //System.out.println("-- " + p.get(i));
        }
        //sekvRek(maxx, minx, p3, p, m);

        threads[0] = new Worker(1, maxx, minx, p3, p);
        threads[0].start();

        IntList pp = getPointsOverLine(minx, maxx, allNumbers);
        int p4 = getPointLongestAwayLine(minx, maxx, pp);
        threads[1] = new Worker(2, minx, maxx, p4, pp);
        threads[1].start();


        //Threads done, loop through to write down there index;
        for (Worker w : threads) {
            try {
                w.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Worker w : threads) {
            IntList localKoHylle = w.getKoHylle();

            // Adds points to global hylle in right order.
            for (int i = 0; i < localKoHylle.size(); i++) {
                m.add(localKoHylle.get(i));
            }
        }
    }

    private void sekvMetode(IntList m) {
        // Find min-x and max-x
        int[] maxXandMinX = getMaxXAndMinXIndex(x);
        int maxx = maxXandMinX[0]; // Indexen.
        int minx = maxXandMinX[1]; // Indexen.

        IntList allNumbers = new IntList();
        for (int i = 0; i < x.length; i++) {
            allNumbers.add(i);
        }

        IntList p = getPointsOverLine(maxx, minx, allNumbers);

        m.add(maxx);

        int p3 = getPointLongestAwayLine(maxx, minx, p);
        sekvRek(maxx, minx, p3, p, m);

        p.clear();
        p = getPointsOverLine(minx, maxx, allNumbers);

        m.add(minx);

        int p4 = getPointLongestAwayLine(minx, maxx, p);
        sekvRek(minx, maxx, p4, p, m);
    }

    private void sekvRek(int p1, int p2, int p3, IntList p, IntList m) {
        int right = getPointLongestAwayLine(p1, p3, p);
        if (right != -1) {
            sekvRek(p1, p3, right, getPointsOverLine(p1, p3, p), m);
        }

        m.add(p3);

        int left = getPointLongestAwayLine(p3, p2, p);
        if (left != -1) {
            sekvRek(p3, p2, left, getPointsOverLine(p3, p2, p), m);
        }
    }

    private IntList getPointsOverLine(int p1, int p2, IntList p) {
        IntList list = new IntList();

        for (int i = 0; i < p.size(); i++) {
            double distance = getDistanceFromLine(p1, p2, p.get(i));

            if (0.0 >= distance) {
                list.add(p.get(i));
            }
        }
        return list;
    }

    private boolean withinSquare(int p, int x, int y) {

        return false;
    }

    private int getPointLongestAwayLine(int p1, int p2, IntList p) {
        double value = 0.0;
        int valueIndex = -1;

        for (int i = 0; i < p.size(); i++) {
            double distance = getDistanceFromLine(p1, p2, p.get(i));

            //if (distance == 0.0) {
            if (distance == 0.0) {
                if (x[p.get(i)] < x[p2] && x[p.get(i)] > x[p1] && (y[p.get(i)] == y[p1] && y[p.get(i)] == y[p2])) {
                    return p.get(i);
                } else if (x[p.get(i)] < x[p1] && x[p.get(i)] > x[p2] && (y[p.get(i)] == y[p1] && y[p.get(i)] == y[p2])) {
                    return p.get(i);
                } else if (y[p.get(i)] < y[p2] && y[p.get(i)] > y[p1] && (x[p.get(i)] == x[p1] && x[p.get(i)] == x[p2])) {
                    return p.get(i);
                } else if (y[p.get(i)] < y[p1] && y[p.get(i)] > y[p2] && (x[p.get(i)] == x[p1] && x[p.get(i)] == x[p2])) {
                    return p.get(i);
                }
            } else if (value >= distance) {
                value = distance;
                valueIndex = p.get(i);
            }
        }
        return valueIndex;
    }

    private void printInnhylling(IntList m) {
        System.out.printf("### KONVEKS-INNHYLLING FOR %d PUNKTER ###\n", m.size());
        System.out.println("Hylle size: " + m.size());

        for (int i = 0; i < m.size(); i++) {
            System.out.printf("%d) %d\n", i, m.get(i));
        }

    }

    private int[] getMaxXAndMinXIndex(int[] x) {
        int[] result = new int[2];

        for (int i = 0; i < x.length; i++) {
            if (x[i] > x[result[0]]) {
                result[0] = i;
            }
            if (x[i] < x[result[1]]) {
                result[1] = i;
            }
        }
        return result;
    }

    /**
     * Returns distance from point (px, py) to  line (x1,y1) -> (x2, y2).
     */
    private double getDistanceFromLine(int p1, int p2, int p3) {
        int[] line = lineEquation(x[p1], y[p1], x[p2], y[p2]);
        return (line[0] * x[p3] + line[1] * y[p3] + line[2]);
    }

    /**
     * Returns line equation ax + by + c = 0 from cordinate (x1, y1) and (x2, y2).
     * Array index 0 contains ax, index 1: by and index 2: c.
     *
     * @return result array where first index contains a, second b and 3rd c.
     */
    private int[] lineEquation(int x1, int y1, int x2, int y2) {
        int[] result = new int[3];
        result[0] = y1 - y2; // ax
        result[1] = x2 - x1; // by
        result[2] = y2 * x1 - y1 * x2; // c
        return result;
    }

    private class Worker extends Thread {
        private int threadIndex;

        private int p1;
        private int p2;
        private int p3;
        private IntList p;
        private IntList m;

        public Worker(int threadIndex, int p1, int p2, int p3, IntList p) {
            this.threadIndex = threadIndex;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.p = p;
            this.m = new IntList();
            this.m.add(p1); // Must not be removed to get point in right order.
        }

        public void run() {
            if(p3 != -1) {
                sekvRek(p1, p2, p3, p, m);
            }
        }

        public IntList getKoHylle() {
            return m;
        }

    }

}
