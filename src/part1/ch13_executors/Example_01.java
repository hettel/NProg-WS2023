package part1.ch13_executors;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Example_01
{
   static  class Task implements Runnable
   {
      private final int start;
      private final int end;
      
      public Task(int start, int end)  {
        this.start = start;
        this.end = end;
      }

      @Override
      public void run() {
        long count = 
        IntStream.range(this.start, this.end)
                 .mapToObj( BigInteger::valueOf )
                 .filter( bInt -> bInt.isProbablePrime(1000) )
                 .count();
        
        System.out.printf("From %d to %d : %d -> %s \n", start, end,count, Thread.currentThread().getName()  );
      }
   }
   
   public static void main(String[] args)
   {
	  System.out.println("Start");
	   
      ExecutorService executor = Executors.newCachedThreadPool();
      
      // Partition the work
      Task task1 = new Task(1_000_000, 1_250_000);
      Task task2 = new Task(1_250_000, 1_500_000);
      Task task3 = new Task(1_500_000, 1_750_000);
      Task task4 = new Task(1_750_000, 2_000_000);
      
      executor.execute(task1);
      executor.execute(task2);
      executor.execute(task3);
      executor.execute(task4);
      
      // Running tasks are not aborted
      //executor.shutdown();
      System.out.println("main done");
   }
}
