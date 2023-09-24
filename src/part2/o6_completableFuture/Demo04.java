package part2.o6_completableFuture;

import java.util.concurrent.CompletableFuture;

public class Demo04
{

  public static void main(String[] args)
  {
    CompletableFuture<Integer> cfRoot = CompletableFuture.supplyAsync(() -> 42);

    // Splitting
    CompletableFuture<Integer> left = cfRoot.thenApplyAsync(i -> i / 2);
    CompletableFuture<Integer> right = cfRoot.thenApplyAsync(i -> i - 2);

    // AND operation
    left.thenCombineAsync(right, (l, r) -> r + l).thenAccept(System.out::println);

  }

}
