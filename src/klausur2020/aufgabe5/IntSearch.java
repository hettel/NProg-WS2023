package klausur2020.aufgabe5;

import java.util.Random;

public class IntSearch
{

  public static void main(String[] args)
  {
    int[] array = getArray(1024*1024);


    int num = count( 42, array, 0, array.length );
    System.out.println("Anzahl : " + num );
  }


  public static int count(int search, int[] array, int start, int end)
  {
    int count = 0;

    if( (end - start) < 64 )
    {
      for( int i = start; i < end; i++ )
      {
        if( array[i] == search )
          count++;
      }

      return count;
    }

    int mid = (end - start)/2;
    int countLeft = count( search, array, start, start + mid);
    int countRight = count( search, array, start + mid, end );

    return countLeft + countRight;
  }


  public static int[] getArray(int len)
  {
    int[] array = new int[len];

    Random rand = new Random();
    for(int i = 0; i  < len; i++)
    {
      array[i] = rand.nextInt(len/4);
    }

    return array;
  }
}
