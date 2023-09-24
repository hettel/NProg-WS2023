package part2.o6_completableFuture;

import java.util.concurrent.CompletableFuture;

public class Demo03
{

  public static void main(String[] args)
  {
    CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 42);

    cf.thenApplyAsync(i -> i / 2).thenAcceptAsync(System.out::println);

    cf.thenApplyAsync(i -> i + 2).thenAcceptAsync(System.out::println);

  }

}
