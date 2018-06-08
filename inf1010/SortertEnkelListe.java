import java.lang.Comparable;
import java.lang.Iterable;
import java.util.Iterator; 
class SortertEnkelListe<E extends Comparable<E> & Lik> implements AbstraktSortertEnkelListe<E> { 
	Node foran = null;
	Node bak = null;
	int antall = 0; //elementer i lista
	

	/*
	@Override
    public int compareTo(E e) { //flyttes til alle klasser som skal sammenlignes
		if (midlertidig.data.compareTo(e) > 0) {
        return 1;
    }
		if (midlertidig.data.compareTo(e) < 0) {
        return -1;
    }
		return 0;
    }	
*/

    public SortertEnkelListe(){

    }

	public boolean settInnMinsteElement(E nyttElement){
		Node tmp = foran;
		Node forrige = foran;
		Node ny = new Node(nyttElement);
		Node beholder;
		int posisjon = 0;
		
		if(nyttElement == null) { //Dette skal egentlig ikke skje
			return false;
		}
		
		if (tmp == null) { //Sjekker om listen er tom, hvis så setter første element i liste og hopper ut
			foran = ny;
			bak = ny;
			//System.out.println("\"" + ((Lege) foran.data).toString() + "\" er eneste element i listen");
			antall++;
			return true;
		} 
		
		while (tmp != null) {
			if (tmp.data.compareTo(nyttElement) < 0) {
				posisjon++;
				forrige = tmp;
				tmp = tmp.neste;
				/*if (tmp != null) {
					System.out.println(((Lege) nyttElement).toString() + " > " + ((Lege)tmp.data).toString() + " -- iterating to " + posisjon + ".");
				}*/
			} else {
				//System.out.println(((Lege) nyttElement).toString() + " < " + ((Lege)tmp.data).toString() + " -- final position: " + posisjon);
				break;
			}
		}
		
		if(tmp == null) {
			forrige.neste = ny;
			bak = ny;
			/*System.out.println("\"" + ((Lege) ny.data).toString() + "\" lagt til bakerst i listen");
			System.out.println("Foran: " + ((Lege) foran.data).toString());
			System.out.println("Bak: " + ((Lege) bak.data).toString());*/
		} else if (tmp == foran) {
			beholder = foran;
			foran = ny;
			foran.neste = beholder;
		} else {
			beholder = tmp; //beholder elementet som ligger foran ny
			forrige.neste = ny;
			forrige.neste.neste = beholder; //ny.neste
			//System.out.println("\"" + ((Lege) forrige.neste.data).toString() + "\" lagt inn som nummer " + posisjon);
			//System.out.println("Plassert foran \"" + ((Lege) forrige.neste.neste.data).toString() + "\"");
		}		
		antall++;
		
		return true;
	}
	public E finnElement(String nokkel){ 
		Node tmp = foran;
		Iterator it = this.iterator();
		if(it.hasNext() && tmp.data.toString().equalsIgnoreCase(nokkel)){
			return tmp.data;
		}
		
		while(it.hasNext()){
			if(tmp.data.toString().equalsIgnoreCase(nokkel)){
				return tmp.data;
			}
			it.next();
		}
		System.out.println("Fant ikke et element som er likt nokkelen");
		return null;
	}
	
	public Iterator<E> iterator() { return new EnkelListeIterator(); }


	private class Node{
		Node neste;
		E data;

		public Node(E e){
			data = e;
		}
		
		public String toString(){
			return data + "";
		}
		
	}
	
	private class EnkelListeIterator implements Iterator<E> {
       int teller = 0; 
       Node pekerTilNeste = foran;
       
       public boolean hasNext() { return ( teller < antall );}
	   
       public void remove() {throw new UnsupportedOperationException(); }
	   
       public E next() {
			E returnerDenne = pekerTilNeste.data;
			pekerTilNeste = pekerTilNeste.neste;
			teller++;
			return returnerDenne;
       }
   }  
}
