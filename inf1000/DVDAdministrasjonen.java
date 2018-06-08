import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;

public class DVDAdministrasjonen{
	private static ArrayList<DVD> kjopArkiv = new ArrayList<DVD>();
	private static ArrayList<DVD> laanArkiv = new ArrayList<DVD>();
	private static ArrayList<Person> personArkiv = new ArrayList<Person>();
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception{
		opprettFil();
		meny();
		System.out.println("\nVennlist velg\n ");
		String nValg = in.nextLine().toUpperCase();
		char valg = nValg.charAt(0);


		while(valg != 'A'){
			System.out.println("\nSkriv inn navn paa person");
			String navn = in.nextLine();
			String nNavn = navn.toLowerCase();
			Person n = new Person(nNavn);

			if(n.finnPerson(nNavn) == false && (valg != 'N')){ //blir tydeligvis oppettet her?
				System.out.println("Du maa opprette en bruker");
			}
			//her sjekker den om brukeren er allerede opprettet.

			if(valg == 'N') {
				opprettPerson(n, nNavn);
			}
			else if(valg == 'K' && n.finnPerson(nNavn) == true) {
				kjopFilm(n); //Her må en knytte det til en ett personsobjekt.
			}
			else if(valg == 'L' && n.finnPerson(nNavn) == true){
				leiFilm(n, nNavn);
			}
			else if(valg == 'R' && n.finnPerson(nNavn) == true) {
				returnerFilm(n);
			}
			else if(valg == 'O' && n.finnPerson(nNavn) == true){
				oversikt(n);
			}
			else if(valg == 'V' && n.finnPerson(nNavn) == true){
				videoOversikt(n);
			}
			
			System.out.println("\nVelg hva du onsker aa gjore");
			nValg = in.nextLine().toUpperCase();
			valg = nValg.charAt(0);
		}

		skrivTilFil();
	}

	public static void opprettFil() throws Exception {
		File fil = new File ("dvdarkiv.txt");
	    PrintWriter writer = new PrintWriter(fil);
	    /*Her opprettes en ny fil med filnavnet dvdarkiv.txt. Om filen allerede er 
	    opprettet skriver den over ny informasjonen til denne filen. */
	    
	}

	public static void meny(){
		System.out.println("Tast N for aa opprette nybruker.");
		System.out.println("Tast K for kjop av DVD.");
		System.out.println("Tast L for laan av DVD.");
		System.out.println("Tast R for aa returnere DVD");
		System.out.println("Tast O for aa vise oversikt");
		System.out.println("Tast V for aa vise personlig oversikt");
		System.out.println("Tast A for aa avslutte");
	}

	public static void opprettPerson(Person n, String navn){
		if(n.opprettPerson(navn) == true){
			//sjekker om personen finnes i arkivet, om den finnes legger den til true.
			personArkiv.add(n);
			//legger til personen om den ikke finnes allerede.
			System.out.println("Vi har nu opprettet en ny person til deg!");
		}
		else{
			System.out.println("Personen er allerede opprettet!");
		}
	}
	public static DVD hentDVD(String film){
		for(int i = 0; i < kjopArkiv.size(); i++) {
			if(film.equalsIgnoreCase(kjopArkiv.get(i).toString())){
				//Her sjekker en om navnet filmen personen har oppgitt, er lik den i kjopArkivet.
				return kjopArkiv.get(i);
				//om navnet på filmen matcher kjopArkivet returnerer den DVD'en.
			}
		}
		return null;
		//om den ikke finner noe match finner den.
	}

	public static void kjopFilm(Person eier){
		System.out.println("Skriv navn paa film "+ eier.toString() + " onsker aa kjope");
		String fNavn = in.nextLine().toLowerCase();
		DVD kjop = new DVD(fNavn);
		//oppretter film.

		kjop.settEier(eier);
		//her setter man navn på filmen
		
		kjopArkiv.add(kjop);
		//Her lagrer en filmen med eieren.

		if(hentDVD(fNavn) != null) { //blir registrert.
			if(hentDVD(fNavn).returnerEier().toString().equals(eier.toString())) {
				System.out.println("Du har nu kjopt filmen");
			}
		}
	}

