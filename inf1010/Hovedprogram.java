import java.util.Scanner;
import java.io.PrintWriter;
import java.io.*;
import java.util.ArrayList;

public class Hovedprogram{

		static Tabell<Person> personTab = new Tabell<>(100);
		//beholder personer

		static Tabell<Legemiddel> lmTabell = new Tabell<>(100);
		//beholder legemidler

		static SortertEnkelListe<Lege> legeListe = new SortertEnkelListe<>();
		//beholder leger

		static EldsteForstReseptListe legeReseptE = new EldsteForstReseptListe();
		//beholder leger sine resepter.

		static YngsteForstReseptListe personReseptY = new YngsteForstReseptListe(); 
		//beholder personer sine resepter.	
		
		public static boolean sop = true; //slaar av og paa feilsokningsmeldinger
		
	public static void main(String[] args) {
		System.out.println("Hei! Velkommen til E-resept.");
		meny();
		Scanner in = new Scanner(System.in);
		int valg = Integer.parseInt(in.nextLine());
		
		while(valg != 0){
			
			switch(valg){
				case 1: lesDataFraFil();
						break;
				case 2: skrivDataTilFil();
						break;
				case 3: skrivUtData();
						break;
				case 4: opprettLege();
						break;
				case 5: opprettNyPerson();
						break;
				case 6: opprettResept();
						break;
				case 7: hentLegemiddelPaaResept();
						break;
				case 8: skrivUtVannedannendeResepter();
						break;
				case 9:	hentAntallbeboereiOslo();
						break;
				case 10: hentBlaaResepter();
						break;
				case 11: skrivUtResepterTilLege();
						break;
				case 12: samledeMengdeVirkestoffer();
						break;
				case 13: finnMisbrukAvNarkotika();
						break;
			}
			meny();
			valg = Integer.parseInt(in.nextLine());
		}
	}
	
	public static void meny(){
		System.out.println("For aa lese fra Fil, trykk 1");
		System.out.println("For aa skrive data til fil, trykk 2");
		System.out.println("For aa skrive ut alle personer, leger, legemiddler, samt alle resepter, trykk 3");
		System.out.println("For aa opprette og legge inn en ny lege, trykk 4");
		System.out.println("For aa opprette og legge inn en ny person, trykk 5");
		System.out.println("For aa opprette og legge inn en ny resept, trykk 6");
		System.out.println("For aa hente legemiddel paa resept, trykk 7");
		System.out.println("For aa skrive ut vanedannende resepter, trykk 8");
		System.out.println("For aa skrive ut antall beboere i Oslo, trykk 9");
		System.out.println("For aa hente blaa resepter, trykk 10");
		System.out.println("For aa hente lege, trykk 11");
		System.out.println("For aa hente samlede mengde virkestoffer, trykk 12");
		System.out.println("For aa finne misbruk av narkotika, trykk 13");
		System.out.println("For aa avslutte, trykk 0");
	}
	
