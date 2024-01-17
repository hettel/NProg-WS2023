package klausur2020.aufgabe3;

public class Pythagoras
{

  public static void main(String[] args)
  {
    for( int a = 1; a <= 1000; a++ )
    {
      for( int b = a; b <= 1000; b++ )
      {
        int c2 = a*a + b*b;

        if( isSquare(c2) )
        {
          System.out.println("L�sung gefunden: a = " + a + " b = " + b + " c = " + (int)Math.sqrt(c2));
        }
      }
    }
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