	public static void leiFilm(Person laaner, String kommando){
		System.out.println("Skriv navn paa film " + laaner + " onsker aa leie?");
		String film = in.nextLine().toLowerCase();
		
		if(hentDVD(film) == null) { //vil ikke registrere at den har kjopt filmen
			System.out.println("Det finnes ingen som har kjopt filmen enda");
		}
		else {
			System.out.println("Hvem onsker du aa laane fra");
			String oEier = in.nextLine().toLowerCase();
			Person eier = new Person(oEier);

			if(eier.finnPerson(oEier) == false){
				System.out.println("Beklager men personen finnes ikke i arkivet");
				return;
			}

			byttEier(film, laaner, eier);
		}
	}
	public static void byttEier(String filmNavn, Person laaner, Person oEier){
		if(hentDVD(filmNavn) == null) {
		//Her sjekker en om det er noen filmer som matcher filmen personen har oppgitt.
			System.out.println("Beklager! Men det finnes for øyeblikket ingen som eier filmen");
			return;
		}
		else{
			for(int i = 0; i < kjopArkiv.size(); i++){
				if(oEier.toString().equalsIgnoreCase(kjopArkiv.get(i).returnerEier().toString())) {
					if(hentDVD(filmNavn).returnerLaaner() == null) {
						if(laaner.toString().equalsIgnoreCase(kjopArkiv.get(i).returnerEier().toString())){
							System.out.println("Beklager, men du eier allerede denne filmen");
							return;
						}
						else{
							DVD laan = hentDVD(filmNavn);
							/*Her lagrer vi ett DVD objekt med dataen vi har hatt
							i tidligere metoder. */ 
							laan.settLaaner(laaner);
							//Her setter vi laaner.
							laanArkiv.add(laan);
							//legger til dvd i arkivet.
							kjopArkiv.remove(hentDVD(filmNavn));
							/*Her sletter man den tidligere dvden, slik at det ikke finnes flere dvd'er
							av samme type*/
							System.out.println("Du har nu laant filmen");
							return;
						}
					}
				}
			}

			System.out.println("Beklager, men " + oEier.toString() + "har ikke denne filmen");
			System.out.println("Filmen finnes likevel i arkivet. Trykk o for aa sjekke hvem du kan laane den av");
		} 
	}

	public static DVD hentLaantFilm(String film){
		for(int i = 0; i < laanArkiv.size(); i++){
			if(film.equalsIgnoreCase(laanArkiv.get(i).toString())){
				//Her sjekker en om navnet filmen personen har oppgitt, er lik den i kjopArkivet.
				return laanArkiv.get(i);
			}
		}
		return null;
	}

	public static void returnerFilm(Person laaner){
		System.out.println("Hvilken film onsker du aa returnere?");
		String filmNavn = in.nextLine().toLowerCase();

		if(hentLaantFilm(filmNavn) == null){  //Det blir ikke opprettet noen dvd?
			System.out.println("Det finnes ingen slik film som er utleid");
		}
		else{
			for(int i = 0; i < laanArkiv.size(); i++){
				if(laaner.toString().equalsIgnoreCase(laanArkiv.get(i).returnerLaaner().toString())) {
					//sjekker om laaner finnes i arkivet.
					if(filmNavn.toString().equalsIgnoreCase(laanArkiv.get(i).toString())) {
						//sjekker om filmen er lik den personen ønsker aa levere.
						DVD film = laanArkiv.get(i);
						//oppretter ett nytt objekt film.
						film.slettLaaner();
						//sletter laan.
						laanArkiv.remove(laanArkiv.get(i));
						//sletter den ene filmen i arkivet.
						kjopArkiv.add(film);
						//legger til film i kjoperArkivet.
						System.out.println("Filmen er nu returnert.");
						return;
					}
				}
			}	
		}
	}

	public static void oversikt(Person navn){
		System.out.println("Hvem onsker du aa se for");
		System.out.println("Trykk * for aa se alle");
		String svar = in.nextLine();

		if(svar.equalsIgnoreCase("*")){
			for(int i = 0; i < personArkiv.size(); i++){
				Person tmp1 = personArkiv.get(i);
				System.out.println(tmp1.toString() + " har kjopt " + antallKjop(tmp1));
				System.out.println(tmp1.toString() + " har laant " + antallLaan(tmp1));
				System.out.println(tmp1.toString() + " har laant ut " + antallLaanUt(tmp1));
			}
			/*Her benytter vi oss av en enkel loop, slik at vi . Hver gang loopen foregår
			bytter den navn. Den varer så lenge det ikke er noen personer igjen i arkivet. */
		}
		else{
			System.out.println(navn.toString() + " har kjopt " + antallKjop(navn));
			//Inni antallKjop går loops, som regner ut hvor mye en person har kjopt.
			System.out.println(navn.toString() + " har laant " + antallLaan(navn));
			//Inni antallLaan går loops, som regner ut hvor mye en person har laant.
			System.out.println(navn.toString() + " har laant ut " + antallLaanUt(navn));
			//Inni antallLaan går loops, som regner ut hvor mye en person har laant.
		}
	}


