public class Oblig4{
  int[] x, y;
  int MAX_X, MAX_Y;
  int n;

  public static void main(String[] args){
    int n = 20;
    Oblig4 o = new Oblig4(n);
  }

  public Oblig4(int n){
    NPunkter17 p = new NPunkter17(n);

    //initialize data
    x = new int[n];
    y = new int[n];
    p.fyllArrayer(x, y);

    IntList kohyll = new IntList();
    IntList idxs = p.lagIntList();

    //find outer corners
	MAX_X = p.maxXY;
	MAX_Y = p.maxXY;
	this.n = n;

    //start timetaking
   /* SekvensiellKohylle s = new SekvensiellKohylle(x, y);
    s.sekvMetode(idxs, kohyll);
    for(int i = 0; i < kohyll.size(); i++){
      System.out.println(kohyll.get(i));
    }
    TegnUt tu = new TegnUt(this , kohyll, "");*/
	
	ParallellKohylle pk = new ParallellKohylle(x, y, idxs, kohyll);
	System.out.println("end.");
  }

}
