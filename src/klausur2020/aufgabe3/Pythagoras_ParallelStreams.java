package klausur2020.aufgabe3;

import java.util.stream.IntStream;

public class Pythagoras_ParallelStreams
{

  public static void main(String[] args)
  {
    IntStream.rangeClosed(1,1000).parallel().forEach( a -> {
      IntStream.rangeClosed(a, 1000).forEach( b -> {
        int c2 = a*a + b*b;

        if( isSquare(c2) )
        {
          System.out.println("Lösung gefunden: a = " + a + " b = " + b + " c = " + (int)Math.sqrt(c2));
        }
      } );
    } );
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
