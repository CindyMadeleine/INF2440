import java.util.*;
import java.util.concurrent.CyclicBarrier;

public class Oblig2{
	public static void main(String[] args){
		long starttime, finishtime;
		double totTime;
		int num = 100;
		Sekvensiell(num);
		/*for(int num = 2000000; num <= 2000000000; num*=10){
				System.out.println("Num:\t" + num);
				double numTimeParallell = 0;
				double numTimeSekvensiell = 0;
				for(int round = 0; round < 7; round++){

					//System.out.println("N:\t"+num + "\tRound: " + round);
					starttime = System.nanoTime();
					Sekvensiell(num);
					finishtime = System.nanoTime();
					totTime = (finishtime - starttime) / 1000000.0;
					numTimeSekvensiell += totTime;
					//System.out.println("Sekvensiell:\t" + totTime);

					starttime = System.nanoTime();
					parallell(num);
					finishtime = System.nanoTime();
					totTime = (finishtime - starttime) / 1000000.0;
					numTimeParallell += totTime;
					//System.out.println("Parallell:\t" + totTime);
				}

				System.out.println("Sekvensiell\t" + numTimeSekvensiell + "\nparallell\t" + numTimeParallell);
			}*/
	}

	public static void Sekvensiell(int maxNum){
		EratosthenesSil es = new EratosthenesSil(maxNum);
		es.generatePrimesByEratosthenes();
		//es.printAllPrimes();

		long firstnum = maxNum + maxNum;
		long lastnum = firstnum - 100;
		for(long num = firstnum; num > 0 && num > lastnum; num--){
			ArrayList<Long> faktarr = es.factorize(num);
			System.out.println(faktarr);
		}
	}

	public static void parallell(int maxNum){
		EratosthenesSil es = new EratosthenesSil(maxNum);
		long num = maxNum + maxNum;

		ArrayList<Integer> startprimes = es.generateSqrtPrimes();

		int nrCores =  Runtime.getRuntime().availableProcessors();
		CyclicBarrier cb = new CyclicBarrier(nrCores+1);
		int nrPrimesPrThread = startprimes.size()/nrCores;

		int rest = startprimes.size()%nrCores;
		int startidx = 0;
		int endidx = nrPrimesPrThread;
		EratosthenesSilThread[] estarr = new EratosthenesSilThread[nrCores];
		for(int i = 0; i < nrCores; i++){
			if(rest > 0){
				endidx++;
				rest--;
			}

			EratosthenesSilThread est = new EratosthenesSilThread(i, cb, startprimes, startidx, endidx, maxNum);
			estarr[i] = est;
			Thread estThr = new Thread(est);
			estThr.start();
			startidx = endidx;
			endidx += nrPrimesPrThread;
		}

		try{
			cb.await();
		}catch(Exception e){

		}

		for(int i = 0; i < es.bitArr.length; i++){
			for(int j = 0; j < estarr.length; j++){
				es.bitArr[i] = (byte) (es.bitArr[i] & estarr[j].es.bitArr[i]);
			}
		}

		es.printAllPrimes();
	}

	public static class EratosthenesSilThread implements Runnable{
		ArrayList<Integer> startprimes;
		int startidx, endidx;
		int ind;
		CyclicBarrier cb;
		EratosthenesSil es;

		public EratosthenesSilThread(int ind, CyclicBarrier cb, ArrayList<Integer> startprimes, int startidx, int endidx, int maxNum){
			this.startprimes = startprimes;
			this.startidx = startidx;
			this.endidx = endidx;
			this.ind = ind;
			this.cb = cb;
			this.es = new EratosthenesSil(maxNum);
		}

		public void run(){
			//for hvert av primtallene kaller jeg generatePrimesByEratosthenesByPrime
			for(int i = startidx; i < endidx; i++){
				es.generatePrimesByEratosthenesByPrime(startprimes.get(i));
			}

			try{
				cb.await();
			}catch(Exception e){

			}
		}
	}

	public static class EratosthenesSil {
		byte [] bitArr ;           // bitArr[0] represents the 7 integers:  1,3,5,...,13, and so on
		int bitPrByte = 7;
		int  maxNum;               // all primes in this bit-array is <= maxNum
		final  int [] bitMask = {1,2,4,8,16,32,64};  // kanskje trenger du denne
		final  int [] bitMask2 ={255-1,255-2,255-4,255-8,255-16,255-32,255-64}; // kanskje trenger du denne


		 EratosthenesSil (int maxNum) {
	        this.maxNum = maxNum;
					bitArr = new byte [(maxNum/14)+1];
					setAllPrime();
					crossOut(1);      // 1 is not a prime
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

		  ArrayList<Long> factorize (long num) {
			  ArrayList <Long> fakt = new ArrayList <Long>();

				for(int idx = 2; idx <= Math.sqrt(num); idx++){
					if(isPrime(idx) && num % idx == 0){
						num /= idx;
						fakt.add((long) idx);
						idx = 1; //restarter for-løkken
					}
				}

				fakt.add(num);
			  return fakt;
		  } // end factorize


	      int nextPrime(int previousPrime) {
		   // returns next prime number after number 'i'
	   	   		previousPrime+=2; //for aa finne storre nummer

						int nextPrime = previousPrime;
						for(; nextPrime < maxNum; nextPrime+=2){
							if(isPrime(nextPrime)){
								return nextPrime;
							}
						}

							return nextPrime;
		  } // end nextTrue


		 void printAllPrimes(){
				for ( int i = maxNum, counter = 0; i > 0 && counter < 100; i--){
				  if (isPrime(i)){
							System.out.println(" "+i);
							counter++;
					}
				}

		 }

		  void generatePrimesByEratosthenes() {
			  // krysser av alle  oddetall i 'bitArr[]' som ikke er primtall (setter de =0)
			       int prime = 3;

			       while(prime < Math.sqrt(maxNum)){
			       		generatePrimesByEratosthenesByPrime(prime);
			       		prime = nextPrime(prime);
			       }
		 	 } // end generatePrimesByEratosthenes

			 void generatePrimesByEratosthenesByPrime(int prime){
						int doubleprime = prime*prime;
						int piv = doubleprime;

						for(int i = 0; piv < maxNum ; i++){
							if (piv % 2 != 0){
								crossOut(piv);
							}
							piv = doubleprime + 2*i*prime;
						}
			 }

			 ArrayList<Integer> generateSqrtPrimes(){
				 ArrayList<Integer> primes = new ArrayList<Integer>();

				 int prime = 3;
				 while(prime < Math.sqrt(maxNum)){
					 primes.add(prime);
					 prime = nextPrime(prime);
				 }
				 return primes;
			 }


	} // end class Bool

}
