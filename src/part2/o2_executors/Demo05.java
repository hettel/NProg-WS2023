package part2.o2_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Demo05
{

  public static void main(String[] args) throws Exception
  {
    ExecutorService executor = Executors.newCachedThreadPool();
    
    for(int i=0; i < 10; i++)
    {
      executor.execute( Demo05::doSomething );
    }

    executor.shutdown();
    // waits until all tasks are finished
    executor.awaitTermination( Long.MAX_VALUE, TimeUnit.DAYS);
    System.out.println("main done");
  }
  
  private static void doSomething()
  {
    try
    {
      TimeUnit.MILLISECONDS.sleep( 1_000 + ThreadLocalRandom.current().nextInt(2_000));
      System.out.println("done " + Thread.currentThread().getName());
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }

}
