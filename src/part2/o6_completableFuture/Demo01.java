package part2.o6_completableFuture;

import java.util.concurrent.CompletableFuture;

public class Demo01
{

  public static void main(String[] args)
  {
    CompletableFuture.supplyAsync( () -> 42 )
                     .thenApplyAsync( i -> i/2 )
                     .thenApplyAsync( i -> i + 2)
                     .thenAcceptAsync( System.out::println )
                     .join();

  }

}
