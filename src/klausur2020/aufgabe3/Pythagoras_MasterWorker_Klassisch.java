package klausur2020.aufgabe3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Pythagoras_MasterWorker_Klassisch
{
  record AB (int a, int b) { };

  static class Task implements Callable< List<AB> >
  {
    private final int start;
    private final int end;

    public Task(int start, int end)
    {
      this.start = start;
      this.end = end;
    }

    @Override
    public List<AB> call() throws Exception
    {
      List<AB> result = new ArrayList<>();

      for( int a = start; a <= end; a++ )
      {
        for( int b = a; b <= 1000; b++ )
        {
          int c2 = a*a + b*b;

          if( isSquare(c2) )
          {
            result.add( new AB(a,b) );
          }
        }
      }

      return result;
    }
  }


  public static void main(String[] args) throws Exception
  {
    ExecutorService executor = Executors.newFixedThreadPool(4);

    Task task1 = new Task(1,250);
    Task task2 = new Task(251,500);
    Task task3 = new Task(501,750);
    Task task4 = new Task(751,1000);

    List< Future<List<AB>> > futureList = new ArrayList<>();
    futureList.add( executor.submit(task1) );
    futureList.add( executor.submit(task2) );
    futureList.add( executor.submit(task3) );
    futureList.add( executor.submit(task4) );


    for( Future<List<AB>> futures : futureList )
    {
      for( AB ab : futures.get() )
      {
          int c2 = ab.a()*ab.a() + ab.b()*ab.b();
          System.out.println("Lösung gefunden: a = " + ab.a() + " b = " + ab.b() + " c = " + (int)Math.sqrt(c2));
      }
    }

    executor.shutdown();
  }

  // Prüfalgorithmus stammt aus:
  // http://www.johndcook.com/blog/2008/11/17/fast-way-to-test-whether-a-number-is-a-square/
  private static boolean isSquare(int n)
  {
    int h = n & 0xF; // last hexidecimal "digit"
    if (h > 9)
        return false; // return immediately in 6 cases out of 16.

    // Take advantage of Boolean short-circuit evaluation
    if ( h != 2 && h != 3 && h != 5 && h != 6 && h != 7 && h != 8 )
    {
        // take square root if you must
        int t = (int) Math.floor( Math.sqrt((double) n) + 0.5 );
            return t*t == n;
    }
    return false;
  }
}
