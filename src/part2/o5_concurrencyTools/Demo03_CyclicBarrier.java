package part2.o5_concurrencyTools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo03_CyclicBarrier
{
  static class Task implements Runnable
  {
    private final CyclicBarrier barrier;
    
    public Task(CyclicBarrier barrier)
    {
      this.barrier = barrier;
    }
   
    @Override
    public void run()
    {
      try
      {
        for(int i=0; i < 10; i++)
        {
          this.barrier.await();
          System.out.println( Thread.currentThread() + " is running");
          // do something in "lock step mode"
          // ...
        }
      }
      catch (InterruptedException | BrokenBarrierException e)
      {
        e.printStackTrace();
      } 
    }
  }
  
  public static void main(String[] args) throws InterruptedException
  {
    CyclicBarrier barrier = new CyclicBarrier(4, new Runnable()
    {
      @Override
      public void run()
      {
        sleep(1000, TimeUnit.MILLISECONDS);
        System.out.println("Next round (by " + Thread.currentThread().getName() + ")" );
      }
    });
    
    ExecutorService executor = Executors.newFixedThreadPool(4);
    executor.execute( new Task(barrier) );
    executor.execute( new Task(barrier) );
    executor.execute( new Task(barrier) );
    executor.execute( new Task(barrier) );
 
    System.out.println("done");
    executor.shutdown();
  }
  
  private static void sleep(int time, TimeUnit unit) 
  {
    try
    {
      unit.sleep(time);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
