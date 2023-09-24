package part1.ch07_streams_collection;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Example_04
{

   public static void main(String[] args)
   {
      List<Integer> distinctRandomList =
            IntStream.range(0, 100)
                     .parallel()
                     .map( i -> ThreadLocalRandom.current().nextInt(1000))
                     .mapToObj( i -> Integer.valueOf(i) )
                     .distinct()
                     .collect( Collectors.toList() );
        
    System.out.println("Size " + distinctRandomList.size() );
   }

}
