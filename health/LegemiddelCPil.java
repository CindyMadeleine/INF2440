/**
 * Vanlige legemidler i pilleform
 * Created by martin on 26.02.2015.
 */
public class LegemiddelCPil extends LegemiddelC implements Piller {

    private int pillerPrEske;
    private int virkestoffPrPille;

    LegemiddelCPil(String navn, Double pris, int pillerPrEske, int virkestoffPrPille) {
        super(navn, pris);
        this.virkestoffPrPille = virkestoffPrPille;
        this.pillerPrEske = pillerPrEske;
    }

    public int getPills() {
        return pillerPrEske;
    }
    public int getPillEffect() {
        return virkestoffPrPille;
    }
}
