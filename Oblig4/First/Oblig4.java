public class Oblig4{
  int[] x, y;
  int MAX_X, MAX_Y;
  int n;

  public static void main(String[] args){
    int n = 10000;
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
    SekvensiellKohylle s = new SekvensiellKohylle(x, y);
    double start = System.nanoTime();
    s.sekvMetode(idxs, kohyll);
    double totTime = System.nanoTime() - start / 1000000.0;
    System.out.println("Tid paa aa fullfore sekvensiell losning:\t" + totTime);
    /*for(int i = 0; i < kohyll.size(); i++){
      System.out.println(kohyll.get(i));
    }
    new TegnUt(this , kohyll, "");*/

    //init parallell solution
    kohyll = new IntList();
    idxs = p.lagIntList();

    //start parallell soloution
  	ParallellKohylle pk = new ParallellKohylle(x, y, idxs, kohyll);
    double start2 = System.nanoTime();
    pk.sekvMetode();
    double totTime2 = System.nanoTime() - start2 / 1000000.0;
    System.out.println("Tid paa aa fullfore parallell losning:\t" + totTime2);
    /*for(int i = 0; i < kohyll.size(); i++){
      System.out.println(kohyll.get(i));
    }
    if(kohyll.size() != 0){
      new TegnUt(this , kohyll, "");
    }*/
  }

}
