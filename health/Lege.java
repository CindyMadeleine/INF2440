/**
 * Created by martin on 26.02.2015.
 */
public class Lege implements Lik, Comparable<Lege> {
	
	String navn;
	
	public Lege(String navn) { //brukes til oblig 7
		this.navn = navn;
	}

  

    public boolean samme(String navn) {
        return navn.equals(navn);
    }
	
	public String toString() {
		return navn;
	
	}
	
	public int compareTo(Lege l) { //flyttes til alle klasser som skal sammenlignes		
		
		if (navn.compareTo(l.toString()) > 0) {
        return 1;
    }
		if (navn.compareTo(l.toString()) < 0) {
        return -1;
    }
		return 0;
    }
}
