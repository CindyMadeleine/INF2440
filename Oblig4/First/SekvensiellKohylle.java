public class SekvensiellKohylle {
  int[] x, y;

  public SekvensiellKohylle(int[] x, int[] y){
    this.x = x;
    this.y = y;
  }

  public void sekvMetode(IntList m, IntList kohyll){
    //find minX and maxX
    int minX = x[0];
    int maxX = x[0];
    int minIndeks = 0, maxIndeks = 0;

    for(int i = 0; i < x.length; i++){

      if(minX > x[i]){
        minX = x[i];
        minIndeks = i;
      }
      if(maxX < x[i]){
        maxX = x[i];
        maxIndeks = i;
      }
    }

	System.out.println(minIndeks + "" + maxIndeks);
    //get all indexs
    IntList xcoords = drawLineBetweenPoints(maxIndeks, minIndeks, m);

    int xcoord = findPointWithMostNegativeDistance(maxIndeks, minIndeks, xcoords);

	kohyll.add(maxIndeks);
    sekvRek(maxIndeks, minIndeks, xcoord, xcoords, kohyll);

    //reset xcoords
    xcoords.clear();

    xcoords = drawLineBetweenPoints(minIndeks, maxIndeks, m);

	
    xcoord = findPointWithMostNegativeDistance(minIndeks, maxIndeks, xcoords);

	
    kohyll.add(minIndeks);
	
    sekvRek(minIndeks, maxIndeks, xcoord, xcoords, kohyll);
  }

  private double getDistance(int x1, int y1, int x2, int y2, int x, int y){
    int a = y1 - y2;
    int b = x2 - x1;
    int c = y2 * x1 - y1 * x2;
    return a * x + b * y + c;
  }

  private IntList drawLineBetweenPoints(int p1, int p2, IntList m){
    IntList xcoords = new IntList();
    for(int i = 0; i < m.size(); i++){
      double distance = getDistance(x[p1], y[p1], x[p2], y[p2], x[m.get(i)], y[m.get(i)]);
      if(distance < 0.0){
          xcoords.add(m.get(i));
      }
    }

    return xcoords;
  }

  public int findPointWithMostNegativeDistance(int p1, int p2, IntList m){
    double val = 0.0;
    int xcoord = -1;

    for (int i = 0; i < m.size(); i++) {
        double distance = getDistance(x[p1], y[p1], x[p2], y[p2], x[m.get(i)], y[m.get(i)]);
		
        if (distance == 0.0) {
            int x3 = x[m.get(i)];
            int y3 = y[m.get(i)];

            if((y3 == y[p1] && y3 == y[p2]) &&
            (x3 > x[p1] && x3 < x[p2] || x3 < x[p1] && x3 > x[p2])){
				System.out.println("p1:\t" + m.get(i) + "\tdistance" + distance);
                return m.get(i);
            } else if(( x3 == x[p1] && x3 == x[p2]) &&
             (y3 > y[p1] && y3 < y[p2] || y3 < y[p1] && y3 > y[p2])){
				 System.out.println("p2:\t" + m.get(i) + "\tdistance" + distance);
                return m.get(i);
            }
        } else if (distance < val ) {
            val = distance;
            xcoord = m.get(i);
        }
    }
	if(xcoord != -1){
		System.out.println("p3:\t" + xcoord + "\tdistance" + val);
	}
    return xcoord;
  }

  public void sekvRek (int p1, int p2, int p3, IntList m, IntList koHyll){
    int rightcoord = findPointWithMostNegativeDistance(p1, p3, m);
    if (rightcoord != -1) {
      IntList line = drawLineBetweenPoints(p1, p3, m);
      sekvRek(p1, p3, rightcoord, line, koHyll);
    }

    koHyll.add(p3);

  int leftcoords = findPointWithMostNegativeDistance(p3, p2, m);
    if (leftcoords != -1) {
        IntList line = drawLineBetweenPoints(p3, p2, m);
        sekvRek(p3, p2, leftcoords, line, koHyll);
    }
  }
}
