package part2.o4_synchronization;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Demo04
{
  static class ThreadSafeCounter
  {
    private AtomicInteger counter = new AtomicInteger(0);

    int getValue()
    {
      return this.counter.get();
    }
    
    void increment()
    {
      this.counter.incrementAndGet();
    }
  }
  
  public static void main(String[] args) throws Exception
  {
    ThreadSafeCounter counter = new ThreadSafeCounter();
    
    Runnable task = () -> IntStream.range(0, 100).forEach( i -> counter.increment() );
    
    Thread th1 = new Thread( task );
    Thread th2 = new Thread( task );
    
    th1.start();
    th2.start();
    
    th1.join();
    th2.join();
    
    System.out.println("Result " + counter.getValue() );
  }
}
