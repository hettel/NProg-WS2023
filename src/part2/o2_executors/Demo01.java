package part2.o2_executors;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo01
{
  static private class Task implements Runnable
  {
    private final int start;
    private final int end;
    
    public Task(int start, int end)
    {
      this.start = start;
      this.end = end;
    }

    @Override
    public void run()
    {
      int count = 0;
         
      long startTime = System.currentTimeMillis(); 
      for( int candidate = this.start; candidate < this.end; candidate++ )
      {
        BigInteger bInt = BigInteger.valueOf(candidate);
        if( bInt.isProbablePrime(1000) )
        {
          count++;
        }
      }
      long endTime = System.currentTimeMillis(); 
      
      System.out.println("Number of prims " + count + ", Task Runtime: " + (endTime - startTime) + "[ms]");
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
    
    executor.execute(task1);
    executor.execute(task2);
    executor.execute(task3);
    executor.execute(task4);
    
    // Running tasks are not aborted
    executor.shutdown();
    System.out.println("main done");
  }

}
