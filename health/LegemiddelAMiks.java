import java.security.PrivateKey;

/**
 * Narkotiske stoffer i miksturform
 * Created by martin on 26.02.2015.
 */
public class LegemiddelAMiks extends LegemiddelA implements Mikstur {

    private int flaskeStorrelse; //måles i cm3
    private int virkestoffMengde; // mg per cm3


    LegemiddelAMiks(String navn, Double pris, int styrke, int flaskeStorrelse, int virkestoffMengde) {
        super(navn, pris, styrke);
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
