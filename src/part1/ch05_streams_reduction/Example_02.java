package part1.ch05_streams_reduction;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Example_02
{

   public static void main(String[] args)
   {
      // Create an int-array with random numbers
      int[] array = new int[1_000];
      Arrays.setAll(array, i-> ThreadLocalRandom.current().nextInt(0,10_000) );

      // Find the index of the greatest element
      int idxOfMaxValue =
      IntStream.range(0, array.length)
               .reduce( (i,j) -> array[i] > array[j] ? i : j )
               .getAsInt();
            
      System.out.println("Max " + array[idxOfMaxValue] + " at " + idxOfMaxValue);
   }

}
