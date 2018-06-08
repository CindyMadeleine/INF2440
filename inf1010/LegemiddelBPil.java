/**
 * Vanedannende stoffer i pilleform
 * Created by martin on 26.02.2015.
 */
public class LegemiddelBPil extends LegemiddelB implements Piller {

    private int pillerPrEske;
    private int virkestoffPrPille;

    LegemiddelBPil(String navn, Double pris, int styrke, int pillerPrEske, int virkestoffPrPille) {
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

    public int getTotalEffect(){
        return pillerPrEske * virkestoffPrPille;
    }
}
