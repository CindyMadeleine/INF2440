/**
 * Created by martin on 26.02.2015.
 */
public abstract class Legemiddel {
    String navn;
    int nummer;
    static int legeMiddelTeller;
    Double pris;
    String type;

    public Legemiddel(String navn, Double pris) {
        this.navn = navn;
        this.nummer = legeMiddelTeller;
        this.pris = pris;
        legeMiddelTeller++;
    }

    public Double getPris(){
        return pris;
    }

    public int getNummer(){
        return nummer;
    }

    public String getNavn(){
        return navn;
    }
}
