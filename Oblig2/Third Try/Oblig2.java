import java.util.*;

public class Oblig2{
	public static void main(String[] args){

		int maxNum = 100;
		long num = 10;
		//Sekvensiell(maxNum, num);
		parallell(maxNum, num);
	}

	public static void Sekvensiell(int maxNum, long num){
		EratosthenesSil es = new EratosthenesSil(maxNum);
		es.factorize(num);
		es.printAllPrimes();
	}

	public static void parallell(int maxNum, long num){
		EratosthenesSil es = new EratosthenesSil(maxNum);
		es.printAllPrimes();
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
	        generatePrimesByEratosthenes();
					//checkprimes();
	      } // end konstruktor ErathostenesSil

			void checkprimes(){
				//PrimeChecker pc = new PrimeChecker();
				//pc.readfile("1000firstprimes.txt");
				//pc.checkBitprimes(bitArr);
			}

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

				for(int idx = 2; idx <= num; idx++){
					if(num % idx == 0){
						num = num / idx;
						fakt.add((long) idx);
						idx = 2; //restarter for-løkken
						//break;
					}
				}

				fakt.add((long) num);

				for(int i = 0; i < fakt.size(); i++){
					System.out.println(fakt.get(i));
				}

			  return fakt;
		  } // end factorize


	      int nextPrime(int num) {
		   // returns next prime number after number 'i'
	   	   		num++; //for aa finne storre nummer
	   	   		if(num == 2){
	   	   			return num;
	   	   		} else if(num % 2 == 0){ //for aa ikke returnere partall
	      			num++;
	      		}

	      		for(; num < maxNum; num += 2){
	      			int piv = 2;

	      			for(; (num % piv) != 0; piv++){}

	      			if(num == piv){
      					return num;
      				}
	      		}

	          	return num;
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


	} // end class Bool

}
