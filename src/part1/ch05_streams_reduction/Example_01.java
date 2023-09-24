package part1.ch05_streams_reduction;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class Example_01
{
   public static void main(String[] args)
   {
      // Build-in reduction
      long count1 = IntStream.range(0, 1_000)
                             .mapToObj( BigInteger::valueOf )
                             .filter( bigInt -> bigInt.isProbablePrime(1000) )
                             .count();

      // User-defined reduction
      long count2 = IntStream.range(0, 1_000)
                             .mapToObj( BigInteger::valueOf )
                             .filter( bigInt -> bigInt.isProbablePrime(1000) )
                             .mapToInt( BigInteger::intValue )
                             .reduce(0, (a, b) -> a + 1);
      
      // User-defined reduction
      BigInteger count3 = IntStream.range(0, 1_000)
                             .mapToObj( BigInteger::valueOf )
                             .filter( bigInt -> bigInt.isProbablePrime(1000) )
                             .reduce( BigInteger.ZERO, (a, b) -> a.add( BigInteger.ONE) );
      
      // User-defined reduction
      long count4 = IntStream.range(0, 1_000)
                             .mapToObj( BigInteger::valueOf )
                             .filter( bigInt -> bigInt.isProbablePrime(1000) )
                             .reduce(Integer.valueOf(0), (a, b) -> a.intValue() + 1, (i1,i2) -> i1 + i2 );

      System.out.println("Sum1 : " + count1 );
      System.out.println("Sum2 : " + count2 );
      System.out.println("Sum3 : " + count3 );
      System.out.println("Sum4 : " + count4 );
   }
}
