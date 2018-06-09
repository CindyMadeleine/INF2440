class YngsteForstReseptListe extends EnkelReseptliste{

	public void settInnResept(Resept ny){
		Node nyNode = new Node(ny);

		if(forste == null){
			 settInnForste(nyNode);
			 //System.out.println("Resept satt inn i tom liste");
		} else {
			Node tmp = forste;
			forste = nyNode;
			forste.neste = tmp;
			//System.out.println("Resept satt inn i listen");
		}
	}
}