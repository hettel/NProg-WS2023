package part2.o2_executors;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo02
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
    
    // Partition the work
    Task task1 = new Task(1_000_000, 1_250_000);
    Task task2 = new Task(1_250_000, 1_500_000);
    Task task3 = new Task(1_500_000, 1_750_000);
    Task task4 = new Task(1_750_000, 2_000_000);
    
    // Start threads
    Future<Integer> futureOfTask1 = executor.submit(task1);
    Future<Integer> futureOfTask2 = executor.submit(task2);
    Future<Integer> futureOfTask3 = executor.submit(task3);
    Future<Integer> futureOfTask4 = executor.submit(task4);
    
    // wait until the results are available
    int count = futureOfTask1.get() + futureOfTask2.get() 
              + futureOfTask3.get() + futureOfTask4.get();
    System.out.println("Count " + count );
    
    executor.shutdown();
    System.out.println("main done");
  }
}
