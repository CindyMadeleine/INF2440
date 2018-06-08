import java.util.Scanner;

public class Forlokker
{
    public static void main(String[] args)
    {
	//Skriver ut partall mellom 0 og 19
	//finn summen av alle partall mellom 0 og 10
	//finn summen av alle oddetall mellom 0 og 10.

	int[] partall = {0,2,4,6,8,10};
	int[] oddetall = {1,3,5,7,9};
	int sum = 0;
	int sum1 = 0;
	
	System.out.println("Her er alle partall mellom 0 og 10");

	for (int par : partall) {
	    System.out.println("tallet " + par);
	    sum += par;
	}
	System.out.println("Summen av alle partall mellom 0 og 10 er " + sum);

	for (int odd : oddetall) {
	    System.out.println("tallet " + odd);
	    sum1 += odd;
	}
	System.out.println("Summen av alle oddetall mellom 0 og 10 er " + sum1);
    }
}