	/**
	*	Leser inn alle data fra fil
	**/
	public static void lesDataFraFil() {
		String filnavn = "data.txt";//endre til brukerstyrt
		String nylinje; 
		
		Scanner sc = null;
		File fil;
		try{
			fil = new File(filnavn);
			sc = new Scanner(fil);
		} catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
		
		//nylinje = sc.nextLine();
		
		//while(!(nylinje.startsWith("# Slutt"))){
		while(sc.hasNextLine()){
			
			nylinje = sc.nextLine();
			if(nylinje.startsWith("# Personer")){
				if (sop) System.out.println("Personer funnet");
				nylinje = "abc";
				
				while(nylinje.length() > 2 && nylinje != null) {
					nylinje = sc.nextLine();
					if( sop) System.out.print(nylinje);
					String[] nyPerson = nylinje.split(", ");
					if (sop) System.out.println("While 2");
					if (sop) System.out.println("Lest alle personer");
					if (sop) System.out.println(nyPerson[0]);
					try {
						int nr = Integer.parseInt(nyPerson[0]);
						long fnr = 0;
						String navn = nyPerson[1];
						fnr = Long.parseLong(nyPerson[2]);
						String adresse = nyPerson[3];
						int postnr = Integer.parseInt(nyPerson[4]);
						Person p = new Person(nr, fnr, navn, adresse, postnr);
						System.out.println(p.getNavn());

						if(personTab.finnObjektPaaOppgittPlass(0) == null){
							personTab.settInnPaaOppgittPlass(0, p);
							if(sop) System.out.println("Personer opprettet i tomt array!");
						} else {
							int i = nr;

							while(!(personTab.finnObjektPaaOppgittPlass(i) == null)){
								i++;
							}
							personTab.settInnPaaOppgittPlass(i, p);
							if(sop)System.out.println(p.getNavn());
						}
						
					} catch (NumberFormatException nu) {
						break;
					}
					
				}
				if (sop) System.out.println("Escape from the While");
			
			} else if(nylinje.startsWith("# Legemidler")){
				nylinje = "abc";
				
				while(nylinje.length() > 2 && nylinje != null) {
					try {
				//kjor lesLegemidler()
					nylinje = sc.nextLine();
					if( sop) System.out.print(nylinje);
					String[] nyttLegemiddel = nylinje.split(", ");
					if (sop) System.out.println("While 2");
					if (sop) System.out.println("Lest alle legemiddel");
					if (sop) System.out.println(nyttLegemiddel[0]);
						int nr = Integer.parseInt(nyttLegemiddel[0]); 
						String navn = nyttLegemiddel[1]; 
						String form = nyttLegemiddel[2];
						String type = nyttLegemiddel[3];
						Double pris = Double.parseDouble(nyttLegemiddel[4]);
						int antall = Integer.parseInt(nyttLegemiddel[5]);
						int virkestoff = Integer.parseInt(nyttLegemiddel[6]);

						if(type.equalsIgnoreCase("a")){
							int styrke = Integer.parseInt(nyttLegemiddel[7]);

							if(form.equalsIgnoreCase("pille")){
								LegemiddelAPil l = new LegemiddelAPil(navn, pris, styrke, antall, virkestoff);
								lmTabell.settInnPaaOppgittPlass(nr, l);

							} else if(form.equalsIgnoreCase("mikstur")){
								LegemiddelAMiks l= new LegemiddelAMiks(navn, pris, styrke, antall, virkestoff);
								lmTabell.settInnPaaOppgittPlass(nr, l);

							}
						} else if(type.equalsIgnoreCase("b")){
							int styrke = Integer.parseInt(nyttLegemiddel[7]);
							if(form.equalsIgnoreCase("pille")){
								LegemiddelBPil l = new LegemiddelBPil(navn, pris, styrke, antall, virkestoff);

								lmTabell.settInnPaaOppgittPlass(nr, l);
							} else if(form.equalsIgnoreCase("mikstur")){
								LegemiddelBMiks l = new LegemiddelBMiks(navn, pris, styrke, antall, virkestoff);
								lmTabell.settInnPaaOppgittPlass(nr, l);

							}
						} else if(type.equalsIgnoreCase("c")){
							if(form.equalsIgnoreCase("pille")){
								LegemiddelCPil l = new LegemiddelCPil(navn, pris, antall, virkestoff);
								lmTabell.settInnPaaOppgittPlass(nr, l);

							} else if(form.equalsIgnoreCase("mikstur")){
								LegemiddelCMiks l = new LegemiddelCMiks(navn, pris, antall, virkestoff);
								lmTabell.settInnPaaOppgittPlass(nr, l);

							}
						}
						
						} catch (NumberFormatException nu) {
							break;
						}
			}
				if (sop) System.out.println("Escape from the While");
			
			} else if (nylinje.startsWith("# Leger")){
				
				if(sop)System.out.println("I Leger");
				nylinje = "abc";
				
				while(nylinje.length() > 2 && nylinje != null) {
					nylinje = sc.nextLine();
					if( sop) System.out.print(nylinje);
					String[] nyLege = nylinje.split(", ");
					if (sop) System.out.println("While 3");
					if (sop) System.out.println("Lest alle leger");
					
					try {
						String navn = nyLege[0];
						int nr = Integer.parseInt(nyLege[1]);

						if (nr == 0) {
							Lege l = new Lege(navn);
							legeListe.settInnMinsteElement(l);
							System.out.println("Lege lagt til");
						} else {
							
							Fastlege fl = new Fastlege(navn, nr);	
							legeListe.settInnMinsteElement(fl);
							System.out.println("Fastlege lagt til");
						} 
						
					} catch (NumberFormatException nu) {
						break;
					} catch (ArrayIndexOutOfBoundsException arr) {
						break;
					}
					
				}
			} else if(nylinje.startsWith("# Resepter")){
				if(sop)System.out.println("I Resept");
				nylinje ="abc";
				
				while(nylinje.length() > 2 && nylinje != null){
					nylinje = sc.nextLine();
					if( sop) System.out.print(nylinje);
					String[] nyResept = nylinje.split(", ");
					if (sop) System.out.println("While 3");
					if (sop) System.out.println("Lest alle Resepter");
					
					try {
						int nr = Integer.parseInt(nyResept[0]);
						String farge = nyResept[1];

						int persNummer = Integer.parseInt(nyResept[2]);

						String legeNavn = nyResept[3];
						Lege l = (Lege) legeListe.finnElement(legeNavn);

						int legemiddelNummer = Integer.parseInt(nyResept[4]);
						Legemiddel lm = lmTabell.finnObjektPaaOppgittPlass(legemiddelNummer);

						int antallGangerIgjen = Integer.parseInt(nyResept[5]);

						if(farge.equalsIgnoreCase("hvit")){
							Double pris = lm.getPris();
						 	HvitResept r = new HvitResept(l, lm, persNummer, antallGangerIgjen, pris);
						 	legeReseptE.settInnResept(r);
							personReseptY.settInnResept(r);

						} else if(farge.equalsIgnoreCase("blå")){
						 	BlaaResept r = new BlaaResept( l, lm, persNummer, antallGangerIgjen);
						 	legeReseptE.settInnResept(r);
							personReseptY.settInnResept(r);
						}
					} catch (NumberFormatException nu) {
						break;
					} catch (ArrayIndexOutOfBoundsException arr) {
						break;
					}
					
				}
				
			}
		}
		sc.close();
	}
	


