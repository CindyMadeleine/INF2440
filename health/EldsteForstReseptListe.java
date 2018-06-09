class EldsteForstReseptListe extends EnkelReseptliste{	

	public void settInnResept(Resept ny){
		Node nyNode = new Node(ny);
		Node tmp = forste;
		
		if(forste == null){
			settInnForste(nyNode);
			//System.out.println("Resept satt inn i tom liste");
		} else {
			//ReseptIterator resIterator = iterator(); 
			
			System.out.println("Lista er ikke tom");
			
			while(tmp != null) {
				System.out.println("tmp ikke null");
				if(tmp.neste == null) {
					tmp.neste = nyNode;
					bakerste = nyNode;
					System.out.println("Kom til slutten av listen, satte inn bakerst");
				} else {
					tmp = tmp.neste;
					break;
				}
			}
		}

		antallResepter++;
		System.out.println("Resept satt inn");
	}
}
