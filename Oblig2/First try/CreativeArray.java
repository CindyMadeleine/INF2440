import java.util.Random;

public class CreativeArray{
	public int[] makeArr(int size){
		Random rm = new Random();

		int[] arr = new int[size];

		for(int i = 0; i < size; i++){
			arr[i] = rm.nextInt();
		}

		return arr;
	}

	public int[] makeTestArr(int size){
		int[] arr = new int[size];

		for(int i = 0; i < size; i++){
			arr[i] = i;
		}

		return arr;
	}

	public void printArr(int[] arr, int startpos, int length){
		if(startpos < 0){
			startpos = 0;
		}

		System.out.print("[ ");
		for(int i = startpos; i < length && i < arr.length; i++){
			System.out.print(arr[i] + " ");
		}

		System.out.println("]");
	}


	public boolean isLeftBigger(int[] arr, int startpos, int k){
		for(int i = startpos; i < k - 1; i++){
			if(arr[i] < arr[i+1]){
				return false;
			}
		}

		return true;
	}

	public boolean isKElementIsBiggest(int[] arr, int k, int endpos){
		if(k == 0){
			return true;
		}

		for(int i = k; i < endpos && i < arr.length; i++){
			if(arr[k-1] < arr[i]){
				return false;
			}
		}

		return true;
	}
}
