package part2.o6_completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class Demo07
{

  public static void main(String[] args)
  {
    CompletableFuture
    .supplyAsync( () -> ThreadLocalRandom.current().nextInt(100))
    .thenApply( i -> i/(i%3) )
    .thenApply( i -> 3*i )
    .handleAsync( (i,th) -> { 
      if( th == null )
      {
        return i;
      }
      else
      {
        th.printStackTrace();
        return 0;
      }
    })
    .thenAccept( System.out::println );
  }

}
