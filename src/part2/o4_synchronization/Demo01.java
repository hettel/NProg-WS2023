package part2.o4_synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Demo01
{
  static class ThreadSafeCounter
  {
    private Lock lock = new ReentrantLock();
    
    private int value = 0;
    
    int incrementAndGet()
    {
      lock.lock();
      try
      {
        this.value++;
        return value;
      }
      finally 
      {
        lock.unlock();
      }
    }
  }
  
  public static void main(String[] args) throws Exception
  {
    ThreadSafeCounter counter = new ThreadSafeCounter();
    
    Runnable task = () -> IntStream.range(0, 100).forEach( i -> counter.incrementAndGet() );
    
    Thread th1 = new Thread( task );
    Thread th2 = new Thread( task );
    
    th1.start();
    th2.start();
    
    th1.join();
    th2.join();
    
    System.out.println("Result " + counter.incrementAndGet() );
  }
}
