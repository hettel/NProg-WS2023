package part1.ch03_streams;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class Example_03
{
   public static void main(String[] args)
   {
      long count = IntStream.range(0, 1_000)
                            .parallel()
                            .filter(i -> i%2 != 2 )
                            .sequential()
                            .filter(i -> BigInteger.valueOf(i).isProbablePrime(1000) )
                            .parallel()
                            .count();
      
      System.out.println("Count : " + count );
   }
}
