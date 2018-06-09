/**
 * Created by martin on 26.02.2015.
 */
public class Fastlege extends Lege implements KommuneAvtale {
    private int avtalenummer;

    Fastlege( String navn, int avtalenummer) {
        super(navn);
        this.avtalenummer = avtalenummer;
    }

    public int getAvtalenummer() {
        return avtalenummer;
    }
}
