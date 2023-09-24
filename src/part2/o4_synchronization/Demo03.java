package part2.o4_synchronization;

import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

public class Demo03
{
  static class ThreadSafeCounter
  {
    private StampedLock lock = new StampedLock();

    private int value = 0;

    int getValue()
    {
      long stamp = lock.tryOptimisticRead();
      int tmp = this.value;

      if (!lock.validate(stamp))
      {
        stamp = lock.readLock();
        try
        {
          return this.value;
        }
        finally
        {
          lock.unlockRead(stamp);
        }
      }
      else
      {
        return tmp;
      }
    }

    void increment()
    {
      long stamp = lock.writeLock();
      try
      {
        this.value++;
      }
      finally
      {
        lock.unlockWrite(stamp);
      }
    }
  }

  public static void main(String[] args) throws Exception
  {
    ThreadSafeCounter counter = new ThreadSafeCounter();

    Runnable task = () -> IntStream.range(0, 100).forEach(i -> counter.increment());

    Thread th1 = new Thread(task);
    Thread th2 = new Thread(task);

    th1.start();
    th2.start();

    th1.join();
    th2.join();

    System.out.println("Result " + counter.getValue());
  }
}
