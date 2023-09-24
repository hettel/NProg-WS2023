package part2.o4_synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class Demo02
{
  static class ThreadSafeCounter
  {
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private Lock rdLock = rwLock.readLock();
    private Lock wtLock = rwLock.writeLock();
    
    private int value = 0;
    
    void increment() {
      wtLock.lock();
      try {
        this.value++;
      }
      finally {
        wtLock.unlock();
      }
    }
    
    int getValue()  {
      rdLock.lock();
      try {
        return this.value;
      }
      finally {
        rdLock.unlock();
      }
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
