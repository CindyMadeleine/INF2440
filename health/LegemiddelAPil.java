/**
 * Narkotiske stoffer i pilleform
 * Created by martin on 26.02.2015.
 */
public class LegemiddelAPil extends LegemiddelA implements Piller {

    private int pillerPrEske;
    private int virkestoffPrPille;

    LegemiddelAPil(String navn, Double pris, int styrke, int pillerPrEske, int virkestoffPrPille) {
        super(navn, pris, styrke);
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