	/**
	*	Skriver alle data til fil.
	**/

	public static void skrivDataTilFil(){
		String filnavn = "E-resept.txt";
		
		try{
			PrintWriter pw = new PrintWriter(filnavn);
			
			pw.write("# Personer (nr, navn, fnr, adresse, postnr)");
			
			int nr1 = 0;
			for(Person p : personTab){
				String navn = p.getNavn();
				String fnr = p.toString();
				String adresse = p.getAdresse();
				int postnr = p.getPostnr();

				pw.write(navn + ", " + fnr + ", " + adresse + ", " + postnr);
				nr1++;
			}
			
			pw.write("# Leger (navn, avtalenr / 0 hvis ingen avtale");
			
			for(Lege l : legeListe){
				String navn = l.toString();

				int avtalenr = 0;
				if(l instanceof Fastlege){
					Fastlege fl = (Fastlege) l;
					avtalenr = fl.getAvtalenummer();
				}

				pw.write(navn + ", " + avtalenr);
			}

			pw.write("# Resepter (nr, hvit/blå, persNummer, legeNavn, legemiddelNummer, reit)");

			int nr2 = 0;
			for(Resept r : legeReseptE){

				String farge = ""; 
				if(r instanceof BlaaResept){
					farge = "blå";
				} else if(r instanceof HvitResept){
					farge = "hvit";
				}

				int persNummer = r.getPersonID();
				if(sop) System.out.println(r.hentLege());
				String legeNavn = r.hentLege().toString();

				int legemiddelNummer = r.hentLegemiddel().getNummer();

				int reit = r.hentReit();

				pw.write(nr2 + ", " + farge + ", " + persNummer + ", " + legeNavn + ", " + legemiddelNummer + ", " + reit);
				nr2++;
			}

			pw.write("# Slutt");

			pw.close();
		} catch (FileNotFoundException f){
			f.printStackTrace();
		}
	}	
	
