import java.util.Arrays;

public class Sorting{
  public static void main(String[] args){
    if(args.length < 2){
      System.out.println("Ulovlig antall av parametre. Vennligst angi to hele sifre");
      return;
    }

    int nrAnswers, nrGreatest;
    try{
      nrAnswers = Integer.parseInt(args[0]); //n
      nrGreatest = Integer.parseInt(args[1]); //k
    }catch(NumberFormatException nfe){
      System.out.println("Parametrene er ikke et heltall");
      return;
    }

    if(nrAnswers < nrGreatest){
      System.out.println("Error! Antall svar er mindre enn antall storste nr.\nHusk at første siffer er antall svar og andre siffer er storste nummer");
      return;
    } else if(nrAnswers == 0){
      System.out.println("Error! Du har ikke fått noen søkematch");
      return;
    }

    CreativeArray ca = new CreativeArray();
		int arr[] = ca.makeArr(nrAnswers);

		long starttime = System.nanoTime();
    Arrays.sort(arr);
    long finishtime = System.nanoTime();
    double totTime = (finishtime - starttime) / 1000000.0;
    System.out.println("Tid paa aa fullfore:\t" + totTime);
  }


}
