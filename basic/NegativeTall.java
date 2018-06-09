import java.util.Scanner;

public class NegativeTall
{
	public static void main(String[] args) 
	{

		int[] a = {1, 4, 5, -2, -4, 6, 10, 3, -2};
		int i;
		int antallNegative = 0;

		for(i = 0; i < a.length; i++) {

			if (a[i] < 0) 
				{
				System.out.println("Dette er ett negativt tall: " + a[i] );
				antallNegative++;
				} 	
		}
		System.out.println("Det eksisterer " + antallNegative + " negative siffre");
		System.out.println("");
		System.out.println("");

		int n = 0;
		int antall = 0;

		while(n < a.length) 
		{
			if (a[n] < 0) 
				{
				System.out.println("Dette er ett negativt tall: " + a[n] );
				antall++;
				}
			n++; 	
		}
		System.out.println("Det eksisterer " + antall + " negative tall");
		System.out.println("");

		int ny;
		int erstatt = 0;

		
		for(i = 0; i < a.length; i++) {

			if (a[i] < 0) 
				{
				System.out.println("Dette er ett negativt tall: " + a[i] );
				antallNegative++;
				a[i] = i;
				System.out.println("Dette gir den positive versjonen av tallet: " + a[i] );
				} 	
		}
	}
}