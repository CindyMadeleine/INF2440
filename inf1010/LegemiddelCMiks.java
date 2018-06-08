/**
 * Vanlige legemidler i miksturform
 * Created by martin on 26.02.2015.
 */
public class LegemiddelCMiks extends LegemiddelC implements Mikstur {

    private int flaskeStorrelse; //måles i cm3
    private int virkestoffMengde; // mg per cm3

    LegemiddelCMiks(String navn, Double pris, int flaskeStorrelse, int virkestoffMengde) {
        super(navn, pris);
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
