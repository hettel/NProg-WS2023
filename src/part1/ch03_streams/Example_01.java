package part1.ch03_streams;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class Example_01
{
   public static void main(String[] args)
   {
      long count = IntStream.range(1, 1_000)
                            .mapToObj( BigInteger::valueOf ) 
                            .filter(bInt -> bInt.isProbablePrime(1000) )
                            .count();
                     
      System.out.println("Count : " + count );
   }
}
