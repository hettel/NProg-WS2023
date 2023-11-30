package playground.klausurWS23.aufgabe5;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Summe
{
  public static void main(String[] args)
  {
    int[] array = getArray(1_000_000);

    int sum = summe(array, 0, array.length );
    System.out.println("Summe : " + sum );
  }


  public static int summe(int[] array, int start, int end)
  {
    if( (end - start) < 64 )
    {
      int sum = 0;
      
      for( int i = start; i < end; i++ )
      {
        sum += array[i];
      }

      return sum;
    }

    int mid = (end - start)/2;
    int sumLeft = summe( array, start, start + mid);
    int sumRight = summe( array, start + mid, end );

    return (sumLeft + sumRight);
  }


  private static int[] getArray(int len)
  {
    int[] array = new int[len];
    Arrays.setAll(array, i -> ThreadLocalRandom.current().nextInt(len/4) );
    return array;
  }

}
