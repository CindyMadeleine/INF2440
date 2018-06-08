import java.util.Scanner;

public class FirstArray
{
    public static void main(String[] args)
    {
	Scanner in = new Scanner(System.in);

	int[] heltall = {0,1,2,3};
	heltall = new int[4];
	int replacement;

	for(replacement = 0; replacement < heltall.length; replacement++) {
	heltall[replacement] = replacement	
	}

	heltall[0] = 1337;
	heltall[3] = 1337;

	System.out.println("Vennligst tast inn fem navn");
	String navn1 = in.nextLine();
	String navn2 = in.nextLine();
	String navn3 = in.nextLine();
	String navn4 = in.nextLine();
	String navn5 = in.nextLine();

	String[] navn  = {navn1, navn2, navn3, navn4, navn5};
	
    }
}