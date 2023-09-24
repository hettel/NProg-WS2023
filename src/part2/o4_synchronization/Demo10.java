package part2.o4_synchronization;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class Demo10
{
  static class Counter
  {
    private int counter = 0;

    public void increment()
    {
      this.counter++;
    }

    public int get()
    {
      return this.counter;
    }
  }

  private static final VarHandle COUNTER;
  static
  {
    try
    {
      COUNTER = MethodHandles.privateLookupIn(Counter.class, MethodHandles.lookup())
                             .findVarHandle(Counter.class, "counter", int.class);
    }
    catch (ReflectiveOperationException e)
    {
      throw new ExceptionInInitializerError(e);
    }
  }

  public static void main(String[] args) throws InterruptedException
  {
    final Counter counter = new Counter();
    
    Runnable incrementTask = () -> {
      for(int i=0; i < 1_000_000; i++)
      {
        //counter.increment();
        atomicIncrement(counter);
      }
    };
    
    Thread th1 = new Thread( incrementTask );
    Thread th2 = new Thread( incrementTask );
    Thread th3 = new Thread( incrementTask );
    th1.start(); th2.start(); th3.start();
    
    th1.join(); th2.join(); th3.join();
    
    System.out.println("Value " + counter.get() );
  }

  static void atomicIncrement(Counter counter)
  {
    int value = 0;
    do
    {
      value = (int) COUNTER.get(counter);
    }
    while(COUNTER.compareAndSet(counter, value, value + 1) == false);
  }
}
