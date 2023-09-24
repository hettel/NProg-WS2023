package part2.o2_executors;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo03
{
  static private class Task implements Callable<Integer>
  {
     private final int start;
     private final int end;
     
     public Task(int start, int end)  {
       this.start = start;
       this.end = end;
     }

     @Override
     public Integer call() {
       int count = 0;
       for( int candidate = this.start; candidate < this.end; candidate++ )
       {
         BigInteger bInt = BigInteger.valueOf(candidate);
         if( bInt.isProbablePrime(1000) )
         {
           count++;
         }
       }
       return count;
     }
  }
  
  public static void main(String[] args) throws Exception
  {
    ExecutorService executor = Executors.newFixedThreadPool(4);
    
    int startValue = 1_000_000;
    int stepValue  = 250_000;
    
    List<Callable<Integer>> tasks = new ArrayList<>();
    for(int i=0; i < 4; i++)
    {
      int endValue = startValue + stepValue;
      tasks.add( new Task(startValue, endValue));
      startValue = endValue;
    }
    
    List<Future<Integer>> futures = executor.invokeAll(tasks);
    
    int sum = 0;
    for( var future : futures )
    {
      sum += future.get();
    }
    
    System.out.println("Count: " + sum );
    
    executor.shutdown();
  }
}