	public static int antallKjop(Person n){
		int antall = 0;

		for(int i = 0; i < kjopArkiv.size(); i++){
			if(n.toString().equalsIgnoreCase(kjopArkiv.get(i).returnerEier().toString())){
				antall++;
			}
		}
		return antall;
	}

	public static int antallLaan(Person n){
		int antall = 0;

		for(int i = 0; i < laanArkiv.size(); i++){
			if(n.toString().equalsIgnoreCase(laanArkiv.get(i).returnerLaaner().toString())){
				antall++;
			}
		}
		return antall;
	}

	public static int antallLaanUt(Person n){
		int antall = 0;

		for(int i = 0; i < laanArkiv.size(); i++){
			if(n.toString().equalsIgnoreCase(laanArkiv.get(i).returnerEier().toString())){
				antall++;
			}
		}

		return antall;
	}

	public static void videoOversikt(Person navn){
		System.out.println("Hvem onsker du aa se for");
		System.out.println("Trykk * for aa se alle");
		String svar = in.nextLine();

		if(svar.equalsIgnoreCase("*")){
			 for(int i = 0; i < personArkiv.size(); i++){
				/*Her benytter vi oss av en enkel loop, slik at vi . Hver gang loopen foregår
				bytter den navn. Den varer så lenge det ikke er noen personer igjen i arkivet. */

				Person tmp = personArkiv.get(i);
				//Her oppretter vi en person for hver ny instanse.

				pOversikt(tmp);
				/*Hver av de tre loopene inneholder en loop, hvor det enten printes ut hvilken film personen
				har kjopt, lant eller laant ut.*/
			}
		}
		else {
			pOversikt(navn);
			/*Hver av de tre loopene inneholder en loop, hvor det enten printes ut hvilken film personen
			har kjopt, lant eller laant ut.*/
		}
	}

	public static void pOversikt(Person n){
		for(int i = 0; i < kjopArkiv.size(); i++){
			if(n.toString().equalsIgnoreCase(kjopArkiv.get(i).returnerEier().toString())){
				System.out.println(kjopArkiv.get(i).toString());
			}
		}

		for(int j = 0; j < laanArkiv.size(); j++){
			if(n.toString().equalsIgnoreCase(laanArkiv.get(j).returnerLaaner().toString())){
				System.out.println(laanArkiv.get(j).toString());
			}
		}

		for(int k = 0; k < laanArkiv.size(); k++){
			if(n.toString().equalsIgnoreCase(laanArkiv.get(k).returnerEier().toString())){
					System.out.print(kjopArkiv.get(k).toString() + "(laant av "); 
					System.out.println(laanArkiv.get(k).returnerLaaner().toString() + ")" );
			}
		}
	}

	public static void skrivTilFil() throws Exception {
	 	File fil = new File ("dvdarkiv.txt");
	    PrintWriter writer = new PrintWriter(fil);

	     for(int i = 0; i < personArkiv.size(); i++){
	     	writer.println(personArkiv.get(i).toString());
	     	//Her printer vi ut hvilken person i arkivet dette gjelder.

			for(int j = 0; j < kjopArkiv.size(); j++){
				if(personArkiv.get(i).toString().equalsIgnoreCase(kjopArkiv.get(j).returnerEier().toString())) {
					writer.println(kjopArkiv.get(j).toString());
				}
				//Loopen sjekker om personen har kjopt filmen til fil..
			}

			for(int k = 0; k < laanArkiv.size(); k++){
				if(personArkiv.get(i).toString().equalsIgnoreCase(laanArkiv.get(k).returnerLaaner().toString())){
					writer.println(laanArkiv.get(k).toString());
				}
				//Loopen sjekker om personen har lant filmen. Om den har lant filmen skriver den ut filmensnavn til fil.
			}

			for(int l = 0; l < laanArkiv.size(); l++){
				if(personArkiv.get(i).toString().equalsIgnoreCase(laanArkiv.get(l).returnerEier().toString())){
					if(laanArkiv.get(l).sjekkLaaner() == true){
						//sjekker om filmen er laant ut.
						writer.println("*" + laanArkiv.get(l).toString() + "(laant av" + laanArkiv.get(l).returnerLaaner().toString() + ")");
					}
				}
			}
			//Loopen sjekker om personen har en filmen som er laant ut. Om den er lant ut, så printes filmen til fil.
			writer.println("");
		}
		writer.close();
	}
}