	/**
	*	Skriver ut alle personer, alle leger, alle legemidler og alle resepter
	**/
	public static void skrivUtData(){
		
		for(Legemiddel lm : lmTabell){
			System.out.println(lm.getNavn());
			if(sop) System.out.println("1");
		}

		for(Person p : personTab){
			System.out.println(p.getNavn());
			if(sop) System.out.println("2");
		}

		for(Lege l : legeListe){
			System.out.println(l.toString());
			if(sop) System.out.println("3");
		}

		for(Resept r : legeReseptE){
			System.out.println(r.toString());
			if(sop) System.out.println("4");
		}
	}

	/**
	*	Opprett og legger inn et nytt lege
	**/

	public static void opprettLege(){
		System.out.println("Onsker du aa opprette en fastlege eller en lege?");
		System.out.println("Tast 1 for lege /n + Tast 2 for Fastlege /n + Tast 3 for avbryt");
		int avtalenummer = 0;
		int valg = 0;
		Scanner in = new Scanner(System.in);
		
		try{
			valg = Integer.parseInt(in.nextLine());
		} catch(NumberFormatException num){
			System.out.println("Argument" + valg + "must be an integer.");
			return;
	
		}
		System.out.println("Tast inn navn");
		String navn = in.nextLine();
		Lege l = null;

		switch(valg) {
			case 1:	l = new Lege(navn);

			case 2:	System.out.println("Tast inn avtalenummer");
					avtalenummer = Integer.parseInt(in.nextLine());
					l = (Lege) new Fastlege(navn, avtalenummer);
		}

		if(l != null){
			legeListe.settInnMinsteElement(l);
			if(sop) System.out.println("Lege lagt til!");
		}

	}

	/**
	*	Opprett og legge inn en ny person
	**/
	public static void opprettNyPerson(){ //pasient
		System.out.println("Skriv inn navn paa person");
		Scanner in = new Scanner(System.in);
		String navn = in.nextLine();
		long foedselsnr = 0;
		int postnummer = 0;
		int nr = personTab.antallElementer;

		System.out.println("Skriv inn fodselsnummer");
		foedselsnr = Long.parseLong(in.nextLine());
		System.out.println("Skriv inn adresse");
		String adresse = in.nextLine();
		System.out.println("Skriv inn postnummer");
		postnummer = Integer.parseInt(in.nextLine());

		Person p = new Person(nr, foedselsnr, navn, adresse, postnummer);

		int index = personTab.getAntallElementer() + 1;
		personTab.settInnPaaOppgittPlass(index, p);

		System.out.println("Ny person lagt til med data "+" "+navn+" "+foedselsnr+" "+adresse+" "+postnummer);
		
	}

