package part1.ch07_streams_collection;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Example_01
{

   public static void main(String[] args)
   {
      List<Integer> primes = IntStream.range(0, 1_000)
            .filter(i -> BigInteger.valueOf(i).isProbablePrime(1000) )
            .mapToObj( i -> Integer.valueOf(i) )
          //.toList();
            .collect( Collectors.toList() );
      
      System.out.println("Count " + primes.size() );

   }

}
