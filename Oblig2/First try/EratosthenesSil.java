///--------------------------------------------------------
//
//     File: EratosthenesSil.java for INF2440
//     implements bit-array (Boolean) for prime numbers
//     written by:  Arne Maus , Univ of Oslo,
//
//--------------------------------------------------------
import java.util.*;
/**
* Implements the bitArray of length 'maxNum' [0..bitLen ]
*   1 - true (is prime number)
*   0 - false
*  can be used up to 2 G Bits (integer range)
*  Stores info on prime/not-prime in bits 0..6 in each byte
*  (does not touch the sign-bit - bit7)
*/
public class EratosthenesSil {
  byte [] bitArr ;           // bitArr[0] represents the 7 integers:  1,3,5,...,13, and so on
  int  maxNum;               // all primes in this bit-array is <= maxNum
  final  int [] bitMask = {1,2,4,8,16,32,64};  // kanskje trenger du denne
  final  int [] bitMask2 ={255-1,255-2,255-4,255-8,255-16,255-32,255-64}; // kanskje trenger du denne


  EratosthenesSil (int maxNum) {
    this.maxNum = maxNum;
    bitArr = new byte [(maxNum/14)+1];
    setAllPrime();
    generatePrimesByEratosthenes();
  } // end konstruktor ErathostenesSil

  void setAllPrime() {
    for (int i = 0; i < bitArr.length; i++) {
      bitArr[i] = (byte)127;
    }
  }

  void crossOut(int k) { //skal foregå i bit
    // set as not prime- cross out (set to 0)  bit represening 'int i'
        bitArr[p] = k % 16;
  } //

  boolean isPrime (int num) { //skal foregå i bit
    if(num > 2 && num % 2 == 0){ //sjekker om partall
      return false;
    } else if(num < 2){
      return false;
    }

    int greatestCompNum = (int)(Math.sqrt(num)+1); //tar kvadratroten av tallet og legger til en.
    for(int i = 3; i < greatestCompNum; i+=2){ //Sjekker kun oddetall
      if(num % i == 0){
        return false;
      }
    }

    return true;
  }

  ArrayList<Long> factorize (long num) {
    ArrayList <Long> fakt = new ArrayList <Long>();
    // <Ukeoppgave i Uke 7: din kode her>

    long piv;
    int curPrime;

    do{
      int pivprime = 0;
      curPrime = 1;
      while(pivprime < num){
        pivprime = nextPrime(pivprime);
        if(num % pivprime == 0){
          curPrime = pivprime;
        }
      }

      num = num / curPrime;
      piv = curPrime;
      if(curPrime != 1){
        fakt.add(piv);
      }
    } while(curPrime != 1);

    if(num > 1 || fakt.size() < 2){
      fakt.add(num);

      if(fakt.size() <= 1){
        piv = 1;
        fakt.add(piv);
      }
    }
    return fakt;
  } // end factorize


  int nextPrime(int num) { //lese av om noe er primtall ved bit?
    // returns next prime number after number 'i'
    if(num < 2){ //in case of negative numbers
      return 2;
    }

    int piv = num + 1;
    while(!isPrime(piv)){
      piv++;
    }

    return  piv;
  } // end nextTrue


  void printAllPrimes(){
    for ( int i = 2; i <= maxNum; i++)
      if (isPrime(i)) System.out.println(" "+i);

  }

  void generatePrimesByEratosthenes() {
    // krysser av alle  oddetall i 'bitArr[]' som ikke er primtall (setter de =0)
    for(int i = 0; i < 2; i++){
      bitArr[0] = 0;
    }

    crossOut(1);      // 1 is not a prime
    // < din Kode her, kryss ut multipla av alle primtall <= sqrt(maxNum),
    // og start avkryssingen av neste primtall p med p*p>



    int currprime = 2;
    while(currprime <= Math.sqrt(maxNum)){
      int cons = i*i;
      int piv = cons;

      /*starter vi avkryssingen for dette primtallet først for tallet p*p (i eksempelet: 25), men etter det
      krysses det av for p*p+2p, p*p+4p,.. */
      for(int i = 0; piv < maxNum; i++){
        crossout(piv);
        piv = cons + p*(2^i);
      }

      currprime = nextPrime(currprime);
    }
  } // end generatePrimesByEratosthenes


} // end class Bool
