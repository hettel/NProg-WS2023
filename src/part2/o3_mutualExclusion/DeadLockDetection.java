package part2.o3_mutualExclusion;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class DeadLockDetection
{
  static class ThreadDeadlockWatchDogTask implements Runnable
  {
    @Override
    public void run()
    {
      ThreadMXBean bean = ManagementFactory.getThreadMXBean();

      while (true)
      {
        long ids[] = bean.findMonitorDeadlockedThreads();
        if (ids != null)
        {
          System.out.println("Deadlock found");
          ThreadInfo threadInfo[] = bean.getThreadInfo(ids);
          for (ThreadInfo thInfo : threadInfo)
          {
            System.out.println("Thread ID   : " + thInfo.getThreadId());
            System.out.println("Thread name : " + thInfo.getThreadName());
            System.out.println("Lock object : " + thInfo.getLockName());
            System.out.println("Lock holder : " + thInfo.getLockOwnerId());
            System.out.println("Lock holder : " + thInfo.getLockOwnerName());
            System.out.println();
          }
          // abort program
          System.err.println("Deadlock detected!");
          System.exit(1);
        }

        // low level sleep function
        LockSupport.parkNanos(1_000_000);
      }
    }
  }

  static class ValueHolder
  {
    int value;

    public ValueHolder(int value)
    {
      this.value = value;
    }
  }

  public static void main(String[] args) throws InterruptedException
  {
    final int LEN = 10;
    List<ValueHolder> list = new ArrayList<>(LEN);
    for (int i = 0; i < LEN; i++)
    {
      list.add(new ValueHolder(i));
    }

    Thread watchDog = new Thread(new ThreadDeadlockWatchDogTask(), "Watch Dog");
    watchDog.setDaemon(true);
    watchDog.setPriority( Thread.MIN_PRIORITY );
    watchDog.start();

    Runnable exchanger = () -> {
      while (Thread.currentThread().isInterrupted() == false)
      {
        int idx1 = ThreadLocalRandom.current().nextInt(LEN);
        int idx2 = ThreadLocalRandom.current().nextInt(LEN);
        swapValues(list.get(idx1), list.get(idx2));
      }
    };

    System.out.println("Start workers");
    Thread worker1 = new Thread(exchanger, "Worker 1");
    Thread worker2 = new Thread(exchanger, "Worker 2");
    worker1.start();
    worker2.start();

    TimeUnit.SECONDS.sleep(3);
    worker1.join();
    worker2.join();
    list.forEach(v -> System.out.println(v.value));
  }

  public static void swapValues(ValueHolder holder1, ValueHolder holder2)
  {
    synchronized (holder1)
    {
      synchronized (holder2)
      {
        int tmp = holder1.value;
        holder1.value = holder2.value;
        holder2.value = tmp;
      }
    }
  }
}
