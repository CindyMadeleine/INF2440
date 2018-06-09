/**
 * Vanedannende legemidler
 * Created by martin on 26.02.2015.
 */
public abstract class LegemiddelB extends Legemiddel {

    public int vanedannende;

    LegemiddelB(String navn, Double pris, int vanedannende) {
        super(navn, pris);
        this.vanedannende = vanedannende;
    }
}
