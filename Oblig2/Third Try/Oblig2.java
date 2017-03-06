import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Oblig2 {
	public static void main(String[] args) {
		int nrRuns = 7;
		for(int i = 2000000; i <= 2000000000 ; i*=10){
			long totalNtime = 0;
			for(int j = 0; j < nrRuns; j++){
				long start = System.nanoTime();
				sekvensiell(i);    
				long elapsedTime = System.nanoTime() - start;
				System.out.println("Sekvensiell: Round " + j + " time:\t" + (elapsedTime / 1000000000.0));
				
				start = System.nanoTime();
				//parallell(i);
				oldParallell(i);
				elapsedTime = System.nanoTime() - start;
				System.out.println("Parallell: Round " + j + " time:\t" + (elapsedTime / 1000000000.0));
				
				totalNtime+=elapsedTime;
			}

			totalNtime /= nrRuns;
			System.out.println("N:\t" + i + "\tTotal time:\t" + (totalNtime / 1000000000.0));
		}
	}

	public static void sekvensiell(int maxNum){
		EratosthenesSil es = new EratosthenesSil(maxNum);
		es.generatePrimesByEratosthenes();
		int num = maxNum + maxNum;
		es.factorize(num);
	}
	
	public static void parallell(int maxNum){
		EratosthenesSil es = new EratosthenesSil(maxNum);
		int nrCores = Runtime.getRuntime().availableProcessors();
		es.setParallell(nrCores);
	}

	public static void oldParallell(int maxNum){
		// TODO Auto-generated method stub
		System.out.println("Loading " + maxNum);
		int nrCores = Runtime.getRuntime().availableProcessors();
		EratosthenesSil es = new EratosthenesSil(maxNum);
		es.setParallell(nrCores);

		ArrayList<Integer> startoddprimes = getStartOddPrimes(es);		
		int[][] numpartisions = partionBitArrayToThreads(es, nrCores);
		
		
		CyclicBarrier cb = new CyclicBarrier(nrCores+1);
		CyclicBarrier pb = new CyclicBarrier(nrCores+1);
		ErathostenesThread[] estarr = new ErathostenesThread[nrCores];


		long num = maxNum + maxNum;
		es.initializefactorization(num);

		int nrPrimes = (int) (Math.sqrt(num) / nrCores);
		int rest = (int) Math.sqrt(num) % nrCores;
		int startposval = 2;
		int endposval = nrPrimes;
		for(int idx = 0; idx < nrCores; idx++){	
			if(rest > 0){
				endposval++;
			}	

			ErathostenesThread est = new ErathostenesThread(cb, idx, startposval, endposval, numpartisions[idx], startoddprimes, es);
			estarr[idx] = est;
			Thread t = new Thread(est);
			t.start();
			
			startposval = endposval + 1;
			endposval += endposval;
		}
		
		System.out.println("initialization...");
		try{
			cb.await();
		} catch(Exception e){
			return;
		}

		cb.reset();
		es.stop = false;
		//es.printAllPrimes();
		System.out.println("primes...");

		try{
			cb.await();
		} catch(Exception e){
			return;
		}
		System.out.println("factorization");
		ArrayList<Long> al = es.getFakt();
		System.out.println(al);
		
		System.out.println("Sincerly yours, main()");
	}
	
	public static ArrayList<Integer> getStartOddPrimes(EratosthenesSil es){
		ArrayList<Integer> primes = es.getSqrtPrimes();
		return primes;
	}
	
	public static int[][] partionBitArrayToThreads(EratosthenesSil es, int nrThreads){
		int nrArr = es.bitArr.length;
		int nrSlices = (nrArr/nrThreads) + 1;
		
		int[][] partArrPos = new int[nrThreads][nrSlices];
		
		int threadnum = 0;
		int arrpart = 0;
		for(int arrNum = 0; arrNum < nrArr; arrNum++, threadnum++){	
			if(threadnum == nrThreads){
				threadnum = 0; //reset threadNum
				arrpart++;
			}
			
			partArrPos[threadnum][arrpart] = arrNum;
		}
		
		return partArrPos;
	}

public static class ErathostenesThread implements Runnable{
		int ind;
		int[] bitArrPos;
		CyclicBarrier cb;
		CyclicBarrier pb;
		EratosthenesSil es;
		ArrayList<Integer> startPrimes;
		int startVal, endVal;
		
		public ErathostenesThread(CyclicBarrier cb, int ind, int startVal, int endVal, int[] bitArrPos, ArrayList<Integer> startPrimes, EratosthenesSil es){
			this.ind = ind;
			this.bitArrPos = bitArrPos;
			this.cb = cb;
			this.startPrimes = startPrimes;
			this.es = es;
			this.startVal = startVal;
			this.endVal = endVal;
		}
		
		@Override
		public void run() {
			for(int prime : startPrimes){
				generateCrossOut(prime);
			}
			
			try{
				cb.await();
			} catch(Exception e){
				return;
			}

			factorize();

			try{
				cb.await();
			} catch(Exception e){
				return;
			}
		}

		public void factorize(){
			es.factorizeParallell(startVal, endVal);
		}
		
			
		public int calculateIndex(int prime, int startnum){
			int ind = (startnum / (2 * prime)) - (prime / 2);

			int piv =  prime * prime + (2 * ind * prime);
			
			 for(; piv < startnum; ind++){
				 piv = prime * prime + (2 * ind * prime);
			 }
			 
			 for(; piv > startnum; ind--){
				 piv = prime * prime + (2 * ind * prime);
			 }
			 
			return ind;
		}

		 void generateCrossOut(int prime){
			 for(int i = 0; i < bitArrPos.length; i++){
				 if(bitArrPos[i] == 0 && i > 0){
					 continue;
				 }
				 
				 //set startpos to the  first number in the byte the bitArrpos points to
				 int startnum = (bitArrPos[i] * es.bitPrByte * 2) + 1;
				 //set endpos to the last number in the byte the bitArrPos[i] points to
				 int endnum = ((bitArrPos[i] + 1) * es.bitPrByte * 2) - 1;
				 
				 //calculate which index to start at
				 int ind = calculateIndex(prime, startnum);
				 
				 int num = prime * prime + (2 * ind * prime); 
				 for(; num <= endnum; ind++){
					 if(num >= startnum & prime != num){
						es.crossOut(num);
					 }
					 num = prime * prime + (2 * ind * prime); 
				 }
			 }
		 }
	}
	public static class EratosthenesSil {
		byte [] bitArr ;           // bitArr[0] represents the 7 integers:  1,3,5,...,13, and so on
		int bitPrByte = 7;
		int  maxNum;               // all primes in this bit-array is <= maxNum
		final  int [] bitMask = {1,2,4,8,16,32,64};  // kanskje trenger du denne
		final  int [] bitMask2 ={255-1,255-2,255-4,255-8,255-16,255-32,255-64}; // kanskje trenger du denne
	    ArrayList<Long> fakt;
	    CyclicBarrier cb;
	    
		 EratosthenesSil (int maxNum) {
	        this.maxNum = maxNum;
			bitArr = new byte [(maxNum/14)+1];
			setAllPrime();
			fakt = new ArrayList<Long>();
		}

		void setParallell(int nrThreads){
			cb = new CyclicBarrier(nrThreads+1);
					//checkprimes();
	      } // end konstruktor ErathostenesSil

			void printBytes(){
				for (int i = 0; i < bitArr.length; i++) {
					System.out.println(Integer.toBinaryString(bitArr[i]));
				}
			}

		  void setAllPrime() {
			  for (int i = 0; i < bitArr.length; i++) {
			   	bitArr[i] = (byte)127;
		     }
		   }

	      void crossOut(int i) {
	       // set as not prime- cross out (set to 0)  bit represening 'int i
	             int bytenum = i/(bitPrByte*2);
	             int bitnuminByte = (i%(bitPrByte*2))/2;
	           	 int piv =  bitArr[bytenum] & ~(1 << bitnuminByte);
	             bitArr[bytenum] = (byte) piv;
		   } //

	      boolean isPrime (int val) {
	      	/*særskilt test for å teste at partall er under 2.*/
	      	if(val == 2){
	      		return true;
	      	} else if(val % 2 == 0){
	      		return false;
	      	}

			int bytenum = val/(bitPrByte*2);
	        int bitnuminByte = (val%(bitPrByte*2))/2;
	        return (bitArr[bytenum] & (1 << bitnuminByte)) != 0;
		 }
	      
	      
	      long currNum = 0;
	      boolean stop = true;
	      
	      public ArrayList<Long> getFakt(){
	    	  fakt.add(currNum);
	    	  return fakt;
	      }

	      public void initializefactorization(long num){
	    	  this.currNum = num;
	      }
	      
	      public synchronized void division(long divnum){
	      		if(currNum % divnum != 0){
	      			return;
	      		}

		  		currNum = currNum / divnum;
		  		fakt.add(divnum);
	       }
	      
		  public void factorizeParallell (int startnum, int endnum) {
		  		while(stop){}

		  		if(endnum > currNum){
		  			endnum = (int) currNum;
		  		}

				for(int idx = startnum; idx < endnum; idx++){
					if(currNum % idx == 0 && isPrime(idx)){
						division((long) idx);
						idx = startnum - 1;
					}
				}
		  } // end factorize


		  ArrayList<Long> factorize (long num) {
			  ArrayList <Long> fakt = new ArrayList <Long>();

				for(int idx = 2; idx <= num; idx++){
					if(num % idx == 0 && isPrime(idx)){
						num = num / idx;
						fakt.add((long) idx);
						idx = 1; //restarter for-løkken
						//break;
					}
				}

				fakt.add((long) num);
			  return fakt;
		  } // end factorize


		    public int nextPrime(int num) {
			   // returns next prime number after number 'i'
		    		if(num < 2){
		    			return 2;
		    		} 
		    	
		 	   		num++; //for aa finne storre nummer
		 	   		if(num % 2 == 0){ //for aa ikke returnere partall
		    			num++;
		    		}

		    		for(; num < maxNum; num += 2){
		    			int piv = 2;

		    			for(; (num % piv) != 0; piv++){}

		    			if(num == piv){
							return num;
						}
		    		}

		        	return num; //have not find a prime
			  } // end nextTrue

		 void printAllPrimes(){
			 for ( int i = 2; i <= maxNum; i++)
			  if (isPrime(i)) System.out.println(" "+i);

		 }


		  void generatePrimesByEratosthenes() {
			  // krysser av alle  oddetall i 'bitArr[]' som ikke er primtall (setter de =0)

			  		crossOut(1);      // 1 is not a prime
			       int prime = 3;

			       while(prime < Math.sqrt(maxNum)){
			       		int doubleprime = prime*prime;
								int piv = doubleprime;

			       		for(int i = 0; piv < maxNum ; i++){
									if (piv % 2 != 0){
										crossOut(piv);
									}
				       		piv = doubleprime + 2*i*prime;
			       		}
			       		prime = nextPrime(prime);
			       }
		 	 } // end generatePrimesByEratosthenes

		 
		  ArrayList<Integer> getSqrtPrimes() {
			       ArrayList<Integer> primes = new ArrayList<Integer>();

			       int currprime = 3;
			       
			       while(currprime < Math.sqrt(maxNum)){
			       		primes.add(currprime);
			       		currprime = nextPrime(currprime);
			       }
			       
			       return primes;
		 	 } // end generatePrimesByEratosthenes


	} // end class Bool

}
