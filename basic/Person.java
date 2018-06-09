import java.util.HashMap;

public class Person {
	private static HashMap<String, Person> navnArkiv = new HashMap<String, Person>();
	private String navn;

	public Person(String navn){
		this.navn = navn;
	}

	public String toString(){
		return navn;
	}

	public boolean finnPerson(String n){
		if(navnArkiv.get(navn) != null){
			return true;
			//returnerer false om personen finnes i arkivet.
		}
		return false;
	}

	public boolean opprettPerson(String navn){
		Person n = new Person(navn);

		if(finnPerson(navn) == false){
			navnArkiv.put(navn, n);
			return true;
			//returnerer true om personen ikke finnes i arkivet.
		}
		return false;
	}
	/*Her anvender vi hashmap, i stedet for en loop for å sjekke om personen er i arkivet. 
	Om den er i arkivet returnerer den er i arkivet går den ut av metoden. Om den ikke er i arkivet
	legger den til den nye brukeren. */
}