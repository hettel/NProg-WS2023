package part2.o4_synchronization;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Demo09
{

  public static void main(String[] args)
  {
    AtomicOneValueBuffer<Integer> queue = new AtomicOneValueBuffer<>();

    Runnable producer = () -> {
      for(int i=0; i < 10; i++)
      {
        queue.put(i);
        randomSleep(1000, TimeUnit.MILLISECONDS);
      }
    };
    Runnable consumer = () -> {
      for(int i=0; i < 20; i++)
      {
        System.out.println( queue.take() );
      }
    };
    
    new Thread(producer).start();
    new Thread(producer).start();
    new Thread(consumer).start();
    
  }

  private static void randomSleep(int maxtime, TimeUnit unit)
  {
    try
    {
      unit.sleep( ThreadLocalRandom.current().nextLong(maxtime));
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
  
}
