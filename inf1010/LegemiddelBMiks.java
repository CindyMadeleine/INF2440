/**
 * Vanedannende stoffer i miksturform
 * Created by martin on 26.02.2015.
 */
public class LegemiddelBMiks extends LegemiddelB implements Mikstur {

    private int flaskeStorrelse; //måles i cm3
    private int virkestoffMengde; // mg per cm3

    LegemiddelBMiks(String navn, Double pris, int vanedannende, int flaskeStorrelse, int virkestoffMengde) {
        super(navn, pris, vanedannende);
        this.flaskeStorrelse = flaskeStorrelse;
        this.virkestoffMengde = virkestoffMengde;
    }

    public int getVolume() {
        return flaskeStorrelse;
    }

    public int getEffect() {
        return virkestoffMengde;
    }
}
