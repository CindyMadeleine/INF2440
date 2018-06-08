/**
 * Created by martin on 26.02.2015.
 */
public class Person {
    private long foedselsNr;
    private String navn;
    private String adresse;
    private int postNummer;
    private int personID;
    private int antallNarkotiskeStoffer = 0;
    
	
    public Person(int PersonID, long foedselsNr, String navn, String adresse, int postNummer) {
        this.foedselsNr = foedselsNr;
        this.navn = navn;
        this.adresse = adresse;
        this.postNummer = postNummer;
        this.personID = personID;
    }

	@Override
    public String toString() {
        return Long.toString(foedselsNr);
    }

    public String getNavn() {
        return navn;
    }
    
    public String getAdresse() {
        return adresse;
    }

    public int getPostnr(){
        return postNummer;
    }

    
    public int getPersonID() {
        return personID;
    }

    public void setAntallNarkotiskeStoffer(){
        antallNarkotiskeStoffer++;
    }

    public int getAntallNarkotiskeStoffer(){
        return antallNarkotiskeStoffer;
    }
}
