package part1.ch13_executors;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Example_03
{
   static class Task implements Callable<Long>
   {
      private final int start;
      private final int end;
      
      public Task(int start, int end)  {
        this.start = start;
        this.end = end;
      }

      @Override
      public Long call() {
        
        return IntStream.range(this.start, this.end)
              .mapToObj( BigInteger::valueOf )
              .filter( bInt -> bInt.isProbablePrime(1000) )
              .count();
      }
   }
   
   public static void main(String[] args) throws Exception
   {
     ExecutorService executor = Executors.newFixedThreadPool(4);
     
     int startValue = 1_000_000;
     int stepValue  = 250_000;
     
     List<Callable<Long>> tasks = new ArrayList<>();
     for(int i=0; i < 4; i++)
     {
       int endValue = startValue + stepValue;
       tasks.add( new Task(startValue, endValue));
       startValue = endValue;
     }
     
     List<Future<Long>> futures = executor.invokeAll(tasks);
     
     long sum = 0L;
     for( var future : futures )
     {
       sum += future.get();
     }
     
     System.out.println("Count: " + sum );
     
     executor.shutdown();
   }
}
