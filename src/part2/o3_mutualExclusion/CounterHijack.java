package part2.o3_mutualExclusion;

import java.util.concurrent.TimeUnit;

public class CounterHijack
{
  static class Counter
  {
    private int value = 0;
    
    synchronized int incrementAndGet()
    {
      return ++this.value;
    }
  }

  public static void main(String[] args) throws InterruptedException
  {
    Counter counter = new Counter();

    Runnable countTask = () -> {
      while (true)
      {
        System.out.println(counter.incrementAndGet());
      }
    };

    Runnable lockHijackTask = () -> {
      synchronized (counter)
      {
        try
        {
          TimeUnit.DAYS.sleep(Long.MAX_VALUE);
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    };

    new Thread(countTask).start();
    TimeUnit.MILLISECONDS.sleep(10);
    new Thread(lockHijackTask).start();
    System.out.println("done");
  }
}
