package part2.o6_completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class Demo06
{

  public static void main(String[] args)
  {
    System.out.println("Version 1");
    CompletableFuture
         .supplyAsync( () -> ThreadLocalRandom.current().nextInt(100))
         .thenApply( i -> i/(i%3) )
         .thenApply( i -> 3*i )
         //.exceptionally( th -> { th.printStackTrace(); return 0; } )
         .thenAccept( System.out::println );
    
    System.out.println("Version 2");
    CompletableFuture<Integer> result = CompletableFuture
        .supplyAsync( () -> ThreadLocalRandom.current().nextInt(100))
        .thenApply( i -> i/(i%3) )
        .thenApply( i -> 3*i );
    
    System.out.println("Result " + result.join() );

  }

}
