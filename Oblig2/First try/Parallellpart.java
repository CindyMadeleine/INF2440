import java.util.ArrayList;

public class Parallellpart{
  public static void runParallellProgram(int[] arr, int antInndata){
    CreativeArray ca = new CreativeArray();
    //generere først EratosthenesSil av alle primtall
    EratosthenesSil eos = new EratosthenesSil(antInndata);

    //faktorisere og skrive ut de 100 siste tallene t < N*N
    //Utskrive ut de 100 siste tallene t < N*N
    int hundresiste = arr.length - 100;
    //ca.printArr(arr, hundresiste, arr.length);
  }
}
