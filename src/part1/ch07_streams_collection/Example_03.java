package part1.ch07_streams_collection;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Example_03
{

   public static void main(String[] args)
   {
      double[][] matrix = getRandomMatrix(5, 5);
      
      System.out.println( Arrays.deepToString(matrix));
   }

   public static double[][] getRandomMatrix(final int row, final int col)
   {     
      return IntStream.range(0, row)
                      .mapToObj( i -> ThreadLocalRandom.current().doubles(col).toArray() )
                      .toArray( double[][]::new );
   }
}
