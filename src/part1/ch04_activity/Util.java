package part1.ch04_activity;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Util
{
   public static double[][] getRandomMatrix(final int row, final int col)
   {     
      return IntStream.range(0, row)
                      .parallel()
                      .mapToObj( i -> ThreadLocalRandom.current().doubles(col).toArray() )
                      .toArray( double[][]::new );
   }
}
