import java.util.Scanner;

public class SumTall
{
    public static void main(String[] args)
    {
	Scanner in = new Scanner(System.in);
	System.out.println("Dette programmet vil fortsette helt til du oppgir tallet 0");
	System.out.println("Vennligst tast inn et  tall: ");
	String linje = in.nextLine();
	double helTall = Double.parseDouble(linje);
	double sum = 0;
	double tall = 1;
	
	while (helTall != 0) { //Så lenge tall er ulikt null, gjør dette.
		System.out.println("Tast inn ett tall til");
		linje = in.nextLine();
		helTall = Integer.parseInt(linje);
		/* helTall er allerede deklarert
		 vi plasserer ny verdi i helTall*/
		sum += helTall; 
		/*sum = sum + helTall 
		  legger til tallet bruker har oppgitt i while løkka*/ 
	    }
	System.out.println(sum + helTall); 
	/* Her summerer vi sammen tallene vi har brukt i while.
	  I tillegg til den første helTall. */
    }
}

