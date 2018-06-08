/**
 * Narkotiske legemidler
 * Created by martin on 26.02.2015.
 */
public abstract class LegemiddelA extends Legemiddel {

    public int styrke;

    LegemiddelA(String navn, Double pris, int styrke) {
        super(navn, pris);
        this.styrke = styrke;
    }
}