	/**
	* 	Opprett og legg inn ny resept
	**/
	public static void opprettResept(){												//må hente legemiddel og lege fra et sted.
		System.out.println("Onsker du aa opprette en blaa eller hvit resept");
		System.out.println("Hvis blaa, tast 1");
		System.out.println("Hvis hvit, tast 2");
		Scanner in = new Scanner(System.in);
		int farge = Integer.parseInt(in.nextLine());

		System.out.println("Skriv inn navn på lege/fastlege");
		String navn = in.nextLine();

		if(legeListe.finnElement(navn) == null){
			System.out.println("Du må opprette lege først");
			return;
		}

		Lege l = legeListe.finnElement(navn);

		System.out.println("Skriv inn navn paa id paa legemidler");
		String legemiddelID = in.nextLine();

		if(lmTabell.finnObjektEtterNokkel(legemiddelID) ==  null){
			System.out.println("Sorry! Men vi faar ikke tak i noen legemidler uten ID'en dems");
			return;
		}

		Legemiddel lm = lmTabell.finnObjektEtterNokkel(legemiddelID); 
		//skal hente leger og legemiddel


		int personID = 0;
		try{
			personID = Integer.parseInt(in.nextLine());
		} catch(NumberFormatException num){
			System.out.println("Argument" + personID + "must be an integer.");
			return;
		}

		if(personTab.finnObjektPaaOppgittPlass(personID) == null){
			System.out.println("Vi finner desverre ikke deg i systemet");
		}

		int antallGangerIgjen = 0;
		try{
			antallGangerIgjen = Integer.parseInt(in.nextLine());
		} catch(NumberFormatException num){
			System.out.println("Argument" + antallGangerIgjen + "must be an integer.");
			return;
		}

		double pris = 0;
		Resept r = null;
		switch(farge){
			case 1:	try{
						pris = Double.parseDouble(in.nextLine());
					} catch(NumberFormatException num){
						System.out.println("Argument" + antallGangerIgjen + "must be an integer.");
						return;
					}
					r = (Resept) new HvitResept(l, lm, personID, antallGangerIgjen, pris);
			case 2: r = (Resept) new BlaaResept(l, lm, personID, antallGangerIgjen);
		}

		legeReseptE.settInnResept(r);
		personReseptY.settInnResept(r);
	}

	
	/**
	*	Hente legemiddelet på en resept.
	**/
	public static Legemiddel hentLegemiddelPaaResept(){							//OBS LEGEMIDDEL ER ABSTRACT! Får nullpointerException
		Scanner in = new Scanner(System.in);

		System.out.println("Tast inn reseptnr");
		int reseptnr = Integer.parseInt(in.nextLine());

		Legemiddel lm = null;

		try{
			lm = personReseptY.finnResept(reseptnr).hentLegemiddel();
		} catch(NullPointerException np){
			np.printStackTrace();
		}

		System.out.println("Printer ut");
		return lm;
	}
	
	/**
	*	-går gjennom de ulike beholderne gjennom en for-each løkke
	*	-går til klassen Statistikk
	*	
	**/

	/**
	*	-Skriv ut hvor mange vanedannende resepter det finnes totalt
	**/

	public static void skrivUtVannedannendeResepter(){
		int antallVannedannendeResepter = 0;

		for(Resept r : personReseptY){//Kan hende at vi må forandre til LegemiddelBMiks || LegemiddelBPille
			if(r.hentLegemiddel() instanceof LegemiddelB){
				antallVannedannendeResepter++;
			}
		}

		for(Resept r : legeReseptE){
			if(r.hentLegemiddel() instanceof LegemiddelB){
				antallVannedannendeResepter++;
			}
		}

		System.out.println("Det finnes " + antallVannedannendeResepter + "antall vannedannende resepter");
	}

	/**
	*	-Skriv ut hvor mange som er skrevet ut til personer bosatt i Oslo
	**/

	public static void hentAntallbeboereiOslo(){
		for(Person p : personTab){				//Måtte legge til en metode, siden iteratoren ikke fungerer!
			if((p.getPostnr() + "").startsWith("0")){
				System.out.println(p.getNavn());
			}
		}

	}

	/**	-For en gitt person, 
	*	-skriv ut alle dens blå resepter, yngste resept først.
	*	- Personen identifiseres enten ved sitt fødselsnummer eller ved dets unike nummer i programmet. 
	**/
	public static void hentBlaaResepter(){
		Scanner in = new Scanner(System.in);

		System.out.println("Du har nu to valg");
		System.out.println("For aa tast inn nr paa person, tast 1");
		System.out.println("For aa tast inn fodselsnummer paa person, tast 2");
		int valg = Integer.parseInt(in.nextLine());

		Person p = null;
		switch(valg){
			case 1:	System.out.println("Du kan nu taste inn nr paa person");
					int index = Integer.parseInt(in.nextLine());
					p = personTab.finnObjektPaaOppgittPlass(index);
					break;
			case 2:	System.out.println("Du kan nu taste inn nr paa person");
					String fodselsnr = in.nextLine();
					p = personTab.finnObjektEtterNokkel(fodselsnr);
					break;
		}
		
		if(p == null){
			System.out.println("Finner ikke person!");
			return;
		}

		for(Resept r : personReseptY){	//For-each løkke fungerer!
			if(r instanceof BlaaResept && (r.getPersonID() == p.getPersonID())){
					System.out.println("Finner blaa resepter på personen");
						System.out.println(r.toString());
					//mangler test på om personen sitt resept er vanedannende og blå
			}
		}
	}

