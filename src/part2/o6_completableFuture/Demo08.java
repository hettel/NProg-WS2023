package part2.o6_completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class Demo08
{

  public static void main(String[] args)
  {
    CompletableFuture
    .supplyAsync( () -> ThreadLocalRandom.current().nextInt(100))
    .thenApply( i -> {System.out.println("A:" + i); return i;} )
    .thenApply( i -> i/(i%3) )
    .thenApply( i -> 3*i )
    .thenApply( i -> {System.out.println("B:" + i); return i;} )
    .exceptionally( th -> { th.printStackTrace(); return 1;} )
    .thenApply( i -> {System.out.println("C:" + i); return i;} )
    .thenApply( i -> ThreadLocalRandom.current().nextInt(100) )
    .thenApply( i -> i/(i%3) )
    .thenApply( i -> 3*i )
    .thenApply( i -> {System.out.println("D:" + i); return i;} )
    .exceptionally( th -> { th.printStackTrace(); return 1;} )
    .thenApply( i -> {System.out.println("E:" + i); return i;} )
    .thenAccept( System.out::println );
  }

}
