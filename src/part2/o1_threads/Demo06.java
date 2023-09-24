package part2.o1_threads;


public class Demo06
{
  static private class SyncCounter
  {
    private int value;

    public SyncCounter()
    {
      this.value = 0;
    }

    public synchronized void increment()
    {
      this.value++;
    }

    public synchronized int getValue()
    {
      return this.value;
    }
  }
  
  public static void main(String[] args) throws InterruptedException
  {
    SyncCounter counter = new SyncCounter();

    Runnable task = () -> {
      for (int i = 0; i < 5_000; i++)
        counter.increment();
    };

    Thread th1 = new Thread(task);
    Thread th2 = new Thread(task);

    // start threads
    th1.start();
    th2.start();

    // Wait until threads are finished
    th1.join();
    th2.join();

    System.out.println("Value " + counter.getValue());
  }
}