	/**
	*	-For en lege med et gittnavn, 
	*	-skriv ut alle legens resepter på mikstur-preparater, eldste resept først.
	**/
	public static void skrivUtResepterTilLege(){
		Scanner in = new Scanner(System.in);
		System.out.println("Skriv inn navn på lege");
		String navn = in.nextLine();

		String legenavn = legeListe.finnElement(navn).toString();

		for(Resept r : legeReseptE){
			if(r.hentLege().toString().equalsIgnoreCase(legenavn)){
				if((r.hentLegemiddel() instanceof LegemiddelAMiks) || r.hentLegemiddel() instanceof LegemiddelBMiks || r.hentLegemiddel() instanceof LegemiddelCMiks){
					System.out.println(r.toString());
				}
			}
		}
	}

	/*
	*	-Skriv ut den samledemengde virkestoff for alle resepter legen har skrevet ut
	*	-Skriv ut hvor mye av dette som er i pilleform og hvor mye er mikstur.
	*	
	*/
	public static void samledeMengdeVirkestoffer(){
		Scanner in = new Scanner(System.in);
		System.out.println("Skriv inn navn på lege");
		String navn = in.nextLine();

		String legenavn = legeListe.finnElement(navn).toString();

		int samledeVirkestoffIMikstur = 0;
		int samledeVirkestoffIPilleform = 0;

		for(Resept r : legeReseptE){
			if(r.hentLege().toString() == legenavn){
				if(r.hentLegemiddel() instanceof LegemiddelBMiks){
					samledeVirkestoffIMikstur += ((LegemiddelBMiks) r.hentLegemiddel()).getEffect();
				} else if(r.hentLegemiddel() instanceof LegemiddelBPil){
					samledeVirkestoffIPilleform += ((LegemiddelBPil) r.hentLegemiddel()).getTotalEffect();
				}
			}
		}

		System.out.println("Den samlede mengden virkestoff er " + (samledeVirkestoffIMikstur + samledeVirkestoffIPilleform));
		System.out.println("Den samlede mengde virkestoff i mikstur er " + samledeVirkestoffIMikstur);
		System.out.println("Den samlede mengde virkestoff i pilleform er " + samledeVirkestoffIPilleform);
	}


	/**
	*		For å finne medisinsk misbruk av narkotika gjør to ting:
	*		1) List opp navnene på alle leger (i alfabetisk rekkefølge) som har skrevet ut minst en (gyldig eller ikke) resept på narkotiske legemidler, 
	*			ogantallet slike resepter per lege.
	*		2) List opp navnene på alle personer som har minst en gyldig resept på narkotisk legemiddler, og for disse, skriv ut antallet per person.
	*			Skriv en kommentar i programmet om hva dere bør gjøre hvis dissesistetospørringene (om narkotiske resepter)utføres veldig ofte.
	**/
	public static void finnMisbrukAvNarkotika(){
		ArrayList<Person> personliste = new ArrayList<Person>();
		int teller = 0;

		for(Resept r : legeReseptE){
			if(r.hentLegemiddel() instanceof LegemiddelA){
				System.out.println(r.hentLege().toString());
			}
		}

		for(Resept r : personReseptY){
			if(r.hentLegemiddel() instanceof LegemiddelA){
				int personID = r.getPersonID();

				for(Person p: personTab){
					if(p.getPersonID() == personID){
						System.out.println(p.getNavn());
						boolean pExist = false;

						for(Person p2 : personliste){
							if(p.getNavn().equalsIgnoreCase(p2.getNavn())){
								pExist = true;
							}
						}

						if(pExist == false){
							personliste.add(p);
						}
					}
				}

				for(Person p : personliste){
					if(p.getPersonID() == personID){
						p.setAntallNarkotiskeStoffer();
					}
				}
			}
		}

		for(Person p : personliste){
			System.out.println(p.getNavn() + "har faatt " + p.getAntallNarkotiskeStoffer() + "paa resept");
		}
	}

}