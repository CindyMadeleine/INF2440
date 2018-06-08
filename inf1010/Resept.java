/**
 * Resepter, enten hvite eller blå
 * Created by martin on 26.02.2015.
 */
public abstract class Resept {
    private int reseptNr;
    Legemiddel lm;
    Lege l;
    private int personID = 0;
    private int antallGangerIgjen; // all reit
    private static int teller = 0;

    public Resept(Lege l, Legemiddel lm,int personID, int antallGangerIgjen) {
        this.reseptNr = teller;
        this.personID = personID;
        this.lm = lm;
        this.l = l;
        this.antallGangerIgjen = antallGangerIgjen;
        teller++;
    }

    public boolean reit() {
        if (antallGangerIgjen > 0) {
            return true; // its all reit
        }
        return false; // not all reit
    }

    public Legemiddel hentLegemiddel(){
        return lm;
    }

    public String toString(){
        return reseptNr + "";
    }

    public int getPersonID() {
        return personID;
    }

    public Lege hentLege(){
        return l;
    }

    public int hentReit(){
        return antallGangerIgjen;
    }
}
