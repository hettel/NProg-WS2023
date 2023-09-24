package part2.o5_concurrencyTools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo02_CountDownLatch
{
  static class Task implements Runnable
  {
    private final CountDownLatch latch;
    
    public Task(CountDownLatch latch)
    {
      this.latch = latch;
    }
   
    @Override
    public void run()
    {
      try
      {
        this.latch.await();
        System.out.println( Thread.currentThread() + " is running");
        // ...
        // do something
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      } 
    }
  }
  
  public static void main(String[] args) throws InterruptedException
  {
    CountDownLatch latch = new CountDownLatch(11);
    
    ExecutorService executor = Executors.newFixedThreadPool(4);
    executor.execute( new Task(latch) );
    executor.execute( new Task(latch) );
    executor.execute( new Task(latch) );
    executor.execute( new Task(latch) );
    
    // Main thread gives the start signal doing a count down
    System.out.println("Count down");
    for(int i=10; i>=0; i--)
    {
      System.out.println( i>0 ? i : "go");
      latch.countDown();
      TimeUnit.SECONDS.sleep(1);
    }
    System.out.println("done");
    executor.shutdown();
  }
}
