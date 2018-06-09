import java.util.concurrent.CyclicBarrier;

public class parallellsok{
	static CyclicBarrier barrier;
	static int threadK;

	public static void main(String[] args){
		if(args.length < 2){
			System.out.println("Ulovlig antall av parametre. Vennligst angi to hele sifre");
			return;
		}

		int nrAnswers, nrGreatest;
		try{
			nrAnswers = Integer.parseInt(args[0]); //n
			nrGreatest = Integer.parseInt(args[1]); //k
		}catch(NumberFormatException nfe){
			System.out.println("Parametrene er ikke et heltall");
			return;
		}

		if(nrAnswers < nrGreatest){
			System.out.println("Error! Antall svar er mindre enn antall storste nr.\nHusk at første siffer er antall svar og andre siffer er storste nummer");
			return;
		} else if(nrAnswers == 0){
			System.out.println("Error! Du har ikke fått noen søkematch");
			return;
		}

		CreativeArray ca = new CreativeArray();
		int[] arr = ca.makeArr(nrAnswers);

		long starttime = System.nanoTime();
		int[] positions = doPartsolution(nrAnswers, nrGreatest, arr);

		if(positions != null){
			sortBiggestK(nrGreatest, positions, arr);
		}

		long finishtime = System.nanoTime();
		double totTime = (finishtime - starttime) / 1000000.0;

		if(!ca.isLeftBigger(arr, 0, nrGreatest)){
			System.out.println("Brukte " + totTime + " og sorterte ikke riktig");
			//ca.printArr(arr, 0, arr.length);
			return;
		}

		if(!ca.isKElementIsBiggest(arr, nrGreatest, arr.length)){
			System.out.println("Brukte " + totTime + " og fant ikke K storste nr");
			//ca.printArr(arr, 0, arr.length);
			return;
		}

		System.out.println("Total tid brukt paa aa fullfore:\t" + totTime);
		//ca.printArr(arr, 0, arr.length);
	}

	private static int[] doPartsolution(int nrAnswers, int nrGreatest, int[] arr){ //Når du kommer ut av denne delen skal alt være sortert
		if(nrGreatest == 0){
			return null;
		}

		int nrCores = Runtime.getRuntime().availableProcessors();
		barrier = new CyclicBarrier(nrCores+1);
		int[] positions = new int[nrCores];
		int startpos = 0; //place to start looking after answers(n)
		int intervall = nrAnswers/nrCores; //place to end looking after answers(n)
		int rest = nrAnswers % nrCores; //kan alltid ta modulo ,da dette aldri er under
		if(intervall < nrGreatest){//Ville her gaat over til aa kjøre den sekvensielt.
			System.out.println("Arrayet til traader er mindre enn antall traader. ");
			System.exit(0);
		}

		for(int i = 0, endpos = intervall; i < nrCores; i++, endpos+=intervall){
			if(rest != 0){ //for aa fordele det som ikke gaar opp jevnt
				endpos++;
				rest--;
			}

			positions[i] = startpos;

			parallellsokThread pt = new parallellsokThread(barrier, startpos, endpos, nrGreatest, arr, i);
			Thread th1 = new Thread(pt);
			th1.start();

			startpos = endpos;
		}

		try{
			barrier.await();
		} catch(Exception e){
			//do something?
		}

		return positions;
	}

	public static void sortBiggestK(int nrGreatest, int[] positions, int[] arr){
		int startpos2 = 0;
		for(int i = 1; i < positions.length; i++){
			int startpos = positions[i];
			sortBiggestK(nrGreatest, startpos, arr);
		}

	}

	private static void sortBiggestK(int nrGreatest, int startpos, int[] arr){
		for(int j = nrGreatest-1; j > -1; j--){
			int kpos = nrGreatest-1;
			if(arr[kpos] < arr[startpos+j]){
				//swapping arr[K-1] with arr[startpos+j]
				int piv = arr[kpos];
				arr[kpos] = arr[startpos+j];
				arr[startpos+j] = piv;


				for(; kpos > 0 && arr[kpos-1] < arr[kpos]; kpos--){
					int tmp = arr[kpos];
					arr[kpos] = arr[kpos-1];
					arr[kpos-1] = tmp;
				}
			}

		}
	}
}
