import java.lang.Iterable;
import java.util.*;
public class EnkelReseptliste implements Iterable<Resept>{				//Vi har her opprettet en lenkeliste, som heter enkelreseptliste, uten parametere.
	protected Node forste = null;
	protected Node bakerste = null;
	protected int antallResepter = 0;

	
	
	
	public void settInnResept(Resept ny){ 	
		ReseptIterator resept = iterator();
		Node nyNode = new Node(ny);

		if(forste == null){
			settInnForste(nyNode);
		} else {	
			Node tmp = new Node(ny);

			while(resept.hasNext()){
				if(tmp.neste == null){
					tmp.neste = nyNode;		
					break;
				} else {	
					tmp = tmp.neste;		
				}
			}

			Node bakerste = nyNode; 			 
		}
		antallResepter++;

	}

	public Resept finnResept(int reseptNr){
			if(forste == null){
				System.out.println("Det finnes ingen elementer i lista");
				return null;
			}


			ReseptIterator resept2 = iterator();

			Resept tmp = null;
			int antallRetNoder = 0;

			while(resept2.hasNext()){
				if(reseptNr == antallRetNoder){
					return tmp;
				}
				tmp = resept2.next();
				antallRetNoder++;
			}

			return null;
	}

	protected class Node{
		Node neste;
		Resept data;

		public Node(Resept r){
			this.data = r;
		}
	}
			
	public void settInnForste(Node nyNode) {	
		forste = nyNode;
		bakerste = nyNode;
		antallResepter++;
		System.out.println("forste er overskrevet");
	}

	public ReseptIterator iterator(){
		return new ReseptIterator();
	}

	protected class ReseptIterator implements Iterator<Resept>{				//E er parameteren, Object er det den har lagret et annet sted i minnet.
														
		private int antRetNoder = 0;					
		private  Node pekerTilNeste = forste;								//E er byttet ut i forelesninga med Node
		
		public boolean hasNext(){
			return antRetNoder < antallResepter;
		}

		public Resept next(){								
				Resept returnerDenne = pekerTilNeste.data;
				antRetNoder++;
				pekerTilNeste = pekerTilNeste.neste;
				return returnerDenne;			
		}
		
		public void remove(){								
			throw new UnsupportedOperationException();
		}
	}
}

