package part2.o6_completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class Demo02
{

  public static void main(String[] args)
  {
    CompletableFuture.supplyAsync( 
        () -> ThreadLocalRandom.current().nextInt(5) )
    .thenApplyAsync( i -> 100/i )
    .handle( (t, ex) -> {
      if (ex != null) {
        //ex.printStackTrace();
        return -1;
      } else {
        return t;
      }
    } )
    .thenApplyAsync( i -> i + 2)
    .thenAcceptAsync( System.out::println )
    .join();

  }

}
