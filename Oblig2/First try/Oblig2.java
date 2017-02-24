import java.lang.NumberFormatException;

public class Oblig2{
  public static void main(String[] args){
        if(args.length < 1){
          System.out.println("Oblig2 <inndata>");
          return;
        }


        int antInndata = 0;
        try{
          antInndata = Integer.parseInt(args[0]);
        } catch(NumberFormatException nfe){
          System.out.println("Verdi er ikke integer");
          return;
        }

        if(antInndata < 16){
          System.out.println("Verdi må vaere over eller lik tallet 16");
          return;
        }

        CreativeArray ca = new CreativeArray();
        int[] arr = ca.makeTestArr(antInndata);

        Sekvensiellpart sp = new Sekvensiellpart();
        sp.runSekvensiellProgram(arr, antInndata);
/*
        Parallellpart pp = new Parallellpart();
        pp.runParallellProgram(arr, antInndata);*/

  }
}
