package part2.o6_completableFuture;

import java.util.concurrent.CompletableFuture;

public class Demo05
{

  public static void main(String[] args)
  {
    CompletableFuture<Integer> cfRoot = CompletableFuture.supplyAsync(() -> 42);

    // Splitting
    CompletableFuture<Integer> left = cfRoot.thenApplyAsync(i -> i / 2);
    CompletableFuture<Integer> right = cfRoot.thenApplyAsync(i -> i - 2);

    // OR operation
    left.applyToEitherAsync(right, i -> 2*i).thenAcceptAsync(System.out::println).join();

  }

}
