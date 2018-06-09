import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class PrimeChecker {
Scanner sc;

  public void readfile(String name){
    try{
      File f = new File(name);
       sc = new Scanner(f);
    } catch(FileNotFoundException fnfe){
      return;
    }

    //ArrayList<String> s = new ArrayList();

    while(sc.hasNextLine()){
      String[] primes = sc.nextLine().split("\\s+");
      for(int i = 0; i < primes.length; i++){
        System.out.println(primes[i]);
      }
    }
  }

  public void checkBitprimes(byte[] bitArr){
    for(int i = 0; i < bitArr.length; i++){
      byte bitarrbyte = bitArr[i];
      String bin = Integer.toBinaryString(bitarrbyte);
      System.out.println(bin);
    }

  }
}
