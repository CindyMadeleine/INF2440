import java.util.concurrent.CyclicBarrier;

public class parallellsokThread implements Runnable{
	CyclicBarrier barrier;
	int startpos, endepos, antStorsteElementer, ind;
	int[] arr;

	public parallellsokThread(CyclicBarrier barrier, int startpos, int endepos, int antStorsteElementer, int[] arr, int ind){
		this.barrier = barrier;
		this.startpos = startpos;
		this.endepos = endepos;
		this.antStorsteElementer = antStorsteElementer;
		this.arr = arr;
		this.ind = ind;
	}

	public void run(){
		if(endepos > arr.length){
			endepos = arr.length;
		}

		int k = startpos + antStorsteElementer;
		if(k > arr.length){
			k = arr.length - 1;
		}

		CreativeArray ca = new CreativeArray();


		innstikkSorteringTilElementK(k);
		if(!ca.isLeftBigger(arr, startpos, k)){
				System.out.println("Thread" + ind+": Left is not bigger");
		}

		getStorsteKelementer(k);
		if(!ca.isKElementIsBiggest(arr, k, endepos)){
			System.out.println("Thread" + ind+": K element is not biggest");
		}

		try{
			barrier.await();
		} catch(Exception e){
			//Do nothing?
		}
	}

	public void innstikkSorteringTilElementK(int k){
		int j;
		for(int pos = startpos; pos < k && pos < endepos; pos++){
			int tmp = arr[pos];
			for(j = pos; j > startpos && tmp > arr[j-1]; j--){
				arr[j] = arr[j - 1];
			}
			arr[j] = tmp;
		}

	}

	public void getStorsteKelementer(int k){
		for(int i = k; i < endepos; i++){
			if(arr[i] > arr[k-1]){
				int piv = arr[i];
				arr[i] = arr[k-1];
				arr[k-1] = piv;


				for(int j = k - 1; j > startpos && arr[j-1] < arr[j]; j--){
					int tmp = arr[j - 1];
					arr[j - 1] = arr[j];
					arr[j] = tmp;
				}
			}
		}
	}

}
