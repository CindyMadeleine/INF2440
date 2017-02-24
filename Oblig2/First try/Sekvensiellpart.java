import java.util.ArrayList;

public class Sekvensiellpart{
  public static void runSekvensiellProgram(int[] arr, int antInndata){
    CreativeArray ca = new CreativeArray();

    //generere først EratosthenesSil av alle primtall
    EratosthenesSil eos = new EratosthenesSil(antInndata);

    long starttime = System.nanoTime();

    //faktorisere de 100 siste tallene t < N*N
    //for(int i = 0; i < arr.length; i++){
      int num = arr[0];
      ArrayList<Long> fakt = eos.factorize(num);
      if(fakt != null){
        System.out.println(num + " can be factorized " + fakt);
      }
    //}


    //Utskrive ut de 100 siste tallene t < N*N
    int hundresiste = arr.length - 100;
    if(hundresiste < arr.length){
      hundresiste = 0;
    }
    //ca.printArr(arr, hundresiste, arr.length);

    long tottime = ((System.nanoTime() - starttime) / 100);
  }

}
