import java.util.Scanner;
public class Metoder
{
    public static void main(String[] args)
    {
	Scanner in = new Scanner(System.in);
	System.out.println("Mitt navn er Arnold. Hva heter du?");
	String navn = in.nextLine();
	System.out.println("");
	System.out.println("Hei " + navn + "! Hvor bor du?");
	String bosted = in.nextLine();
	System.out.println("");
	System.out.println("Kult! Du er fra " + bosted);

	System.out.println("");
	System.out.println("");
	System.out.println("Hei! Mitt navn er Arnold!");
	informasjon();
	System.out.println("");
	informasjon();
	System.out.println("");
	informasjon();
     }

    static void informasjon()
    {
	Scanner in = new Scanner(System.in);
	System.out.println("Hva heter du? ");
	String navn = in.nextLine();
	System.out.println("");
	System.out.println("Hei! " + navn);
	Scanner sted = new Scanner(System.in);
	System.out.println("Hvor bor du?");
	String bosted =sted.nextLine();
	System.out.println("Kult! Du er fra " + bosted);
    }
}

	
	
	