public class FindMax implements Runnable{
	int startpos,endpos;
	int[] arr;
	int numBit = 2;
	MultiSort msort;

	public FindMax(MultiSort msort, int startpos, int endpos){
		this.msort = msort;
		this.arr = msort.arr;
		this.startpos = startpos;
		this.endpos = endpos;
	}

	public int getMaxValue() {
		int max = arr[startpos];

		int i = startpos;
		if(i == 0)
			i++;

		for (; i < endpos ; i++)
			if (arr[i] > max) max = arr[i];
			while (max >= (1L<<numBit) )numBit++;

		return max;
	}

	@Override
	public void run() {
		 int max = getMaxValue();
		 msort.updateMaxValue(max, numBit);
	}
}
