package part1.ch13_executors;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Example_02
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
	  System.out.println("Start"); 
	   
      ExecutorService executor = Executors.newFixedThreadPool(4);
      
      // Partition the work
      Task task1 = new Task(1_000_000, 1_250_000);
      Task task2 = new Task(1_250_000, 1_500_000);
      Task task3 = new Task(1_500_000, 1_750_000);
      Task task4 = new Task(1_750_000, 2_000_000);
      
      // Start threads
      Future<Long> futureOfTask1 = executor.submit(task1);
      Future<Long> futureOfTask2 = executor.submit(task2);
      Future<Long> futureOfTask3 = executor.submit(task3);
      Future<Long> futureOfTask4 = executor.submit(task4);
            
      // wait until the results are available
      long count = futureOfTask1.get() +  futureOfTask2.get() 
                  + futureOfTask3.get() +futureOfTask4.get();
      
      System.out.println( futureOfTask1.isDone() );
      
      executor.shutdown();
      System.out.println("main done");
   }

}
