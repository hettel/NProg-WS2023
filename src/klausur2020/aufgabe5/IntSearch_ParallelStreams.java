package klausur2020.aufgabe5;

import java.util.Arrays;
import java.util.Random;

public class IntSearch_ParallelStreams
{

  public static void main(String[] args)
  {
    int[] array = getArray(1024*1024);

    long anzahl = Arrays.stream(array).parallel().filter( a -> a == 42 ).count();
    System.out.println("Anzahl : " + anzahl );
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
