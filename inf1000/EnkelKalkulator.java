import java.util.Scanner;

public class EnkelKalkulator
{
	
	public static void main(String[] args)
	{
		System.out.println("Du har nu entret kalkulatoren. ");
		System.out.println("Her kan du multiplisere, addere og subtrahere to verdier ");
		System.out.println("");
		System.out.println("");
		Scanner innleser = new Scanner(System.in);
		System.out.println("Velg verdi til X");
		int x = innleser.nextInt();
		System.out.println("");
		System.out.println("Velg verdi til Y");
		int y = innleser.nextInt();
		System.out.println("");
		System.out.println("");
		
		addere(x,y);
		System.out.println("");
		System.out.println("");
		subtrahere(x,y);
		System.out.println("");
		System.out.println("");
		multiplisere(x,y);
	}
	static void addere(int x, int y){
		System.out.println("Vi adderer " + x + " med " + y);
		int addisjon = x + y;
		System.out.println("Dette gir oss:    " + addisjon);
	}
	static void subtrahere(int x, int y){
		System.out.println("Vi subtraherer " + x + " med " + y);
		int subtrasjon  = x - y;
		System.out.println("Dette gir oss:    " + subtrasjon);
	}
	static void multiplisere(int x, int y){
		System.out.println("Vi multipliserer " + x + " med " + y);
		int multiplikasjon = x * y;
		System.out.println("Dette gir oss:    " + multiplikasjon);
	}
}