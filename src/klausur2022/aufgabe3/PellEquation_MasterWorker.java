package klausur2022.aufgabe3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PellEquation_MasterWorker
{
  static class Worker implements Callable< List<int[]>>
  {
    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;

    public Worker(int xMin, int xMax, int yMin, int yMax)
    {
      this.xMin = xMin;
      this.xMax = xMax;
      this.yMin = yMin;
      this.yMax = yMax;
    }

    @Override
    public List<int[]> call() throws Exception
    {
      List<int[]> loesungen = new ArrayList<int[]>();

      for(int x= xMin; x < xMax; x++ )
      {
        for(int y= yMin; y < yMax; y++ )
        {
          if( gleichung(x, y) == 1 )
          {
            loesungen.add( new int[]{x,y});
          }
        }
      }

      return loesungen;
    }
  }


  public static void main(String[] args) throws Exception
  {
    List<int[]> loesungen = new ArrayList<int[]>();

    ExecutorService executor = Executors.newFixedThreadPool(4);

    Worker w1 = new Worker(0,10000, 0, 10000);
    Worker w2 = new Worker(-10000,0, 0, 10000);
    Worker w3 = new Worker(-10000,0, -10000, 0);
    Worker w4 = new Worker(0,10000, -10000, 0);

    Future<List<int[]>> f1 = executor.submit( w1 );
    Future<List<int[]>> f2 = executor.submit( w2 );
    Future<List<int[]>> f3 = executor.submit( w3 );
    Future<List<int[]>> f4 = executor.submit( w4 );

    loesungen.addAll( f1.get() );
    loesungen.addAll( f2.get() );
    loesungen.addAll( f3.get() );
    loesungen.addAll( f4.get() );


    for( int[] p : loesungen )
    {
      System.out.println(" x = " + p[0] + ", y = " + p[1]);
    }
    
    System.out.println("Anzahl: " + loesungen.size() );

    executor.shutdown();
  }
  
  public static int gleichung(int x, int y)
  {
    return x*x - 2*y*y;
  }

}
