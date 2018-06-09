/**
 * Created by martin on 26.02.2015.
 */
public class HvitResept extends Resept {
    private double pris;

    HvitResept( Lege l, Legemiddel lm, int personID, int antallGangerIgjen, double pris) {
        super(l, lm, personID, antallGangerIgjen);
        this.pris = pris;
    }
}
