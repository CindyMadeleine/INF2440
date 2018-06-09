import java.util.Arrays;

public class sekvensiellsok{
	public static void main(String[] args){
		if(args.length < 2){
			System.out.println("Ulovlig antall av parametre. Vennligst angi to hele sifre");
			return;
		}

		int nrAnswers, nrGreatest;
		try{
			 nrAnswers = Integer.parseInt(args[0]); //n
			 nrGreatest = Integer.parseInt(args[1]); //k
		} catch(NumberFormatException nfe){
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

		int[] relevansscore = new int[nrAnswers];
		CreativeArray ca = new CreativeArray();
		int arr[] = ca.makeArr(nrAnswers);
		long starttime = System.nanoTime();

		insertionSortToElementK(arr, nrGreatest, 1);
		getbiggestKelements(arr, nrGreatest);

		long finishtime = System.nanoTime();
		double totTime = (finishtime - starttime) / 1000000.0;

		if(!ca.isLeftBigger(arr, 0, nrGreatest)){
			System.out.println("Brukte " + totTime + " og sorterte ikke riktig");
			//ca.printArr(arr, 0, arr.length);
			return;
		}

		if(!ca.isKElementIsBiggest(arr, nrGreatest, arr.length)){
			System.out.println("Brukte " + totTime + " og fant ikke de k storste elementene");
			//ca.printArr(arr, 0, nrGreatest);
			return;
		}

		System.out.println("Tid paa aa fullfore:\t" + totTime);
	}

	/**
	* Sorterer slik at elementene i arrayen er sortert fra storst til minst, slik at storste kommer forst.
	**/
	public static void insertionSortToElementK(int[] arr, int k, int n){
		int j;
		for(int pos = n; pos < k; pos++){
			int tmp = arr[pos];
			for(j = pos; j > 0 && tmp > arr[j-1]; j--){
				arr[j] = arr[j - 1];
			}
			arr[j] = tmp;
		}
	}

	/**
	*
	**/

	public static void getbiggestKelements(int[] arr, int k){
		for(int i = k; i < arr.length; i++){
			if(arr[i] > arr[k-1]){
				int piv = arr[i];
				arr[i] = arr[k-1];
				arr[k-1] = piv;



				for(int j = k - 1; j > 0 && arr[j-1] < arr[j]; j--){
					int tmp = arr[j - 1];
					arr[j - 1] = arr[j];
					arr[j] = tmp;
				}
			}
		}
	}
}
